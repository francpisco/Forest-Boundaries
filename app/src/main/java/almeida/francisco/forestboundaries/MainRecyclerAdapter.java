package almeida.francisco.forestboundaries;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import almeida.francisco.forestboundaries.model.Property;

/**
 * Created by Francisco Almeida on 21/01/2018.
 */

public class MainRecyclerAdapter extends RecyclerView.Adapter<MainRecyclerAdapter.ViewHolder> {

    public interface MainRecyclerListener {
        void onItemClicked(View view, int position);
    }
    private static MainRecyclerListener mainRecyclerListener;

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private CardView cardView;
        public ViewHolder(CardView v) {
            super(v);
            cardView = v;
            cardView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            mainRecyclerListener.onItemClicked(view, this.getAdapterPosition());
        }
    }

    private List<Property> properties;
    private Context context;

    public MainRecyclerAdapter(List<Property> properties, Context context,
                               MainRecyclerListener listener) {
        this.properties = properties;
        this.context = context;
        mainRecyclerListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CardView cardView = (CardView) LayoutInflater.from(context)
                .inflate(R.layout.card_property_main, parent, false);
        return new ViewHolder(cardView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        CardView cardView = holder.cardView;
        TextView ownerTxt = (TextView) cardView.findViewById(R.id.owner_name_card);
        TextView descriptionTxt = (TextView) cardView.findViewById(R.id.prop_description_card);
        ownerTxt.setText(properties
                .get(position)
                .getOwner()
                .getName());
        descriptionTxt.setText(properties.get(position).getLocationAndDescription());
    }

    @Override
    public int getItemCount() {
        return properties.size();
    }


}
