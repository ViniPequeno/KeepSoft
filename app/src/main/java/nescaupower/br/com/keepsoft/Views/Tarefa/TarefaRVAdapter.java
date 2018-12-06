package nescaupower.br.com.keepsoft.Views.Tarefa;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import nescaupower.br.com.keepsoft.Controller.PerfilController;
import nescaupower.br.com.keepsoft.Controller.TarefaController;
import nescaupower.br.com.keepsoft.Controller.TarefaStatusController;
import nescaupower.br.com.keepsoft.Factory.Model.Perfil;
import nescaupower.br.com.keepsoft.Factory.Model.Projeto;
import nescaupower.br.com.keepsoft.Factory.Model.Tarefa;
import nescaupower.br.com.keepsoft.Factory.Model.TarefaStatus;
import nescaupower.br.com.keepsoft.Factory.Model.Usuario;
import nescaupower.br.com.keepsoft.R;
import nescaupower.br.com.keepsoft.Views.Tarefa.TarefaFragment.OnListFragmentInteractionListener;

/**
 * {@link RecyclerView.Adapter} that can display a {@link Tarefa} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 */
public class TarefaRVAdapter extends RecyclerView.Adapter<TarefaRVAdapter.ViewHolder> {

    private Context context;
    private List<Tarefa> tarefas;
    private final OnListFragmentInteractionListener mListener;

    public TarefaRVAdapter(OnListFragmentInteractionListener listener, List<Tarefa> tarefas, Context context) {
        this.tarefas = tarefas;
        this.mListener = listener;
        this.context = context;
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

        TarefaStatusController tsc = new TarefaStatusController();
        TarefaStatus tarefaStatus = tsc.findCuurentStatusOfTarefa(holder.mItem.getId());
        holder.lblStatus.setText(tarefaStatus.getStatus().getNome());
        holder.imgColor.setColorFilter(tarefaStatus.getStatus().getCor());
        holder.lblDataLimite.setText(tarefas.get(position).getDataLimiteformat());

        Perfil scrumMaster = new PerfilController().procurarPorProjetoUsuario(Projeto.getUltimoProjetoUsado().getCodigo(), Usuario.getUsuarioLogado().getId());
        if (scrumMaster.getPerfil() == nescaupower.br.com.keepsoft.Enum.Perfil.SCRUM_MASTER) {
            AlertDialog.Builder alertDelete = new AlertDialog.Builder(context);
            alertDelete.setTitle(R.string.delete_task_confirm);
            alertDelete.setPositiveButton(R.string.confirm, (dialogInterface, index) -> {
                deletar(position);
            });
            alertDelete.setNegativeButton(R.string.cancel, null);

            holder.btnEditar.setOnClickListener(view -> telaEditar(holder.mItem.getId()));
            holder.btnDelete.setOnClickListener(view -> alertDelete.show());
        } else {
            holder.btnEditar.setVisibility(View.INVISIBLE);
            holder.btnDelete.setVisibility(View.INVISIBLE);
        }
        holder.mView.setOnClickListener(v -> {
            if (null != mListener) {
                mListener.onListFragmentInteraction(holder.mItem);
            }
        });
    }

    private void telaEditar(long tarefaId) {
        Intent i = new Intent(context, EditarTarefaActivity.class);
        i.putExtra("tarefaId", tarefaId);
        context.startActivity(i);
    }

    private void deletar(int position) {
        TarefaController tc = new TarefaController();
        tc.deletar(tarefas.get(position));
        tarefas.remove(tarefas.get(position));
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return tarefas.size();
    }

    public List<Tarefa> getTarefas() {
        return tarefas;
    }

    public void setTarefas(List<Tarefa> tarefas) {
        this.tarefas = tarefas;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView lblNome;
        public final TextView lblUsuario;
        public final TextView lblStatus;
        public final TextView lblDataLimite;
        public final ImageView imgColor;
        public final ImageButton btnEditar;
        public final ImageButton btnDelete;
        public Tarefa mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            lblNome = view.findViewById(R.id.lblNome);
            lblUsuario = view.findViewById(R.id.lblUsuario);
            lblStatus = view.findViewById(R.id.lblStatus);
            lblDataLimite = view.findViewById(R.id.lblDataLimite);
            imgColor = view.findViewById(R.id.imgColor);
            btnEditar = view.findViewById(R.id.btnEditar);
            btnDelete = view.findViewById(R.id.btnDelete);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + lblNome.getText() + "'";
        }
    }
}
