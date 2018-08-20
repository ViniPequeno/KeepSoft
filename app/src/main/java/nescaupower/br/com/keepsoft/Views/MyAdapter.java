package nescaupower.br.com.keepsoft.Views;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import nescaupower.br.com.keepsoft.Factory.Model.Projeto;
import nescaupower.br.com.keepsoft.R;

public class MyAdapter extends RecyclerView.Adapter<MyHolder> {

    Context c;
    List<Projeto> projetos;

    public MyAdapter(Context c, List<Projeto> projetos) {
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
        holder.lblName.setText(projetos.get(position).getNome());
    }

    @Override
    public int getItemCount() {
        return projetos.size();
    }
}
