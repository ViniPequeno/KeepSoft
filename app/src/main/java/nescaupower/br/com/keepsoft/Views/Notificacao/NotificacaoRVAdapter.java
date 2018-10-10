package nescaupower.br.com.keepsoft.Views.Notificacao;

import android.content.Context;
import android.content.res.Resources;
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

    private Context context;
    ProjetoController pc;
    UsuarioController uc;
    private List<Object> notificacoes;

    public NotificacaoRVAdapter(Context c, List<Object> notificacoes) {
        this.context = c;
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
        Resources res = context.getResources();
        holder.mItem = notificacoes.get(position);
        //Se o item da lista for um convite
        if (notificacoes.get(position) instanceof Convite) {
            holder.lblTitulo.setText(R.string.invitation);
            Convite convite = (Convite) notificacoes.get(position);
            Usuario remetente = uc.procurarPorID(convite.getRemetenteId());
            Projeto projeto = pc.procurarPorCodigo(convite.getCodProjeto());

            /*String dinâmica que forma a descrição do convite
              Parâmetros:
              1: Nome do usuário que envia o convite
              2: Nome do projeto para o qual o usuário foi convidado
              3: Função que o usuário assumirá inicialmente no projeto
            */
            String descricao = this.context.getString(R.string.invitation_description, remetente.getLogin(), projeto.getNome(), convite.getFuncao().toString());
            holder.lblDescricao.setText(descricao);

            long tempoSegundos = convite.getData().getTime() * 1000;
            if (tempoSegundos < 60) {
                holder.lblData.setText(R.string.segundos);
            } else if (tempoSegundos < 3600) {
                holder.lblData.setText(R.string.segundos);
            } else if (tempoSegundos < (3600 * 24)) {
                holder.lblData.setText(R.string.segundos);
            } else if (tempoSegundos < (3600 * 24 * 30)) {
                holder.lblData.setText(R.string.segundos);
            } else {
                holder.lblData.setText(R.string.segundos);
            }
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
