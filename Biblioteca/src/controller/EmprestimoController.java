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
        Optional<Livro> livroOpt = livroController.buscarLivroPorCodigo(codigoLivro);
        if (livroOpt.isEmpty()) {
            System.err.println("Erro ao realizar empréstimo: Livro não encontrado.");
            return false;
        }
    
        Livro livro = livroOpt.get();
        if (livro.getExemplaresDisponiveis() <= 0) {
            System.err.println("Erro ao realizar empréstimo: Livro sem exemplares disponíveis.");
            return false;
        }
    
        Optional<Usuario> usuarioOpt = usuarioController.buscarUsuarioPorId(idUsuario);
        if (usuarioOpt.isEmpty()) {
            System.err.println("Erro ao realizar empréstimo: Usuário não encontrado.");
            return false;
        }
    
        Usuario usuario = usuarioOpt.get();
        if (!usuario.getEmprestimos().isEmpty()) {
            System.err.println("Erro ao realizar empréstimo: Usuário já possui empréstimo ativo.");
            return false;
        }
    
        Emprestimo emprestimo = new Emprestimo(livro, usuario, dataEmprestimo, dataPrevista, null);
        livroController.atualizarExemplaresDisponiveis(codigoLivro, livro.getExemplaresDisponiveis() - 1);
        emprestimos.add(emprestimo);
        usuario.getEmprestimos().add(emprestimo);
    
        return true;
    }

    public boolean registrarDevolucao(int codigoLivro, int idUsuario, LocalDate dataDevolucao) {
        Optional<Emprestimo> emprestimoOpt = buscarEmprestimoAtivo(codigoLivro, idUsuario);
        if (emprestimoOpt.isEmpty()) {
            System.err.println("Erro ao registrar devolução: Empréstimo não encontrado.");
            return false;
        }
    
        Emprestimo emprestimo = emprestimoOpt.get();
    
        if (emprestimo.getDataDevolucao() != null) {
            System.err.println("Erro ao registrar devolução: Esse livro já foi devolvido.");
            return false;
        }
    
        emprestimo.setDataDevolucao(dataDevolucao);
        Livro livro = emprestimo.getLivro();
        livroController.atualizarExemplaresDisponiveis(livro.getCodigo(), livro.getExemplaresDisponiveis() + 1);
    
        return true;
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
