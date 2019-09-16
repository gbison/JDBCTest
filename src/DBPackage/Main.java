package DBPackage;
import javax.swing.*;

class Main {
    public static void main(String[] args){

        JFrame frame = new JFrame("DB ConForm");
        frame.setContentPane(new ConForm().getDBConForm());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
