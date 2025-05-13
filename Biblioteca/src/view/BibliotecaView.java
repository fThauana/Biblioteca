package view;

import controller.BibliotecaController;
import java.time.LocalDate;
import java.util.Scanner;
import model.Biblioteca;
import model.Livro;
import model.Relatorio;
import model.Usuario;
import util.PreCarga;

public class BibliotecaView {
    private Scanner scanner;
    private BibliotecaController controller;
    private Relatorio relatorio;

    public BibliotecaView() {
        Biblioteca biblioteca = new Biblioteca();
        this.controller = new BibliotecaController(biblioteca);
        this.relatorio = new Relatorio(biblioteca);
        this.scanner = new Scanner(System.in);
    }

    public void iniciar() {
        int opcao;
        do {
            System.out.println("\n <info: aperte um numero de 1 a 0 para executar o sistema!>");
            System.out.println("\n==== COMANDOS DA BIBLIOTECA =======");
            System.out.println("|| 1. Cadastrar Livro             ||");
            System.out.println("|| 2. Cadastrar Usuário           ||");
            System.out.println("|| 3. Realizar Empréstimo         ||");
            System.out.println("|| 4. Registrar Devolução         ||");
            System.out.println("|| 5. Gerar Relatórios            ||");
            System.out.println("|| 6. Executar Pré-carga          ||");
            System.out.println("|| 7. Listar Todos os Livros      ||");
            System.out.println("|| 8. Listar Todos os Usuários    ||");
            System.out.println("|| 0. Sair                        ||");
            System.out.println("====================================");
            System.out.print("Escolha um numero: ");
            opcao = Integer.parseInt(scanner.nextLine());

            switch (opcao) {
                case 1:
                    cadastrarLivro();
                    break;
                case 2:
                    cadastrarUsuario();
                    break;
                case 3:
                    realizarEmprestimo();
                    break;
                case 4:
                    registrarDevolucao();
                    break;
                case 5:
                    gerarRelatorios();
                    break;
                case 6:
                    PreCarga.executar(controller.getLivroController(), controller.getUsuarioController(), controller.getEmprestimoController());
                    break;
                case 7:
                    controller.listarTodosLivros().forEach(System.out::println);
                    break;
                case 8:
                    controller.listarTodosUsuarios().forEach(System.out::println);
                    break;
                case 0:
                    System.out.println("<><><><><><><><><><><><><><><><><>");
                    System.out.println("Encerrando o sistema...");
                    System.out.print("Muito obrigado pelo seu tempo!");
                    break;
                default:
                    System.out.println("Opção inválida!");
            }

        } while (opcao != 0);
    }

    private int lerInteiro(String mensagem) {
        while (true) {
            System.out.print(mensagem);
            String entrada = scanner.nextLine();
            if (entrada.matches("\\d+")) {
                return Integer.parseInt(entrada);
            } else {
                System.out.println("Entrada inválida. Digite um número inteiro.");
            }
        }
    }

    private int diasNoMes(int mes, int ano) {
        switch (mes) {
            case 4: case 6: case 9: case 11:
                return 30;
            case 2:
                return (ano % 4 == 0 && (ano % 100 != 0 || ano % 400 == 0)) ? 29 : 28;
            default:
                return 31;
        }
    }

    private LocalDate lerData(String mensagem) {
        while (true) {
            System.out.print(mensagem);
            String entrada = scanner.nextLine().trim();
    
            
            if (entrada.matches("\\d{2}/\\d{2}/\\d{4}")) {
                String[] partes = entrada.split("/");
                int dia = Integer.parseInt(partes[0]);
                int mes = Integer.parseInt(partes[1]);
                int ano = Integer.parseInt(partes[2]);
    
                int diasMax = diasNoMes(mes, ano);
    
                
                if (ano >= 1900 && ano <= 2100 && mes >= 1 && mes <= 12 && dia >= 1 && dia <= diasMax) {
                    
                    return LocalDate.of(ano, mes, dia);
                } else {
                    System.out.println("Data inválida. Dia ou mês fora do permitido.");
                }
            } else {
                System.out.println("Formato inválido. Use DD/MM/AAAA (ex: 13/05/2025).");
            }
        }
    }
    
    
    
    
    private void cadastrarLivro() {
        int codigo = lerInteiro("Código (número inteiro): ");
        System.out.print("Título: ");
        String titulo = scanner.nextLine().trim();
        if (titulo.isEmpty()) {
            System.out.println("Erro: título não pode ser vazio.");
            return;
        }
    
        System.out.print("Autor: ");
        String autor = scanner.nextLine().trim();
        if (autor.isEmpty()) {
            System.out.println("Erro: autor não pode ser vazio.");
            return;
        }
    
        System.out.print("Categoria: ");
        String categoria = scanner.nextLine().trim();
        if (categoria.isEmpty()) {
            System.out.println("Erro: categoria não pode ser vazia.");
            return;
        }
    
        int ano = lerInteiro("Ano de Publicação: ");
        int exemplares = lerInteiro("Exemplares disponíveis: ");
    
        Livro livro = new Livro(codigo, titulo, autor, categoria, ano, exemplares);
        if (controller.cadastrarLivro(livro)) {
            System.out.println("Livro cadastrado com sucesso.");
        } else {
            System.out.println("Erro: código já existente.");
        }
    }
    

    private void cadastrarUsuario() {
        System.out.print("ID: ");
        int id = Integer.parseInt(scanner.nextLine());
        System.out.print("Nome: ");
        String nome = scanner.nextLine();
        System.out.print("Telefone: ");
        String telefone = scanner.nextLine();
        System.out.print("Endereço: ");
        String endereco = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();

        Usuario usuario = new Usuario(nome, telefone, endereco, email, id);
        if (controller.cadastrarUsuario(usuario)) {
            System.out.println("Usuário cadastrado com sucesso.");
        } else {
            System.out.println("Erro: ID já existente.");
        }
    }

    private void realizarEmprestimo() {
        int codigoLivro = lerInteiro("Código do livro: ");
        int idUsuario = lerInteiro("ID do usuário: ");
        LocalDate dataEmprestimo = lerData("Data de empréstimo (DD/MM/AAAA): ");
        LocalDate dataPrevista = lerData("Data prevista para devolução (DD/MM/AAAA): ");
    
        boolean sucesso = controller.realizarEmprestimo(codigoLivro, idUsuario, dataEmprestimo, dataPrevista);
        System.out.println(sucesso ? "Empréstimo realizado com sucesso." : "Erro ao realizar empréstimo.");
    }


    private void registrarDevolucao() {
    int codigoLivro = lerInteiro("Código do livro: ");
    int idUsuario = lerInteiro("ID do usuário: ");
    LocalDate dataDevolucao = lerData("Data de devolução (DD/MM/AAAA): ");

    boolean sucesso = controller.registrarDevolucao(codigoLivro, idUsuario, dataDevolucao);
    System.out.println(sucesso ? "Devolução registrada com sucesso." : "Erro ao registrar devolução.");
}

    private void gerarRelatorios() {
        System.out.println("1. Livros emprestados");
        System.out.println("2. Usuários com atraso");
        System.out.println("3. Livros mais populares");
        System.out.print("Escolha: ");
        int op = Integer.parseInt(scanner.nextLine());

        switch (op) {
            case 1:
                System.out.println(relatorio.gerarRelatorioLivrosEmprestados());
                break;
            case 2:
                System.out.println(relatorio.gerarRelatorioUsuariosComAtraso());
                break;
            case 3:
                System.out.println(relatorio.gerarRelatorioLivrosPopulares());
                break;
            default:
                System.out.println("Opção inválida.");
        }
    }
}