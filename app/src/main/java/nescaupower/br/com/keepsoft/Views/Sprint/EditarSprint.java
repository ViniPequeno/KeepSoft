package nescaupower.br.com.keepsoft.Views.Sprint;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import nescaupower.br.com.keepsoft.Config.Settings;
import nescaupower.br.com.keepsoft.Controller.SprintController;
import nescaupower.br.com.keepsoft.Controller.UsuarioController;
import nescaupower.br.com.keepsoft.Factory.Model.Sprint;
import nescaupower.br.com.keepsoft.Factory.Model.Usuario;
import nescaupower.br.com.keepsoft.R;

public class EditarSprint extends AppCompatActivity {

    private Usuario usuario;
    private Sprint sprint;
    private UsuarioController uc;
    private SprintController sc;

    private ConstraintLayout root;
    private Calendar dataAtual = Calendar.getInstance();
    private EditText txtTituloEditar;
    private EditText txtDescricaoEditar;
    private EditText txtDataInicioEditar;
    private EditText txtDataFimEditar;
    private DatePickerDialog dialogDataInicio, dialogDataFim;
    private DatePickerDialog.OnDateSetListener listenerDataSelecionadaDataInicio, listenerDataSelecionadaDataFim;
    private DatePickerDialog.OnCancelListener listenerSelecaoCanceladaDataInicio, listenerSelecaoCanceladaDataFim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_sprint);
        root = findViewById(R.id.sprintEditar);


        uc = new UsuarioController();
        sc = new SprintController();

        //Singleton
        usuario = Usuario.getUsuarioLogado();
        if (usuario == null || usuario.getLogin().equals("")) {
            SharedPreferences sharedPreferences = getSharedPreferences(Settings.SHARED_PREF_NAME, Context.MODE_PRIVATE);
            usuario = uc.procurarPorLogin(sharedPreferences.getString(Settings.LOGIN, ""));
        }

        sprint = new SprintController().procurarPorCodigo(getIntent().getLongExtra("EXTRA_CODIGO_SPRINT", 0));
        if (sprint == null) {
            this.finish();
        }

        txtTituloEditar = findViewById(R.id.txtTituloEditar);
        txtDescricaoEditar = findViewById(R.id.txtDescricaoEditar);
        txtDataInicioEditar = findViewById(R.id.txtDataInicioEditar);
        txtDataFimEditar = findViewById(R.id.txtDataFimEditar);


        txtTituloEditar.setText(sprint.getTitulo());
        txtDescricaoEditar.setText(sprint.getDescricao());
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        txtDataInicioEditar.setText(dateFormat.format(sprint.getDataInicio()));
        txtDataFimEditar.setText(dateFormat.format(sprint.getDataFim()));

        txtDataInicioEditar.setOnFocusChangeListener(new View.OnFocusChangeListener() {
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
                txtDataInicioEditar.setText(sdf.format(data));
                root.clearFocus();
            }
        };
        listenerSelecaoCanceladaDataInicio = new DatePickerDialog.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialogInterface) {
                root.clearFocus();
            }
        };

        dialogDataInicio = new DatePickerDialog(EditarSprint.this, listenerDataSelecionadaDataInicio, dataAtual
                .get(Calendar.YEAR), dataAtual.get(Calendar.MONTH), dataAtual.get(Calendar.DAY_OF_MONTH));
        dialogDataInicio.setOnCancelListener(listenerSelecaoCanceladaDataInicio);

        txtDataFimEditar.setOnFocusChangeListener(new View.OnFocusChangeListener() {
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
                txtDataFimEditar.setText(sdf.format(data));
                root.clearFocus();
            }
        };
        listenerSelecaoCanceladaDataFim = new DatePickerDialog.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialogInterface) {
                root.clearFocus();
            }
        };

        dialogDataFim = new DatePickerDialog(EditarSprint.this, listenerDataSelecionadaDataFim, dataAtual
                .get(Calendar.YEAR), dataAtual.get(Calendar.MONTH), dataAtual.get(Calendar.DAY_OF_MONTH));
        dialogDataFim.setOnCancelListener(listenerSelecaoCanceladaDataFim);
    }

    public void editarSprint(View v) {

        if (txtTituloEditar.getText().toString() == "" || txtTituloEditar.getText().toString() == null) {
            Toast.makeText(this, "O Sprint deve ter um título", Toast.LENGTH_SHORT).show();
            return;
        } else if (txtDescricaoEditar.getText().toString() == "" || txtDescricaoEditar.getText().toString() == null) {
            Toast.makeText(this, "O Sprint deve ter uma descrição", Toast.LENGTH_SHORT).show();
            return;
        } else if (txtDataInicioEditar.getText().toString() == "" || txtDataInicioEditar.getText().toString() == null) {
            Toast.makeText(this, "O Sprint deve ter uma data de início", Toast.LENGTH_SHORT).show();
            return;
        } else if (txtDataFimEditar.getText().toString() == "" || txtDataFimEditar.getText().toString() == null) {
            Toast.makeText(this, "O Sprint deve ter uma data de finalização", Toast.LENGTH_SHORT).show();
            return;
        }


        sprint.setTitulo(txtTituloEditar.getText().toString());
        sprint.setDescricao(txtDescricaoEditar.getText().toString());
        sprint.setDataInicio(Calendar.getInstance().getTime());

        try {
            sprint.setDataInicio(new SimpleDateFormat("dd/MM/yyyy").parse(txtDataInicioEditar.getText().toString()));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        try {
            sprint.setDataFim(new SimpleDateFormat("dd/MM/yyyy").parse(txtDataFimEditar.getText().toString()));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        sc.atualizar(sprint);

        Intent i = new Intent(EditarSprint.this, DetalhesSprintActivity.class);
        i.putExtra("EXTRA_CODIGO_SPRINT", getIntent().getLongExtra("EXTRA_CODIGO_SPRINT", 0));
        startActivity(i);
        EditarSprint.this.finish();

    }
}
