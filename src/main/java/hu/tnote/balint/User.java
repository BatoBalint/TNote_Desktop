package hu.tnote.balint;

public class User {
    private static int ID = -1;
    private static String NAME;
    private static String EMAIL;

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

    public static void setId(int id) {
        ID = id;
    }

    public static void setName(String name) {
        NAME = name;
    }

    public static void setEmail(String email) {
        EMAIL = email;
    }
    //endregion

    public static void setUser(int id, String name, String email) {
        ID = id;
        NAME = name;
        EMAIL = email;
    }
}
