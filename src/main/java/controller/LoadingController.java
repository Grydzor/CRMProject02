package controller;

import entity.User;
import entity.UserSession;
import service.UserService;
import util.ApplicationContextFactory;
import util.LoadingService;
import util.StageFactory;

/**
 * Created by eriol4ik on 03.03.2017.
 */
public class LoadingController {
    public void initialize() {
        LoadingService service = new LoadingService();

        service.setOnSucceeded((event) -> {
            UserSession session = service.getValue();
            if (session == null) {
                StageFactory.genericWindow("/view/login_panel.fxml", "CRM", -1L);
            } else {
                User user = ApplicationContextFactory.getApplicationContext()
                        .getBean(UserService.class).read(session.getUserId());
                switch (user.getEmployee().getPosition()) {
                    case ADMIN:
                        StageFactory.genericWindow("/view/admin_panel.fxml", "Administration", session.getUserId());
                        break;
                    case MANAGER:
                        StageFactory.genericWindow("/view/manager_panel.fxml", "Management", session.getUserId());
                        break;
                    case CASHIER:
                        StageFactory.genericWindow("/view/cashier_panel.fxml", "Cashier", session.getUserId());
                        break;
                    case STOREKEEPER:
                        StageFactory.genericWindow("/view/storage_panel_two.fxml", "Storage", session.getUserId());
                        break;
                }
            }
        });

        service.start();
    }
}
