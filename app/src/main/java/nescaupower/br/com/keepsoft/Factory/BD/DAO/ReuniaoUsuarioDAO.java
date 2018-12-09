package nescaupower.br.com.keepsoft.Factory.BD.DAO;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.List;
import java.util.concurrent.ExecutionException;

import nescaupower.br.com.keepsoft.Factory.BD.Database.HttpService;
import nescaupower.br.com.keepsoft.Factory.Model.ReuniaoUsuario;
import nescaupower.br.com.keepsoft.Factory.Model.Usuario;

public class ReuniaoUsuarioDAO {
    public List<Usuario> getAll(Long id) {
        String tJson;
        try {
            tJson = new HttpService().execute("/reuniao/usuario/" + id, "Get", null).get();
            Type type = new TypeToken<List<Usuario>>() {
            }.getType();
            return new Gson().fromJson(tJson, type);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }


    public void delete(ReuniaoUsuario reuniaoUsuario) {
        try {
            new HttpService().execute("/reuniao/usuario/"+reuniaoUsuario.getId(), "Delete", "").get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }


    public void insertAll(ReuniaoUsuario... reunaioes) {
        for (ReuniaoUsuario reuniao : reunaioes) {
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

    public List<Usuario> getUsuariosNotReuniao(Long reuniaoId, String login) {
        String tJson;
        try {
            tJson = new HttpService().execute("/reuniao/getUsuariosNotReuniao/" + reuniaoId + "/" + login, "Get", null).get();
            Type type = new TypeToken<List<Usuario>>() {
            }.getType();
            return new Gson().fromJson(tJson, type);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }


    public ReuniaoUsuario getReuniaoUsuario(Long reuniaoId, Long id) {
        String tJson;
        try {
            tJson = new HttpService().execute("/reuniao/getReuniaoUsuario/" + reuniaoId+"/"+id, "Get", null).get();
            return new Gson().fromJson(tJson, ReuniaoUsuario.class);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }
}
