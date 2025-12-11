package user;

public abstract class User {
    protected String id;
    protected String name;
    protected String role;
    protected String username;
    protected String password;

    public User(String id, String name, String role, String username, String password) {
        this.id = id;
        this.name = name;
        this.role = role;
        this.username = username;
        this.password = password;
    }


    public abstract boolean login(String inputUser, String inputPass);

    public void changePassword(String newPassword) {
        this.password = newPassword;
    }


    public String getId() { return id; }
    public String getName() { return name; }
    public String getRole() { return role; }
    public String getUsername() { return username; }
    public String getPassword() { return password; }
}
