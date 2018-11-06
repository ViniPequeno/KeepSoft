package nescaupower.br.com.keepsoft.Views.Reuniao;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.List;

import nescaupower.br.com.keepsoft.Factory.Model.Reuniao;
import nescaupower.br.com.keepsoft.R;
import nescaupower.br.com.keepsoft.Views.ItemClickListener;
import nescaupower.br.com.keepsoft.Views.Reuniao.DetalhesReuniao;

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
        holder.lblReuniaoTitulo.setText(reuniaoList.get(position).getNome());
        holder.lblReuniaoDescricao.setText(reuniaoList.get(position).getAssunto());
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        String dataInicio = format.format(reuniaoList.get(position).getDataInicio());
        String dataFim = format.format(reuniaoList.get(position).getDataFim());

        dataInicio += " - "+reuniaoList.get(position).getHoraInicioFormat();
        dataFim += " - "+reuniaoList.get(position).getHoraFimFormat();
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
        private ItemClickListener itemClickListener;


        public ViewHolder(View itemView) {
            super(itemView);

            lblReuniaoTitulo = itemView.findViewById(R.id.lblReuniaoTitulo);
            lblReuniaoDescricao = itemView.findViewById(R.id.lblReuniaoDescricao );
            lblReuniaoDataInicio = itemView.findViewById(R.id.lblReuniaoDataInicio);
            lblReuniaoDataFim = itemView.findViewById(R.id.lblReuniaoDataFim);

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
