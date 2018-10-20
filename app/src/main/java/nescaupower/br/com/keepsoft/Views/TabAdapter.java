package nescaupower.br.com.keepsoft.Views;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import nescaupower.br.com.keepsoft.Views.Equipe.EquipeFragment;
import nescaupower.br.com.keepsoft.Views.Sprint.SprintFragment;
import nescaupower.br.com.keepsoft.Views.Tarefa.TarefaFragment;

public class TabAdapter extends FragmentPagerAdapter {

    private int numOfTabs;

    public TabAdapter(FragmentManager fm, int numOfTabs) {
        super(fm);
        this.numOfTabs = numOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new SprintFragment();
            case 1:
                return new TarefaFragment();
            case 2:
                return new EquipeFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return numOfTabs;
    }
}
