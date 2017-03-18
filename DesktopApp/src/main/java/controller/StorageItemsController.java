package controller;

import entity.Product;
import entity.Storage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import org.springframework.context.ApplicationContext;
import service.ProductService;
import service.StorageService;
import util.ApplicationContextFactory;
import util.FilenameGenerator;
import util.InputDataChecker;
import util.StageFactory;

import java.io.File;
import java.math.BigDecimal;

public class StorageItemsController {

    @FXML TableView<Storage> productTableView;
    @FXML TableColumn<Storage, String> productNameColumn;
    @FXML TableColumn<Storage, Integer> productQuantityColumn;
    @FXML TableColumn<Storage, BigDecimal> productPriceColumn;

    @FXML TextField nameTextField;
    @FXML TextField quantityTextField;
    @FXML TextField priceTextField;

    @FXML HBox nameBox;
    @FXML HBox quantityBox;
    @FXML HBox priceBox;

    @FXML TextArea  descriptionArea;
    @FXML Button pictureChooser;
    private File chosenPicture;

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

    private ObservableList<Storage> storageItems;
    private Storage currentStorage;

    private ApplicationContext context;

    private Helper helper;

    private Boolean isNew;

    private FileChooser chooser;
    private String fileName;

    public void initialize() {
        helper = new Helper();
        helper.refreshTable();
        helper.addSelectListener();

        chooser = new FileChooser();
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
            if (!helper.createProduct()) return;
        } else {
            if (!helper.saveProduct()) return;
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
        StageFactory.loadWindow("/view/storage_panel_two.fxml", "Storage", -1L);
    }

    @FXML
    public void choosePicture() {
        chosenPicture = chooser.showOpenDialog(this.deleteButton.getScene().getWindow());
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
            storageService = context.getBean(StorageService.class);
            productService = context.getBean(ProductService.class);
            productNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
            productQuantityColumn.setCellValueFactory(new PropertyValueFactory<>("amount"));
            productPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
            storageItems = FXCollections.observableArrayList(storageService.findAll());
            productTableView.setItems(storageItems);
        }

        public Boolean saveProduct() {
            String name = InputDataChecker.checkString(nameTextField);
            BigDecimal price = InputDataChecker.checkBigDecimal(priceTextField);
            Integer amount = InputDataChecker.checkInteger(quantityTextField);
            String description = InputDataChecker.checkString(descriptionArea);

            if (name != null && price != null && amount != null && description != null) {
                currentStorage.getProduct().setName(name);
                currentStorage.getProduct().setPrice(price);
                currentStorage.getProduct().setDescription(description);
                currentStorage.setAmount(amount);

                if (chosenPicture != null) {
                    String imageFilename = FilenameGenerator.generate();

                    String path = this.getClass().getResource("/product_images").getFile();
                    System.out.println(path);

                    File imageFile = new File(path + "/" + imageFilename + ".jpg");
                    System.out.println(chosenPicture.renameTo(imageFile));
                    System.out.println(chosenPicture.getAbsolutePath());

                    // consider that image files are placed in webapp/img/product_images
                    currentStorage.getProduct().setFilename(imageFilename);

                    chosenPicture = null;
                }

                productService.update(currentStorage.getProduct());
                storageService.update(currentStorage);
                return true;
            } else {
                return false;
            }
        }

        public Boolean createProduct() {
            String name = InputDataChecker.checkString(nameTextField);
            BigDecimal price = InputDataChecker.checkBigDecimal(priceTextField);
            Integer quantity = InputDataChecker.checkInteger(quantityTextField);
            String description = InputDataChecker.checkString(descriptionArea);
            if (chosenPicture == null) {
                pictureChooser.setStyle("-fx-border-color: red;" +
                        "-fx-border-radius: inherit");
                return false;
            } else {
                pictureChooser.setStyle("-fx-border-color: inherit;");
            }

            if (name != null && price != null && quantity != null && description != null) {
                Product product = new Product();
                product.setName(name);
                product.setPrice(price);
                product.setDescription(description);

                String imageFilename = FilenameGenerator.generate();

                String path = this.getClass().getResource("/product_images").getFile();

                File imageFile = new File(path + "/" + imageFilename + ".jpg");
                chosenPicture.renameTo(imageFile);

                // consider that image files are placed in webapp/img/product_images
                currentStorage.getProduct().setFilename(imageFilename);

                chosenPicture = null;

                productService.create(product);
                Storage storage = new Storage(product, quantity);
                storageService.create(storage);
                return true;
            }
            return false;
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
            nameBox.setVisible(true);
            quantityBox.setVisible(true);
            priceBox.setVisible(true);
            descriptionArea.setEditable(true);
            pictureChooser.setVisible(true);
            nameTextField.setText("");
            quantityTextField.setText("");
            priceTextField.setText("");
            descriptionArea.setText("");
            saveCancel.setVisible(true);
            newEditDelete.setVisible(false);
        }

        public void fieldsOnForDeleting() {
            productTableView.setDisable(true);
            newEditDelete.setVisible(false);
            deleteCancel.setVisible(true);
        }

        private void fieldsOn() {
            nameBox.setVisible(true);
            quantityBox.setVisible(true);
            priceBox.setVisible(true);
            descriptionArea.setEditable(true);
            pictureChooser.setVisible(true);
            nameTextField.setText(currentStorage.getName());
            quantityTextField.setText(currentStorage.getAmount().toString());
            priceTextField.setText(currentStorage.getPrice().toString());
            descriptionArea.setText(currentStorage.getProduct().getDescription());
            productTableView.setDisable(true);
            newEditDelete.setVisible(false);
        }

        public void fieldsOff() {
            nameBox.setVisible(false);
            quantityBox.setVisible(false);
            priceBox.setVisible(false);
            descriptionArea.setEditable(false);
            pictureChooser.setVisible(false);
            nameTextField.setText("");
            quantityTextField.setText("");
            priceTextField.setText("");
            descriptionArea.setText("");
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
