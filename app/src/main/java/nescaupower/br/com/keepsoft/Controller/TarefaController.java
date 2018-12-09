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

    public List<Tarefa> listarPorSprint(Long sprintId) {
        return tarefaDAO.findBySprintID(sprintId);
    }

    public Tarefa atualizar(Tarefa tarefa) {
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        tarefa.setDataLimiteformat(formato.format(tarefa.getDataLimite()));
        return tarefaDAO.updateAll(tarefa);
    }

    public Tarefa cadastrar(Tarefa... tarefas) {
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        for (Tarefa tarefa : tarefas) {
            if (tarefa.getDataLimite() != null) {
                tarefa.setDataLimiteformat(formato.format(tarefa.getDataLimite()));
            } else {
                tarefa.setDataLimite(null);
                tarefa.setDataLimiteformat("");
            }

        }
        return tarefaDAO.insertAll(tarefas);
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
