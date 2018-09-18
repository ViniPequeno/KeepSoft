package nescaupower.br.com.keepsoft.Controller;

import android.content.Context;
import android.database.Cursor;

import java.util.List;

import nescaupower.br.com.keepsoft.Factory.BD.Database.AppDatabase;
import nescaupower.br.com.keepsoft.Factory.Factory;
import nescaupower.br.com.keepsoft.Factory.Model.Usuario;

public class UsuarioController {
    private AppDatabase db;
    private String mensagem;

    public UsuarioController(Context context){
        db = Factory.startDatabase(context);
    }

    public void inserir(Usuario... usuarios) {
        db.usuarioDAO().insertAll(usuarios);
    }

    public void atualizar(Usuario... usuarios) {
        db.usuarioDAO().updateAll(usuarios);
    }

    public Usuario procurarPorLogin(String login) {
        return db.usuarioDAO().findByLogin(login);
    }

    public Usuario procurarPorID(long id) {
        return db.usuarioDAO().findByID(id);
    }

    public boolean cadastrar(Usuario usuario) {
        if(db.usuarioDAO().findByLogin(usuario.getLogin()) != null){
            this.mensagem = "O nome de login já existe!";
            return false;
        }else if(db.usuarioDAO().findByEmail(usuario.getEmail()) !=null){
            this.mensagem = "O endereço de email já existe!";
            return false;
        }else{
            db.usuarioDAO().insertAll(usuario);
            this.mensagem = "Cadastrado!";
            return true;
        }
    }

    public Usuario realizarLogin(String login, String senha){
        Usuario usuario = db.usuarioDAO().login(login, senha);
        if(usuario !=null){
            this.mensagem = "Login feito!";
        }else{
            this.mensagem = "Login ou senha errados!";
        }
        return usuario;
    }

    public List<Usuario> listarPorNomeLogin(String login, String nome){ return db.usuarioDAO().findByLoginAndName(login,nome); }

    //TODO: consertar nomenclatura
    public Cursor listarUsuariosCursor(String nome) {
        return db.usuarioDAO().listUsuariosCursor(nome);
    }

    public String getMensagem(){
        return this.mensagem;
    }
}
