package nescaupower.br.com.keepsoft.Views.Tarefa;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

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

        SpinUsuarioAdapter adapter = new SpinUsuarioAdapter(CadastroTarefaActivity.this, perfis);
        spinUsuario.setAdapter(adapter);
        spinUsuario.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                Perfil p = (Perfil) parent.getItemAtPosition(pos);
                Toast.makeText(CadastroTarefaActivity.this, p.getUsuario().getNome(), Toast.LENGTH_SHORT).show();
            }

            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void cadastrar(View v) {

    }

    public class SpinUsuarioAdapter extends ArrayAdapter {

        public SpinUsuarioAdapter(@NonNull Context context, @NonNull List<Perfil> perfis) {
            super(context, 0, perfis);
        }

        @NonNull
        @Override
        public View getView(int position, View convertView, @NonNull ViewGroup parent) {
            /*TextView text = new TextView(CadastroTarefaActivity.this);
            text.setText(perfis.get(position).getUsuario().getNome());
            return text;*/
            //return createItemView(position, convertView, parent);
            return initView(position, convertView, parent);
        }

        @Override
        public View getDropDownView(int position, View convertView, ViewGroup parent) {
            TextView v = (TextView) View.inflate(mContext, android.R.layout.simple_spinner_dropdown_item,null);
            v.setText(perfis.get(position).getUsuario().getNome());
            return v;
        }

        private View initView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.fragment_equipe_model, parent, false);
            }
            convertView.setFocusable(false);
            convertView.setClickable(false);

            TextView lblNome = convertView.findViewById(R.id.lblNome);
            TextView lblFuncao = convertView.findViewById(R.id.lblFuncao);
            TextView lblObs = convertView.findViewById(R.id.lblObs);

            Perfil p = perfis.get(position);

            if (p != null) {
                lblNome.setText(p.getUsuario().getNome());
                lblFuncao.setText(p.getPerfil().toString());
                lblObs.setText(null);
            }
            return convertView;
        }
    }
}
