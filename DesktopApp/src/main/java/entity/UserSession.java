package entity;

import org.springframework.context.ApplicationContext;
import javax.persistence.*;
import java.time.LocalDateTime;

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
