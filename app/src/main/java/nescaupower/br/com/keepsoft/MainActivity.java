package nescaupower.br.com.keepsoft;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.List;
import java.util.concurrent.ExecutionException;

import nescaupower.br.com.keepsoft.Config.Settings;
import nescaupower.br.com.keepsoft.Factory.BD.Database.HttpService;
import nescaupower.br.com.keepsoft.Factory.Model.Usuario;
import nescaupower.br.com.keepsoft.Views.Login.LoginActivity;
import nescaupower.br.com.keepsoft.Views.Usuario.PaginaInicialActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String tJson = null;
        Log.e("1233", "233");
        try {
            tJson = new HttpService().execute("/usuarios", "Get", null).get();
            Type type = new TypeToken<List<Usuario>>(){}.getType();
            List<Usuario> list = (List<Usuario>) new Gson().fromJson(tJson, type);
            Log.e("Jose", "123");
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


        /*SharedPreferences sharedPreferences = getSharedPreferences(Settings.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        boolean logado = sharedPreferences.getBoolean(Settings.LOGADO, false);
        Intent intent;

        //Se usuário já estiver logado, carrega a página inicial, senão, carrega a tela de login
        if (logado) {
            intent = new Intent(MainActivity.this, PaginaInicialActivity.class);
        } else {
            intent = new Intent(MainActivity.this, LoginActivity.class);
        }

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK ); // adiciona a flag para a intent
        startActivity(intent);
        MainActivity.this.finish();*/

    }
}
