package nescaupower.br.com.keepsoft.Views.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import nescaupower.br.com.keepsoft.Views.MyAdapter;
import nescaupower.br.com.keepsoft.Views.Projeto.CadastroProjeto;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProjetosFragment extends Fragment {

    ProjetoController pc;

    List<Projeto> projetos;
    Button btnCadastrar;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rootView = inflater.inflate(R.layout.fragment_projetos, container, false);

        pc = new ProjetoController(getActivity().getApplicationContext());
        projetos = pc.listarProjetosPorUsuario(Usuario.getUsuario_logado().getId());
        Toast.makeText(getContext(), " " + projetos.size(), Toast.LENGTH_SHORT);

        RecyclerView rv = rootView.findViewById(R.id.ProjetosRV);
        rv.setLayoutManager(new LinearLayoutManager(this.getActivity()));

        MyAdapter adapter = new MyAdapter(this.getActivity(), projetos);
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
        Intent i = new Intent(getActivity(), CadastroProjeto.class);
        startActivity(i);
        getActivity().finish();
    }

}
