package nescaupower.br.com.keepsoft.Views.Status;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.thebluealliance.spectrum.SpectrumDialog;

import nescaupower.br.com.keepsoft.Controller.StatusController;
import nescaupower.br.com.keepsoft.Factory.Model.Status;
import nescaupower.br.com.keepsoft.R;
import nescaupower.br.com.keepsoft.Views.Reuniao.ReuniaoActivity;

public class EditarStatus extends AppCompatActivity {

    private EditText txtNomeStatusEditar;
    private EditText txtDescricaoStatusEditar;
    private EditText txtSeletorCor;
    private ImageView imgColor;
    private int intColor;

    private StatusController sc;
    private Status status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_status);

        sc = new StatusController();
        txtNomeStatusEditar = findViewById(R.id.txtNomeStatusEditar);
        txtDescricaoStatusEditar = findViewById(R.id.txtDescricaoStatusEditar);
        txtSeletorCor = findViewById(R.id.txtSeletorCor);
        imgColor = findViewById(R.id.imgColor);

        status = sc.findByID(getIntent().getLongExtra("EXTRA_CODIGO_STATUS", 0));
        if (status == null) {
            //
        }

        txtNomeStatusEditar.setText(status.getNome());
        txtDescricaoStatusEditar.setText(status.getDescricao());
        imgColor.setColorFilter(status.getCor());
        intColor = status.getCor();

        SpectrumDialog.Builder sdBuilder = new SpectrumDialog.Builder(this);
        sdBuilder.setSelectedColor(status.getCor());
        sdBuilder.setColors(R.array.demo_colors);
        sdBuilder.setPositiveButtonText(R.string.confirm);
        sdBuilder.setNegativeButtonText(R.string.cancel);
        sdBuilder.setOnColorSelectedListener((positiveResult, color) -> {
            if (positiveResult) {
                imgColor.setColorFilter(color);
                intColor = color;
                sdBuilder.setSelectedColor(color);
            }
            txtSeletorCor.clearFocus();
        });
        sdBuilder.setTitle("Escolha uma cor");

        txtSeletorCor.setOnFocusChangeListener((view, hasFocus) -> {
            if (hasFocus) {
                SpectrumDialog sd = sdBuilder.build();
                sd.show(getSupportFragmentManager(), "Cor");
            }
        });
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
        status.setCor(intColor);

        if(sc.updateAll(status) !=null) {
            Intent i = new Intent(this, StatusActivity.class);
            startActivity(i);
            EditarStatus.this.finish();
        }else{
            Toast.makeText(this, "O Status já existi!", Toast.LENGTH_SHORT).show();
        }
    }
}
