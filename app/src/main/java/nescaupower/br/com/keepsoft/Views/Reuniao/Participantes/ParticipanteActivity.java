package nescaupower.br.com.keepsoft.Views.Reuniao.Participantes;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.List;

import nescaupower.br.com.keepsoft.Controller.ReuniaoController;
import nescaupower.br.com.keepsoft.Controller.ReuniaoUsuarioController;
import nescaupower.br.com.keepsoft.Factory.Model.Projeto;
import nescaupower.br.com.keepsoft.Factory.Model.Reuniao;
import nescaupower.br.com.keepsoft.Factory.Model.Usuario;
import nescaupower.br.com.keepsoft.R;

public class ParticipanteActivity extends AppCompatActivity {

    private Projeto projeto;
    private Reuniao reuniao;
    private ReuniaoController rc;
    private ReuniaoUsuarioController ruc;
    private List<Usuario> usuarioList;
    private FloatingActionButton btnCadastrar;
    private RecyclerView rv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_participante);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        rc = new ReuniaoController();
        reuniao = rc.getReuniao(getIntent().getLongExtra("EXTRA_CODIGO_REUNIAO", 0));

        ruc = new ReuniaoUsuarioController();
        projeto = Projeto.getUltimoProjetoUsado();

        usuarioList = ruc.listarPorReuniao(reuniao.getId());

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rv = findViewById(R.id.ParticipanteRV);
        rv.setLayoutManager(layoutManager);
        ParticipanteRVAdapter adapter = new ParticipanteRVAdapter(getApplicationContext(), usuarioList);
        rv.setAdapter(adapter);

        btnCadastrar = findViewById(R.id.btnCadastrarReuniaoParticipante);
        btnCadastrar.setOnClickListener(view -> cadastrar());
    }

    @Override
    public void onResume() {
        super.onResume();
        ParticipanteRVAdapter adapter = (ParticipanteRVAdapter) rv.getAdapter();
        adapter.setUsuarios(usuarioList = ruc.listarPorReuniao(reuniao.getId()));
        adapter.notifyDataSetChanged();
    }

    private void cadastrar() {
        Intent i = new Intent(ParticipanteActivity.this, CadastrarParticipanteActivity.class);
        i.putExtra("EXTRA_CODIGO_REUNIAO", getIntent().getLongExtra("EXTRA_CODIGO_REUNIAO", 0));
        startActivity(i);
    }


}
