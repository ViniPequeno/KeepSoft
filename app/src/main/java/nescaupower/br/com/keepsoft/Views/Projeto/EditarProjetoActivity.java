package nescaupower.br.com.keepsoft.Views.Projeto;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import nescaupower.br.com.keepsoft.Config.Settings;
import nescaupower.br.com.keepsoft.Controller.ProjetoController;
import nescaupower.br.com.keepsoft.Factory.Model.Projeto;
import nescaupower.br.com.keepsoft.R;

public class EditarProjetoActivity extends AppCompatActivity {

    Button btnAlterarProjeto, btnCancelarAlterarProjeto;
    //Calendar dataAtual = Calendar.getInstance();
    private EditText txtNome;
    private EditText txtDescricao;
    private EditText txtDataPrevista;
    private ProjetoController pc;
    private DatePickerDialog dialogDataPrevista;
    private DatePickerDialog.OnDateSetListener seletorDataPrevista;
    private Projeto projeto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_projeto);

        pc = new ProjetoController();

        //Singleton
        projeto = Projeto.getUltimoProjetoUsado();
        if (projeto == null) {
            SharedPreferences sharedPreferences = getSharedPreferences(Settings.SHARED_PREF_NAME, Context.MODE_PRIVATE);
            projeto = pc.procurarPorCodigo(sharedPreferences.getLong(Settings.ID_PROJETO, 0));
        }

        txtNome = findViewById(R.id.editarNomeProjeto);
        txtDescricao = findViewById(R.id.editarDescricaoProjeto);
        txtDataPrevista = findViewById(R.id.editarDataPrevistaEntrega);

        String data = new SimpleDateFormat("dd/MM/yyyy").format(projeto.getDataPrevFinalizacao());

        final int ano = Integer.parseInt(data.substring(6));
        final int mes = Integer.parseInt(data.substring(3, 5));
        final int dia = Integer.parseInt(data.substring(0, 2));

        txtNome.setText(projeto.getNome());
        txtDescricao.setText(projeto.getDescricao());
        txtDataPrevista.setText(data);

        txtDataPrevista.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                dialogDataPrevista.show();
            }
        });
        seletorDataPrevista = (view, year, month, dayOfMonth) -> {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            Date data1 = new GregorianCalendar(year, month, dayOfMonth).getTime();
            txtDataPrevista.setText(sdf.format(data1));
            txtDataPrevista.clearFocus();
            getCurrentFocus().clearFocus();
        };
        dialogDataPrevista = new DatePickerDialog(EditarProjetoActivity.this, seletorDataPrevista, ano, mes, dia);
        dialogDataPrevista.getDatePicker().setMinDate(Calendar.getInstance().getTimeInMillis());
    }

    public void alterarProjeto(View view) {
        if (txtNome.getText().toString().equals("")) {
            Toast.makeText(this, "O Projeto deve ter um nome", Toast.LENGTH_SHORT).show();
            return;
        } else if (txtDescricao.getText().toString().equals("")) {
            Toast.makeText(this, "O Projeto deve ter um descrição", Toast.LENGTH_SHORT).show();
            return;
        } else if (txtDataPrevista.getText().toString().equals("")) {
            Toast.makeText(this, "O Projeto deve ter uma data de previsão final", Toast.LENGTH_SHORT).show();
            return;
        }

        projeto.setNome(txtNome.getText().toString());
        projeto.setDescricao(txtDescricao.getText().toString());
        try {
            projeto.setDataPrevFinalizacao(new SimpleDateFormat("dd/MM/yyyy").
                    parse(txtDataPrevista.getText().toString()));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        pc.atualizar(projeto);
        Projeto.setUltimoProjetoUsado(projeto);

        Toast.makeText(this, "Alterado!", Toast.LENGTH_SHORT).show();
        Intent intent;
        intent = new Intent(EditarProjetoActivity.this, DetalhesProjetoActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK); // adiciona a flag para a intent
        startActivity(intent);
        EditarProjetoActivity.this.finish();
    }

    public void cancelarAlterarProjeto(View view){
        Intent intent;
        intent = new Intent(EditarProjetoActivity.this, DetalhesProjetoActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK); // adiciona a flag para a intent
        startActivity(intent);
        EditarProjetoActivity.this.finish();
    }
}
