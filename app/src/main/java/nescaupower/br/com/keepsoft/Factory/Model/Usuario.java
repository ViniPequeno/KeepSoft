package nescaupower.br.com.keepsoft.Factory.Model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

import java.util.Calendar;
import java.util.Date;

@Entity(indices = {
        @Index(value = "emailIndex", unique = true)
})
public class Usuario {
    @PrimaryKey
    private String login;
    private String email;
    private String nome;
    private String senha;
    private String telefone;

    public Usuario() {
        login="";
        email="";
        nome="";
        senha="";
        telefone="";
    }

    public Usuario(String login, String email, String nome, String senha, String telefone) {
        this.login = login;
        this.email = email;
        this.nome = nome;
        this.senha = senha;
        this.telefone = telefone;
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

    public static class Perfil {
        long id;
        Date dataInicio;
        Date dataFim;

        public Perfil() {
            id=0;
            dataInicio = Calendar.getInstance().getTime();
        }

        public Perfil(long id, Date dataInicio, Date dataFim) {
            this.id = id;
            this.dataInicio = dataInicio;
            this.dataFim = dataFim;
        }

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public Date getDataInicio() {

            return dataInicio;
        }

        public void setDataInicio(Date dataInicio) {
            this.dataInicio = dataInicio;
        }

        public Date getDataFim() {
            return dataFim;
        }

        public void setDataFim(Date dataFim) {
            this.dataFim = dataFim;
        }
    }
}
