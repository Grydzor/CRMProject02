package controller;

import entity.Item;
import entity.Order;
import enum_types.OrderStatus;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import service.ItemService;
import service.ItemServiceImpl;
import service.OrderService;
import service.OrderServiceImpl;
import util.StageFactory;

import java.math.BigDecimal;
import java.sql.Date;
import java.text.DecimalFormat;
import java.time.format.DateTimeFormatter;

/**
 * Created by Никита on 21.02.2017.
 */

public class StorageController {


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

    @FXML private TextField managerField;
    @FXML private TextField deadlineField;
    @FXML private TextField customerField;

    @FXML private ComboBox<OrderStatus> statusBox;

    @FXML private Button saveButton;
    @FXML private Button logOutButton;

    private ObservableList<Order> orders;
    private ObservableList<Item> items;
    private ObservableList<OrderStatus> statuses;

    private Order currentOrder;

    private OrderService orderService;
    private ItemService itemService;

    private Helper helper;

    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yy");
    private DecimalFormat decimalFormat = new DecimalFormat("#0.00");

    public void initialize() {

        helper = new Helper();

        saveButton.setDisable(true);
        statusBox.setDisable(true);

        orderService = OrderServiceImpl.getInstance();
        itemService = ItemServiceImpl.getInstance();

        ordersIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        ordersDateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        ordersPriceColumn.setCellValueFactory(new PropertyValueFactory<>("summary"));
        orders = FXCollections.observableArrayList(orderService.findAll());
        ordersTable.setItems(orders);

        itemsIdColumn.setCellValueFactory(p -> new ReadOnlyObjectWrapper(itemsTable.getItems().indexOf(p.getValue()) + 1 + ""));
        itemsQuantityColumn.setCellValueFactory(new PropertyValueFactory<>("amount"));
        itemsNameColumn.setCellValueFactory(new PropertyValueFactory<>("productName"));
        itemsPriceNoVATColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        //itemsPriceVATColumn.setCellValueFactory(new PropertyValueFactory<>("priceVAT"));
        //itemsSumNoVATColumn.setCellValueFactory(new PropertyValueFactory<>("sumNoVAT"));
        //helper.setCellFactoryForBigDecimal();

        items = FXCollections.observableArrayList();

        statuses = FXCollections.observableArrayList(OrderStatus.values());
        statusBox.setItems(statuses);

        statusBox.getSelectionModel().selectedItemProperty().addListener(
                (v, oldValue, newValue) -> {
                    if (!currentOrder.getStatus().equals(newValue)) {
                        saveButton.setDisable(false);
                    }
                }
        );

        helper.addSelectListener();

    }

    @FXML
    public void saveButtonOnAction() {

    }

    @FXML
    public void logOutButtonOnAction() {
        StageFactory.backToLogInWindow();
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

        private void setCellFactoryForBigDecimal() {

            Callback callback = param -> new TableCell<Item, BigDecimal>() {
                @Override
                protected void updateItem(BigDecimal item, boolean empty) {
                    super.updateItem(item, empty);
                    if(empty || item == null) {
                        setText("");
                    } else {
                        //setText(decimalFormat.format(item));
                    }
                }
            };

            //itemsPriceNoVATColumn.setCellFactory(callback);
            //itemsPriceVATColumn.setCellFactory(callback);
            //itemsSumNoVATColumn.setCellFactory(callback);
            //itemsSumVATColumn.setCellFactory(callback);

        }

        private void fillInfoWith(Order currentOrder) {
            if (currentOrder != null) {

                items.setAll(currentOrder.getItems());
                itemsTable.setItems(items);

                managerField.setText(currentOrder.getManager().shortInfo());
                deadlineField.setText(currentOrder.getDeadline().toLocalDate().format(formatter));
                customerField.setText(currentOrder.getCustomer().toString());
                statusBox.setValue(currentOrder.getStatus());

            } else {
                items.clear();
            }
        }

    }

}
