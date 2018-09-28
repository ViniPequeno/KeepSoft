package nescaupower.br.com.keepsoft.Controller;

import android.content.Context;

import java.util.List;

import nescaupower.br.com.keepsoft.Factory.BD.Database.AppDatabase;
import nescaupower.br.com.keepsoft.Factory.Factory;
import nescaupower.br.com.keepsoft.Factory.Model.Convite;

public class ConviteController {
    private AppDatabase db;
    private String mensagem;

    public ConviteController(Context context) {
        db = Factory.startDatabase(context);
    }

    public void inserir(Convite... perfis) {
        db.conviteDAO().insertAll(perfis);
    }

    public void atualizar(Convite convite) {
        db.conviteDAO().updateAll(convite);
    }

    public boolean cadastrar(Convite convite) {
        db.conviteDAO().insertAll(convite);
        this.mensagem = "Cadastrado!";
        return true;
    }

    public boolean deletar(Convite convite) {
        if (db.conviteDAO().findById(convite.getRemetenteId(), convite.getDestinatarioId(), convite.getCodProjeto()) != null) {
            db.conviteDAO().delete(convite);
            this.mensagem = "O convite foi deletado!";
            return true;
        } else {
            this.mensagem = "O convite não existe";
            return false;
        }
    }

    public List<Convite> listarPorProjeto(long codProjeto) {
        return db.conviteDAO().findByProjectID(codProjeto);
    }

    public List<Convite> listarTodos() {
        return db.conviteDAO().getAll();
    }

    public String getMensagem() {
        return this.mensagem;
    }
}
