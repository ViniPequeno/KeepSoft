package nescaupower.br.com.keepsoft.Factory.Model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

import java.util.Calendar;
import java.util.Date;

import io.reactivex.annotations.NonNull;

@Entity(indices = {
        @Index(value = "email", unique = true)
})
public class Usuario {
    @PrimaryKey(autoGenerate = true)
    private long id;
    private String login;
    private String email;
    private String nome;
    private String senha;
    private String telefone;

    public Usuario() {
        login = "";
        email = "";
        nome = "";
        senha = "";
        telefone = "";
    }

    public Usuario(String login, String email, String nome, String senha, String telefone) {
        this.login = login;
        this.email = email;
        this.nome = nome;
        this.senha = senha;
        this.telefone = telefone;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }


}
