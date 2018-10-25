package nescaupower.br.com.keepsoft.Views.Status;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import nescaupower.br.com.keepsoft.R;
import nescaupower.br.com.keepsoft.Views.ItemClickListener;

public class MyHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    final TextView lblNomeStatus;
    final TextView lblDescricaoStatus;
    final ImageView imgColor;
    private ItemClickListener itemClickListener;


    public MyHolder(View itemView) {
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
