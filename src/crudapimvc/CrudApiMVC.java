
package crudapimvc;

import Controller.StudentController;
import Model.ApiStudent;
import Model.Student;
import Model.User;
import View.Login;
import View.View;

public class CrudApiMVC {

    public static void main(String[] args) {
        
        View vista = new View();
        Login login = new Login();
        Student student = new Student();
        User user = new User();
        ApiStudent apiS = new ApiStudent();
        StudentController studentController = new StudentController(student, user, apiS, vista, login);
        
        studentController.runLogin();
        
        
    }
    
}
