package nescaupower.br.com.keepsoft.Factory.BD.DAO;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.List;
import java.util.concurrent.ExecutionException;

import nescaupower.br.com.keepsoft.Factory.BD.Database.HttpService;
import nescaupower.br.com.keepsoft.Factory.Model.Reuniao;

public class ReuniaoDAO {
    public List<Reuniao> getAll(){
        String tJson;
        try {
            tJson = new HttpService().execute("/reuniao", "Get", null).get();
            Type type = new TypeToken<List<Reuniao>>(){}.getType();
            return new Gson().fromJson(tJson, type);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Reuniao> findByProjectID(Long id){
        String tJson;
        try {
            tJson = new HttpService().execute("/reuniao/findByProjectID/"+id, "Get", null).get();
            Type type = new TypeToken<List<Reuniao>>(){}.getType();
            return new Gson().fromJson(tJson, type);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;

    }

    public Reuniao findByID(Long id){
        String tJson;
        try {
            tJson = new HttpService().execute("/reuniao/"+id, "Get", null).get();
            return new Gson().fromJson(tJson, Reuniao.class);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }


    public Reuniao insertAll(Long id, Reuniao... reunaioes){
        for(Reuniao reuniao : reunaioes){
            String tJson = new Gson().toJson(reuniao);
            try {
                tJson = new HttpService().execute("/reuniao/"+id, "Post", tJson).get();
                return new Gson().fromJson(tJson, Reuniao.class);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public Reuniao updateAll(Reuniao... reunaioes){
        for(Reuniao reuniao : reunaioes){
            String tJson = new Gson().toJson(reuniao);
            try {
                tJson = new HttpService().execute("/reuniao/"+reuniao.getId(), "Put", tJson).get();
                return new Gson().fromJson(tJson, Reuniao.class);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public void delete(Reuniao reuniao){
        String tJson = null;
        try {
            tJson = new HttpService().execute("/reuniao/"+reuniao.getId(), "Delete", null).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}
