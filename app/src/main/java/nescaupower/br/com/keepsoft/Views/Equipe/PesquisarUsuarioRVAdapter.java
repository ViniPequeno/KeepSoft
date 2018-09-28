package nescaupower.br.com.keepsoft.Views.Equipe;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;
import java.util.Locale;

import nescaupower.br.com.keepsoft.Controller.UsuarioController;
import nescaupower.br.com.keepsoft.Factory.Model.Usuario;
import nescaupower.br.com.keepsoft.R;

public class PesquisarUsuarioRVAdapter extends RecyclerView.Adapter<PesquisarUsuarioRVAdapter.ViewHolder> {

    private UsuarioController uc;
    private Context context;
    private List<Usuario> usuarios;


    public PesquisarUsuarioRVAdapter(List<Usuario> usuarios, Context context) {
        this.usuarios = usuarios;
        this.context = context;
        uc = new UsuarioController(context);
    }

    @Override
    public PesquisarUsuarioRVAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_convidar_membro_model, parent, false);
        return new PesquisarUsuarioRVAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final PesquisarUsuarioRVAdapter.ViewHolder holder, int position) {
        holder.mItem = usuarios.get(position);
        Log.e("e", holder.mItem.getNome());
        holder.lblLogin.setText(holder.mItem.getLogin());
        holder.lblNome.setText(holder.mItem.getNome());
        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Usuario usuarioRemovido = usuarios.get(position);
                usuarios.remove(usuarioRemovido);
                notifyItemRemoved(position);
            }
        });
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
            usuarios = uc.listarPorNomeLogin(texto, texto);
        }
        notifyDataSetChanged();
    }

    public void add(Usuario u) {
        if (!containsID(u.getId())) {
            usuarios.add(u);
            notifyItemInserted(usuarios.size() - 1);
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView lblLogin;
        public final TextView lblNome;
        public final ImageButton btnDelete;
        public Usuario mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            lblLogin = view.findViewById(R.id.lblLogin);
            lblNome = view.findViewById(R.id.lblNome);
            btnDelete = view.findViewById(R.id.btnDelete);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + lblNome.getText() + "'";
        }
    }

    @TargetApi(Build.VERSION_CODES.N)
    private boolean containsID(long id) {
        return usuarios.stream().filter(o -> o.getId() == id).findFirst().isPresent();
    }
}
