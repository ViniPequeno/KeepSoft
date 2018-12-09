package nescaupower.br.com.keepsoft.Factory.BD.DAO;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.List;
import java.util.concurrent.ExecutionException;

import nescaupower.br.com.keepsoft.Factory.BD.Database.HttpService;
import nescaupower.br.com.keepsoft.Factory.Model.Tarefa;

public class TarefaDAO {

    public List<Tarefa> getAll() {
        String tJson;
        try {
            tJson = new HttpService().execute("/tarefa", "Get", null).get();
            Type type = new TypeToken<List<Tarefa>>() {
            }.getType();
            return new Gson().fromJson(tJson, type);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Tarefa findById(Long id) {
        String tJson;
        try {
            tJson = new HttpService().execute("/tarefa/" + id, "Get", null).get();
            return new Gson().fromJson(tJson, Tarefa.class);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }


    public Tarefa insertAll(Tarefa... tarefas) {
        for (Tarefa tarefa : tarefas) {
            String tJson = new Gson().toJson(tarefa);
            try {
                String resposta = new HttpService().execute("/tarefa", "Post", tJson).get();
                return new Gson().fromJson(resposta, Tarefa.class);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
        return  null;
    }

    public Tarefa updateAll(Tarefa... tarefas) {
        for (Tarefa tarefa : tarefas) {
            String tJson = new Gson().toJson(tarefa);
            try {
                tJson = new HttpService().execute("/tarefa/" + tarefa.getId(), "Put", tJson).get();
                return new Gson().fromJson(tJson, Tarefa.class);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public void delete(Tarefa tarefa) {
        String tJson = null;
        try {
            tJson = new HttpService().execute("/tarefa/" + tarefa.getId(), "Delete", null).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    public List<Tarefa> findByProjectID(Long codProjeto) {
        String tJson;
        try {
            tJson = new HttpService().execute("/tarefa/findByProjeto/" + codProjeto, "Get", null).get();
            Type type = new TypeToken<List<Tarefa>>() {
            }.getType();
            return new Gson().fromJson(tJson, type);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Tarefa> findBySprintID(Long sprintId) {
        String tJson;
        try {
            tJson = new HttpService().execute("/tarefa/findBySprint/" + sprintId, "Get", null).get();
            Type type = new TypeToken<List<Tarefa>>() {
            }.getType();
            return new Gson().fromJson(tJson, type);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Tarefa findByUserIdAndProjectID(Long codProjeto, Long idUsuario) {
        String tJson;
        try {
            tJson = new HttpService().execute("/tarefa/findByUserIdAndProjectID/" + codProjeto + "/" + idUsuario, "Get", null).get();
            return new Gson().fromJson(tJson, Tarefa.class);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }
}
