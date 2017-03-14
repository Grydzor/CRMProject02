package controller;

import entity.User;
import entity.UserSession;
import service.UserService;
import util.ApplicationContextFactory;
import util.LoadingService;
import util.StageFactory;

public class LoadingController {
    public void initialize() {
        LoadingService service = new LoadingService();

        service.setOnSucceeded((event) -> {
            UserSession session = service.getValue();
            if (session == null) {
                StageFactory.loadWindow("/view/login_panel.fxml", "CRM", -1L);
            } else {
                User user = ApplicationContextFactory.getApplicationContext()
                        .getBean(UserService.class).read(session.getUserId());
                switch (user.getEmployee().getPosition()) {
                    case ADMIN:
                        StageFactory.loadWindow("/view/admin_panel.fxml", "Administration", session.getUserId());
                        break;
                    case MANAGER:
                        StageFactory.loadWindow("/view/manager_panel.fxml", "Management", session.getUserId());
                        break;
                    case CASHIER:
                        StageFactory.loadWindow("/view/cashier_panel.fxml", "Cashier", session.getUserId());
                        break;
                    case STOREKEEPER:
                        StageFactory.loadWindow("/view/storage_panel_two.fxml", "Storage", session.getUserId());
                        break;
                }
            }
        });

        service.setOnFailed((event -> {
            System.out.println("Cannot connect to db");
            System.exit(0);
        }));

        service.start();
    }
}
