package nescaupower.br.com.keepsoft.Controller;

import android.content.Context;

import nescaupower.br.com.keepsoft.Factory.BD.Database.AppDatabase;
import nescaupower.br.com.keepsoft.Factory.Factory;
import nescaupower.br.com.keepsoft.Factory.Model.Projeto;

public class ProjetoController {
    private AppDatabase db;
    private String mensagem;

    public ProjetoController(Context context) {
        db = Factory.startDatabase(context);
    }

    public void inserirProjeto(Projeto... projetos) {
        db.projetoDAO().insertAll(projetos);
    }

    public Projeto procurarPeloNome(String nome) {
        return db.projetoDAO().findByName(nome);
    }

    public boolean cadastroProjeto(Projeto projeto) {
        if (db.projetoDAO().findByName(projeto.getNome()) != null) {
            this.mensagem = "O nome de login já existe!";
            return false;
        } else {
            db.projetoDAO().insertAll(projeto);
            this.mensagem = "Cadastrado!";
            return true;
        }
    }

    public String getMensagem() {
        return this.mensagem;
    }
}
