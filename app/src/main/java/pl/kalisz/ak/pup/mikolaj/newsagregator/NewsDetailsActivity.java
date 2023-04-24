package pl.kalisz.ak.pup.mikolaj.newsagregator;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import pl.kalisz.ak.pup.mikolaj.newsagregator.Models.Headline;

public class NewsDetailsActivity extends AppCompatActivity implements View.OnClickListener {

    Headline headline;
    TextView title;
    TextView author;
    TextView time;
    TextView detail;
    TextView content;
    ImageView image;
    Button navButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_details);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        title = findViewById(R.id.detail_title);
        author = findViewById(R.id.detal_author);
        time = findViewById(R.id.detal_time);
        detail = findViewById(R.id.detail_text_details);
        content = findViewById(R.id.detail_content);
        image = findViewById(R.id.detail_img);
        navButton = findViewById(R.id.navigate_button);

        navButton.setOnClickListener(this);

        headline = (Headline) getIntent().getSerializableExtra("data");

        title.setText(headline.getTitle());
        author.setText(headline.getAuthor());
        time.setText(headline.getPublishedAt());
        detail.setText(headline.getDescription());
        content.setText(headline.getContent());

        Picasso.get().load(headline.getUrlToImage()).into(image);

    }

    @Override
    public void onClick(View view) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(headline.getUrl()));
        startActivity(browserIntent);
    }
}