package nescaupower.br.com.keepsoft.Views.Equipe;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.SearchView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import nescaupower.br.com.keepsoft.Factory.Model.Usuario;
import nescaupower.br.com.keepsoft.R;

public class ConvidarMembroActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    List<Usuario> usuarios;
    RecyclerView usuariosRV;
    SearchView txtPesquisarUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_convidar_membro);

        usuarios = new ArrayList<>();

        usuariosRV = findViewById(R.id.UsuariosRV);
        usuariosRV.setLayoutManager(new LinearLayoutManager(this));
        usuariosRV.setAdapter(new PesquisarEquipeRecyclerViewAdapter(usuarios, this));

        txtPesquisarUsuario = findViewById(R.id.txtPesquisarUsuario);
        txtPesquisarUsuario.setOnQueryTextListener(this);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        String text = newText;
        ((PesquisarEquipeRecyclerViewAdapter) usuariosRV.getAdapter()).filtrar(text);
        Toast.makeText(ConvidarMembroActivity.this, usuarios.size() + " " + text, Toast.LENGTH_SHORT).show();
        return false;
    }
}
