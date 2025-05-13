package interfaces;

import java.util.List;
import java.util.Optional;

import model.Usuario;

public interface IUsuarioService {
    boolean cadastrarUsuario(Usuario usuario);
    Optional<Usuario> buscarUsuarioPorId(int id);
    List<Usuario> buscarUsuariosPorNome(String nome);
    boolean atualizarUsuario(int id, Usuario usuarioAtualizado);
    boolean removerUsuario(int id);
    List<Usuario> listarTodosUsuarios();
    List<Usuario> listarUsuariosComEmprestimos();
    boolean verificarUsuarioPossuiEmprestimo(int id);
}
