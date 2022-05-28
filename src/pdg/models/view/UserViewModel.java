package pdg.models.view;

import java.util.Date;

import javafx.beans.property.*;

import pdg.models.User;
import pdg.models.UserRole;
import pdg.utils.DateHelper;

public class UserViewModel {
    private IntegerProperty id;
    private StringProperty name;
    private StringProperty email;
    private StringProperty password;
    private StringProperty confirmPassword;
    private StringProperty username;

    public UserViewModel() {
        username = new SimpleStringProperty();
        email = new SimpleStringProperty();
        password = new SimpleStringProperty();
        confirmPassword = new SimpleStringProperty();
    }

    public UserViewModel(User model) {
        this();
        this.setUsername(model.getUsername());
        this.setEmail(model.getEmail());
        this.setPassword("");
        this.setConfirmPassword("");
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public int getId() {
        return id.getValue();
    }

    public void setId(int value) {
        id.setValue(value);
    }

    public StringProperty nameProperty() {
        return name;
    }

    public String getName() {
        return name.getValue();
    }

    public void setName(String value) {
        name.setValue(value);
    }

    public StringProperty emailProperty() {
        return email;
    }

    public String getEmail() {
        return email.getValue();
    }

    public void setEmail(String value) {
        email.setValue(value);
    }

    public StringProperty passwordProperty() {
        return password;
    }

    public String getPassword() {
        return password.getValue();
    }

    public void setPassword(String value) {
        password.setValue(value);
    }

    public StringProperty confirmPasswordProperty() {
        return confirmPassword;
    }

    public String getConfirmPassword() {
        return confirmPassword.getValue();
    }

    public void setConfirmPassword(String value) {
        confirmPassword.setValue(value);
    }


//    public User getModel() {
//        return new User(getId(), (String) getUsername(), getEmail(), getPassword());
//    }

    public Object getUsername() {
        return username.getValue();
    }

    public void setUsername(String value) {
        username.setValue(value);
    }

    public Property<String> usernameProperty() {
        return username;
    }
}
