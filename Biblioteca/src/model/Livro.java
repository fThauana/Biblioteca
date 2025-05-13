package model;

public class Livro {
    private int codigo;
    private String titulo, autor, categoria;
    private int anoPublicacao, exemplaresDisponiveis;

    public Livro(int codigo, String titulo, String autor, String categoria, int anoPublicacao,
            int exemplaresDisponiveis) {
        this.codigo = codigo;
        this.titulo = titulo;
        this.autor = autor;
        this.categoria = categoria;
        this.anoPublicacao = anoPublicacao;
        this.exemplaresDisponiveis = exemplaresDisponiveis;
    }


    public int getCodigo() {
        return codigo;
    }
    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }
    public String getTitulo() {
        return titulo;
    }
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
    public String getAutor() {
        return autor;
    }
    public void setAutor(String autor) {
        this.autor = autor;
    }
    public String getCategoria() {
        return categoria;
    }
    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }
    public int getAnoPublicacao() {
        return anoPublicacao;
    }
    public void setAnoPublicacao(int anoPublicacao) {
        this.anoPublicacao = anoPublicacao;
    }
    public int getExemplaresDisponiveis() {
        return exemplaresDisponiveis;
    }
    public void setExemplaresDisponiveis(int exemplaresDisponiveis) {
        this.exemplaresDisponiveis = exemplaresDisponiveis;
    }

    @Override
    public String toString() {
        return "Livro [codigo=" + codigo + ", titulo=" + titulo + ", autor=" + autor + ", categoria=" + categoria + ", anoPublicacao=" + anoPublicacao + ", exemplaresDisponiveis=" + exemplaresDisponiveis + "]\n";
    }

    


}

