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