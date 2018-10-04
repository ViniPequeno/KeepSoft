package nescaupower.br.com.keepsoft.Views.Notificacao;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import nescaupower.br.com.keepsoft.Factory.Model.Convite;
import nescaupower.br.com.keepsoft.Factory.Model.Notificacao;
import nescaupower.br.com.keepsoft.R;
import nescaupower.br.com.keepsoft.Views.ItemClickListener;

public class NotificacaoRVAdapter extends RecyclerView.Adapter<NotificacaoRVAdapter.ViewHolder> {

    private Context c;
    private List<Notificacao> notificacoes;

    public NotificacaoRVAdapter(Context c, List<Notificacao> notificacoes) {
        this.c = c;
        this.notificacoes = notificacoes;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_notificacoes_model, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        //holder.lblNome.setText(notificacoes.get(position).getNome());
        //holder.lblData.setText(notificacoes.get(position).getDescricao());
    }

    @Override
    public int getItemCount() {
        return notificacoes.size();
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView lblNome;
        public final TextView lblData;
        public Convite mItem;
        private ItemClickListener itemClickListener;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            lblNome = view.findViewById(R.id.lblNome);
            lblData = view.findViewById(R.id.lblFuncao);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + lblData.getText() + "'";
        }
    }
}
