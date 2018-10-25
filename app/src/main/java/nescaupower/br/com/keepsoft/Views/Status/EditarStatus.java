package nescaupower.br.com.keepsoft.Views.Status;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import nescaupower.br.com.keepsoft.Controller.StatusController;
import nescaupower.br.com.keepsoft.Factory.Model.Status;
import nescaupower.br.com.keepsoft.R;

public class EditarStatus extends AppCompatActivity {

    private EditText txtNomeStatusEditar;
    private EditText txtDescricaoStatusEditar;

    private StatusController sc;
    private Status status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_status);

        sc = new StatusController();
        txtNomeStatusEditar = findViewById(R.id.txtNomeStatusEditar);
        txtDescricaoStatusEditar = findViewById(R.id.txtDescricaoStatusEditar);

        status = sc.findByID(getIntent().getLongExtra("EXTRA_CODIGO_STATUS", 0));
        if (status == null) {
            //
        }

        txtNomeStatusEditar.setText(status.getNome());
        txtDescricaoStatusEditar.setText(status.getDescricao());
    }

    public void editar(View view) {
        if (txtNomeStatusEditar.getText().toString().equals("")) {
            Toast.makeText(this, "O Status deve ter um nome", Toast.LENGTH_SHORT).show();
            return;
        } else if (txtDescricaoStatusEditar.getText().toString().equals("")) {
            Toast.makeText(this, "O Status deve ter um descrição", Toast.LENGTH_SHORT).show();
            return;
        }


        status.setNome(txtNomeStatusEditar.getText().toString());
        status.setDescricao(txtDescricaoStatusEditar.getText().toString());


        sc.updateAll(status);

        EditarStatus.this.finish();
    }
}
