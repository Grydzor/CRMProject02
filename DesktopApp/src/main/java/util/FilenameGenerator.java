package util;

public class FilenameGenerator {
    public static String generate() {
        int charLength = 12;
        char[] temp = new char[charLength];
        String possibleChar =
                "QWERTYUIOPLKJHGFDSAZXCVBNMmnbvcxzlkjhgfdsapoiuytrewq1234567890_";
        for(int i = 0; i < temp.length; i++){
            int randomChar = (int) (Math.random()*possibleChar.length());
            temp[i] = possibleChar.charAt(randomChar);
        }
        return String.valueOf(temp);
    }
}
