package pl.kalisz.ak.pup.mikolaj.newsagregator.Interfaces;

import pl.kalisz.ak.pup.mikolaj.newsagregator.Models.NewsResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiCall {
    @GET("top-headlines")
    Call<NewsResponse> callApi(
            @Query("country") String country,
            @Query("category") String category,
            @Query("q") String query,
            @Query("pageSize") int pageSize,
            @Query("apiKey") String apiKey
    );
}
