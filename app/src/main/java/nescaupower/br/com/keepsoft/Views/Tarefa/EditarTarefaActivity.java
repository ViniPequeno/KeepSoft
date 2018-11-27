package nescaupower.br.com.keepsoft.Views.Tarefa;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import nescaupower.br.com.keepsoft.Controller.StatusController;
import nescaupower.br.com.keepsoft.Controller.TarefaController;
import nescaupower.br.com.keepsoft.Controller.TarefaStatusController;
import nescaupower.br.com.keepsoft.Factory.Model.Perfil;
import nescaupower.br.com.keepsoft.Factory.Model.Tarefa;
import nescaupower.br.com.keepsoft.R;

public class EditarTarefaActivity extends AppCompatActivity {

    Perfil perfilSelecionado = null;
    List<Perfil> perfis;
    private Button btnAlterar, btnCancelarAlterar;
    private EditText txtTitulo;
    private EditText txtDescricao;
    private EditText txtDataLimite;
    private Spinner spinStatus;
    private Spinner spinPrioridade;
    private Spinner spinDificuldade;
    private Spinner spinUsuario;
    private TarefaController tc;
    private StatusController sc;
    private TarefaStatusController tsc;

    private DatePickerDialog dialogDataPrevista;
    private DatePickerDialog.OnDateSetListener seletorDataPrevista;
    private Tarefa tarefa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_tarefa);

        tc = new TarefaController();

        txtTitulo = findViewById(R.id.txtTitulo);
        txtDescricao = findViewById(R.id.txtDescricao);
        txtDataLimite = findViewById(R.id.txtDataLimite);

        String data = new SimpleDateFormat("dd/MM/yyyy").format(tarefa.getDataLimite());

        final int ano = Integer.parseInt(data.substring(6));
        final int mes = Integer.parseInt(data.substring(3, 5));
        final int dia = Integer.parseInt(data.substring(0, 2));

        txtTitulo.setText(tarefa.getTitulo());
        txtDescricao.setText(tarefa.getDescricao());
        txtDataLimite.setText(data);

        txtDataLimite.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                dialogDataPrevista.show();
            }
        });
        seletorDataPrevista = (view, year, month, dayOfMonth) -> {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            Date data1 = new GregorianCalendar(year, month, dayOfMonth).getTime();
            txtDataLimite.setText(sdf.format(data1));
            txtDataLimite.clearFocus();
            getCurrentFocus().clearFocus();
        };
        dialogDataPrevista = new DatePickerDialog(EditarTarefaActivity.this, seletorDataPrevista, ano, mes, dia);
        dialogDataPrevista.getDatePicker().setMinDate(Calendar.getInstance().getTimeInMillis());
    }

    public void alterarTarefa(View view) {
        if (txtTitulo.getText().toString().equals("")) {
            Toast.makeText(this, "O Tarefa deve ter um nome", Toast.LENGTH_SHORT).show();
            return;
        } else if (txtDescricao.getText().toString().equals("")) {
            Toast.makeText(this, "O Tarefa deve ter um descrição", Toast.LENGTH_SHORT).show();
            return;
        } else if (txtDataLimite.getText().toString().equals("")) {
            Toast.makeText(this, "O Tarefa deve ter uma data de previsão final", Toast.LENGTH_SHORT).show();
            return;
        }

        tarefa.setTitulo(txtTitulo.getText().toString());
        tarefa.setDescricao(txtDescricao.getText().toString());
        try {
            tarefa.setDataLimite(new SimpleDateFormat("dd/MM/yyyy").
                    parse(txtDataLimite.getText().toString()));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (tc.atualizar(tarefa) != null) {
            Toast.makeText(this, "Alterado!", Toast.LENGTH_SHORT).show();
            EditarTarefaActivity.this.finish();
        } else {
            Toast.makeText(this, "Tarefa já existe!", Toast.LENGTH_SHORT).show();
        }
    }

    public void cancelarAlterarTarefa(View view) {
        EditarTarefaActivity.this.finish();
    }
}
