package util;

import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.File;
import java.math.BigDecimal;
import java.sql.Date;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* Класс принимает поля ввода (текста и чисел - TextField,
   даты - DatePicker) и:
*       - если поле пустое (или содержит только пробелы),
*       - если числа не парсятся (Long, Double, Integer),
*  то возвращается null и граница поля становится красной.
*
*  В других случаях, если информация введена правильно,
*  возвращается требуемое значение этого поля и граница
*  поля становится обычной.
*
* */
public class InputDataChecker {
    private static final String STYLE_OK = "-fx-border-color: inherit;";
    private static final String STYLE_BAD = "-fx-border-color: red;" +
                                            "-fx-border-radius: inherit";

    private static DecimalFormat decimalFormat = new DecimalFormat("#0.00");

    public static Date checkDate(DatePicker datePicker) {
        LocalDate localDate = datePicker.getValue();
        if (localDate == null) {
            datePicker.setStyle(STYLE_BAD);
            return null;
        }

        datePicker.setStyle(STYLE_OK);
        return Date.valueOf(localDate);
    }

    public static String checkString(TextField textField) {
        String text = textField.getText().trim();
        if (text.isEmpty()) {
            textField.setStyle(STYLE_BAD);
            return null;
        }

        textField.setStyle(STYLE_OK);
        return text;
    }

    public static String checkString(TextArea textArea) {
        String text = textArea.getText().trim();
        if (text.isEmpty()) {
            textArea.setStyle(STYLE_BAD);
            return null;
        }

        textArea.setStyle(STYLE_OK);
        return text;
    }

    public static Double checkDouble(TextField textField) {
        String text = textField.getText().trim();
        if (text.isEmpty()) {
            textField.setStyle(STYLE_BAD);
            return null;
        }

        Double num;
        try {
            num = Double.parseDouble(text);
        } catch (NumberFormatException nfe) {
            textField.setStyle(STYLE_BAD);
            return null;
        }

        textField.setStyle(STYLE_OK);
        return num;
    }

    public static BigDecimal checkBigDecimal(TextField textField) {
        String text = textField.getText().trim();
        if (text.isEmpty()) {
            textField.setStyle(STYLE_BAD);
            return null;
        }

        BigDecimal num;
        try {
            num = new BigDecimal(decimalFormat.parse(text).doubleValue());
        } catch (NumberFormatException | ParseException e) {
            textField.setStyle(STYLE_BAD);
            return null;
        }

        textField.setStyle(STYLE_OK);
        return num;
    }

    public static Long checkLong(TextField textField) {
        String text = textField.getText().trim();
        if (text.isEmpty()) {
            textField.setStyle(STYLE_BAD);
            return null;
        }

        Long num;
        try {
            num = Long.parseLong(text);
        } catch (NumberFormatException nfe) {
            textField.setStyle(STYLE_BAD);
            return null;
        }

        textField.setStyle(STYLE_OK);
        return num;
    }

    public static Integer checkInteger(TextField textField) {
        String text = textField.getText().trim();
        if (text.isEmpty()) {
            textField.setStyle(STYLE_BAD);
            return null;
        }

        Integer num;
        try {
            num = Integer.parseInt(text);
        } catch (NumberFormatException nfe) {
            textField.setStyle(STYLE_BAD);
            return null;
        }

        textField.setStyle(STYLE_OK);
        return num;
    }

    public static Integer checkAmount(TextField amountField) {
        String text = amountField.getText().trim();
        if (text.isEmpty()) {
            amountField.setStyle(STYLE_BAD);
            return null;
        }

        Integer num;
        try {
            num = Integer.parseInt(text);
            if (num <= 0) throw new NumberFormatException();
        } catch (NumberFormatException nfe) {
            amountField.setStyle(STYLE_BAD);
            return null;
        }

        amountField.setStyle(STYLE_OK);
        return num;
    }

    public static <T> T checkEnum(ComboBox<T> box) {
        if (box.getValue() == null) {
            box.setStyle(STYLE_BAD);
            return null;
        }

        box.setStyle(STYLE_OK);
        return box.getValue();
    }

    public static boolean checkPassword(TextField passwordField) {
        String password = passwordField.getText().trim();
        if (password.isEmpty() || password.length() < 8 || password.length() > 16) {
            passwordField.setStyle(STYLE_BAD);
            return false;
        } else {
            passwordField.setStyle(STYLE_OK);
            return true;
        }
    }

    public static String checkEmail(TextField emailField) {
        String email = emailField.getText().trim();
        if (email.isEmpty()) {
            emailField.setStyle(STYLE_BAD);
            return null;
        }

        Pattern p = Pattern.compile("(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])");
        Matcher m = p.matcher(email);
        if (m.matches()) {
            emailField.setStyle(STYLE_OK);
            return email;
        } else {
            emailField.setStyle(STYLE_BAD);
            return null;
        }
    }

    public static String checkMobile(TextField mobileField) {
        String mobile = mobileField.getText().trim();
        if (mobile.isEmpty()) {
            mobileField.setStyle(STYLE_BAD);
            return null;
        }

        Pattern p = Pattern.compile("^(([(]?(\\d{2,4})[)]?)|(\\d{2,4})|([+1-9]+\\d{1,2}))?[-\\s]?(\\d{2,3})?[-\\s]?((\\d{7,8})|(\\d{3,4}[-\\s]\\d{3,4}))$");
        Matcher m = p.matcher(mobile);
        if (m.matches()) {
            mobileField.setStyle(STYLE_OK);
            return mobile;
        } else {
            mobileField.setStyle(STYLE_BAD);
            return null;
        }
    }
}
