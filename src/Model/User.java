
package Model;

public class User {
    private int id;
    private String name;
    private String email;
    private String password;
    private String access_token;
    private String type_token;

    public User() {
    }

    public User(int id, String name, String email, String password, String access_token, String type_token) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.access_token = access_token;
        this.type_token = type_token;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public String getType_token() {
        return type_token;
    }

    public void setType_token(String type_token) {
        this.type_token = type_token;
    }

    
    
    
}
