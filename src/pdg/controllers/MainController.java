package pdg.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import pdg.components.AboutComponent;
import pdg.components.ErrorPopupComponent;
import pdg.models.LangEnum;
import pdg.utils.AppConfig;
import pdg.utils.SessionManager;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

public class MainController extends BaseController {
    public final static String PROFILE_VIEW = "profile";
    public final static String LOG_IN_VIEW = "login";
    public final static String PRODUCT_LIST_VIEW = "product-list";
    public final static String PRODUCT_DETAILS_VIEW = "product-details";
    public final static String WISHLIST_PRODUCT_LIST_VIEW = "wishlist-product-list";
    public final static String CART_PRODUCT_LIST_VIEW = "cart-product-list";

    public final static String LOGIN_CHAT_VIEW = "LoginChatView";


    private static final String VIEW_PATH = "../views";

    @FXML
    private VBox contentPane;

    @FXML
    private Label loggedInUserLabel;

    @FXML
    CheckMenuItem enMenuItem;

    @FXML
    CheckMenuItem alMenuItem;

    public boolean enSelected;

    @Override
    public void initialize(URL url, ResourceBundle bundle) {
        super.initialize(url, bundle);
        enSelected = AppConfig.get().getLanguage() == LangEnum.EN;
        enMenuItem.setSelected(enSelected);
        alMenuItem.setSelected(!enSelected);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
        Date date = new Date(System.currentTimeMillis());
        loggedInUserLabel.setText("@" + SessionManager.user.getUsername() + "   " + formatter.format(date));
    }

    public void loadView(String screen) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        Parent node;
        switch (screen) {
            case PRODUCT_LIST_VIEW:
                loader.setLocation(getClass().getResource(viewPath(PRODUCT_LIST_VIEW)));
                node = loader.load();
                break;
            case PRODUCT_DETAILS_VIEW:
                loader.setLocation(getClass().getResource(viewPath(PRODUCT_DETAILS_VIEW)));
                node = loader.load();
                break;
            case WISHLIST_PRODUCT_LIST_VIEW:
                loader.setLocation((getClass().getResource(viewPath(WISHLIST_PRODUCT_LIST_VIEW))));
                node = loader.load();
                break;
            case CART_PRODUCT_LIST_VIEW:
                loader.setLocation((getClass().getResource(viewPath(CART_PRODUCT_LIST_VIEW))));
                node = loader.load();
                break;
            case PROFILE_VIEW:
                loader.setLocation(getClass().getResource(viewPath(PROFILE_VIEW)));
                node = loader.load();
                break;
            case LOG_IN_VIEW:
                loader.setLocation(getClass().getResource(viewPath(LOG_IN_VIEW)));
                node = loader.load();
                break;
            case LOGIN_CHAT_VIEW:
                loader.setLocation(getClass().getResource(viewPath(LOGIN_CHAT_VIEW)));
                node = loader.load();
                break;
            default:
                throw new Exception("ERR_SCREEN_NOT_FOUND");
        }

        ChildController controller = loader.getController();
        loadView(screen, node, controller);
    }
    public void loadView(String screen, Parent pane, ChildController controller) throws Exception{
        controller.setParentController(this);
        this.childController = controller;

        contentPane.getChildren().clear();
        contentPane.getChildren().add(pane);
        VBox.setVgrow(pane, Priority.ALWAYS);

        switch (screen) {
            case PRODUCT_LIST_VIEW:
                contentPane.setAlignment(Pos.CENTER);
                break;
            case PRODUCT_DETAILS_VIEW:
                contentPane.setAlignment(Pos.CENTER);
                break;
            case WISHLIST_PRODUCT_LIST_VIEW:
                contentPane.setAlignment(Pos.CENTER);
                break;
            case CART_PRODUCT_LIST_VIEW:
                contentPane.setAlignment(Pos.CENTER);
                break;
            case LOG_IN_VIEW:
                contentPane.setAlignment(Pos.CENTER);
                break;
            case PROFILE_VIEW:
                contentPane.setAlignment(Pos.TOP_LEFT);
                break;
            default:
                throw new Exception("ERR_SCREEN_NOT_FOUND");
        }
        ResourceBundle langBundle = getLangBundle();
        controller.loadLangTexts(langBundle);
    }
//
//    @FXML
//    private void onProductsNavClick(ActionEvent event) {
//        try {
//            this.loadView(PRODUCT_LIST_VIEW);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    @FXML
    private void onProfileNavClick(ActionEvent event) {
        try {
            this.loadView(PROFILE_VIEW);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void onProductsNavClick(ActionEvent event){
        try{
            this.loadView(PRODUCT_LIST_VIEW);
        } catch (Exception e){
            e.printStackTrace();
        }
    }
@FXML
    public void OpenChat(ActionEvent actionEvent) {
    try {
//        Server server = new Server();
//        server.main();
        Parent root = FXMLLoader.load(getClass().getResource(viewPath("LoginChatView")));
        Scene scene = new Scene(root);

        Stage primaryStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        primaryStage.setScene(scene);
        primaryStage.show();

    } catch (Exception e) {
    }
    }
    @FXML
    private void onLogoutNavClick(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(viewPath("login")));
            Scene scene = new Scene(root);
            Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception e) {

        }
    }


    @FXML
    private void onLogoutMenuClick(ActionEvent event) {
        try {
            Parent parent = FXMLLoader.load(getClass().getResource(viewPath("login")));
            Scene scene = new Scene(parent);

            Stage primaryStage = (Stage) loggedInUserLabel.getScene().getWindow();
            primaryStage.setScene(scene);

            SessionManager.user = null;

        } catch (Exception e) {
            ErrorPopupComponent.show(e.toString());
        }
    }

    @FXML
    private void onExitMenuClick(ActionEvent event) {
        try {
            Stage primaryStage = (Stage) loggedInUserLabel.getScene().getWindow();
            primaryStage.close();
        } catch (Exception e) {
            ErrorPopupComponent.show(e.toString());
        }
    }

    @FXML
    private void onAboutClick(ActionEvent event) {
        try {
            new AboutComponent().showDialog();
        } catch (Exception e) {
        }
    }

    @FXML
    private void onWishlistBtnClick(ActionEvent event){
        try{
            this.loadView(WISHLIST_PRODUCT_LIST_VIEW);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @FXML
    private void onCartBtnClick(ActionEvent event){
        try{
            this.loadView(CART_PRODUCT_LIST_VIEW);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @FXML
    public void onAlMenuItemCLick(ActionEvent ev) {
        enMenuItem.setSelected(false);
        alMenuItem.setSelected(true);
        updateLanguage();
    }

    @FXML
    public void onEnMenuItemCLick(ActionEvent ev) {
        enMenuItem.setSelected(true);
        alMenuItem.setSelected(false);
        updateLanguage();
    }

    private void updateLanguage() {
        try {
            LangEnum lang = enMenuItem.isSelected() ? LangEnum.EN : LangEnum.AL;
            AppConfig conf = AppConfig.get();
            conf.setLanguage(lang);

            ResourceBundle bundle = getLangBundle();
            loadLangTexts(bundle);
            if (this.childController != null) {
                this.childController.loadLangTexts(bundle);
            }
        } catch (Exception ex) {
            ErrorPopupComponent.show(ex.toString());
        }
    }

    private ChildController childController = null;


    @FXML
    private SplitMenuButton msLang;
    @FXML
    private Button logOutButt;
    @FXML
    private MenuItem onLogoutMenuClick;
    @FXML
    private MenuItem onExitMenuClick;
    @FXML
    private MenuItem onNewGameButton;
    @FXML
    private MenuItem onAboutButton;
    @FXML
    private Menu newGameButt;
    @FXML
    private Menu helpButt;
    @FXML
    private Label nav;
    @FXML
    private Button msLead;
    @FXML
    private Button msProf;
    @FXML
    private Menu fileButt;

    private String viewPath(String view) {
        return VIEW_PATH + "/" + view + ".fxml";
    }

    @Override
    public void loadLangTexts(ResourceBundle langBundle) {
        try {

            String msLangu = langBundle.getString("ms_lang");
            String logOutButton = langBundle.getString("log_out_button");
            String closeButton = langBundle.getString("close_button");
            String newGameButton = langBundle.getString("new_game_button");
            String helpButton = langBundle.getString("help_button");
            String navigation = langBundle.getString("navigation");
            String msLeaderboard = langBundle.getString("ms_leaderboard");
            String msProfile = langBundle.getString("ms_profile");
            String logoutButton = langBundle.getString("log_out_button");
            String aboutButton = langBundle.getString("about");
            String anewGameButton = langBundle.getString("anew_game_button");
            String file = langBundle.getString("file_button");


            msLang.setText(msLangu);
            logOutButt.setText(logOutButton);
            onExitMenuClick.setText(closeButton);
            onLogoutMenuClick.setText(logoutButton);
            newGameButt.setText(newGameButton);
            helpButt.setText(helpButton);
            nav.setText(navigation);
            msLead.setText(msLeaderboard);
            msProf.setText(msProfile);
            onAboutButton.setText(aboutButton);
            onNewGameButton.setText(anewGameButton);
            fileButt.setText(file);


            if (this.childController != null) {
                this.childController.loadLangTexts(langBundle);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}