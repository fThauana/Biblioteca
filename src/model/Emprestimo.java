package model;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Emprestimo {
    private Livro livro;
    private Usuario usuario;
    private LocalDate dataEmprestimo, dataPrevista, dataDevolucao;

    public Emprestimo(Livro livro, Usuario usuario, LocalDate dataEmprestimo, LocalDate dataPrevista,
            LocalDate dataDevolucao) {
        this.livro = livro;
        this.usuario = usuario;
        this.dataEmprestimo = dataEmprestimo;
        this.dataPrevista = dataPrevista;
        this.dataDevolucao = dataDevolucao;
    }

    public int diasAtraso(){
        if(dataDevolucao == null) return 0;
        return (int) ChronoUnit.DAYS.between(dataPrevista, dataDevolucao);
    }

    public Livro getLivro() {
        return livro;
    }
    public void setLivro(Livro livro) {
        this.livro = livro;
    }
    public Usuario getUsuario() {
        return usuario;
    }
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
    public LocalDate getDataEmprestimo() {
        return dataEmprestimo;
    }
    public void setDataEmprestimo(LocalDate dataEmprestimo) {
        this.dataEmprestimo = dataEmprestimo;
    }
    public LocalDate getDataPrevista() {
        return dataPrevista;
    }
    public void setDataPrevista(LocalDate dataPrevista) {
        this.dataPrevista = dataPrevista;
    }
    public LocalDate getDataDevolucao() {
        return dataDevolucao;
    }
    public void setDataDevolucao(LocalDate dataDevolucao) {
        this.dataDevolucao = dataDevolucao;
    }

    @Override
    public String toString() {
        return "Emprestimo [livro=" + livro + ", usuario=" + usuario + ", dataEmprestimo=" + dataEmprestimo + ", dataPrevista=" + dataPrevista + ", dataDevolucao=" + dataDevolucao + "]\n";
    }

}
