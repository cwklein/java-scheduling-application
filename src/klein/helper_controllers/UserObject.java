package klein.helper_controllers;

public class UserObject {
    private static String userName;
    private static int userID;

    /**
     * Basic getter function for the private attribute 'userName'.
     * @return string-type private attribute 'userName'.
     * */
    public static String getUserName() {
        return userName;
    }

    /**
     * Basic setter function for the private attribute 'userName'.
     * @param userName string to be assigned to private attribute 'userName'.
     * */
    public static void setUserName(String userName) {
        UserObject.userName = userName;
    }

    /**
     * Basic getter function for the private attribute 'userID'.
     * @return integer-type private attribute 'userID'.
     * */
    public static int getUserID() {
        return userID;
    }

    /**
     * Basic setter function for the private attribute 'userID'.
     * @param userID Integer to be assigned to private attribute 'userID'.
     * */
    public static void setUserID(int userID) {
        UserObject.userID = userID;
    }

}
