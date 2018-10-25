package nescaupower.br.com.keepsoft.Views.Status;

import android.graphics.Color;
import android.graphics.ColorFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.thebluealliance.spectrum.SpectrumDialog;

import nescaupower.br.com.keepsoft.Controller.StatusController;
import nescaupower.br.com.keepsoft.Factory.Model.Projeto;
import nescaupower.br.com.keepsoft.Factory.Model.Status;
import nescaupower.br.com.keepsoft.R;

public class CadastrarStatus extends AppCompatActivity {

    private EditText txtNomeStatus;
    private EditText txtDescricaoStatus;
    private EditText txtSeletorCor;
    private ImageView imgColor;
    private int intColor;

    private StatusController sc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_status);

        sc = new StatusController();
        txtNomeStatus = findViewById(R.id.txtNomeStatus);
        txtDescricaoStatus = findViewById(R.id.txtDescricaoStatus);
        txtSeletorCor = findViewById(R.id.txtSeletorCor);
        imgColor = findViewById(R.id.imgColor);
        imgColor.setColorFilter(Color.RED);
        intColor=(Color.RED);

        SpectrumDialog.Builder sdBuilder = new SpectrumDialog.Builder(this);
        sdBuilder.setSelectedColor(0);
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
        status.setCor(intColor);

        Projeto projeto = Projeto.getUltimoProjetoUsado();

        status.setProjeto(projeto);

        sc.insertAll(status);

        CadastrarStatus.this.finish();
    }
}
