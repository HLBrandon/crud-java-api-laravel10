
package crudapimvc;

import Controller.StudentController;
import Model.ApiStudent;
import Model.Student;
import View.View;

public class CrudApiMVC {

    public static void main(String[] args) {
        
        View vista = new View();
        Student student = new Student();
        ApiStudent apiS = new ApiStudent();
        StudentController studentController = new StudentController(student, apiS, vista);
        
        studentController.run();
        
        
    }
    
}
