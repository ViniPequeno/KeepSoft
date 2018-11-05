package nescaupower.br.com.keepsoft.Views.Tarefa;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
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
    private TextView perfilSelecionado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_tarefa);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        txtTitulo = findViewById(R.id.txtTitulo);
        txtDescricao = findViewById(R.id.txtDescricao);
        spinStatus = findViewById(R.id.spinStatus);
        spinUsuario = findViewById(R.id.spinUsuario);
        perfilSelecionado = findViewById(R.id.selectedOffer);

        perfis = pc.listarPorProjeto(Projeto.getUltimoProjetoUsado().getCodigo());

        CustomArrayAdapter adapter = new CustomArrayAdapter(CadastroTarefaActivity.this, R.layout.fragment_equipe_model, perfis);
        spinUsuario.setAdapter(adapter);
        spinUsuario.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                Toast.makeText(CadastroTarefaActivity.this, "o", Toast.LENGTH_SHORT).show();
                String item = ((TextView) view.findViewById(R.id.lblNome)).getText().toString();
                perfilSelecionado.setText(item);
            }

            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void cadastrar(View v) {

    }

    public class CustomArrayAdapter extends ArrayAdapter<String> {

        private final LayoutInflater mInflater;
        private final Context mContext;
        private final List<Perfil> perfis;
        private final int mResource;

        public CustomArrayAdapter(@NonNull Context context, @LayoutRes int resource,
                                  @NonNull List objects) {
            super(context, resource, 0, objects);

            mContext = context;
            mInflater = LayoutInflater.from(context);
            mResource = resource;
            perfis = objects;
        }

        @NonNull
        @Override
        public View getView(int position, View convertView, @NonNull ViewGroup parent) {
            /*TextView text = new TextView(CadastroTarefaActivity.this);
            text.setText(perfis.get(position).getUsuario().getNome());
            return text;*/
            return createItemView(position, convertView, parent);
        }

        @Override
        public View getDropDownView(int position, View convertView, ViewGroup parent) {
            View v = View.inflate(CadastroTarefaActivity.this, R.layout.fragment_equipe_model, parent);
            return super.getDropDownView(position, convertView, parent);
        }

        private View createItemView(int position, View convertView, ViewGroup parent) {
            final View view = mInflater.inflate(mResource, parent, false);

            TextView lblNome = view.findViewById(R.id.lblNome);
            TextView lblFuncao = view.findViewById(R.id.lblFuncao);

            Perfil perfil = perfis.get(position);

            lblNome.setText(perfil.getUsuario().getNome());
            lblFuncao.setText(perfil.getPerfil().toString());

            return view;
        }
    }
}
