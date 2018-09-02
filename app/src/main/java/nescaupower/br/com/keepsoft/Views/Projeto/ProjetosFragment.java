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

import java.util.List;

import nescaupower.br.com.keepsoft.Controller.ProjetoController;
import nescaupower.br.com.keepsoft.Factory.Model.Projeto;
import nescaupower.br.com.keepsoft.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProjetosFragment extends Fragment {

    private ProjetoController pc;

    private List<Projeto> projetos;
    private Button btnCadastrar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pc = new ProjetoController(getActivity().getApplicationContext());
        //projetos = pc.listarProjetosPorUsuario(Usuario.getUsuarioLogado().getId());
        projetos = pc.listarTodos();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rootView = inflater.inflate(R.layout.fragment_projetos, container, false);

        RecyclerView rv = rootView.findViewById(R.id.ProjetosRV);
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        rv.setLayoutManager(layoutManager);

        MyProjetoRecyclerViewAdapter adapter = new MyProjetoRecyclerViewAdapter(this.getActivity(), projetos);
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

    private void cadastrar() {
        Intent i = new Intent(getActivity(), CadastroProjetoActivity.class);
        startActivity(i);
    }

}
