package nescaupower.br.com.keepsoft.Controller;

import java.util.List;

import nescaupower.br.com.keepsoft.Factory.BD.DAO.StatusDAO;
import nescaupower.br.com.keepsoft.Factory.Model.Status;

public class StatusController {

    private StatusDAO statusDAO;

    public StatusController() {
        statusDAO = new StatusDAO();
    }

    public List<Status> getAll() {
        return statusDAO.getAll();
    }

    public List<Status> getAllFindByProjeto(Long projetoId) {
        return statusDAO.findByProjeto(projetoId);
    }

    public List<String> getNamesByProjeto(Long id) {
        return statusDAO.getNamesByProjeto(id);
    }

    public Status findByID(long id) {
        return statusDAO.findByID(id);
    }

    public Status findByNameInProjeto(long projetoId, String statusNome) {
        return statusDAO.findByNameInProjeto(projetoId, statusNome);
    }

    public Status insertAll(Status... statuses) {
        return statusDAO.insertAll(statuses);
    }

    public Status updateAll(Status... statuses) {
        return statusDAO.updateAll(statuses);
    }

    public void delete(Status status) {
        statusDAO.delete(status);
    }
}
