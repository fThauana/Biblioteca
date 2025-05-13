package util;

import controller.EmprestimoController;
import controller.LivroController;
import controller.UsuarioController;
import java.time.LocalDate;
import model.Livro;
import model.Usuario;

public class PreCarga {

    public static void executar(LivroController livroController, UsuarioController usuarioController, EmprestimoController emprestimoController) {
    
        livroController.cadastrarLivro(new Livro(1, "Java para Iniciantes", "Herbert Schildt", "Programação", 2018, 3));
        livroController.cadastrarLivro(new Livro(2, "Clean Code", "Robert C. Martin", "Engenharia de Software", 2009, 2));
        livroController.cadastrarLivro(new Livro(3, "Algoritmos em Java", "Thomas Cormen", "Algoritmos", 2015, 1));


        usuarioController.cadastrarUsuario(new Usuario("Lucas", "41 99999-0001", "Rua A", "lucas@email.com", 1));
        usuarioController.cadastrarUsuario(new Usuario("Ana", "41 99999-0002", "Rua B", "ana@email.com", 2));

   
        Livro livro = livroController.buscarLivroPorCodigo(1).orElse(null);
        Usuario usuario = usuarioController.buscarUsuarioPorId(1).orElse(null);

        if (livro != null && usuario != null) {
            emprestimoController.realizarEmprestimo(
                livro.getCodigo(),
                usuario.getId(),
                LocalDate.now().minusDays(10),
                LocalDate.now().minusDays(3)
            );

    
            emprestimoController.registrarDevolucao(
                livro.getCodigo(),
                usuario.getId(),
                LocalDate.now()
            );
        }

        System.out.println(" Pré-carga concluída com sucesso.");
    }
}