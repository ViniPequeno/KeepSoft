package nescaupower.br.com.keepsoft.Views.Reuniao.Participantes;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import nescaupower.br.com.keepsoft.Controller.UsuarioController;
import nescaupower.br.com.keepsoft.Factory.Model.Usuario;
import nescaupower.br.com.keepsoft.R;

public class PesquisarParticipanteCursosAdapter extends CursorAdapter {
    PesquisarParticipanteRVAdapter rvAdapter;
    private LayoutInflater mLayoutInflater;
    private Context mContext;
    private SearchView searchView;

    public PesquisarParticipanteCursosAdapter(Context context, Cursor cursor, SearchView sv, PesquisarParticipanteRVAdapter rvAdapter) {
        super(context, cursor, true);
        mContext = context;
        searchView = sv;
        mLayoutInflater = LayoutInflater.from(context);
        this.rvAdapter = rvAdapter;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return mLayoutInflater.inflate(R.layout.dropdown_participante_search_view_model, parent, false);
    }

    @Override
    public void bindView(View view, final Context context, Cursor cursor) {
        final String login = cursor.getString(cursor.getColumnIndexOrThrow("login"));
        final String nome = cursor.getString(cursor.getColumnIndexOrThrow("nome"));
        final long id = cursor.getLong(cursor.getColumnIndexOrThrow("_id"));

        TextView lblLogin = view.findViewById(R.id.lblLoginParticipante2);
        lblLogin.setText(login);

        TextView lblNome = view.findViewById(R.id.lblNomeParticipante2);
        lblNome.setText(nome);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchView.setIconified(true);
                Toast.makeText(context, "ID usuário: " + id,
                        Toast.LENGTH_LONG).show();
                Usuario u = new UsuarioController().procurarPorID(id);
                rvAdapter.add(u);
            }
        });
    }
}
