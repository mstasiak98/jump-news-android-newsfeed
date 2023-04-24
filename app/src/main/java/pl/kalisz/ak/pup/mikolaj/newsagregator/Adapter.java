package pl.kalisz.ak.pup.mikolaj.newsagregator;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import pl.kalisz.ak.pup.mikolaj.newsagregator.Interfaces.SelectListener;
import pl.kalisz.ak.pup.mikolaj.newsagregator.Models.Headline;

public class Adapter extends RecyclerView.Adapter<ViewHolder> {

    private Context context;
    private List<Headline> headlines;
    private SelectListener selectListener;

    public Adapter(Context context, List<Headline> headlines, SelectListener listener) {
        this.context = context;
        this.headlines = headlines;
        this.selectListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.list_items, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Headline headline = headlines.get(position);
        holder.t_title.setText(headline.getTitle());
        holder.t_source.setText(headline.getSource().getName());
        if(headline.getUrlToImage() != null) {
            Picasso.get().load(headline.getUrlToImage()).into(holder.img_headline);
        }

        holder.cardView.setOnClickListener(view -> selectListener.onNewsClicked(headlines.get(position)));
    }

    @Override
    public int getItemCount() {
        return headlines.size();
    }
}
