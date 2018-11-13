package nescaupower.br.com.keepsoft.Views.Equipe;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import de.hdodenhof.circleimageview.CircleImageView;
import nescaupower.br.com.keepsoft.AsyncTasks.GetImageAsyncTask;
import nescaupower.br.com.keepsoft.Config.Settings;
import nescaupower.br.com.keepsoft.Controller.UsuarioController;
import nescaupower.br.com.keepsoft.Factory.Model.Usuario;
import nescaupower.br.com.keepsoft.R;

public class PesquisarUsuarioCursorAdapter extends CursorAdapter {
    PesquisarUsuarioRVAdapter rvAdapter;
    private LayoutInflater mLayoutInflater;
    private Context mContext;
    private SearchView searchView;

    public PesquisarUsuarioCursorAdapter(Context context, Cursor cursor, SearchView sv, PesquisarUsuarioRVAdapter rvAdapter) {
        super(context, cursor, true);
        mContext = context;
        searchView = sv;
        mLayoutInflater = LayoutInflater.from(context);
        this.rvAdapter = rvAdapter;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View v = mLayoutInflater.inflate(R.layout.dropdown_usuario_search_view_model, parent, false);
        return v;
    }

    @Override
    public void bindView(View view, final Context context, Cursor cursor) {
        final String login = cursor.getString(cursor.getColumnIndexOrThrow("login"));
        final String nome = cursor.getString(cursor.getColumnIndexOrThrow("nome"));
        final long id = cursor.getLong(cursor.getColumnIndexOrThrow("_id"));

        CircleImageView imgPerfil = view.findViewById(R.id.imgPerfil);
        new GetImageAsyncTask(imgPerfil).execute(Settings.URL + "/usuarios/imagem/" + id);

        TextView lblLogin = view.findViewById(R.id.lblLogin);
        lblLogin.setText(login);

        TextView lblNome = view.findViewById(R.id.lblNome);
        lblNome.setText(nome);

        view.setOnClickListener(view1 -> {
            searchView.setIconified(true);
            Toast.makeText(context, "ID usuário: " + id,
                    Toast.LENGTH_LONG).show();
            Usuario u = new UsuarioController().procurarPorID(id);
            rvAdapter.add(u);
        });
    }
}
