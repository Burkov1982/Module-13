package DataStorage;


import com.google.gson.Gson;

import java.net.http.HttpClient;

public class Utils {
    public static HttpClient client = HttpClient.newHttpClient();
    public static final String URI = "https://jsonplaceholder.typicode.com/";
    public static final String ADD_USER_PART = "users";
    public static Gson gson = new Gson();
}
