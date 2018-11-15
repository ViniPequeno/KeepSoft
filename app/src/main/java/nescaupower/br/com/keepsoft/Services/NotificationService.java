package nescaupower.br.com.keepsoft.Services;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.support.annotation.Nullable;

import java.util.List;

import nescaupower.br.com.keepsoft.Config.Settings;
import nescaupower.br.com.keepsoft.Controller.ConviteController;
import nescaupower.br.com.keepsoft.Controller.ProjetoController;
import nescaupower.br.com.keepsoft.Controller.UsuarioController;
import nescaupower.br.com.keepsoft.Factory.Model.Convite;
import nescaupower.br.com.keepsoft.Factory.Model.Projeto;
import nescaupower.br.com.keepsoft.Factory.Model.Usuario;
import nescaupower.br.com.keepsoft.R;
import nescaupower.br.com.keepsoft.Utils.Notificacao;
import nescaupower.br.com.keepsoft.Utils.NotificationBroadcastReceiver;
import nescaupower.br.com.keepsoft.Views.PaginaInicialActivity;

public class NotificationService extends Service implements Runnable {
    public boolean ativo;
    private ConviteController cc;
    private ProjetoController pc;
    private Usuario usuario;
    private UsuarioController uc;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }

    @Override
    public void onCreate() {
        ativo = true;
        if (Usuario.getUsuarioLogado() != null) {
            Thread thread = new Thread(this);
            thread.start();
        }
        super.onCreate();
    }

    @Override
    public void onDestroy() {
        Intent broadcastIntent = new Intent(this, NotificationBroadcastReceiver.class);
        sendBroadcast(broadcastIntent);
        super.onDestroy();
    }

    @Override
    public void run() {
        try {
            Thread.sleep(5000);
            if (cc == null) {
                cc = new ConviteController();
            }
            if (pc == null) {
                pc = new ProjetoController();
            }
            if (uc == null) {
                uc = new UsuarioController();
            }
            //Singleton
            usuario = Usuario.getUsuarioLogado();
            if (usuario == null || usuario.getLogin().equals("")) {
                SharedPreferences sharedPreferences = getSharedPreferences(Settings.SHARED_PREF_NAME, Context.MODE_PRIVATE);
                usuario = uc.procurarPorLogin(sharedPreferences.getString(Settings.LOGIN, ""));
            }
            if(usuario != null) {
                try {
                    List<Convite> conviteList = cc.procurarPorIDNotVistos(usuario.getId());
                    if (!conviteList.isEmpty()) {
                        if (conviteList.size() == 1) {
                            Projeto projeto = pc.procurarPorCodigo(conviteList.get(0).getProjeto().getCodigo());
                            Notificacao notificacao = new Notificacao("Convite - " + projeto.getNome(),
                                    "Você recebeu um convite para participar do projeto como " + conviteList.get(0).getFuncao().toString(),
                                    R.mipmap.ic_launcher1, "Teset123",
                                    this, new Intent(this, PaginaInicialActivity.class));
                            notificacao.notificar();
                        } else {
                            Notificacao notificacao = new Notificacao(conviteList.size() + " convites",
                                    "Você recebeu " + conviteList.size() + " convite",
                                    R.mipmap.ic_launcher1, "Teset123",
                                    this, new Intent(this, PaginaInicialActivity.class));
                            notificacao.notificar();
                        }
                        for (Convite convite : conviteList) {
                            convite.setVisto(true);
                            cc.atualizar(convite);
                        }
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ativo = false;
    }
}
