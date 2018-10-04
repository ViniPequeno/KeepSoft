package nescaupower.br.com.keepsoft.Views.Notificacao;

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

import nescaupower.br.com.keepsoft.Factory.Model.Notificacao;
import nescaupower.br.com.keepsoft.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class NotificacoesFragment extends Fragment {

    private RecyclerView rv;
    private List<Notificacao> notificacoes;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        notificacoes = new ArrayList<>();
        notificacoes.add(new Notificacao());
        notificacoes.add(new Notificacao());
        notificacoes.add(new Notificacao());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Resources res = getContext().getResources();

        // Inflate the layout for this fragment
        final View rootView = inflater.inflate(R.layout.fragment_notificacoes, container, false);

        rv = rootView.findViewById(R.id.NotificacoesRV);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        rv.setLayoutManager(layoutManager);

        NotificacaoRVAdapter adapter = new NotificacaoRVAdapter(this.getActivity(), notificacoes);
        rv.setAdapter(adapter);

        //Título
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(res.getString(R.string.title_notifications));
        return rootView;
    }

}
