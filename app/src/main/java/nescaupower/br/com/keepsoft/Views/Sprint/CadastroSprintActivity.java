package nescaupower.br.com.keepsoft.Views.Sprint;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import nescaupower.br.com.keepsoft.Controller.SprintController;
import nescaupower.br.com.keepsoft.Factory.Factory;
import nescaupower.br.com.keepsoft.Factory.Model.Projeto;
import nescaupower.br.com.keepsoft.Factory.Model.Sprint;
import nescaupower.br.com.keepsoft.R;
import nescaupower.br.com.keepsoft.Views.Projeto.DetalhesProjetoActivity;

public class CadastroSprintActivity extends AppCompatActivity {

    private ConstraintLayout root;
    private Calendar dataAtual = Calendar.getInstance();
    private EditText txtTitulo;
    private EditText txtDescricao;
    private EditText txtDataInicio;
    private EditText txtDataFim;
    private SprintController pc;
    private DatePickerDialog dialogDataInicio, dialogDataFim;
    private DatePickerDialog.OnDateSetListener listenerDataSelecionadaDataInicio, listenerDataSelecionadaDataFim;
    private DatePickerDialog.OnCancelListener listenerSelecaoCanceladaDataInicio, listenerSelecaoCanceladaDataFim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_sprint);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        root = findViewById(R.id.sprint);

        pc = new SprintController();

        txtTitulo = findViewById(R.id.txtTitulo);
        txtDescricao = findViewById(R.id.txtDescricao);
        txtDataInicio = findViewById(R.id.txtDataInicio);
        txtDataFim = findViewById(R.id.txtDataFim);

        txtDataInicio.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                dialogDataInicio.show();
            }
        });

        //TODO: ajustar foco após selecionar data
        listenerDataSelecionadaDataInicio = (view, year, month, dayOfMonth) -> {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            Date data = new GregorianCalendar(year, month, dayOfMonth).getTime();
            txtDataInicio.setText(sdf.format(data));
            root.clearFocus();
        };
        listenerSelecaoCanceladaDataInicio = dialogInterface -> root.clearFocus();

        dialogDataInicio = new DatePickerDialog(CadastroSprintActivity.this, listenerDataSelecionadaDataInicio, dataAtual
                .get(Calendar.YEAR), dataAtual.get(Calendar.MONTH), dataAtual.get(Calendar.DAY_OF_MONTH));
        dialogDataInicio.setOnCancelListener(listenerSelecaoCanceladaDataInicio);

        txtDataFim.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                dialogDataFim.show();
            }
        });
        //TODO: ajustar foco após selecionar data
        listenerDataSelecionadaDataFim = (view, year, month, dayOfMonth) -> {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            Date data = new GregorianCalendar(year, month, dayOfMonth).getTime();
            txtDataFim.setText(sdf.format(data));
            root.clearFocus();
        };
        listenerSelecaoCanceladaDataFim = dialogInterface -> root.clearFocus();

        dialogDataFim = new DatePickerDialog(CadastroSprintActivity.this, listenerDataSelecionadaDataFim, dataAtual
                .get(Calendar.YEAR), dataAtual.get(Calendar.MONTH), dataAtual.get(Calendar.DAY_OF_MONTH));
        dialogDataFim.setOnCancelListener(listenerSelecaoCanceladaDataFim);
    }

    public void cadastrar(View v) {

        if (txtTitulo.getText().toString() == "") {
            Toast.makeText(this, "O Sprint deve ter um título", Toast.LENGTH_SHORT).show();
            return;
        } else if (txtDescricao.getText().toString() == "") {
            Toast.makeText(this, "O Sprint deve ter uma descrição", Toast.LENGTH_SHORT).show();
            return;
        } else if (txtDataInicio.getText().toString() == "") {
            Toast.makeText(this, "O Sprint deve ter uma data de início", Toast.LENGTH_SHORT).show();
            return;
        } else if (txtDataFim.getText().toString() == "") {
            Toast.makeText(this, "O Sprint deve ter uma data de finalização", Toast.LENGTH_SHORT).show();
            return;
        }

        Sprint s = Factory.startSprint();

        s.setTitulo(txtTitulo.getText().toString());
        s.setDescricao(txtDescricao.getText().toString());
        s.setDataInicio(Calendar.getInstance().getTime());

        try {
            s.setDataInicio(new SimpleDateFormat("dd/MM/yyyy").parse(txtDataInicio.getText().toString()));
        } catch ( ParseException e ) {
            e.printStackTrace();
        }

        try {
            s.setDataFim(new SimpleDateFormat("dd/MM/yyyy").parse(txtDataFim.getText().toString()));
        } catch ( ParseException e ) {
            e.printStackTrace();
        }

        s.setProjeto(Projeto.getUltimoProjetoUsado());

        boolean cadastrou = pc.cadastrar(s);

        if (cadastrou) {
            Intent i = new Intent(CadastroSprintActivity.this, DetalhesProjetoActivity.class);
            startActivity(i);
            CadastroSprintActivity.this.finish();
        } else {
            Toast.makeText(this, "Sprint já existe", Toast.LENGTH_SHORT).show();
        }
    }

}
