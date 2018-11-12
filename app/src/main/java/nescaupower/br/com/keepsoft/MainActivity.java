package nescaupower.br.com.keepsoft;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.concurrent.ExecutionException;

import nescaupower.br.com.keepsoft.Config.Settings;
import nescaupower.br.com.keepsoft.EmailController.ReuniaoEmail;
import nescaupower.br.com.keepsoft.Factory.BD.Database.HttpService;
import nescaupower.br.com.keepsoft.Views.Login.LoginActivity;
import nescaupower.br.com.keepsoft.Views.PaginaInicialActivity;

public class MainActivity extends AppCompatActivity {

    TextView txtInformacao;
    Button btnReconectar;

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

        String tJson = testarAPI();

        if(tJson != null){
            iniciar();
        }else{
            setContentView(R.layout.activity_main);
            txtInformacao = findViewById(R.id.txtInformacao);
            btnReconectar = findViewById(R.id.btnReconectar);
            btnReconectar.setOnClickListener(view -> {
                if (testarAPI() != null) {
                    iniciar();
                }
            });
            if(verificaConexao()){
                txtInformacao.setText(R.string.unavailable_services);
            }else{
                txtInformacao.setText(R.string.check_internet_connection);
            }

        }

    }

    @Nullable
    private String testarAPI() {
        String tJson = null;
        try {
            tJson = new HttpService().execute("/values/", "Get", null).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return tJson;
    }


    private void iniciar() {
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

    public  boolean verificaConexao() {
        boolean conectado;
        ConnectivityManager conectivtyManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        conectado = conectivtyManager.getActiveNetworkInfo() != null
                && conectivtyManager.getActiveNetworkInfo().isAvailable()
                && conectivtyManager.getActiveNetworkInfo().isConnected();
        return conectado;
    }
}
