package nescaupower.br.com.keepsoft.Controller;

import android.content.Context;

import java.util.List;

import nescaupower.br.com.keepsoft.Factory.BD.Database.AppDatabase;
import nescaupower.br.com.keepsoft.Factory.Factory;
import nescaupower.br.com.keepsoft.Factory.Model.Projeto;

public class ProjetoController {
    private AppDatabase db;
    private String mensagem;

    public ProjetoController(Context context) {
        db = Factory.startDatabase(context);
    }

    public void inserir(Projeto... projetos) {
        db.projetoDAO().insertAll(projetos);
    }

    public Projeto procurarPorNome(String nome) {
        return db.projetoDAO().findByName(nome);
    }

    public Projeto procurarPorCodigo(Long codigo) {
        return db.projetoDAO().findById(codigo);
    }

    public void atualizar(Projeto projeto) {
        db.projetoDAO().updateAll(projeto);
    }

    public boolean cadastrar(Projeto projeto) {
        if (db.projetoDAO().findByName(projeto.getNome()) != null) {
            this.mensagem = "O nome de projeto já existe!";
            return false;
        } else {
            db.projetoDAO().insertAll(projeto);
            this.mensagem = "Cadastrado!";
            return true;
        }
    }

    public boolean deletar(Projeto projeto) {
        if (db.projetoDAO().findById(projeto.getCodigo()) != null) {
            db.projetoDAO().delete(projeto);
            this.mensagem = "O projeto foi deletado!";
            return true;
        } else {
            this.mensagem = "O projeto não existe";
            return false;
        }
    }

    public List<Projeto> listarPorUsuario(long idUsuario) {
        return db.projetoDAO().findByUserID(idUsuario);
    }

    public List<Projeto> listarTodos() {
        return db.projetoDAO().getAll();
    }

    public String getMensagem() {
        return this.mensagem;
    }
}
