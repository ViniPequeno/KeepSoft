package nescaupower.br.com.keepsoft.Views.Reuniao;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import nescaupower.br.com.keepsoft.Controller.ReuniaoController;
import nescaupower.br.com.keepsoft.Factory.Model.Projeto;
import nescaupower.br.com.keepsoft.Factory.Model.Reuniao;
import nescaupower.br.com.keepsoft.R;

public class EditarReuniaoActivity extends AppCompatActivity {

    private ConstraintLayout root;
    private Calendar dataAtual = Calendar.getInstance();
    private EditText txtReuniaoTituloEditar;
    private EditText txtReuniaoAssuntoEditar;
    private EditText txtReuniaoDataInicioEditar;
    private EditText txtReuniaoHoraInicioEditar;
    private EditText txtReuniaoHoraFimEditar;
    private EditText txtReuniaoLocalEditar;
    private ReuniaoController rc;
    private Reuniao reuniao;


    private DatePickerDialog dialogDataInicio;
    private DatePickerDialog.OnDateSetListener listenerDataSelecionadaDataInicio;
    private DatePickerDialog.OnCancelListener listenerSelecaoCanceladaDataInicio;

    private TimePickerDialog dialogHoraInicio, dialogHoraFim;
    private TimePickerDialog.OnTimeSetListener listenerHoraDataInicio, listenerHoraDataFim;
    private TimePickerDialog.OnCancelListener listenerHoraDataInicioC, listenerHoraDataFimC;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_reuniao);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        root = findViewById(R.id.reuniaoEditar);

        rc = new ReuniaoController();
        reuniao = rc.getReuniao(getIntent().getLongExtra("EXTRA_CODIGO_REUNIAO", 0));

        txtReuniaoTituloEditar = findViewById(R.id.txtNomeReuniaoEditar);
        txtReuniaoAssuntoEditar = findViewById(R.id.txtAssuntoReuniaoEditar);
        txtReuniaoLocalEditar = findViewById(R.id.txtLocalReuniaoEditar);

        //Datas
        txtReuniaoDataInicioEditar = findViewById(R.id.txtReuniaoDataInicioEditar);

        txtReuniaoHoraInicioEditar = findViewById(R.id.txtReuniaoHoraInicioEditar);
        txtReuniaoHoraFimEditar = findViewById(R.id.txtReuniaoHoraFimEditar);


        if (reuniao != null) {
            txtReuniaoTituloEditar.setText(reuniao.getNome());
            txtReuniaoAssuntoEditar.setText(reuniao.getAssunto());
            txtReuniaoLocalEditar.setText(reuniao.getLocal());
            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
            txtReuniaoDataInicioEditar.setText(format.format(reuniao.getDataInicio()));
            txtReuniaoHoraInicioEditar.setText(reuniao.getHoraInicioFormat());
            txtReuniaoHoraFimEditar.setText(reuniao.getHoraFimFormat());
        }


        /////////////
        txtReuniaoHoraInicioEditar.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                dialogHoraInicio.show();
            }
        });

        listenerHoraDataInicio = (view, hour, minute) -> {
            String hora = "" + hour;
            if (hour < 10) {
                hora = "0" + hour;
            }
            String minutos = "" + minute;
            if (minute < 10) {
                minutos = "0" + minute;
            }
            txtReuniaoHoraInicioEditar.setText(hora + ":" + minutos);
            root.clearFocus();
        };

        listenerHoraDataInicioC = dialogInterface -> root.clearFocus();


        int horaInicial = Integer.parseInt(reuniao.getHoraInicioFormat().split(":")[0]);
        int minutoInicial = Integer.parseInt(reuniao.getHoraInicioFormat().split(":")[1]);
        dialogHoraInicio = new TimePickerDialog(EditarReuniaoActivity.this, listenerHoraDataInicio,
                horaInicial, minutoInicial, true);
        dialogHoraInicio.setOnCancelListener(listenerHoraDataInicioC);
        /////


        ///////////// Hora FIM
        txtReuniaoHoraFimEditar.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                dialogHoraFim.show();
            }
        });

        listenerHoraDataFim = (view, hour, minute) -> {
            String hora = "" + hour;
            if (hour < 10) {
                hora = "0" + hour;
            }
            String minutos = "" + minute;
            if (minute < 10) {
                minutos = "0" + minute;
            }
            txtReuniaoHoraFimEditar.setText(hora + ":" + minutos);
            root.clearFocus();
        };

        listenerHoraDataFimC = dialogInterface -> root.clearFocus();

        int horaFim = Integer.parseInt(reuniao.getHoraFimFormat().split(":")[0]);
        int minutoFim = Integer.parseInt(reuniao.getHoraFimFormat().split(":")[1]);
        dialogHoraFim =
                new TimePickerDialog(EditarReuniaoActivity.this, listenerHoraDataFim,
                        horaFim, minutoFim, true);
        dialogHoraFim.setOnCancelListener(listenerHoraDataFimC);
        /////


        txtReuniaoDataInicioEditar.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                dialogDataInicio.show();
            }
        });

        //TODO: ajustar foco após selecionar data
        listenerDataSelecionadaDataInicio = (view, year, month, dayOfMonth) -> {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            Date data = new GregorianCalendar(year, month, dayOfMonth).getTime();
            txtReuniaoDataInicioEditar.setText(sdf.format(data));
            root.clearFocus();
            dialogHoraInicio.show();
        };

        listenerSelecaoCanceladaDataInicio = dialogInterface -> root.clearFocus();

        dialogDataInicio = new DatePickerDialog(EditarReuniaoActivity.this, listenerDataSelecionadaDataInicio,
                dataAtual.get(Calendar.YEAR), dataAtual.get(Calendar.MONTH), dataAtual.get(Calendar.DAY_OF_MONTH));
        dialogDataInicio.setOnCancelListener(listenerSelecaoCanceladaDataInicio);

    }

    public void editar(View view) {
        if (txtReuniaoTituloEditar.getText().toString() == "") {
            Toast.makeText(this, "A reunião deve ter um nome", Toast.LENGTH_SHORT).show();
            return;
        } else if (txtReuniaoAssuntoEditar.getText().toString() == "") {
            Toast.makeText(this, "A reunião deve ter um assunto", Toast.LENGTH_SHORT).show();
            return;
        } else if (txtReuniaoDataInicioEditar.getText().toString() == "") {
            Toast.makeText(this, "A reunião deve ter uma data de início", Toast.LENGTH_SHORT).show();
            return;
        } else if (txtReuniaoHoraInicioEditar.getText().toString() == "") {
            Toast.makeText(this, "A reunião deve ter uma hora de início", Toast.LENGTH_SHORT).show();
            return;
        } else if (txtReuniaoHoraFimEditar.getText().toString() == "") {
            Toast.makeText(this, "A reunião deve ter uma hora de finalização", Toast.LENGTH_SHORT).show();
            return;
        } else if (txtReuniaoLocalEditar.getText().toString() == "") {
            Toast.makeText(this, "A reunião deve ter um local", Toast.LENGTH_SHORT).show();
            return;
        }

        reuniao.setNome(txtReuniaoTituloEditar.getText().toString());
        reuniao.setAssunto(txtReuniaoAssuntoEditar.getText().toString());
        reuniao.setHoraInicioFormat(txtReuniaoHoraInicioEditar.getText().toString());
        reuniao.setHoraFimFormat(txtReuniaoHoraFimEditar.getText().toString());

        reuniao.setLocal(txtReuniaoLocalEditar.getText().toString());
        try {
            reuniao.setDataInicio(new SimpleDateFormat("dd/MM/yyyy").parse(txtReuniaoDataInicioEditar.getText().toString()));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        reuniao.setProjeto(Projeto.getUltimoProjetoUsado());

        if(rc.atualizar(reuniao) != null) {
            Intent i = new Intent(EditarReuniaoActivity.this, ReuniaoActivity.class);
            startActivity(i);
            EditarReuniaoActivity.this.finish();
        }else{
            Toast.makeText(this, "A reunião já existi!", Toast.LENGTH_SHORT).show();
        }

    }
}
