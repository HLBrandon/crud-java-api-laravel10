
package Model;

public class Student {
    
    private int id;
    private String first_name;
    private String last_name;
    private String email;
    private String password;
    private int age;
    private int career_id;
    private String career_name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
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

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getCareer_id() {
        return career_id;
    }

    public void setCareer_id(int career_id) {
        this.career_id = career_id;
    }

    public String getCareer_name() {
        return career_name;
    }

    public void setCareer_name(String career_name) {
        this.career_name = career_name;
    }
    
}
