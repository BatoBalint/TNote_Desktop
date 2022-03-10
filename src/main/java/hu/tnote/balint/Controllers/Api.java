package hu.tnote.balint.Controllers;

import hu.tnote.balint.Note;
import hu.tnote.balint.User;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Api {
    private static final String BASE_URL = "http://localhost:8000/";
    private static final String API_URL = BASE_URL + "api/";
    private static final String TEST_TOKEN = "";

    public static List<Note> getNotes() throws IOException, ParseException {
        HttpURLConnection conn = getConn("users/" + User.getId() + "/notes");
        setBearer(conn, User.getToken());
        conn.connect();

        checkStatusCode(conn);

        List<Note> noteList = new ArrayList<>();
        JSONArray jsonArray = getJSONArray(conn);
        for (Object o : jsonArray) {
            JSONObject jsonObject = (JSONObject) o;
            int id = Integer.parseInt(jsonObject.get("id").toString());
            int ownerId = Integer.parseInt(jsonObject.get("ownerId").toString());
            String title = jsonObject.get("title").toString();
            String content = jsonObject.get("content").toString();
            Note n = new Note(id, ownerId, title, content);
            noteList.add(n);
        }
        return noteList;
    }

    public static void logout() throws IOException {
        HttpURLConnection conn = postConn("logout");
        setBearer(conn, User.getToken());
        conn.connect();

        checkStatusCode(conn);
        User.setToken("");
    }

    public static void login(String email, String password) throws IOException, ParseException {
        HttpURLConnection conn = sendData("login", "", email, password);
        checkStatusCode(conn);
        processReceivedData(conn);
    }

    public static void register(String name, String email, String password) throws IOException, ParseException {
        HttpURLConnection conn = sendData("register", name, email, password);
        checkStatusCode(conn);
        processReceivedData(conn);
    }

    private static void checkStatusCode(HttpURLConnection conn) throws IOException {
        int statusCode = conn.getResponseCode();
        if (statusCode / 100 != 2) {
            StringBuilder s = new StringBuilder();
            s.append("Error in request:\n");
            s.append("Response code: ").append(statusCode).append("\n");

            InputStreamReader is = new InputStreamReader(conn.getErrorStream());
            BufferedReader br = new BufferedReader(is);

            String line = br.readLine();
            while (line != null) {
                s.append(line).append("\n");
                line = br.readLine();
            }

            br.close();
            is.close();
            throw new RuntimeException(String.valueOf(s));
        }
    }

    private static HttpURLConnection sendData(String apiEndAddress, String name, String email, String password) throws IOException {
        HttpURLConnection conn = postConn(apiEndAddress);
        conn.connect();

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name", name);
        jsonObject.put("email", email);
        jsonObject.put("password", password);

        OutputStreamWriter os = new OutputStreamWriter(conn.getOutputStream());
        BufferedWriter bw = new BufferedWriter(os);

        bw.write(jsonObject.toJSONString());

        bw.close();
        os.close();

        return conn;
    }

    private static void processReceivedData(HttpURLConnection conn) throws IOException, ParseException {
        JSONObject regResponse = getJSONObject(conn);
        String token = regResponse.get("token").toString();
        System.out.println(token);
        JSONObject user = getJSONObjectFromString(regResponse.get("user").toString());
        String n = user.get("name").toString();
        String e = user.get("email").toString();
        int i = Integer.parseInt(user.get("id").toString());
        User.setToken(token);
        User.setUser(i, n, e);
    }

    //region Tools
    private static HttpURLConnection defaultConn(String apiAddressEnd) throws IOException {
        HttpURLConnection conn = (HttpURLConnection) new URL(API_URL + apiAddressEnd).openConnection();
        conn.setRequestProperty("Accept", "application/json");
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setConnectTimeout(10000);
        conn.setReadTimeout(10000);

        return conn;
    }

    private static HttpURLConnection getConn(String apiAddressEnd) throws IOException {
        HttpURLConnection conn = defaultConn(apiAddressEnd);
        conn.setRequestMethod("GET");

        return conn;
    }

    private static HttpURLConnection postConn(String apiAddressEnd) throws IOException {
        HttpURLConnection conn = defaultConn(apiAddressEnd);
        conn.setRequestMethod("POST");
        conn.setDoOutput(true);

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

    private static JSONArray getJSONArrayFromString(String stringJson) throws ParseException {
        JSONParser perser = new JSONParser();
        return (JSONArray) perser.parse(String.valueOf(stringJson));
    }

    private static JSONArray getJSONArray(HttpURLConnection conn) throws IOException, ParseException {
        String jsonString = readJson(conn);
        return getJSONArrayFromString(jsonString);
    }

    private static JSONObject getJSONObjectFromString(String stringJson) throws ParseException {
        JSONParser perser = new JSONParser();
        return (JSONObject) perser.parse(String.valueOf(stringJson));
    }

    private static JSONObject getJSONObject(HttpURLConnection conn) throws IOException, ParseException {
        String jsonString = readJson(conn);
        return getJSONObjectFromString(jsonString);
    }
    //endregion
}
