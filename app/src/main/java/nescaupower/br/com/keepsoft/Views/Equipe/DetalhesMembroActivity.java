package nescaupower.br.com.keepsoft.Views.Equipe;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import nescaupower.br.com.keepsoft.Controller.ConviteController;
import nescaupower.br.com.keepsoft.Controller.PerfilController;
import nescaupower.br.com.keepsoft.Controller.UsuarioController;
import nescaupower.br.com.keepsoft.Factory.Model.Perfil;
import nescaupower.br.com.keepsoft.Factory.Model.Usuario;
import nescaupower.br.com.keepsoft.R;

public class DetalhesMembroActivity extends AppCompatActivity {

    private TextView lblLogin, lblNome, lblFuncao, lblEmail, lblTelefone;
    private Button btnAlterarFuncao, btnDelete;
    private UsuarioController uc;
    private PerfilController pc;
    private Usuario usuario;
    private Perfil perfil;

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

        lblLogin.setText(usuario.getLogin());
        lblNome.setText(usuario.getNome());
        lblEmail.setText(usuario.getEmail());
        lblTelefone.setText(usuario.getTelefone());
        lblFuncao.setText(perfil.getPerfil().toString());

        btnAlterarFuncao.setOnClickListener(view -> {

        });

        btnDelete.setOnClickListener(view -> {
            //Se tentou remover um usuário diferente dele mesmo
            if (perfil.getUsuario().getId() != Usuario.getUsuarioLogado().getId()) {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Aviso!");
                builder.setMessage("Deseja realmente remover " + usuario.getLogin() + " deste projeto?");
                builder.setPositiveButton(R.string.confirm, (dialogInterface, i) -> {
                    //Apagar convite da lista
                    //perfis.remove(holder.getAdapterPosition());
                    //notifyItemRemoved(holder.getAdapterPosition());
                    Log.e("seila123", "oi+ "+perfil.getDataInicioFormat()+"+oi");
                    //Apagar o convite do banco
                    if (!perfil.getDataInicioFormat().equals(" ")) {
                        ConviteController cc = new ConviteController();
                        cc.deletar(cc.procurarPorID(perfil.getUsuario().getId(), perfil.getProjeto().getCodigo()));
                    }

                    //Apagar perfil do banco
                    PerfilController pc = new PerfilController();
                    pc.deletar(perfil);
                    finish();
                });
                builder.setNegativeButton(R.string.cancel, null);
                builder.show();
            }
        });
    }

}
