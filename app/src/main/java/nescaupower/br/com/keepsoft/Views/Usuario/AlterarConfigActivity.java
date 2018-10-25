package nescaupower.br.com.keepsoft.Views.Usuario;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Switch;
import android.widget.Toast;

import nescaupower.br.com.keepsoft.Config.Settings;
import nescaupower.br.com.keepsoft.Controller.UsuarioController;
import nescaupower.br.com.keepsoft.Factory.Model.Usuario;
import nescaupower.br.com.keepsoft.R;

public class AlterarConfigActivity extends AppCompatActivity {

    private Switch reiceverEmail, reiceverNotification;
    private Usuario usuario;
    private UsuarioController uc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alterar_config);

        reiceverEmail= findViewById(R.id.reiceverEmail);
        reiceverNotification = findViewById(R.id.reiceverNotification);

        //Singleton
        usuario = Usuario.getUsuarioLogado();
        if (usuario == null || usuario.getLogin().equals("")) {
            SharedPreferences sharedPreferences = getSharedPreferences(Settings.SHARED_PREF_NAME, Context.MODE_PRIVATE);
            usuario = uc.procurarPorLogin(sharedPreferences.getString(Settings.LOGIN, ""));
        }

        uc = new UsuarioController();

        reiceverEmail.setChecked(usuario.isReceiverEmail());
        reiceverNotification.setChecked(usuario.isReceiverNotification());

        reiceverEmail.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                Toast.makeText(getApplicationContext(), "Você irá receber notificiações a partir de agora!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getApplicationContext(), "Você não irá receber notificiações a partir de agora!", Toast.LENGTH_SHORT).show();
            }

            usuario.setReceiverNotification(isChecked);
            uc.atualizar(usuario);


        });

        reiceverNotification.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                Toast.makeText(getApplicationContext(), "Você irá receber e-mails a partir de agora!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getApplicationContext(), "Você não irá receber e-mails a partir de agora!", Toast.LENGTH_SHORT).show();
            }

            usuario.setReceiverEmail(isChecked);
            uc.atualizar(usuario);
        });

    }
}
