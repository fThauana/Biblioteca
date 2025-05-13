package model;

import java.util.ArrayList;
import java.util.List;

public class Biblioteca {
    private List<Livro> livros;
    private List<Usuario> usuarios;
    private List<Emprestimo> emprestimos;

    public Biblioteca() {
        this.livros = new ArrayList<>();
        this.usuarios = new ArrayList<>();
        this.emprestimos = new ArrayList<>();
    }


    public boolean cadastrarLivro(Livro livro) {
        if (livro == null) {
            System.err.println("Erro ao cadastrar livro: objeto nulo.");
            return false;
        }
    
        for (Livro l : livros) {
            if (l.getCodigo() == livro.getCodigo()) {
                System.err.println("Erro ao cadastrar livro: código já existente.");
                return false;
            }
        }
    
        boolean sucesso = livros.add(livro);
        if (!sucesso) {
            System.err.println("Erro ao cadastrar livro: falha ao adicionar.");
        }
        return sucesso;
    }

    public Livro buscarLivroPorCodigo(int codigo) {
        for (Livro livro : livros) {
            if (livro.getCodigo() == codigo) {
                return livro;
            }
        }
        return null;
    }

    public List<Livro> buscarLivrosPorTitulo(String titulo) {
        List<Livro> resultado = new ArrayList<>();
        for (Livro livro : livros) {
            if (livro.getTitulo().toLowerCase().contains(titulo.toLowerCase())) {
                resultado.add(livro);
            }
        }
        return resultado;
    }

    public List<Livro> buscarLivrosPorAutor(String autor) {
        List<Livro> resultado = new ArrayList<>();
        for (Livro livro : livros) {
            if (livro.getAutor().toLowerCase().contains(autor.toLowerCase())) {
                resultado.add(livro);
            }
        }
        return resultado;
    }

    public List<Livro> buscarLivrosPorCategoria(String categoria) {
        List<Livro> resultado = new ArrayList<>();
        for (Livro livro : livros) {
            if (livro.getCategoria().toLowerCase().contains(categoria.toLowerCase())) {
                resultado.add(livro);
            }
        }
        return resultado;
    }

    public boolean atualizarLivro(int codigo, Livro livroAtualizado) {
        for (int i = 0; i < livros.size(); i++) {
            if (livros.get(i).getCodigo() == codigo) {
                livros.set(i, livroAtualizado);
                return true;
            }
        }
        return false;
    }

    public boolean removerLivro(int codigo) {
        for (int i = 0; i < livros.size(); i++) {
            if (livros.get(i).getCodigo() == codigo) {
                livros.remove(i);
                return true;
            }
        }
        return false;
    }

    public List<Livro> listarTodosLivros() {
        return new ArrayList<>(livros);
    }

    public List<Livro> listarLivrosDisponiveis() {
        List<Livro> disponiveis = new ArrayList<>();
        for (Livro livro : livros) {
            if (livro.getExemplaresDisponiveis() > 0) {
                disponiveis.add(livro);
            }
        }
        return disponiveis;
    }

   
    public boolean cadastrarUsuario(Usuario usuario) {
        if (usuario == null) {
            System.err.println("Erro ao cadastrar usuário: objeto nulo.");
            return false;
        }
    
        for (Usuario u : usuarios) {
            if (u.getId() == usuario.getId()) {
                System.err.println("Erro ao cadastrar usuário: ID já existente.");
                return false;
            }
        }
    
        boolean sucesso = usuarios.add(usuario);
        if (!sucesso) {
            System.err.println("Erro ao cadastrar usuário: falha ao adicionar.");
        }
        return sucesso;
    }
    

    public Usuario buscarUsuarioPorId(int id) {
        for (Usuario usuario : usuarios) {
            if (usuario.getId() == id) {
                return usuario;
            }
        }
        return null;
    }

    public List<Usuario> buscarUsuariosPorNome(String nome) {
        List<Usuario> resultado = new ArrayList<>();
        for (Usuario usuario : usuarios) {
            if (usuario.getNome().toLowerCase().contains(nome.toLowerCase())) {
                resultado.add(usuario);
            }
        }
        return resultado;
    }

    public boolean atualizarUsuario(int id, Usuario usuarioAtualizado) {
        for (int i = 0; i < usuarios.size(); i++) {
            if (usuarios.get(i).getId() == id) {
                usuarios.set(i, usuarioAtualizado);
                return true;
            }
        }
        return false;
    }

    public boolean removerUsuario(int id) {
        for (int i = 0; i < usuarios.size(); i++) {
            if (usuarios.get(i).getId() == id) {
                
                if (!usuarios.get(i).getEmprestimos().isEmpty()) {
                    return false;
                }
                usuarios.remove(i);
                return true;
            }
        }
        return false;
    }

    public List<Usuario> listarTodosUsuarios() {
        return new ArrayList<>(usuarios);
    }


    public boolean realizarEmprestimo(Livro livro, Usuario usuario, Emprestimo emprestimo) {
        if (livro == null || livro.getExemplaresDisponiveis() <= 0) {
            System.err.println("Erro ao realizar empréstimo: livro inválido ou sem exemplares.");
            return false;
        }
    
        if (usuario == null) {
            System.err.println("Erro ao realizar empréstimo: usuário inválido.");
            return false;
        }
    
        if (!usuario.getEmprestimos().isEmpty()) {
            System.err.println("Erro ao realizar empréstimo: usuário já possui empréstimo.");
            return false;
        }
    
        livro.setExemplaresDisponiveis(livro.getExemplaresDisponiveis() - 1);
        boolean adicionado = emprestimos.add(emprestimo);
        if (!adicionado) {
            System.err.println("Erro ao realizar empréstimo: falha ao registrar.");
            return false;
        }
    
        usuario.getEmprestimos().add(emprestimo);
        return true;
    }

    public boolean registrarDevolucao(Emprestimo emprestimo) {
        if (emprestimo == null) {
            System.err.println("Erro ao registrar devolução: empréstimo inválido.");
            return false;
        }
    
        if (emprestimo.getDataDevolucao() != null) {
            System.err.println("Erro ao registrar devolução: devolução já registrada.");
            return false;
        }
    
        Livro livro = emprestimo.getLivro();
        if (livro == null) {
            System.err.println("Erro ao registrar devolução: livro inválido.");
            return false;
        }
    
        livro.setExemplaresDisponiveis(livro.getExemplaresDisponiveis() + 1);
        return true;
    }

    public List<Emprestimo> listarTodosEmprestimos() {
        return new ArrayList<>(emprestimos);
    }

    public List<Emprestimo> listarEmprestimosAtivos() {
        List<Emprestimo> ativos = new ArrayList<>();
        for (Emprestimo emprestimo : emprestimos) {
            if (emprestimo.getDataDevolucao() == null) {
                ativos.add(emprestimo);
            }
        }
        return ativos;
    }

    public List<Emprestimo> listarEmprestimosDevolvidos() {
        List<Emprestimo> devolvidos = new ArrayList<>();
        for (Emprestimo emprestimo : emprestimos) {
            if (emprestimo.getDataDevolucao() != null) {
                devolvidos.add(emprestimo);
            }
        }
        return devolvidos;
    }

    public List<Emprestimo> listarEmprestimosComAtraso() {
        List<Emprestimo> atrasados = new ArrayList<>();
        for (Emprestimo emprestimo : emprestimos) {
            if (emprestimo.getDataDevolucao() != null && emprestimo.diasAtraso() > 0) {
                atrasados.add(emprestimo);
            }
        }
        

        atrasados.sort((e1, e2) -> Integer.compare(e2.diasAtraso(), e1.diasAtraso()));
        
        return atrasados;
    }

    public List<Livro> getLivros() {
        return livros;
    }

    public void setLivros(List<Livro> livros) {
        this.livros = livros;
    }

    public List<Usuario> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(List<Usuario> usuarios) {
        this.usuarios = usuarios;
    }

    public List<Emprestimo> getEmprestimos() {
        return emprestimos;
    }

    public void setEmprestimos(List<Emprestimo> emprestimos) {
        this.emprestimos = emprestimos;
    }
}
