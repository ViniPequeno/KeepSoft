package nescaupower.br.com.keepsoft.Views.Usuario;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
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

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import de.hdodenhof.circleimageview.CircleImageView;
import nescaupower.br.com.keepsoft.Config.Settings;
import nescaupower.br.com.keepsoft.Controller.UsuarioController;
import nescaupower.br.com.keepsoft.Factory.Model.Usuario;
import nescaupower.br.com.keepsoft.R;
import nescaupower.br.com.keepsoft.Views.Login.LoginActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class PerfilFragment extends Fragment {

    private CircleImageView imgPerfil;
    private TextView lblLogin;
    private TextView lblEmail;
    private TextView lblNome;
    private TextView lblTelefone;
    private Button btnAlterarPerfil;
    private Button btnAlterarSenha;
    private Button btnSair;
    private Button btnDeleteUsrr;
    private Button btnAlterarConfiguracoes;
    AlertDialog dialogSair, dialogDeleteUser;
    private UsuarioController uc;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Resources res = getResources();

        // Inflate the layout for this fragment
        uc = new UsuarioController();

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

        imgPerfil = getView().findViewById(R.id.imgPerfil);
        lblLogin = getView().findViewById(R.id.lblLogin);
        lblEmail = getView().findViewById(R.id.lblEmail);
        lblNome = getView().findViewById(R.id.lblNome);
        lblTelefone = getView().findViewById(R.id.lblTelefone);


        new MyAsyncTask().execute(Settings.URL+"/usuarios/imagem/"+Usuario.getUsuarioLogado().getId());

        lblLogin.setText(Usuario.getUsuarioLogado().getLogin());
        lblEmail.setText(Usuario.getUsuarioLogado().getEmail());
        lblNome.setText(Usuario.getUsuarioLogado().getNome());
        lblTelefone.setText(Usuario.getUsuarioLogado().getTelefone());

        btnAlterarPerfil = getView().findViewById(R.id.btnAlterarPerfil);
        btnAlterarSenha = getView().findViewById(R.id.btnAlterarSenha);
        btnDeleteUsrr = getView().findViewById(R.id.btnDeleteUser);
        btnSair = getView().findViewById(R.id.btnSair);
        btnAlterarConfiguracoes = getView().findViewById(R.id.btnAlterarConfiguracoes);

        AlertDialog.Builder dialogDeleteUserBuilder = new AlertDialog.Builder(getContext());
        dialogDeleteUserBuilder.setMessage(R.string.delete_account_confirm);
        dialogDeleteUserBuilder.setPositiveButton(R.string.confirm, (dialogInterface, i) -> {
            uc.delete(Usuario.getUsuarioLogado());
            Toast.makeText(getContext(), "Usuário deletado!", Toast.LENGTH_LONG).show();
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
        });
        dialogDeleteUserBuilder.setNegativeButton(R.string.cancel, (dialogInterface, i) -> dialogDeleteUser.dismiss());
        dialogDeleteUserBuilder.setCancelable(true);
        dialogDeleteUser = dialogDeleteUserBuilder.create();
        ///////////////

        AlertDialog.Builder dialogSairBuilder = new AlertDialog.Builder(getContext());
        dialogSairBuilder.setMessage(R.string.confirm_log_out);
        dialogSairBuilder.setPositiveButton(R.string.log_out, (dialogInterface, i) -> {
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
        });
        dialogSairBuilder.setNegativeButton(R.string.cancel, (dialogInterface, i) -> dialogSair.dismiss());
        dialogSairBuilder.setCancelable(true);

        dialogSair = dialogSairBuilder.create();

        btnAlterarPerfil.setOnClickListener(this::trocarTelaAlterarPerfil);
        btnAlterarSenha.setOnClickListener(this::trocarTelaAlterarSenha);
        btnSair.setOnClickListener(this::sair);
        btnDeleteUsrr.setOnClickListener(this::deleteUser);
        btnAlterarConfiguracoes.setOnClickListener(this::trocarTelaAlterarConfig);
    }

    @Override
    public void onResume() {
        super.onResume();
        new MyAsyncTask().execute(Settings.URL+"/usuarios/imagem/"+Usuario.getUsuarioLogado().getId());

        lblLogin.setText(Usuario.getUsuarioLogado().getLogin());
        lblEmail.setText(Usuario.getUsuarioLogado().getEmail());
        lblNome.setText(Usuario.getUsuarioLogado().getNome());
        lblTelefone.setText(Usuario.getUsuarioLogado().getTelefone());
    }

    private void sair(View view) {
        dialogSair.show();
    }

    private void deleteUser(View view) {
        dialogDeleteUser.show();
    }

    private void trocarTelaAlterarPerfil(View view) {
        Intent intent;
        intent = new Intent(getActivity(), AlterarPerfilActivity.class);
        startActivity(intent);
    }

    private void trocarTelaAlterarSenha(View view) {
        Intent intent;
        intent = new Intent(getActivity(), AlterarSenhaActivity.class);
        startActivity(intent);
    }

    private void trocarTelaAlterarConfig(View view) {
        Intent intent;
        intent = new Intent(getActivity(), AlterarConfigActivity.class);
        startActivity(intent);
    }

    private class MyAsyncTask extends AsyncTask<String, Void, Bitmap> {
        protected Bitmap doInBackground(String... params) {
            try {
                URL url = new URL(params[0]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);
                connection.connect();
                InputStream input = connection.getInputStream();
                Bitmap myBitmap = BitmapFactory.decodeStream(input);
                return myBitmap;
            } catch(IOException e) {
                return null;
            }
        }

        protected void onPostExecute(Bitmap result) {
            //do what you want with your bitmap result on the UI thread
            imgPerfil.setImageBitmap(result);
        }

    }
}
