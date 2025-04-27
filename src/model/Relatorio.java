package model;

import interfaces.IRelatorioService;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Relatorio implements IRelatorioService {
    private Biblioteca biblioteca;

    public Relatorio(Biblioteca biblioteca) {
        this.biblioteca = biblioteca;
    }

    @Override
    public String gerarRelatorioLivrosEmprestados() {
        List<Emprestimo> emprestimosAtivos = listarEmprestimosAtivos();
        
        if (emprestimosAtivos.isEmpty()) {
            return "Não há livros emprestados no momento.";
        }
        
        StringBuilder relatorio = new StringBuilder();
        relatorio.append("=== RELATÓRIO DE LIVROS EMPRESTADOS ===\n");
        relatorio.append("Total de livros emprestados: ").append(emprestimosAtivos.size()).append("\n\n");
        
        for (Emprestimo emprestimo : emprestimosAtivos) {
            Livro livro = emprestimo.getLivro();
            Usuario usuario = emprestimo.getUsuario();
            
            relatorio.append("Livro: ").append(livro.getTitulo()).append(" (Código: ").append(livro.getCodigo()).append(")\n");
            relatorio.append("Autor: ").append(livro.getAutor()).append("\n");
            relatorio.append("Emprestado para: ").append(usuario.getNome()).append(" (ID: ").append(usuario.getId()).append(")\n");
            relatorio.append("Data de empréstimo: ").append(emprestimo.getDataEmprestimo()).append("\n");
            relatorio.append("Data prevista para devolução: ").append(emprestimo.getDataPrevista()).append("\n");
            relatorio.append("------------------------------------------\n");
        }
        
        return relatorio.toString();
    }

    @Override
    public String gerarRelatorioUsuariosComAtraso() {
        List<Usuario> usuariosComAtraso = listarUsuariosComAtraso();
        
        if (usuariosComAtraso.isEmpty()) {
            return "Não há usuários com atraso na devolução.";
        }
        
        StringBuilder relatorio = new StringBuilder();
        relatorio.append("=== RELATÓRIO DE USUÁRIOS COM ATRASO ===\n");
        relatorio.append("Total de usuários com atraso: ").append(usuariosComAtraso.size()).append("\n\n");
        
        for (Usuario usuario : usuariosComAtraso) {
            relatorio.append("Usuário: ").append(usuario.getNome()).append(" (ID: ").append(usuario.getId()).append(")\n");
            relatorio.append("Contato: ").append(usuario.getTelefone()).append(" / ").append(usuario.getEmail()).append("\n");
            
            // Busca os empréstimos com atraso deste usuário
            List<Emprestimo> emprestimosAtrasados = usuario.getEmprestimos().stream()
                    .filter(e -> e.getDataDevolucao() != null && e.diasAtraso() > 0)
                    .sorted(Comparator.comparing(Emprestimo::diasAtraso).reversed())
                    .collect(Collectors.toList());
            
            for (Emprestimo emprestimo : emprestimosAtrasados) {
                Livro livro = emprestimo.getLivro();
                relatorio.append("  - Livro: ").append(livro.getTitulo()).append("\n");
                relatorio.append("    Dias de atraso: ").append(emprestimo.diasAtraso()).append("\n");
                relatorio.append("    Data prevista: ").append(emprestimo.getDataPrevista()).append("\n");
                relatorio.append("    Data de devolução: ").append(emprestimo.getDataDevolucao()).append("\n");
            }
            
            relatorio.append("------------------------------------------\n");
        }
        
        return relatorio.toString();
    }

    @Override
    public String gerarRelatorioLivrosPopulares() {
        List<Livro> livrosPopulares = listarLivrosMaisEmprestados();
        
        if (livrosPopulares.isEmpty()) {
            return "Não há histórico de empréstimos para gerar o relatório.";
        }
        
        StringBuilder relatorio = new StringBuilder();
        relatorio.append("=== RELATÓRIO DE LIVROS MAIS POPULARES ===\n\n");
        
        int posicao = 1;
        for (Livro livro : livrosPopulares) {
            // Conta quantas vezes o livro foi emprestado
            long quantidadeEmprestimos = biblioteca.listarTodosEmprestimos().stream()
                    .filter(e -> e.getLivro().getCodigo() == livro.getCodigo())
                    .count();
            
            relatorio.append(posicao).append(". ").append(livro.getTitulo()).append("\n");
            relatorio.append("   Autor: ").append(livro.getAutor()).append("\n");
            relatorio.append("   Categoria: ").append(livro.getCategoria()).append("\n");
            relatorio.append("   Quantidade de empréstimos: ").append(quantidadeEmprestimos).append("\n");
            relatorio.append("   Exemplares disponíveis: ").append(livro.getExemplaresDisponiveis()).append("\n");
            relatorio.append("------------------------------------------\n");
            
            posicao++;
        }
        
        return relatorio.toString();
    }

    @Override
    public List<Emprestimo> listarEmprestimosAtivos() {
        return biblioteca.listarEmprestimosAtivos();
    }

    @Override
    public List<Emprestimo> listarEmprestimosComAtraso() {
        return biblioteca.listarEmprestimosComAtraso();
    }

    @Override
    public List<Usuario> listarUsuariosComAtraso() {
        return biblioteca.listarTodosUsuarios().stream()
                .filter(u -> u.getEmprestimos().stream()
                        .anyMatch(e -> e.getDataDevolucao() != null && e.diasAtraso() > 0))
                .collect(Collectors.toList());
    }

    @Override
    public List<Livro> listarLivrosMaisEmprestados() {
        List<Emprestimo> todosEmprestimos = biblioteca.listarTodosEmprestimos();
        
        // Conta a frequência de cada livro nos empréstimos
        Map<Integer, Integer> frequenciaLivros = new HashMap<>();
        for (Emprestimo emprestimo : todosEmprestimos) {
            int codigoLivro = emprestimo.getLivro().getCodigo();
            frequenciaLivros.put(codigoLivro, frequenciaLivros.getOrDefault(codigoLivro, 0) + 1);
        }
        
        // Ordena os livros por frequência de empréstimo (decrescente)
        return biblioteca.listarTodosLivros().stream()
                .filter(l -> frequenciaLivros.containsKey(l.getCodigo()))
                .sorted((l1, l2) -> frequenciaLivros.get(l2.getCodigo()).compareTo(frequenciaLivros.get(l1.getCodigo())))
                .collect(Collectors.toList());
    }
}
