package nescaupower.br.com.keepsoft.Views.Projeto;

import android.app.AlertDialog;
import android.content.Context;
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
import android.widget.EditText;
import android.widget.Toast;

import nescaupower.br.com.keepsoft.Config.Settings;
import nescaupower.br.com.keepsoft.Controller.PerfilController;
import nescaupower.br.com.keepsoft.Controller.ProjetoController;
import nescaupower.br.com.keepsoft.Controller.SprintController;
import nescaupower.br.com.keepsoft.Controller.UsuarioController;
import nescaupower.br.com.keepsoft.Factory.Factory;
import nescaupower.br.com.keepsoft.Factory.Model.Perfil;
import nescaupower.br.com.keepsoft.Factory.Model.Projeto;
import nescaupower.br.com.keepsoft.Factory.Model.Sprint;
import nescaupower.br.com.keepsoft.Factory.Model.Tarefa;
import nescaupower.br.com.keepsoft.Factory.Model.Usuario;
import nescaupower.br.com.keepsoft.R;
import nescaupower.br.com.keepsoft.Views.Equipe.DetalhesMembroActivity;
import nescaupower.br.com.keepsoft.Views.Equipe.EquipeFragment;
import nescaupower.br.com.keepsoft.Views.Reuniao.ReuniaoActivity;
import nescaupower.br.com.keepsoft.Views.Sprint.DetalhesSprintActivity;
import nescaupower.br.com.keepsoft.Views.Sprint.SprintFragment;
import nescaupower.br.com.keepsoft.Views.Status.StatusActivity;
import nescaupower.br.com.keepsoft.Views.TabAdapter;
import nescaupower.br.com.keepsoft.Views.Tarefa.TarefaFragment;

public class DetalhesProjetoActivity extends AppCompatActivity implements
        SprintFragment.OnListFragmentInteractionListener,
        TarefaFragment.OnListFragmentInteractionListener,
        EquipeFragment.OnListFragmentInteractionListener {

    private TabAdapter tabAdapter;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private Toolbar toolbar;

    private Projeto projeto;
    private Perfil perfil;

    private String nomeProjeto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes_projeto);

        //Recuperando "Projeto Logado"
        SharedPreferences sharedPreferences = getSharedPreferences(Settings.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor;
        editor = sharedPreferences.edit();

        ProjetoController pc = new ProjetoController();
        projeto = pc.procurarPorCodigo(getIntent().getLongExtra("EXTRA_CODIGO_PROJETO", 0));
        if (projeto == null) {
            projeto = Projeto.getUltimoProjetoUsado();
        }

        editor.putBoolean(Settings.PROJETO, true);
        editor.putLong(Settings.ID_PROJETO, projeto.getCodigo());
        editor.apply();

        //Singleton
        Factory.setProjetoLogado(projeto);

        nomeProjeto = projeto.getNome();

        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(nomeProjeto);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.fragmentContainer);

        tabAdapter = new TabAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(tabAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        perfil = new PerfilController().procurarPorProjetoUsuario(projeto.getCodigo(), Usuario.getUsuarioLogado().getId());

        SprintController sc = new SprintController();
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
        Intent intent;
        UsuarioController uc;
        uc = new UsuarioController();

        //Singleton
        Usuario usuario = Usuario.getUsuarioLogado();
        if (usuario == null || usuario.getLogin().equals("")) {
            SharedPreferences sharedPreferences = getSharedPreferences(Settings.SHARED_PREF_NAME, Context.MODE_PRIVATE);
            usuario = uc.procurarPorLogin(sharedPreferences.getString(Settings.LOGIN, ""));
        }
        //perfil = new PerfilController().procurarPorProjetoUsuario(projeto.getCodigo(), Usuario.getUsuarioLogado().getId());
        //Toast.makeText(this, perfil.getPerfil().toString(), Toast.LENGTH_SHORT).show();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_editar) {
            if (projeto.getUsuarioAdm().getId().equals(usuario.getId())) {
                intent = new Intent(DetalhesProjetoActivity.this, EditarProjetoActivity.class);
                startActivity(intent);
            } else {
                Toast.makeText(this, "Você não tem permissão", Toast.LENGTH_SHORT).show();
            }
        }
        if (id == R.id.action_excluir) {
            showDialogDigitarSenha();
        }
        if(id == R.id.action_status){
            if (projeto.getUsuarioAdm().getId().equals(usuario.getId())) {
                intent = new Intent(DetalhesProjetoActivity.this, StatusActivity.class);
                startActivity(intent);
            } else {
                Toast.makeText(this, "Você não tem permissão", Toast.LENGTH_SHORT).show();
            }
        }
        if(id == R.id.action_reuniao){
            if(perfil.getPerfil() == nescaupower.br.com.keepsoft.Enum.Perfil.SCRUM_MASTER){
                intent = new Intent(DetalhesProjetoActivity.this, ReuniaoActivity.class);
                startActivity(intent);
            }else{
                Toast.makeText(this, "Você não tem permissão", Toast.LENGTH_SHORT).show();
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onListFragmentInteraction(Perfil perfil) {
        Intent i = new Intent(DetalhesProjetoActivity.this, DetalhesMembroActivity.class);
        i.putExtra("perfilIdUsuario", perfil.getUsuario().getId());
        i.putExtra("perfilId", perfil.getId());
        startActivity(i);
    }

    @Override
    public void onListFragmentInteraction(Sprint sprint) {
        Intent i = new Intent(DetalhesProjetoActivity.this, DetalhesSprintActivity.class);
        i.putExtra("EXTRA_CODIGO_SPRINT", sprint.getId());
        startActivity(i);
    }

    @Override
    public void onListFragmentInteraction(Tarefa tarefa) {
        /*Intent i = new Intent(DetalhesProjetoActivity.this, DetalhesMembroActivity.class);
        i.putExtra("tarefaId", tarefa.getId());
        startActivity(i);*/
    }

    private void showDialogDigitarSenha() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.type_your_password);
        final View dialogView = this.getLayoutInflater().inflate(R.layout.dialog_senha, findViewById(R.id.content), false);
        builder.setView(dialogView);

        builder.setPositiveButton(R.string.confirm, (dialogInterface, i) -> {
            ProjetoController pc = new ProjetoController();
            EditText txtSenha = dialogView.findViewById(R.id.txtSenha);
            UsuarioController uc = new UsuarioController();
            //Singleton
            Usuario usuario = Usuario.getUsuarioLogado();
            if (usuario == null || usuario.getLogin().equals("")) {
                SharedPreferences sharedPreferences = getSharedPreferences(Settings.SHARED_PREF_NAME, Context.MODE_PRIVATE);
                usuario = uc.procurarPorLogin(sharedPreferences.getString(Settings.LOGIN, ""));
            }
            if (uc.realizarLogin(usuario.getLogin(), txtSenha.getText().toString()) != null) {
                boolean deletou = pc.deletar(projeto);

                Toast.makeText(DetalhesProjetoActivity.this, pc.getMensagem(), Toast.LENGTH_SHORT).show();
                if (deletou) {
                    DetalhesProjetoActivity.this.finish();
                }
            } else {
                Toast.makeText(DetalhesProjetoActivity.this, "Senha errada", Toast.LENGTH_SHORT).show();
            }
        });

        builder.setNegativeButton(R.string.cancel, (dialogInterface, i) -> Toast.makeText(DetalhesProjetoActivity.this, "não", Toast.LENGTH_SHORT).show());

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
