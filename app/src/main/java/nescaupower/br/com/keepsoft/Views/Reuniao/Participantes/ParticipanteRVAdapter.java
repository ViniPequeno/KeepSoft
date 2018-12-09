package nescaupower.br.com.keepsoft.Views.Reuniao.Participantes;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import nescaupower.br.com.keepsoft.Factory.Model.Usuario;
import nescaupower.br.com.keepsoft.R;
import nescaupower.br.com.keepsoft.Views.ItemClickListener;

public class ParticipanteRVAdapter extends RecyclerView.Adapter<ParticipanteRVAdapter.ViewHolder>{

    private Context c;
    private List<Usuario> usuarios;

    public ParticipanteRVAdapter(Context c, List<Usuario> usuarios) {
        this.c = c;
        this.usuarios = usuarios;
    }

    @Override
    public ParticipanteRVAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_participante_model, parent, false);
        return new ParticipanteRVAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ParticipanteRVAdapter.ViewHolder holder, int position) {

        holder.lblParticipanteNome.setText(this.usuarios.get(position).getNome());
        holder.lblParticipantePerfil.setText(this.usuarios.get(position).getLogin());

        holder.setItemClickListener((v, pos) -> {
            Intent i = new Intent(c, DetalhesParticipante.class);
            i.putExtra("EXTRA_PARTICIPANTE_ID", usuarios.get(pos).getId());
            c.startActivity(i);
        });
    }

    public void setUsuarios(List<Usuario> usuarios) {
        this.usuarios = usuarios;
    }

    @Override
    public int getItemCount() {
        return usuarios.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        final TextView lblParticipanteNome;
        final TextView lblParticipantePerfil;
        private ItemClickListener itemClickListener;


        public ViewHolder(View itemView) {
            super(itemView);

            lblParticipanteNome = itemView.findViewById(R.id.lblParticipanteNome);
            lblParticipantePerfil = itemView.findViewById(R.id.lblParticipanteLogin);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            this.itemClickListener.onItemClick(view, getLayoutPosition());
        }

        public void setItemClickListener(ItemClickListener ic) {
            this.itemClickListener = ic;
        }
    }
}
