package entity;

import org.springframework.context.ApplicationContext;
import service.UserSessionService;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.util.Scanner;

/**
 * Created by eriol4ik on 19.02.2017.
 */

@Entity
@Table(name = "Session")
public class UserSession {
    private static ApplicationContext context;

    @Id
    @Column(name = "EMPLOYEE_ID")
    private Long userId;

    @Column(name = "SESSION_ID")
    private Integer sessionId;

    public UserSession() {}

    private void setEmployeeId(Long userId) {
        this.userId = userId;
    }

    private void setSessionId(Integer sessionId) {
        this.sessionId = sessionId;
    }

    public Integer getSessionId() {
        return sessionId;
    }

    public Long getUserId() {
        return userId;
    }

    public static UserSession readFromResource() {
        InputStream stream = UserSession.class.getResourceAsStream("/session.properties");
        Scanner scanner = new Scanner(stream);

        UserSession userSession = new UserSession();
        if (scanner.hasNextLong()) userSession.setEmployeeId(scanner.nextLong());
        else return null;

        if (scanner.hasNextInt()) userSession.setSessionId(scanner.nextInt());
        else return null;

        return userSession;
    }

    public static UserSession writeToResource(Long userId) {
        if (userId != null && userId.equals(-1L)) return null;
        UserSessionService userSessionService = context.getBean(UserSessionService.class);

        UserSession userSession = new UserSession();
        userSession.userId = userId;
        userSession.sessionId = LocalDateTime.now().toString().hashCode();

        UserSession from = readFromResource();

        String path = UserSession.class.getResource("/session.properties").getPath();

        File file = new File(path);

        try (PrintWriter out = new PrintWriter(file.getAbsoluteFile())) {
            if (userId == null) {
                if (from == null) return null;
                userSessionService.delete(from);
                out.print(""); return null; }
            out.println(userId);
            out.println(userSession.sessionId);
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

        userSessionService.createOrUpdate(userSession);
        return userSession;
    }
}
