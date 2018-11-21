package nescaupower.br.com.keepsoft;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;

import java.util.concurrent.ExecutionException;

import nescaupower.br.com.keepsoft.Config.Settings;
import nescaupower.br.com.keepsoft.Controller.UsuarioController;
import nescaupower.br.com.keepsoft.Factory.BD.Database.HttpService;
import nescaupower.br.com.keepsoft.Factory.Model.Usuario;
import nescaupower.br.com.keepsoft.Services.NotificationService;
import nescaupower.br.com.keepsoft.Views.Login.LoginActivity;
import nescaupower.br.com.keepsoft.Views.PaginaInicialActivity;

public class MainActivity extends AppCompatActivity {

    TextView txtInformacao;
    Button btnReconectar;

    private Intent intent;
    private NotificationService  notificationService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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

        notificationService = new NotificationService();
        if(!isMyServiceRunning(notificationService.getClass())){
            intent = new Intent(this, notificationService.getClass());
            startService(intent);
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
        Intent intent1;

        //Se usuário já estiver logado, carrega a página inicial, senão, carrega a tela de login
        if (logado) {
            //Singleton
            Usuario usuario = null;
            sharedPreferences = getSharedPreferences(Settings.SHARED_PREF_NAME, Context.MODE_PRIVATE);
            UsuarioController uc = new UsuarioController();
            usuario = uc.procurarPorLogin(sharedPreferences.getString(Settings.LOGIN, ""));
            Usuario.setUsuarioLogado(usuario);
            intent1 = new Intent(MainActivity.this, PaginaInicialActivity.class);
        } else {
            intent1 = new Intent(MainActivity.this, LoginActivity.class);
        }

        intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK); // adiciona a flag para a intent
        startActivity(intent1);
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

    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
