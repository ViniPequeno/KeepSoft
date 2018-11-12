package nescaupower.br.com.keepsoft.Controller;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.TimeZone;

import nescaupower.br.com.keepsoft.Factory.BD.DAO.ReuniaoDAO;
import nescaupower.br.com.keepsoft.Factory.BD.DAO.ReuniaoUsuarioDAO;
import nescaupower.br.com.keepsoft.Factory.BD.DAO.SprintDAO;
import nescaupower.br.com.keepsoft.Factory.Model.Perfil;
import nescaupower.br.com.keepsoft.Factory.Model.Reuniao;
import nescaupower.br.com.keepsoft.Factory.Model.ReuniaoUsuario;
import nescaupower.br.com.keepsoft.Factory.Model.Sprint;
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
        List<Usuario> reuniaoList = reuniaoDAO.getAll(id);

        return reuniaoList;
    }

    public String getMensagem() {
        return this.mensagem;
    }
}
