package almeida.francisco.forestboundaries;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import almeida.francisco.forestboundaries.model.MyMarker;

/**
 * Created by Francisco Almeida on 14/01/2018.
 */

public class LabelledMarkersAdapter extends RecyclerView.Adapter<LabelledMarkersAdapter.ViewHolder> {

    public interface RecyclerViewClickListener {
        void onItemClicked(View view, int position);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private CardView cardView;
        public ViewHolder(CardView v) {
            super(v);
            cardView = v;
            cardView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            listener.onItemClicked(view, this.getAdapterPosition());
        }
    }

    private static RecyclerViewClickListener listener;
    private final char[] NUM_TO_CHAR = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L',
            'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};

    private List<MyMarker> markers = new ArrayList<>();
    private Context context;

    public LabelledMarkersAdapter(List<MyMarker> markers, Context context,
                                  RecyclerViewClickListener listener) {
        this.markers = markers;
        this.context = context;
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CardView cardView = (CardView) LayoutInflater.from(context)
                .inflate(R.layout.card_markers_edit, parent, false);
        return new ViewHolder(cardView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        CardView cardView = holder.cardView;
        TextView idText = (TextView) cardView.findViewById(R.id.card_id);
        idText.setText("" + NUM_TO_CHAR[position]);
        TextView descriptionText = (TextView) cardView.findViewById(R.id.card_text);
        descriptionText.setText(Double.toString(markers.get(position).getMarkedLatitude()) + " " +
                Double.toString(markers.get(position).getMarkedLongitude()));
    }

    @Override
    public int getItemCount() {
        return markers.size();
    }
}
