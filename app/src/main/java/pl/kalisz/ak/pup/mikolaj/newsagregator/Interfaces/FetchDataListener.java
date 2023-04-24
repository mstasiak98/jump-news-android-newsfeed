package pl.kalisz.ak.pup.mikolaj.newsagregator.Interfaces;

import java.util.List;

import pl.kalisz.ak.pup.mikolaj.newsagregator.Models.Headline;

public interface FetchDataListener<NewsResponse> {

    void onFetchData(List<Headline> news, String message);
    void onError(String message);

}
