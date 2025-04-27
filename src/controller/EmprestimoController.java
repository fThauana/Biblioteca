package controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import model.Emprestimo;
import model.Livro;
import model.Usuario;

public class EmprestimoController {
    private List<Emprestimo> emprestimos;
    private LivroController livroController;
    private UsuarioController usuarioController;

    public EmprestimoController(LivroController livroController, UsuarioController usuarioController) {
        this.emprestimos = new ArrayList<>();
        this.livroController = livroController;
        this.usuarioController = usuarioController;
    }

    public EmprestimoController(List<Emprestimo> emprestimos, LivroController livroController, UsuarioController usuarioController) {
        this.emprestimos = emprestimos;
        this.livroController = livroController;
        this.usuarioController = usuarioController;
    }

    public boolean realizarEmprestimo(int codigoLivro, int idUsuario, LocalDate dataEmprestimo, LocalDate dataPrevista) {
        try {
            // Verifica se o livro existe e está disponível
            Optional<Livro> livroOpt = livroController.buscarLivroPorCodigo(codigoLivro);
            if (livroOpt.isEmpty() || livroOpt.get().getExemplaresDisponiveis() <= 0) {
                return false; // Livro não existe ou não está disponível
            }

            // Verifica se o usuário existe
            Optional<Usuario> usuarioOpt = usuarioController.buscarUsuarioPorId(idUsuario);
            if (usuarioOpt.isEmpty()) {
                return false; // Usuário não existe
            }

            // Verifica se o usuário já possui empréstimos
            Usuario usuario = usuarioOpt.get();
            if (!usuario.getEmprestimos().isEmpty()) {
                return false; // Usuário já possui empréstimos
            }

            // Cria o empréstimo
            Livro livro = livroOpt.get();
            Emprestimo emprestimo = new Emprestimo(livro, usuario, dataEmprestimo, dataPrevista, null);
            
            // Atualiza a quantidade de exemplares disponíveis
            livroController.atualizarExemplaresDisponiveis(codigoLivro, livro.getExemplaresDisponiveis() - 1);
            
            // Adiciona o empréstimo à lista
            emprestimos.add(emprestimo);
            
            // Adiciona o empréstimo ao usuário
            usuario.getEmprestimos().add(emprestimo);
            
            return true;
        } catch (Exception e) {
            System.err.println("Erro ao realizar empréstimo: " + e.getMessage());
            return false;
        }
    }

    public boolean registrarDevolucao(int codigoLivro, int idUsuario, LocalDate dataDevolucao) {
        try {
            // Busca o empréstimo
            Optional<Emprestimo> emprestimoOpt = buscarEmprestimoAtivo(codigoLivro, idUsuario);
            if (emprestimoOpt.isEmpty()) {
                return false; // Empréstimo não encontrado
            }

            Emprestimo emprestimo = emprestimoOpt.get();
            
            // Verifica se o empréstimo já foi devolvido
            if (emprestimo.getDataDevolucao() != null) {
                return false; // Já devolvido
            }

            // Registra a data de devolução
            emprestimo.setDataDevolucao(dataDevolucao);
            
            // Atualiza a quantidade de exemplares disponíveis
            Livro livro = emprestimo.getLivro();
            livroController.atualizarExemplaresDisponiveis(livro.getCodigo(), livro.getExemplaresDisponiveis() + 1);
            
            return true;
        } catch (Exception e) {
            System.err.println("Erro ao registrar devolução: " + e.getMessage());
            return false;
        }
    }

    public Optional<Emprestimo> buscarEmprestimoAtivo(int codigoLivro, int idUsuario) {
        return emprestimos.stream()
                .filter(e -> e.getLivro().getCodigo() == codigoLivro && 
                       e.getUsuario().getId() == idUsuario && 
                       e.getDataDevolucao() == null)
                .findFirst();
    }

    public List<Emprestimo> listarTodosEmprestimos() {
        return new ArrayList<>(emprestimos);
    }

    public List<Emprestimo> listarEmprestimosAtivos() {
        return emprestimos.stream()
                .filter(e -> e.getDataDevolucao() == null)
                .collect(Collectors.toList());
    }

    public List<Emprestimo> listarEmprestimosDevolvidos() {
        return emprestimos.stream()
                .filter(e -> e.getDataDevolucao() != null)
                .collect(Collectors.toList());
    }

    public List<Emprestimo> listarEmprestimosComAtraso() {
        return emprestimos.stream()
                .filter(e -> e.getDataDevolucao() != null && e.diasAtraso() > 0)
                .sorted(Comparator.comparing(Emprestimo::diasAtraso).reversed())
                .collect(Collectors.toList());
    }

    public List<Emprestimo> listarEmprestimosPorUsuario(int idUsuario) {
        return emprestimos.stream()
                .filter(e -> e.getUsuario().getId() == idUsuario)
                .collect(Collectors.toList());
    }

    public List<Emprestimo> listarEmprestimosPorLivro(int codigoLivro) {
        return emprestimos.stream()
                .filter(e -> e.getLivro().getCodigo() == codigoLivro)
                .collect(Collectors.toList());
    }
}
