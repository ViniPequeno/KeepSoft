package nescaupower.br.com.keepsoft.Views.Equipe;

import android.database.Cursor;
import android.database.MatrixCursor;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Button;
import android.widget.SearchView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import nescaupower.br.com.keepsoft.Controller.ConviteController;
import nescaupower.br.com.keepsoft.Controller.PerfilController;
import nescaupower.br.com.keepsoft.Controller.UsuarioController;
import nescaupower.br.com.keepsoft.Factory.Factory;
import nescaupower.br.com.keepsoft.Factory.Model.Convite;
import nescaupower.br.com.keepsoft.Factory.Model.Perfil;
import nescaupower.br.com.keepsoft.Factory.Model.Projeto;
import nescaupower.br.com.keepsoft.Factory.Model.Usuario;
import nescaupower.br.com.keepsoft.R;

public class ConvidarMembroActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    List<Usuario> usuarios;
    RecyclerView usuariosRV;
    SearchView txtPesquisarUsuario;
    PesquisarUsuarioCursorAdapter searchViewAdapter; //Adapter da lista do campo de pesquisa
    PesquisarUsuarioRVAdapter rvAdapter; //Adapter da lista que conterá os usuários que serão convidados
    ConviteController cc;
    PerfilController pc;
    Button btnEnivarConvite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_convidar_membro);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        usuarios = new ArrayList<>();
        cc = new ConviteController();
        pc = new PerfilController();

        txtPesquisarUsuario = findViewById(R.id.txtPesquisarUsuario);
        btnEnivarConvite = findViewById(R.id.btnEnviarConvite);

        UsuarioController uc = new UsuarioController();
        List<Usuario> list = uc.listarUsuariosCursor(" ", new Long(0));

        rvAdapter = new PesquisarUsuarioRVAdapter(usuarios, ConvidarMembroActivity.this);


        searchViewAdapter = new PesquisarUsuarioCursorAdapter(ConvidarMembroActivity.this, getCursor(list), txtPesquisarUsuario, rvAdapter);

        txtPesquisarUsuario.setOnQueryTextListener(this);
        txtPesquisarUsuario.setSuggestionsAdapter(searchViewAdapter);

        usuariosRV = findViewById(R.id.UsuariosRV);
        usuariosRV.setLayoutManager(new LinearLayoutManager(this));
        usuariosRV.setAdapter(rvAdapter);

        btnEnivarConvite.setOnClickListener(v -> {
            int index = 0;
            Convite convites[] = new Convite[usuarios.size()];
            Perfil perfis[] = new Perfil[usuarios.size()];
            for (Usuario usuario : usuarios) {
                convites[index] = Factory.startConvite();
                convites[index].setCodProjeto(Projeto.getUltimoProjetoUsado());
                convites[index].setRemetenteId(Usuario.getUsuarioLogado());
                convites[index].setFuncao(rvAdapter.getFuncaoUsuario(index));
                convites[index].setDestinatarioId(usuario);

                Date hoje = Calendar.getInstance().getTime();
                convites[index].setData(hoje);

                perfis[index] = Factory.startPerfil();
                perfis[index].setProjeto(Projeto.getUltimoProjetoUsado());
                perfis[index].setUsuario(usuario);
                perfis[index].setDataInicioFormat("");
                perfis[index].setDataFimFormat("");
                perfis[index].setDataInicio(null);
                perfis[index].setDataFim(null);
                perfis[index].setPerfil(rvAdapter.getFuncaoUsuario(index));
                index++;
            }
            cc.cadastrar(convites);
            pc.cadastrar(perfis);
            ConvidarMembroActivity.this.finish();
        });
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        String login = newText;
        if(login == ""){
            login = " ";
        }
        //((PesquisarUsuarioRVAdapter) usuariosRV.getAdapter()).filtrar(text);
        //Toast.makeText(ConvidarMembroActivity.this, usuarios.size() + " " + text, Toast.LENGTH_SHORT).show();
        UsuarioController uc = new UsuarioController();
        List<Usuario> list = uc.listarUsuariosCursor(login, Usuario.getUsuarioLogado().getId());
        atualizarResultados(list);
        return false;
    }

    private void atualizarResultados(List<Usuario> list) {


        searchViewAdapter.swapCursor(getCursor(list));
    }

    private MatrixCursor getCursor(List<Usuario> list){
        MatrixCursor matrixCursor = new MatrixCursor(
                new String[] {"_id", "login", "email", "nome", "telefone"}
        );
        if(list != null) {
            for (Usuario usuario : list) {
                matrixCursor.newRow()
                        .add(usuario.getId())
                        .add(usuario.getLogin())
                        .add(usuario.getEmail())
                        .add(usuario.getNome())
                        .add(usuario.getTelefone());
            }
        }

        return matrixCursor;
    }
}
