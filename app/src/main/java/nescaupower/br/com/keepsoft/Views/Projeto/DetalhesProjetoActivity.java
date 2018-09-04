package nescaupower.br.com.keepsoft.Views.Projeto;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import nescaupower.br.com.keepsoft.Config.Settings;
import nescaupower.br.com.keepsoft.Controller.ProjetoController;
import nescaupower.br.com.keepsoft.Controller.UsuarioController;
import nescaupower.br.com.keepsoft.Factory.Factory;
import nescaupower.br.com.keepsoft.Factory.Model.Projeto;
import nescaupower.br.com.keepsoft.Factory.Model.Usuario;
import nescaupower.br.com.keepsoft.R;
import nescaupower.br.com.keepsoft.Views.Equipe.EquipeFragment;
import nescaupower.br.com.keepsoft.Views.Equipe.dummy.DummyContent;
import nescaupower.br.com.keepsoft.Views.Sprint.SprintFragment;
import nescaupower.br.com.keepsoft.Views.TabAdapter;
import nescaupower.br.com.keepsoft.Views.Tarefa.TarefaFragment;
import nescaupower.br.com.keepsoft.Views.Usuario.CadastroUsuarioActivity;

public class DetalhesProjetoActivity extends AppCompatActivity implements
        SprintFragment.OnListFragmentInteractionListener,
        TarefaFragment.OnListFragmentInteractionListener,
        EquipeFragment.OnListFragmentInteractionListener {

    private TabAdapter adpater;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private Toolbar toolbar;

    private Projeto projeto;
    private Usuario usuario;

    private int indexProjeto;
    private String nomeProjeto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes_projeto);

        //Recuperando "Projeto Logado"
        SharedPreferences sharedPreferences = getSharedPreferences(Settings.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor;
        editor = sharedPreferences.edit();

        //Posição do projeto seleciado na lista de projetos
        indexProjeto = getIntent().getIntExtra("EXTRA_INDEX_PROJETO", -1);

        projeto = new ProjetoController(getApplicationContext()).procurarPorCodigo(getIntent().getLongExtra("EXTRA_CODIGO_PROJETO", 0));
        if (projeto == null) {
            projeto = Projeto.getGetProjeto();
        }

        editor.putBoolean(Settings.PROJETO, true);
        editor.putLong(Settings.ID_PROJETO, projeto.getCodigo());
        editor.commit();

        //Singleton
        Factory.setProjetoLogado(projeto);

        viewPager = findViewById(R.id.fragmentContainer);
        tabLayout = findViewById(R.id.tabLayout);
        adpater = new TabAdapter(getSupportFragmentManager());
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        adpater.addFragment(new SprintFragment(), "Sprint");
        adpater.addFragment(new TarefaFragment(), "Tarefas");
        adpater.addFragment(new EquipeFragment(), "Equipe");

        viewPager.setAdapter(adpater);
        tabLayout.setupWithViewPager(viewPager);

        nomeProjeto = projeto.getNome();
        getSupportActionBar().setTitle(nomeProjeto);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_detalhes_projeto, menu);
        //menu.getItem(0).setOnMenuItemClickListener();
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        Intent intent = null;
        UsuarioController uc;
        uc = new UsuarioController(getApplicationContext());

        //Singleton
        Usuario usuario = Usuario.getUsuarioLogado();
        if (usuario == null || usuario.getLogin().equals("")) {
            SharedPreferences sharedPreferences = getSharedPreferences(Settings.SHARED_PREF_NAME, Context.MODE_PRIVATE);
            usuario = uc.procurarPorLogin(sharedPreferences.getString(Settings.LOGIN, ""));
        }
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            intent = new Intent(DetalhesProjetoActivity.this, CadastroUsuarioActivity.class);
            startActivity(intent);
        }
        if (id == R.id.action_editar) {
            if (projeto.getIdUsuario() == usuario.getId()) {
                intent = new Intent(DetalhesProjetoActivity.this, EditarProjetoActivity.class);
                startActivity(intent);
            } else {
                Toast.makeText(this, "Você não tem permissão", Toast.LENGTH_SHORT).show();
            }
        }
        if (id == R.id.action_excluir) {
            showDialogDigitarSenha();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onListFragmentInteraction(DummyContent.DummyItem item) {

    }

    @Override
    public void onListFragmentInteraction(nescaupower.br.com.keepsoft.Views.Sprint.dummy.DummyContent.DummyItem item) {

    }

    @Override
    public void onListFragmentInteraction(nescaupower.br.com.keepsoft.Views.Tarefa.dummy.DummyContent.DummyItem item) {

    }

    private void showDialogDigitarSenha() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.type_your_password);
        final View dialogView = this.getLayoutInflater().inflate(R.layout.dialog_senha, (ViewGroup) findViewById(R.id.content), false);
        builder.setView(dialogView);

        builder.setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                ProjetoController pc = new ProjetoController(DetalhesProjetoActivity.this);
                EditText txtSenha = dialogView.findViewById(R.id.txtSenha);
                if (txtSenha.getText().toString().equals(Usuario.getUsuarioLogado().getSenha())) {
                    boolean deletou = pc.deletar(projeto);
                    if (deletou) {
                        DetalhesProjetoActivity.this.finish();
                    } else {
                        Toast.makeText(DetalhesProjetoActivity.this, pc.getMensagem(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(DetalhesProjetoActivity.this, "Senha errada", Toast.LENGTH_SHORT).show();
                }
            }
        });

        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(DetalhesProjetoActivity.this, "não", Toast.LENGTH_SHORT).show();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
