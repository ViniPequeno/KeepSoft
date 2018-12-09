package nescaupower.br.com.keepsoft.Controller;

import java.util.List;

import nescaupower.br.com.keepsoft.Factory.BD.DAO.ReuniaoUsuarioDAO;
import nescaupower.br.com.keepsoft.Factory.Model.ReuniaoUsuario;
import nescaupower.br.com.keepsoft.Factory.Model.Usuario;

public class ReuniaoUsuarioController {

    private String mensagem;
    private ReuniaoUsuarioDAO reuniaoDAO;

    public ReuniaoUsuarioController() {
        reuniaoDAO = new ReuniaoUsuarioDAO();
    }

    public boolean cadastrar(ReuniaoUsuario... reuniao) {
        reuniaoDAO.insertAll(reuniao);
        this.mensagem = "Cadastrado!";
        return true;
    }

    public ReuniaoUsuario getReuniaoUsuario(Long reuniaoId, Long id){
        return reuniaoDAO.getReuniaoUsuario(reuniaoId, id);
    }

    public List<Usuario> getUsuariosNotReuniao(Long reuniaoId, String login){
        return reuniaoDAO.getUsuariosNotReuniao(reuniaoId, login);
    }

    public void deleteReuniaoUsuario(ReuniaoUsuario reuniaoUsuario){
        reuniaoDAO.delete(reuniaoUsuario);
    }


    public List<Usuario> listarPorReuniao(Long id) {

        return reuniaoDAO.getAll(id);
    }

    public String getMensagem() {
        return this.mensagem;
    }
}
