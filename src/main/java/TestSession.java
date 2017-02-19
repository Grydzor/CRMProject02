import entity.UserSession;

/**
 * Created by eriol4ik on 19.02.2017.
 */
public class TestSession {
    public static void main(String[] args) {
        UserSession userSession = UserSession.writeToResource(null);

        /*InputStream stream = UserSession.class.getResourceAsStream("/userSession.properties");
        Scanner scanner = new Scanner(stream);

        System.out.println(scanner.nextLong());
        System.out.println(scanner.nextInt())*/;

        UserSession userSession1 = UserSession.readFromResource();
        if (userSession1 != null) {
            System.out.println(userSession1.getUserId());
            System.out.println(userSession1.getSessionId());
        }
    }
}
