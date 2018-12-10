package nescaupower.br.com.keepsoft.Views.Usuario;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import nescaupower.br.com.keepsoft.Controller.UsuarioController;
import nescaupower.br.com.keepsoft.Factory.Factory;
import nescaupower.br.com.keepsoft.Factory.Model.Usuario;
import nescaupower.br.com.keepsoft.R;

public class CadastroUsuarioActivity extends AppCompatActivity {

    private EditText txtLogin;
    private EditText txtNome;
    private EditText txtEmail;
    private EditText txtSenha;
    private EditText txtConfirmarSenha;
    private EditText txtTelefone;

    private UsuarioController uc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_usuario);

        uc = new UsuarioController();

        txtLogin = findViewById(R.id.txtLogin);
        txtNome = findViewById(R.id.txtNome);
        txtEmail = findViewById(R.id.txtEmail);
        txtSenha = findViewById(R.id.txtSenha);
        txtConfirmarSenha = findViewById(R.id.txtConfirmarSenha);
        txtTelefone = findViewById(R.id.txtTelefone);
    }

    public void cadastrar(View view) {
        if (txtEmail.getText().equals("")) {
            Toast.makeText(this, "Você deve preencher o campo e-mail!", Toast.LENGTH_SHORT).show();
        } else if (txtLogin.getText().equals("")) {
            Toast.makeText(this, "Você deve preencher o campo login!", Toast.LENGTH_SHORT).show();
        } else if (txtNome.getText().equals("")) {
            Toast.makeText(this, "Você deve preencher o campo nome", Toast.LENGTH_SHORT).show();
        } else if (txtSenha.getText().equals("")) {
            Toast.makeText(this, "Você deve preencher o campo senha", Toast.LENGTH_SHORT).show();
        } else if (txtConfirmarSenha.getText().equals("")) {
            Toast.makeText(this, "Você deve preencher o campo confirmar senha", Toast.LENGTH_SHORT).show();
        } else if (txtSenha.getText().toString().equals(txtConfirmarSenha.getText().toString())) {
            Usuario u = Factory.startUsuario();
            u.setEmail(txtEmail.getText().toString());
            u.setLogin(txtLogin.getText().toString());
            u.setSenha(txtSenha.getText().toString());
            u.setNome(txtNome.getText().toString());
            u.setTelefone(txtTelefone.getText().toString());

            boolean cadastrou = uc.cadastrar(u);

            Toast.makeText(this, uc.getMensagem(), Toast.LENGTH_SHORT).show();
            if (cadastrou) {
                CadastroUsuarioActivity.this.finish();
            }
        } else {
            Toast.makeText(this, "Senhas não são iguais!", Toast.LENGTH_SHORT).show();
        }
    }
}
