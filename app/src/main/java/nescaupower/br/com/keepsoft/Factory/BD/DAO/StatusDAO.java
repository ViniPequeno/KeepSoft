package nescaupower.br.com.keepsoft.Factory.BD.DAO;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.List;
import java.util.concurrent.ExecutionException;

import nescaupower.br.com.keepsoft.Factory.BD.Database.HttpService;
import nescaupower.br.com.keepsoft.Factory.Model.Status;

public class StatusDAO {


    public List<Status> getAll() {
        String tJson = null;
        try {
            tJson = new HttpService().execute("/status", "Get", null).get();
            Type type = new TypeToken<List<Status>>(){}.getType();
            List<Status> list = new Gson().fromJson(tJson, type);
            return list;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Status> findByProjeto(Long projetoId) {
        String tJson = null;
        try {
            tJson = new HttpService().execute("/status/findByProjeto/" + projetoId, "Get", null).get();
            Type type = new TypeToken<List<Status>>(){}.getType();
            List<Status> list = new Gson().fromJson(tJson, type);
            return list;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<String> getNamesByProjeto(Long id) {
        String tJson;
        try {
            tJson = new HttpService().execute("/status/findNamesByProjeto/" + id, "Get", null).get();
            Type type = new TypeToken<List<String>>() {}.getType();
            return new Gson().fromJson(tJson, type);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Status findByID(long id){
        String tJson = null;
        try {
            tJson = new HttpService().execute("/status/"+id, "Get", null).get();
            Status status =  new Gson().fromJson(tJson, Status.class);
            return status;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Status findByNameInProjeto(long projetoId, String nomeStatus) {
        String tJson = null;
        try {
            tJson = new HttpService().execute("/status/findByNameInProjeto/" + projetoId + "/" + nomeStatus, "Get", null).get();
            Status status = new Gson().fromJson(tJson, Status.class);
            return status;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Status insertAll(Status... statuses){
        for(Status status : statuses){
            String tJson = new Gson().toJson(status);
            try {
                tJson = new HttpService().execute("/status", "Post", tJson).get();
                return new Gson().fromJson(tJson, Status.class);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public Status updateAll(Status... statuses){
        for(Status status : statuses){
            String tJson = new Gson().toJson(status);
            try {
                tJson = new HttpService().execute("/status/"+status.getId(), "Put", tJson).get();
                return new Gson().fromJson(tJson, Status.class);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public void delete(Status status){
        String tJson = null;
        try {
            tJson = new HttpService().execute("/status/"+status.getId(), "Delete", null).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}
