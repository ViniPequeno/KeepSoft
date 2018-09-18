package nescaupower.br.com.keepsoft.Views.Equipe;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;
import android.widget.SearchView;

import java.util.ArrayList;
import java.util.List;

import nescaupower.br.com.keepsoft.Controller.UsuarioController;
import nescaupower.br.com.keepsoft.Factory.Model.Usuario;
import nescaupower.br.com.keepsoft.R;

public class ConvidarMembroActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    List<Usuario> usuarios;
    RecyclerView usuariosRV;
    SearchView txtPesquisarUsuario;
    PesquisarUsuarioCursorAdapter searchViewAdapter; //Adapter da lista do campo de pesquisa
    PesquisarUsuarioRVAdapter rvAdapter; //Adapter da lista que conterá os usuários que serão convidados
    Button btnEnivarConvite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_convidar_membro);

        usuarios = new ArrayList<>();

        txtPesquisarUsuario = findViewById(R.id.txtPesquisarUsuario);
        btnEnivarConvite = findViewById(R.id.btnEnviarConvite);

        Cursor cursor = new UsuarioController(ConvidarMembroActivity.this).listarUsuariosCursor("");
        rvAdapter = new PesquisarUsuarioRVAdapter(usuarios, ConvidarMembroActivity.this);
        searchViewAdapter = new PesquisarUsuarioCursorAdapter(ConvidarMembroActivity.this, cursor, txtPesquisarUsuario, rvAdapter);

        txtPesquisarUsuario.setOnQueryTextListener(this);
        txtPesquisarUsuario.setSuggestionsAdapter(searchViewAdapter);

        usuariosRV = findViewById(R.id.UsuariosRV);
        usuariosRV.setLayoutManager(new LinearLayoutManager(this));
        usuariosRV.setAdapter(rvAdapter);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        String text = "%" + newText + "%";
        //((PesquisarUsuarioRVAdapter) usuariosRV.getAdapter()).filtrar(text);
        //Toast.makeText(ConvidarMembroActivity.this, usuarios.size() + " " + text, Toast.LENGTH_SHORT).show();
        UsuarioController uc = new UsuarioController(ConvidarMembroActivity.this);
        Cursor c = uc.listarUsuariosCursor(text);
        atualizarResultados(c);
        return false;
    }

    private void atualizarResultados(Cursor cursor) {
        searchViewAdapter.swapCursor(cursor);
    }
}
