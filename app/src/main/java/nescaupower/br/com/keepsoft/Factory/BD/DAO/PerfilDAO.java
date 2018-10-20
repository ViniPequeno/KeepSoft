package nescaupower.br.com.keepsoft.Factory.BD.DAO;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.List;
import java.util.concurrent.ExecutionException;

import nescaupower.br.com.keepsoft.Factory.BD.Database.HttpService;
import nescaupower.br.com.keepsoft.Factory.Model.Perfil;

public class PerfilDAO {

    public List<Perfil> getAll(){
        String tJson;
        try {
            tJson = new HttpService().execute("/perfil", "Get", null).get();
            Type type = new TypeToken<List<Perfil>>(){}.getType();
            List<Perfil> list = new Gson().fromJson(tJson, type);
            return list;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;

    }

    public Perfil findById(Long id){
        String tJson;
        try {
            tJson = new HttpService().execute("/perfil/"+id, "Get", null).get();
            Perfil perfil =  new Gson().fromJson(tJson, Perfil.class);
            return perfil;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }


    public void insertAll(Perfil... perfis) {
        for (Perfil perfil : perfis) {
            String tJson = new Gson().toJson(perfil);
            try {
                new HttpService().execute("/perfil", "Post", tJson).get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
    }

    public void updateAll(Perfil... perfis) {
        for (Perfil perfil : perfis) {
            String tJson = new Gson().toJson(perfil);
            try {
                new HttpService().execute("/perfil/"+perfil.getId(), "Put", tJson).get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
    }

    public void delete(Perfil perfil){
        String tJson = null;
        try {
            tJson = new HttpService().execute("/perfil/"+perfil.getId(), "Delete", null).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }




    public List<Perfil> findByProjectID(Long codProjeto){
        String tJson;
        try {
            tJson = new HttpService().execute("/perfil/findByProjeto/"+codProjeto, "Get", null).get();
            Type type = new TypeToken<List<Perfil>>(){}.getType();
            List<Perfil> list = new Gson().fromJson(tJson, type);
            return list;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;

    }


    public Perfil findByUserIdAndProjectID(Long codProjeto, Long idUsuario){
        String tJson;
        try {
            tJson = new HttpService().execute("/perfil/findByUserIdAndProjectID/"+codProjeto+"/"+idUsuario, "Get", null).get();
            Perfil perfil =  new Gson().fromJson(tJson, Perfil.class);
            return perfil;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }


}
