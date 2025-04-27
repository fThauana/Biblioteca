package interfaces;

import java.util.List;
import java.util.Optional;

import model.Livro;

public interface ILivroService {
    boolean cadastrarLivro(Livro livro);
    Optional<Livro> buscarLivroPorCodigo(int codigo);
    List<Livro> buscarLivrosPorTitulo(String titulo);
    List<Livro> buscarLivrosPorAutor(String autor);
    List<Livro> buscarLivrosPorCategoria(String categoria);
    boolean atualizarLivro(int codigo, Livro livroAtualizado);
    boolean removerLivro(int codigo);
    List<Livro> listarTodosLivros();
    List<Livro> listarLivrosDisponiveis();
}
