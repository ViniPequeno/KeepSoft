package nescaupower.br.com.keepsoft.Views.Projeto;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import nescaupower.br.com.keepsoft.Config.Settings;
import nescaupower.br.com.keepsoft.Controller.ProjetoController;
import nescaupower.br.com.keepsoft.Controller.ProjetoController;
import nescaupower.br.com.keepsoft.Factory.Model.Projeto;
import nescaupower.br.com.keepsoft.R;

public class EditarPorjetoActivity extends AppCompatActivity {

    //Calendar dataAtual = Calendar.getInstance();
    EditText editarNomeProjeto, editarDescricaoProjeto, editarDataPrevistaEntrega;
    ProjetoController pc;
    DatePickerDialog.OnDateSetListener seletorDataPrevista;
    Projeto projeto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_porjeto);

        pc = new ProjetoController(getApplicationContext());

        //Singleton
        Projeto projeto = Projeto.getGetProjeto();
        if (projeto == null) {
            SharedPreferences sharedPreferences = getSharedPreferences(Settings.SHARED_PREF_NAME, Context.MODE_PRIVATE);
            projeto = pc.procurarPeloCodigo(sharedPreferences.getLong(Settings.ID_PROJETO, 0));
        }

        editarNomeProjeto = findViewById(R.id.editarNomeProjeto);
        editarDescricaoProjeto = findViewById(R.id.editarDescricaoProjeto);
        editarDataPrevistaEntrega = findViewById(R.id.editarDataPrevistaEntrega);

        String data = projeto.getDataPrevFinalizacao().toString();
        data = new SimpleDateFormat("dd/MM/yyyy").format(projeto.getDataPrevFinalizacao());

        final int ano = Integer.parseInt(data.substring(6));
        final int mes = Integer.parseInt(data.substring(3, 5));
        final int dia = Integer.parseInt(data.substring(0, 2));

        editarNomeProjeto.setText(projeto.getNome());
        editarDescricaoProjeto.setText(projeto.getDescricao());
        editarDataPrevistaEntrega.setText(data);

        editarDataPrevistaEntrega.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    new DatePickerDialog(EditarPorjetoActivity.this, seletorDataPrevista, ano, mes, dia).show();
                }
            }
        });
    }
}
