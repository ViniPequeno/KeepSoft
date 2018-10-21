package nescaupower.br.com.keepsoft.Views.Status;

import android.content.Intent;
import android.content.res.Resources;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.List;

import javax.annotation.Nullable;

import nescaupower.br.com.keepsoft.Controller.StatusController;
import nescaupower.br.com.keepsoft.Factory.Model.Projeto;
import nescaupower.br.com.keepsoft.Factory.Model.Status;
import nescaupower.br.com.keepsoft.Factory.Model.Usuario;
import nescaupower.br.com.keepsoft.R;
import nescaupower.br.com.keepsoft.Views.Projeto.ProjetoRVAdapter;

public class StatusActivity extends AppCompatActivity {

    private StatusController sc;
    private Projeto projeto;


    private List<Status> listStatus;
    private FloatingActionButton btnCadastrar;
    private RecyclerView rv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status);

        sc = new StatusController();
        projeto = Projeto.getUltimoProjetoUsado();


        listStatus = sc.getAllFindByProjeto(projeto.getCodigo());


        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        rv = findViewById(R.id.StatusRV);
        rv.setLayoutManager(layoutManager);
        StatusRVAdapter adapter = new StatusRVAdapter(getApplicationContext(), listStatus);
        rv.setAdapter(adapter);

        btnCadastrar = findViewById(R.id.btnCadastrarStatus);
        btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cadastrar();
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
        StatusRVAdapter adapter = (StatusRVAdapter) rv.getAdapter();
        adapter.setStatuses(listStatus = sc.getAllFindByProjeto(projeto.getCodigo()));
        adapter.notifyDataSetChanged();
    }

    private void cadastrar() {
        Intent i = new Intent(StatusActivity.this, CadastrarStatus.class);
        startActivity(i);
    }




}
