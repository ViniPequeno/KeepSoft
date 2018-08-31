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
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import nescaupower.br.com.keepsoft.Config.Settings;
import nescaupower.br.com.keepsoft.Controller.UsuarioController;
import nescaupower.br.com.keepsoft.Factory.Model.Usuario;
import nescaupower.br.com.keepsoft.R;
import nescaupower.br.com.keepsoft.Views.Login.LoginActivity;
import nescaupower.br.com.keepsoft.Views.Usuario.AlterarPerfilActivity;
import nescaupower.br.com.keepsoft.Views.Usuario.AlterarPerfilSenhaActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class PerfilFragment extends Fragment {

    TextView lblLogin, lblEmail, lblNome, lblTelefone;
    Button btnAlterarPerfil, btnAlterarSenha, btnSair;
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

        if (Usuario.getUsuario_logado() == null || Usuario.getUsuario_logado().getLogin().equals("")) {
            SharedPreferences sharedPreferences = getActivity().getSharedPreferences(Settings.SHARED_PREF_NAME, Context.MODE_PRIVATE);
            Usuario.setUsuario_logado(uc.procurarPeloLogin(sharedPreferences.getString(Settings.LOGIN, "")));
        }

        lblLogin = getView().findViewById(R.id.lblLogin);
        lblEmail = getView().findViewById(R.id.lblEmail);
        lblNome = getView().findViewById(R.id.lblNome);
        lblTelefone = getView().findViewById(R.id.lblTelefone);

        lblLogin.setText(Usuario.getUsuario_logado().getLogin());
        lblEmail.setText(Usuario.getUsuario_logado().getEmail());
        lblNome.setText(Usuario.getUsuario_logado().getNome());
        lblTelefone.setText(Usuario.getUsuario_logado().getTelefone());

        btnAlterarPerfil = getView().findViewById(R.id.btnAlterarPerfil);
        btnAlterarSenha = getView().findViewById(R.id.btnAlterarSenha);
        btnSair = getView().findViewById(R.id.btnSair);

        btnAlterarPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                trocarTelaAlterarPerfil(view);
            }
        });
        btnAlterarSenha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                trocarTelaAlterarSenha(view);
            }
        });
        btnSair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sair(view);
            }
        });
    }

    private void sair(View view) {
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

    private void trocarTelaAlterarPerfil(View view) {
        Intent intent;
        intent = new Intent(getActivity(), AlterarPerfilActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK); // adiciona a flag para a intent
        startActivity(intent);
    }

    private void trocarTelaAlterarSenha(View view) {
        Intent intent;
        intent = new Intent(getActivity(), AlterarPerfilSenhaActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK); // adiciona a flag para a intent
        startActivity(intent);
    }

}
