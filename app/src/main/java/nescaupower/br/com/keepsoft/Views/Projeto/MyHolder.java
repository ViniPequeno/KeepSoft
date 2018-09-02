package nescaupower.br.com.keepsoft.Views.Projeto;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import nescaupower.br.com.keepsoft.R;
import nescaupower.br.com.keepsoft.Views.ItemClickListener;

public class MyHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    TextView lblNome;
    TextView lblDescricao;
    private ItemClickListener itemClickListener;

    public MyHolder(View itemView) {
        super(itemView);

        lblNome = itemView.findViewById(R.id.lblNome);
        lblDescricao = itemView.findViewById(R.id.lblDescricao);

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
