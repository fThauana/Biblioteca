package model;
import java.util.ArrayList;
import java.util.List;

public final class Usuario extends Pessoa {
    private int id;
    private List<Emprestimo> emprestimos = new ArrayList<>();
    
    public Usuario(String nome, String telefone, String endereco, String email, int id) {
        super(nome, telefone, endereco, email);
        this.id = id;
    }


    public int getId() {
        return id;
    }
    public List<Emprestimo> getEmprestimos() {
        return emprestimos;
    }

    @Override
    public String toString() {
        return "Usuario [nome=" + nome + ", telefone=" + telefone + ", endereco=" + endereco + ", email=" + email
                + ", id=" + id + ", emprestimos=" + emprestimos + "]";
    }
    
}
