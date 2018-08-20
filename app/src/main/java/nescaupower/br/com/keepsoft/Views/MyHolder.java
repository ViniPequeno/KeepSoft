package nescaupower.br.com.keepsoft.Views;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import nescaupower.br.com.keepsoft.R;

public class MyHolder extends RecyclerView.ViewHolder {
    TextView lblName;

    public MyHolder(View itemView) {
        super(itemView);

        lblName = itemView.findViewById(R.id.lblName);
    }
}
