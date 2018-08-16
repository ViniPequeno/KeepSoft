package nescaupower.br.com.keepsoft.Views;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

public class MyAdapter extends RecyclerView.Adapter<MyHolder> {

    Context c;
    String[] projetos;

    public MyAdapter(Context c, String[] projetos) {
        this.c = c;
        this.projetos = projetos;
    }

    /* TODO: Descomentar código*/
    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        //View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.model,parent,false);
        //return new MyHolder(v);
        return null;
    }

    @Override
    public void onBindViewHolder(MyHolder holder, int position) {
        holder.txtName.setText(projetos[position]);
    }

    @Override
    public int getItemCount() {
        return projetos.length;
    }
}
