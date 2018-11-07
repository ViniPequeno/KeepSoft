package nescaupower.br.com.keepsoft.Views.Reuniao.Participantes;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import nescaupower.br.com.keepsoft.Controller.UsuarioController;
import nescaupower.br.com.keepsoft.Enum.Perfil;
import nescaupower.br.com.keepsoft.Factory.Model.Usuario;
import nescaupower.br.com.keepsoft.R;

public class PesquisarUsuarioRVAdapter extends RecyclerView.Adapter<PesquisarUsuarioRVAdapter.ViewHolder> {

    private UsuarioController uc;
    private Context context;
    private List<Usuario> usuarios;
    private List<Integer> funcoesUsuarios;

    public PesquisarUsuarioRVAdapter(List<Usuario> usuarios, Context context) {
        this.usuarios = usuarios;
        this.context = context;
        funcoesUsuarios = new ArrayList<>();
        uc = new UsuarioController();
    }

    @Override
    public PesquisarUsuarioRVAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_convidar_membro_model, parent, false);
        return new PesquisarUsuarioRVAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final PesquisarUsuarioRVAdapter.ViewHolder holder, final int position) {
        holder.mItem = usuarios.get(position);
        Log.e("e", holder.mItem.getNome());
        holder.lblLogin.setText(holder.mItem.getLogin());
        holder.lblNome.setText(holder.mItem.getNome());
        holder.btnDelete.setOnClickListener((View view) -> {
            Usuario usuarioRemovido = usuarios.get(holder.getAdapterPosition());
            usuarios.remove(usuarioRemovido);
            notifyItemRemoved(holder.getAdapterPosition());
        });
        funcoesUsuarios.add(holder.spinFuncao.getSelectedItemPosition());
        holder.spinFuncao.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                funcoesUsuarios.set(holder.getAdapterPosition(), i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    public Perfil getFuncaoUsuario(int position) {
        int indexFuncao = funcoesUsuarios.get(position);
        switch (indexFuncao) {
            case 0:
                return Perfil.TEAM;
            case 1:
                return Perfil.SCRUM_MASTER;
            default:
                return Perfil.PRODUCT_OWNER;
        }
    }

    public int len() {
        return funcoesUsuarios.size();
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
            usuarios = uc.listarUsuariosCursor(texto, 0L);
        }
        notifyDataSetChanged();
    }

    public void add(Usuario u) {
        if (!containsID(u.getId())) {
            usuarios.add(u);
            notifyItemInserted(usuarios.size() - 1);
        }
    }

    @TargetApi(Build.VERSION_CODES.N)
    private boolean containsID(long id) {
        return usuarios.stream().anyMatch(o -> o.getId() == id);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView lblLogin;
        public final TextView lblNome;
        public final ImageButton btnDelete;
        public final Spinner spinFuncao;
        public Usuario mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            lblLogin = view.findViewById(R.id.lblLogin);
            lblNome = view.findViewById(R.id.lblNome);
            btnDelete = view.findViewById(R.id.btnDelete);
            spinFuncao = view.findViewById(R.id.spinFuncao);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + lblNome.getText() + "'";
        }
    }
}
