package nescaupower.br.com.keepsoft.Views.Tarefa;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import nescaupower.br.com.keepsoft.Controller.PerfilController;
import nescaupower.br.com.keepsoft.Controller.StatusController;
import nescaupower.br.com.keepsoft.Controller.TarefaController;
import nescaupower.br.com.keepsoft.Controller.TarefaStatusController;
import nescaupower.br.com.keepsoft.Enum.Dificuldade;
import nescaupower.br.com.keepsoft.Enum.Prioridade;
import nescaupower.br.com.keepsoft.Factory.Model.Perfil;
import nescaupower.br.com.keepsoft.Factory.Model.Projeto;
import nescaupower.br.com.keepsoft.Factory.Model.Status;
import nescaupower.br.com.keepsoft.Factory.Model.Tarefa;
import nescaupower.br.com.keepsoft.Factory.Model.TarefaStatus;
import nescaupower.br.com.keepsoft.R;
import nescaupower.br.com.keepsoft.Views.Projeto.DetalhesProjetoActivity;

public class CadastroTarefaActivity extends AppCompatActivity {

    EditText txtTitulo;
    EditText txtDescricao;
    EditText txtDataLimite;
    Spinner spinStatus;
    Spinner spinPrioridade;
    Spinner spinDificuldade;
    Spinner spinUsuario;
    Perfil perfilSelecionado = null;
    List<Perfil> perfis;

    private TarefaController tc;
    private StatusController sc;
    private TarefaStatusController tsc;
    private PerfilController pc;

    private LinearLayout root;
    private Calendar dataAtual = Calendar.getInstance();
    private DatePickerDialog dialogDataLimite;
    private DatePickerDialog.OnDateSetListener listenerDataSelecionadaDataLimite;
    private DatePickerDialog.OnCancelListener listenerSelecaoCanceladaDataLimite;

    private Button btnCadastrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_tarefa);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        root = findViewById(R.id.rootTarefa);
        txtTitulo = findViewById(R.id.txtTitulo);
        txtDescricao = findViewById(R.id.txtDescricao);
        txtDataLimite = findViewById(R.id.txtDataLimite);
        spinStatus = findViewById(R.id.spinStatus);
        spinPrioridade = findViewById(R.id.spinPrioridade);
        spinDificuldade = findViewById(R.id.spinDificuldade);
        spinUsuario = findViewById(R.id.spinUsuario);

        btnCadastrar = findViewById(R.id.btnCadastrar);
        btnCadastrar.setOnClickListener(this::cadastrar);

        tc = new TarefaController();
        sc = new StatusController();
        tsc = new TarefaStatusController();

        perfis = pc.listarPorProjeto(Projeto.getUltimoProjetoUsado().getCodigo());

        //Inicializando Spinner de Status
        ArrayAdapter statusAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,
                sc.getNamesByProjeto(Projeto.getUltimoProjetoUsado().getCodigo()));
        statusAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinStatus.setAdapter(statusAdapter);
        spinStatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                Log.e("oi", parent.getSelectedItem().toString());
                Status st = sc.findByNameInProjeto(Projeto.getUltimoProjetoUsado().getCodigo(),
                        parent.getSelectedItem().toString());
                if (st != null)
                    Toast.makeText(CadastroTarefaActivity.this, st.getNome(), Toast.LENGTH_SHORT).show();
            }

            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        //Inicializando Spinner de Usuário Responsável pela Tarefa
        SpinPerfilAdapter adapter = new SpinPerfilAdapter(CadastroTarefaActivity.this, perfis);
        spinUsuario.setAdapter(adapter);
        spinUsuario.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                perfilSelecionado = (Perfil) parent.getItemAtPosition(pos);
                Toast.makeText(CadastroTarefaActivity.this, perfilSelecionado.getUsuario().getNome(), Toast.LENGTH_SHORT).show();
            }

            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        txtDataLimite.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                dialogDataLimite.show();
            }
        });

        listenerDataSelecionadaDataLimite = (view, year, month, dayOfMonth) -> {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            Date data = new GregorianCalendar(year, month, dayOfMonth).getTime();
            txtDataLimite.setText(sdf.format(data));
            root.clearFocus();
        };
        listenerSelecaoCanceladaDataLimite = dialogInterface -> root.clearFocus();
        dialogDataLimite = new DatePickerDialog(CadastroTarefaActivity.this, listenerDataSelecionadaDataLimite, dataAtual
                .get(Calendar.YEAR), dataAtual.get(Calendar.MONTH), dataAtual.get(Calendar.DAY_OF_MONTH));
        dialogDataLimite.setOnCancelListener(listenerSelecaoCanceladaDataLimite);
        dialogDataLimite.getDatePicker().setMinDate(Calendar.getInstance().getTimeInMillis());
    }


    public void cadastrar(View v) {
        if (txtTitulo.getText().toString().equals("")) {
            Toast.makeText(getApplicationContext(), "Titulo não pode ser vazio!", Toast.LENGTH_SHORT).show();
            return;
        } else if (txtDescricao.getText().toString().equals("")) {
            Toast.makeText(getApplicationContext(), "Descrição não pode ser vazio!", Toast.LENGTH_SHORT).show();
            return;
        } else if (txtDataLimite.getText().toString().equals("")) {
            Toast.makeText(getApplicationContext(), "Data limite não pode ser vazio!", Toast.LENGTH_SHORT).show();
            return;
        }

        Tarefa tarefa = new Tarefa();
        tarefa.setTitulo(txtTitulo.getText().toString());
        tarefa.setDescricao(txtDescricao.getText().toString());
        tarefa.setPrioridade(Prioridade.values()[spinPrioridade.getSelectedItemPosition()]);
        tarefa.setDificuldade(Dificuldade.values()[spinDificuldade.getSelectedItemPosition()]);
        tarefa.setPerfil(perfilSelecionado);
        try {
            tarefa.setDataLimite(new SimpleDateFormat("dd/MM/yyyy").parse(txtDataLimite.getText().toString()));
        } catch (ParseException e) {
            e.printStackTrace();
            return;
        }
        tc.cadastrar(tarefa);

        TarefaStatus tarefaStatus = new TarefaStatus();
        tarefaStatus.setTarefa(tarefa);
        Status status = sc.findByNameInProjeto(Projeto.getUltimoProjetoUsado().getCodigo(),
                spinStatus.getSelectedItem().toString());
        tarefaStatus.setStatus(status);
        tarefaStatus.setDataInicio(Calendar.getInstance().getTime());
        tsc.cadastrar(tarefaStatus);

        Intent i = new Intent(CadastroTarefaActivity.this, DetalhesProjetoActivity.class);
        startActivity(i);
        CadastroTarefaActivity.this.finish();
    }
}
