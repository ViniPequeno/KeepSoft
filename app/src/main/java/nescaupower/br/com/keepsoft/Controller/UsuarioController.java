package nescaupower.br.com.keepsoft.Controller;

import android.content.Context;
import android.database.Cursor;

import java.util.List;

import nescaupower.br.com.keepsoft.Factory.BD.DAO.UsuarioDAO;
import nescaupower.br.com.keepsoft.Factory.Factory;
import nescaupower.br.com.keepsoft.Factory.Model.AlterarSenha;
import nescaupower.br.com.keepsoft.Factory.Model.Login;
import nescaupower.br.com.keepsoft.Factory.Model.Usuario;

public class UsuarioController {
    private UsuarioDAO usuarioDAO;
    private String mensagem;

    public UsuarioController(){
        usuarioDAO = new UsuarioDAO();
    }

    public void inserir(Usuario... usuarios) {
        usuarioDAO.insertAll(usuarios);
    }

    public void atualizar(Usuario... usuarios) {
        usuarioDAO.updateAll(usuarios);
    }

    public Usuario procurarPorLogin(String login) {
        return usuarioDAO.findByLogin(login);
    }

    public Usuario procurarPorID(Long id) {
        return usuarioDAO.findByID(id);
    }

    public boolean cadastrar(Usuario usuario) {
        if(usuarioDAO.findByLogin(usuario.getLogin()) != null){
            this.mensagem = "O nome de login já existe!";
            return false;
        }else if(usuarioDAO.findByEmail(usuario.getEmail()) !=null){
            this.mensagem = "O endereço de email já existe!";
            return false;
        }else{
            usuarioDAO.insertAll(usuario);
            this.mensagem = "Cadastrado!";
            return true;
        }
    }

    public void alterarSenha(AlterarSenha alterarSenha){
        usuarioDAO.alterarSenha(alterarSenha);
    }
    public Usuario realizarLogin(String login, String senha){
        Login loginClass = new Login();
        loginClass.setLogin(login);
        loginClass.setSenha(senha);
        Usuario usuario = usuarioDAO.login(loginClass);
        if(usuario !=null){
            this.mensagem = "Login feito!";
        }else{
            this.mensagem = "Login ou senha errados!";
        }
        return usuario;
    }


    //TODO: consertar nomenclatura
    //public Cursor listarUsuariosCursor(String login, long id) {
      //  return db.usuarioDAO().listUsersCursor(login, id);
    //}

    public List<Usuario> listarUsuariosCursor(String login, Long id){ return usuarioDAO.findByLoginOrName(login, id);}

    public String getMensagem(){
        return this.mensagem;
    }
}
