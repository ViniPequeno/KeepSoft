package nescaupower.br.com.keepsoft.Views.Reuniao;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.List;

import nescaupower.br.com.keepsoft.Controller.ReuniaoController;
import nescaupower.br.com.keepsoft.Factory.Model.Projeto;
import nescaupower.br.com.keepsoft.Factory.Model.Reuniao;
import nescaupower.br.com.keepsoft.R;

public class ReuniaoActivity extends AppCompatActivity {

    private ReuniaoController rc;
    private Projeto projeto;
    private List<Reuniao> reuniaos;
    private FloatingActionButton btnCadastrar;
    private RecyclerView rv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reuniao);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        rc = new ReuniaoController();
        projeto = Projeto.getUltimoProjetoUsado();

        reuniaos = rc.listarPorProjeto(projeto.getCodigo());
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rv = findViewById(R.id.ReuniaoRV);
        rv.setLayoutManager(layoutManager);

        ReuniaoRVAdapter adapter = new ReuniaoRVAdapter(getApplicationContext(), reuniaos);
        rv.setAdapter(adapter);

        btnCadastrar = findViewById(R.id.btnCadastrarReuniao);
        btnCadastrar.setOnClickListener(view -> cadastrar());
    }

    @Override
    public void onResume() {
        super.onResume();
        ReuniaoRVAdapter adapter = (ReuniaoRVAdapter) rv.getAdapter();
        adapter.setReuniaoList(reuniaos = rc.listarPorProjeto(projeto.getCodigo()));
        adapter.notifyDataSetChanged();
    }

    private void cadastrar() {
        Intent i = new Intent(ReuniaoActivity.this, CadastrarReuniao.class);
        startActivity(i);
    }
}
