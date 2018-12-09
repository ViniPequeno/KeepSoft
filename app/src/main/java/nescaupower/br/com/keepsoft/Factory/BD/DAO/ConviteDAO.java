package nescaupower.br.com.keepsoft.Factory.BD.DAO;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.List;
import java.util.concurrent.ExecutionException;

import nescaupower.br.com.keepsoft.Factory.BD.Database.HttpService;
import nescaupower.br.com.keepsoft.Factory.Model.Convite;
import nescaupower.br.com.keepsoft.Factory.Model.QuantidadeNotificacoes;

public class ConviteDAO {

    public List<Convite> getAll(){
        String tJson;
        try {
            tJson = new HttpService().execute("/convite", "Get", null).get();
            Type type = new TypeToken<List<Convite>>(){}.getType();
            return new Gson().fromJson(tJson, type);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Convite findByProjetoUsuariosDestinario(Long id) {
        String tJson;
        try {
            tJson = new HttpService().execute("/convite/"+id, "Get", null).get();
            return new Gson().fromJson(tJson, Convite.class);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Convite> findByIDNotVistos(Long id){
        String tJson;
        try {
            tJson = new HttpService().execute("/convite/findByReceiverIDNotVistos/"+id, "Get", null).get();
            Type type = new TypeToken<List<Convite>>(){}.getType();
            return new Gson().fromJson(tJson, type);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Convite findByProjetoUsuariosDestinario(Long destinatarioId, Long codProjeto) {
        String tJson;
        try {
            tJson = new HttpService().execute("/convite/findByProjetoUsuariosDestinario/"+codProjeto+"/"+destinatarioId, "Get", null).get();
            return new Gson().fromJson(tJson, Convite.class);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    public QuantidadeNotificacoes findNotVistos(Long destinatarioId) {
        String tJson;
        try {
            tJson = new HttpService().execute("/convite/findNotVistos/"+destinatarioId, "Get", null).get();
            return new Gson().fromJson(tJson, QuantidadeNotificacoes.class);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Convite> findByProjectID(Long codProjeto){
        String tJson;
        try {
            tJson = new HttpService().execute("/convite/findByProjeto/"+codProjeto, "Get", null).get();
            Type type = new TypeToken<List<Convite>>(){}.getType();
            return new Gson().fromJson(tJson, type);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Convite> findByReceiverID(long destinatarioId){
        String tJson;
        try {
            tJson = new HttpService().execute("/convite/findByReceiverID/"+destinatarioId, "Get", null).get();
            Type type = new TypeToken<List<Convite>>(){}.getType();
            List<Convite> list = new Gson().fromJson(tJson, type);
            return list;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void insertAll(Convite... convitees) {
        for (Convite convite : convitees) {
            String tJson = new Gson().toJson(convite);
            try {
                new HttpService().execute("/convite", "Post", tJson).get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
    }

    public void updateAll(Convite... convitees) {
        for (Convite convite : convitees) {
            String tJson = new Gson().toJson(convite);
            try {
                new HttpService().execute("/convite/" + convite.getId(), "Put", tJson).get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
    }

    public void delete(Convite convite) {
        String tJson = null;
        try {
            tJson = new HttpService().execute("/convite/" + convite.getId(), "Delete", null).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}
