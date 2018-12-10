package nescaupower.br.com.keepsoft.Views.Tarefa;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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
import nescaupower.br.com.keepsoft.Controller.SprintController;
import nescaupower.br.com.keepsoft.Controller.StatusController;
import nescaupower.br.com.keepsoft.Controller.TarefaController;
import nescaupower.br.com.keepsoft.Controller.TarefaStatusController;
import nescaupower.br.com.keepsoft.Enum.Dificuldade;
import nescaupower.br.com.keepsoft.Enum.Prioridade;
import nescaupower.br.com.keepsoft.Factory.Model.Perfil;
import nescaupower.br.com.keepsoft.Factory.Model.Projeto;
import nescaupower.br.com.keepsoft.Factory.Model.Sprint;
import nescaupower.br.com.keepsoft.Factory.Model.Status;
import nescaupower.br.com.keepsoft.Factory.Model.Tarefa;
import nescaupower.br.com.keepsoft.Factory.Model.TarefaStatus;
import nescaupower.br.com.keepsoft.R;

public class EditarTarefaActivity extends AppCompatActivity {

    private EditText txtTitulo;
    private EditText txtDescricao;
    private EditText txtDataLimite;
    private Spinner spinUsuario;
    private Spinner spinSprint;
    private Spinner spinStatus;
    private Spinner spinPrioridade;
    private Spinner spinDificuldade;

    Sprint sprintSelecionado = null;
    Status statusSelecionado = null;
    Perfil perfilSelecionado = null;
    List<Perfil> perfis;

    private SprintController spc;
    private PerfilController pc;
    private TarefaController tc;
    private StatusController sc;
    private TarefaStatusController tsc;

    private LinearLayout root;
    private Calendar dataAtual = Calendar.getInstance();
    private DatePickerDialog dialogDataLimite;
    private DatePickerDialog.OnDateSetListener listenerDataSelecionadaDataLimite;
    private DatePickerDialog.OnCancelListener listenerSelecaoCanceladaDataLimite;
    private Tarefa tarefa;
    private TarefaStatus tarefaStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_tarefa);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tc = new TarefaController();
        sc = new StatusController();
        tsc = new TarefaStatusController();
        pc = new PerfilController();
        spc = new SprintController();

        root = findViewById(R.id.rootTarefa);
        txtTitulo = findViewById(R.id.txtTitulo);
        txtDescricao = findViewById(R.id.txtDescricao);
        txtDataLimite = findViewById(R.id.txtDataLimite);
        spinUsuario = findViewById(R.id.spinUsuario);
        spinSprint = findViewById(R.id.spinSprint);
        spinStatus = findViewById(R.id.spinStatus);
        spinPrioridade = findViewById(R.id.spinPrioridade);
        spinDificuldade = findViewById(R.id.spinDificuldade);

        List<String> statusNomes = sc.getNamesByProjeto(Projeto.getUltimoProjetoUsado().getCodigo());
        List<String> sprintsNomes = spc.getNamesByProjeto(Projeto.getUltimoProjetoUsado().getCodigo());
        perfis = pc.listarPorProjeto(Projeto.getUltimoProjetoUsado().getCodigo());

        preencherSpinners();

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
        dialogDataLimite = new DatePickerDialog(EditarTarefaActivity.this, listenerDataSelecionadaDataLimite, dataAtual
                .get(Calendar.YEAR), dataAtual.get(Calendar.MONTH), dataAtual.get(Calendar.DAY_OF_MONTH));
        dialogDataLimite.setOnCancelListener(listenerSelecaoCanceladaDataLimite);
        dialogDataLimite.getDatePicker().setMinDate(Calendar.getInstance().getTimeInMillis());

        //Preechendo campos com dados da tarefa
        tarefa = tc.procurarPorId(getIntent().getLongExtra("tarefaId", 0));
        tarefaStatus = tsc.findCuurentStatusOfTarefa(tarefa.getId());

        String dataLimite = new SimpleDateFormat("dd/MM/yyyy").format(tarefa.getDataLimite());
        txtTitulo.setText(tarefa.getTitulo());
        txtDescricao.setText(tarefa.getDescricao());
        txtDataLimite.setText(dataLimite);
        spinSprint.setSelection(sprintsNomes.indexOf(tarefa.getSprint().getTitulo()));
        spinUsuario.setSelection(perfis.indexOf(tarefa.getPerfil()));
        spinStatus.setSelection(statusNomes.indexOf(tarefaStatus.getStatus().getNome()));
        spinPrioridade.setSelection(Prioridade.valueOf(tarefa.getPrioridade().name()).ordinal());
        spinDificuldade.setSelection(Dificuldade.valueOf(tarefa.getDificuldade().name()).ordinal());
    }

    private void preencherSpinners() {
        //Inicializando Spinner de Sprint
        ArrayAdapter sprintAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,
                spc.getNamesByProjeto(Projeto.getUltimoProjetoUsado().getCodigo()));
        sprintAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinSprint.setAdapter(sprintAdapter);
        spinSprint.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                Log.e("oi", parent.getSelectedItem().toString());
                sprintSelecionado = spc.procurarPorNome(parent.getSelectedItem().toString());
                if (sprintSelecionado != null)
                    Toast.makeText(EditarTarefaActivity.this, sprintSelecionado.getTitulo(), Toast.LENGTH_SHORT).show();
            }

            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        //Inicializando Spinner de Status
        ArrayAdapter statusAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,
                sc.getNamesByProjeto(Projeto.getUltimoProjetoUsado().getCodigo()));
        statusAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinStatus.setAdapter(statusAdapter);
        spinStatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                Log.e("oi", parent.getSelectedItem().toString());
                statusSelecionado = sc.findByNameInProjeto(Projeto.getUltimoProjetoUsado().getCodigo(),
                        parent.getSelectedItem().toString());
                if (statusSelecionado != null)
                    Toast.makeText(EditarTarefaActivity.this, statusSelecionado.getNome(), Toast.LENGTH_SHORT).show();
            }

            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        //Inicializando Spinner de Usuário Responsável pela Tarefa
        SpinPerfilAdapter adapter = new SpinPerfilAdapter(EditarTarefaActivity.this, perfis);
        spinUsuario.setAdapter(adapter);
        spinUsuario.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                perfilSelecionado = (Perfil) parent.getItemAtPosition(pos);
                Toast.makeText(EditarTarefaActivity.this, perfilSelecionado.getUsuario().getNome(), Toast.LENGTH_SHORT).show();
            }

            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    public void editar(View view) {
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
        tarefa.setPrioridade(Prioridade.values()[spinPrioridade.getSelectedItemPosition()]);
        tarefa.setDificuldade(Dificuldade.values()[spinDificuldade.getSelectedItemPosition()]);
        tarefa.setPerfil(perfilSelecionado);
        tarefa.setSprint(sprintSelecionado);
        try {
            tarefa.setDataLimite(new SimpleDateFormat("dd/MM/yyyy").
                    parse(txtDataLimite.getText().toString()));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (tc.atualizar(tarefa) != null) {
            if (statusSelecionado.getNome().equals(tarefaStatus.getStatus().getNome())) {
                Toast.makeText(this, "Eai", Toast.LENGTH_SHORT).show();
                EditarTarefaActivity.this.finish();
            } else {
                tarefaStatus.setDataFim(Calendar.getInstance().getTime());
                if (tsc.updateAll(tarefaStatus) != null) {
                    TarefaStatus newTarefaStatus = new TarefaStatus();
                    newTarefaStatus.setStatus(statusSelecionado);
                    newTarefaStatus.setTarefa(tarefa);
                    newTarefaStatus.setDataInicio(tarefaStatus.getDataFim());
                    newTarefaStatus.setDataFimFormat("");

                    if (tsc.cadastrar(newTarefaStatus)) {
                        Toast.makeText(this, "Alterado!", Toast.LENGTH_SHORT).show();
                        EditarTarefaActivity.this.finish();
                    }
                }
            }
        } else {
            Toast.makeText(this, "Erro!", Toast.LENGTH_SHORT).show();
        }
    }

    public void cancelarAlterar(View view) {
        EditarTarefaActivity.this.finish();
    }
}
