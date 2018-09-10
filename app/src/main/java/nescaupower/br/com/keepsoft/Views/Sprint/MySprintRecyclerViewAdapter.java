package nescaupower.br.com.keepsoft.Views.Sprint;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import nescaupower.br.com.keepsoft.R;
import nescaupower.br.com.keepsoft.Views.Sprint.SprintFragment.OnListFragmentInteractionListener;
import nescaupower.br.com.keepsoft.Factory.Model.Sprint;

/**
 * {@link RecyclerView.Adapter} that can display a {@link Sprint} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MySprintRecyclerViewAdapter extends RecyclerView.Adapter<MySprintRecyclerViewAdapter.ViewHolder> {

    private final List<Sprint> sprints;
    private final OnListFragmentInteractionListener mListener;

    public MySprintRecyclerViewAdapter(OnListFragmentInteractionListener listener,List<Sprint> items) {
        sprints = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_sprint_model, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = sprints.get(position);
        holder.mIdView.setText(Long.toString(sprints.get(position).getCodigo()));
        holder.mContentView.setText(sprints.get(position).getTitulo());

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mItem);
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
        public final TextView mIdView;
        public final TextView mContentView;
        public Sprint mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mIdView = view.findViewById(R.id.lblId);
            mContentView = view.findViewById(R.id.lblTitulo);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}
