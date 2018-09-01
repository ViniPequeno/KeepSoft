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

import nescaupower.br.com.keepsoft.Config.Settings;
import nescaupower.br.com.keepsoft.Controller.ProjetoController;
import nescaupower.br.com.keepsoft.Factory.Model.Projeto;
import nescaupower.br.com.keepsoft.R;

public class EditarProjetoActivity extends AppCompatActivity {

    //Calendar dataAtual = Calendar.getInstance();
    EditText editarNomeProjeto, editarDescricaoProjeto, editarDataPrevistaEntrega;
    Button btnAlterarProjeto, btnCancelarAlterarProjeto;
    ProjetoController pc;
    DatePickerDialog.OnDateSetListener seletorDataPrevista;
    Projeto projeto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_projeto);

        pc = new ProjetoController(getApplicationContext());

        //Singleton
        projeto = Projeto.getGetProjeto();
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
                    new DatePickerDialog(EditarProjetoActivity.this, seletorDataPrevista, ano, mes, dia).show();
                }
            }
        });
    }

    public void alterarProjeto(View view) {
        if (editarNomeProjeto.getText().toString() == "" || editarNomeProjeto.getText().toString() == null) {
            Toast.makeText(this, "O Projeto deve ter um nome", Toast.LENGTH_SHORT).show();
            return;
        } else if (editarDescricaoProjeto.getText().toString() == "" || editarDescricaoProjeto.getText().toString() == null) {
            Toast.makeText(this, "O Projeto deve ter um descrição", Toast.LENGTH_SHORT).show();
            return;
        } else if (editarDataPrevistaEntrega.getText().toString() == "" || editarDataPrevistaEntrega.getText().toString() == null) {
            Toast.makeText(this, "O Projeto deve ter uma data de previsão final", Toast.LENGTH_SHORT).show();
            return;
        }

        projeto.setNome(editarNomeProjeto.getText().toString());
        projeto.setDescricao(editarDescricaoProjeto.getText().toString());
        try {
            projeto.setDataPrevFinalizacao(new SimpleDateFormat("dd/MM/yyyy").
                    parse(editarDataPrevistaEntrega.getText().toString()));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        pc.updateProjeto(projeto);
        Projeto.setGetProjeto(projeto);

        Toast.makeText(this, "Alterado!", Toast.LENGTH_SHORT).show();
        Intent intent;
        intent = new Intent(EditarProjetoActivity.this, DetalhesProjetoActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK); // adiciona a flag para a intent
        startActivity(intent);
        EditarProjetoActivity.this.finish();
        return;
    }

    public void cancelarAlterarProjeto(View view){
        Intent intent;
        intent = new Intent(EditarProjetoActivity.this, DetalhesProjetoActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK); // adiciona a flag para a intent
        startActivity(intent);
        EditarProjetoActivity.this.finish();
    }
}
