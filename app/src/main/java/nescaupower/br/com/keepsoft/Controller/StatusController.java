package nescaupower.br.com.keepsoft.Controller;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.List;
import java.util.concurrent.ExecutionException;

import nescaupower.br.com.keepsoft.Factory.BD.DAO.StatusDAO;
import nescaupower.br.com.keepsoft.Factory.BD.Database.HttpService;
import nescaupower.br.com.keepsoft.Factory.Model.Status;

public class StatusController {

    private StatusDAO statusDAO;

    public StatusController() {
        statusDAO = new StatusDAO();
    }

    public List<Status> getAllFindByProjeto(Long projetoId) {
        return statusDAO.getAllFindByProjeto(projetoId);
    }

    public List<Status> getAll() {
        return statusDAO.getAll();
    }

    public Status findByID(long id) {
        return statusDAO.findByID(id);
    }


    public List<String> listarTodosOsNomesdeProjeto(Long id) {
        return statusDAO.listarTodosOsNomesdeProjeto(id);
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
