package nescaupower.br.com.keepsoft.Views.Login;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import nescaupower.br.com.keepsoft.Config.Settings;
import nescaupower.br.com.keepsoft.Controller.UsuarioController;
import nescaupower.br.com.keepsoft.Factory.Factory;
import nescaupower.br.com.keepsoft.Factory.Model.Usuario;
import nescaupower.br.com.keepsoft.R;
import nescaupower.br.com.keepsoft.Views.Usuario.CadastroUsuarioActivity;
import nescaupower.br.com.keepsoft.Views.Usuario.PaginaInicialActivity;

public class LoginActivity extends AppCompatActivity {

    private EditText login;
    private EditText senha;

    private UsuarioController uc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        uc = new UsuarioController(getApplicationContext());

        login = findViewById(R.id.login);
        senha = findViewById(R.id.senha);
    }


    public void entrar(View view) {

        String loginText = login.getText().toString();
        String senhaText = senha.getText().toString();
        Usuario u =  uc.realizarLogin(loginText, senhaText);

        if(u != null){
            SharedPreferences sharedPreferences = getSharedPreferences(Settings.SHARED_PREF_NAME, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor;
            editor = sharedPreferences.edit();
            editor.putBoolean(Settings.LOGADO, true);
            editor.putString(Settings.LOGIN, u.getLogin());
            editor.commit();

            //Singleton
            Factory.setLogado(u);
            //Usuario.setUsuario_logado(u);
            /////////////

            Intent intent;
            intent = new Intent(LoginActivity.this, PaginaInicialActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK ); // adiciona a flag para a intent
            startActivity(intent);
            LoginActivity.this.finish();

        }else{
            Toast.makeText(this, uc.getMensagem(), Toast.LENGTH_SHORT).show();
        }
    }

    public void cadastrar(View view) {
        Intent intent;
        intent = new Intent(LoginActivity.this, CadastroUsuarioActivity.class);

        startActivity(intent);
    }
}
