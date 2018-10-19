package nescaupower.br.com.keepsoft.Views.Usuario;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import nescaupower.br.com.keepsoft.Config.Settings;
import nescaupower.br.com.keepsoft.Controller.UsuarioController;
import nescaupower.br.com.keepsoft.Factory.Model.Usuario;
import nescaupower.br.com.keepsoft.R;

public class AlterarPerfilActivity extends AppCompatActivity {

    private EditText alterarNome;
    private EditText alterarEmail;
    private EditText alterarTelefone;
    private Button btnAlterar;
    private Button btnCancelar;

    private UsuarioController uc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alterar_perfil);

        uc = new UsuarioController();

        //Singleton
        Usuario usuario = Usuario.getUsuarioLogado();
        if (usuario == null || usuario.getLogin().equals("")) {
            SharedPreferences sharedPreferences = getSharedPreferences(Settings.SHARED_PREF_NAME, Context.MODE_PRIVATE);
            usuario = uc.procurarPorLogin(sharedPreferences.getString(Settings.LOGIN, ""));
        }

        alterarEmail = findViewById(R.id.alterarEmail);
        alterarNome = findViewById(R.id.alterarNome);
        alterarTelefone = findViewById(R.id.alterarTelefone);

        btnAlterar = findViewById(R.id.btnAlterarPerfil);
        btnCancelar = findViewById(R.id.btnAlterarPerfilCancelar);

        alterarEmail.setText(usuario.getEmail());
        alterarNome.setText(usuario.getNome());
        alterarTelefone.setText(usuario.getTelefone());
    }

    public void alterarPerfil(View view) {
        if(alterarEmail.getText().toString().equals("")){
            Toast.makeText(this, "O E-mail não pode ficar vazio!", Toast.LENGTH_SHORT).show();
        }else if(alterarNome.getText().toString().equals("")){
            Toast.makeText(this, "O nome não pode ficar vazio!", Toast.LENGTH_SHORT).show();
        }else{
            Usuario usuario = Usuario.getUsuarioLogado();
            usuario.setEmail(alterarEmail.getText().toString());
            usuario.setNome(alterarNome.getText().toString());
            usuario.setTelefone(alterarTelefone.getText().toString());

            uc.atualizar(usuario);
            Usuario.setUsuarioLogado(usuario);

            Intent intent;
            intent = new Intent(AlterarPerfilActivity.this, PaginaInicialActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK); // adiciona a flag para a intent
            startActivity(intent);
            AlterarPerfilActivity.this.finish();
        }
    }

    public void alterarPerfilCancelar(View view) {
        Intent intent;
        intent = new Intent(AlterarPerfilActivity.this, PaginaInicialActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK); // adiciona a flag para a intent
        startActivity(intent);
        AlterarPerfilActivity.this.finish();
    }
}
