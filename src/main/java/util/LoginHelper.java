package util;

import org.springframework.context.ApplicationContext;
import service.UserService;

public class LoginHelper {
    private static UserService userService;
    private static ApplicationContext context;

    public static String generate(String name, String surname) {
        context = ApplicationContextFactory.getApplicationContext();
        userService = context.getBean(UserService.class);
        String loginOriginal = name.charAt(0) + "." + surname;
        String login = Translit.cyr2lat(loginOriginal);
        String tempLogin = login;
        int i = 2;
        while (userService.find(login) != null) {
            login = tempLogin;
            login = login + i;
            i++;
        }
        return login;
    }

    public static String generatePassword(){
        int charLength = (int) ((Math.random()*12) +6);
        char[] temp = new char[charLength];
        String possibleChar =
                "QWERTYUIOPLKJHGFDSAZXCVBNM,.mnbvcxzlkjhgfdsapoiuytrewq1234567890-=!@#$%^&*_+";
        for(int i = 0; i < temp.length; i++){
            int randomChar = (int) (Math.random()*possibleChar.length());
            temp[i] = possibleChar.charAt(randomChar);
        }
        return String.valueOf(temp);
    }
}
