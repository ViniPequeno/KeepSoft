package nescaupower.br.com.keepsoft.Views.Tarefa;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import nescaupower.br.com.keepsoft.Controller.TarefaController;
import nescaupower.br.com.keepsoft.Factory.Model.Projeto;
import nescaupower.br.com.keepsoft.Factory.Model.Tarefa;
import nescaupower.br.com.keepsoft.R;

/**
 * A fragment representing a perfis of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class TarefaFragment extends Fragment {

    private TarefaController tc;
    private List<Tarefa> tarefaList;

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private OnListFragmentInteractionListener mListener;

    private RecyclerView rv;
    private FloatingActionButton btnCadastrar;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public TarefaFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static TarefaFragment newInstance(int columnCount) {
        TarefaFragment fragment = new TarefaFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tc = new TarefaController();
        tarefaList = tc.listarPorProjeto(Projeto.getUltimoProjetoUsado().getCodigo());

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_tarefa_list, container, false);
        rv = rootView.findViewById(R.id.TarefasRV);

        // Set the adapter
        if (rv != null) {
            Context context = rv.getContext();
            RecyclerView recyclerView = rv;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            recyclerView.setAdapter(new TarefaRVAdapter(tarefaList, mListener));
        }

        btnCadastrar = rootView.findViewById(R.id.btnCadastrar);
        btnCadastrar.setOnClickListener(view -> cadastrar());
        
        return rootView;
    }

    private void cadastrar() {
        Intent intent = new Intent(getActivity(), CadastroTarefaActivity.class);
        startActivity(intent);
    }

    @Override
    public void onResume() {
        super.onResume();
        TarefaRVAdapter adapter = (TarefaRVAdapter) rv.getAdapter();
        adapter.setTarefas(tarefaList = tc.listarPorProjeto(Projeto.getUltimoProjetoUsado().getCodigo()));
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(Tarefa item);
    }
}
