package service;

import dao.UserSessionDAO;
import entity.UserSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Scanner;

@Service("userSessionService")
public class UserSessionServiceImpl extends ServiceImpl<UserSession> implements UserSessionService {
    @Autowired
    @Qualifier("userSessionDAO")
    private UserSessionDAO userSessionDAO;

    private UserSessionServiceImpl() {}

    @Override
    @Transactional
    public void createOrUpdate(UserSession session) {
        userSessionDAO.createOrUpdate(session);
    }

    // checks if user session exists locally
    // if yes checks the session in DB
    // if exists method returns UserSession
    private UserSession readFromResource() {
        Long userId;
        Integer sessionId;

        InputStream stream = UserSession.class.getResourceAsStream("/session.properties");
        Scanner scanner = new Scanner(stream);

        if (scanner.hasNextLong()) userId = scanner.nextLong();
        else return null;

        if (scanner.hasNextInt()) sessionId = scanner.nextInt();
        else return null;

        UserSession session = new UserSession();
        session.setUserId(userId);
        session.setSessionId(sessionId);

        return session;
    }

    // restores UserSession from local copy
    @Override
    @Transactional
    public UserSession restoreSession() {
        UserSession from = readFromResource();
        if (from == null) return null;
        UserSession userSession = userSessionDAO.read(from.getUserId());

        if (userSession == null) return null;

        if (!userSession.getSessionId().equals(from.getSessionId())) return null;

        return userSession;
    }

    // when user is logged in write to DB and locally new UserSession
    // if userId == null delete local and DB data; returns deleted UserSession
    @Override
    @Transactional
    public UserSession writeToResource(Long userId) {
        UserSession userSession = new UserSession(userId);
        UserSession from = readFromResource();

        String path = UserSession.class.getResource("/session.properties").getPath();

        File file = new File(path);
        if (!file.exists()) try {
            file.createNewFile();
        } catch (IOException ioe) {
            System.out.println("I can't create new file.");
        }

        try (PrintWriter out = new PrintWriter(file)) {
            if (userId != null) {
                out.println(userId);
                out.println(userSession.getSessionId());
                userSessionDAO.createOrUpdate(userSession);
                return userSession;
            } else {
                out.print("");
                userSessionDAO.delete(from);
                return from;
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
            return null;
        }
    }
}
