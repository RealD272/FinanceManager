public class User {
    private int id;
    private String username;
    private String password;
    private boolean isSubscribed;

    public User(int id, String username, String password, boolean isSubscribed) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.isSubscribed = isSubscribed;
    }

    public User(String username, String password, boolean isSubscribed) {
        this.username = username;
        this.password = password;
        this.isSubscribed = isSubscribed;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isSubscribed() {
        return isSubscribed;
    }

    public void setSubscribed(boolean subscribed) {
        isSubscribed = subscribed;
    }
}
