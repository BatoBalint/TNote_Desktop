package hu.tnote.balint.Controllers;

import hu.tnote.balint.Note;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Api {
    private static final String BASE_URL = "http://localhost:8000";
    private static final String API_URL = BASE_URL + "/api/";
    private static final String TEST_TOKEN = "1|Aytns2Ed1IOQL32weTcUEvCqyJBWcxv3XemQExkF";

    public static List<Note> getNotes() throws IOException, ParseException {
        HttpURLConnection conn = getConn("notes");
        setBearer(conn, "");
        conn.connect();

        int status = conn.getResponseCode();

        if (status != 200) {
            throw new RuntimeException("API response code: " + status);
        } else {
            String s = readJson(conn);

            JSONParser perser = new JSONParser();
            JSONArray jsonArray = (JSONArray) perser.parse(String.valueOf(s));

            List<Note> noteList = new ArrayList<>();

            for (Object o : jsonArray) {
                JSONObject jsonObject = (JSONObject) o;
                int id = Integer.parseInt(jsonObject.get("id").toString());
                int ownerId = Integer.parseInt(jsonObject.get("ownerId").toString());
                String title = jsonObject.get("title").toString();
                String content = jsonObject.get("content").toString();

                Note n = new Note(id, ownerId, title, content);
                noteList.add(n);
                //System.out.println(n);
            }

            return noteList;
        }
    }

    private static HttpURLConnection getConn(String apiAddressEnd) throws IOException {
        HttpURLConnection conn = (HttpURLConnection) new URL(API_URL + apiAddressEnd).openConnection();
        conn.setRequestMethod("GET");

        return conn;
    }

    private static HttpURLConnection setBearer(HttpURLConnection conn, String token) {
        token = (!token.equals("")) ? token : TEST_TOKEN;
        conn.setRequestProperty("Authorization", "Bearer " + token);
        return conn;
    }

    private static String readJson(HttpURLConnection conn) throws IOException {
        StringBuilder s = new StringBuilder();

        InputStreamReader is = new InputStreamReader(conn.getInputStream());
        BufferedReader br = new BufferedReader(is);
        String line = br.readLine();

        while (line != null) {
            s.append(line);
            s.append("\n");
            line = br.readLine();
        }

        br.close();
        is.close();

        return String.valueOf(s);
    }
}
