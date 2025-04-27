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

    // Métodos para gerenciamento de livros
    public boolean cadastrarLivro(Livro livro) {
        try {
            // Verifica se já existe um livro com o mesmo código
            for (Livro l : livros) {
                if (l.getCodigo() == livro.getCodigo()) {
                    return false; // Livro com mesmo código já existe
                }
            }
            return livros.add(livro);
        } catch (Exception e) {
            System.err.println("Erro ao cadastrar livro: " + e.getMessage());
            return false;
        }
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

    // Métodos para gerenciamento de usuários
    public boolean cadastrarUsuario(Usuario usuario) {
        try {
            // Verifica se já existe um usuário com o mesmo ID
            for (Usuario u : usuarios) {
                if (u.getId() == usuario.getId()) {
                    return false; // Usuário com mesmo ID já existe
                }
            }
            return usuarios.add(usuario);
        } catch (Exception e) {
            System.err.println("Erro ao cadastrar usuário: " + e.getMessage());
            return false;
        }
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
                // Verifica se o usuário possui empréstimos ativos
                if (!usuarios.get(i).getEmprestimos().isEmpty()) {
                    return false; // Não pode remover usuário com empréstimos
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

    // Métodos para gerenciamento de empréstimos
    public boolean realizarEmprestimo(Livro livro, Usuario usuario, Emprestimo emprestimo) {
        try {
            // Verifica se o livro existe e está disponível
            if (livro == null || livro.getExemplaresDisponiveis() <= 0) {
                return false;
            }

            // Verifica se o usuário existe
            if (usuario == null) {
                return false;
            }

            // Verifica se o usuário já possui empréstimos
            if (!usuario.getEmprestimos().isEmpty()) {
                return false;
            }

            // Atualiza a quantidade de exemplares disponíveis
            livro.setExemplaresDisponiveis(livro.getExemplaresDisponiveis() - 1);
            
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

    public boolean registrarDevolucao(Emprestimo emprestimo) {
        try {
            if (emprestimo == null || emprestimo.getDataDevolucao() != null) {
                return false; // Empréstimo não encontrado ou já devolvido
            }
            
            // Atualiza a quantidade de exemplares disponíveis
            Livro livro = emprestimo.getLivro();
            livro.setExemplaresDisponiveis(livro.getExemplaresDisponiveis() + 1);
            
            return true;
        } catch (Exception e) {
            System.err.println("Erro ao registrar devolução: " + e.getMessage());
            return false;
        }
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
        
        // Ordena por tempo de atraso (decrescente)
        atrasados.sort((e1, e2) -> Integer.compare(e2.diasAtraso(), e1.diasAtraso()));
        
        return atrasados;
    }

    // Getters e setters
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
