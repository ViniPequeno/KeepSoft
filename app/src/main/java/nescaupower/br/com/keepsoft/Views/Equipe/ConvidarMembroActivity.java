package nescaupower.br.com.keepsoft.Views.Equipe;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import nescaupower.br.com.keepsoft.Controller.ConviteController;
import nescaupower.br.com.keepsoft.Controller.UsuarioController;
import nescaupower.br.com.keepsoft.Factory.Model.Usuario;
import nescaupower.br.com.keepsoft.R;

public class ConvidarMembroActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    List<Usuario> usuarios;
    RecyclerView usuariosRV;
    SearchView txtPesquisarUsuario;
    PesquisarUsuarioCursorAdapter searchViewAdapter; //Adapter da lista do campo de pesquisa
    PesquisarUsuarioRVAdapter rvAdapter; //Adapter da lista que conterá os usuários que serão convidados
    ConviteController cc;
    Button btnEnivarConvite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_convidar_membro);

        usuarios = new ArrayList<>();
        cc = new ConviteController(ConvidarMembroActivity.this);

        txtPesquisarUsuario = findViewById(R.id.txtPesquisarUsuario);
        btnEnivarConvite = findViewById(R.id.btnEnviarConvite);

        UsuarioController uc = new UsuarioController(ConvidarMembroActivity.this);
        Cursor cursor = uc.listarUsuariosCursor("", 0);

        rvAdapter = new PesquisarUsuarioRVAdapter(usuarios, ConvidarMembroActivity.this);
        searchViewAdapter = new PesquisarUsuarioCursorAdapter(ConvidarMembroActivity.this, cursor, txtPesquisarUsuario, rvAdapter);

        txtPesquisarUsuario.setOnQueryTextListener(this);
        txtPesquisarUsuario.setSuggestionsAdapter(searchViewAdapter);

        usuariosRV = findViewById(R.id.UsuariosRV);
        usuariosRV.setLayoutManager(new LinearLayoutManager(this));
        usuariosRV.setAdapter(rvAdapter);

        btnEnivarConvite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ConvidarMembroActivity.this, "Qtde: "+usuarios.size(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        String login = "%" + newText + "%";
        //((PesquisarUsuarioRVAdapter) usuariosRV.getAdapter()).filtrar(text);
        //Toast.makeText(ConvidarMembroActivity.this, usuarios.size() + " " + text, Toast.LENGTH_SHORT).show();
        UsuarioController uc = new UsuarioController(ConvidarMembroActivity.this);
        Cursor c = uc.listarUsuariosCursor(login, Usuario.getUsuarioLogado().getId());
        atualizarResultados(c);
        return false;
    }

    private void atualizarResultados(Cursor cursor) {
        searchViewAdapter.swapCursor(cursor);
    }
}
