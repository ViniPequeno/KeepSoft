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
import nescaupower.br.com.keepsoft.Views.Usuario.PaginaInicialActivity;

public class CadastroSprintActivity extends AppCompatActivity {

    private ConstraintLayout root;
    private Calendar dataAtual = Calendar.getInstance();
    private EditText txtNome;
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
        root = findViewById(R.id.teste);

        pc = new SprintController(getApplicationContext());

        txtNome = findViewById(R.id.txtNome);
        txtDescricao = findViewById(R.id.txtDescricao);
        txtDataInicio = findViewById(R.id.txtDataInicio);
        txtDataFim = findViewById(R.id.txtDataFim);

        txtDataInicio.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    dialogDataInicio.show();
                }
            }
        });

        //TODO: ajustar foco após selecionar data
        listenerDataSelecionadaDataInicio = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                Date data = new GregorianCalendar(year, month, dayOfMonth).getTime();
                txtDataInicio.setText(sdf.format(data));
                root.clearFocus();
            }
        };
        listenerSelecaoCanceladaDataInicio = new DatePickerDialog.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialogInterface) {
                root.clearFocus();
            }
        };

        dialogDataInicio = new DatePickerDialog(CadastroSprintActivity.this, listenerDataSelecionadaDataInicio, dataAtual
                .get(Calendar.YEAR), dataAtual.get(Calendar.MONTH), dataAtual.get(Calendar.DAY_OF_MONTH));
        dialogDataInicio.setOnCancelListener(listenerSelecaoCanceladaDataInicio);

        txtDataFim.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    dialogDataFim.show();
                }
            }
        });
        //TODO: ajustar foco após selecionar data
        listenerDataSelecionadaDataFim = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                Date data = new GregorianCalendar(year, month, dayOfMonth).getTime();
                txtDataFim.setText(sdf.format(data));
                root.clearFocus();
            }
        };
        listenerSelecaoCanceladaDataFim = new DatePickerDialog.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialogInterface) {
                root.clearFocus();
            }
        };

        dialogDataFim = new DatePickerDialog(CadastroSprintActivity.this, listenerDataSelecionadaDataFim, dataAtual
                .get(Calendar.YEAR), dataAtual.get(Calendar.MONTH), dataAtual.get(Calendar.DAY_OF_MONTH));
        dialogDataFim.setOnCancelListener(listenerSelecaoCanceladaDataFim);
    }

    public void cadastrar(View v) {

        if (txtNome.getText().toString() == "" || txtNome.getText().toString() == null) {
            Toast.makeText(this, "O Sprint deve ter um nome", Toast.LENGTH_SHORT).show();
            return;
        } else if (txtDescricao.getText().toString() == "" || txtDescricao.getText().toString() == null) {
            Toast.makeText(this, "O Sprint deve ter um descrição", Toast.LENGTH_SHORT).show();
            return;
        } else if (txtDataInicio.getText().toString() == "" || txtDataInicio.getText().toString() == null) {
            Toast.makeText(this, "O Sprint deve ter uma data de previsão final", Toast.LENGTH_SHORT).show();
            return;
        }

        Sprint s = Factory.startSprint();

        s.setTitulo(txtNome.getText().toString());
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

        s.setCodProjeto(Projeto.getUltimoProjetoUsado().getCodigo());

        boolean cadastrou = pc.cadastrar(s);

        if (cadastrou) {
            Intent i = new Intent(CadastroSprintActivity.this, PaginaInicialActivity.class);
            startActivity(i);
            CadastroSprintActivity.this.finish();
        } else {
            Toast.makeText(this, "Sprint já existe", Toast.LENGTH_SHORT).show();
        }
    }

}
