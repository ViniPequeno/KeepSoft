package nescaupower.br.com.keepsoft;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import nescaupower.br.com.keepsoft.Factory.BD.DAO.UsuarioDAO;
import nescaupower.br.com.keepsoft.Factory.BD.Database.AppDatabase;
import nescaupower.br.com.keepsoft.Factory.Factory;
import nescaupower.br.com.keepsoft.Factory.Model.Usuario;

public class MainActivity extends AppCompatActivity {

    AppDatabase db;
    Usuario usuario;
    TextView txtEmail;
    Button btnTestLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtEmail  = (TextView) findViewById(R.id.txtEmail);
        btnTestLogin  = (Button)findViewById(R.id.btnTestLogin);

        db = Factory.startDatabase(getApplicationContext());

        Usuario u = Factory.startUsuario();
        u.setEmail("viniciuspedro350@gmail.com");
        u.setLogin("pedro");
        u.setSenha("Pedro8251");
        u.setNome("Pedro Pequeno");
        u.setTelefone("991497173");
        if (db.usuarioDAO().findByEmail(u.getEmail()) == null) {
            Toast.makeText(getApplicationContext(),"Nulo! 0.1",Toast.LENGTH_SHORT).show();
            db.usuarioDAO().insertAll(u);
        }else{
            usuario = db.usuarioDAO().findByEmail(u.getEmail());
        }

        btnTestLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                usuario = db.usuarioDAO().findByEmail(txtEmail.getText().toString());
                if(usuario==null){
                    Toast.makeText(getApplicationContext(),"Nulo!",Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(getApplicationContext(), "Bem vindo " + usuario.getLogin() + "!", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
