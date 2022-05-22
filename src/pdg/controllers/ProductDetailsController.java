package pdg.controllers;

import java.io.File;
import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import pdg.components.ErrorPopupComponent;
import pdg.models.Product;
import pdg.models.view.ProductViewModel;
import pdg.repositories.ProductRepository;
import pdg.utils.DateHelper;
import pdg.utils.FileHelper;
import pdg.utils.Util;

public class ProductDetailsController extends ChildController {
    @FXML
    private TextField idField;
    @FXML
    private TextField titleField;
    @FXML
    private TextArea descriptionField;
    @FXML
    private ImageView imageField;
    @FXML
    private TextField priceField;
    @FXML
    private TextField qtyField;
    @FXML
    private TextField createdAtField;
    @FXML
    private TextField updatedAtField;

    private boolean isEditable = false;
    private ProductViewModel viewModel;
    private FileChooser fileChooser;

    @Override
    public void initialize(URL url, ResourceBundle bundle) {
        super.initialize(url, bundle);
        fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("image", "*.png", "*.jpg", "*.jpeg"));
    }

    @FXML
    private void onCancelButtonClick(ActionEvent event) {
        try {
            parentController.loadView(MainController.PRODUCT_LIST_VIEW);
        } catch (Exception e) {
            ErrorPopupComponent.show(String.valueOf(e));
        }
    }

    @FXML
    private void onSaveButtonClick(ActionEvent event) {
        try {
            if (viewModel.getId() > 0) {
                ProductRepository.update(viewModel.getModel());
            } else {
                ProductRepository.create(viewModel.getModel());
            }
            parentController.loadView(MainController.PRODUCT_LIST_VIEW);
        } catch (Exception e) {
            ErrorPopupComponent.show(String.valueOf(e));
        }
    }

    public void setEditable(boolean editable) {
        this.isEditable = editable;

        titleField.setDisable(!isEditable);
        descriptionField.setDisable(!isEditable);
        priceField.setDisable(!isEditable);
        qtyField.setDisable(!isEditable);

        if (isEditable) {
            imageField.setOnMouseClicked(e -> this.onImageClick(e));
        } else {
            imageField.setOnMouseClicked(null);
        }
    }

    public void setModel(Product model) {
        viewModel = new ProductViewModel(model);

        idField.setText(Integer.toString(viewModel.getId()));
        titleField.setText(viewModel.getTitle());
        descriptionField.setText(viewModel.getDescription());
        if (!Util.isEmpty(viewModel.getImage())) {
            File src = new File(FileHelper.get().getImageDir() + "/" + viewModel.getImage());
            Image image = new Image(src.toURI().toString());
            imageField.setImage(image);
        }
        priceField.setText(Double.toString(viewModel.getPrice()));
        qtyField.setText(Double.toString(viewModel.getQty()));
        createdAtField.setText(DateHelper.toSqlFormat(viewModel.getCreatedAt()));
        updatedAtField.setText(DateHelper.toSqlFormat(viewModel.getUpdatedAt()));

        titleField.textProperty().bindBidirectional(viewModel.titleProperty());
        descriptionField.textProperty().bindBidirectional(viewModel.descriptionProperty());
        priceField.textProperty().addListener((ov, oldVal, newVal) -> {
            if (!Util.isEmpty(newVal)) {
                try {
                    viewModel.setPrice(Double.parseDouble(newVal));
                } catch (Exception e) {
                }
            }
        });
        qtyField.textProperty().addListener((ov, oldVal, newVal) -> {
            if (!Util.isEmpty(newVal)) {
                try {
                    viewModel.setQty(Double.parseDouble(newVal));
                } catch (Exception e) {
                }
            }
        });
    }

    private void onImageClick(MouseEvent event) {
        try {
            Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            File srcFile = fileChooser.showOpenDialog(primaryStage);
            if (srcFile != null) {
                FileHelper fh = FileHelper.get();
                String filename = new Date().getTime() + (int) (Math.random() * 100) + "." + fh.fileExt(srcFile);
                File destFile = new File(fh.getImageDir() + "/" + filename);
                fh.copyFile(srcFile, destFile);

                Image image = new Image(destFile.toURI().toString());
                imageField.setImage(image);
                viewModel.setImage(filename);
            }
        } catch (Exception e) {
            ErrorPopupComponent.show(String.valueOf(e));
        }
    }

//    @Override
//    public void loadLangTexts(ResourceBundle langBundle) {
//        // TODO Auto-generated method stub
//
//    }
}
