package controller;

import entity.Order;
import entity.Product;
import entity.Storage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import service.ProductService;
import service.ProductServiceImpl;
import service.StorageService;
import util.StageFactory;

import java.math.BigDecimal;

/**
 * Created by Никита on 24.02.2017.
 */

public class StorageItemsController {

    @FXML TableView<Product> productTableView;
    @FXML TableColumn<Product, String> productNameColumn;
    @FXML TableColumn<Order, Integer> productQuantityColumn;
    @FXML TableColumn<Product, BigDecimal> productPriceColumn;

    @FXML TextField nameTextField;
    @FXML TextField quantityTextField;
    @FXML TextField priceTextField;

    @FXML Button newButton;
    @FXML Button editButton;
    @FXML Button deleteButton;
    @FXML Button closeButton;

    private ProductService productService;
    private StorageService storageService;

    private ObservableList<Product> products;
    private ObservableList<Storage> storages;

    public void initialize() {
        productService = ProductServiceImpl.getInstance();
        productNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        productQuantityColumn.setCellValueFactory(new PropertyValueFactory<>("amount"));
        productPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        products = FXCollections.observableArrayList(productService.findAll());
        storages = FXCollections.observableArrayList(storageService.findAll());
        productTableView.setItems(products);
        //productTableView.setItems(storages);
    }

    @FXML
    public void closeButtonOnAction() {
        StageFactory.genericWindow("/view/storage_panel_two.fxml", "Storage", null, "/view/styles/light_theme.css");
    }

}
