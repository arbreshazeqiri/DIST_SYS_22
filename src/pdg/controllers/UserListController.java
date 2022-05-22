package pdg.controllers;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import pdg.components.ErrorPopupComponent;
import pdg.components.PaginationComponent;
import pdg.components.UserCardComponent;
import pdg.models.User;
import pdg.repositories.UserRepository;

public class UserListController extends ChildController {
    private final int PAGE_SIZE = 10;

    private PaginationComponent paginationComponent;

    @FXML
    private FlowPane usersPane;
    @FXML
    private HBox paginationPane;

    @Override
    public void initialize(URL url, ResourceBundle bundle) {
        super.initialize(url, bundle);
        try {
            paginationComponent = new PaginationComponent(userCount(), PAGE_SIZE);
            paginationComponent.render(paginationPane, (page) -> {
                try {
                    showUsers(page);
                } catch (Exception e) {
                    ErrorPopupComponent.show(String.valueOf(e));
                }
            });

            showUsers(0);
        } catch (Exception e) {
            ErrorPopupComponent.show(String.valueOf(e));
        }
    }

    private int userCount() throws Exception {
        return UserRepository.count();
    }

    private void showUsers(int page) throws Exception {
        usersPane.getChildren().clear();
        List<User> users = UserRepository.getAll(PAGE_SIZE, page);
        UserCardComponent userCard = new UserCardComponent();
        for (User user : users) {
            usersPane.getChildren().add(userCard.getContent(user, e -> showUser(user), e -> removeUser(user)));
        }
    }

    private void removeUser(User user) {
        try {
            UserRepository.remove(user.getUsername());

            int currPage = paginationComponent.getCursor();
            paginationComponent = new PaginationComponent(userCount(), PAGE_SIZE);
            paginationComponent.render(paginationPane, (page) -> {
                try {
                    showUsers(page);
                } catch (Exception e) {
                    ErrorPopupComponent.show(String.valueOf(e));
                }
            });

            paginationComponent.forceCursor(currPage);
            showUsers(currPage);
        } catch (Exception e) {
            ErrorPopupComponent.show(String.valueOf(e));
        }
    }

    private void showUser(User user) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("../views/" + MainController.USER_DETAILS_VIEW + ".fxml"));

            Pane pane = loader.load();
            UserDetailsController controller = loader.getController();
            controller.setModel(user);

            parentController.loadView(MainController.USER_DETAILS_VIEW, pane, controller);
        } catch (Exception e) {
            ErrorPopupComponent.show(String.valueOf(e));
        }
    }

//    private void changeUserState(User user) {
//        try {
//            user.setActive(!user.getActive());
//            UserRepository.update(user);
//        } catch (Exception e) {
//            ErrorPopupComponent.show(e);
//        }
//    }

    @Override
    public void loadLangTexts(ResourceBundle langBundle) {
        // TODO Auto-generated method stub

    }
}
