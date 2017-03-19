package controller;

import entity.Customer;
import entity.Item;
import entity.Order;
import entity.UserSession;
import enum_types.OrderStatus;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import org.springframework.context.ApplicationContext;
import service.ItemService;
import service.OrderService;
import util.ApplicationContextFactory;
import util.StageFactory;

import java.math.BigDecimal;
import java.sql.Date;
import java.text.DecimalFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class StorageController implements MainController {
    @FXML private TableView<Order> ordersTable;
    @FXML private TableColumn<Order, Long> ordersIdColumn;
    @FXML private TableColumn<Order, Date> ordersDateColumn;
    @FXML private TableColumn<Order, Date> ordersDeadlineColumn;
    @FXML private TableColumn<Order, BigDecimal> ordersPriceColumn;
    @FXML private TableColumn<Order, Customer> ordersCustomerColumn;

    @FXML private TableView<Item> itemsTable;
    @FXML private TableColumn<Item, Integer> itemsIdColumn;
    @FXML private TableColumn<Item, String> itemsNameColumn;
    @FXML private TableColumn<Item, Integer> itemsQuantityColumn;
    @FXML private TableColumn<Item, BigDecimal> itemsPriceNoVATColumn;
    @FXML private TableColumn<Item, Integer> storageColumn;
    @FXML private TableColumn<Item, String> inStockColumn;

    @FXML private Button saveButton;
    @FXML private Button logOutButton;
    @FXML private Button storageButton;
    @FXML private Button reviewButton;
    @FXML private Button formedButton;

    @FXML private HBox tables;
    @FXML private HBox statusButtons;

    private ObservableList<Order> orders = FXCollections.observableArrayList();
    private ObservableList<Item> items;
    private ObservableList<OrderStatus> statuses;

    private Order currentOrder;

    private OrderService orderService;
    private ItemService itemService;

    private Helper helper;
    private UserSession session;

    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yy");
    private DecimalFormat decimalFormat = new DecimalFormat("#0.00");
    private ApplicationContext context;

    public void initialize() {
        context = ApplicationContextFactory.getApplicationContext();

        orderService = context.getBean(OrderService.class);
        itemService = context.getBean(ItemService.class);

        helper = new Helper();

        helper.refreshTable();

        itemsIdColumn.setCellValueFactory(p -> new ReadOnlyObjectWrapper(itemsTable.getItems().indexOf(p.getValue()) + 1 + ""));
        itemsQuantityColumn.setCellValueFactory(new PropertyValueFactory<>("amount"));
        itemsNameColumn.setCellValueFactory(new PropertyValueFactory<>("productName"));
        itemsPriceNoVATColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        storageColumn.setCellValueFactory(new PropertyValueFactory<>("availability"));
        inStockColumn.setCellValueFactory(new PropertyValueFactory<>("inStock"));

        items = FXCollections.observableArrayList();

        helper.addSelectListener();
    }

    @FXML
    private void statusButtonOnAction() {
        if (currentOrder != null) {
            tables.setDisable(true);
            tables.setVisible(false);
            statusButtons.setDisable(false);
            statusButtons.setVisible(true);
        }
    }

    @FXML
    public void saveButtonOnAction() {
        orderService.update(currentOrder);
        saveButton.setDisable(true);
        saveButton.setVisible(false);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText("Successfully saved!");
        alert.setContentText("Order status has been changed to - " + currentOrder.getStatus().toString().toLowerCase() + ".");
        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.getStylesheets().add(
                getClass().getResource("/view/styles/light_theme.css").toExternalForm());
        dialogPane.getStyleClass().add("Alert");
        helper.refreshTable();
        alert.showAndWait();
        tables.setDisable(false);
        tables.setVisible(true);
        statusButtons.setDisable(true);
        statusButtons.setVisible(false);
    }

    public void storageButtonOnAction() {
        StageFactory.loadWindow("/view/storage_items_panel.fxml", "Storage", -1L);
    }

    @FXML
    public void logOutButtonOnAction() {
        StageFactory.backToLogInWindow();
    }

    @FXML
    public void formedButtonOnAction() {
        currentOrder.setStatus(OrderStatus.FORMED);
        saveButton.setDisable(false);
        saveButton.setVisible(true);
    }

    @FXML
    public void reviewButtonOnAction() {
        currentOrder.setStatus(OrderStatus.UNDER_REVIEW);
        saveButton.setDisable(false);
        saveButton.setVisible(true);
    }


    @Override
    public void setUserSession(UserSession session) {
        this.session = session;
    }

    private class Helper {

        private void addSelectListener() {
            ordersTable.getSelectionModel().selectedItemProperty()
                    .addListener((observable, oldValue, newValue) -> {
                        currentOrder = newValue;
                        fillInfoWith(currentOrder);
                        System.out.println(currentOrder);
                    });
        }

        private boolean isKeeperStatus(Order order) {
            return order.getStatus().equals(OrderStatus.PAID);
        }

        private void refreshTable() {
            orders.clear();
            ordersIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
            ordersDateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
            ordersPriceColumn.setCellValueFactory(new PropertyValueFactory<>("summary"));
            ordersDeadlineColumn.setCellValueFactory(new PropertyValueFactory<>("deadline"));
            ordersCustomerColumn.setCellValueFactory(new PropertyValueFactory<>("customer"));
            ObservableList<Order> result = FXCollections.observableArrayList(orderService.findAll());
            for (Order order : result) {
                if (helper.isKeeperStatus(order)) {
                    orders.add(order);
                }
            }
            ordersTable.setItems(orders);
        }

        private void fillInfoWith(Order currentOrder) {
            if (currentOrder != null) {
                items.setAll(currentOrder.getItems());
                itemsTable.setItems(items);
            } else {
                items.clear();
            }
        }

    }

}
