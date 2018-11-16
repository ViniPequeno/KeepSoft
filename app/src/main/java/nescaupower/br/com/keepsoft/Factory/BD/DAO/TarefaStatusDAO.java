package nescaupower.br.com.keepsoft.Factory.BD.DAO;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.List;
import java.util.concurrent.ExecutionException;

import nescaupower.br.com.keepsoft.Factory.BD.Database.HttpService;
import nescaupower.br.com.keepsoft.Factory.Model.TarefaStatus;

public class TarefaStatusDAO {
    public List<TarefaStatus> getAll() {
        String tJson = null;
        try {
            tJson = new HttpService().execute("/tarefasStatus", "Get", null).get();
            Type type = new TypeToken<List<TarefaStatus>>() {
            }.getType();
            List<TarefaStatus> list = new Gson().fromJson(tJson, type);
            return list;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;

    }

    public TarefaStatus findByID(long id) {
        String tJson = null;
        try {
            tJson = new HttpService().execute("/tarefasStatus/" + id, "Get", null).get();
            TarefaStatus tarefasStatus = new Gson().fromJson(tJson, TarefaStatus.class);
            return tarefasStatus;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<TarefaStatus> findByTarefa(long tarefaId) {
        String tJson = null;
        try {
            tJson = new HttpService().execute("/tarefasStatus/findByTarefa/" + tarefaId, "Get", null).get();
            Type type = new TypeToken<List<TarefaStatus>>() {
            }.getType();
            List<TarefaStatus> list = new Gson().fromJson(tJson, type);
            return list;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<TarefaStatus> findByStatus(long statusId) {
        String tJson = null;
        try {
            tJson = new HttpService().execute("/tarefasStatus/findByStatus/" + statusId, "Get", null).get();
            Type type = new TypeToken<List<TarefaStatus>>() {
            }.getType();
            List<TarefaStatus> list = new Gson().fromJson(tJson, type);
            return list;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void insertAll(TarefaStatus... tarefasStatuses) {
        for (TarefaStatus tarefasStatus : tarefasStatuses) {
            String tJson = new Gson().toJson(tarefasStatus);
            try {
                new HttpService().execute("api/tarefasStatus", "Post", tJson).get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
    }

    public void updateAll(TarefaStatus... tarefasStatuses) {
        for (TarefaStatus tarefasStatus : tarefasStatuses) {
            String tJson = new Gson().toJson(tarefasStatus);
            try {
                new HttpService().execute("api/tarefasStatus/" + tarefasStatus.getId(), "Put", tJson).get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
    }

    public void delete(TarefaStatus tarefasStatus) {
        String tJson = null;
        try {
            tJson = new HttpService().execute("/tarefasStatus/" + tarefasStatus.getId(), "Get", null).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}
