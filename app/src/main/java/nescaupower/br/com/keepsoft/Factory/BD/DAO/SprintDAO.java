package nescaupower.br.com.keepsoft.Factory.BD.DAO;

import android.util.Log;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.List;
import java.util.concurrent.ExecutionException;

import nescaupower.br.com.keepsoft.Factory.BD.Database.HttpService;
import nescaupower.br.com.keepsoft.Factory.Model.Perfil;
import nescaupower.br.com.keepsoft.Factory.Model.Sprint;

public class SprintDAO {

    public List<Sprint> getAll(){
        String tJson = null;
        try {
            tJson = new HttpService().execute("/sprint", "Get", null).get();
            Type type = new TypeToken<List<Sprint>>(){}.getType();
            List<Sprint> list = new Gson().fromJson(tJson, type);
            return list;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;

    }

    public Sprint findById(long id){
        String tJson = null;
        try {
            tJson = new HttpService().execute("/sprint/"+id, "Get", null).get();
            Sprint sprint =  new Gson().fromJson(tJson, Sprint.class);
            return sprint;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Sprint findByName(String titulo){
        String tJson = null;
        try {
            tJson = new HttpService().execute("/sprint/findByTitulo/"+titulo, "Get", null).get();
            Sprint sprint =  new Gson().fromJson(tJson, Sprint.class);
            return sprint;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Sprint> findByProjectID(long codProjeto){
        String tJson = null;
        try {
            tJson = new HttpService().execute("/sprint/findByProjectID/"+codProjeto, "Get", null).get();
            Type type = new TypeToken<List<Sprint>>(){}.getType();
            List<Sprint> list = new Gson().fromJson(tJson, type);
            return list;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    public int getCountInProjeto(long codProjeto){
        String tJson = null;
        try {
            tJson = new HttpService().execute("/sprint/getCountInProjeto/"+codProjeto, "Get", null).get();
            Integer quantidade =  new Gson().fromJson(tJson, Integer.class);
            return quantidade;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public List<String> getNamesByProjeto(Long id) {
        String tJson;
        try {
            tJson = new HttpService().execute("/sprint/findNamesByProjeto/" + id, "Get", null).get();
            Type type = new TypeToken<List<String>>() {}.getType();
            return new Gson().fromJson(tJson, type);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Perfil findPerfilOfSprintUsuario(long id, long codProjeto){

        String tJson = null;
        try {
            tJson = new HttpService().execute("/perfil/findByUserIdAndProjectID/"+codProjeto+"/"+id, "Get", null).get();
            Perfil perfil =  new Gson().fromJson(tJson, Perfil.class);
            return perfil;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void insertAll(Sprint... sprintes){
        for(Sprint sprint : sprintes){
            String tJson = new Gson().toJson(sprint);
            try {
                new HttpService().execute("/sprint", "Post", tJson).get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
    }

    public Sprint updateAll(Sprint... sprintes){
        for(Sprint sprint : sprintes){
            String tJson = new Gson().toJson(sprint);
            try {
                tJson = new HttpService().execute("/sprint/"+sprint.getId(), "Put", tJson).get();
                return new Gson().fromJson(tJson, Sprint.class);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public void delete(Sprint sprint){
        String tJson = null;
        try {
            tJson = new HttpService().execute("/sprint/"+sprint.getId(), "Delete", null).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}
