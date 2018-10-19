package nescaupower.br.com.keepsoft.Views.Sprint;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.List;

import nescaupower.br.com.keepsoft.Factory.Model.Sprint;
import nescaupower.br.com.keepsoft.R;
import nescaupower.br.com.keepsoft.Views.Sprint.SprintFragment.OnListFragmentInteractionListener;

/**
 * {@link RecyclerView.Adapter} that can display a {@link Sprint} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class SprintRVAdapter extends RecyclerView.Adapter<SprintRVAdapter.ViewHolder> {
    private Context context;
    private final List<Sprint> sprints;
    private final OnListFragmentInteractionListener mListener;

    public SprintRVAdapter(OnListFragmentInteractionListener listener, List<Sprint> items, Context context) {
        sprints = items;
        mListener = listener;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_sprint_model, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Resources res = context.getResources();

        holder.mItem = sprints.get(position);

        holder.lblIndex.setText(Integer.toString(position + 1));
        holder.lblTitulo.setText(sprints.get(position).getTitulo());

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        String stringDataInicio = res.getString(R.string.sprint_since_date, sdf.format(sprints.get(position).getDataInicio()));
        holder.lblDataInicio.setText(stringDataInicio);

        String stringDataFim = res.getString(R.string.sprint_until_date, sdf.format(sprints.get(position).getDataFim()));
        holder.lblDataFim.setText(stringDataFim);

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    Intent i = new Intent(context, DetalhesSprintActivity.class);

                    i.putExtra("EXTRA_CODIGO_SPRINT", holder.mItem.getId());

                    context.startActivity(i);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return sprints.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView lblIndex;
        public final TextView lblTitulo;
        public final TextView lblDataInicio;
        public final TextView lblDataFim;
        public Sprint mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            lblIndex = view.findViewById(R.id.lblId);
            lblTitulo = view.findViewById(R.id.lblTitulo);
            lblDataInicio = view.findViewById(R.id.lblDataInicio);
            lblDataFim = view.findViewById(R.id.lblDataFim);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + lblTitulo.getText() + "'";
        }
    }
}
