package nescaupower.br.com.keepsoft.Views.Usuario;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import nescaupower.br.com.keepsoft.Factory.BD.Database.AppDatabase;
import nescaupower.br.com.keepsoft.Factory.Factory;
import nescaupower.br.com.keepsoft.Factory.Model.Usuario;
import nescaupower.br.com.keepsoft.R;
import nescaupower.br.com.keepsoft.Views.Login.LoginActivity;

public class CadastroUsuario extends AppCompatActivity {

    EditText txtLogin, txtNome, txtEmail, txtSenha, txtConfirmarSenha, txtTelefone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_usuario);

        txtLogin = findViewById(R.id.txtLogin);
        txtNome = findViewById(R.id.txtNome);
        txtEmail = findViewById(R.id.txtEmail);
        txtSenha = findViewById(R.id.txtSenha);
        txtConfirmarSenha = findViewById(R.id.txtConfirmarSenha);
        txtTelefone = findViewById(R.id.txtTelefone);

    }

    public void cadastrar(View view) {
        if(txtSenha.getText().toString().equals(txtConfirmarSenha.getText().toString())){
            AppDatabase db = Factory.startDatabase(getApplicationContext());
            Usuario u = Factory.startUsuario();
            u.setEmail(txtEmail.getText().toString());
            u.setLogin(txtLogin.getText().toString());
            u.setSenha(txtSenha.getText().toString());
            u.setNome(txtNome.getText().toString());
            u.setTelefone(txtTelefone.getText().toString());
            if(db.usuarioDAO().findByLogin(u.getLogin()) == null) {
                db.usuarioDAO().insertAll(u);
                Intent intent;
                intent = new Intent(CadastroUsuario.this, LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK); // adiciona a flag para a intent
                Toast.makeText(this, "Cadastrado!", Toast.LENGTH_SHORT).show();
                startActivity(intent);
                CadastroUsuario.this.finish();
            }else{
                Toast.makeText(this, "Usuario já existe!", Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(this, "Senhas não são iguais!", Toast.LENGTH_SHORT).show();
        }


    }
}
