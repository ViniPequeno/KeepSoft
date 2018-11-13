package nescaupower.br.com.keepsoft.Controller;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.TimeZone;

import nescaupower.br.com.keepsoft.Factory.BD.DAO.ConviteDAO;
import nescaupower.br.com.keepsoft.Factory.Model.Convite;

public class ConviteController {
    private String mensagem;
    private ConviteDAO conviteDAO;

    public ConviteController() {
        conviteDAO = new ConviteDAO();
    }

    public boolean cadastrar(Convite... convite) {
        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        formato.setTimeZone(TimeZone.getTimeZone("GMT-4:00"));
        for (Convite convite1 : convite) {
            if (convite1.getDataEnvio() != null) {
                convite1.setDataEnvioFormat(formato.format(convite1.getDataEnvio()));
                Log.e("hey", convite1.getDataEnvioFormat());
                Log.e("hey", convite1.getDataEnvio().toString());
            } else {
                convite1.setDataEnvioFormat("");
            }
        }
        conviteDAO.insertAll(convite);
        this.mensagem = "Cadastrado!";
        return true;
    }

    public void atualizar(Convite... convite) {
        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        formato.setTimeZone(TimeZone.getTimeZone("GMT-4:00"));
        for (Convite convite1 : convite) {
            if (convite1.getDataEnvio() != null) {
                convite1.setDataEnvioFormat(formato.format(convite1.getDataEnvio()));
            } else {
                convite1.setDataEnvioFormat("");
            }
        }
        conviteDAO.updateAll(convite);
        this.mensagem = "Atualizado!";
    }

    public boolean deletar(Convite convite) {
        Log.e("Convite", convite.getId()+"  "+convite.getDestinatarioId());
        if (conviteDAO.findByID(convite.getDestinatarioId(), convite.getProjeto().getCodigo()) != null) {
            conviteDAO.delete(convite);
            this.mensagem = "O convite foi deletado!";
            return true;
        } else {
            this.mensagem = "O convite não existe";
            return false;
        }
    }

    public Convite procurarPorID(long idDestinatario, long codProjeto) {
        return conviteDAO.findByID(idDestinatario, codProjeto);
    }

    public List<Convite> procurarPorIDNotVistos(long idDestinatario) {
        return conviteDAO.findByIDNotVistos(idDestinatario);
    }

    public List<Convite> listarPorProjeto(long codProjeto) {
        return conviteDAO.findByProjectID(codProjeto);
    }

    public List<Convite> listarPorDestinatario(long idDestinatario) {
        List<Convite> conviteList = conviteDAO.findByReceiverID(idDestinatario);
        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        formato.setTimeZone(TimeZone.getTimeZone("GMT-4:00"));
        for (Convite convite1 : conviteList) {
            try {
                convite1.setDataEnvio(formato.parse(convite1.getDataEnvioFormat()));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return conviteList;
    }

    public List<Convite> listarTodos() {
        return conviteDAO.getAll();
    }

    public String getMensagem() {
        return this.mensagem;
    }
}
