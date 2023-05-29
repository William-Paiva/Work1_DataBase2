import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Cliente {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    Integer cpf;

    @Column
    String nome;
    @Column
    String endereco;
    @Column
    String genero;
    @Column
    String dt_nasc;

    //

    public Cliente() {

    }

    public Cliente(Integer cpf, String nome, String endereco, String genero, String dt_nasc) {
        this.cpf = cpf;
        this.nome = nome;
        this.endereco = endereco;
        this.genero = genero;
        this.dt_nasc = dt_nasc;
    }

    // get set

    public Integer getCpf() {
        return cpf;
    }

    public void setCpf(Integer cpf) {
        this.cpf = cpf;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getDt_nasc() {
        return dt_nasc;
    }

    public void setDt_nasc(String dt_nasc) {
        this.dt_nasc = dt_nasc;
    }

    //toString

    @Override
    public String toString() {
        return "Cliente [Cpf: " + cpf +
                ", Nome: " + nome +
                ", Endereço: " + endereco +
                ", Genero" + genero +
                ", Dt_Nasc: " + dt_nasc + "]";
    }


}