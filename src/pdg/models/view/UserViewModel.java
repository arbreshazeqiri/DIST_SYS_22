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
    private StringProperty userRole;
    private BooleanProperty active;
    private StringProperty createdAt;
    private StringProperty updatedAt;
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

    public StringProperty userRoleProperty() {
        return userRole;
    }

    public UserRole getUserRole() {
        return userRole.getValue().equals("Admin") ? UserRole.Admin : UserRole.Buyer;
    }

    public void setUserRole(String value) {
        userRole.setValue(value);
    }

    public void setUserRole(UserRole value) {
        userRole.setValue(value == UserRole.Admin ? "Admin" : "Buyer");
    }

    public BooleanProperty activeProperty() {
        return active;
    }

    public boolean getActive() {
        return active.getValue();
    }

    public void setActive(boolean value) {
        active.setValue(value);
    }

    public StringProperty createdAtProperty() {
        return createdAt;
    }

    public Date getCreatedAt() {
        try {
            return DateHelper.fromSql(createdAt.getValue());
        } catch (Exception e) {
            return null;
        }
    }

    public void setCreatedAt(String value) {
        createdAt.setValue(value);
    }

    public void setCreatedAt(Date value) {
        createdAt.setValue(DateHelper.toSqlFormat(value));
    }

    public StringProperty updatedAtProperty() {
        return updatedAt;
    }

    public Date getUpdatedAt() {
        try {
            return DateHelper.fromSql(updatedAt.getValue());
        } catch (Exception e) {
            return null;
        }
    }

    public void setUpdatedAt(String value) {
        updatedAt.setValue(value);
    }

    public void setUpdatedAt(Date value) {
        updatedAt.setValue(DateHelper.toSqlFormat(value));
    }

    public User getModel() {
        return new User((String) getUsername(), getEmail(), getPassword());
    }

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
