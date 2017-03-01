package controller;

import controller.modal.ModalController;
import entity.*;
import enum_types.OrderStatus;
import javafx.beans.InvalidationListener;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.util.StringConverter;
import org.springframework.context.ApplicationContext;
import service.*;
import util.ApplicationContextFactory;
import util.InputDataChecker;
import util.StageFactory;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.sql.Date;

public class ManagerController implements MainController {
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
    @FXML private TableColumn<Item, String> itemIdColumn;
    @FXML private TableColumn<Item, String> itemNameColumn;
    @FXML private TableColumn<Item, String> itemQuantityColumn;
    @FXML private TableColumn<Item, String> itemPriceNoVATColumn;
    @FXML private TableColumn<Item, String> itemSumNoVATColumn;
    @FXML private TableColumn<Item, String> itemPriceVATColumn;
    @FXML private TableColumn<Item, String> itemSumVATColumn;
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
    // newItemRow
    @FXML private HBox newItemRow;
    @FXML private ComboBox<Product> productsNewItem;
          private ObservableList<Product> productsList;
    @FXML private Label numberNewItem;
    @FXML private TextField amountNewItem;
    @FXML private TextField priceNewItem;
    @FXML private Label sumNewItem;
    @FXML private Label priceVATNewItem;
    @FXML private Label sumVATNewItem;

          private Integer number;
          private Integer amount;
          private BigDecimal price;
          private BigDecimal sum;
          private BigDecimal priceVAT;
          private BigDecimal sumVAT;

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
    private UserSessionService sessionService;

    // formats for dates & BigDecimal
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yy");
    private transient DecimalFormat decimalFormat = new DecimalFormat("#0.00");

    private UserSession userSession;

    private Helper helper;

    public void initialize() {
        helper = new Helper();

        helper.initServices();

        UserSession session = sessionService.restoreSession();
        if (session != null) currentManager = userService.read(session.getUserId()).getEmployee();
        else currentManager = userService.read(13L).getEmployee();

        // set columns in TableView
        orderIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        orderDateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        orderPriceColumn.setCellValueFactory(new PropertyValueFactory<>("summary"));

        orders = FXCollections.observableArrayList(orderService.findAllFor(currentManager));
        ordersTable.setItems(orders);

        itemIdColumn.setCellValueFactory(p -> new ReadOnlyObjectWrapper<>(itemsTable.getItems().indexOf(p.getValue()) + 1 + "."));
        itemQuantityColumn.setCellValueFactory(new PropertyValueFactory<>("amount"));
        itemNameColumn.setCellValueFactory(new PropertyValueFactory<>("productName"));
        itemPriceNoVATColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        itemPriceVATColumn.setCellValueFactory(new PropertyValueFactory<>("priceVAT"));
        itemSumNoVATColumn.setCellValueFactory(new PropertyValueFactory<>("sum"));
        itemSumVATColumn.setCellValueFactory(new PropertyValueFactory<>("sumVAT"));
//        helper.setCellFactoryForBigDecimal();

        statuses = FXCollections.observableArrayList(OrderStatus.values());
        statusBox.setItems(statuses);

        customerBox.setItems(FXCollections.observableArrayList(customerService.findAll()));

        items = FXCollections.observableArrayList();
        itemsTable.setItems(items);

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

        helper.addInvalidationListenerForNewItemRow();
    }

    @FXML
    public void addOrder() {
        beforeCurrentOrder = currentOrder;
        currentOrder = new Order();

        currentOrder.setStatus(OrderStatus.OPENED);
        currentOrder.setDate(Date.valueOf(LocalDate.now()));
        currentOrder.setManager(currentManager);

        items.setAll(currentOrder.getItems());
        helper.disableForActionButNot(true, addOrderButton, applyAddingOrderButton, cancelAddingOrderButton,
                itemsTable);

        helper.disableOrderInfo(false);
        helper.selectCurrentOrder();

//        items.clear();

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
            currentOrder.setCustomer(currentCustomer);
            currentOrder.setDeadline(currentDeadline);
//            currentOrder.updateSummary();
            orderService.create(currentOrder);
            for (Item item : items) {
                // set here because currentOrder got its id at creating (2 rows above)
//                item.setOrder(currentOrder);
                itemService.create(item);
            }
            ordersTable.getItems().add(currentOrder);

            helper.disableForActionButNot(false, addOrderButton, applyAddingOrderButton, cancelAddingOrderButton,
                    itemsTable);
            helper.disableOrderInfo(true);

            ordersTable.getSelectionModel().select(currentOrder);
            ordersTable.scrollTo(currentOrder);
        }
    }

    @FXML
    public void changeOrder() {
        helper.disableForActionButNot(true, changeOrderButton, applyChangingOrderButton, cancelChangingOrderButton,
                itemsTable, addItemButton);

        // editable fields
        statusBox.setDisable(false);
        statusBox.setStyle("-fx-border-color: transparent");
        customerBox.setDisable(false);
        customerBox.setStyle("-fx-border-color: transparent");
        deadlinePicker.setDisable(false);
        deadlinePicker.setStyle("-fx-border-color: transparent");
    }

    @FXML
    public void applyChangingOrder() {
        helper.checkFields();
        if (currentCustomer != null && currentDeadline != null) {
            currentOrder.setCustomer(currentCustomer);
            currentOrder.setDeadline(currentDeadline);
            currentOrder.setStatus(statusBox.getValue());
            orderService.update(currentOrder);
            helper.disableForActionButNot(false, changeOrderButton, applyChangingOrderButton, cancelChangingOrderButton,
                    itemsTable, addItemButton);

            // editable fields
            statusBox.setDisable(true);
            statusBox.setStyle("-fx-border-color: transparent");
            customerBox.setDisable(true);
            customerBox.setStyle("-fx-border-color: transparent");
            deadlinePicker.setDisable(true);
            deadlinePicker.setStyle("-fx-border-color: transparent");
        }
    }

    @FXML
    public void cancelChangingOrder() {
        helper.disableForActionButNot(false, changeOrderButton, applyChangingOrderButton, cancelChangingOrderButton,
                itemsTable, addItemButton);

        // editable fields
        statusBox.setDisable(true);
        statusBox.setStyle("-fx-border-color: transparent");
        customerBox.setDisable(true);
        customerBox.setStyle("-fx-border-color: transparent");
        deadlinePicker.setDisable(true);
        deadlinePicker.setStyle("-fx-border-color: transparent");
    }

    @FXML
    public void deleteOrder() {
        helper.disableForActionButNot(true, deleteOrderButton, applyDeletingOrderButton, cancelDeletingOrderButton);
    }

    @FXML
    public void applyDeletingOrder() {
        for (Item item : currentOrder.getItems()) {
            itemService.delete(item);
        }
        orderService.delete(currentOrder);
        ordersTable.getItems().remove(currentOrder);
        helper.disableForActionButNot(false, deleteOrderButton, applyDeletingOrderButton, cancelDeletingOrderButton);
    }

    @FXML
    public void cancelDeletingOrder() {
        helper.disableForActionButNot(false, deleteOrderButton, applyDeletingOrderButton, cancelDeletingOrderButton);
    }

    @FXML
    public void logOut() {
        StageFactory.backToLogInWindow();
    }

    @FXML
    public void addItem() {
        helper.clearNewItemRow();
        helper.disableForActionButNot(true,
                addItemButton, applyAddingItemButton, cancelAddingItemButton);
        helper.disableNewItemRow(false);

        productsList = FXCollections.observableArrayList(productService.findAll());
        productsNewItem.setItems(productsList);
    }

    @FXML
    public void applyAddingItem() {
        Product product = InputDataChecker.checkEnum(productsNewItem);
        BigDecimal price = InputDataChecker.checkBigDecimal(priceNewItem);
        Integer amount = InputDataChecker.checkAmount(amountNewItem);

        if (product != null && price != null && amount != null) {
            if (!price.equals(product.getPrice())) {
                product.setPrice(price);
                productService.update(product);
            }
            currentItem = context.getBean(Item.class, product, amount, currentOrder);
            if (!helper.isCurrentOrderNew()) {
                itemService.create(currentItem);
            }

            // How????!!!
            // upd: understand (because of helper.selectCurrentOrder(); <- items.setAll(currentOrder.getItems());
            currentOrder.getItems().add(currentItem);
            currentOrder.updateSummary();
            if (!helper.isCurrentOrderNew()) {
                orders.set(orders.indexOf(currentOrder), currentOrder);
            }

            helper.disableNewItemRow(true);
            helper.disableForActionButNot(false,
                    addItemButton, applyAddingItemButton, cancelAddingItemButton);
            helper.selectCurrentOrder();

            itemsTable.requestFocus();
            itemsTable.getSelectionModel().select(currentItem);
            itemsTable.scrollTo(currentItem);
        }

    }

    @FXML
    public void cancelAddingItem() {
        helper.disableNewItemRow(true);
        helper.disableForActionButNot(false,
                addItemButton, applyAddingItemButton, cancelAddingItemButton);
        helper.selectCurrentOrder();

        productsNewItem.setStyle("-fx-border-color: inherit");
        amountNewItem.setStyle("-fx-border-color: inherit");
        priceNewItem.setStyle("-fx-border-color: inherit");
    }

    @FXML
    public void changeItem() {
        helper.disableForActionButNot(true,
                changeItemButton, applyChangingItemButton, cancelChangingItemButton);

        numberNewItem.setText("" + itemsTable.getSelectionModel().getSelectedIndex());
        amountNewItem.setText("" + currentItem.getAmount());
        productsNewItem.setItems(productsList == null ? productsList = FXCollections.observableArrayList(productService.findAll()) : productsList);
        productsNewItem.getSelectionModel().select(currentItem.getProduct());
//        priceNewItem.setText(decimalFormat.format(currentItem.getPrice()));

        // sets text in last cells
        sum = currentItem.getPrice().multiply(BigDecimal.valueOf(currentItem.getAmount()));
        priceVAT = currentItem.getPrice().multiply(BigDecimal.valueOf(1.2));
        sumVAT = priceVAT.multiply(BigDecimal.valueOf(currentItem.getAmount()));

        sumNewItem.setText(decimalFormat.format(sum));
        priceVATNewItem.setText(decimalFormat.format(priceVAT));
        sumVATNewItem.setText(decimalFormat.format(sumVAT));


        newItemRow.setVisible(true);
    }

    @FXML
    public void applyChangingItem() {
        Product product = InputDataChecker.checkEnum(productsNewItem);
        BigDecimal price = InputDataChecker.checkBigDecimal(priceNewItem);
        Integer amount = InputDataChecker.checkAmount(amountNewItem);

        if (product != null && price != null && amount != null) {
            newItemRow.setVisible(false);

            if (!price.equals(product.getPrice())) {
                product.setPrice(price);
                productService.update(product);
            }
            currentItem.setProduct(product);
            currentItem.setPrice(price);
            currentItem.setAmount(amount);

            itemService.update(currentItem);
            currentOrder.updateSummary();
            if (!helper.isCurrentOrderNew()) {
                orders.set(orders.indexOf(currentOrder), currentOrder);
            }

            helper.disableForActionButNot(false,
                    changeItemButton, applyChangingItemButton, cancelChangingItemButton);
            helper.selectCurrentOrder();
        }
    }

    @FXML
    public void cancelChangingItem() {
        newItemRow.setVisible(false);
        helper.disableForActionButNot(false,
                changeItemButton, applyChangingItemButton, cancelChangingItemButton);
        helper.selectCurrentOrder();

        productsNewItem.setStyle("-fx-border-color: inherit");
        amountNewItem.setStyle("-fx-border-color: inherit");
        priceNewItem.setStyle("-fx-border-color: inherit");
    }

    @FXML
    public void deleteItem() {
        helper.disableForActionButNot(true, deleteItemButton, applyDeletingItemButton, cancelDeletingItemButton);
    }

    @FXML
    public void applyDeletingItem() {
        Item itemToDelete = currentItem;
        itemService.delete(itemToDelete);
        currentOrder.getItems().remove(itemToDelete);
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

    @Override
    public void setUserSession(UserSession session) {
        this.userSession = session;
    }

    private class Helper {
        private void initServices() {
            context = ApplicationContextFactory.getApplicationContext();
            orderService = context.getBean(OrderService.class);
            itemService = context.getBean(ItemService.class);
            productService = context.getBean(ProductService.class);
            customerService = context.getBean(CustomerService.class);
            userService = context.getBean(UserService.class);
            sessionService = context.getBean(UserSessionService.class);
        }

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
            changeOrderButton.setDisable(currentOrder == null || isCurrentOrderNew());
            deleteOrderButton.setDisable(currentOrder == null || isCurrentOrderNew());
            selectCurrentItem();
        }

        private void fillInfoWith(Order currentOrder) {
            // second check in case if order is not saved in DB (in other words if it's new)
            if (currentOrder != null) {
                items.setAll(currentOrder.getItems());
                addItemButton.setDisable(false);
                fillOrderInfo(currentOrder);

                if (currentOrder.getId() != null) {
                    Integer amount = 0;
                    BigDecimal sum = BigDecimal.ZERO;
                    for (Item item : items) {
                        amount += item.getAmount();
                        sum = sum.add(item.getSumVAT());
                    }
                    amountLabel.setText("" + amount);
                    sumLabel.setText(decimalFormat.format(sum));
                }
            } else {
                items.clear();
                clearOrderInfo();

                addItemButton.setDisable(true);
            }
        }

        private void fillOrderInfo(Order currentOrder) {
            // extra checks for case - new Order()
            if (currentOrder.getId() != null) orderNumberField.setText(currentOrder.getId().toString());
            if (currentOrder.getDeadline() != null) deadlinePicker.setValue(currentOrder.getDeadline().toLocalDate());
            if (currentOrder.getCustomer() != null) customerBox.setValue(currentOrder.getCustomer());

            managerField.setText(currentOrder.getManager().shortInfo());
            orderDateField.setText(currentOrder.getDate().toLocalDate().format(formatter));
            statusBox.setValue(currentOrder.getStatus());
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
            newItemRow.setVisible(!bool);
        }

        private Boolean isCurrentOrderNew() {
            return currentOrder != null && currentOrder.getId() == null;
        }

        private void clearNewItemRow() {
            numberNewItem.setText("" + (items.size() + 1));
            productsNewItem.setItems(productsList == null ? productsList = FXCollections.observableArrayList(productService.findAll()) : productsList);
            amountNewItem.setText("");
            priceNewItem.setText("");
            sumNewItem.setText("");
            priceVATNewItem.setText("");
            sumVATNewItem.setText("");
        }

        private void addInvalidationListenerForNewItemRow() {
            InvalidationListener listener = (observable) -> {
                if (amountNewItem.getText().isEmpty()) {
                    sumNewItem.setText("");
                    priceVATNewItem.setText("");
                    sumVATNewItem.setText("");
                }
                if (priceNewItem.getText().isEmpty()) {
                    sumNewItem.setText("");
                    priceVATNewItem.setText("");
                    sumVATNewItem.setText("");
                }
                if (productsNewItem.getSelectionModel().getSelectedItem() == null) return;
                try {
                    amount = Integer.parseInt(amountNewItem.getText());
                    price = BigDecimal.valueOf(decimalFormat.parse(priceNewItem.getText()).doubleValue());

                    sum = price.multiply(BigDecimal.valueOf(amount));
                    priceVAT = price.multiply(BigDecimal.valueOf(1.2));
                    sumVAT = priceVAT.multiply(BigDecimal.valueOf(amount));
                    sumNewItem.setText(decimalFormat.format(sum));
                    priceVATNewItem.setText(decimalFormat.format(priceVAT));
                    sumVATNewItem.setText(decimalFormat.format(sumVAT));
                } catch (NumberFormatException | ParseException  e) {
                    sumNewItem.setText("");
                    priceVATNewItem.setText("");
                    sumVATNewItem.setText("");
                }
            };
            productsNewItem.getSelectionModel().selectedItemProperty()
                    .addListener(((observable, oldValue, newValue) -> {
                        if (newValue != null) {
                            priceNewItem.setText(decimalFormat.format(newValue.getPrice()));
                        }
                    }));

            amountNewItem.textProperty().addListener(listener);
            priceNewItem.textProperty().addListener(listener);
        }
    }
}