package nescaupower.br.com.keepsoft.Views.Notificacao;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import nescaupower.br.com.keepsoft.Controller.ConviteController;
import nescaupower.br.com.keepsoft.Factory.Model.Usuario;
import nescaupower.br.com.keepsoft.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class NotificacoesFragment extends Fragment {

    private RecyclerView rv;
    private ConviteController cc;
    private List<Object> notificacoes;
    private NotificacaoRVAdapter adapter;
    private OnViewNoticationsListener viewNoticationsListener;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        cc = new ConviteController();
        notificacoes = new ArrayList<>();
        notificacoes.addAll(cc.listarPorDestinatario(Usuario.getUsuarioLogado().getId()));
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Resources res = getContext().getResources();
        //Título
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(res.getString(R.string.title_notifications));

        final View rootView;

        //Id do layout do fragmento
        int idLayout = R.layout.fragment_notificacoes;
        //Se não houver notificações troca id para layout com messagem de lista vazia
        if(notificacoes.isEmpty()){
            idLayout = R.layout.no_notification;
            rootView = inflater.inflate(idLayout, container, false);
        }else {
            // Inflate the layout for this fragment
            rootView = inflater.inflate(idLayout, container, false);

            rv = rootView.findViewById(R.id.NotificacoesRV);
            LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
            rv.setLayoutManager(layoutManager);

            adapter = new NotificacaoRVAdapter(this.getActivity(), notificacoes);
            rv.setAdapter(adapter);
        }
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        notificacoes = new ArrayList<>();
        notificacoes.addAll(cc.listarPorDestinatario(Usuario.getUsuarioLogado().getId()));
        viewNoticationsListener.updateNotificationsCount();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnViewNoticationsListener) {
            viewNoticationsListener = (OnViewNoticationsListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnViewNoticationsListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        viewNoticationsListener = null;
    }

    public interface OnViewNoticationsListener {
        void updateNotificationsCount();
    }
}
