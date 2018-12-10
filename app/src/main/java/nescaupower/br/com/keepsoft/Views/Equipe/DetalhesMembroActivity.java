package nescaupower.br.com.keepsoft.Views.Equipe;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import de.hdodenhof.circleimageview.CircleImageView;
import nescaupower.br.com.keepsoft.AsyncTasks.GetImageAsyncTask;
import nescaupower.br.com.keepsoft.Config.Settings;
import nescaupower.br.com.keepsoft.Controller.ConviteController;
import nescaupower.br.com.keepsoft.Controller.PerfilController;
import nescaupower.br.com.keepsoft.Controller.UsuarioController;
import nescaupower.br.com.keepsoft.Factory.Model.Convite;
import nescaupower.br.com.keepsoft.Factory.Model.Perfil;
import nescaupower.br.com.keepsoft.Factory.Model.Usuario;
import nescaupower.br.com.keepsoft.R;

public class DetalhesMembroActivity extends AppCompatActivity {

    private TextView lblLogin, lblNome, lblFuncao, lblEmail, lblTelefone;
    private Button btnAlterarFuncao, btnDelete;
    private CircleImageView imgPerfil;
    private UsuarioController uc;
    private PerfilController pc;
    private Usuario usuario;
    private Perfil perfil;
    private int funcaoIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes_membro);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        long idUsuario = intent.getLongExtra("perfilIdUsuario", 0);
        long idPerfil = intent.getLongExtra("perfilId", 0);
        intent.removeExtra("perfilIdUsuario");
        intent.removeExtra("perfilId");

        uc = new UsuarioController();
        pc = new PerfilController();

        usuario = uc.procurarPorID(idUsuario);
        perfil = pc.procurarPorId(idPerfil);

        lblLogin = findViewById(R.id.lblLogin);
        lblNome = findViewById(R.id.lblNome);
        lblFuncao = findViewById(R.id.lblFuncao);
        lblEmail = findViewById(R.id.lblEmail);
        lblTelefone = findViewById(R.id.lblTelefone);
        btnAlterarFuncao = findViewById(R.id.btnAlterarFuncao);
        btnDelete = findViewById(R.id.btnDelete);
        imgPerfil = findViewById(R.id.imgPerfil);

        setLabelsContent();

        //Singleton
        Usuario usuarioLogado = Usuario.getUsuarioLogado();
        if (usuarioLogado == null || usuarioLogado.getLogin().equals("")) {
            SharedPreferences sharedPreferences = getSharedPreferences(Settings.SHARED_PREF_NAME, Context.MODE_PRIVATE);
            usuarioLogado = uc.procurarPorLogin(sharedPreferences.getString(Settings.LOGIN, ""));
        }

        Perfil perfilLogado = pc.procurarPorProjetoUsuario(perfil.getProjeto().getCodigo(), usuarioLogado.getId());

        // SE não for SCRUM MASTER -> pode sair do proejto apenas
        if (!perfilLogado.getPerfil().equals(nescaupower.br.com.keepsoft.Enum.Perfil.SCRUM_MASTER)) {
            btnDelete.setText(R.string.leave);

            //Se for usuário diferente dele mesmo, não verá botão de Sair/Remover Membro
            if (!perfilLogado.getUsuario().getId().equals(Usuario.getUsuarioLogado().getId())) {
                btnDelete.setVisibility(View.INVISIBLE);
            } else {//Senão, pode ver botão de sair do projeto
                btnDelete.setOnClickListener(view -> sairDoProjeto());
            }
        } else {
            // SENÂO Pode remover usuários/cancelar covites , sair do projeto
            // e pode funções de membros

            //Se tentou clicou num usuário diferente dele mesmo
            if (!perfil.getUsuario().getId().equals(usuarioLogado.getId())) {
                ConviteController cc = new ConviteController();
                Convite convite = cc.procurarPorID(perfil.getUsuario().getId(), perfil.getProjeto().getCodigo());
                if (convite != null) {
                    btnDelete.setText(R.string.cancel_invitation);
                } else {
                    btnDelete.setText(R.string.remove_member);
                }

                //Remover Membro
                btnDelete.setOnClickListener(view -> {
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle(R.string.warning);
                    String msg = getString(R.string.remove_member_confirm, usuario.getLogin());
                    builder.setMessage(msg);
                    builder.setPositiveButton(R.string.confirm, (dialogInterface, i) -> {

                        //Apagar o convite do banco se houver
                        Log.e("Perfil1", "m"+perfil.getDataInicioFormat()+"p");
                        if (perfil.getDataInicioFormat().equals("")) {
                            cc.deletar(convite);
                        }

                        //Apagar perfil do banco
                        PerfilController pc = new PerfilController();
                        pc.deletar(perfil);
                        finish();
                    });
                    builder.setNegativeButton(R.string.cancel, null);
                    builder.show();

                });


                //Altera função de membro
                btnAlterarFuncao.setOnClickListener(view -> {
                    alterarFuncaoDeMembro();
                });
            } else { //SENÂO, pode sair do projeto e alterar própria função
                btnDelete.setText(R.string.leave);
                btnDelete.setOnClickListener(view -> sairDoProjeto());

                btnAlterarFuncao.setOnClickListener(view -> {
                    alterarFuncaoDeMembro();
                });
            }
        }
    }

    private void alterarFuncaoDeMembro() {
        AlertDialog.Builder alertFuncao = new AlertDialog.Builder(this);

        final View dialogView = this.getLayoutInflater().inflate(R.layout.dialog_alterar_funcao, findViewById(R.id.content), false);
        alertFuncao.setView(dialogView);
        nescaupower.br.com.keepsoft.Enum.Perfil funcaoAntiga = perfil.getPerfil();

        alertFuncao.setTitle("Definir nova função");
        alertFuncao.setMessage("Alterar função do usuário " + usuario.getLogin() + ":");
        Spinner spinFuncao = dialogView.findViewById(R.id.spinFuncao);
        spinFuncao.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                funcaoIndex = i;
                switch (funcaoIndex) {
                    case 0:
                        perfil.setPerfil(nescaupower.br.com.keepsoft.Enum.Perfil.TEAM);
                        break;
                    case 1:
                        perfil.setPerfil(nescaupower.br.com.keepsoft.Enum.Perfil.SCRUM_MASTER);
                        break;
                    case 2:
                        perfil.setPerfil(nescaupower.br.com.keepsoft.Enum.Perfil.PRODUCT_OWNER);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        alertFuncao.setPositiveButton(R.string.confirm, (dialogInterface, i) -> {
            if (funcaoAntiga != perfil.getPerfil()) {
                //Alterar função do perfil do usuário selecionado
                PerfilController pc = new PerfilController();
                pc.atualizar(perfil);
                lblFuncao.setText(perfil.getPerfil().toString());
            }
        });
        alertFuncao.setNegativeButton(R.string.cancel, null);
        alertFuncao.show();
    }

    private void sairDoProjeto() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.warning);
        builder.setMessage(R.string.leave_project_confirm);
        builder.setPositiveButton(R.string.confirm, (dialogInterface, i) -> {
            //Apagar perfil do banco
            PerfilController pc = new PerfilController();
            pc.deletar(perfil);
            finish();
        });
        builder.setNegativeButton(R.string.cancel, null);
        builder.show();
    }

    private void setLabelsContent() {
        lblLogin.setText(usuario.getLogin());
        lblNome.setText(usuario.getNome());
        lblEmail.setText(usuario.getEmail());
        lblTelefone.setText(usuario.getTelefone());
        lblFuncao.setText(perfil.getPerfil().toString());
        new GetImageAsyncTask(imgPerfil).execute(Settings.URL + "/usuarios/imagem/" + usuario.getId());
    }
}
