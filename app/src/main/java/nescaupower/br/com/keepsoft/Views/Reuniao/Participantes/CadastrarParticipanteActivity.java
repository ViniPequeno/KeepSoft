package nescaupower.br.com.keepsoft.Views.Reuniao.Participantes;

import android.database.MatrixCursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;
import android.widget.SearchView;

import java.util.ArrayList;
import java.util.List;

import nescaupower.br.com.keepsoft.Controller.ProjetoController;
import nescaupower.br.com.keepsoft.Controller.ReuniaoController;
import nescaupower.br.com.keepsoft.Controller.ReuniaoUsuarioController;
import nescaupower.br.com.keepsoft.Controller.UsuarioController;
import nescaupower.br.com.keepsoft.EmailController.ReuniaoEmail;
import nescaupower.br.com.keepsoft.Factory.Model.Projeto;
import nescaupower.br.com.keepsoft.Factory.Model.Reuniao;
import nescaupower.br.com.keepsoft.Factory.Model.ReuniaoUsuario;
import nescaupower.br.com.keepsoft.Factory.Model.Usuario;
import nescaupower.br.com.keepsoft.R;

public class CadastrarParticipanteActivity extends AppCompatActivity implements SearchView.OnQueryTextListener{

    List<Usuario> usuarios;
    RecyclerView usuariosRV;
    SearchView txtPesquisarParticipante;
    PesquisarParticipanteCursosAdapter searchViewAdapter; //Adapter da lista do campo de pesquisa
    PesquisarParticipanteRVAdapter rvAdapter; //Adapter da lista que conterá os usuários que serão convidados
    ReuniaoController rc;
    ReuniaoUsuarioController ruc;
    UsuarioController uc;
    ProjetoController pc;
    private Reuniao reuniao;
    Button btnEnivarConviteParticipante;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar_participante);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        usuarios = new ArrayList<>();
        rc = new ReuniaoController();
        ruc = new ReuniaoUsuarioController();
        pc = new ProjetoController();
        uc = new UsuarioController();

        reuniao = rc.getReuniao(getIntent().getLongExtra("EXTRA_CODIGO_REUNIAO", 0));

        txtPesquisarParticipante = findViewById(R.id.txtPesquisarParticipante);
        btnEnivarConviteParticipante = findViewById(R.id.btnEnviarConviteParticipante);

        UsuarioController uc = new UsuarioController();
        List<Usuario> list = uc.listarUsuariosCursor(" ", 0L, 0L);

        rvAdapter = new PesquisarParticipanteRVAdapter(usuarios, CadastrarParticipanteActivity.this);

        searchViewAdapter = new PesquisarParticipanteCursosAdapter(CadastrarParticipanteActivity.this,
                getCursor(list), txtPesquisarParticipante, rvAdapter);

        txtPesquisarParticipante.setOnQueryTextListener(this);
        txtPesquisarParticipante.setSuggestionsAdapter(searchViewAdapter);

        usuariosRV = findViewById(R.id.ParticipanteRV2);
        usuariosRV.setLayoutManager(new LinearLayoutManager(this));
        usuariosRV.setAdapter(rvAdapter);

        btnEnivarConviteParticipante.setOnClickListener(v -> {
            int index = 0;
            ReuniaoUsuario reuniaoUsuarios[] = new ReuniaoUsuario[usuarios.size()];
            for (Usuario usuario : usuarios) {
                reuniaoUsuarios[index] = new ReuniaoUsuario();
                reuniaoUsuarios[index].setUsuario(usuario);
                reuniaoUsuarios[index].setReuniao(reuniao);
                index++;
            }
            ruc.cadastrar(reuniaoUsuarios);

            for(ReuniaoUsuario reuniaoUsuario : reuniaoUsuarios){
                Usuario rementente = Usuario.getUsuarioLogado();
                Usuario destino = reuniaoUsuario.getUsuario();

                if(destino.isReceiverEmail()){
                    ReuniaoEmail.enviarEmail(rementente, destino, reuniao);
                }

            }

            CadastrarParticipanteActivity.this.finish();
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
        List<Usuario> list = ruc.getUsuariosNotReuniao(reuniao.getId(), login);
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
