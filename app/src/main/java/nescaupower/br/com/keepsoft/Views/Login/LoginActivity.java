package nescaupower.br.com.keepsoft.Views.Login;

import android.app.AlertDialog;
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
import nescaupower.br.com.keepsoft.EmailController.EsqueceuSenhaEmail;
import nescaupower.br.com.keepsoft.Factory.Factory;
import nescaupower.br.com.keepsoft.Factory.Model.Usuario;
import nescaupower.br.com.keepsoft.R;
import nescaupower.br.com.keepsoft.Views.PaginaInicialActivity;
import nescaupower.br.com.keepsoft.Views.Usuario.CadastroUsuarioActivity;

public class LoginActivity extends AppCompatActivity {

    private EditText login;
    private EditText senha;

    private UsuarioController uc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        uc = new UsuarioController();

        login = findViewById(R.id.login);
        senha = findViewById(R.id.senha);
    }


    public void entrar(View view) {

        String loginText = login.getText().toString();
        String senhaText = senha.getText().toString();
        Usuario usuario = uc.realizarLogin(loginText, senhaText);

        if (usuario != null) {
            if (!usuario.isEmailVerificated()) {
                //Mostrar alerta de erro
                AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                builder.setTitle(R.string.error);
                builder.setMessage(R.string.confirmEmail);
                builder.setPositiveButton(R.string.confirm, null);
                builder.show();
            } else {
                SharedPreferences sharedPreferences = getSharedPreferences(Settings.SHARED_PREF_NAME, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor;
                editor = sharedPreferences.edit();
                editor.putBoolean(Settings.LOGADO, true);
                editor.putString(Settings.LOGIN, usuario.getLogin());
                editor.commit();

                //Singleton
                Factory.setUsuarioLogado(usuario);

                Intent intent;
                intent = new Intent(LoginActivity.this, PaginaInicialActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK); // adiciona a flag para a intent
                startActivity(intent);
                LoginActivity.this.finish();
            }
        } else {
            //Mostrar alerta de erro
            AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
            builder.setTitle(R.string.error);
            builder.setMessage(R.string.wrong_login_password);
            builder.setPositiveButton(R.string.confirm, null);
            builder.show();
        }
    }

    public void cadastrar(View view) {
        Intent intent;
        intent = new Intent(LoginActivity.this, CadastroUsuarioActivity.class);
        startActivity(intent);
    }

    public void esqueceuSenha(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.rememberpass);
        final View dialogView = this.getLayoutInflater().inflate(R.layout.remenberpass, findViewById(R.id.content), false);
        builder.setView(dialogView);

        builder.setPositiveButton(R.string.confirm, (dialogInterface, i) -> {
            UsuarioController uc = new UsuarioController();
            EditText txtSenha = dialogView.findViewById(R.id.txtEmail);

            Usuario usuario = uc.procurarPorEmail(txtSenha.getText().toString());
            if (usuario != null) {
                new EsqueceuSenhaEmail().enviarEmail(usuario);
            } else {
                Toast.makeText(this, "E-mail não cadastrado!", Toast.LENGTH_SHORT).show();
            }
        });

        builder.setNegativeButton(R.string.cancel, (dialogInterface, i) -> Toast.makeText(this, "não", Toast.LENGTH_SHORT).show());

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
