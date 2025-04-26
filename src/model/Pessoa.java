package model;

public abstract class Pessoa {
    private String nome, telefone, endereço, email;

    public Pessoa(String nome, String telefone, String endereço, String email) {
        this.nome = nome;
        this.telefone = telefone;
        this.endereço = endereço;
        this.email = email;
    }

    //getters, setters e to string
    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public String getTelefone() {
        return telefone;
    }
    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }
    public String getEndereço() {
        return endereço;
    }
    public void setEndereço(String endereço) {
        this.endereço = endereço;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Pessoa [nome=" + nome + ", telefone=" + telefone + ", endereço=" + endereço + ", email=" + email + "]";
    }

    
}
