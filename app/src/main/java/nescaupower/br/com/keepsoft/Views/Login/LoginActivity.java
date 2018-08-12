package nescaupower.br.com.keepsoft.Views.Login;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import nescaupower.br.com.keepsoft.Config.Settings;
import nescaupower.br.com.keepsoft.Factory.BD.Database.AppDatabase;
import nescaupower.br.com.keepsoft.Factory.Factory;
import nescaupower.br.com.keepsoft.Factory.Model.Usuario;
import nescaupower.br.com.keepsoft.R;
import nescaupower.br.com.keepsoft.Views.Usuario.CadastroUsuario;
import nescaupower.br.com.keepsoft.Views.Usuario.PaginaInicial;

public class LoginActivity extends AppCompatActivity {

    EditText login;
    EditText senha;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        login = findViewById(R.id.login);
        senha = findViewById(R.id.senha);
    }


    public void entrar(View view) {
        AppDatabase db = Factory.startDatabase(getApplicationContext());

        Usuario u = db.usuarioDAO().login(login.getText().toString(), login.getText().toString(), senha.getText().toString());
        if(u != null){
            Toast.makeText(this, "Certo!", Toast.LENGTH_SHORT).show();
            SharedPreferences sharedPreferences = getSharedPreferences(Settings.SHARED_PREF_NAME, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor;
            editor = sharedPreferences.edit();
            editor.putBoolean(Settings.LOGADO, true);
            editor.putString(Settings.LOGIN, u.getLogin());
            editor.commit();

            //Singleton
            Usuario.setUsuario_logado(u);
            /////////////

            Intent intent;
            intent = new Intent(LoginActivity.this, PaginaInicial.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK ); // adiciona a flag para a intent
            startActivity(intent);
            LoginActivity.this.finish();

        }else{
            Toast.makeText(this, "Algo errado!", Toast.LENGTH_SHORT).show();
        }
    }

    public void cadastrar(View view) {
        Intent intent;
        intent = new Intent(LoginActivity.this, CadastroUsuario.class);

        startActivity(intent);
    }
}
