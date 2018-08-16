package nescaupower.br.com.keepsoft;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import nescaupower.br.com.keepsoft.Config.Settings;
import nescaupower.br.com.keepsoft.Views.Login.LoginActivity;
import nescaupower.br.com.keepsoft.Views.Usuario.PaginaInicial;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences sharedPreferences = getSharedPreferences(Settings.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        boolean logado = sharedPreferences.getBoolean(Settings.LOGADO, false);
        Intent intent;

        //Se usuário já estiver logado, carrega a página inicial, senão, carrega a tela de login
        if (logado) {
            intent = new Intent(MainActivity.this, PaginaInicial.class);
        } else {
            intent = new Intent(MainActivity.this, LoginActivity.class);
        }

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK ); // adiciona a flag para a intent
        startActivity(intent);
        MainActivity.this.finish();

    }
}
