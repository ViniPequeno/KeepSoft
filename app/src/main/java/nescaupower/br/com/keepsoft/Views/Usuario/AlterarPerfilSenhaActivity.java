package nescaupower.br.com.keepsoft.Views.Usuario;

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
import nescaupower.br.com.keepsoft.Factory.Model.Usuario;
import nescaupower.br.com.keepsoft.R;

public class AlterarPerfilSenhaActivity extends AppCompatActivity {

    private EditText senhaAntiga;
    private EditText senhaNova;
    private EditText senhaNovaConfirmar;

    private UsuarioController uc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alterar_perfil_senha);

        uc = new UsuarioController(getApplicationContext());

        //Singleton
        Usuario usuario = Usuario.getUsuarioLogado();
        if (usuario == null || usuario.getLogin().equals("")) {
            SharedPreferences sharedPreferences = getSharedPreferences(Settings.SHARED_PREF_NAME, Context.MODE_PRIVATE);
            usuario = uc.procurarPorLogin(sharedPreferences.getString(Settings.LOGIN, ""));
        }


        senhaAntiga = findViewById(R.id.senhaAntiga);
        senhaNova = findViewById(R.id.senhaNova);
        senhaNovaConfirmar = findViewById(R.id.senhaNovaConfirmar);
    }

    public void alterarPerfilSenha(View view) {
        if(senhaNova.getText().toString().equals("")){
            Toast.makeText(this, "Senha nova não pode ser vazio!", Toast.LENGTH_SHORT).show();
        }else{
            if(senhaNova.getText().toString().equals(senhaAntiga.getText().toString())){
                Toast.makeText(this, "Senha nova não pode ser igual a senha antiga!", Toast.LENGTH_SHORT).show();
            }else{
                if(senhaNova.getText().toString().equals(senhaNovaConfirmar.getText().toString())){
                    Toast.makeText(this, "Senha nova não é igual ao confirmar!", Toast.LENGTH_SHORT).show();
                }else{
                    Usuario usuario = Usuario.getUsuarioLogado();

                    if(usuario.getSenha().equals(senhaAntiga.getText().toString())){
                        usuario.setSenha(senhaNova.getText().toString());

                        uc.atualizar(usuario);
                        Usuario.setUsuarioLogado(usuario);

                        Intent intent;
                        intent = new Intent(AlterarPerfilSenhaActivity.this, PaginaInicialActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK); // adiciona a flag para a intent
                        startActivity(intent);
                        AlterarPerfilSenhaActivity.this.finish();
                    }else{
                        Toast.makeText(this, "Senha antiga não está certo!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }
    }

    public void alterarPerfilSenhaCancelar(View view) {
        Intent intent;
        intent = new Intent(AlterarPerfilSenhaActivity.this, PaginaInicialActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK); // adiciona a flag para a intent
        startActivity(intent);
        AlterarPerfilSenhaActivity.this.finish();
    }
}
