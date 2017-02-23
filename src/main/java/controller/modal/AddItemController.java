package controller.modal;

import entity.Item;
import entity.Order;
import entity.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import service.ItemService;
import service.ItemServiceImpl;
import service.ProductService;
import service.ProductServiceImpl;
import util.InputDataChecker;
import util.StageFactory;

import java.math.BigDecimal;

/**
 * Created by eriol4ik on 19.02.2017.
 */
public class AddItemController implements ValueSettable<Order, Item> {
    @FXML private ComboBox<Product> productBox;
          private ObservableList<Product> products;
    @FXML private TextField productNameField;
    @FXML private Button newProductButton;
    @FXML private Button addProductButton;
    @FXML private Button cancelAddingProductButton;
    @FXML private TextField priceField;
    @FXML private TextField amountField;
    @FXML private Button saveButton;
    @FXML private Button cancelButton;

    @FXML private Label amountLabel;
    @FXML private Label priceLabel;

    private Order currentOrder;

    private Item item;

    private ProductService productService;
    private ItemService itemService;

    public void initialize() {
        productService = ProductServiceImpl.getInstance();
        itemService = ItemServiceImpl.getInstance();

        products = FXCollections.observableArrayList(productService.findAll());
        productBox.setItems(products);

        productBox.getSelectionModel().selectedItemProperty()
                .addListener(((observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        priceField.setText("" + newValue.getPrice());
                    }
                }));
    }

    @FXML
    public void save() {
        Product product = InputDataChecker.checkEnum(productBox);
        BigDecimal price = InputDataChecker.checkBigDecimal(priceField);
        Integer amount = InputDataChecker.checkInteger(amountField);

        if (product != null && price != null && amount != null) {
            if (!price.equals(product.getPrice())) {
                product.setPrice(price);
                productService.update(product);
            }
            item = new Item(product, amount, currentOrder);
            if (currentOrder != null) {
                itemService.add(item);
            }

            StageFactory.closeModal();
        }
    }

    @FXML
    public void cancel() {
        StageFactory.closeModal();
    }

    @FXML
    public void newProduct() {
        disableFields(true);
    }

    @FXML
    public void cancelAddingProduct() {
        disableFields(false);
    }

    @FXML
    public void addProduct() {
        String name = InputDataChecker.checkString(productNameField);
        BigDecimal price = InputDataChecker.checkBigDecimal(priceField);

        if (name != null && price != null) {
            Product product = new Product(name, price);
            productService.add(product);
            products.add(product);
            disableFields(false);
            productBox.getSelectionModel().select(product);

        }
    }

    private void disableFields(Boolean bool) {
        productBox.setVisible(!bool);
        productNameField.setVisible(bool);
        productNameField.setStyle("-fx-border-color: transparent");
        newProductButton.setVisible(!bool);
        addProductButton.setVisible(bool);
        amountField.setDisable(bool);
        priceField.setStyle("-fx-border-color: transparent");
        saveButton.setDisable(bool);
        cancelButton.setDisable(bool);
        cancelAddingProductButton.setVisible(bool);

        priceField.setText("");
        if (!bool) productNameField.setText("");
    }


    public void setCurrentOrder(Order currentOrder) {
        this.currentOrder = currentOrder;
    }

    public Item getItem() {
        return item;
    }

    @Override
    public void setParameter(Order parameter) {
        this.currentOrder = parameter;
    }

    @Override
    public Item getResult() {
        return item;
    }
}
