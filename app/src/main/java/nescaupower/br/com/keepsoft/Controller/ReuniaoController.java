package nescaupower.br.com.keepsoft.Controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import nescaupower.br.com.keepsoft.Factory.BD.DAO.ReuniaoDAO;
import nescaupower.br.com.keepsoft.Factory.Model.Reuniao;

public class ReuniaoController {

    private String mensagem;
    private ReuniaoDAO reuniaoDAO;

    public ReuniaoController() {
        reuniaoDAO = new ReuniaoDAO();
    }

    public Reuniao atualizar(Reuniao reuniao) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        reuniao.setDataInicioFormat(format.format(reuniao.getDataInicio()));

        return reuniaoDAO.updateAll(reuniao);
    }

    public Reuniao cadastrar(Reuniao reuniao, Long id) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

        reuniao.setDataInicioFormat(format.format(reuniao.getDataInicio()));
        this.mensagem = "Cadastrado!";
        return reuniaoDAO.insertAll(id, reuniao);
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

    public Reuniao getReuniao(Long id) {
        Reuniao reuniao = reuniaoDAO.findByID(id);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            reuniao.setDataInicio(format.parse(reuniao.getDataInicioFormat()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return reuniao;
    }

    public List<Reuniao> listarPorProjeto(Long codProjeto) {
        List<Reuniao> reuniaoList = reuniaoDAO.findByProjectID(codProjeto);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        for (int i = 0; i < reuniaoList.size(); i++) {
            try {
                reuniaoList.get(i).setDataInicio(format.parse(reuniaoList.get(i).getDataInicioFormat()));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return reuniaoList;
    }

    public List<Reuniao> listarTodos() {
        return reuniaoDAO.getAll();
    }

    public String getMensagem() {
        return this.mensagem;
    }
}
