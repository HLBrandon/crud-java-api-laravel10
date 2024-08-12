package Controller;

import Model.ApiStudent;
import Model.Student;
import Model.User;
import View.Login;
import View.View;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class StudentController implements ActionListener, MouseListener {

    private Student student;
    private User user;
    private ApiStudent apiS;
    private View view;
    private Login login;
    private DefaultTableModel modelTablaStudent;
    private int fila = -1;

    public StudentController(Student student, User user, ApiStudent apiS, View view, Login login) {
        this.student = student;
        this.user = user;
        this.apiS = apiS;
        this.view = view;
        this.login = login;

        this.view.tabla.addMouseListener(this);
        this.view.btnCrear.addActionListener(this);
        this.view.btnEditar.addActionListener(this);
        this.view.btnEliminar.addActionListener(this);
        this.view.btnLimpiar.addActionListener(this);
        this.view.btn_logout.addActionListener(this);

        this.login.btn_login.addActionListener(this);
    }

    public void run() {
        view.setTitle("Simple CRUD with API Laravel");
        view.setResizable(false);
        view.setLocationRelativeTo(null);
        view.txt_id.setVisible(false);
        view.btnEditar.setEnabled(false);
        view.btnEliminar.setEnabled(false);
        cargarTable(view.tabla);
        //login.setVisible(false);
        login.dispose();
        view.setVisible(true);
    }

    public void runLogin() {
        login.setTitle("Login");
        login.setResizable(false);
        login.setLocationRelativeTo(null);
        //view.setVisible(false);
        view.dispose();
        login.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == login.btn_login) {
            if (!"".equals(login.txt_email.getText()) && !"".equals(String.valueOf(login.txt_password.getPassword()))) {
                user.setEmail(login.txt_email.getText());
                user.setPassword(String.valueOf(login.txt_password.getPassword()));
                if (apiS.login(user)) {
                    limpiarCampos();
                    view.txt_user_name.setText(user.getName());
                    view.txt_user_email.setText(user.getEmail());
                    run();
                } else {
                    JOptionPane.showMessageDialog(null, "Incorrect email/password");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Complete the entire form");
            }
        }

        if (e.getSource() == view.btnCrear) {
            if (!"".equals(view.txt_firstName.getText()) && !"".equals(view.txt_lastName.getText()) && !"".equals(view.txt_email.getText()) && !"".equals(String.valueOf(view.txt_password.getPassword())) && !"".equals(view.txt_age.getText()) && !"".equals(view.txt_career.getText())) {
                student.setFirst_name(view.txt_firstName.getText());
                student.setLast_name(view.txt_lastName.getText());
                student.setEmail(view.txt_email.getText());
                student.setPassword(String.valueOf(view.txt_password.getPassword()));
                student.setAge(Integer.parseInt(view.txt_age.getText()));
                student.setCareer_id(Integer.parseInt(view.txt_career.getText()));
                System.out.println(student.getPassword());
                if (apiS.create(student)) {
                    cargarCreateRow(view.tabla);
                    JOptionPane.showMessageDialog(null, "Student Created successfully");
                    limpiarCampos();
                } else {
                    JOptionPane.showMessageDialog(null, "Error in execute");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Complete the entire form");
            }
        }

        if (e.getSource() == view.btnEditar) {
            if (!"".equals(view.txt_id.getText())) {
                if (!"".equals(view.txt_firstName.getText()) && !"".equals(view.txt_lastName.getText()) && !"".equals(view.txt_email.getText()) && !"".equals(view.txt_age.getText()) && !"".equals(view.txt_career.getText())) {
                    student.setId(Integer.parseInt(view.txt_id.getText()));
                    student.setFirst_name(view.txt_firstName.getText());
                    student.setLast_name(view.txt_lastName.getText());
                    student.setEmail(view.txt_email.getText());
                    student.setPassword(String.valueOf(view.txt_password.getPassword()));
                    student.setAge(Integer.parseInt(view.txt_age.getText()));
                    student.setCareer_id(Integer.parseInt(view.txt_career.getText()));
                    if (apiS.update(student)) {
                        view.tabla.setValueAt(student.getId(), fila, 0);
                        view.tabla.setValueAt(student.getFirst_name() + " " + student.getLast_name(), fila, 1);
                        view.tabla.setValueAt(student.getEmail(), fila, 2);
                        view.tabla.setValueAt(student.getAge(), fila, 3);
                        view.tabla.setValueAt(student.getCareer_name(), fila, 4);
                        JOptionPane.showMessageDialog(null, "Student Updated successfully");
                        limpiarCampos();
                    } else {
                        JOptionPane.showMessageDialog(null, "Error");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Complete the entire form");
                }
            } else {
                JOptionPane.showMessageDialog(null, "You must select a student");
            }
        }

        if (e.getSource() == view.btnEliminar) {
            if (!"".equals(view.txt_id.getText())) {
                int res = JOptionPane.showConfirmDialog(null, "Delete this Student?", "Delete", JOptionPane.YES_NO_OPTION);
                if (res == JOptionPane.YES_OPTION) {
                    student.setId(Integer.parseInt(view.txt_id.getText()));
                    if (apiS.delete(student)) {
                        cargarRemoveRow(view.tabla);
                        JOptionPane.showMessageDialog(null, "Student Deleted successfully");
                    } else {
                        JOptionPane.showMessageDialog(null, "The Student does not exist");
                    }
                    limpiarCampos();
                }
            } else {
                JOptionPane.showMessageDialog(null, "You must select a student");
            }
        }

        if (e.getSource() == view.btnLimpiar) {
            limpiarCampos();
        }
        
        if (e.getSource() == view.btn_logout) {
            if (apiS.logout(user)) {
                limpiarCampos();
                runLogin();
            } else {
                JOptionPane.showMessageDialog(null, "Error in execute");
            }
        }

    }

    public void limpiarCampos() {
        fila = -1;
        view.txt_id.setText("");
        view.txt_firstName.setText("");
        view.txt_lastName.setText("");
        view.txt_email.setText("");
        view.txt_password.setText("");
        view.txt_age.setText("");
        view.txt_career.setText("");
        view.btnCrear.setEnabled(true);
        view.btnEditar.setEnabled(false);
        view.btnEliminar.setEnabled(false);
        login.txt_email.setText("");
        login.txt_password.setText("");
    }

    public void cargarTable(JTable tabla) {
        modelTablaStudent = (DefaultTableModel) tabla.getModel();
        List<Student> lista = apiS.index(student);
        Object[] obj = new Object[5];
        for (int i = 0; i < lista.size(); i++) {
            obj[0] = lista.get(i).getId();
            obj[1] = lista.get(i).getFirst_name() + " " + lista.get(i).getLast_name();
            obj[2] = lista.get(i).getEmail();
            obj[3] = lista.get(i).getAge();
            obj[4] = lista.get(i).getCareer_name();
            modelTablaStudent.addRow(obj);
        }
        tabla.setModel(modelTablaStudent);
    }

    public void cargarCreateRow(JTable tabla) {
        modelTablaStudent = (DefaultTableModel) tabla.getModel();
        Object[] obj = new Object[5];
        obj[0] = student.getId();
        obj[1] = student.getFirst_name() + " " + student.getLast_name();
        obj[2] = student.getEmail();
        obj[3] = student.getAge();
        obj[4] = student.getCareer_name();
        modelTablaStudent.addRow(obj);
        tabla.setModel(modelTablaStudent);
    }

    public void cargarRemoveRow(JTable tabla) {
        modelTablaStudent = (DefaultTableModel) tabla.getModel();
        modelTablaStudent.removeRow(fila);
        tabla.setModel(modelTablaStudent);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource() == view.tabla) {
            fila = view.tabla.getSelectedRow();
            int id = view.tabla.getValueAt(fila, 0).hashCode();
            student.setId(id);
            if (apiS.show(student)) {
                view.txt_id.setText(String.valueOf(student.getId()));
                view.txt_firstName.setText(student.getFirst_name());
                view.txt_lastName.setText(student.getLast_name());
                view.txt_email.setText(student.getEmail());
                view.txt_age.setText(String.valueOf(student.getAge()));
                view.txt_career.setText(String.valueOf(student.getCareer_id()));
                view.btnCrear.setEnabled(false);
                view.btnEditar.setEnabled(true);
                view.btnEliminar.setEnabled(true);
            } else {
                JOptionPane.showMessageDialog(null, "The Student does not exist");
                limpiarCampos();
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

}
