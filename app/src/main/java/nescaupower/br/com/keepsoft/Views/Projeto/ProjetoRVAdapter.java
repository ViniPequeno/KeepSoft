package nescaupower.br.com.keepsoft.Views.Projeto;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import nescaupower.br.com.keepsoft.Controller.ProjetoController;
import nescaupower.br.com.keepsoft.Factory.Model.Porcentagem;
import nescaupower.br.com.keepsoft.Factory.Model.Projeto;
import nescaupower.br.com.keepsoft.R;

public class ProjetoRVAdapter extends RecyclerView.Adapter<MyHolder> {

    private Context c;
    private List<Projeto> projetos;

    public ProjetoRVAdapter(Context context, List<Projeto> projetos) {
        this.c = context;
        this.projetos = projetos;
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_projeto_model, parent, false);
        return new MyHolder(v);
    }

    @Override
    public void onBindViewHolder(MyHolder holder, int position) {
        Resources res = c.getResources();
        holder.lblNome.setText(projetos.get(position).getNome());
        holder.lblDescricao.setText(projetos.get(position).getDescricao());
        //holder.lblDataProximoSprint.setText(c.getString(R.string.next_sprint, "ola"));
        holder.lblDataProximoSprint.setText(R.string.progress);
        ProjetoController pc = new ProjetoController();
        Porcentagem p = pc.procurarPorcentagemPorCodigo(projetos.get(position).getCodigo());
        int porcentagem = (int) p.getPorcentagem();
        String stPorcentagem = res.getString(R.string.percentage, porcentagem);
        holder.lblPorcentagem.setText(stPorcentagem);
        holder.cpbProgressoAtual.setProgress(porcentagem);

        holder.setItemClickListener((v, pos) -> {
            Intent i = new Intent(c, DetalhesProjetoActivity.class);

            i.putExtra("EXTRA_CODIGO_PROJETO", projetos.get(pos).getCodigo());
            i.putExtra("EXTRA_INDEX_PROJETO", pos);

            /*(i.putExtra("EXTRA_DESCRICAO_PROJETO", projetos.get(pos).getDescricao());
            i.putExtra("EXTRA_DATA_CRIACAO_PROJETO", projetos.get(pos).getDataCriacao());
            i.putExtra("EXTRA_DATA_FINALIAZACAO_PROJETO", projetos.get(pos).getDataFinalizacao());
            i.putExtra("EXTRA_DATA_PREVISTA_PROJETO", projetos.get(pos).getDataPrevFinalizacao());*/

            c.startActivity(i);
        });
    }

    public void setProjetos(List<Projeto> projetos) {
        this.projetos = projetos;
    }

    @Override
    public int getItemCount() {
        return projetos.size();
    }
}
