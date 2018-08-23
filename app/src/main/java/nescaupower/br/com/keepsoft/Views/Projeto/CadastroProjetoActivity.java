package nescaupower.br.com.keepsoft.Views.Projeto;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import nescaupower.br.com.keepsoft.Controller.ProjetoController;
import nescaupower.br.com.keepsoft.Factory.Factory;
import nescaupower.br.com.keepsoft.Factory.Model.Projeto;
import nescaupower.br.com.keepsoft.Factory.Model.Usuario;
import nescaupower.br.com.keepsoft.R;
import nescaupower.br.com.keepsoft.Views.Usuario.PaginaInicialActivity;

public class CadastroProjetoActivity extends AppCompatActivity {

    EditText txtNome, txtDescricao, txtDataPrevista;
    ProjetoController pc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_projeto);

        pc = new ProjetoController(getApplicationContext());

        txtNome = findViewById(R.id.txtNome);
        txtDescricao = findViewById(R.id.txtDescricao);
        txtDataPrevista = findViewById(R.id.txtDataPrevista);
    }

    public void cadastrar(View v) {
        Projeto p = Factory.startProjeto();

        p.setNome(txtNome.getText().toString());
        p.setDescricao(txtDescricao.getText().toString());
        p.setDataCriacao(Calendar.getInstance().getTime());

        try {
            p.setDataFinalizacao(new SimpleDateFormat("dd/MM/yy").parse(txtDataPrevista.getText().toString()));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        p.setIdUsuario(Usuario.getUsuario_logado().getId());

        boolean cadastrou = pc.cadastroProjeto(p);

        if (cadastrou) {
            Intent i = new Intent(CadastroProjetoActivity.this, PaginaInicialActivity.class);
            startActivity(i);
            CadastroProjetoActivity.this.finish();
        } else {
            Toast.makeText(this, "Projeto já existe", Toast.LENGTH_SHORT).show();
        }
    }

}
