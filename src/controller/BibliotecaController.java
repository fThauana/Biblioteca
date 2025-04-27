package controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import model.Biblioteca;
import model.Emprestimo;
import model.Livro;
import model.Usuario;

public class BibliotecaController {
    private Biblioteca biblioteca;
    private LivroController livroController;
    private UsuarioController usuarioController;
    private EmprestimoController emprestimoController;

    public BibliotecaController(Biblioteca biblioteca) {
        this.biblioteca = biblioteca;
        this.livroController = new LivroController(biblioteca.getLivros());
        this.usuarioController = new UsuarioController(biblioteca.getUsuarios());
        this.emprestimoController = new EmprestimoController(biblioteca.getEmprestimos(), livroController, usuarioController);
    }

    // Métodos para gerenciamento de livros
    public boolean cadastrarLivro(Livro livro) {
        return livroController.cadastrarLivro(livro);
    }

    public Optional<Livro> buscarLivroPorCodigo(int codigo) {
        return livroController.buscarLivroPorCodigo(codigo);
    }

    public List<Livro> buscarLivrosPorTitulo(String titulo) {
        return livroController.buscarLivrosPorTitulo(titulo);
    }

    public List<Livro> buscarLivrosPorAutor(String autor) {
        return livroController.buscarLivrosPorAutor(autor);
    }

    public List<Livro> buscarLivrosPorCategoria(String categoria) {
        return livroController.buscarLivrosPorCategoria(categoria);
    }

    public boolean atualizarLivro(int codigo, Livro livroAtualizado) {
        return livroController.atualizarLivro(codigo, livroAtualizado);
    }

    public boolean removerLivro(int codigo) {
        return livroController.removerLivro(codigo);
    }

    public List<Livro> listarTodosLivros() {
        return livroController.listarTodosLivros();
    }

    public List<Livro> listarLivrosDisponiveis() {
        return livroController.listarLivrosDisponiveis();
    }

    // Métodos para gerenciamento de usuários
    public boolean cadastrarUsuario(Usuario usuario) {
        return usuarioController.cadastrarUsuario(usuario);
    }

    public Optional<Usuario> buscarUsuarioPorId(int id) {
        return usuarioController.buscarUsuarioPorId(id);
    }

    public List<Usuario> buscarUsuariosPorNome(String nome) {
        return usuarioController.buscarUsuariosPorNome(nome);
    }

    public boolean atualizarUsuario(int id, Usuario usuarioAtualizado) {
        return usuarioController.atualizarUsuario(id, usuarioAtualizado);
    }

    public boolean removerUsuario(int id) {
        return usuarioController.removerUsuario(id);
    }

    public List<Usuario> listarTodosUsuarios() {
        return usuarioController.listarTodosUsuarios();
    }

    // Métodos para gerenciamento de empréstimos
    public boolean realizarEmprestimo(int codigoLivro, int idUsuario, LocalDate dataEmprestimo, LocalDate dataPrevista) {
        return emprestimoController.realizarEmprestimo(codigoLivro, idUsuario, dataEmprestimo, dataPrevista);
    }

    public boolean registrarDevolucao(int codigoLivro, int idUsuario, LocalDate dataDevolucao) {
        return emprestimoController.registrarDevolucao(codigoLivro, idUsuario, dataDevolucao);
    }

    public List<Emprestimo> listarTodosEmprestimos() {
        return emprestimoController.listarTodosEmprestimos();
    }

    public List<Emprestimo> listarEmprestimosAtivos() {
        return emprestimoController.listarEmprestimosAtivos();
    }

    public List<Emprestimo> listarEmprestimosDevolvidos() {
        return emprestimoController.listarEmprestimosDevolvidos();
    }

    public List<Emprestimo> listarEmprestimosComAtraso() {
        return emprestimoController.listarEmprestimosComAtraso();
    }

    // Getters para os controllers
    public LivroController getLivroController() {
        return livroController;
    }

    public UsuarioController getUsuarioController() {
        return usuarioController;
    }

    public EmprestimoController getEmprestimoController() {
        return emprestimoController;
    }
}
