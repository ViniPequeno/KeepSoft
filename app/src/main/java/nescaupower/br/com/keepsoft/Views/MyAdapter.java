package nescaupower.br.com.keepsoft.Views;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.List;

import nescaupower.br.com.keepsoft.Factory.Model.Projeto;
import nescaupower.br.com.keepsoft.R;
import nescaupower.br.com.keepsoft.Views.Projeto.DetalhesProjetoActivity;

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
        holder.lblNome.setText(projetos.get(position).getNome());
        holder.lblDescricao.setText(projetos.get(position).getDescricao());

        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onItemClick(View v, int pos) {
                Toast.makeText(v.getContext(),"Oi "+pos, Toast.LENGTH_SHORT).show();
                Intent i = new Intent(c, DetalhesProjetoActivity.class);

                i.putExtra("Nome", projetos.get(pos).getNome());
                i.putExtra("Descricao", projetos.get(pos).getDescricao());
                i.putExtra("DataCriacao", projetos.get(pos).getDataCriacao());
                i.putExtra("DataFinalizacao", projetos.get(pos).getDataFinalizacao());
                i.putExtra("DataPrevista", projetos.get(pos).getDataPrevFinalizacao());

                c.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return projetos.size();
    }
}
