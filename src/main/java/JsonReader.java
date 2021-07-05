import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javax.imageio.ImageIO;
import javax.swing.*;

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
        ArrayList<String> image = new ArrayList<>();
        for(int i = 0; i < recs.length(); i++) {
            JSONObject rec = recs.getJSONObject(i);
            JSONObject test = rec.getJSONObject("urls");
            image.add(test.get("small").toString());
            System.out.println(test.get("small"));
        }
        System.out.println(image.get(0));
        URL url = new URL(image.get(0));
        BufferedImage i = ImageIO.read(url);
        JFrame frame = new JFrame("FrameDemo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel emptyLabel = new JLabel(new ImageIcon(i));
        emptyLabel.setPreferredSize(new Dimension(175, 100));
        frame.getContentPane().add(emptyLabel, BorderLayout.CENTER);

        //Display the window.
        frame.pack();
        frame.setVisible(true);

    }
}