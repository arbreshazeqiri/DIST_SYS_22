package pdg.controllers;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.WindowEvent;
import pdg.components.ErrorPopupComponent;
import pdg.components.FindProductComponent;
import pdg.components.PaginationComponent;
import pdg.models.Product;
import pdg.repositories.ProductRepository;

public class ProductListController extends ChildController {
    final KeyCombination keyCtrlF = new KeyCodeCombination(KeyCode.F, KeyCombination.CONTROL_ANY);
    private final int PAGE_SIZE = 10;

    private PaginationComponent paginationComponent;

    @FXML
    private TableView<Product> tableView;
    @FXML
    private TableColumn<Product, Integer> idColumn;
    @FXML
    private TableColumn<Product, String> titleColumn;
    @FXML
    private TableColumn<Product, Double> priceColumn;
    @FXML
    private TableColumn<Product, Double> qtyColumn;
    @FXML
    private ToggleButton multipleButton;
    @FXML
    private Button findButton;
    @FXML
    private Button showAllButton;
    @FXML
    private HBox paginationPane;
    @FXML
    private MenuItem viewMenuItem;
    @FXML
    private MenuItem editMenuItem;
    @FXML
    private MenuItem removeMenuItem;

    @FXML
    private ImageView image;

    @Override
    public void initialize(URL url, ResourceBundle bundle) {
        super.initialize(url, bundle);

        try {
            idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
            titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
            priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
            qtyColumn.setCellValueFactory(new PropertyValueFactory<>("qty"));

            tableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
            showProducts(0);

            paginationComponent = new PaginationComponent(productCount(), PAGE_SIZE);
            paginationComponent.render(paginationPane, (page) -> {
                try {
                    showProducts(page);
                } catch (Exception e) {
                    ErrorPopupComponent.show(String.valueOf(e));
                }
            });
        } catch (Exception e) {
            ErrorPopupComponent.show(String.valueOf(e));
        }
    }

    @FXML
    private void onViewMenuItemClick(ActionEvent event) {
        Product selected = tableView.getSelectionModel().getSelectedItem();
        if (selected == null)
            return;

        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("../views/" + MainController.PRODUCT_DETAILS_VIEW + ".fxml"));

            Pane pane = loader.load();
            ProductDetailsController controller = loader.getController();
            controller.setModel(selected);
            controller.setEditable(false);

            parentController.loadView(MainController.PRODUCT_DETAILS_VIEW, pane, controller);
        } catch (Exception e) {
            ErrorPopupComponent.show(String.valueOf(e));
        }
    }

    @FXML
    private void onEditMenuItemClick(ActionEvent event) {
        Product selected = tableView.getSelectionModel().getSelectedItem();
        if (selected == null)
            return;

        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("../views/" + MainController.PRODUCT_DETAILS_VIEW + ".fxml"));

            Pane pane = loader.load();
            ProductDetailsController controller = loader.getController();
            controller.setModel(selected);
            controller.setEditable(true);

            parentController.loadView(MainController.PRODUCT_DETAILS_VIEW, pane, controller);
        } catch (Exception e) {
            ErrorPopupComponent.show(String.valueOf(e));
        }
    }

    @FXML
    private void onRemoveMenuItemClick(ActionEvent event) {

    }

    @FXML
    private void onContextMenuShowing(WindowEvent event) {
        int selected = tableView.getSelectionModel().getSelectedItems().size();
        if (selected == 1) {
            viewMenuItem.setDisable(false);
            editMenuItem.setDisable(false);
            removeMenuItem.setDisable(false);
        } else if (selected > 1) {
            viewMenuItem.setDisable(true);
            editMenuItem.setDisable(true);
            removeMenuItem.setDisable(false);
        } else {
            viewMenuItem.setDisable(true);
            editMenuItem.setDisable(true);
            removeMenuItem.setDisable(true);
        }
    }

    @FXML
    private void onMultipleButtonClick(ActionEvent event) {
        if (multipleButton.isSelected()) {
            tableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        } else {
            tableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        }
    }

    @FXML
    private void onFindButtonClick(ActionEvent event) {
        try {
            List<Product> products = this.findProducts();
            tableView.getItems().clear();
            tableView.setItems(FXCollections.observableArrayList(products));
            paginationPane.setVisible(false);
        } catch (Exception e) {
            ErrorPopupComponent.show(String.valueOf(e));
        }
    }

    @FXML
    private void onShowAllButtonClick(ActionEvent event) {
        try {
            showProducts(paginationComponent.getCursor());
            paginationPane.setVisible(true);
        } catch (Exception e) {
            ErrorPopupComponent.show(String.valueOf(e));
        }
    }

    @FXML
    private void onScreenKeyPressed(KeyEvent event) {
        if (keyCtrlF.match(event)) {
            try {
                List<Product> products = this.findProducts();
                tableView.getItems().clear();
                tableView.setItems(FXCollections.observableArrayList(products));
                paginationPane.setVisible(false);
            } catch (Exception e) {
                ErrorPopupComponent.show(String.valueOf(e));
            }
        }
    }

    private List<Product> findProducts() throws Exception {
        String text = new FindProductComponent().showDialog();
        return ProductRepository.find(text);
    }

    private void showProducts(int page) throws Exception {
        List<Product> products = ProductRepository.getAll(PAGE_SIZE, page);
        tableView.getItems().clear();
        tableView.setItems(FXCollections.observableArrayList(products));
    }

    private int productCount() throws Exception {
        return ProductRepository.count();
    }

//    @Override
//    public void loadLangTexts(ResourceBundle langBundle) {
//        String showAllTxt = langBundle.getString("product_list_show_all_button");
//        showAllButton.setText(showAllTxt);
//    }
}
