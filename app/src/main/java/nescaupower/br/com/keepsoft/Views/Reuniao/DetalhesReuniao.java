package nescaupower.br.com.keepsoft.Views.Reuniao;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;

import nescaupower.br.com.keepsoft.Config.Settings;
import nescaupower.br.com.keepsoft.Controller.PerfilController;
import nescaupower.br.com.keepsoft.Controller.ReuniaoController;
import nescaupower.br.com.keepsoft.Controller.UsuarioController;
import nescaupower.br.com.keepsoft.Enum.Perfil;
import nescaupower.br.com.keepsoft.Factory.Model.Projeto;
import nescaupower.br.com.keepsoft.Factory.Model.Reuniao;
import nescaupower.br.com.keepsoft.Factory.Model.Usuario;
import nescaupower.br.com.keepsoft.R;
import nescaupower.br.com.keepsoft.Views.Reuniao.Participantes.ParticipanteActivity;
import nescaupower.br.com.keepsoft.Views.Status.DetalhesStatus;
import nescaupower.br.com.keepsoft.Views.Status.EditarStatus;

public class DetalhesReuniao extends AppCompatActivity {

    private EditText txtReuniaoTituloDetalhes;
    private EditText txtReuniaoAssuntoDetalhes;
    private EditText txtReuniaoDataInicioDetalhes;
    private EditText txtReuniaoHoraInicioDetalhes;
    private EditText txtReuniaoHoraFimDetalhes;
    private EditText txtReuniaoLocalDetalhes;
    private ReuniaoController rc;
    private Reuniao reuniao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes_reuniao);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        rc = new ReuniaoController();
        reuniao = rc.getReuniao(getIntent().getLongExtra("EXTRA_CODIGO_REUNIAO", 0));

        Reuniao.setUltimoReuniao(reuniao);
        txtReuniaoTituloDetalhes = findViewById(R.id.txtNomeReuniaoDetalhes);
        txtReuniaoAssuntoDetalhes = findViewById(R.id.txtAssuntoReuniaoDetalhes);
        txtReuniaoLocalDetalhes = findViewById(R.id.txtLocalReuniaoDetalhes);

        //Datas
        txtReuniaoDataInicioDetalhes = findViewById(R.id.txtReuniaoDataInicioDetalhes);

        txtReuniaoHoraInicioDetalhes = findViewById(R.id.txtReuniaoHoraInicioDetalhes);
        txtReuniaoHoraFimDetalhes = findViewById(R.id.txtReuniaoHoraFimDetalhes);

        if (reuniao != null) {
            txtReuniaoTituloDetalhes.setText(reuniao.getNome());
            txtReuniaoAssuntoDetalhes.setText(reuniao.getAssunto());
            txtReuniaoLocalDetalhes.setText(reuniao.getLocal());
            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
            txtReuniaoDataInicioDetalhes.setText(format.format(reuniao.getDataInicio()));
            txtReuniaoHoraInicioDetalhes.setText(reuniao.getHoraInicioFormat());
            txtReuniaoHoraFimDetalhes.setText(reuniao.getHoraFimFormat());
        }
    }

    public void adicionarParticipante(View view) {
        Intent intent;
        intent = new Intent(DetalhesReuniao.this, ParticipanteActivity.class);
        intent.putExtra("EXTRA_CODIGO_REUNIAO", getIntent().getLongExtra("EXTRA_CODIGO_REUNIAO", 0));
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_detalhes_reuniao, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
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

        Projeto projeto = reuniao.getProjeto();
        //noinspection SimplifiableIfStatement
        PerfilController pc = new PerfilController();
        nescaupower.br.com.keepsoft.Factory.Model.Perfil perfil = pc.procurarPorProjetoUsuario(reuniao.getProjeto().getCodigo(), usuario.getId());
        if (id == R.id.action_editar_reuniao) {
            if (perfil.getPerfil() == Perfil.SCRUM_MASTER) {
                intent = new Intent(DetalhesReuniao.this, EditarReuniaoActivity.class);
                intent.putExtra("EXTRA_CODIGO_REUNIAO", getIntent().getLongExtra("EXTRA_CODIGO_REUNIAO", 0));
                startActivity(intent);
            } else {
                Toast.makeText(this, "Você não tem permissão", Toast.LENGTH_SHORT).show();
            }
        }
        if (id == R.id.action_excluir_reuniao) {
            if (perfil.getPerfil() == Perfil.SCRUM_MASTER) {
                showDialogDigitarSenha();
            }else{
                Toast.makeText(this, "Você não tem permissão", Toast.LENGTH_SHORT).show();
            }
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
                rc.deletar(reuniao);

                Toast.makeText(DetalhesReuniao.this, rc.getMensagem(), Toast.LENGTH_SHORT).show();
                DetalhesReuniao.this.finish();
            } else {
                Toast.makeText(DetalhesReuniao.this, "Senha errada", Toast.LENGTH_SHORT).show();
            }
        });

        builder.setNegativeButton(R.string.cancel, (dialogInterface, i) -> Toast.makeText(DetalhesReuniao.this, "não", Toast.LENGTH_SHORT).show());

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
