package nescaupower.br.com.keepsoft.Views.Status;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import nescaupower.br.com.keepsoft.Factory.Model.Status;
import nescaupower.br.com.keepsoft.R;
import nescaupower.br.com.keepsoft.Views.ItemClickListener;

public class StatusRVAdapter extends RecyclerView.Adapter<MyHolder>  {

    private Context c;
    private List<Status> statusList;

    public StatusRVAdapter(Context c, List<Status> statusList) {
        this.c = c;
        this.statusList = statusList;
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_status_model, parent, false);
        return new MyHolder(v);
    }

    @Override
    public void onBindViewHolder(MyHolder holder, int position) {
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
}
