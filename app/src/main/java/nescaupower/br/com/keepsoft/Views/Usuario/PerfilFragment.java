package nescaupower.br.com.keepsoft.Views.Usuario;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
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

/**
 * A simple {@link Fragment} subclass.
 */
public class PerfilFragment extends Fragment {

    private TextView lblLogin;
    private TextView lblEmail;
    private TextView lblNome;
    private TextView lblTelefone;
    private Button btnAlterarPerfil;
    private Button btnAlterarSenha;
    private Button btnSair;
    AlertDialog dialogSair;
    private UsuarioController uc;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Resources res = getResources();

        // Inflate the layout for this fragment
        uc = new UsuarioController(getActivity().getApplicationContext());

        //Título
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(res.getString(R.string.profile));
        return inflater.inflate(R.layout.fragment_perfil, container, false);
    }

    @Override
    public void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);

        if (Usuario.getUsuarioLogado() == null || Usuario.getUsuarioLogado().getLogin().equals("")) {
            SharedPreferences sharedPreferences = getActivity().getSharedPreferences(Settings.SHARED_PREF_NAME, Context.MODE_PRIVATE);
            Usuario.setUsuarioLogado(uc.procurarPorLogin(sharedPreferences.getString(Settings.LOGIN, "")));
        }

        lblLogin = getView().findViewById(R.id.lblLogin);
        lblEmail = getView().findViewById(R.id.lblEmail);
        lblNome = getView().findViewById(R.id.lblNome);
        lblTelefone = getView().findViewById(R.id.lblTelefone);

        lblLogin.setText(Usuario.getUsuarioLogado().getLogin());
        lblEmail.setText(Usuario.getUsuarioLogado().getEmail());
        lblNome.setText(Usuario.getUsuarioLogado().getNome());
        lblTelefone.setText(Usuario.getUsuarioLogado().getTelefone());

        btnAlterarPerfil = getView().findViewById(R.id.btnAlterarPerfil);
        btnAlterarSenha = getView().findViewById(R.id.btnAlterarSenha);
        btnSair = getView().findViewById(R.id.btnSair);

        AlertDialog.Builder dialogSairBuilder = new AlertDialog.Builder(getContext());
        dialogSairBuilder.setMessage(R.string.confirm_log_out);
        dialogSairBuilder.setPositiveButton(R.string.log_out, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
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
        });
        dialogSairBuilder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogSair.dismiss();
            }
        });
        dialogSairBuilder.setCancelable(true);

        dialogSair = dialogSairBuilder.create();

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
        dialogSair.show();
    }

    private void trocarTelaAlterarPerfil(View view) {
        Intent intent;
        intent = new Intent(getActivity(), AlterarPerfilActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK); // adiciona a flag para a intent
        startActivity(intent);
    }

    private void trocarTelaAlterarSenha(View view) {
        Intent intent;
        intent = new Intent(getActivity(), AlterarSenhaActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK); // adiciona a flag para a intent
        startActivity(intent);
    }

}
