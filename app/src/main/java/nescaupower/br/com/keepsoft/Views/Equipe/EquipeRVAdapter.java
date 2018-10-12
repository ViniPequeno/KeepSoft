package nescaupower.br.com.keepsoft.Views.Equipe;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import nescaupower.br.com.keepsoft.Config.Settings;
import nescaupower.br.com.keepsoft.Controller.ConviteController;
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
        this.uc = new UsuarioController(context);
        this.pc = new PerfilController(context);
        this.projeto = Projeto.getUltimoProjetoUsado();

        //Singleton
        this.usuarioLogado = Usuario.getUsuarioLogado();
        if (usuarioLogado == null || usuarioLogado.getLogin().equals("")) {
            SharedPreferences sharedPreferences = context.getSharedPreferences(Settings.SHARED_PREF_NAME, Context.MODE_PRIVATE);
            usuarioLogado = uc.procurarPorLogin(sharedPreferences.getString(Settings.LOGIN, ""));
        }
        this.perfilUsuarioLogado= pc.procurarPorProjetoUsuario(projeto.getCodigo(), usuarioLogado.getId());
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_equipe_model, parent, false);
        Toast.makeText(context, "" + perfilUsuarioLogado.getDataInicio(), Toast.LENGTH_SHORT).show();
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        holder.mItem = perfis.get(position);
        final Usuario usuario = uc.procurarPorID(holder.mItem.getIdUsuario());

        holder.lblNome.setText(usuario.getLogin());
        holder.lblFuncao.setText(perfis.get(position).getPerfil().toString());

        if (!perfilUsuarioLogado.getPerfil().equals(nescaupower.br.com.keepsoft.Enum.Perfil.SCRUM_MASTER)) {
            holder.btnDelete.setVisibility(View.INVISIBLE);
        } else {
            holder.btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //Se tentou remover um usuário diferente dele mesmo
                    if (holder.mItem.getIdUsuario() != Usuario.getUsuarioLogado().getId()) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                        builder.setTitle("Aviso!");
                        builder.setMessage("Deseja realmente remover " + usuario.getLogin() + " deste projeto?");
                        builder.setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //Apagar convite da tela
                                perfis.remove(holder.getAdapterPosition());
                                notifyItemRemoved(holder.getAdapterPosition());

                                //Apagar o convite do banco
                                ConviteController cc = new ConviteController(context);
                                cc.deletar(cc.procurarPorID(holder.mItem.getIdUsuario(), holder.mItem.getCodProjeto()));

                                //Apagar perfil do banco
                                PerfilController pc = new PerfilController(context);
                                pc.deletar(holder.mItem);
                            }
                        });
                        builder.setNegativeButton(R.string.cancel, null);
                        builder.show();
                    }
                }
            });
        }

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


    public void setPerfis(List<Perfil> perfis) {
        this.perfis = perfis;
    }

    @Override
    public int getItemCount() {
        return perfis.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView lblNome;
        public final TextView lblFuncao;
        public final TextView lblObs;
        public final ImageButton btnDelete;
        public Perfil mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            lblNome = view.findViewById(R.id.lblNome);
            lblFuncao = view.findViewById(R.id.lblFuncao);
            lblObs = view.findViewById(R.id.lblObs);
            btnDelete = view.findViewById(R.id.btnDelete);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + lblFuncao.getText() + "'";
        }
    }
}
