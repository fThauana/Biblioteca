package model;
import java.util.ArrayList;
import java.util.List;

public final class Usuario extends Pessoa {
    private final int id;
    private List<Emprestimo> emprestimos = new ArrayList<>();
    
    public Usuario(String nome, String telefone, String endereço, String email, int id) {
        super(nome, telefone, endereço, email);
        this.id = id;
    }

    //getters e to string
    public int getId() {
        return id;
    }
    public List<Emprestimo> getEmprestimos() {
        return emprestimos;
    }

    @Override
    public String toString() {
        return "Usuario ["+ super.toString() + "id=" + id + ", emprestimos=" + emprestimos + "]\n";
    }

    
}
