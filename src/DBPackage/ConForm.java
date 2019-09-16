package DBPackage;

import javax.swing.*;
import javax.swing.event.MenuDragMouseEvent;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ConForm {
    private JButton pressMeButton;
    private JTextField textField1;
    private JTextField textField2;
    private JPanel DBConForm;
    private JButton clearButton;
    private JTextArea textArea1;
    private JProgressBar progressBar1;
    private JButton writeToFileButton;

    public ConForm() {
        //Defaults
        textField1.setText("jdbc:sqlite:SQLiteDB.db");
        textField2.setText("select * from Employee;");

        pressMeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                progressBar1.setValue(20);
                SetupConnection();
            }
        });
        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                ResetConForm();
            }
        });
        writeToFileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                WriteToFile();
            }
        });
    }

    //Getters
    public JButton getPressMeButton() {
        return pressMeButton;
    }

    public JTextField getTextField1() {
        return textField1;
    }

    public JTextField getTextField2() {
        return textField2;
    }

    public JPanel getDBConForm() {
        return DBConForm;
    }

    public void SetupConnection()
    {
        System.out.println("Setting up connection....");

        //Instantiate our custom DB Connector based on the JDBC
        DBPackage.DBConnector dbconnection = new DBPackage.DBConnector();
        dbconnection.SetUrl(textField1.getText());     //SQLite
        dbconnection.SetQuery(textField2.getText());   //SQL statment

        //Attempt connection.
        if(dbconnection.connect()) {
            ResultSet results = dbconnection.GetResults();
            System.out.println("Connection Established Successfully!");
            String cols[] = {"Name", "Salary"};

            try{
                while (results.next()) {
                    for (String column:cols) {
                        textArea1.append(column + " : " + results.getString(column)+"\n");
                    }
                    textArea1.append("----------------------------------------------------------------"+"\n");
                    progressBar1.setValue(progressBar1.getValue() + 16);
                }
            }
            catch (SQLException err) {
                System.out.println(err.getMessage());
                err.printStackTrace();
            }

            dbconnection.DBClose();
        }
    }
    public void ResetConForm()
    {
        //textField2.setText("");
        //textField1.setText("");
        textArea1.removeAll();
        textArea1.setText("");
        progressBar1.setValue(0);
    }

    public boolean WriteToFile()
    {
        if(textArea1.getText().length() > 0) {
            File file = new File("DBText.txt");
            String mode = (file.exists())?"Append":"New";

            try {
                BufferedWriter writer = new BufferedWriter(new FileWriter("DBText.txt",true));
                String contents = textArea1.getText();
                System.out.println(contents);

                if(mode.equals("Append"))
                    writer.append(contents);
                else
                    writer.write(contents);

                writer.close();
                Desktop.getDesktop().open(file);
                return true;
            } catch (IOException err) {
                err.printStackTrace();
                System.out.println(err.getMessage());
            }

        }
        return false;
    }
}
