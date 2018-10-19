package nescaupower.br.com.keepsoft.Controller;

import android.content.Context;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.TimeZone;

import nescaupower.br.com.keepsoft.Factory.BD.DAO.ConviteDAO;
import nescaupower.br.com.keepsoft.Factory.Factory;
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
            if (convite1.getData() != null) {
                Log.e("format", convite1.getData().toString());
                convite1.setDataFormat(formato.format(convite1.getData()));
            } else {
                convite1.setDataFormat("");
            }
        }
        conviteDAO.insertAll(convite);
        this.mensagem = "Cadastrado!";
        return true;
    }

    public boolean deletar(Convite convite) {
        Log.e("Convite", convite.getId()+"  "+convite.getDestinatarioId());
        if (conviteDAO.findByID(convite.getDestinatarioId(), convite.getCodProjeto()) != null) {
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

    public List<Convite> listarPorProjeto(long codProjeto) {
        return conviteDAO.findByProjectID(codProjeto);
    }

    public List<Convite> listarPorDestinatario(long idDestinatario) {
        List<Convite> conviteList = conviteDAO.findByReceiverID(idDestinatario);
        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        formato.setTimeZone(TimeZone.getTimeZone("GMT-4:00"));
        for (Convite convite1 : conviteList) {
            try {
                convite1.setData(formato.parse(convite1.getDataFormat()));
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
