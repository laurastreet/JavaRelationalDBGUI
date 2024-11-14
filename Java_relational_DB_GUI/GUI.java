import java.awt.*;
import javax.swing.*;
import java.sql.*;
import java.io.*;
import java.util.*;

public class GUI {

    public GUI(){
        JFrame frame = new JFrame("Company Database");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));
        try {
            frame.getContentPane().add(new PrimaryPanel());
        }catch(SQLException | IOException e){
            System.out.println("Exception thrown.");
        }
        frame.setSize(600,600);
        frame.setVisible(true);
    }

}
