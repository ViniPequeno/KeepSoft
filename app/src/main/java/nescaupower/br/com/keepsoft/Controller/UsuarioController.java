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

    public String getMensagem(){
        return this.mensagem;
    }
}
