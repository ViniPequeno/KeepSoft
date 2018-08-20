package nescaupower.br.com.keepsoft.Views.Projeto;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;

import nescaupower.br.com.keepsoft.Controller.ProjetoController;
import nescaupower.br.com.keepsoft.Factory.Factory;
import nescaupower.br.com.keepsoft.Factory.Model.Projeto;
import nescaupower.br.com.keepsoft.R;
import nescaupower.br.com.keepsoft.Views.Usuario.PaginaInicial;

public class CadastroProjeto extends Activity {

    EditText txtNome, txtDescricao;
    ProjetoController pc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_projeto);

        pc = new ProjetoController(getApplicationContext());

        txtNome = findViewById(R.id.txtNome);
        txtDescricao = findViewById(R.id.txtDescricao);
    }

    public void cadastrar(View v) {
        Projeto p = Factory.startProjeto();
        p.setNome(txtNome.getText().toString());
        p.setDescricao(txtDescricao.getText().toString());
        p.setDataCriacao(Calendar.getInstance().getTime());

        boolean cadastrou = pc.cadastroProjeto(p);

        if (cadastrou) {
            Intent i = new Intent(CadastroProjeto.this, PaginaInicial.class);
            startActivity(i);
            CadastroProjeto.this.finish();
        } else {
            Toast.makeText(this, "Projeto já existe", Toast.LENGTH_SHORT).show();
        }
    }

}
