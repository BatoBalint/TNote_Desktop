package hu.tnote.balint;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.Buffer;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Api {
    private static final String BASE_URL = "http://localhost:8000/";
    private static final String API_URL = BASE_URL + "api/";
    private static final String TEST_TOKEN = "";

    //region Auth
    public static String logout() throws IOException {
        HttpURLConnection conn = postConn("logout");
        setBearer(conn, User.getToken());
        conn.connect();

        User.setToken("");
        return getResponse(conn);
    }

    public static int login(String email, String password) throws IOException, ParseException {
        HashMap<String, String> loginData = new HashMap<>();
        loginData.put("email", email);
        loginData.put("password", password);

        HttpURLConnection conn = sendPostData("login", loginData, "");

        int statuscode = conn.getResponseCode();
        if (statuscode / 100 == 2) {
            processReceivedAuthData(conn);
        }

        return statuscode;
    }

    public static int register(String name, String email, String password) throws IOException, ParseException {
        HashMap<String, String> regData = new HashMap<>();
        regData.put("name", name);
        regData.put("email", email);
        regData.put("password", password);

        HttpURLConnection conn = sendPostData("register", regData, "");

        int statuscode = conn.getResponseCode();
        if (statuscode / 100 == 2) {
            processReceivedAuthData(conn);
        }

        return statuscode;
    }
    //endregion

    //region Notes
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
            String title = (jsonObject.get("title") != null) ? jsonObject.get("title").toString() : "";
            String content = (jsonObject.get("content") != null) ? jsonObject.get("content").toString() : "";
            Note n = new Note(id, ownerId, title, content);
            noteList.add(n);
        }
        return noteList;
    }

    public static void saveNote(int id, String title, String content) throws IOException {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("title", title);
        hashMap.put("content", content);
        hashMap.put("ownerId", User.getId() + "");
        HttpURLConnection conn = sendPatchData("notes/" + id, hashMap, User.getToken());

        checkStatusCode(conn);
        //writeResponseMessage(conn);
    }

    public static Note addNote(int id, String title, String content) throws IOException, ParseException {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("title", title);
        hashMap.put("content", content);
        hashMap.put("ownerId", User.getId() + "");
        HttpURLConnection conn = sendPostData("notes", hashMap, User.getToken());

        JSONObject jsonObject = getJSONObject(conn);
        Note note = new Note(Integer.parseInt(jsonObject.get("id").toString()),
                Integer.parseInt(jsonObject.get("ownerId").toString()),
                jsonObject.get("title").toString(),
                (jsonObject.get("content") == null) ? "" : jsonObject.get("content").toString());

        checkStatusCode(conn);
        //writeResponseMessage(conn);
        return note;
    }

    public static void deleteNote(int id) throws IOException {
        HttpURLConnection conn = deleteConn("notes/" + id);
        setBearer(conn, User.getToken());

        conn.connect();
        checkStatusCode(conn);
    }
    //endregion

    //region Timetable
    public static List<TimetableElement> getTimetableElements() throws IOException, ParseException {
        HttpURLConnection conn = getConn("users/" + User.getId() + "/fulltimetables");
        setBearer(conn, User.getToken());
        conn.connect();

        checkStatusCode(conn);

        List<TimetableElement> timetableElementList = new ArrayList<>();
        JSONArray jsonArray = getJSONArray(conn);
        for (Object o : jsonArray) {
            JSONObject jsonObject = (JSONObject) o;

            int id = Integer.parseInt(jsonObject.get("id").toString());
            int ttid = Integer.parseInt(jsonObject.get("ttid").toString());
            String day = jsonObject.get("day").toString();
            String title = jsonObject.get("title").toString();
            String description = jsonObject.get("description").toString();
            LocalTime start = LocalTime.parse(jsonObject.get("start").toString());
            LocalTime end = LocalTime.parse(jsonObject.get("end").toString());
            boolean repeating = jsonObject.get("repeating").toString().equals("1");

            TimetableElement tte = new TimetableElement(id, ttid, day, title, description, start, end, repeating);
            timetableElementList.add(tte);
        }
        return timetableElementList;
    }

    public static void saveTTElement(TimetableElement tte) throws IOException {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("ttid", tte.getTtid() + "");
        hashMap.put("day", tte.getDay());
        hashMap.put("title", tte.getTitle());
        hashMap.put("description", tte.getDescription());
        hashMap.put("start", tte.getStart().format(DateTimeFormatter.ISO_LOCAL_TIME));
        hashMap.put("end", tte.getEnd().format(DateTimeFormatter.ISO_LOCAL_TIME));
        hashMap.put("repeating", (tte.isRepeating() ? "1" : "0"));

        HttpURLConnection conn = sendPatchData("ttelements/" + tte.getId(), hashMap, User.getToken());

        checkStatusCode(conn);
//        writeResponseMessage(conn);
    }

    public static void addTTElement(TimetableElement tte) throws IOException {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("ttid", tte.getTtid() + "");
        hashMap.put("day", tte.getDay());
        hashMap.put("title", tte.getTitle());
        hashMap.put("description", tte.getDescription());
        hashMap.put("start", tte.getStart().format(DateTimeFormatter.ISO_LOCAL_TIME));
        hashMap.put("end", tte.getEnd().format(DateTimeFormatter.ISO_LOCAL_TIME));
        hashMap.put("repeating", (tte.isRepeating() ? "1" : "0"));

        HttpURLConnection conn = sendPostData("ttelements", hashMap, User.getToken());

        checkStatusCode(conn);
//        writeResponseMessage(conn);
    }

    public static void deleteTTElement(int id) throws IOException {
        HttpURLConnection conn = deleteConn("ttelements/" + id);
        setBearer(conn, User.getToken());

        conn.connect();
        checkStatusCode(conn);
    }

    public static List<Integer> getTimetableIds() throws IOException, ParseException {
        List<Integer> idList = new ArrayList<>();
        HttpURLConnection conn = getConn("users/" + User.getId() + "/timetables");
        setBearer(conn, User.getToken());
        conn.connect();

        checkStatusCode(conn);

        JSONArray jsonArray = getJSONArray(conn);
        if (jsonArray.isEmpty()) {
            idList.add(createTimetable());
        } else {
            for (Object o : jsonArray) {
                JSONObject jsonObject = (JSONObject) o;
                idList.add(Integer.parseInt(jsonObject.get("id").toString()));
            }
        }
        return idList;
    }

    private static int createTimetable() throws IOException, ParseException {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("userId", User.getId() + "");
        hashMap.put("name", "TimetableDefaultName");

        HttpURLConnection conn = sendPostData("timetables", hashMap, User.getToken());

        JSONArray jsonArray = getJSONArray(conn);
        int id = Integer.parseInt(((JSONObject) jsonArray.get(0)).get("id").toString());

        checkStatusCode(conn);
//        writeResponseMessage(conn);

        return id;
    }
    //endregion

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

    private static String getResponse(HttpURLConnection conn) throws IOException {
        int statusCode = conn.getResponseCode();
        StringBuilder s = new StringBuilder();
        s.append(statusCode).append("|");

        InputStreamReader is;
        if (statusCode / 100 > 2) is = new InputStreamReader(conn.getErrorStream());
        else is = new InputStreamReader(conn.getInputStream());
        BufferedReader br = new BufferedReader(is);

        String line = br.readLine();
        while (line != null && !line.equals("")) {
            s.append(line);
            line = br.readLine();
        }

        br.close();
        is.close();

        return s.toString();
    }

    private static void writeResponseMessage(HttpURLConnection conn) throws IOException {
        StringBuilder s = new StringBuilder();
        s.append("Response message:").append("\n");

        InputStreamReader is = new InputStreamReader(conn.getInputStream());
        BufferedReader br = new BufferedReader(is);

        String line = br.readLine();
        while (line != null) {
            s.append(line).append("\n");
            line = br.readLine();
        }

        br.close();
        is.close();

        System.out.println(s);
    }

    private static HttpURLConnection sendPostData(String apiEndAddress, HashMap<String, String> hashmap, String token) throws IOException {
        HttpURLConnection conn = postConn(apiEndAddress);
        if (!token.isEmpty()) setBearer(conn, token);
        conn.connect();

        return sendDataDefault(conn, hashmap);
    }

    private static HttpURLConnection sendPatchData(String apiEndAddress, HashMap<String, String> hashmap, String token) throws IOException {
        HttpURLConnection conn = patchConn(apiEndAddress);
        if (!token.isEmpty()) setBearer(conn, token);
        conn.connect();

        return sendDataDefault(conn, hashmap);
    }

    private static HttpURLConnection sendDataDefault(HttpURLConnection conn, HashMap<String, String> hashmap) throws IOException {
        JSONObject jsonObject = new JSONObject();
        for (String key : hashmap.keySet()) {
            jsonObject.put(key, hashmap.get(key));
        }

        OutputStreamWriter os = new OutputStreamWriter(conn.getOutputStream());
        BufferedWriter bw = new BufferedWriter(os);

        bw.write(jsonObject.toJSONString());

        bw.close();
        os.close();

        return conn;
    }

    private static void processReceivedAuthData(HttpURLConnection conn) throws IOException, ParseException {
        JSONObject regResponse = getJSONObject(conn);
        String token = regResponse.get("token").toString();
        JSONObject user = getJSONObjectFromString(regResponse.get("user").toString());
        String n = user.get("name").toString();
        String e = user.get("email").toString();
        int i = Integer.parseInt(user.get("id").toString());
        String createdAt = user.get("created_at").toString();
        User.setToken(token);
        User.setCreatedAt(createdAt);
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

    private static HttpURLConnection patchConn(String apiAddressEnd) throws IOException {
        HttpURLConnection conn = defaultConn(apiAddressEnd);
        conn.setRequestProperty("X-HTTP-Method-Override", "PATCH");
        conn.setRequestMethod("POST");
        conn.setDoOutput(true);

        return conn;
    }

    private static HttpURLConnection deleteConn(String apiAddressEnd) throws IOException {
        HttpURLConnection conn = defaultConn(apiAddressEnd);
        conn.setRequestMethod("DELETE");
        conn.setDoOutput(true);

        return conn;
    }

    private static void setBearer(HttpURLConnection conn, String token) {
        token = (!token.equals("")) ? token : TEST_TOKEN;
        conn.setRequestProperty("Authorization", "Bearer " + token);
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
        JSONParser parser = new JSONParser();
        JSONArray jsonArray = new JSONArray();
        if (parser.parse(String.valueOf(stringJson)).getClass().equals(JSONObject.class)) {
            jsonArray.add(parser.parse(String.valueOf(stringJson)));
            return jsonArray;
        } else return (JSONArray) parser.parse(String.valueOf(stringJson));
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
