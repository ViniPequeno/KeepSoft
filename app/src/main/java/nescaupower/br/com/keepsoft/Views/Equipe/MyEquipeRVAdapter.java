package nescaupower.br.com.keepsoft.Views.Equipe;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import nescaupower.br.com.keepsoft.Controller.UsuarioController;
import nescaupower.br.com.keepsoft.Factory.Model.Perfil;
import nescaupower.br.com.keepsoft.Factory.Model.Usuario;
import nescaupower.br.com.keepsoft.R;
import nescaupower.br.com.keepsoft.Views.Equipe.EquipeFragment.OnListFragmentInteractionListener;

/**
 * {@link RecyclerView.Adapter} that can display a {@link Perfil} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyEquipeRVAdapter extends RecyclerView.Adapter<MyEquipeRVAdapter.ViewHolder> {

    private Context context;
    private final List<Perfil> perfis;
    private final OnListFragmentInteractionListener mListener;

    public MyEquipeRVAdapter(OnListFragmentInteractionListener listener, List<Perfil> perfis, Context context) {
        this.perfis = perfis;
        this.mListener = listener;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_equipe_model, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        holder.mItem = perfis.get(position);

        UsuarioController uc = new UsuarioController(context);
        Usuario usuario = uc.procurarPorID(holder.mItem.getIdUsuario());

        holder.lblNome.setText(usuario.getLogin());
        holder.lblFuncao.setText(perfis.get(position).getPerfil().toString());

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return perfis.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView lblNome;
        public final TextView lblFuncao;
        public Perfil mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            lblNome = view.findViewById(R.id.lblNome);
            lblFuncao = view.findViewById(R.id.lblFuncao);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + lblFuncao.getText() + "'";
        }
    }
}
