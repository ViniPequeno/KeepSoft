package nescaupower.br.com.keepsoft.Factory.BD.DAO;

import android.util.Log;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.List;
import java.util.concurrent.ExecutionException;

import nescaupower.br.com.keepsoft.Factory.BD.Database.HttpService;
import nescaupower.br.com.keepsoft.Factory.Model.Porcentagem;
import nescaupower.br.com.keepsoft.Factory.Model.Projeto;

public class ProjetoDAO {

    public List<Projeto> getAll() {
        String tJson = null;
        try {
            tJson = new HttpService().execute("/projeto", "Get", null).get();
            Type type = new TypeToken<List<Projeto>>() {
            }.getType();
            List<Projeto> list = new Gson().fromJson(tJson, type);
            return list;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;

    }

    public Projeto findById(Long id) {
        String tJson = null;
        try {
            tJson = new HttpService().execute("/projeto/" + id, "Get", null).get();
            Projeto projeto = new Gson().fromJson(tJson, Projeto.class);
            return projeto;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Porcentagem findPorcentagemById(Long id) {
        String tJson = null;
        try {
            tJson = new HttpService().execute("/projeto/porcentagem/" + id, "Get", null).get();
            Porcentagem porcentagem = new Gson().fromJson(tJson, Porcentagem.class);
            return porcentagem;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Projeto findByName(String nome) {
        String tJson = null;
        try {
            tJson = new HttpService().execute("/projeto/findByName/" + nome, "Get", null).get();
            Projeto projeto = new Gson().fromJson(tJson, Projeto.class);
            return projeto;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }


    public Projeto
    insertAll(Projeto projeto) {
        String tJson = new Gson().toJson(projeto);
        Log.e("cadastro2", tJson);
        try {
            tJson = new HttpService().execute("/projeto", "Post", tJson).get();
            Projeto projetoRetorno = new Gson().fromJson(tJson, Projeto.class);
            return projetoRetorno;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Projeto updateAll(Projeto... projetoes) {
        for (Projeto projeto : projetoes) {
            String tJson = new Gson().toJson(projeto);
            try {
                tJson = new HttpService().execute("/projeto/" + projeto.getCodigo(), "Put", tJson).get();
                return new Gson().fromJson(tJson, Projeto.class);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public void delete(Projeto projeto) {
        String tJson = null;
        try {
            tJson = new HttpService().execute("/projeto/" + projeto.getCodigo(), "Delete", null).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }


    public List<Projeto> findByUserID(Long userID) {
        String tJson = null;
        try {
            tJson = new HttpService().execute("/projeto/findByUserID/" + userID, "Get", null).get();
            Type type = new TypeToken<List<Projeto>>() {
            }.getType();
            List<Projeto> list = new Gson().fromJson(tJson, type);
            return list;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }


    public List<Projeto> findByParticipatingUserID(Long userID) {
        String tJson = null;
        try {
            tJson = new HttpService().execute("/projeto/findByParticipantingUserID/" + userID, "Get", null).get();
            Type type = new TypeToken<List<Projeto>>() {
            }.getType();
            List<Projeto> list = new Gson().fromJson(tJson, type);
            return list;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }


}
