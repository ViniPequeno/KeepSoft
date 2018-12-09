package nescaupower.br.com.keepsoft.Views.Reuniao;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.List;

import nescaupower.br.com.keepsoft.Factory.Model.Reuniao;
import nescaupower.br.com.keepsoft.R;
import nescaupower.br.com.keepsoft.Views.ItemClickListener;

public class ReuniaoRVAdapter  extends RecyclerView.Adapter<ReuniaoRVAdapter.ViewHolder>  {
    private Context c;
    private List<Reuniao> reuniaoList;

    public ReuniaoRVAdapter(Context c, List<Reuniao> reuniaos) {
        this.c = c;
        this.reuniaoList = reuniaos;
    }

    @Override
    public ReuniaoRVAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_reuniao_model, parent, false);
        return new ReuniaoRVAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ReuniaoRVAdapter.ViewHolder holder, int position) {
        holder.lblReuniaoTitulo.setText("Nome: "+reuniaoList.get(position).getNome());
        holder.lblReuniaoDescricao.setText("Assunto: "+ reuniaoList.get(position).getAssunto());
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        String dataInicio = format.format(reuniaoList.get(position).getDataInicio());
        String dataFim;

        holder.lblReuniaoLocal.setText("Local: "+reuniaoList.get(position).getLocal());


        dataFim = reuniaoList.get(position).getHoraInicioFormat()+" - "+reuniaoList.get(position).getHoraFimFormat();
        holder.lblReuniaoDataInicio.setText(dataInicio);
        holder.lblReuniaoDataFim.setText(dataFim);

        holder.setItemClickListener((v, pos) -> {
            Intent i = new Intent(c, DetalhesReuniao.class);

            i.putExtra("EXTRA_CODIGO_REUNIAO", reuniaoList.get(pos).getId());
            i.putExtra("EXTRA_INDEX_REUNIAO", pos);


            c.startActivity(i);
        });
    }

    public void setReuniaoList(List<Reuniao> reuniaoList) {
        this.reuniaoList = reuniaoList;
    }

    @Override
    public int getItemCount() {
        return reuniaoList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        final TextView lblReuniaoTitulo;
        final TextView lblReuniaoDescricao;
        final TextView lblReuniaoDataInicio;
        final TextView lblReuniaoDataFim;
        final TextView lblReuniaoLocal;
        private ItemClickListener itemClickListener;


        public ViewHolder(View itemView) {
            super(itemView);

            lblReuniaoTitulo = itemView.findViewById(R.id.lblReuniaoTitulo);
            lblReuniaoDescricao = itemView.findViewById(R.id.lblReuniaoDescricao );
            lblReuniaoDataInicio = itemView.findViewById(R.id.lblReuniaoDataInicio);
            lblReuniaoDataFim = itemView.findViewById(R.id.lblReuniaoDataFim);
            lblReuniaoLocal = itemView.findViewById(R.id.lblReuniaoLocal);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            this.itemClickListener.onItemClick(view,getLayoutPosition());
        }

        public void setItemClickListener(ItemClickListener ic){
            this.itemClickListener = ic;
        }
    }
}
