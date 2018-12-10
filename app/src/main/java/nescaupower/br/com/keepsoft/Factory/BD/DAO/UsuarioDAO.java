package nescaupower.br.com.keepsoft.Factory.BD.DAO;

import android.util.Log;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.lang.reflect.Type;
import java.util.List;
import java.util.concurrent.ExecutionException;

import nescaupower.br.com.keepsoft.Factory.BD.Database.HttpService;
import nescaupower.br.com.keepsoft.Factory.Model.AlterarSenha;
import nescaupower.br.com.keepsoft.Factory.Model.Login;
import nescaupower.br.com.keepsoft.Factory.Model.Usuario;

public class UsuarioDAO {

    public List<Usuario> getAll(){
        String tJson;
        try {
            tJson = new HttpService().execute("usuarios", "Get", null).get();
            Type type = new TypeToken<List<Usuario>>(){}.getType();
            return new Gson().fromJson(tJson, type);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Usuario findByID(long id){
        String tJson;
        try {
            tJson = new HttpService().execute("/usuarios/"+id, "Get", null).get();
            return new Gson().fromJson(tJson, Usuario.class);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Usuario findByLogin(String login){
        String tJson;
        try {
            tJson = new HttpService().execute("/usuarios/getByLogin/"+login, "Get", null).get();
            return new Gson().fromJson(tJson, Usuario.class);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void alterarSenha(AlterarSenha alterarSenha){
        String tJson = new Gson().toJson(alterarSenha);
        try{
            new HttpService().execute("/usuarios/alterarSenha", "Put", tJson).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    public List<Usuario> findByLoginOrName(String search, Long id, Long idProjeto){
        String tJson;
        try {
            tJson = new HttpService().execute("/usuarios/getByLoginOrName/"+search+"/"+id+"/"+idProjeto, "Get", null).get();
            Type type = new TypeToken<List<Usuario>>(){}.getType();
            return new Gson().fromJson(tJson, type);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Usuario findByEmail(String email){
        String tJson;
        try {
            tJson = new HttpService().execute("/usuarios/getByEmail/"+email, "Get", null).get();
            return new Gson().fromJson(tJson, Usuario.class);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Usuario login(Login login){
        String tJson = new Gson().toJson(login);
        try {
            tJson = new HttpService().execute("/login", "Post", tJson).get();
            Gson gson = new GsonBuilder().disableHtmlEscaping().create();
            Log.e("Json", tJson);
            return gson.fromJson(tJson, Usuario.class);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Usuario insert(Usuario usuario) {
        String tJson = new Gson().toJson(usuario);
        try {
            tJson = new HttpService().execute("/usuarios", "Post", tJson).get();
            return new Gson().fromJson(tJson, Usuario.class);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void updateAll(Usuario... usuarios){
        for(Usuario usuario : usuarios){
            String tJson = new Gson().toJson(usuario);
            try {
                new HttpService().execute("/usuarios/"+usuario.getId(), "Put", tJson).get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
    }

    public void delete(Usuario usuario){
        String tJson = null;
        try {
            tJson = new HttpService().execute("/usuarios/"+usuario.getId(), "Delete", null).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    /*@Query("SELECT id as _id, nome, login, email, telefone FROM usuario WHERE (login LIKE :login OR nome LIKE :login) AND id <> :id")
    Cursor listUsersCursor(String login, long id);*/
}
