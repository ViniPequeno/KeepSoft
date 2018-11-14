package nescaupower.br.com.keepsoft.Views.Reuniao.Participantes;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.Locale;

import nescaupower.br.com.keepsoft.Controller.UsuarioController;
import nescaupower.br.com.keepsoft.Factory.Model.Usuario;
import nescaupower.br.com.keepsoft.R;

public class PesquisarParticipanteRVAdapter extends RecyclerView.Adapter<PesquisarParticipanteRVAdapter.ViewHolder> {
    private UsuarioController uc;
    private Context context;
    private List<Usuario> usuarios;

    public PesquisarParticipanteRVAdapter(List<Usuario> usuarios, Context context) {
        this.usuarios = usuarios;
        this.context = context;
        uc = new UsuarioController();
    }

    @Override
    public PesquisarParticipanteRVAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_convidar_participante_model, parent, false);
        return new PesquisarParticipanteRVAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final PesquisarParticipanteRVAdapter.ViewHolder holder, final int position) {
        holder.mItem = usuarios.get(position);
        holder.lblLoginParticipante.setText(holder.mItem.getLogin());
        holder.lblNomeParticipante.setText(holder.mItem.getNome());
        holder.btnDeleteParticipante.setOnClickListener((View view) -> {
            Usuario usuarioRemovido = usuarios.get(holder.getAdapterPosition());
            usuarios.remove(usuarioRemovido);
            notifyItemRemoved(holder.getAdapterPosition());
        });
    }

    @Override
    public int getItemCount() {
        return usuarios.size();
    }

    public void filtrar(String texto) {
        texto = texto.toLowerCase(Locale.getDefault());
        usuarios.clear();
        if (texto.length() == 0) {
            //usuarios.addAll(arraylist);
        } else {
            usuarios = uc.listarUsuariosCursor(texto, 0L, 0L);
        }
        notifyDataSetChanged();
    }

    public void add(Usuario u) {
        if (!containsID(u.getId())) {
            usuarios.add(u);
            notifyItemInserted(usuarios.size() - 1);
        }
    }

    @TargetApi(Build.VERSION_CODES.N)
    private boolean containsID(long id) {
        return usuarios.stream().anyMatch(o -> o.getId() == id);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView lblLoginParticipante;
        public final TextView lblNomeParticipante;
        public final ImageButton btnDeleteParticipante;
        public Usuario mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            lblLoginParticipante = view.findViewById(R.id.lblLoginParticipante2);
            lblNomeParticipante = view.findViewById(R.id.lblNomeParticipante2);
            btnDeleteParticipante = view.findViewById(R.id.btnDeleteParticipante2);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + lblNomeParticipante.getText() + "'";
        }
    }
}
