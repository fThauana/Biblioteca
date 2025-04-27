package interfaces;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import model.Emprestimo;

public interface IEmprestimoService {
    boolean realizarEmprestimo(int codigoLivro, int idUsuario, LocalDate dataEmprestimo, LocalDate dataPrevista);
    boolean registrarDevolucao(int codigoLivro, int idUsuario, LocalDate dataDevolucao);
    Optional<Emprestimo> buscarEmprestimoAtivo(int codigoLivro, int idUsuario);
    List<Emprestimo> listarTodosEmprestimos();
    List<Emprestimo> listarEmprestimosAtivos();
    List<Emprestimo> listarEmprestimosDevolvidos();
    List<Emprestimo> listarEmprestimosComAtraso();
    List<Emprestimo> listarEmprestimosPorUsuario(int idUsuario);
    List<Emprestimo> listarEmprestimosPorLivro(int codigoLivro);
}
