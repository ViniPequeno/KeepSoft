package nescaupower.br.com.keepsoft.Factory.BD.DAO;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.List;
import java.util.concurrent.ExecutionException;

import nescaupower.br.com.keepsoft.Factory.BD.Database.HttpService;
import nescaupower.br.com.keepsoft.Factory.Model.Reuniao;
import nescaupower.br.com.keepsoft.Factory.Model.ReuniaoUsuario;
import nescaupower.br.com.keepsoft.Factory.Model.Usuario;

public class ReuniaoUsuarioDAO {
    public List<Usuario> getAll(Long id){
        String tJson = null;
        try {
            tJson = new HttpService().execute("/reuniao/usuario/{id}", "Get", null).get();
            Type type = new TypeToken<List<Reuniao>>(){}.getType();
            List<Usuario> list = new Gson().fromJson(tJson, type);
            return list;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }



    public void insertAll(ReuniaoUsuario ... reunaioes){
        for(ReuniaoUsuario reuniao : reunaioes){
            String tJson = new Gson().toJson(reuniao);
            try {
                new HttpService().execute("/reuniao/usuario", "Post", tJson).get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
    }

}
