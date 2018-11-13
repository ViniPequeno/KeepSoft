package nescaupower.br.com.keepsoft.Services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import nescaupower.br.com.keepsoft.R;
import nescaupower.br.com.keepsoft.Utils.Notificacao;
import nescaupower.br.com.keepsoft.Utils.NotificationBroadcastReceiver;
import nescaupower.br.com.keepsoft.Views.Usuario.CadastroUsuarioActivity;

public class NotificationService extends Service implements Runnable {
    public boolean ativo;

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
        if (ativo) {
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
            Log.e("Seila", "Fatima");
            Notificacao notificacao = new Notificacao("TITULO", "Texto", R.drawable.ic_home_black_24dp, "Tickef",
                    this, new Intent(this, CadastroUsuarioActivity.class));
            notificacao.notificar();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ativo = false;
    }
}
