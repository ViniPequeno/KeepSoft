package nescaupower.br.com.keepsoft.Views;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

import nescaupower.br.com.keepsoft.Config.Settings;
import nescaupower.br.com.keepsoft.Controller.ConviteController;
import nescaupower.br.com.keepsoft.Controller.UsuarioController;
import nescaupower.br.com.keepsoft.Factory.Model.QuantidadeNotificacoes;
import nescaupower.br.com.keepsoft.Factory.Model.Usuario;
import nescaupower.br.com.keepsoft.R;
import nescaupower.br.com.keepsoft.Views.Notificacao.NotificacoesFragment;
import nescaupower.br.com.keepsoft.Views.Projeto.ProjetosFragment;
import nescaupower.br.com.keepsoft.Views.Usuario.PerfilFragment;

public class PaginaInicialActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener, NotificacoesFragment.OnViewNoticationsListener {

    BottomNavigationView menuInferior;
    BottomNavigationMenuView bottomNavigationMenuView;

    UsuarioController uc;
    View badge;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pagina_inicial);

        uc = new UsuarioController();

        menuInferior = findViewById(R.id.menu_inferior);

        //Atribuindo listener ao menu inferior
        menuInferior.setOnNavigationItemSelectedListener(this);
        bottomNavigationMenuView = (BottomNavigationMenuView) menuInferior.getChildAt(0);
        badge = LayoutInflater.from(this).inflate(R.layout.notification_badge, bottomNavigationMenuView, false);

        //Carregar tela padrão: projetos
        loadFragment(new ProjetosFragment());

        //Singleton
        Usuario usuario = Usuario.getUsuarioLogado();
        if (usuario == null || usuario.getLogin().equals("")) {
            SharedPreferences sharedPreferences = getSharedPreferences(Settings.SHARED_PREF_NAME, Context.MODE_PRIVATE);
            usuario = uc.procurarPorLogin(sharedPreferences.getString(Settings.LOGIN, ""));
        }
    }

    private void numNotifications() {
        ConviteController cc = new ConviteController();
        QuantidadeNotificacoes qn = cc.findNotVistos(Usuario.getUsuarioLogado().getId());
        BottomNavigationItemView itemView = (BottomNavigationItemView) bottomNavigationMenuView.getChildAt(1);

        if (qn.getQuantidade() > 0) {
            itemView.removeView(badge);
            itemView.addView(badge);
            TextView lblNotificationBadge = badge.findViewById(R.id.lblNotificationBadge);
            lblNotificationBadge.setText(String.format(Locale.ENGLISH, "%1$d", qn.getQuantidade()));
        } else {
            itemView.removeView(badge);
        }
    }


    //Listener do menu inferior
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        //Toast.makeText(getApplicationContext(), Integer.toString(item.getItemId()) + " " + item.getTitle(), Toast.LENGTH_SHORT).show();
        Fragment fragment = null;
        switch (item.getItemId()) {
            case R.id.navigation_projetos:
                fragment = new ProjetosFragment();
                break;
            case R.id.navigation_notificacoes:
                fragment = new NotificacoesFragment();
                break;
            case R.id.navigation_perfil:
                fragment = new PerfilFragment();
                break;
        }
        return loadFragment(fragment);
    }

    //Carrega fragmento da tela
    private boolean loadFragment(Fragment fragment) {
        //Trocando fragmento
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit();
            return true;
        }
        return false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        numNotifications();
    }

    @Override
    public void updateNotificationsCount() {
        numNotifications();
    }
}
