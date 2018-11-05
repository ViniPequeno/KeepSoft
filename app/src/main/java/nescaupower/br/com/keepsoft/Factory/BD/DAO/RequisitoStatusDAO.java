package nescaupower.br.com.keepsoft.Factory.BD.DAO;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.List;
import java.util.concurrent.ExecutionException;

import nescaupower.br.com.keepsoft.Factory.BD.Database.HttpService;
import nescaupower.br.com.keepsoft.Factory.Model.RequisitoStatus;

public class RequisitoStatusDAO {
    public List<RequisitoStatus> getAll(){
        String tJson = null;
        try {
            tJson = new HttpService().execute("/requisitosStatus", "Get", null).get();
            Type type = new TypeToken<List<RequisitoStatus>>(){}.getType();
            List<RequisitoStatus> list = new Gson().fromJson(tJson, type);
            return list;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;

    }

    public RequisitoStatus findByID(long id){
        String tJson = null;
        try {
            tJson = new HttpService().execute("/requisitosStatus/"+id, "Get", null).get();
            RequisitoStatus requisitosStatus =  new Gson().fromJson(tJson, RequisitoStatus.class);
            return requisitosStatus;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }


    void insertAll(RequisitoStatus... requisitosStatuses){
        for(RequisitoStatus requisitosStatus : requisitosStatuses){
            String tJson = new Gson().toJson(requisitosStatus);
            try {
                new HttpService().execute("api/requisitosStatus", "Post", tJson).get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
    }

    void updateAll(RequisitoStatus... requisitosStatuses){
        for(RequisitoStatus requisitosStatus : requisitosStatuses){
            String tJson = new Gson().toJson(requisitosStatus);
            try {
                new HttpService().execute("api/requisitosStatus/"+requisitosStatus.getId(), "Put", tJson).get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
    }

    void delete(RequisitoStatus requisitosStatus){
        String tJson = null;
        try {
            tJson = new HttpService().execute("/requisitosRequisitoStatus/"+requisitosStatus.getId(), "Get", null).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}
