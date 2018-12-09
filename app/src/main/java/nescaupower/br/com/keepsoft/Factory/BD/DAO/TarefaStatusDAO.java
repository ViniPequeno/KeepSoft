package nescaupower.br.com.keepsoft.Factory.BD.DAO;

import android.util.Log;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.List;
import java.util.concurrent.ExecutionException;

import nescaupower.br.com.keepsoft.Factory.BD.Database.HttpService;
import nescaupower.br.com.keepsoft.Factory.Model.TarefaStatus;

public class TarefaStatusDAO {
    public List<TarefaStatus> getAll() {
        String tJson;
        try {
            tJson = new HttpService().execute("/tarefasStatus", "Get", null).get();
            Type type = new TypeToken<List<TarefaStatus>>() {
            }.getType();
            return new Gson().fromJson(tJson, type);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;

    }

    public TarefaStatus findByID(long id) {
        String tJson;
        try {
            tJson = new HttpService().execute("/tarefasStatus/" + id, "Get", null).get();
            return new Gson().fromJson(tJson, TarefaStatus.class);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    public TarefaStatus findCuurentStatusOfTarefa(long tarefaId) {
        String tJson;
        try {
            tJson = new HttpService().execute("/tarefasStatus/findCuurentStatusOfTarefa/" + tarefaId, "Get", null).get();
            return new Gson().fromJson(tJson, TarefaStatus.class);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<TarefaStatus> findByTarefa(long tarefaId) {
        String tJson;
        try {
            tJson = new HttpService().execute("/tarefasStatus/findByTarefa/" + tarefaId, "Get", null).get();
            Type type = new TypeToken<List<TarefaStatus>>() {
            }.getType();
            return new Gson().fromJson(tJson, type);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<TarefaStatus> findByStatus(long statusId) {
        String tJson;
        try {
            tJson = new HttpService().execute("/tarefasStatus/findByStatus/" + statusId, "Get", null).get();
            Type type = new TypeToken<List<TarefaStatus>>() {
            }.getType();
            return new Gson().fromJson(tJson, type);
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
                new HttpService().execute("/tarefasStatus", "Post", tJson).get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
    }

    public TarefaStatus updateAll(TarefaStatus... tarefasStatuses) {
        for (TarefaStatus tarefaStatus : tarefasStatuses) {
            String tJson = new Gson().toJson(tarefaStatus);
            try {
                tJson = new HttpService().execute("/tarefasStatus/" + tarefaStatus.getId(), "Put", tJson).get();
                Log.e("dataFim3", "oi " + tarefaStatus.getDataFimFormat() + " eai3");
                return new Gson().fromJson(tJson, TarefaStatus.class);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public void delete(TarefaStatus tarefasStatus) {
        String tJson = null;
        try {
            tJson = new HttpService().execute("/tarefasStatus/" + tarefasStatus.getId(), "Delete", null).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}
