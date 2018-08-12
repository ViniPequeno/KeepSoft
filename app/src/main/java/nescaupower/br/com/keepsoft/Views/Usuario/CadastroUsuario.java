package nescaupower.br.com.keepsoft.Views.Usuario;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import nescaupower.br.com.keepsoft.Factory.BD.Database.AppDatabase;
import nescaupower.br.com.keepsoft.Factory.Factory;
import nescaupower.br.com.keepsoft.Factory.Model.Usuario;
import nescaupower.br.com.keepsoft.R;
import nescaupower.br.com.keepsoft.Views.Login.LoginActivity;

public class CadastroUsuario extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_usuario);
    }

    public void cadastrar(View view) {
        AppDatabase db = Factory.startDatabase(getApplicationContext());
        Usuario u = Factory.startUsuario();
        u.setEmail("viniciuspedro350@gmail.com");
        u.setLogin("pedro");
        u.setSenha("Pedro8251");
        u.setNome("Pedro Pequeno");
        u.setTelefone("991497173");
        if(db.usuarioDAO().findByLogin(u.getLogin()) == null) {
            db.usuarioDAO().insertAll(u);
            Intent intent;
            intent = new Intent(CadastroUsuario.this, LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK); // adiciona a flag para a intent

            startActivity(intent);
            CadastroUsuario.this.finish();
        }else{
            Toast.makeText(this, "Usuario já existe!", Toast.LENGTH_SHORT).show();
        }

    }
}
