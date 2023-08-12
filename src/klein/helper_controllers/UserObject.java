package klein.helper_controllers;

public class UserObject {
    private static String userName;
    private static int userID;

    public static String getUserName() {
        return userName;
    }

    public static void setUserName(String userName) {
        UserObject.userName = userName;
    }

    public static int getUserID() {
        return userID;
    }

    public static void setUserID(int userID) {
        UserObject.userID = userID;
    }

}
