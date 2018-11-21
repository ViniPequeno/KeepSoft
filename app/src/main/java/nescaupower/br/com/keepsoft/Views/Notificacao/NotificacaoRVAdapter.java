package nescaupower.br.com.keepsoft.Views.Notificacao;

import android.content.Context;
import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Date;
import java.util.List;

import nescaupower.br.com.keepsoft.Controller.ConviteController;
import nescaupower.br.com.keepsoft.Controller.PerfilController;
import nescaupower.br.com.keepsoft.Controller.ProjetoController;
import nescaupower.br.com.keepsoft.Controller.UsuarioController;
import nescaupower.br.com.keepsoft.Factory.Model.Convite;
import nescaupower.br.com.keepsoft.Factory.Model.Perfil;
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
        pc = new ProjetoController();
        uc = new UsuarioController();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_notificacoes_model, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Resources res = context.getResources();
        holder.mItem = notificacoes.get(holder.getAdapterPosition());
        //Se o item da lista for um convite
        if (notificacoes.get(holder.getAdapterPosition()) instanceof Convite) {
            holder.lblTitulo.setText(R.string.invitation);
            Convite convite = (Convite) notificacoes.get(holder.getAdapterPosition());
            Usuario remetente = uc.procurarPorID(convite.getRemetenteId());
            Projeto projeto = pc.procurarPorCodigo(convite.getProjeto().getCodigo());

            /*String dinâmica que forma a descrição do convite
              Parâmetros:
              1: Nome do usuário que envia o convite
              2: Nome do projeto para o qual o usuário foi convidado
              3: Função que o usuário assumirá inicialmente no projeto
            */
            String descricao = res.getString(R.string.invitation_description, remetente.getLogin(), projeto.getNome(), convite.getFuncao().toString());
            holder.lblDescricao.setText(descricao);

            //Formatando data na notificação
            //(dataHoraAtual - dataHoraConvite) para segundos
            long tempoSegundos = (new Date().getTime() - convite.getDataEnvio().getTime()) / 1000;
            String tempoFormatado;
            if (tempoSegundos < 60) {                       //Se tempo < 1 minuto
                tempoFormatado = res.getString(R.string.seconds, tempoSegundos);
                holder.lblData.setText(tempoFormatado);
            } else if (tempoSegundos < 3600) {              //Se tempo < 1 hora
                int tempoMinutos = (int) tempoSegundos / 60;
                tempoFormatado = res.getQuantityString(R.plurals.minutes, tempoMinutos, tempoMinutos);
                holder.lblData.setText(tempoFormatado);
            } else if (tempoSegundos < (3600 * 24)) {       //Se tempo < 1 dia
                int tempoHoras = (int) tempoSegundos / 3600;
                tempoFormatado = res.getQuantityString(R.plurals.hours, tempoHoras, tempoHoras);
                holder.lblData.setText(tempoFormatado);
            } else if (tempoSegundos < (3600 * 24 * 7)) {   //Se tempo < 1 semana
                int tempoDias = (int) tempoSegundos / 3600 / 24;
                tempoFormatado = res.getQuantityString(R.plurals.days, tempoDias, tempoDias);
                holder.lblData.setText(tempoFormatado);
            } else if (tempoSegundos < (3600 * 24 * 30)) {  //Se tempo < 1 mês
                int tempoSemanas = (int) tempoSegundos / 3600 / 24 / 7;
                tempoFormatado = res.getQuantityString(R.plurals.weeks, tempoSemanas, tempoSemanas);
                holder.lblData.setText(tempoFormatado);
            } else {                                        //Se tempo >= 1 mês
                int tempoMeses = (int) tempoSegundos / 3600 / 24 / 30;
                tempoFormatado = res.getQuantityString(R.plurals.months, tempoMeses, tempoMeses);
                holder.lblData.setText(tempoFormatado);
            }

            holder.btnDelete.setOnClickListener(view -> apagarConvite(holder.getAdapterPosition(), convite));

            holder.btnRecusar.setOnClickListener(view -> apagarConvite(holder.getAdapterPosition(), convite));

            holder.btnAceitar.setOnClickListener(view -> aceitarConvite(holder.getAdapterPosition(), convite));
        }
    }

    private void aceitarConvite(int position, Convite convite) {
        //Apagar notificação da tela
        notificacoes.remove(position);
        notifyItemRemoved(position);

        //Apagar o convite do banco
        ConviteController cc = new ConviteController();
        cc.deletar(cc.procurarPorID(convite.getDestinatarioId(), convite.getProjeto().getCodigo()));

        //Atualizar perfil correspondente ao projeto
        PerfilController pc = new PerfilController();
        Perfil perfil = pc.procurarPorProjetoUsuario(convite.getProjeto().getCodigo(), convite.getDestinatarioId());
        perfil.setDataInicio(new Date());
        pc.atualizar(perfil);
        Toast.makeText(context, "3", Toast.LENGTH_SHORT).show();
    }

    private void apagarConvite(int position, Convite convite) {
        Log.e("Convite", convite.getId() + "");
        Log.e("Convite2", convite.getDestinatarioId() + "");
        //Apagar notificação da tela
        notificacoes.remove(position);
        notifyItemRemoved(position);

        //Apagar o convite do banco
        ConviteController cc = new ConviteController();
        cc.deletar(cc.procurarPorID(convite.getDestinatarioId(), convite.getProjeto().getCodigo()));

        //Apagar perfil do banco
        PerfilController pc = new PerfilController();
        pc.deletar(pc.procurarPorProjetoUsuario(convite.getProjeto().getCodigo(), convite.getDestinatarioId()));
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
        public final ImageButton btnDelete;
        public final Button btnRecusar;
        public final Button btnAceitar;
        public Object mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            lblTitulo = view.findViewById(R.id.lblTitulo);
            lblDescricao = view.findViewById(R.id.lblDescricao);
            lblData = view.findViewById(R.id.lblData);
            btnDelete = view.findViewById(R.id.btnDelete);
            btnRecusar = view.findViewById(R.id.btnRecusar);
            btnAceitar = view.findViewById(R.id.btnAceitar);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + lblData.getText() + "'";
        }
    }
}
