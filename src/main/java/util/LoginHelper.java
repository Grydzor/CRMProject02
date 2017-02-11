package util;

import service.Service;
import service.ServiceImpl;

/**
 * Created by eriol4ik on 11.02.2017.
 */
public class LoginHelper {
    private static Service service;

    static {
        service = new ServiceImpl();
    }

    public static String generate(String name, String surname) {
        String loginOriginal = name.charAt(0) + "." + surname;
        String login = Translit.cyr2lat(loginOriginal);
        String tempLogin = login;
        int i = 2;
        while (service.find(login) != null) {
            login = tempLogin;
            login = login + i;
            i++;
        }
        return login;
    }
}
