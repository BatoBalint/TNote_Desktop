package hu.tnote.balint;

import java.util.prefs.Preferences;

public class User {
    private static int ID = -1;
    private static String NAME;
    private static String EMAIL;
    private static String PAT; //Personal access token
    private static String CREATED_AT;
    private static boolean REMAIN_SINGED_IN = false;

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

    public static String getCreatedAt() {
        String[] half = CREATED_AT.split("T");

        return half[0] + " " + half[1].split("\\.")[0];
    }

    public static boolean isRemainSingedIn() {
        return REMAIN_SINGED_IN;
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
        if (token.isEmpty()) {
            Preferences.userRoot().remove("TNoteUserId");
            Preferences.userRoot().remove("TNoteUserName");
            Preferences.userRoot().remove("TNoteUserEmail");
        }
    }

    public static void setCreatedAt(String createdAt) {
        CREATED_AT = createdAt;
        Preferences.userRoot().put("TNoteUserCreatedAt", createdAt);
    }

    public static void setRemainSingedIn(boolean remainSingedIn) {
        REMAIN_SINGED_IN = remainSingedIn;
    }

    //endregion

    public static void setUser(int id, String name, String email) {
        ID = id;
        NAME = name;
        EMAIL = email;
        Preferences.userRoot().putInt("TNoteUserId", id);
        Preferences.userRoot().put("TNoteUserName", name);
        Preferences.userRoot().put("TNoteUserEmail", email);
    }

    public static String asString() {
        return String.format("%3d: %-16s %s", getId(), getName(), getEmail());
    }
}
