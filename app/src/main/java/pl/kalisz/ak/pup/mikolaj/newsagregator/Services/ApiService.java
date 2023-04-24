package pl.kalisz.ak.pup.mikolaj.newsagregator.Services;

import android.content.Context;
import android.widget.Toast;

import pl.kalisz.ak.pup.mikolaj.newsagregator.Interfaces.ApiCall;
import pl.kalisz.ak.pup.mikolaj.newsagregator.Interfaces.FetchDataListener;
import pl.kalisz.ak.pup.mikolaj.newsagregator.Models.NewsResponse;
import pl.kalisz.ak.pup.mikolaj.newsagregator.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiService {
    Context context;
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://newsapi.org/v2/")
            .addConverterFactory(GsonConverterFactory.create()).build();



    public ApiService(Context context) {
        this.context = context;
    }

    public void getNews(FetchDataListener listener, String category, String query){

        ApiCall apiCall = retrofit.create(ApiCall.class);
        Call<NewsResponse> call = apiCall.callApi("pl", category, query, 100, context.getString(R.string.api_key));

        try{
            call.enqueue(new Callback<NewsResponse>() {
                @Override
                public void onResponse(Call<NewsResponse> call, Response<NewsResponse> response) {
                    if(!response.isSuccessful()){
                        Toast.makeText(context, "Bład", Toast.LENGTH_LONG);
                    }

                    listener.onFetchData(response.body().getArticles(), response.message());
                }

                @Override
                public void onFailure(Call<NewsResponse> call, Throwable t) {
                    listener.onError("Błąd pobierania danych");
                }
            });
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}
