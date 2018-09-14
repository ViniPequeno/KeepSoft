package nescaupower.br.com.keepsoft.Views.Equipe;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;
import java.util.Locale;

import nescaupower.br.com.keepsoft.Controller.UsuarioController;
import nescaupower.br.com.keepsoft.Factory.Model.Usuario;
import nescaupower.br.com.keepsoft.R;

public class PesquisarEquipeRecyclerViewAdapter extends RecyclerView.Adapter<PesquisarEquipeRecyclerViewAdapter.ViewHolder> {

    private UsuarioController uc;
    private Context context;
    private List<Usuario> usuarios;

    public PesquisarEquipeRecyclerViewAdapter(List<Usuario> usuarios, Context context) {
        this.usuarios = usuarios;
        this.context = context;
        uc = new UsuarioController(context);
    }

    @Override
    public PesquisarEquipeRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_convidar_membro_model, parent, false);
        return new PesquisarEquipeRecyclerViewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final PesquisarEquipeRecyclerViewAdapter.ViewHolder holder, int position) {
        holder.mItem = usuarios.get(position);
        Log.e("e", holder.mItem.getNome());
        holder.lblLogin.setText(holder.mItem.getLogin());
        holder.lblNome.setText(holder.mItem.getNome());
    }

    @Override
    public int getItemCount() {
        return usuarios.size();
    }

    public void filtrar(String texto) {
        texto = texto.toLowerCase(Locale.getDefault());
        usuarios.clear();
        if (texto.length() == 0) {
            //usuarios.addAll(arraylist);
        } else {
            texto = "%" + texto + "%";
            usuarios = uc.listarPorNomeLogin(texto, texto);
        }
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView lblLogin;
        public final TextView lblNome;
        public Usuario mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            lblLogin = view.findViewById(R.id.lblLogin);
            lblNome = view.findViewById(R.id.lblNome);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + lblNome.getText() + "'";
        }
    }
}
