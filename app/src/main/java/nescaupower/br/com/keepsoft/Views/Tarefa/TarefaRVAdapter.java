package nescaupower.br.com.keepsoft.Views.Tarefa;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import nescaupower.br.com.keepsoft.Factory.Model.Tarefa;
import nescaupower.br.com.keepsoft.R;
import nescaupower.br.com.keepsoft.Views.Tarefa.TarefaFragment.OnListFragmentInteractionListener;
import nescaupower.br.com.keepsoft.Views.Tarefa.dummy.DummyContent.DummyItem;

/**
 * {@link RecyclerView.Adapter} that can display a {@link DummyItem} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class TarefaRVAdapter extends RecyclerView.Adapter<TarefaRVAdapter.ViewHolder> {

    private final List<Tarefa> tarefas;
    private final OnListFragmentInteractionListener mListener;

    public TarefaRVAdapter(List<Tarefa> items, OnListFragmentInteractionListener listener) {
        tarefas = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_tarefa_model, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = tarefas.get(position);
        holder.lblNome.setText(tarefas.get(position).getTitulo());
        holder.lblUsuario.setText(tarefas.get(position).getPerfil().getUsuario().getNome());
        holder.lblStatus.setText(tarefas.get(position).getDataLimiteformat());
        holder.lblDataLimite.setText(tarefas.get(position).getDataLimiteformat());

        holder.mView.setOnClickListener(v -> {
            if (null != mListener) {
                // Notify the active callbacks interface (the activity, if the
                // fragment is attached to one) that an item has been selected.
                mListener.onListFragmentInteraction(holder.mItem);
            }
        });
    }

    @Override
    public int getItemCount() {
        return tarefas.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView lblNome;
        public final TextView lblUsuario;
        public final TextView lblStatus;
        public final TextView lblDataLimite;
        public Tarefa mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            lblNome = view.findViewById(R.id.lblNome);
            lblUsuario = view.findViewById(R.id.lblUsuario);
            lblStatus = view.findViewById(R.id.lblStatus);
            lblDataLimite = view.findViewById(R.id.lblDataLimite);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + lblNome.getText() + "'";
        }
    }
}
