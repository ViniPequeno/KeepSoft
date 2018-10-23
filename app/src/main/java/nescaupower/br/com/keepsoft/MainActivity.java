package nescaupower.br.com.keepsoft;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.List;
import java.util.concurrent.ExecutionException;

import nescaupower.br.com.keepsoft.Config.Settings;
import nescaupower.br.com.keepsoft.Factory.BD.Database.HttpService;
import nescaupower.br.com.keepsoft.Factory.Model.Usuario;
import nescaupower.br.com.keepsoft.Utils.BroadcastReceiver;
import nescaupower.br.com.keepsoft.Utils.Notificacao;
import nescaupower.br.com.keepsoft.Utils.Receiver;
import nescaupower.br.com.keepsoft.Views.Login.LoginActivity;
import nescaupower.br.com.keepsoft.Views.Usuario.CadastroUsuarioActivity;
import nescaupower.br.com.keepsoft.Views.Usuario.PaginaInicialActivity;

public class MainActivity extends AppCompatActivity {

    TextView txtInformacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //apiAccess();
        //Notificacao notificacao = new Notificacao("TITULO", "Texto", R.drawable.ic_home_black_24dp, "Tickef",
          //      this, new Intent(MainActivity.this, CadastroUsuarioActivity.class));

        //notificacao.notificar();

        /*Intent intent = new Intent();
        intent.setAction("nescaupower.br.com.keepsoft.SOME_ACTION");
        sendBroadcast(intent);*/
        //Intent serviceIntent = new Intent(this, BroadcastReceiver.class);
        //startService(serviceIntent);

        String tJson = null;
        try {
            tJson = new HttpService().execute("/values/", "Get", null).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        if(tJson != null){
            semAPI();
        }else{
            setContentView(R.layout.activity_main);
            txtInformacao = findViewById(R.id.txtInformacao);
            if(verificaConexao()){
                txtInformacao.setText("Os serviços estão indisponível no momento!");
            }else{
                txtInformacao.setText("Verifica sua conexão com a internet!");
            }

        }

    }



    private void semAPI() {
        SharedPreferences sharedPreferences = getSharedPreferences(Settings.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        boolean logado = sharedPreferences.getBoolean(Settings.LOGADO, false);
        Intent intent;

        //Se usuário já estiver logado, carrega a página inicial, senão, carrega a tela de login
        if (logado) {
            intent = new Intent(MainActivity.this, PaginaInicialActivity.class);
        } else {
            intent = new Intent(MainActivity.this, LoginActivity.class);
        }

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK); // adiciona a flag para a intent
        startActivity(intent);
        MainActivity.this.finish();
    }

    private void apiAccess() {
        String tJson = null;
        try {
            tJson = new HttpService().execute("/usuarios", "Get", null).get();
            Type type = new TypeToken<List<Usuario>>(){}.getType();
            List<Usuario> list = (List<Usuario>) new Gson().fromJson(tJson, type);
            if(list !=null) {
                for (Usuario usuario : list) {
                    Log.e("Usuario", usuario.getEmail());
                }
            }else{
                Log.e("Pedro", "erro");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    public  boolean verificaConexao() {
        boolean conectado;
        ConnectivityManager conectivtyManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (conectivtyManager.getActiveNetworkInfo() != null
                && conectivtyManager.getActiveNetworkInfo().isAvailable()
                && conectivtyManager.getActiveNetworkInfo().isConnected()) {
            conectado = true;
        } else {
            conectado = false;
        }
        return conectado;
    }
}
