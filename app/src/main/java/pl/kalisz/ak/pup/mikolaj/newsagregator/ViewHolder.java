package pl.kalisz.ak.pup.mikolaj.newsagregator;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class ViewHolder extends RecyclerView.ViewHolder {
    TextView t_title;
    TextView t_source;
    ImageView img_headline;
    CardView cardView;
    public ViewHolder(@NonNull View itemView) {
        super(itemView);
        t_title = itemView.findViewById(R.id.t_title);
        t_source = itemView.findViewById(R.id.t_source);
        img_headline = itemView.findViewById(R.id.img_headline);
        cardView = itemView.findViewById(R.id.container);

    }
}
