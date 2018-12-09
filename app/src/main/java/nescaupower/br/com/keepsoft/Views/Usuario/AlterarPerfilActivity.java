package nescaupower.br.com.keepsoft.Views.Usuario;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import de.hdodenhof.circleimageview.CircleImageView;
import nescaupower.br.com.keepsoft.AsyncTasks.GetImageAsyncTask;
import nescaupower.br.com.keepsoft.Config.Settings;
import nescaupower.br.com.keepsoft.Controller.UsuarioController;
import nescaupower.br.com.keepsoft.Factory.Model.Usuario;
import nescaupower.br.com.keepsoft.R;
import nescaupower.br.com.keepsoft.Views.PaginaInicialActivity;

public class AlterarPerfilActivity extends AppCompatActivity {

    private EditText lblNome;
    private EditText lblEmail;
    private EditText lblTelefone;
    private Button btnAlterar;
    private Button btnImagemPerfil;

    private CircleImageView circleImageView;
    private Bitmap image;

    public static final int PICK_IMAGE = 1;

    private UsuarioController uc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alterar_perfil);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        uc = new UsuarioController();

        //Singleton
        Usuario usuario = Usuario.getUsuarioLogado();
        if (usuario == null || usuario.getLogin().equals("")) {
            SharedPreferences sharedPreferences = getSharedPreferences(Settings.SHARED_PREF_NAME, Context.MODE_PRIVATE);
            usuario = uc.procurarPorLogin(sharedPreferences.getString(Settings.LOGIN, ""));
        }

        lblEmail = findViewById(R.id.alterarEmail);
        lblNome = findViewById(R.id.alterarNome);
        lblTelefone = findViewById(R.id.alterarTelefone);

        btnAlterar = findViewById(R.id.btnAlterarPerfil);
        circleImageView = findViewById(R.id.circleImageView);

        lblEmail.setText(usuario.getEmail());
        lblNome.setText(usuario.getNome());
        lblTelefone.setText(usuario.getTelefone());

        new GetImageAsyncTask(circleImageView).execute(Settings.URL + "/usuarios/imagem/" + usuario.getId());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==PICK_IMAGE && resultCode==RESULT_OK){
            if(data!=null){
                try {
                    if(image!=null){
                        image.recycle();
                    }
                    InputStream stream = getContentResolver().openInputStream(data.getData());
                    image = BitmapFactory.decodeStream(stream);
                    stream.close();
                    circleImageView.setImageBitmap(image);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void alterarPerfil(View view) {
        if(lblEmail.getText().toString().equals("")){
            Toast.makeText(this, R.string.field_email_empty, Toast.LENGTH_SHORT).show();
        }else if(lblNome.getText().toString().equals("")){
            Toast.makeText(this, R.string.field_name_empty, Toast.LENGTH_SHORT).show();
        }else{
            Usuario usuario = Usuario.getUsuarioLogado();
            usuario.setEmail(lblEmail.getText().toString());
            usuario.setNome(lblNome.getText().toString());
            usuario.setTelefone(lblTelefone.getText().toString());
            ByteArrayOutputStream stream = new ByteArrayOutputStream();

            if (image != null) {
                image.compress(Bitmap.CompressFormat.PNG, 10, stream);
                byte[] byteArray = stream.toByteArray();
                usuario.setImagem(byteArray);
            }

            uc.atualizar(usuario);
            Usuario.setUsuarioLogado(usuario);

            Intent intent;
            intent = new Intent(AlterarPerfilActivity.this, PaginaInicialActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK); // adiciona a flag para a intent
            startActivity(intent);
            AlterarPerfilActivity.this.finish();
        }
    }

    public void escolherImagem(View view){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, getResources().getString(R.string.select_picture)), PICK_IMAGE);
    }
}
