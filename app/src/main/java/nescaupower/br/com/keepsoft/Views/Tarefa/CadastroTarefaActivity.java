package nescaupower.br.com.keepsoft.Views.Tarefa;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import java.util.List;

import nescaupower.br.com.keepsoft.Controller.PerfilController;
import nescaupower.br.com.keepsoft.Factory.Model.Perfil;
import nescaupower.br.com.keepsoft.Factory.Model.Projeto;
import nescaupower.br.com.keepsoft.R;

public class CadastroTarefaActivity extends AppCompatActivity {

    EditText txtTitulo;
    EditText txtDescricao;
    Spinner spinStatus;
    Spinner spinUsuario;
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
        spinUsuario = findViewById(R.id.spinUsuario);

        perfis = pc.listarPorProjeto(Projeto.getUltimoProjetoUsado().getCodigo());
        spinUsuario.setAdapter(new ListAdapter());
    }

    public void cadastrar(View v) {

    }

    private class ListAdapter extends BaseAdapter implements SpinnerAdapter {

        @Override
        public int getCount() {
            return perfis.size();
        }

        @Override
        public Object getItem(int position) {
            return perfis.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View view, ViewGroup parent) {
            TextView text = new TextView(CadastroTarefaActivity.this);
            text.setText(perfis.get(position).getUsuario().getNome());
            return text;
        }

        @Override
        public View getDropDownView(int position, View convertView, ViewGroup parent) {
            View v = View.inflate(CadastroTarefaActivity.this, R.layout.fragment_equipe_model, parent);
            return super.getDropDownView(position, convertView, parent);
        }
    }
}
