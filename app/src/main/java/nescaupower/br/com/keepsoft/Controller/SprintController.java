package nescaupower.br.com.keepsoft.Controller;

import android.content.Context;

import java.util.List;

import nescaupower.br.com.keepsoft.Factory.BD.Database.AppDatabase;
import nescaupower.br.com.keepsoft.Factory.Factory;
import nescaupower.br.com.keepsoft.Factory.Model.Perfil;
import nescaupower.br.com.keepsoft.Factory.Model.Sprint;
import nescaupower.br.com.keepsoft.Factory.Model.Usuario;

public class SprintController {
    private AppDatabase db;
    private String mensagem;

    public SprintController(Context context) {
        db = Factory.startDatabase(context);
    }

    public void inserir(Sprint... sprints) {
        db.sprintDAO().insertAll(sprints);
    }

    public Sprint procurarPorNome(String nome) {
        return db.sprintDAO().findByName(nome);
    }

    public Perfil findPerfilSprintUsuario(Sprint sprint, Usuario usuario){ return db.sprintDAO().findPerfilOfSprintUsuario(usuario.getId(), sprint.getCodProjeto());}

    public Sprint procurarPorCodigo(Long codigo) {
        return db.sprintDAO().findById(codigo);
    }

    public void atualizar(Sprint sprint) {
        db.sprintDAO().updateAll(sprint);
    }

    public boolean cadastrar(Sprint sprint) {
        if (db.sprintDAO().findByName(sprint.getTitulo()) != null) {
            this.mensagem = "O nome de sprint já existe!";
            return false;
        } else {
            db.sprintDAO().insertAll(sprint);
            this.mensagem = "Cadastrado!";
            return true;
        }
    }

    public boolean deletar(Sprint sprint) {
        if (db.sprintDAO().findById(sprint.getCodigo()) != null) {
            db.sprintDAO().delete(sprint);
            this.mensagem = "O sprint foi deletado!";
            return true;
        } else {
            this.mensagem = "O sprint não existe";
            return false;
        }
    }

    public List<Sprint> listarPorProjeto(long codProjeto) {
        return db.sprintDAO().findByProjectID(codProjeto);
    }

    public List<Sprint> listarTodos() {
        return db.sprintDAO().getAll();
    }

    public String getMensagem() {
        return this.mensagem;
    }
}
