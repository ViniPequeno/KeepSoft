package nescaupower.br.com.keepsoft.Views.Tarefa;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import nescaupower.br.com.keepsoft.Factory.Model.Perfil;
import nescaupower.br.com.keepsoft.R;

public class SpinPerfilAdapter extends ArrayAdapter {
    private List<Perfil> perfis;

    public SpinPerfilAdapter(@NonNull Context context, @NonNull List<Perfil> perfis) {
        super(context, 0, perfis);
        this.perfis = perfis;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        return initView(position, convertView, parent);
    }

    @Override
    public View getDropDownView(int position, View convertView, @NonNull ViewGroup parent) {
        return initView(position, convertView, parent);
    }

    private View initView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.fragment_equipe_model, parent, false);
        }
        convertView.setFocusable(false);
        convertView.setClickable(false);

        TextView lblNome = convertView.findViewById(R.id.lblNome);
        TextView lblFuncao = convertView.findViewById(R.id.lblFuncao);
        TextView lblObs = convertView.findViewById(R.id.lblObs);

        Perfil perfil = perfis.get(position);

        if (perfil != null) {
            lblNome.setText(perfil.getUsuario().getNome());
            lblFuncao.setText(perfil.getPerfil().toString());
            lblObs.setText(null);
        }
        return convertView;
    }
}