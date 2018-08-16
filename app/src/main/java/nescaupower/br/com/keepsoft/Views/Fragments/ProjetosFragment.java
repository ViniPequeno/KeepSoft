package nescaupower.br.com.keepsoft.Views.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import nescaupower.br.com.keepsoft.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProjetosFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_projetos, null);

        RecyclerView rv = rootView.findViewById(R.id.ProjetosRV);

        //MyAdapter adapter = new MyAdapter(this.getActivity(), projetos);
        //rv.setAdapter(adapter);

        return rootView;
    }

}
