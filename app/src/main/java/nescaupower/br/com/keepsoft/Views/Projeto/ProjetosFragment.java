package nescaupower.br.com.keepsoft.Views.Projeto;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

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
    private FloatingActionButton btnCadastrar;
    private RecyclerView rv;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pc = new ProjetoController(getActivity().getApplicationContext());
        projetos = pc.listarPorUsuarioParticipando(Usuario.getUsuarioLogado().getId());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Resources res = getContext().getResources();

        // Inflate the layout for this fragment
        final View rootView = inflater.inflate(R.layout.fragment_projeto_list, container, false);

        rv = rootView.findViewById(R.id.ProjetosRV);
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        rv.setLayoutManager(layoutManager);

        ProjetoRVAdapter adapter = new ProjetoRVAdapter(this.getActivity(), projetos);
        rv.setAdapter(adapter);

        btnCadastrar = rootView.findViewById(R.id.btnCadastrar);
        btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cadastrar();
            }
        });

        //Título
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(res.getString(R.string.projects));

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        ProjetoRVAdapter adapter = (ProjetoRVAdapter) rv.getAdapter();
        adapter.setProjetos(projetos = pc.listarPorUsuario(Usuario.getUsuarioLogado().getId()));
        adapter.notifyDataSetChanged();
    }

    private void cadastrar() {
        Intent i = new Intent(getActivity(), CadastroProjetoActivity.class);
        startActivity(i);
    }

}
