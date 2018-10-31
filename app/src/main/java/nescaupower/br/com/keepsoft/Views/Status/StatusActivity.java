package nescaupower.br.com.keepsoft.Views.Status;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import java.util.List;

import nescaupower.br.com.keepsoft.Controller.StatusController;
import nescaupower.br.com.keepsoft.Factory.Model.Projeto;
import nescaupower.br.com.keepsoft.Factory.Model.Status;
import nescaupower.br.com.keepsoft.R;

public class StatusActivity extends AppCompatActivity {

    private StatusController sc;
    private Projeto projeto;


    private List<Status> listStatus;
    private FloatingActionButton btnCadastrar;
    private RecyclerView rv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status_list);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        sc = new StatusController();
        projeto = Projeto.getUltimoProjetoUsado();

        listStatus = sc.getAllFindByProjeto(projeto.getCodigo());

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rv = findViewById(R.id.StatusRV);
        rv.setLayoutManager(layoutManager);
        StatusRVAdapter adapter = new StatusRVAdapter(getApplicationContext(), listStatus);
        rv.setAdapter(adapter);

        btnCadastrar = findViewById(R.id.btnCadastrarStatus);
        btnCadastrar.setOnClickListener(view -> cadastrar());

    }

    @Override
    public void onResume() {
        super.onResume();
        StatusRVAdapter adapter = (StatusRVAdapter) rv.getAdapter();
        adapter.setStatusList(listStatus = sc.getAllFindByProjeto(projeto.getCodigo()));
        adapter.notifyDataSetChanged();
    }

    private void cadastrar() {
        Intent i = new Intent(StatusActivity.this, CadastrarStatus.class);
        startActivity(i);
    }




}
