package util;

import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.math.BigDecimal;
import java.sql.Date;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.time.LocalDate;

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
    private static final String STYLE_OK = "-fx-border-color: transparent;";
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
}
