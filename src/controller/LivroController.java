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
        try {
            // Verifica se já existe um livro com o mesmo código
            Optional<Livro> livroExistente = buscarLivroPorCodigo(livro.getCodigo());
            if (livroExistente.isPresent()) {
                return false; // Livro com mesmo código já existe
            }
            return livros.add(livro);
        } catch (Exception e) {
            System.err.println("Erro ao cadastrar livro: " + e.getMessage());
            return false;
        }
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
        try {
            Optional<Livro> livroOpt = buscarLivroPorCodigo(codigo);
            if (livroOpt.isPresent()) {
                Livro livro = livroOpt.get();
                livros.remove(livro);
                livroAtualizado.setCodigo(codigo); // Mantém o código original
                livros.add(livroAtualizado);
                return true;
            }
            return false;
        } catch (Exception e) {
            System.err.println("Erro ao atualizar livro: " + e.getMessage());
            return false;
        }
    }

    public boolean removerLivro(int codigo) {
        try {
            Optional<Livro> livroOpt = buscarLivroPorCodigo(codigo);
            return livroOpt.map(livros::remove).orElse(false);
        } catch (Exception e) {
            System.err.println("Erro ao remover livro: " + e.getMessage());
            return false;
        }
    }

    public boolean atualizarExemplaresDisponiveis(int codigo, int novaQuantidade) {
        try {
            Optional<Livro> livroOpt = buscarLivroPorCodigo(codigo);
            if (livroOpt.isPresent()) {
                Livro livro = livroOpt.get();
                livro.setExemplaresDisponiveis(novaQuantidade);
                return true;
            }
            return false;
        } catch (Exception e) {
            System.err.println("Erro ao atualizar exemplares: " + e.getMessage());
            return false;
        }
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
