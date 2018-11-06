package nescaupower.br.com.keepsoft.Views.Reuniao;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import nescaupower.br.com.keepsoft.Views.Sprint.CadastroSprintActivity;

public class CadastrarReuniao extends AppCompatActivity {

    private ConstraintLayout root;
    private Calendar dataAtual = Calendar.getInstance();
    private EditText txtReuniaoTitulo;
    private EditText txtReuniaoAssunto;
    private EditText txtReuniaoDataInicio;
    private EditText txtReuniaoDataFim;
    private EditText txtReuniaoHoraInicio;
    private EditText txtReuniaoHoraFim;
    private ReuniaoController rc;


    private DatePickerDialog dialogDataInicio, dialogDataFim;
    private DatePickerDialog.OnDateSetListener listenerDataSelecionadaDataInicio, listenerDataSelecionadaDataFim;
    private DatePickerDialog.OnCancelListener listenerSelecaoCanceladaDataInicio, listenerSelecaoCanceladaDataFim;

    private TimePickerDialog dialogHoraInicio, dialogHoraFim;
    private TimePickerDialog.OnTimeSetListener listenerHoraDataInicio, listenerHoraDataFim;
    private TimePickerDialog.OnCancelListener listenerHoraDataInicioC, listenerHoraDataFimC;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar_reuniao);

        root =findViewById(R.id.reuniao);

        rc = new ReuniaoController();

        txtReuniaoTitulo = findViewById(R.id.txtNomeReuniao);
        txtReuniaoAssunto = findViewById(R.id.txtAssuntoReuniao);
        //Datas
        txtReuniaoDataInicio = findViewById(R.id.txtReuniaoDataInicio);
        txtReuniaoDataFim = findViewById(R.id.txtReuniaoDataFim);

        txtReuniaoHoraInicio = findViewById(R.id.txtReuniaoHoraInicio);
        txtReuniaoHoraFim = findViewById(R.id.txtReuniaoHoraFim);

        /////////////
        txtReuniaoHoraInicio.setOnFocusChangeListener((v, hasFocus) ->{
            if(hasFocus){
                dialogHoraInicio.show();
            }
        });

        listenerHoraDataInicio = (view, hour, minute) ->{
            String hora = ""+hour;
            if(hour < 10){
                hora = "0"+hour;
            }
            String minutos = ""+minute;
            if(minute < 10){
                minutos = "0"+minute;
            }
            txtReuniaoHoraInicio.setText(hora + ":" +minutos);
            root.clearFocus();
        };

        listenerHoraDataInicioC = dialogInterface -> root.clearFocus();

        dialogHoraInicio = new TimePickerDialog(CadastrarReuniao.this, listenerHoraDataInicio, 12, 0, true);
        dialogHoraInicio.setOnCancelListener(listenerHoraDataInicioC);;
        /////


        ///////////// Hora FIM
        txtReuniaoHoraFim.setOnFocusChangeListener((v, hasFocus) ->{
            if(hasFocus){
                dialogHoraFim.show();
            }
        });

        listenerHoraDataFim = (view, hour, minute) ->{
            String hora = ""+hour;
            if(hour < 10){
                hora = "0"+hour;
            }
            String minutos = ""+minute;
            if(minute < 10){
                minutos = "0"+minute;
            }
            txtReuniaoHoraFim.setText(hora + ":" +minutos);
            root.clearFocus();
        };

        listenerHoraDataFimC = dialogInterface -> root.clearFocus();

        dialogHoraFim =
                new TimePickerDialog(CadastrarReuniao.this, listenerHoraDataFim,
                        12, 00, true);
        dialogHoraFim.setOnCancelListener(listenerHoraDataFimC);;
        /////


        txtReuniaoDataInicio.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                dialogDataInicio.show();
            }
        });

        //TODO: ajustar foco após selecionar data
        listenerDataSelecionadaDataInicio = (view, year, month, dayOfMonth) -> {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            Date data = new GregorianCalendar(year, month, dayOfMonth).getTime();
            txtReuniaoDataInicio.setText(sdf.format(data));
            root.clearFocus();
            dialogHoraInicio.show();
        };

        listenerSelecaoCanceladaDataInicio = dialogInterface -> root.clearFocus();

        dialogDataInicio = new DatePickerDialog(CadastrarReuniao.this, listenerDataSelecionadaDataInicio, dataAtual
                .get(Calendar.YEAR), dataAtual.get(Calendar.MONTH), dataAtual.get(Calendar.DAY_OF_MONTH));
        dialogDataInicio.setOnCancelListener(listenerSelecaoCanceladaDataInicio);

        txtReuniaoDataFim.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                dialogDataFim.show();
            }
        });
        //TODO: ajustar foco após selecionar data
        listenerDataSelecionadaDataFim = (view, year, month, dayOfMonth) -> {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            Date data = new GregorianCalendar(year, month, dayOfMonth).getTime();
            txtReuniaoDataFim.setText(sdf.format(data));
            root.clearFocus();
            dialogDataFim.show();
        };
        listenerSelecaoCanceladaDataFim = dialogInterface -> root.clearFocus();

        dialogDataFim = new DatePickerDialog(CadastrarReuniao.this, listenerDataSelecionadaDataFim, dataAtual
                .get(Calendar.YEAR), dataAtual.get(Calendar.MONTH), dataAtual.get(Calendar.DAY_OF_MONTH));
        dialogDataFim.setOnCancelListener(listenerSelecaoCanceladaDataFim);
    }

    public void cadastrar(View view) {
        if (txtReuniaoTitulo.getText().toString() == "") {
            Toast.makeText(this, "A reunião deve ter um nome", Toast.LENGTH_SHORT).show();
            return;
        } else if (txtReuniaoAssunto.getText().toString() == "") {
            Toast.makeText(this, "A reunião deve ter um assunto", Toast.LENGTH_SHORT).show();
            return;
        } else if (txtReuniaoDataInicio.getText().toString() == "") {
            Toast.makeText(this, "A reunião deve ter uma data de início", Toast.LENGTH_SHORT).show();
            return;
        } else if (txtReuniaoDataFim.getText().toString() == "") {
            Toast.makeText(this, "A reunião deve ter uma data de finalização", Toast.LENGTH_SHORT).show();
            return;
        } else if (txtReuniaoHoraInicio.getText().toString() == "") {
            Toast.makeText(this, "A reunião deve ter uma hora de início", Toast.LENGTH_SHORT).show();
            return;
        }else if (txtReuniaoHoraFim.getText().toString() == "") {
            Toast.makeText(this, "A reunião deve ter uma hora de finalização", Toast.LENGTH_SHORT).show();
            return;
        }

        Reuniao r = new Reuniao();
        r.setNome(txtReuniaoTitulo.getText().toString());
        r.setAssunto(txtReuniaoAssunto.getText().toString());
        r.setHoraInicioFormat(txtReuniaoHoraInicio.getText().toString());
        r.setHoraFimFormat(txtReuniaoHoraFim.getText().toString());
        try {
            r.setDataInicio(new SimpleDateFormat("dd/MM/yyyy").parse(txtReuniaoDataInicio.getText().toString()));
        } catch ( ParseException e ) {
            e.printStackTrace();
        }

        try {
            r.setDataFim(new SimpleDateFormat("dd/MM/yyyy").parse(txtReuniaoDataFim.getText().toString()));
        } catch ( ParseException e ) {
            e.printStackTrace();
        }

        r.setProjeto(Projeto.getUltimoProjetoUsado());

        if(rc.cadastrar(r)){
            Intent i = new Intent(CadastrarReuniao.this, ReuniaoActivity.class);
            startActivity(i);
            CadastrarReuniao.this.finish();
        }else{
            Toast.makeText(this, "Reunião já existe", Toast.LENGTH_SHORT).show();
        }
    }
}
