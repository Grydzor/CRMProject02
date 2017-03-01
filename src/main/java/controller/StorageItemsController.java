package controller;

import entity.Product;
import entity.Storage;
import entity.UserSession;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import org.springframework.context.ApplicationContext;
import service.ProductService;
import service.StorageService;
import util.ApplicationContextFactory;
import util.InputDataChecker;
import util.StageFactory;

import java.math.BigDecimal;

/**
 * Created by Никита on 24.02.2017.
 */

public class StorageItemsController {

    @FXML TableView<Storage> productTableView;
    @FXML TableColumn<Storage, String> productNameColumn;
    @FXML TableColumn<Storage, Integer> productQuantityColumn;
    @FXML TableColumn<Storage, BigDecimal> productPriceColumn;

    @FXML TextField nameTextField;
    @FXML TextField quantityTextField;
    @FXML TextField priceTextField;

    @FXML Button newButton;
    @FXML Button editButton;
    @FXML Button deleteButton;
    @FXML Button closeButton;
    @FXML Button deleteHiddenButton;
    @FXML Button cancelHiddenButton;

    @FXML HBox newEditDelete;
    @FXML HBox deleteCancel;
    @FXML HBox saveCancel;

    private StorageService storageService;
    private ProductService productService;

    private ObservableList<Storage> storages;
    private Storage currentStorage;

    private ApplicationContext context;

    private Helper helper;

    private Boolean isNew;

    public void initialize() {
        helper = new Helper();
        helper.refreshTable();
        helper.addSelectListener();
    }

    @FXML
    public void editButtonOnAction() {
        if (currentStorage != null) {
            isNew = false;
            helper.fieldsOnForSaving();
        }
    }

    @FXML
    public void saveButtonOnAction() {
        if (isNew) {
            helper.createProduct();
        } else {
            helper.saveProduct();
        }
        helper.refreshTable();
        helper.fieldsOff();
    }

    @FXML
    public void newButtonOnAction() {
        isNew = true;
        helper.fieldsOnCreating();
    }

    @FXML
    public void cancelButtonOnAction() {
        helper.fieldsOff();
    }

    @FXML
    public void deleteButtonOnAction() {
        if (currentStorage != null) {
            helper.fieldsOnForDeleting();
        }
    }

    public void deleteHiddenButtonOnAction() {
        storageService.delete(currentStorage);
        helper.refreshTable();
        helper.fieldsOff();
    }

    @FXML
    public void closeButtonOnAction() {
        StageFactory.genericWindow("/view/storage_panel_two.fxml", "Storage", -1L);
    }

    private class Helper {
        private void addSelectListener() {
            productTableView.getSelectionModel().selectedItemProperty()
                    .addListener((observable, oldValue, newValue) -> {
                        currentStorage = newValue;
                    });
        }

        private void refreshTable() {
            context = ApplicationContextFactory.getApplicationContext();
            storageService = (StorageService) context.getBean("storageService");
            productService = (ProductService) context.getBean("productService");
            productNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
            productQuantityColumn.setCellValueFactory(new PropertyValueFactory<>("amount"));
            productPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
            storages = FXCollections.observableArrayList(storageService.findAll());
            productTableView.setItems(storages);
        }

        public void saveProduct() {
            currentStorage.getProduct().setName(InputDataChecker.checkString(nameTextField));
            currentStorage.getProduct().setPrice(InputDataChecker.checkBigDecimal(priceTextField));
            currentStorage.setAmount(InputDataChecker.checkInteger(quantityTextField));
            productService.update(currentStorage.getProduct());
            storageService.update(currentStorage);
        }

        public void createProduct() {
            Product product = new Product(InputDataChecker.checkString(nameTextField), InputDataChecker.checkBigDecimal(priceTextField));
            productService.create(product);
            Storage storage = new Storage(product, InputDataChecker.checkInteger(quantityTextField));
            storageService.create(storage);
        }

        public boolean fieldsChanged() {
            if (!currentStorage.getName().equals(InputDataChecker.checkString(nameTextField))) return true;
            if (!currentStorage.getAmount().equals(InputDataChecker.checkInteger(quantityTextField))) return true;
            if (!currentStorage.getPrice().equals(InputDataChecker.checkBigDecimal(priceTextField))) return true;
            return false;
        }

        public void fieldsOnForSaving() {
            fieldsOn();
            saveCancel.setVisible(true);
        }

        public void fieldsOnCreating() {
            nameTextField.setVisible(true);
            quantityTextField.setVisible(true);
            priceTextField.setVisible(true);
            nameTextField.setText("");
            quantityTextField.setText("");
            priceTextField.setText("");
            saveCancel.setVisible(true);
            newEditDelete.setVisible(false);
        }

        public void fieldsOnForDeleting() {
            productTableView.setDisable(true);
            newEditDelete.setVisible(false);
            deleteCancel.setVisible(true);
        }

        private void fieldsOn() {
            nameTextField.setVisible(true);
            quantityTextField.setVisible(true);
            priceTextField.setVisible(true);
            nameTextField.setText(currentStorage.getName());
            quantityTextField.setText(currentStorage.getAmount().toString());
            priceTextField.setText(currentStorage.getPrice().toString());
            productTableView.setDisable(true);
            newEditDelete.setVisible(false);
        }

        public void fieldsOff() {
            nameTextField.setVisible(false);
            quantityTextField.setVisible(false);
            priceTextField.setVisible(false);
            nameTextField.setText("");
            quantityTextField.setText("");
            priceTextField.setText("");
            productTableView.setDisable(false);
            if (saveCancel.isVisible()) {
                saveCancel.setVisible(false);
            } else if (deleteCancel.isVisible()) {
                deleteCancel.setVisible(false);
            }
            newEditDelete.setVisible(true);
        }

    }

}
