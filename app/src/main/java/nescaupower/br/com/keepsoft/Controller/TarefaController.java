package nescaupower.br.com.keepsoft.Controller;

import java.text.SimpleDateFormat;
import java.util.List;

import nescaupower.br.com.keepsoft.Factory.BD.DAO.TarefaDAO;
import nescaupower.br.com.keepsoft.Factory.Model.Tarefa;

public class TarefaController {
    private String mensagem;
    private TarefaDAO tarefaDAO;

    public TarefaController() {
        tarefaDAO = new TarefaDAO();
    }

    public void inserir(Tarefa... perfis) {
        tarefaDAO.insertAll(perfis);
    }

    public Tarefa procurarPorId(Long codigo) {
        return tarefaDAO.findById(codigo);
    }

    public Tarefa procurarPorProjetoUsuario(Long codProjeto, Long idUsuario) {
        return tarefaDAO.findByUserIdAndProjectID(codProjeto, idUsuario);
    }

    public List<Tarefa> listarPorProjeto(Long codProjeto) {
        return tarefaDAO.findByProjectID(codProjeto);
    }

    public void atualizar(Tarefa tarefa) {
        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
        tarefa.setDataLimiteformat(formato.format(tarefa.getDataLimite()));
        tarefaDAO.updateAll(tarefa);
    }

    public boolean cadastrar(Tarefa... tarefas) {
        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
        for (Tarefa tarefa : tarefas) {
            if (tarefa.getDataLimite() != null) {
                tarefa.setDataLimiteformat(formato.format(tarefa.getDataLimite()));
            } else {
                tarefa.setDataLimite(null);
                tarefa.setDataLimiteformat("");
            }

        }
        Tarefa tarefa = tarefaDAO.insertAll(tarefas);
        if(tarefa != null) {
            this.mensagem = "Cadastrado!";
            return true;
        }else{
            this.mensagem = "Tarefa já existe neste projeto!";
            return false;
        }
    }

    public boolean deletar(Tarefa tarefa) {
        if (tarefaDAO.findById(tarefa.getId()) != null) {
            tarefaDAO.delete(tarefa);
            this.mensagem = "A tarefa foi deletado!";
            return true;
        } else {
            this.mensagem = "A tarefa não existe";
            return false;
        }
    }

    public String getMensagem() {
        return this.mensagem;
    }
}
