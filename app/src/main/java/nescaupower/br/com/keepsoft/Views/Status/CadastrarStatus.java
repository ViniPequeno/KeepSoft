package nescaupower.br.com.keepsoft.Views.Status;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import nescaupower.br.com.keepsoft.Controller.StatusController;
import nescaupower.br.com.keepsoft.Factory.Model.Projeto;
import nescaupower.br.com.keepsoft.Factory.Model.Status;
import nescaupower.br.com.keepsoft.R;

public class CadastrarStatus extends AppCompatActivity {

    private EditText txtNomeStatus;
    private EditText txtDescricaoStatus;

    private StatusController sc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar_status);

        sc = new StatusController();
        txtNomeStatus = findViewById(R.id.txtNomeStatus);
        txtDescricaoStatus = findViewById(R.id.txtDescricaoStatus);
    }

    public void cadastrar(View view) {
        if (txtNomeStatus.getText().toString().equals("")) {
            Toast.makeText(this, "O Status deve ter um nome", Toast.LENGTH_SHORT).show();
            return;
        } else if (txtDescricaoStatus.getText().toString().equals("")) {
            Toast.makeText(this, "O Status deve ter um descrição", Toast.LENGTH_SHORT).show();
            return;
        }

        Status status = new Status();
        status.setNome(txtNomeStatus.getText().toString());
        status.setDescricao(txtDescricaoStatus.getText().toString());

        Projeto projeto = Projeto.getUltimoProjetoUsado();

        status.setProjeto(projeto);

        sc.insertAll(status);

        CadastrarStatus.this.finish();
    }
}
