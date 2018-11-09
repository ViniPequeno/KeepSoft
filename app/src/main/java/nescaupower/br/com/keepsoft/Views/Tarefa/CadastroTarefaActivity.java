package nescaupower.br.com.keepsoft.Views.Tarefa;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.List;

import nescaupower.br.com.keepsoft.Controller.PerfilController;
import nescaupower.br.com.keepsoft.Enum.Dificuldade;
import nescaupower.br.com.keepsoft.Enum.Prioridade;
import nescaupower.br.com.keepsoft.Factory.Model.Perfil;
import nescaupower.br.com.keepsoft.Factory.Model.Projeto;
import nescaupower.br.com.keepsoft.Factory.Model.Tarefa;
import nescaupower.br.com.keepsoft.R;

public class CadastroTarefaActivity extends AppCompatActivity {

    EditText txtTitulo;
    EditText txtDescricao;
    Spinner spinStatus;
    Spinner spinPrioridade;
    Spinner spinDificuldade;
    Spinner spinUsuario;
    Perfil perfilSelecionado = null;
    List<Perfil> perfis;

    PerfilController pc = new PerfilController();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_tarefa);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        txtTitulo = findViewById(R.id.txtTitulo);
        txtDescricao = findViewById(R.id.txtDescricao);
        spinStatus = findViewById(R.id.spinStatus);
        spinPrioridade = findViewById(R.id.spinPrioridade);
        spinDificuldade = findViewById(R.id.spinDificuldade);
        spinUsuario = findViewById(R.id.spinUsuario);

        perfis = pc.listarPorProjeto(Projeto.getUltimoProjetoUsado().getCodigo());

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
    }

    public void cadastrar(View v) {
        Tarefa tarefa = new Tarefa();
        tarefa.setTitulo(txtTitulo.getText().toString());
        tarefa.setDescricao(txtDescricao.getText().toString());
        tarefa.setPrioridade(Prioridade.values()[spinPrioridade.getSelectedItemPosition()]);
        tarefa.setDificuldade(Dificuldade.values()[spinDificuldade.getSelectedItemPosition()]);
        tarefa.setPerfil(perfilSelecionado);
        Toast.makeText(this, Dificuldade.values()[spinDificuldade.getSelectedItemPosition()].toString(), Toast.LENGTH_SHORT).show();
    }

}
