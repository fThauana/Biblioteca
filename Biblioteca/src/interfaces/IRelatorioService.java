package interfaces;

import java.util.List;

import model.Emprestimo;
import model.Livro;
import model.Usuario;

public interface IRelatorioService {
    String gerarRelatorioLivrosEmprestados();
    String gerarRelatorioUsuariosComAtraso();
    String gerarRelatorioLivrosPopulares();
    List<Emprestimo> listarEmprestimosAtivos();
    List<Emprestimo> listarEmprestimosComAtraso();
    List<Usuario> listarUsuariosComAtraso();
    List<Livro> listarLivrosMaisEmprestados();
}
