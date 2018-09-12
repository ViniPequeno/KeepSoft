package nescaupower.br.com.keepsoft.Controller;

import android.content.Context;

import java.util.List;

import nescaupower.br.com.keepsoft.Factory.BD.Database.AppDatabase;
import nescaupower.br.com.keepsoft.Factory.Factory;
import nescaupower.br.com.keepsoft.Factory.Model.Perfil;

public class PerfilController {
    private AppDatabase db;
    private String mensagem;

    public PerfilController(Context context) {
        db = Factory.startDatabase(context);
    }

    public void inserir(Perfil... perfis) {
        db.perfilDAO().insertAll(perfis);
    }

    public Perfil procurarPorId(Long codigo) {
        return db.perfilDAO().findById(codigo);
    }

    public Perfil procurarPorProjetoUsuario(long codProjeto, long idUsuario) {
        return db.perfilDAO().findByUserIdAndProjectID(codProjeto, idUsuario);
    }

    public void atualizar(Perfil perfil) {
        db.perfilDAO().updateAll(perfil);
    }

    public boolean cadastrar(Perfil perfil) {
        db.perfilDAO().insertAll(perfil);
        this.mensagem = "Cadastrado!";
        return true;
    }

    public boolean deletar(Perfil perfil) {
        if (db.perfilDAO().findById(perfil.getId()) != null) {
            db.perfilDAO().delete(perfil);
            this.mensagem = "O perfil foi deletado!";
            return true;
        } else {
            this.mensagem = "O perfil não existe";
            return false;
        }
    }

    public List<Perfil> listarPorProjeto(long codProjeto) {
        return db.perfilDAO().findByProjectID(codProjeto);
    }
    public List<Perfil> listarTodos() {
        return db.perfilDAO().getAll();
    }

    public String getMensagem() {
        return this.mensagem;
    }
}
