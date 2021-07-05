//import java.net.URLEncoder;
//import com.mashape.unirest.http.HttpResponse;
//import com.mashape.unirest.http.JsonNode;
//import com.mashape.unirest.http.Unirest;
//import com.google.gson.Gson;
//import com.google.gson.GsonBuilder;
//import com.google.gson.JsonElement;
//import com.google.gson.JsonParser;
//
//public class Unsplash {
//    public static void main(String[] args) throws Exception {
//        // Host url
//        String host = "https://api.unsplash.com/search/photos";
//        String charset = "UTF-8";
//        // Headers for a request
//        String x_rapidapi_host = "api.unsplash.com";
//        String x_rapidapi_key = "w7ykP8fOoON44GakQwDgWRJzCR2izyKBRunjixJEfh8";//Type here your key
//        // Params
//        String s = "dog";
//
//        // Host, charset and headers vars should be the same
//        String i = "tt0110912";
//        // Format query for preventing encoding problems
//        String query = String.format("i=%s",
//                URLEncoder.encode(i, charset));
//        // Json response
//        HttpResponse <JsonNode> response = Unirest.get(host + "?" + s + "&client_id=")
//                .header("x-rapidapi-host", x_rapidapi_host)
//                .header("x-rapidapi-key", x_rapidapi_key)
//                .asJson();
//        //Prettifying
//        Gson gson = new GsonBuilder().setPrettyPrinting().create();
//        JsonParser jp = new JsonParser();
//        JsonElement je = jp.parse(response.getBody().toString());
//        String prettyJsonString = gson.toJson(je);
//        System.out.println(prettyJsonString);
//
////        // Format query for preventing encoding problems
////        String query = String.format("s=%s",
////                URLEncoder.encode(s, charset));
////
////
////        HttpResponse <JsonNode> response = Unirest.get(host + "?" + query)
////                .header("x-rapidapi-host", x_rapidapi_host)
////                .header("x-rapidapi-key", x_rapidapi_key)
////                .asJson();
////        System.out.println(response.getStatus());
////        System.out.println(response.getHeaders().get("Content-Type"));
//    }
//}
import java.io.*;
import java.net.URL;
import java.nio.charset.Charset;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JsonReader {

    private static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }

    public static JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
        InputStream is = new URL(url).openStream();
        try {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String jsonText = readAll(rd);
            JSONObject json = new JSONObject(jsonText);
            return json;
        } finally {
            is.close();
        }
    }

    public static void main(String[] args) throws IOException, JSONException {
        JSONObject json = readJsonFromUrl("https://api.unsplash.com/search/photos?query=dog&client_id=w7ykP8fOoON44GakQwDgWRJzCR2izyKBRunjixJEfh8");

        System.out.println(json.getClass());
        System.out.println(json.get("results"));
        JSONArray recs = json.getJSONArray("results");

        for(int i = 0; i < recs.length(); i++) {
            JSONObject rec = recs.getJSONObject(i);
            JSONObject test = rec.getJSONObject("urls");
            System.out.println(test.get("small"));
//            for(int j = 0; j < test.length(); j++) {
//                JSONObject a = test.getJSONObject(j);
//                System.out.println(a.getJSONObject("small"));
//            }
        }
    }
}