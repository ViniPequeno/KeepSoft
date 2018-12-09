package nescaupower.br.com.keepsoft.Views.Sprint;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import nescaupower.br.com.keepsoft.Config.Settings;
import nescaupower.br.com.keepsoft.Controller.SprintController;
import nescaupower.br.com.keepsoft.Controller.TarefaController;
import nescaupower.br.com.keepsoft.Controller.UsuarioController;
import nescaupower.br.com.keepsoft.Enum.Perfil;
import nescaupower.br.com.keepsoft.Factory.Model.Sprint;
import nescaupower.br.com.keepsoft.Factory.Model.Tarefa;
import nescaupower.br.com.keepsoft.Factory.Model.Usuario;
import nescaupower.br.com.keepsoft.R;
import nescaupower.br.com.keepsoft.Views.Tarefa.TarefaFragment;
import nescaupower.br.com.keepsoft.Views.Tarefa.TarefaRVAdapter;

public class DetalhesSprintActivity extends AppCompatActivity implements TarefaFragment.OnListFragmentInteractionListener {

    private Usuario usuario;
    private Sprint sprint;

    private TextView lblTitulo, lblDescricao, lblDataInicio, lblDataFim;
    private RecyclerView tarefasRV;

    private UsuarioController uc;
    private SprintController sc;
    private TarefaController tc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes_sprint);

        uc = new UsuarioController();
        sc = new SprintController();
        tc = new TarefaController();

        //Singleton
        usuario = Usuario.getUsuarioLogado();
        if (usuario == null || usuario.getLogin().equals("")) {
            SharedPreferences sharedPreferences = getSharedPreferences(Settings.SHARED_PREF_NAME, Context.MODE_PRIVATE);
            usuario = uc.procurarPorLogin(sharedPreferences.getString(Settings.LOGIN, ""));
        }

        sprint = sc.procurarPorCodigo(getIntent().getLongExtra("EXTRA_CODIGO_SPRINT", 0));
        if (sprint == null) {
            this.finish();
        }

        lblTitulo = findViewById(R.id.lblTituloDetalhes);
        lblDescricao = findViewById(R.id.lblDescricaoDetalhes);
        lblDataInicio = findViewById(R.id.lblDataInicioDetalhes);
        lblDataFim = findViewById(R.id.lblDataFimDetalhes);
        tarefasRV = findViewById(R.id.TarefasRV);

        lblTitulo.setText(sprint.getTitulo());
        lblDescricao.setText(sprint.getDescricao());
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        lblDataInicio.setText(dateFormat.format(sprint.getDataInicio()));
        lblDataFim.setText(dateFormat.format(sprint.getDataFim()));

        tarefasRV.setLayoutManager(new LinearLayoutManager(this));
        List<Tarefa> tarefas = tc.listarPorSprint(sprint.getId());
        TarefaRVAdapter adapter = new TarefaRVAdapter(this, tarefas, this);
        tarefasRV.setAdapter(adapter);

        getSupportActionBar().setTitle(sprint.getTitulo());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_detalhes_sprint, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id) {
            case (R.id.action_editar_sprint):
                telaEditar();
                break;
            case (R.id.action_excluir_sprint):
                showDialogDigitarSenha();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void telaEditar() {
        Intent intent;
        nescaupower.br.com.keepsoft.Factory.Model.Perfil perfil = sc.findPerfilSprintUsuario(sprint, usuario);
        if (perfil != null) {
            if (sc.findPerfilSprintUsuario(sprint, usuario).getPerfil() == Perfil.PRODUCT_OWNER ||
                    sc.findPerfilSprintUsuario(sprint, usuario).getPerfil() == Perfil.SCRUM_MASTER) {
                intent = new Intent(DetalhesSprintActivity.this, EditarSprint.class);

                intent.putExtra("EXTRA_CODIGO_SPRINT", getIntent().getLongExtra("EXTRA_CODIGO_SPRINT", 0));
                startActivity(intent);
            } else {
                Toast.makeText(this, "Você não tem permissão", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void showDialogDigitarSenha() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.type_your_password);
        final View dialogView = this.getLayoutInflater().inflate(R.layout.dialog_senha, findViewById(R.id.content), false);
        builder.setView(dialogView);

        builder.setPositiveButton(R.string.confirm, (dialogInterface, i) -> {
            EditText txtSenha = dialogView.findViewById(R.id.txtSenha);
            //Singleton
            usuario = Usuario.getUsuarioLogado();
            if (usuario == null || usuario.getLogin().equals("")) {
                SharedPreferences sharedPreferences = getSharedPreferences(Settings.SHARED_PREF_NAME, Context.MODE_PRIVATE);
                usuario = uc.procurarPorLogin(sharedPreferences.getString(Settings.LOGIN, ""));
            }

            if (uc.realizarLogin(usuario.getLogin(), txtSenha.getText().toString()) != null) {
                boolean deletou = sc.deletar(sprint);
                if (deletou) {
                    DetalhesSprintActivity.this.finish();
                } else {
                    Toast.makeText(DetalhesSprintActivity.this, sc.getMensagem(), Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(DetalhesSprintActivity.this, "Senha errada", Toast.LENGTH_SHORT).show();
            }
        });

        builder.setNegativeButton(R.string.cancel, (dialogInterface, i) -> Toast.makeText(DetalhesSprintActivity.this, "não", Toast.LENGTH_SHORT).show());

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    public void onListFragmentInteraction(Tarefa item) {

    }
}
