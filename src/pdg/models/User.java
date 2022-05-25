package pdg.models;

public class User {
    private Integer id;
    private String username;
    private String fullname;
    private String email;
    private String password;
    private String salt;
    private String country;
    private int turnScore;
    private boolean active;
    private int totalScore;
    private int score;
    private int numberOfWins;

    public User(Integer id, String username, String fullname, String email, String password, String salt, String country) {
        this.id = id;
        this.username = username;
        this.fullname = fullname;
        this.email = email;
        this.password = password;
        this.salt = salt;
        this.country = country;
        this.turnScore = 0;
        this.totalScore = 0;
        this.score = 0;
        this.numberOfWins = 0;
    }

    public User(Integer id, String username, String fullname, String email, String password, String salt, String country, int numberOfWins, int score) {
        this.id = id;
        this.username = username;
        this.fullname = fullname;
        this.email = email;
        this.password = password;
        this.salt = salt;
        this.country = country;
        this.turnScore = 0;
        this.totalScore = 0;
        this.score = score;
        this.numberOfWins = numberOfWins;
    }

    public User(Integer id, String username, String email, String password){
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public User(String username) {
        this.username = username;
        this.turnScore = 0;
        this.totalScore = 0;
    }

    public String getUsername() {
        return username;
    }

    public Integer getId() { return id;}

    public void setId(Integer id) { this.id = id; }

    public String getFullName() {
        return fullname;
    }

    public void setFullName(String fullName) {
        this.fullname = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

}