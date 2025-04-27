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
        try {
            // Verifica se já existe um usuário com o mesmo ID
            Optional<Usuario> usuarioExistente = buscarUsuarioPorId(usuario.getId());
            if (usuarioExistente.isPresent()) {
                return false; // Usuário com mesmo ID já existe
            }
            return usuarios.add(usuario);
        } catch (Exception e) {
            System.err.println("Erro ao cadastrar usuário: " + e.getMessage());
            return false;
        }
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
        try {
            Optional<Usuario> usuarioOpt = buscarUsuarioPorId(id);
            if (usuarioOpt.isPresent()) {
                Usuario usuario = usuarioOpt.get();
                usuarios.remove(usuario);
                // Mantém o ID original e a lista de empréstimos
                List<?> emprestimos = usuario.getEmprestimos();
                usuarioAtualizado = new Usuario(
                    usuarioAtualizado.getNome(),
                    usuarioAtualizado.getTelefone(),
                    usuarioAtualizado.getEndereço(),
                    usuarioAtualizado.getEmail(),
                    id
                );
                // Adiciona os empréstimos de volta
                for (Object emprestimoObj : emprestimos) { // << CORRIGIDO AQUI
                    if (emprestimoObj instanceof Emprestimo) { 
                        Emprestimo emprestimo = (Emprestimo) emprestimoObj;
                        usuarioAtualizado.getEmprestimos().add(emprestimo);
                    }
                }
                usuarios.add(usuarioAtualizado);
                return true;
            }
            return false;
        } catch (Exception e) {
            System.err.println("Erro ao atualizar usuário: " + e.getMessage());
            return false;
        }
    }

    public boolean removerUsuario(int id) {
        try {
            Optional<Usuario> usuarioOpt = buscarUsuarioPorId(id);
            if (usuarioOpt.isPresent()) {
                Usuario usuario = usuarioOpt.get();
                // Verifica se o usuário possui empréstimos ativos
                if (!usuario.getEmprestimos().isEmpty()) {
                    return false; // Não pode remover usuário com empréstimos
                }
                return usuarios.remove(usuario);
            }
            return false;
        } catch (Exception e) {
            System.err.println("Erro ao remover usuário: " + e.getMessage());
            return false;
        }
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
            return !usuario.getEmprestimos().isEmpty();
        }
        return false;
    }
}
