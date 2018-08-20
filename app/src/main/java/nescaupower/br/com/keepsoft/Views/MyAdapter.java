package nescaupower.br.com.keepsoft.Views;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import nescaupower.br.com.keepsoft.R;

public class MyAdapter extends RecyclerView.Adapter<MyHolder> {

    Context c;
    String[] projetos;

    public MyAdapter(Context c, String[] projetos) {
        this.c = c;
        this.projetos = projetos;
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.model, parent, false);
        return new MyHolder(v);
    }

    @Override
    public void onBindViewHolder(MyHolder holder, int position) {
        holder.lblName.setText(projetos[position]);
    }

    @Override
    public int getItemCount() {
        return projetos.length;
    }
}
