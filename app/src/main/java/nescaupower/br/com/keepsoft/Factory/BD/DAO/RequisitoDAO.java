package nescaupower.br.com.keepsoft.Factory.BD.DAO;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.List;
import java.util.concurrent.ExecutionException;

import nescaupower.br.com.keepsoft.Factory.BD.Database.HttpService;
import nescaupower.br.com.keepsoft.Factory.Model.Requisito;
import nescaupower.br.com.keepsoft.Factory.Model.Requisito;


public class RequisitoDAO {

    public List<Requisito> getAll(){
        String tJson = null;
        try {
            tJson = new HttpService().execute("/requisito", "Get", null).get();
            Type type = new TypeToken<List<Requisito>>(){}.getType();
            List<Requisito> list = (List<Requisito>) new Gson().fromJson(tJson, type);
            return list;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;

    }

    public Requisito findByID(long id){
        String tJson = null;
        try {
            tJson = new HttpService().execute("/requisito/"+id, "Get", null).get();
            Requisito requisito =  new Gson().fromJson(tJson, Requisito.class);
            return requisito;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }


    void insertAll(Requisito... requisitoes){
        for(Requisito requisito : requisitoes){
            String tJson = new Gson().toJson(requisito);
            try {
                new HttpService().execute("api/requisito", "Post", tJson).get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
    }

    void updateAll(Requisito... requisitoes){
        for(Requisito requisito : requisitoes){
            String tJson = new Gson().toJson(requisito);
            try {
                new HttpService().execute("api/requisito/"+requisito.getCodigo(), "Put", tJson).get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
    }

    void delete(Requisito requisito){
        String tJson = null;
        try {
            tJson = new HttpService().execute("/requisito/"+requisito.getCodigo(), "Get", null).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}
