package nescaupower.br.com.keepsoft.Controller;

import java.text.SimpleDateFormat;
import java.util.List;

import nescaupower.br.com.keepsoft.Factory.BD.DAO.TarefaStatusDAO;
import nescaupower.br.com.keepsoft.Factory.Model.TarefaStatus;

public class TarefaStatusController {

    private TarefaStatusDAO tarefaStatusDAO;

    public TarefaStatusController() {
        tarefaStatusDAO = new TarefaStatusDAO();
    }

    public List<TarefaStatus> getAll() {
        return tarefaStatusDAO.getAll();
    }

    public TarefaStatus findByID(long id) {
        return tarefaStatusDAO.findByID(id);
    }

    //public TarefaStatus findByStatus(long statusId) { return tarefaStatusDAO.findByStatus(statusId); }
    //public TarefaStatus findByTarefa(long tarefaId) { return tarefaStatusDAO.findByTarefa(tarefaId); }

    public void insertAll(TarefaStatus... statuses) {
        tarefaStatusDAO.insertAll(statuses);
    }

    public boolean cadastrar(TarefaStatus... tarefaStatuses) {
        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
        for (TarefaStatus tarefaStatus : tarefaStatuses) {
            if (tarefaStatus.getDataInicio() != null) {
                tarefaStatus.setDataInicioFormat(formato.format(tarefaStatus.getDataInicio()));
            } else {
                tarefaStatus.setDataInicio(null);
                tarefaStatus.setDataInicioFormat("");
            }

        }
        tarefaStatusDAO.insertAll(tarefaStatuses);
        return true;
    }

    //public TarefaStatus updateAll(TarefaStatus... statuses) { return tarefaStatusDAO.updateAll(statuses); }

    //public void delete(Status status) {tarefaStatusDAO.delete(status);}
}
