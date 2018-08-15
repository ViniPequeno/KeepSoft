package nescaupower.br.com.keepsoft.Controller;

import android.content.Context;

import nescaupower.br.com.keepsoft.Factory.BD.Database.AppDatabase;
import nescaupower.br.com.keepsoft.Factory.Factory;
import nescaupower.br.com.keepsoft.Factory.Model.Usuario;

public class UsuarioController {
    private AppDatabase db;
    private String mensagem;

    public UsuarioController(Context context){
        db = Factory.startDatabase(context);
    }

    public void inserirUsuario(Usuario... usuarios){
        db.usuarioDAO().insertAll(usuarios);
    }

    public Usuario procurarPeloLogin(String login){
        return db.usuarioDAO().findByLogin(login);
    }

    public boolean cadastroUsuario(Usuario usuario){
        if(db.usuarioDAO().findByLogin(usuario.getLogin()) != null){
            mensagem = "O nome de login já existe!";
            return false;
        }else if(db.usuarioDAO().findByEmail(usuario.getEmail()) !=null){
            mensagem = "O endereço de email já existe!";
            return false;
        }else{
            db.usuarioDAO().insertAll(usuario);
            mensagem = "Cadastrado!";
            return true;
        }
    }

    public Usuario realizarLogin(String login, String senha){
        return db.usuarioDAO().login(login, senha);
    }

    public String getMensagem(){
        return mensagem;
    }
}
