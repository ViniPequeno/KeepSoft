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

    private EditText lblNome;
    private EditText lblEmail;
    private EditText lblTelefone;
    private Button btnAlterar;
    private Button btnCancelar;

    private UsuarioController uc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alterar_perfil);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        uc = new UsuarioController(getApplicationContext());

        //Singleton
        Usuario usuario = Usuario.getUsuarioLogado();
        if (usuario == null || usuario.getLogin().equals("")) {
            SharedPreferences sharedPreferences = getSharedPreferences(Settings.SHARED_PREF_NAME, Context.MODE_PRIVATE);
            usuario = uc.procurarPorLogin(sharedPreferences.getString(Settings.LOGIN, ""));
        }

        lblEmail = findViewById(R.id.alterarEmail);
        lblNome = findViewById(R.id.alterarNome);
        lblTelefone = findViewById(R.id.alterarTelefone);

        btnAlterar = findViewById(R.id.btnAlterarPerfil);
        btnCancelar = findViewById(R.id.btnAlterarPerfilCancelar);

        lblEmail.setText(usuario.getEmail());
        lblNome.setText(usuario.getNome());
        lblTelefone.setText(usuario.getTelefone());
    }

    public void alterarPerfil(View view) {
        if(lblEmail.getText().toString().equals("")){
            Toast.makeText(this, "O E-mail não pode ficar vazio!", Toast.LENGTH_SHORT).show();
        }else if(lblNome.getText().toString().equals("")){
            Toast.makeText(this, "O nome não pode ficar vazio!", Toast.LENGTH_SHORT).show();
        }else{
            Usuario usuario = Usuario.getUsuarioLogado();
            usuario.setEmail(lblEmail.getText().toString());
            usuario.setNome(lblNome.getText().toString());
            usuario.setTelefone(lblTelefone.getText().toString());

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
