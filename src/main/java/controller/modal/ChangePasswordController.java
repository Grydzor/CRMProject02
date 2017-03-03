package controller.modal;

import entity.User;
import entity.UserSession;
import javafx.beans.InvalidationListener;
import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import service.UserService;
import util.ApplicationContextFactory;
import util.EmailSender;
import util.StageFactory;

/**
 * Created by eriol4ik on 03.03.2017.
 */
public class ChangePasswordController implements ModalController<UserSession, String> {
    @FXML private PasswordField oldPasswordField;
    @FXML private Label wrongPassLabel;
    @FXML private PasswordField newPasswordField;
    @FXML private Label newPassErrorLabel;
    @FXML private PasswordField confirmNewPasswordField;
    @FXML private Label confirmNewPassErrorLabel;

    @FXML private Button changeButton;
    @FXML private Button cancelButton;

    private UserSession currentSession;
    private String result;

    public void initialize() {
        oldPasswordField.textProperty()
                .addListener(getListener());
        newPasswordField.textProperty()
                .addListener(getListener());
        confirmNewPasswordField.textProperty()
                .addListener(getListener());
    }

    private InvalidationListener getListener() {
        return (observable) -> {
            String oldPass = oldPasswordField.getText();
            String newPass = newPasswordField.getText();
            String confirmNewPass = confirmNewPasswordField.getText();

            // check textfields for emptiness
            if (oldPass.isEmpty() ||
                    newPass.isEmpty()) {
                changeButton.setDisable(true);
                return;
            } else {
                changeButton.setDisable(false);
            }

            // checks old & new pass for identity
            if (oldPass.equals(newPass)) {
                newPassErrorLabel.setVisible(true);
                confirmNewPassErrorLabel.setVisible(false);
                changeButton.setDisable(true);
                return;
            } else {
                newPassErrorLabel.setVisible(false);
                changeButton.setDisable(false);
            }

            if (confirmNewPass.isEmpty()) {
                changeButton.setDisable(true);
                return;
            } else {
                changeButton.setDisable(false);
            }

            // checks new & conf. new pass for identity
            confirmNewPassErrorLabel.setVisible(newPass.equals(confirmNewPass));
            changeButton.setDisable(!newPass.equals(confirmNewPass));
        };
    }

    @FXML
    public void change() {
        UserService service = ApplicationContextFactory.getApplicationContext().getBean(UserService.class);
        User user = service.read(currentSession.getUserId());
        if (user.getPassword().equals(oldPasswordField.getText())) {
            user.setPassword(newPasswordField.getText());
            if (EmailSender.newPass(user)) {
                service.update(user);
                result = "success";
                StageFactory.closeModal();
            }
        }
    }

    @FXML
    public void cancel() {
        result = "";
        StageFactory.closeModal();
    }

    @Override
    public void setParameter(UserSession parameter) {
        this.currentSession = parameter;
    }

    @Override
    public String getResult() {
        return result;
    }
}
