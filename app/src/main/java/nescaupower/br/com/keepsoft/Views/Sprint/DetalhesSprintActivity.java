package nescaupower.br.com.keepsoft.Views.Sprint;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import nescaupower.br.com.keepsoft.Config.Settings;
import nescaupower.br.com.keepsoft.Controller.SprintController;
import nescaupower.br.com.keepsoft.Controller.UsuarioController;
import nescaupower.br.com.keepsoft.Enum.Perfil;
import nescaupower.br.com.keepsoft.Factory.Model.Sprint;
import nescaupower.br.com.keepsoft.Factory.Model.Usuario;
import nescaupower.br.com.keepsoft.R;

public class DetalhesSprintActivity extends AppCompatActivity {

    private Usuario usuario;
    private Sprint sprint;

    private EditText txtTituloDetalhes, txtDescricaoDetalhes, txtDataInicioDetalhes, txtDataFimDetalhes;

    private UsuarioController uc;

    private SprintController sc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes_sprint);


        uc = new UsuarioController();
        sc = new SprintController();

        //Singleton
        usuario = Usuario.getUsuarioLogado();
        if (usuario == null || usuario.getLogin().equals("")) {
            SharedPreferences sharedPreferences = getSharedPreferences(Settings.SHARED_PREF_NAME, Context.MODE_PRIVATE);
            usuario = uc.procurarPorLogin(sharedPreferences.getString(Settings.LOGIN, ""));
        }

        sprint = new SprintController().procurarPorCodigo(getIntent().getLongExtra("EXTRA_CODIGO_SPRINT", 0));
        if (sprint == null) {
            this.finish();
        }

        txtTituloDetalhes = findViewById(R.id.txtTituloDetalhes);
        txtDescricaoDetalhes = findViewById(R.id.txtDescricaoDetalhes);
        txtDataInicioDetalhes = findViewById(R.id.txtDataInicioDetalhes);
        txtDataFimDetalhes = findViewById(R.id.txtDataFimDetalhes);

        txtTituloDetalhes.setText(sprint.getTitulo());
        txtDescricaoDetalhes.setText(sprint.getDescricao());
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        txtDataInicioDetalhes.setText(dateFormat.format(sprint.getDataInicio()));
        txtDataFimDetalhes.setText(dateFormat.format(sprint.getDataFim()));


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_detalhes_sprint, menu);
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

        if (id == R.id.action_editar_sprint) {
            nescaupower.br.com.keepsoft.Factory.Model.Perfil  perfil = sc.findPerfilSprintUsuario(sprint, usuario);
            if(perfil != null) {
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
        if (id == R.id.action_excluir_sprint) {
            showDialogDigitarSenha();
        }
        return super.onOptionsItemSelected(item);
    }

    private void showDialogDigitarSenha() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.type_your_password);
        final View dialogView = this.getLayoutInflater().inflate(R.layout.dialog_senha, (ViewGroup) findViewById(R.id.content), false);
        builder.setView(dialogView);

        builder.setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
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
            }
        });

        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(DetalhesSprintActivity.this, "não", Toast.LENGTH_SHORT).show();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
