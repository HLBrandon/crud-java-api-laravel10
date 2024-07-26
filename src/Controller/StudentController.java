package Controller;

import Model.ApiStudent;
import Model.Student;
import View.View;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

public class StudentController implements ActionListener {

    private Student student;
    private ApiStudent apiS;
    private View view;

    public StudentController(Student student, ApiStudent apiS, View view) {
        this.student = student;
        this.apiS = apiS;
        this.view = view;

        this.view.btnBuscar.addActionListener(this);
        this.view.btnCrear.addActionListener(this);
        this.view.btnEditar.addActionListener(this);
        this.view.btnEliminar.addActionListener(this);
        this.view.btnLimpiar.addActionListener(this);
    }

    public void run() {

        view.setTitle("Simple CRUD with API Laravel 10");
        view.setResizable(false);
        view.setLocationRelativeTo(null);
        view.setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == view.btnCrear) {
            student.setFirst_name(view.txt_firstName.getText());
            student.setLast_name(view.txt_lastName.getText());
            student.setEmail(view.txt_email.getText());
            student.setPassword(view.txt_password.getText());
            student.setAge(Integer.parseInt(view.txt_age.getText()));
            student.setCareer_id(Integer.parseInt(view.txt_career.getText()));
            if (apiS.create(student)) {
                JOptionPane.showMessageDialog(null, "Student Created with exit");
                limpiarCampos();
            } else {
                JOptionPane.showMessageDialog(null, "Error");
            }
        }

        if (e.getSource() == view.btnBuscar) {
            student.setId(Integer.parseInt(view.txt_id.getText()));
            if (apiS.show(student)) {
                view.txt_id.setText(String.valueOf(student.getId()));
                view.txt_firstName.setText(student.getFirst_name());
                view.txt_lastName.setText(student.getLast_name());
                view.txt_email.setText(student.getEmail());
                view.txt_age.setText(String.valueOf(student.getAge()));
                view.txt_career.setText(String.valueOf(student.getCareer_id()));
            } else {
                JOptionPane.showMessageDialog(null, "Student not exist");
                limpiarCampos();
            }
        }

        if (e.getSource() == view.btnEditar) {
            student.setId(Integer.parseInt(view.txt_id.getText()));
            student.setFirst_name(view.txt_firstName.getText());
            student.setLast_name(view.txt_lastName.getText());
            student.setEmail(view.txt_email.getText());
            student.setPassword(view.txt_password.getText());
            student.setAge(Integer.parseInt(view.txt_age.getText()));
            student.setCareer_id(Integer.parseInt(view.txt_career.getText()));
            if (apiS.update(student)) {
                JOptionPane.showMessageDialog(null, "Student Updated with exit");
                limpiarCampos();
            } else {
                JOptionPane.showMessageDialog(null, "Error");
            }
        }

        if (e.getSource() == view.btnEliminar) {
            student.setId(Integer.parseInt(view.txt_id.getText()));
            if (apiS.delete(student)) {
                JOptionPane.showMessageDialog(null, "Student Deleted");
            } else {
                JOptionPane.showMessageDialog(null, "Student not exist");
            }
            limpiarCampos();
        }

        if (e.getSource() == view.btnLimpiar) {
            limpiarCampos();
        }

    }

    public void limpiarCampos() {
        view.txt_id.setText("");
        view.txt_firstName.setText("");
        view.txt_lastName.setText("");
        view.txt_email.setText("");
        view.txt_password.setText("");
        view.txt_age.setText("");
        view.txt_career.setText("");
    }

}
