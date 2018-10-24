package nescaupower.br.com.keepsoft.Factory.Model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

import nescaupower.br.com.keepsoft.Factory.Factory;

public class Usuario {
    private Long id;
    private String login;
    private String email;
    private String nome;
    private String senha;
    private String telefone;
    private boolean receiverNotification;
    private boolean receiverEmail;

    private static Usuario usuarioLogado;

    public static Usuario getUsuarioLogado() {
        if (usuarioLogado == null) {
            usuarioLogado = Factory.startUsuario();
        }
        return usuarioLogado;
    }

    public static void setUsuarioLogado(Usuario u) {
        usuarioLogado = u;
    }


    public Usuario() {
        this.id = new Long(0);
        this.login = "";
        this.email = "";
        this.nome = "";
        this.senha="";
        this.telefone="";
        this.receiverEmail = true;
        this.receiverNotification = true;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public boolean isReceiverNotification() {
        return receiverNotification;
    }

    public void setReceiverNotification(boolean receiverNotification) {
        this.receiverNotification = receiverNotification;
    }

    public boolean isReceiverEmail() {
        return receiverEmail;
    }

    public void setReceiverEmail(boolean receiverEmail) {
        this.receiverEmail = receiverEmail;
    }
}
