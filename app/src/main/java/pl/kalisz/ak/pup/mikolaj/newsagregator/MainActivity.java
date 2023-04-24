package pl.kalisz.ak.pup.mikolaj.newsagregator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.List;
import java.util.logging.Logger;

import pl.kalisz.ak.pup.mikolaj.newsagregator.Interfaces.FetchDataListener;
import pl.kalisz.ak.pup.mikolaj.newsagregator.Interfaces.SelectListener;
import pl.kalisz.ak.pup.mikolaj.newsagregator.Models.Headline;
import pl.kalisz.ak.pup.mikolaj.newsagregator.Models.NewsResponse;
import pl.kalisz.ak.pup.mikolaj.newsagregator.Services.ApiService;

public class MainActivity extends AppCompatActivity implements SelectListener, View.OnClickListener {


    SwipeRefreshLayout swipeRefreshLayout;
    ProgressDialog dialog;
    RecyclerView recyclerView;
    Adapter adapter;
    Button button1, button2, button3, button4, button5, button6, button7, button8;
    SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dialog = new ProgressDialog(this);
        dialog.setTitle("Pobieram nowe wiadomości...");
        dialog.show();


        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.refresh);

        searchView = findViewById(R.id.search_view);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                dialog.setTitle("Pobieram wiadomości ze słowem: " + query);
                dialog.show();
                ApiService apiService = new ApiService(MainActivity.this);
                apiService.getNews(listener, "general", query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        button1 = findViewById(R.id.button1);
        button1.setOnClickListener(this);
        button2 = findViewById(R.id.button2);
        button2.setOnClickListener(this);
        button3 = findViewById(R.id.button3);
        button3.setOnClickListener(this);
        button4 = findViewById(R.id.button4);
        button4.setOnClickListener(this);
        button5 = findViewById(R.id.button5);
        button5.setOnClickListener(this);
        button6 = findViewById(R.id.button6);
        button6.setOnClickListener(this);
        button7 = findViewById(R.id.button7);
        button7.setOnClickListener(this);

        ApiService apiService = new ApiService(this);
        apiService.getNews(listener, "general", null);


        swipeRefreshLayout.setOnRefreshListener(() -> {
            apiService.getNews(listener, "general", null);
            swipeRefreshLayout.setRefreshing(false);
        });
    }

    private final FetchDataListener<NewsResponse> listener = new FetchDataListener<NewsResponse>() {
        @Override
        public void onFetchData(List<Headline> news, String message) {
            if(!news.isEmpty()){
                showNews(news);
                dialog.dismiss();
            } else {
                Toast.makeText(MainActivity.this, "Nie znaleziono wiadomości", Toast.LENGTH_LONG).show();
                showNews(news);
                dialog.dismiss();
            }
        }

        @Override
        public void onError(String message) {
            Toast.makeText(MainActivity.this, "Wystąpił błąd podczas pobierania danych", Toast.LENGTH_LONG).show();
            Log.d("blad", message);
        }
    };

    private void showNews(List<Headline> news) {
        recyclerView = findViewById(R.id.main_recycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 1));
        adapter = new Adapter(this, news, this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onNewsClicked(Headline headline) {
        Intent intent = new Intent(this, NewsDetailsActivity.class);
        intent.putExtra("data", headline);
        startActivity(intent);
    }

    @Override
    public void onClick(View view) {
        Button clickedButton = (Button) view;
        String category = getNewsCategory((String) clickedButton.getText());

        dialog.setTitle("Pobieram kategorię : " + clickedButton.getText());
        dialog.show();

        ApiService apiService = new ApiService(this);
        apiService.getNews(listener, category, null);
    }

    private String getNewsCategory(String text) {
        String category;
        switch (text){
            case "biznes":
                category = "business";
                break;
            case "rozrywka":
                category = "entertainment";
                break;
            case "ogólne":
                category = "general";
                break;
            case "zdrowie":
                category = "health";
                break;
            case "nauka":
                category = "science";
                break;
            case "sport":
                category = "sports";
                break;
            case "technologia":
                category = "technology";
                break;
            default:
                category = "general";
        }
        return category;
    }
}