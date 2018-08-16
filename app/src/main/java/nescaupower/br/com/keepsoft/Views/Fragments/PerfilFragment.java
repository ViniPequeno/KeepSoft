package nescaupower.br.com.keepsoft.Views.Fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import nescaupower.br.com.keepsoft.Config.Settings;
import nescaupower.br.com.keepsoft.Controller.UsuarioController;
import nescaupower.br.com.keepsoft.Factory.Model.Usuario;
import nescaupower.br.com.keepsoft.R;
import nescaupower.br.com.keepsoft.Views.Login.LoginActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class PerfilFragment extends Fragment {

    TextView textViewUsuario;
    UsuarioController uc;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        uc = new UsuarioController(getActivity().getApplicationContext());
        return inflater.inflate(R.layout.fragment_perfil, null);
    }

    @Override
    public void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);

        textViewUsuario = getView().findViewById(R.id.usuario);
        if(Usuario.getUsuario_logado() == null || Usuario.getUsuario_logado().getLogin().equals("")){
            SharedPreferences sharedPreferences = getActivity().getSharedPreferences(Settings.SHARED_PREF_NAME, Context.MODE_PRIVATE);
            Usuario.setUsuario_logado(uc.procurarPeloLogin(sharedPreferences.getString(Settings.LOGIN, "")));
        }
        textViewUsuario.setText("Olá " + Usuario.getUsuario_logado().getNome());
    }

    public void sair(View view) {
        Toast.makeText(getContext(), "Saiu!", Toast.LENGTH_SHORT).show();
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(Settings.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor;
        editor = sharedPreferences.edit();
        editor.putBoolean(Settings.LOGADO, false);
        editor.putString(Settings.LOGIN, "");
        editor.commit();

        Intent intent;
        intent = new Intent(getActivity(), LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK); // adiciona a flag para a intent
        startActivity(intent);
        getActivity().finish();

    }

}
