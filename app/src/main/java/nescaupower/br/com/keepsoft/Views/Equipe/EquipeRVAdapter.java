package nescaupower.br.com.keepsoft.Views.Equipe;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import nescaupower.br.com.keepsoft.AsyncTasks.GetImageAsyncTask;
import nescaupower.br.com.keepsoft.Config.Settings;
import nescaupower.br.com.keepsoft.Controller.PerfilController;
import nescaupower.br.com.keepsoft.Controller.UsuarioController;
import nescaupower.br.com.keepsoft.Factory.Model.Perfil;
import nescaupower.br.com.keepsoft.Factory.Model.Projeto;
import nescaupower.br.com.keepsoft.Factory.Model.Usuario;
import nescaupower.br.com.keepsoft.R;
import nescaupower.br.com.keepsoft.Views.Equipe.EquipeFragment.OnListFragmentInteractionListener;

/**
 * {@link RecyclerView.Adapter} that can display a {@link Perfil} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class EquipeRVAdapter extends RecyclerView.Adapter<EquipeRVAdapter.ViewHolder> {

    private Context context;
    private List<Perfil> perfis;
    private final OnListFragmentInteractionListener mListener;
    private UsuarioController uc;
    private PerfilController pc;
    private Perfil perfilUsuarioLogado;
    private Projeto projeto;
    private Usuario usuarioLogado;

    public EquipeRVAdapter(OnListFragmentInteractionListener listener, List<Perfil> perfis, Context context) {
        this.perfis = perfis;
        this.mListener = listener;
        this.context = context;
        this.uc = new UsuarioController();
        this.pc = new PerfilController();
        this.projeto = Projeto.getUltimoProjetoUsado();

        //Singleton
        this.usuarioLogado = Usuario.getUsuarioLogado();
        if (usuarioLogado == null || usuarioLogado.getLogin().equals("")) {
            SharedPreferences sharedPreferences = context.getSharedPreferences(Settings.SHARED_PREF_NAME, Context.MODE_PRIVATE);
            usuarioLogado = uc.procurarPorLogin(sharedPreferences.getString(Settings.LOGIN, ""));
        }
        this.perfilUsuarioLogado = pc.procurarPorProjetoUsuario(projeto.getCodigo(), usuarioLogado.getId());
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
        final Usuario usuario = uc.procurarPorID(holder.mItem.getUsuario().getId());

        new GetImageAsyncTask(holder.imgPerfil).execute(Settings.URL + "/usuarios/imagem/" + usuario.getId());

        holder.lblNome.setText(usuario.getLogin());
        holder.lblFuncao.setText(perfis.get(position).getPerfil().toString());
        if (holder.mItem.getDataInicio() == null) {
            holder.lblObs.setText(R.string.invitation_not_accepted);
        }

        holder.mView.setOnClickListener(v -> {
            if (null != mListener) {
                // Notify the active callbacks interface (the activity, if the
                // fragment is attached to one) that an item has been selected.
                mListener.onListFragmentInteraction(holder.mItem);
            }
        });
    }


    public void setPerfis(List<Perfil> perfis) {
        this.perfis = perfis;
    }

    @Override
    public int getItemCount() {
        return perfis.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final CircleImageView imgPerfil;
        public final TextView lblNome;
        public final TextView lblFuncao;
        public final TextView lblObs;
        public Perfil mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            imgPerfil = view.findViewById(R.id.imgPerfil);
            lblNome = view.findViewById(R.id.lblNome);
            lblFuncao = view.findViewById(R.id.lblFuncao);
            lblObs = view.findViewById(R.id.lblObs);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + lblFuncao.getText() + "'";
        }
    }
}
