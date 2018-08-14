package nescaupower.br.com.keepsoft.Views.Usuario;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
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
    BottomNavigationView menuInferior;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pagina_inicial);

        textViewUsuario = findViewById(R.id.usuario);
        menuInferior = findViewById(R.id.menu_inferior);

        //Listener do menu inferior
        menuInferior.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.navigation_home:
                                Toast.makeText(getApplicationContext(), Integer.toString(item.getItemId()) + " " + item.getTitle(), Toast.LENGTH_SHORT).show();
                                break;
                            case R.id.navigation_dashboard:
                                Toast.makeText(getApplicationContext(), Integer.toString(item.getItemId()) + " " + item.getTitle(), Toast.LENGTH_SHORT).show();
                                break;
                            case R.id.navigation_notifications:
                                Toast.makeText(getApplicationContext(), Integer.toString(item.getItemId()) + " " + item.getTitle(), Toast.LENGTH_SHORT).show();
                                break;
                        }
                        return false;
                    }
                });

        //Singleton
        Usuario usuario = Usuario.getUsuario_logado();
        if (usuario == null || usuario.getLogin().equals("")) {
            AppDatabase db = Factory.startDatabase(getApplicationContext());
            SharedPreferences sharedPreferences = getSharedPreferences(Settings.SHARED_PREF_NAME, Context.MODE_PRIVATE);
            usuario = db.usuarioDAO().findByLogin(sharedPreferences.getString(Settings.LOGIN, ""));
        }
        //////////

        textViewUsuario.setText("Olá " + usuario.getNome());
        Toast.makeText(getApplicationContext(), "123", Toast.LENGTH_SHORT).show();
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
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK); // adiciona a flag para a intent
        startActivity(intent);
        PaginaInicial.this.finish();

    }
}
