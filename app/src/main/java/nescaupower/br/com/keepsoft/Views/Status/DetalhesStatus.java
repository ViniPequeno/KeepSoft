package nescaupower.br.com.keepsoft.Views.Status;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import nescaupower.br.com.keepsoft.Config.Settings;
import nescaupower.br.com.keepsoft.Controller.StatusController;
import nescaupower.br.com.keepsoft.Controller.UsuarioController;
import nescaupower.br.com.keepsoft.Factory.Model.Projeto;
import nescaupower.br.com.keepsoft.Factory.Model.Status;
import nescaupower.br.com.keepsoft.Factory.Model.Usuario;
import nescaupower.br.com.keepsoft.R;

public class DetalhesStatus extends AppCompatActivity {

    private EditText txtNomeStatusDetalhes;
    private EditText txtDescricaoStatusDetalhes;

    private StatusController sc;
    private Status status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes_status);

        sc = new StatusController();
        txtNomeStatusDetalhes = findViewById(R.id.txtNomeStatusDetalhes);
        txtDescricaoStatusDetalhes = findViewById(R.id.txtDescricaoStatusDetalhes);

        status = sc.findByID(getIntent().getLongExtra("EXTRA_CODIGO_STATUS", 0));
        if (status == null) {
            //
        }

        txtNomeStatusDetalhes.setText(status.getNome());
        txtDescricaoStatusDetalhes.setText(status.getDescricao());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_detalhes_status, menu);
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

        Projeto projeto = status.getProjeto();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_editar_status) {
            if (projeto.getUsuarioAdm().getId().equals(usuario.getId())) {
                intent = new Intent(DetalhesStatus.this, EditarStatus.class);
                intent.putExtra("EXTRA_CODIGO_STATUS", getIntent().getLongExtra("EXTRA_CODIGO_STATUS", 0));
                startActivity(intent);
            } else {
                Toast.makeText(this, "Você não tem permissão", Toast.LENGTH_SHORT).show();
            }
        }
        if (id == R.id.action_excluir_status) {
            showDialogDigitarSenha();
        }
        return super.onOptionsItemSelected(item);
    }


    private void showDialogDigitarSenha() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.type_your_password);
        final View dialogView = this.getLayoutInflater().inflate(R.layout.dialog_senha, findViewById(R.id.content), false);
        builder.setView(dialogView);

        builder.setPositiveButton(R.string.confirm, (dialogInterface, i) -> {
            EditText txtSenha = dialogView.findViewById(R.id.txtSenha);
            UsuarioController uc = new UsuarioController();
            //Singleton
            Usuario usuario = Usuario.getUsuarioLogado();
            if (usuario == null || usuario.getLogin().equals("")) {
                SharedPreferences sharedPreferences = getSharedPreferences(Settings.SHARED_PREF_NAME, Context.MODE_PRIVATE);
                usuario = uc.procurarPorLogin(sharedPreferences.getString(Settings.LOGIN, ""));
            }
            if (uc.realizarLogin(usuario.getLogin(), txtSenha.getText().toString()) != null) {
                sc.delete(status);

                Toast.makeText(DetalhesStatus.this, "Deletado!", Toast.LENGTH_SHORT).show();
                    DetalhesStatus.this.finish();
            } else {
                Toast.makeText(DetalhesStatus.this, "Senha errada", Toast.LENGTH_SHORT).show();
            }
        });

        builder.setNegativeButton(R.string.cancel, (dialogInterface, i) -> Toast.makeText(DetalhesStatus.this, "não", Toast.LENGTH_SHORT).show());

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
