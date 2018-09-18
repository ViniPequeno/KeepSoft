package nescaupower.br.com.keepsoft.Views.Projeto;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import java.util.List;

import nescaupower.br.com.keepsoft.Controller.ProjetoController;
import nescaupower.br.com.keepsoft.Factory.Model.Projeto;
import nescaupower.br.com.keepsoft.Factory.Model.Usuario;
import nescaupower.br.com.keepsoft.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProjetosFragment extends Fragment {

    private ProjetoController pc;

    private List<Projeto> projetos;
    private Button btnCadastrar;
    private RecyclerView rv;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pc = new ProjetoController(getActivity().getApplicationContext());
        projetos = pc.listarPorUsuario(Usuario.getUsuarioLogado().getId());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rootView = inflater.inflate(R.layout.fragment_projeto_list, container, false);

        rv = rootView.findViewById(R.id.ProjetosRV);
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        rv.setLayoutManager(layoutManager);

        MyProjetoRVAdapter adapter = new MyProjetoRVAdapter(this.getActivity(), projetos);
        rv.setAdapter(adapter);

        btnCadastrar = rootView.findViewById(R.id.btnCadastrar);
        btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cadastrar();
            }
        });

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        MyProjetoRVAdapter adapter = (MyProjetoRVAdapter) rv.getAdapter();
        adapter.setProjetos(projetos = pc.listarPorUsuario(Usuario.getUsuarioLogado().getId()));
        Toast.makeText(getActivity(), " Voltei " + projetos.size(), Toast.LENGTH_SHORT).show();
        adapter.notifyDataSetChanged();
    }

    private void cadastrar() {
        Intent i = new Intent(getActivity(), CadastroProjetoActivity.class);
        startActivity(i);
    }

}
