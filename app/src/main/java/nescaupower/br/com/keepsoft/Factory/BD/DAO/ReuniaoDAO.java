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
        String tJson = null;
        try {
            tJson = new HttpService().execute("/reuniao", "Get", null).get();
            Type type = new TypeToken<List<Reuniao>>(){}.getType();
            List<Reuniao> list = new Gson().fromJson(tJson, type);
            return list;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Reuniao> findByProjectID(Long id){
        String tJson = null;
        try {
            tJson = new HttpService().execute("/reuniao/findByProjectID/"+id, "Get", null).get();
            Type type = new TypeToken<List<Reuniao>>(){}.getType();
            List<Reuniao> list = new Gson().fromJson(tJson, type);
            return list;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;

    }

    public Reuniao findByID(Long id){
        String tJson = null;
        try {
            tJson = new HttpService().execute("/reuniao/"+id, "Get", null).get();
            Reuniao reuniao =  new Gson().fromJson(tJson, Reuniao.class);
            return reuniao;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }


    public void insertAll(Reuniao... reunaioes){
        for(Reuniao reuniao : reunaioes){
            String tJson = new Gson().toJson(reuniao);
            try {
                new HttpService().execute("/reuniao", "Post", tJson).get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
    }

    public void updateAll(Reuniao... reunaioes){
        for(Reuniao reuniao : reunaioes){
            String tJson = new Gson().toJson(reuniao);
            try {
                new HttpService().execute("/reuniao/"+reuniao.getId(), "Put", tJson).get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
    }

    public void delete(Reuniao reuniao){
        String tJson = null;
        try {
            tJson = new HttpService().execute("/reuniao/"+reuniao.getId(), "Get", null).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}
