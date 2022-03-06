package hu.tnote.balint;

import java.util.prefs.Preferences;

public class User {
    private static int ID = -1;
    private static String NAME;
    private static String EMAIL;
    private static String PAT; //Personal access token

    //region Getter / Setter
    public static int getId() {
        return ID;
    }

    public static String getName() {
        return NAME;
    }

    public static String getEmail() {
        return EMAIL;
    }

    public static String getToken() {
        return PAT;
    }

    public static void setId(int id) {
        ID = id;
    }

    public static void setName(String name) {
        NAME = name;
    }

    public static void setEmail(String email) {
        EMAIL = email;
    }

    public static void setToken(String token) {
        Preferences.userRoot().remove("TNotePAT");
        Preferences.userRoot().put("TNotePAT", token);
        User.PAT = token;
    }

    //endregion

    public static void setUser(int id, String name, String email) {
        ID = id;
        NAME = name;
        EMAIL = email;
    }

    public static String asString() {
        return String.format("%3d: %-16s %s", getId(), getName(), getEmail());
    }
}
