package controller;

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
import javafx.util.Callback;
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

public class CashierController implements MainController {

    @FXML private TableView<Order> ordersTable;
    @FXML private TableColumn<Order, Long> ordersIdColumn;
    @FXML private TableColumn<Order, Date> ordersDateColumn;
    @FXML private TableColumn<Order, BigDecimal> ordersPriceColumn;

    @FXML private TableView<Item> itemsTable;
    @FXML private TableColumn<Item, Integer> itemsIdColumn;
    @FXML private TableColumn<Item, String> itemsNameColumn;
    @FXML private TableColumn<Item, Integer> itemsQuantityColumn;
    @FXML private TableColumn<Item, BigDecimal> itemsPriceNoVATColumn;
    @FXML private TableColumn<Item, BigDecimal> itemsSumNoVATColumn;
    @FXML private TableColumn<Item, BigDecimal> itemsPriceVATColumn;
    @FXML private TableColumn<Item, BigDecimal> itemsSumVATColumn;

    @FXML private TextField managerField;
    @FXML private TextField deadlineField;
    @FXML private TextField customerField;

    @FXML private ComboBox<OrderStatus> statusBox;

    @FXML private Label amountLabel;
    @FXML private Label summaryLabel;

    @FXML private Button saveButton;
    @FXML private Button logOutButton;

    private ObservableList<Order> orders = FXCollections.observableArrayList();
    private ObservableList<Item> items;
    private ObservableList<OrderStatus> statuses;

    private Order currentOrder;
    private Item currentItem;

    private OrderService orderService;
    private ItemService itemService;

    private Helper helper;
    private UserSession session;
    private ApplicationContext context;

    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yy");
    private DecimalFormat decimalFormat = new DecimalFormat("#0.00");

    public void initialize() {
        context = ApplicationContextFactory.getApplicationContext();

        helper = new Helper();

        saveButton.setDisable(true);
        statusBox.setDisable(true);

        orderService = (OrderService) context.getBean("orderService");
        itemService = (ItemService) context.getBean("itemService");

        helper.refreshTable();

        itemsIdColumn.setCellValueFactory(p -> new ReadOnlyObjectWrapper(itemsTable.getItems().indexOf(p.getValue()) + 1 + ""));
        itemsQuantityColumn.setCellValueFactory(new PropertyValueFactory<>("amount"));
        itemsNameColumn.setCellValueFactory(new PropertyValueFactory<>("productName"));
        itemsPriceNoVATColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        itemsPriceVATColumn.setCellValueFactory(new PropertyValueFactory<>("priceVAT"));
        itemsSumNoVATColumn.setCellValueFactory(new PropertyValueFactory<>("sumNoVAT"));
        itemsSumVATColumn.setCellValueFactory(new PropertyValueFactory<>("sumVAT"));
        items = FXCollections.observableArrayList();

        statuses = FXCollections.observableArrayList(OrderStatus.values());
        ArrayList<OrderStatus> orderStatuses = new ArrayList<>();
        orderStatuses.add(OrderStatus.PAID);
        statuses = FXCollections.observableArrayList(orderStatuses);
        statusBox.setItems(statuses);

        statusBox.getSelectionModel().selectedItemProperty().addListener(
                (v, oldValue, newValue) -> {
                    if (currentOrder != null) {
                        if (!currentOrder.getStatus().equals(newValue)) {
                            saveButton.setDisable(false);
                        }
                    }
                }
        );

        helper.addSelectListener();

    }

    @FXML
    public void saveButtonOnAction() {
        currentOrder.setStatus(statusBox.getValue());
        orderService.update(currentOrder);
        saveButton.setDisable(true);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText("Successfully saved!");
        alert.setContentText("Order status has been changed to - " + statusBox.getValue().toString().toLowerCase() + ".");
        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.getStylesheets().add(
                getClass().getResource("/view/styles/light_theme.css").toExternalForm());
        dialogPane.getStyleClass().add("Alert");
        helper.refreshTable();
        alert.showAndWait();
    }

    @FXML
    public void logOutButtonOnAction() {
        StageFactory.backToLogInWindow();
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
                        statusBox.setDisable(false);
                    });
        }

        private boolean isCashierStatus(Order order) {
            return order.getStatus().equals(OrderStatus.REVIEWED) || order.getStatus().equals(OrderStatus.FORMED) || order.getStatus().equals(OrderStatus.OPENED);
        }

        private void refreshTable() {
            orders.clear();
            if (orders.isEmpty()){
                clearFields();
            }
            ordersIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
            ordersDateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
            ordersPriceColumn.setCellValueFactory(new PropertyValueFactory<>("summary"));
            ObservableList<Order> result = FXCollections.observableArrayList(orderService.findAll());
            for (Order order : result) {
                if (helper.isCashierStatus(order)) {
                    orders.add(order);
                }
            }
            ordersTable.setItems(orders);
        }

        private void clearFields() {
            managerField.setText("");
            customerField.setText("");
            deadlineField.setText("");
            statusBox.setDisable(true);
            statusBox.getSelectionModel().clearSelection();
            currentOrder = null;
        }

        private void fillInfoWith(Order currentOrder) {
            if (currentOrder != null) {

                items.setAll(currentOrder.getItems());
                itemsTable.setItems(items);

                managerField.setText(currentOrder.getManager().shortInfo());
                deadlineField.setText(currentOrder.getDeadline().toLocalDate().format(formatter));
                customerField.setText(currentOrder.getCustomer().toString());
                statusBox.setValue(currentOrder.getStatus());

                Integer amount = 0;
                BigDecimal sum = BigDecimal.ZERO;
                for (Item item : items) {
                    amount += item.getAmount();
                    sum = sum.add(item.getSumVAT());
                }

                amountLabel.setText("" + amount);
                summaryLabel.setText(decimalFormat.format(sum));

            } else {
                items.clear();
            }
        }

    }

}
