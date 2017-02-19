package controller.modal;

import entity.Item;
import entity.Order;
import entity.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
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
public class AddItemController implements ParameterSettable<Order, Item> {
    @FXML private ComboBox<Product> productBox;
          private ObservableList<Product> products;
    @FXML private TextField productField;
    @FXML private Button newProductButton;
    @FXML private Button addProductButton;
    @FXML private TextField priceField;
    @FXML private TextField amountField;
    @FXML private Button saveButton;
    @FXML private Button cancelButton;

    private Order currentOrder;

    private Item item;

    private ProductService productService;
    private ItemService itemService;

    public void initialize() {
        productService = ProductServiceImpl.getInstance();
        itemService = ItemServiceImpl.getInstance();

        products = FXCollections.observableArrayList(productService.findAll());
        productBox.setItems(products);
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
            itemService.add(item);

            StageFactory.closeModal();
        }
    }

    @FXML
    public void cancel() {
        StageFactory.closeModal();
    }

    @FXML
    public void newProduct() {

    }

    @FXML
    public void addProduct() {

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
