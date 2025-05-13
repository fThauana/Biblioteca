package controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import model.Livro;

public class LivroController {
    private List<Livro> livros;

    public LivroController() {
        this.livros = new ArrayList<>();
    }

    public LivroController(List<Livro> livros) {
        this.livros = livros;
    }

    public boolean cadastrarLivro(Livro livro) {
        Optional<Livro> livroExistente = buscarLivroPorCodigo(livro.getCodigo());
        if (livroExistente.isPresent()) {
            System.err.println("Erro ao cadastrar livro: código já existente.");
            return false;
        }
        boolean sucesso = livros.add(livro);
        if (!sucesso) {
            System.err.println("Erro ao cadastrar livro: falha ao adicionar.");
        }
        return sucesso;
    }

    public Optional<Livro> buscarLivroPorCodigo(int codigo) {
        return livros.stream()
                .filter(l -> l.getCodigo() == codigo)
                .findFirst();
    }

    public List<Livro> buscarLivrosPorTitulo(String titulo) {
        return livros.stream()
                .filter(l -> l.getTitulo().toLowerCase().contains(titulo.toLowerCase()))
                .collect(Collectors.toList());
    }

    public List<Livro> buscarLivrosPorAutor(String autor) {
        return livros.stream()
                .filter(l -> l.getAutor().toLowerCase().contains(autor.toLowerCase()))
                .collect(Collectors.toList());
    }

    public List<Livro> buscarLivrosPorCategoria(String categoria) {
        return livros.stream()
                .filter(l -> l.getCategoria().toLowerCase().contains(categoria.toLowerCase()))
                .collect(Collectors.toList());
    }

    public boolean atualizarLivro(int codigo, Livro livroAtualizado) {
        Optional<Livro> livroOpt = buscarLivroPorCodigo(codigo);
        if (livroOpt.isEmpty()) {
            System.err.println("Erro ao atualizar livro: livro não encontrado.");
            return false;
        }
        Livro livro = livroOpt.get();
        boolean removido = livros.remove(livro);
        if (!removido) {
            System.err.println("Erro ao atualizar livro: falha ao remover o livro antigo.");
            return false;
        }
        livroAtualizado.setCodigo(codigo);
        boolean adicionado = livros.add(livroAtualizado);
        if (!adicionado) {
            System.err.println("Erro ao atualizar livro: falha ao adicionar o novo livro.");
        }
        return adicionado;
    }
    

    public boolean removerLivro(int codigo) {
        Optional<Livro> livroOpt = buscarLivroPorCodigo(codigo);
        if (livroOpt.isEmpty()) {
            System.err.println("Erro ao remover livro: livro não encontrado.");
            return false;
        }
        boolean removido = livros.remove(livroOpt.get());
        if (!removido) {
            System.err.println("Erro ao remover livro: falha ao remover.");
        }
        return removido;
    }

    public boolean atualizarExemplaresDisponiveis(int codigo, int novaQuantidade) {
        Optional<Livro> livroOpt = buscarLivroPorCodigo(codigo);
        if (livroOpt.isEmpty()) {
            System.err.println("Erro ao atualizar exemplares: livro não encontrado.");
            return false;
        }
        Livro livro = livroOpt.get();
        livro.setExemplaresDisponiveis(novaQuantidade);
        return true;
    }
    public List<Livro> listarTodosLivros() {
        return new ArrayList<>(livros);
    }

    public List<Livro> listarLivrosDisponiveis() {
        return livros.stream()
                .filter(l -> l.getExemplaresDisponiveis() > 0)
                .collect(Collectors.toList());
    }
}
