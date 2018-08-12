package nescaupower.br.com.keepsoft;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import nescaupower.br.com.keepsoft.Factory.BD.Database.AppDatabase;
import nescaupower.br.com.keepsoft.Factory.Factory;
import nescaupower.br.com.keepsoft.Factory.Model.Usuario;

public class LoginActivity extends AppCompatActivity {

    public static final String EXTRA_MESSAGE = "nescaupower.br.com.keepsoft.MESSAGE";
    AppDatabase db;
    Usuario usuario;
    TextView txtEmail;
    Button btnTestLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        txtEmail = findViewById(R.id.txtEmail);
        btnTestLogin = findViewById(R.id.btnTestLogin);

        db = Factory.startDatabase(getApplicationContext());

        Usuario u = Factory.startUsuario();
        u.setEmail("viniciuspedro350@gmail.com");
        u.setLogin("pedro");
        u.setSenha("Pedro8251");
        u.setNome("Pedro Pequeno");
        u.setTelefone("991497173");
        if (db.usuarioDAO().findByEmail(u.getEmail()) == null) {
            Toast.makeText(getApplicationContext(), "Nulo! 0.1", Toast.LENGTH_SHORT).show();
            db.usuarioDAO().insertAll(u);
        } else {
            usuario = db.usuarioDAO().findByEmail(u.getEmail());
        }

        btnTestLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                usuario = db.usuarioDAO().findByEmail(txtEmail.getText().toString());
                if (usuario == null) {
                    Toast.makeText(getApplicationContext(), "Nulo!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Bem vindo " + usuario.getLogin() + "!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    intent.putExtra(EXTRA_MESSAGE, "Eae man");
                    startActivity(intent);
                }
            }
        });

    }
}
