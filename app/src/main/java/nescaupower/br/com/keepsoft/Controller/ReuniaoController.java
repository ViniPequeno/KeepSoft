package nescaupower.br.com.keepsoft.Controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.TimeZone;

import nescaupower.br.com.keepsoft.Factory.BD.DAO.ReuniaoDAO;
import nescaupower.br.com.keepsoft.Factory.BD.DAO.SprintDAO;
import nescaupower.br.com.keepsoft.Factory.Model.Perfil;
import nescaupower.br.com.keepsoft.Factory.Model.Reuniao;
import nescaupower.br.com.keepsoft.Factory.Model.Sprint;
import nescaupower.br.com.keepsoft.Factory.Model.Usuario;

public class ReuniaoController {

    private String mensagem;
    private ReuniaoDAO reuniaoDAO;

    public ReuniaoController() {
        reuniaoDAO = new ReuniaoDAO();
    }

    public void atualizar(Reuniao reuniao) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        format.setTimeZone(TimeZone.getTimeZone("GMT-4:00"));

        reuniao.setDataInicioFormat(format.format(reuniao.getDataInicio()));
        reuniao.setDataFimFormat(format.format(reuniao.getDataFim()));

        reuniaoDAO.updateAll(reuniao);
    }

    public boolean cadastrar(Reuniao reuniao) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        format.setTimeZone(TimeZone.getTimeZone("GMT-4:00"));

        reuniao.setDataInicioFormat(format.format(reuniao.getDataInicio()));
        reuniao.setDataFimFormat(format.format(reuniao.getDataFim()));
        reuniaoDAO.insertAll(reuniao);
        this.mensagem = "Cadastrado!";
        return true;
    }

    public boolean deletar(Reuniao reuniao) {
        //Verifica se já aconteceu
        if (reuniaoDAO.findByID(reuniao.getId()) != null) {
            reuniaoDAO.delete(reuniao);
            this.mensagem = "A reunião foi cancelada!";
            return true;
        } else {
            this.mensagem = "A reunião não existe";
            return false;
        }
    }

    public List<Reuniao> listarPorProjeto(Long codProjeto) {
        return reuniaoDAO.findByProjectID(codProjeto);
    }

    public List<Reuniao> listarTodos() {
        return reuniaoDAO.getAll();
    }

    public String getMensagem() {
        return this.mensagem;
    }
}
