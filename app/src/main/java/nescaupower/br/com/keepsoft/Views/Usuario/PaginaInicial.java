package nescaupower.br.com.keepsoft.Views.Usuario;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import nescaupower.br.com.keepsoft.Config.Settings;
import nescaupower.br.com.keepsoft.Factory.BD.Database.AppDatabase;
import nescaupower.br.com.keepsoft.Factory.Factory;
import nescaupower.br.com.keepsoft.Factory.Model.Usuario;
import nescaupower.br.com.keepsoft.R;
import nescaupower.br.com.keepsoft.Views.Login.LoginActivity;

public class PaginaInicial extends AppCompatActivity {

    TextView textViewUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pagina_inicial);
        textViewUsuario = findViewById(R.id.usuario);

        //Singleton
        Usuario usuario = Usuario.getUsuario_logado();
        if(usuario == null || usuario.getLogin().equals("")){
            AppDatabase db = Factory.startDatabase(getApplicationContext());
            SharedPreferences sharedPreferences = getSharedPreferences(Settings.SHARED_PREF_NAME, Context.MODE_PRIVATE);
            usuario = db.usuarioDAO().findByLogin(sharedPreferences.getString(Settings.LOGIN, ""));
        }
        //////////

        textViewUsuario.setText("Olá "+usuario.getNome());
    }

    public void sair(View view) {
        Toast.makeText(this, "Saiu!", Toast.LENGTH_SHORT).show();
        SharedPreferences sharedPreferences = getSharedPreferences(Settings.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor;
        editor = sharedPreferences.edit();
        editor.putBoolean(Settings.LOGADO, false);
        editor.putString(Settings.LOGIN, "");
        editor.commit();

        Intent intent;
        intent = new Intent(PaginaInicial.this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK ); // adiciona a flag para a intent
        startActivity(intent);
        PaginaInicial.this.finish();

    }
}
