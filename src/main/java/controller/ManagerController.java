package controller;

import entity.*;
import enum_types.OrderStatus;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.util.Callback;
import javafx.util.StringConverter;
import org.springframework.context.ApplicationContext;
import service.*;
import util.ApplicationContextFactory;
import util.InputDataChecker;
import util.StageFactory;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.sql.Date;

public class ManagerController {
    @FXML private Button logOutButton;

    // Orders table
    @FXML private TableView<Order> ordersTable;
          private ObservableList<Order> orders;
    @FXML private TableColumn<Order, Long> orderIdColumn;
    @FXML private TableColumn<Order, Date> orderDateColumn;
    @FXML private TableColumn<Order, BigDecimal> orderPriceColumn;
    // Order info
    @FXML private TextField managerField;
    @FXML private TextField orderNumberField;
    @FXML private TextField orderDateField;
    @FXML private ComboBox<OrderStatus> statusBox;
    private ObservableList<OrderStatus> statuses;
    @FXML private DatePicker deadlinePicker;
    @FXML private ComboBox<Customer> customerBox;
    // Total
    @FXML private Label amountLabel;
    @FXML private Label sumLabel;
    // Order buttons
    @FXML private Button addOrderButton;
    @FXML private Button applyAddingOrderButton;
    @FXML private Button cancelAddingOrderButton;
    @FXML private Button changeOrderButton;
    @FXML private Button applyChangingOrderButton;
    @FXML private Button cancelChangingOrderButton;
    @FXML private Button deleteOrderButton;
    @FXML private Button applyDeletingOrderButton;
    @FXML private Button cancelDeletingOrderButton;

    // Items table
    @FXML private TableView<Item> itemsTable;
          private ObservableList<Item> items;
    @FXML private TableColumn<Item, Integer> itemIdColumn;
    @FXML private TableColumn<Item, String> itemNameColumn;
    @FXML private TableColumn<Item, Integer> itemQuantityColumn;
    @FXML private TableColumn<Item, BigDecimal> itemPriceNoVATColumn;
    @FXML private TableColumn<Item, BigDecimal> itemSumNoVATColumn;
    @FXML private TableColumn<Item, BigDecimal> itemPriceVATColumn;
    @FXML private TableColumn<Item, BigDecimal> itemSumVATColumn;
    // Item buttons
    @FXML private Button addItemButton;
    @FXML private Button applyAddingItemButton;
    @FXML private Button cancelAddingItemButton;
    @FXML private Button changeItemButton;
    @FXML private Button applyChangingItemButton;
    @FXML private Button cancelChangingItemButton;
    @FXML private Button deleteItemButton;
    @FXML private Button applyDeletingItemButton;
    @FXML private Button cancelDeletingItemButton;

    @FXML private HBox newItemRow;
    @FXML private ComboBox<Product> productsNewItem;
          private ObservableList<Product> productsList;
    @FXML private Label numberNewItem;
    @FXML private TextField amountNewItem;
    @FXML private TextField priceNewItem;
    @FXML private Label sumNewItem;
    @FXML private Label PriceVATNewItem;
    @FXML private Label sumVATNewItem;

    // Displayed info
    private Order currentOrder;
    private Order beforeCurrentOrder;
    private Employee currentManager;
    private Customer currentCustomer;
    private Item currentItem;
    private Date currentDeadline;

    ApplicationContext context;

    // Services
    private OrderService orderService;
    private ItemService itemService;
    private ProductService productService;
    private CustomerService customerService;
    private UserService userService;

    // formats for dates & BigDecimal
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yy");
    private DecimalFormat decimalFormat = new DecimalFormat("#0.00");

    private Helper helper;

    public void initialize() {
        helper = new Helper();

        context = ApplicationContextFactory.getApplicationContext();
        orderService = context.getBean("orderService", OrderService.class);
        itemService = context.getBean("itemService", ItemService.class);
        productService = context.getBean("productService", ProductService.class);
        customerService = context.getBean("customerService", CustomerService.class);
        userService = context.getBean("userService", UserService.class);

        UserSession session = UserSession.readFromResource();
        if (session != null) currentManager = userService.read(session.getUserId()).getEmployee();
        else currentManager = userService.read(13L).getEmployee();

        // set columns in TableView
        orderIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        orderDateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        orderPriceColumn.setCellValueFactory(new PropertyValueFactory<>("summary"));

        orders = FXCollections.observableArrayList(orderService.findAll());
        ordersTable.setItems(orders);

//        itemIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        itemIdColumn.setCellValueFactory(p -> new ReadOnlyObjectWrapper<>(itemsTable.getItems().indexOf(p.getValue()) + 1));

        itemQuantityColumn.setCellValueFactory(new PropertyValueFactory<>("amount"));
        itemNameColumn.setCellValueFactory(new PropertyValueFactory<>("productName"));
        itemPriceNoVATColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        itemPriceVATColumn.setCellValueFactory(new PropertyValueFactory<>("priceVAT"));
        itemSumNoVATColumn.setCellValueFactory(new PropertyValueFactory<>("sumNoVAT"));
        itemSumVATColumn.setCellValueFactory(new PropertyValueFactory<>("sumVAT"));
        helper.setCellFactoryForBigDecimal();

        statuses = FXCollections.observableArrayList(OrderStatus.values());
        statusBox.setItems(statuses);

        customerBox.setItems(FXCollections.observableArrayList(customerService.findAll()));

        items = FXCollections.observableArrayList();

        deadlinePicker.setConverter(new StringConverter<LocalDate>() {
            @Override
            public String toString(LocalDate localDate) {
                if(localDate == null || localDate.isBefore(LocalDate.now())) return "";
                else return localDate.format(formatter);
            }

            @Override
            public LocalDate fromString(String dateString) {
                if(dateString == null || dateString.trim().isEmpty()) return null;
                else return LocalDate.parse(dateString, formatter);
            }
        });
        helper.addSelectListener();


        deadlinePicker.setDayCellFactory(datePicker -> new DateCell() {
            @Override
            public void updateItem(LocalDate item, boolean empty) {
                super.updateItem(item, empty);
                if (item.isBefore(LocalDate.now())) {
                    setDisable(true);
                }
            }
        });
    }

    @FXML
    public void addOrder() {
        beforeCurrentOrder = currentOrder;
        currentOrder = new Order();
        helper.disableForActionButNot(true, addOrderButton, applyAddingOrderButton, cancelAddingOrderButton,
                itemsTable);

        helper.disableOrderInfo(false);
        helper.selectCurrentOrder();

        items.clear();

        managerField.setText(currentManager.shortInfo());
        statusBox.getSelectionModel().select(0);
        orderNumberField.setText("will be generated");
        orderDateField.setText(LocalDate.now().format(formatter));
    }

    @FXML
    public void cancelAddingOrder() {
        currentOrder = beforeCurrentOrder;
        helper.disableForActionButNot(false, addOrderButton, applyAddingOrderButton, cancelAddingOrderButton,
                itemsTable);

        helper.disableOrderInfo(true);

        helper.selectCurrentOrder();
        helper.selectCurrentItem();

        itemsTable.setStyle("-fx-border-color: inherit");
    }

    @FXML
    public void applyAddingOrder() {
        helper.checkFields();

        if (items.isEmpty()) {
            itemsTable.setStyle("-fx-border-color: red");
            return;
        } else {
            itemsTable.setStyle("-fx-border-color: inherit");
        }

        if (currentCustomer != null && currentDeadline != null) {
            currentOrder.setManager(currentManager);
            currentOrder.setCustomer(currentCustomer);
            currentOrder.setDeadline(currentDeadline);
            currentOrder.setStatus(OrderStatus.OPENED);
            for (Item item : items) {
                item.setOrder(currentOrder);
                itemService.create(item);
            }
            orderService.create(currentOrder);
            ordersTable.getItems().add(currentOrder);

            helper.disableForActionButNot(false, addOrderButton, applyAddingOrderButton, cancelAddingOrderButton,
                    itemsTable);
            helper.disableOrderInfo(true);

            ordersTable.getSelectionModel().select(currentOrder);
            ordersTable.scrollTo(currentOrder);
            currentOrder = null;
        }
    }

    @FXML
    public void changeOrder() {

    }

    @FXML
    public void applyChangingOrder() {

    }

    @FXML
    public void cancelChangingOrder() {

    }

    @FXML
    public void deleteOrder() {

    }

    @FXML
    public void applyDeletingOrder() {

    }

    @FXML
    public void cancelDeletingOrder() {

    }

    @FXML
    public void logOut() {
        StageFactory.backToLogInWindow();
    }

    @FXML
    public void addItem() {
        helper.disableForActionButNot(true,
                addItemButton, applyAddingItemButton, cancelAddingItemButton);
        helper.disableNewItemRow(false);

        productsList = FXCollections.observableArrayList(productService.findAll());
        productsNewItem.setItems(productsList);

        productsNewItem.getSelectionModel().selectedItemProperty()
                .addListener(((observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        priceNewItem.setText("" + newValue.getPrice());
                    }
                }));
    }


    @FXML
    public void applyAddingItem() {
        // todo

        Product product = InputDataChecker.checkEnum(productsNewItem);
        BigDecimal price = InputDataChecker.checkBigDecimal(priceNewItem);
        Integer amount = InputDataChecker.checkInteger(amountNewItem);

        if (product != null && price != null && amount != null) {
            Item item = context.getBean(Item.class, product, amount, currentOrder);
            if (!price.equals(product.getPrice())) {
                product.setPrice(price);
                productService.update(product);
            }
            if (!isCurrentOrderNew()) {
                itemService.create(item);
            }
//            items.add(item);
            itemsTable.getItems().add(item);
            itemsTable.refresh();

            helper.disableNewItemRow(true);
            helper.disableForActionButNot(false,
                    addItemButton, applyAddingItemButton, cancelAddingItemButton);
            helper.selectCurrentOrder();
        }


    }

    @FXML
    public void cancelAddingItem() {
        helper.disableNewItemRow(true);
        helper.disableForActionButNot(false,
                addItemButton, applyAddingItemButton, cancelAddingItemButton);
        helper.selectCurrentOrder();
    }

    @FXML
    public void changeItem() {
        helper.disableForActionButNot(true,
                changeItemButton, applyChangingItemButton, cancelChangingItemButton);
    }

    @FXML
    public void applyChangingItem() {
        // todo

        helper.disableForActionButNot(false,
                changeItemButton, applyChangingItemButton, cancelChangingItemButton);
        helper.selectCurrentOrder();
    }

    @FXML
    public void cancelChangingItem() {
        helper.disableForActionButNot(false,
                changeItemButton, applyChangingItemButton, cancelChangingItemButton);
        helper.selectCurrentOrder();
    }

    @FXML
    public void deleteItem() {
        helper.disableForActionButNot(true, deleteItemButton, applyDeletingItemButton, cancelDeletingItemButton);
    }

    @FXML
    public void applyDeletingItem() {
        Item itemToDelete = currentItem;
        itemService.delete(itemToDelete);
        System.out.println(itemToDelete);
        currentOrder.getItems().remove(itemToDelete);
        System.out.println(itemToDelete);
        helper.disableForActionButNot(false, deleteItemButton, applyDeletingItemButton, cancelDeletingItemButton);
        helper.selectCurrentOrder();
    }

    @FXML
    public void cancelDeletingItem() {
        helper.disableForActionButNot(false, deleteItemButton, applyDeletingItemButton, cancelDeletingItemButton);
        helper.selectCurrentOrder();
    }

    @FXML
    public void newCustomer() {
        Customer customer = StageFactory.genericModal("/view/modal/new_customer.fxml", "New customer", null);
        if (customer != null) {
            customerBox.getItems().add(customer);
            if (!customerBox.isDisable()) {
                customerBox.getSelectionModel().select(customer);
            }
        }
    }

    private class Helper {
        private void addSelectListener() {
            ordersTable.getSelectionModel().selectedItemProperty()
                    .addListener((observable, oldValue, newValue) -> {
                        currentOrder = newValue;
                        selectCurrentOrder();
                    });
            itemsTable.getSelectionModel().selectedItemProperty()
                    .addListener((observable, oldValue, newValue) -> {
                        currentItem = newValue;
                        selectCurrentItem();
                    });
        }

        private void selectCurrentItem() {
            changeItemButton.setDisable(currentItem == null);
            deleteItemButton.setDisable(currentItem == null);
        }

        private void selectCurrentOrder() {
            fillInfoWith(currentOrder);
            addItemButton.setDisable(currentOrder == null);
            changeOrderButton.setDisable(!isCurrentOrderNew());
            deleteOrderButton.setDisable(!isCurrentOrderNew());
            selectCurrentItem();
        }

        /*private void selectOrderAndItem() {
            selectCurrentOrder(currentOrder);
            selectCurrentItem(currentItem);
        }*/

        // Override method for updating table column with specified format
        private void setCellFactoryForBigDecimal() {
            Callback<TableColumn<Item, BigDecimal>, TableCell<Item, BigDecimal>> callback = param -> new TableCell<Item, BigDecimal>() {
                @Override
                protected void updateItem(BigDecimal item, boolean empty) {
                    super.updateItem(item, empty);
                    if(empty || item == null) {
                        setText("");
                    } else {
                        setText(decimalFormat.format(item));
                    }
                }
            };

            itemPriceNoVATColumn.setCellFactory(callback);
            itemPriceVATColumn.setCellFactory(callback);
            itemSumNoVATColumn.setCellFactory(callback);
            itemSumVATColumn.setCellFactory(callback);
        }

        private void fillInfoWith(Order currentOrder) {
            // second check in case if order is not saved in DB (in other words if it's new)
            if (currentOrder != null && currentOrder.getId() != null) {
                items.setAll(currentOrder.getItems());
                itemsTable.setItems(items);

                fillOrderInfo(currentOrder);

                addItemButton.setDisable(false);

                Integer amount = 0;
                BigDecimal sum = BigDecimal.ZERO;
                for (Item item : items) {
                    amount += item.getAmount();
                    sum = sum.add(item.getSumVAT());
                }
                amountLabel.setText("" + amount);
                sumLabel.setText(decimalFormat.format(sum));
            } else {
                items.clear();
                clearOrderInfo();

                addItemButton.setDisable(true);
            }
        }

        private void fillOrderInfo(Order currentOrder) {
            managerField.setText(currentOrder.getManager().shortInfo());
            orderNumberField.setText(currentOrder.getId().toString());
            orderDateField.setText(currentOrder.getDate().toLocalDate().format(formatter));
            deadlinePicker.setValue(currentOrder.getDeadline().toLocalDate());
            statusBox.setValue(currentOrder.getStatus());
            customerBox.setValue(currentOrder.getCustomer());
        }

        private void clearOrderInfo() {
            managerField.setText("");
            orderNumberField.setText("");
            orderDateField.setText("");
            deadlinePicker.setValue(null);
            statusBox.setValue(null);
            customerBox.setValue(null);

            amountLabel.setText("");
            sumLabel.setText("");
        }

        private void disableOrderInfo(Boolean bool) {
            // editable fields
            customerBox.setDisable(bool);
            customerBox.setStyle("-fx-border-color: transparent");
            deadlinePicker.setDisable(bool);
            deadlinePicker.setStyle("-fx-border-color: transparent");

            if (bool) fillInfoWith(currentOrder);
            else fillInfoWith(null);
        }

        private void checkFields() {
            currentCustomer = InputDataChecker.checkEnum(customerBox);
            currentDeadline = InputDataChecker.checkDate(deadlinePicker);
        }

        private void disableForActionButNot(boolean bool, Button actionButton, Button applyButton, Button cancelButton,
                                                      Node... nodes) {
            disableAllButNot(bool);

            actionButton.setVisible(!bool);
            actionButton.setDisable(bool);
            applyButton.setVisible(bool);
            applyButton.setDisable(!bool);
            cancelButton.setVisible(bool);
            cancelButton.setDisable(!bool);

            if (nodes != null) {
                for (Node node : nodes) {
                    node.setDisable(false);
                }
            }

        }

        // disables all but doesn't disable nodes independently from bool
        private void disableAllButNot(Boolean bool) {
            ordersTable.setDisable(bool);
            itemsTable.setDisable(bool);

            disableIfEnabled(bool, addOrderButton);
            disableIfEnabled(bool, applyAddingOrderButton);
            disableIfEnabled(bool, cancelAddingOrderButton);
            disableIfEnabled(bool, deleteOrderButton);
            disableIfEnabled(bool, applyDeletingOrderButton);
            disableIfEnabled(bool, cancelDeletingOrderButton);
            disableIfEnabled(bool, changeOrderButton);
            disableIfEnabled(bool, applyChangingOrderButton);
            disableIfEnabled(bool, cancelChangingOrderButton);


            disableIfEnabled(bool, addItemButton);
            disableIfEnabled(bool, changeItemButton);
            disableIfEnabled(bool, deleteItemButton);
        }

        private void disableIfEnabled(Boolean bool, Node node) {
            if (bool && !node.isDisabled() || !bool && node.isDisabled()) node.setDisable(bool);
        }

        private void disableNewItemRow(Boolean bool) {
            newItemRow.setDisable(bool);
        }
    }

    public Boolean isCurrentOrderNew() {
        return currentOrder != null && currentOrder.getId() == null;
    }
}
