package nescaupower.br.com.keepsoft.Views.Status;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import nescaupower.br.com.keepsoft.Factory.Model.Status;
import nescaupower.br.com.keepsoft.R;
import nescaupower.br.com.keepsoft.Views.ItemClickListener;

public class StatusRVAdapter extends RecyclerView.Adapter<StatusRVAdapter.ViewHolder>  {

    private Context c;
    private List<Status> statusList;

    public StatusRVAdapter(Context c, List<Status> statusList) {
        this.c = c;
        this.statusList = statusList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_status_model, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.lblNomeStatus.setText(statusList.get(position).getNome());
        holder.lblDescricaoStatus.setText(statusList.get(position).getDescricao());
        holder.imgColor.setColorFilter(statusList.get(position).getCor());

        holder.setItemClickListener((v, pos) -> {
            Intent i = new Intent(c, DetalhesStatus.class);

            i.putExtra("EXTRA_CODIGO_STATUS", statusList.get(pos).getId());
            i.putExtra("EXTRA_INDEX_STATUS", pos);

            c.startActivity(i);
        });
    }

    public void setStatusList(List<Status> statusList) {
        this.statusList = statusList;
    }

    @Override
    public int getItemCount() {
        return statusList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        final TextView lblNomeStatus;
        final TextView lblDescricaoStatus;
        final ImageView imgColor;
        private ItemClickListener itemClickListener;


        public ViewHolder(View itemView) {
            super(itemView);

            lblNomeStatus = itemView.findViewById(R.id.lblNomeStatus);
            lblDescricaoStatus = itemView.findViewById(R.id.lblDescricaoStatus);
            imgColor = itemView.findViewById(R.id.imgColor);

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
