package util;

import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.sql.Date;
import java.time.LocalDate;

/* Класс принимает поля ввода (текста и чисел - TextField,
   даты - DatePicker) и:
*       - если поле пустое (или содержит только пробелы),
*       - если числа не парсятся (Long, Double),
*  то возвращается null и граница поля становится красной.
*
*  В других случаях, если информация введена правильно,
*  возвращается требуемое значение этого поля и граница
*  поля становится обычной.
*
* */
public class InputDataChecker {
    public static Date checkDate(DatePicker datePicker) {
        LocalDate localDate = datePicker.getValue();
        if (localDate == null) {
            datePicker.setStyle("-fx-border-color: red");
            return null;
        }

        datePicker.setStyle("-fx-border-color: inherit");
        return Date.valueOf(localDate);
    }

    public static String checkString(TextField textField) {
        String text = textField.getText().trim();
        if (text.isEmpty()) {
            textField.setStyle("-fx-border-color: red");
            return null;
        }

        textField.setStyle("-fx-border-color: inherit");
        return text;
    }

    public static Double checkDouble(TextField textField) {
        String text = textField.getText().trim();
        if (text.isEmpty()) {
            textField.setStyle("-fx-border-color: red");
            return null;
        }

        Double num;
        try {
            num = Double.parseDouble(text);
        } catch (NumberFormatException nfe) {
            textField.setStyle("-fx-border-color: red");
            return null;
        }

        textField.setStyle("-fx-border-color: inherit");
        return num;
    }

    public static Long checkLong(TextField textField) {
        String text = textField.getText().trim();
        if (text.isEmpty()) {
            textField.setStyle("-fx-border-color: red");
            return null;
        }

        Long num;
        try {
            num = Long.parseLong(text);
        } catch (NumberFormatException nfe) {
            textField.setStyle("-fx-border-color: red");
            return null;
        }

        textField.setStyle("-fx-border-color: inherit");
        return num;
    }
}
