package nescaupower.br.com.keepsoft.Views.Notificacao;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import nescaupower.br.com.keepsoft.Controller.ProjetoController;
import nescaupower.br.com.keepsoft.Controller.UsuarioController;
import nescaupower.br.com.keepsoft.Factory.Model.Convite;
import nescaupower.br.com.keepsoft.Factory.Model.Projeto;
import nescaupower.br.com.keepsoft.Factory.Model.Usuario;
import nescaupower.br.com.keepsoft.R;

public class NotificacaoRVAdapter extends RecyclerView.Adapter<NotificacaoRVAdapter.ViewHolder> {

    private Context c;
    ProjetoController pc;
    UsuarioController uc;
    private List<Object> notificacoes;

    public NotificacaoRVAdapter(Context c, List<Object> notificacoes) {
        this.c = c;
        this.notificacoes = notificacoes;
        pc = new ProjetoController(c);
        uc = new UsuarioController(c);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_notificacoes_model, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.mItem = notificacoes.get(position);
        //Se o item da lista for um convite
        if (notificacoes.get(position) instanceof Convite) {
            Convite c = (Convite) notificacoes.get(position);
            Usuario remetente = uc.procurarPorID(c.getRemetenteId());
            Projeto p = pc.procurarPorCodigo(c.getCodProjeto());
            holder.lblDescricao.setText(remetente.getLogin() + "o convida para o projeto " + p.getNome());
            holder.lblData.setText(c.getData().toString());
        }
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
        public final TextView lblTitulo;
        public final TextView lblDescricao;
        public final TextView lblData;
        public Object mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            lblTitulo = view.findViewById(R.id.lblTitulo);
            lblDescricao = view.findViewById(R.id.lblDescricao);
            lblData = view.findViewById(R.id.lblData);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + lblData.getText() + "'";
        }
    }
}
