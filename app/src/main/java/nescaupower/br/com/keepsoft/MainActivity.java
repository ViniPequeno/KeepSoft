package nescaupower.br.com.keepsoft;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import nescaupower.br.com.keepsoft.Factory.BD.DAO.UsuarioDAO;
import nescaupower.br.com.keepsoft.Factory.BD.Database.AppDatabase;
import nescaupower.br.com.keepsoft.Factory.Factory;
import nescaupower.br.com.keepsoft.Factory.Model.Usuario;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AppDatabase db = Factory.startDatabase(getApplicationContext());
        Usuario u = Factory.startUsuario();
        u.setEmail("viniciuspedro350@gmail.com");
        u.setLogin("pedro");
        u.setSenha("Pedro8251");
        u.setNome("Pedro Pequeno");
        u.setTelefone("991497173");
        db.usuarioDAO().insertAll(u);

        Usuario usuario = db.usuarioDAO().findByName(u.getNome(), u.getEmail());
        Toast.makeText(this, usuario.getLogin(), Toast.LENGTH_SHORT).show();

    }
}
