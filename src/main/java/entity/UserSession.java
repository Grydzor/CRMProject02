package entity;

import org.springframework.context.ApplicationContext;
import service.UserService;
import service.UserSessionService;
import util.ApplicationContextFactory;

import javax.persistence.*;
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
    private Long userId;

    @Column(name = "SESSION_ID")
    private Integer sessionId;

    public UserSession() {}

    public UserSession(Long userId) {
        this.userId = userId;
        sessionId = LocalDateTime.now().toString().hashCode();
    }

    public Long getUserId() {
        return userId;
    }
    public void setUserId(Long userId) {
        this.userId = userId;
    }
    public Integer getSessionId() {
        return sessionId;
    }
    public void setSessionId(Integer sessionId) {
        this.sessionId = sessionId;
    }

    @Override
    public String toString() {
        return "UserSession{userId = '" + userId + "', " +
                "sessionId = '" + sessionId + "'}";
    }
}
