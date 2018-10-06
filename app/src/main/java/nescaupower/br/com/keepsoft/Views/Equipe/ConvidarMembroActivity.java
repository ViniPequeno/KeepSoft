package nescaupower.br.com.keepsoft.Views.Equipe;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;
import android.widget.SearchView;

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

        usuarios = new ArrayList<>();
        cc = new ConviteController(ConvidarMembroActivity.this);
        pc = new PerfilController(ConvidarMembroActivity.this);

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

        btnEnivarConvite.setOnClickListener(v -> {
            AlertDialog.Builder adb = new AlertDialog.Builder(ConvidarMembroActivity.this);
            adb.setTitle("Lista");
            StringBuilder sb = new StringBuilder();
            int index = 0;
            Convite convites[] = new Convite[usuarios.size()];
            Perfil perfis[] = new Perfil[usuarios.size()];
            for (Usuario usuario : usuarios) {
                convites[index] = Factory.startConvite();
                convites[index].setCodProjeto(Projeto.getUltimoProjetoUsado().getCodigo());
                convites[index].setRemetenteId(Usuario.getUsuarioLogado().getId());
                convites[index].setFuncao(rvAdapter.getFuncaoUsuario(index));
                convites[index].setDestinatarioId(usuario.getId());

                Date hoje = Calendar.getInstance().getTime();
                convites[index].setData(hoje);

                perfis[index] = Factory.startPerfil();
                perfis[index].setCodProjeto(Projeto.getUltimoProjetoUsado().getCodigo());
                perfis[index].setIdUsuario(usuario.getId());
                perfis[index].setDataInicio(null);
                perfis[index].setDataFim(null);
                perfis[index].setPerfil(rvAdapter.getFuncaoUsuario(index));
                sb.append(usuario.getNome() + ": " + perfis[index].getPerfil().toString() + " " + usuario.getSenha() + " \n");
                index++;
            }
            adb.setMessage(sb.toString());
            adb.create().show();
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
