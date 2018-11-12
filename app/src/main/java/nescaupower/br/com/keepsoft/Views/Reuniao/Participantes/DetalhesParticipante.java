package nescaupower.br.com.keepsoft.Views.Reuniao.Participantes;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import de.hdodenhof.circleimageview.CircleImageView;
import nescaupower.br.com.keepsoft.Config.Settings;
import nescaupower.br.com.keepsoft.Controller.PerfilController;
import nescaupower.br.com.keepsoft.Controller.ReuniaoController;
import nescaupower.br.com.keepsoft.Controller.ReuniaoUsuarioController;
import nescaupower.br.com.keepsoft.Controller.UsuarioController;
import nescaupower.br.com.keepsoft.Factory.Model.Reuniao;
import nescaupower.br.com.keepsoft.Factory.Model.ReuniaoUsuario;
import nescaupower.br.com.keepsoft.Factory.Model.Usuario;
import nescaupower.br.com.keepsoft.R;

public class DetalhesParticipante extends AppCompatActivity {

    private TextView lblLoginParticipanteDetalhes, lblNomeParticipanteDetalhes, lblFuncaoParticipanteDetalhes,
            lblEmailParticipanteDetalhes, lblTelefoneParticipanteDetalhes;
    private Button btnDeleteParticipanteDetalhes;
    private CircleImageView circleImageView;
    private UsuarioController uc;
    private ReuniaoUsuarioController rc;
    private Usuario usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes_participante);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        long idUsuario = intent.getLongExtra("EXTRA_PARTICIPANTE_ID", 0);
        intent.removeExtra("EXTRA_PARTICIPANTE_ID");

        uc = new UsuarioController();
        rc = new ReuniaoUsuarioController();

        usuario = uc.procurarPorID(idUsuario);

        lblLoginParticipanteDetalhes = findViewById(R.id.lblLoginParticipanteDetalhes);
        lblNomeParticipanteDetalhes = findViewById(R.id.lblNomeParticipanteDetalhes);
        lblEmailParticipanteDetalhes = findViewById(R.id.lblEmailParticipanteDetalhes);
        lblTelefoneParticipanteDetalhes = findViewById(R.id.lblTelefoneParticipanteDetalhes);
        btnDeleteParticipanteDetalhes = findViewById(R.id.btnDeleteParticipanteDetalhes);
        circleImageView = findViewById(R.id.imgPerfilParticipanteDetalhes);

        setLabelsContent();

        //Singleton
        Usuario usuarioLogado = Usuario.getUsuarioLogado();
        if (usuarioLogado == null || usuarioLogado.getLogin().equals("")) {
            SharedPreferences sharedPreferences = getSharedPreferences(Settings.SHARED_PREF_NAME, Context.MODE_PRIVATE);
            usuarioLogado = uc.procurarPorLogin(sharedPreferences.getString(Settings.LOGIN, ""));
        }

        btnDeleteParticipanteDetalhes.setOnClickListener(view -> sairDoProjeto());
    }

    private void sairDoProjeto() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Aviso!");
        builder.setMessage("Deseja realmente remover este participante?");
        builder.setPositiveButton(R.string.confirm, (dialogInterface, i) -> {

            rc.deleteReuniaoUsuario(rc.getReuniaoUsuario(Reuniao.getUltimoReuniao().getId(), usuario.getId()));
            finish();
        });
        builder.setNegativeButton(R.string.cancel, null);
        builder.show();
    }

    private void setLabelsContent() {
        lblLoginParticipanteDetalhes.setText(usuario.getLogin());
        lblNomeParticipanteDetalhes.setText(usuario.getNome());
        lblEmailParticipanteDetalhes.setText(usuario.getEmail());
        lblTelefoneParticipanteDetalhes.setText(usuario.getTelefone());
        new MyAsyncTask().execute(Settings.URL+"/usuarios/imagem/"+usuario.getId());
    }

    private class MyAsyncTask extends AsyncTask<String, Void, Bitmap> {
        protected Bitmap doInBackground(String... params) {
            try {
                URL url = new URL(params[0]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);
                connection.connect();
                InputStream input = connection.getInputStream();
                Bitmap myBitmap = BitmapFactory.decodeStream(input);
                return myBitmap;
            } catch(IOException e) {
                return null;
            }
        }

        protected void onPostExecute(Bitmap result) {
            //do what you want with your bitmap result on the UI thread
            circleImageView.setImageBitmap(result);
        }

    }

}
