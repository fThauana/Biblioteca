package controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import model.Emprestimo;
import model.Usuario;

public class UsuarioController {
    private List<Usuario> usuarios;

    public UsuarioController() {
        this.usuarios = new ArrayList<>();
    }

    public UsuarioController(List<Usuario> usuarios) {
        this.usuarios = usuarios;
    }

    public boolean cadastrarUsuario(Usuario usuario) {
        Optional<Usuario> usuarioExistente = buscarUsuarioPorId(usuario.getId());
        if (usuarioExistente.isPresent()) {
            System.err.println("Erro ao cadastrar usuário: ID já existente.");
            return false;
        }
        boolean sucesso = usuarios.add(usuario);
        if (!sucesso) {
            System.err.println("Erro ao cadastrar usuário: falha ao adicionar.");
        }
        return sucesso;
    }

    public Optional<Usuario> buscarUsuarioPorId(int id) {
        return usuarios.stream()
                .filter(u -> u.getId() == id)
                .findFirst();
    }

    public List<Usuario> buscarUsuariosPorNome(String nome) {
        return usuarios.stream()
                .filter(u -> u.getNome().toLowerCase().contains(nome.toLowerCase()))
                .collect(Collectors.toList());
    }

    public boolean atualizarUsuario(int id, Usuario usuarioAtualizado) {
        Optional<Usuario> usuarioOpt = buscarUsuarioPorId(id);
        if (usuarioOpt.isEmpty()) {
            System.err.println("Erro ao atualizar usuário: usuário não encontrado.");
            return false;
        }
    
        Usuario usuario = usuarioOpt.get();
        boolean removido = usuarios.remove(usuario);
        if (!removido) {
            System.err.println("Erro ao atualizar usuário: falha ao remover o antigo.");
            return false;
        }
    
        List<?> emprestimos = usuario.getEmprestimos();
        usuarioAtualizado = new Usuario(
            usuarioAtualizado.getNome(),
            usuarioAtualizado.getTelefone(),
            usuarioAtualizado.getendereco(),
            usuarioAtualizado.getEmail(),
            id
        );
    
        for (Object emprestimoObj : emprestimos) {
            if (emprestimoObj instanceof Emprestimo) {
                Emprestimo emprestimo = (Emprestimo) emprestimoObj;
                usuarioAtualizado.getEmprestimos().add(emprestimo);
            }
        }
    
        boolean adicionado = usuarios.add(usuarioAtualizado);
        if (!adicionado) {
            System.err.println("Erro ao atualizar usuário: falha ao adicionar o novo.");
        }
        return adicionado;
    }

    public boolean removerUsuario(int id) {
        Optional<Usuario> usuarioOpt = buscarUsuarioPorId(id);
        if (usuarioOpt.isEmpty()) {
            System.err.println("Erro ao remover usuário: usuário não encontrado.");
            return false;
        }
    
        Usuario usuario = usuarioOpt.get();
        if (!usuario.getEmprestimos().isEmpty()) {
            System.err.println("Erro ao remover usuário: existem empréstimos associados.");
            return false;
        }
    
        boolean removido = usuarios.remove(usuario);
        if (!removido) {
            System.err.println("Erro ao remover usuário: falha ao remover.");
        }
        return removido;
    }

    public List<Usuario> listarTodosUsuarios() {
        return new ArrayList<>(usuarios);
    }

    public List<Usuario> listarUsuariosComEmprestimos() {
        return usuarios.stream()
                .filter(u -> !u.getEmprestimos().isEmpty())
                .collect(Collectors.toList());
    }

    public boolean verificarUsuarioPossuiEmprestimo(int id) {
        Optional<Usuario> usuarioOpt = buscarUsuarioPorId(id);
        if (usuarioOpt.isPresent()) {
            Usuario usuario = usuarioOpt.get();
            return usuario.getEmprestimos().size() > 0;
        }
        return false;
    }

}
