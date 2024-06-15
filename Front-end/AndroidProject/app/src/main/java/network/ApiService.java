package network;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {
    @GET("/")
    Call<Map<String, String>> getRoot();

    @GET("/items/{item_id}")
    Call<Map<String, String>> getItem(@Path("item_id") int itemId, @Query("q") String query);
}
