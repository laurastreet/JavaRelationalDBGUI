import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;
import java.io.*;
import java.util.*;
import java.text.*;
import java.io.*;

public class PrimaryPanel extends JPanel{
    private JTextField [] empValues, projValues, depValues;
    private JLabel [] labels;
    private JPanel cards, comboBoxPane, ssnBPanel, empBPanel, projBPanel, depBPanel, printPanel;
    private DefaultListCellRenderer listRenderer;
    private JButton enterSSN, enterEmp, enterProj, enterDep,  printTables;
    private JComboBox cb;
    private String empSSN;
    private String [] empFields = {"fname", "minit", "lname", "ssn", "bdate", "address", "sex", "salary",
            "superssn", "dno"};
    private String [] projFields = {"pname", "hours"};
    private String [] depFields = {"essn", "dependent_name", "sex", "bdate", "relationship"};
    private double currHours;
    public static final Color LIGHT_RED = new Color(255, 102, 102);
    public static final Color LIGHT_BLUE = new Color(51,204,255);
    public static final Color LIGHT_GRAY = new Color(211,211,211);

    public PrimaryPanel() throws SQLException, IOException{
        try{
            File myObj = new File("/home/laura/Desktop/db_tables.txt");
            if(myObj.createNewFile())
                System.out.println("File created: " + myObj.getName());
            else
                System.out.println("File already exists");
        }catch(IOException ioe){System.out.println(ioe);}


        try{
            Class.forName("oracle.jdbc.driver.OracleDriver");
        }catch(ClassNotFoundException x) {
            System.out.println("Driver could not be loaded"   + x);
        }

        String dbacct, passwrd;
        boolean isManager = false;
        currHours = 0.0;
        dbacct = "lstreet";
        passwrd = "eetseety";
        Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@artemis.vsnet.gmu.edu:1521/vse18c.vsnet.gmu.edu", dbacct, passwrd);
        String [] comboBoxItems = {"SSN", "employee", "projects", "dependents", "print tables"};
        labels = new JLabel[10];
        empValues = new JTextField[10];
        projValues = new JTextField[4];
        depValues = new JTextField[5];

            //make buttons
        enterSSN = new JButton("Enter");
        enterEmp = new JButton("Add employee");
        enterProj = new JButton("Add project");
        enterDep = new JButton("Add dependent");
        printTables = new JButton ("Print database tables");
        ssnBPanel = new JPanel();
        ssnBPanel.add(enterSSN);
        empBPanel = new JPanel();
        empBPanel.add(enterEmp);
        projBPanel = new JPanel();
        projBPanel.add(enterProj);
        depBPanel = new JPanel();
        depBPanel.add(enterDep);
        printPanel = new JPanel();
        printPanel.add(printTables);

            //card1
        JPanel card1 = new JPanel();
        card1.setBackground(LIGHT_RED);
        JLabel ssn_label = new JLabel("Enter SSN: ");
        JTextField ssn = new JTextField(50);
        card1.add(ssn_label);
        card1.add(ssn);
        card1.add(ssnBPanel);

            //card2
        JPanel card2 = new JPanel();
        card2.setLayout(new BoxLayout(card2, BoxLayout.Y_AXIS));
        card2.setBackground(LIGHT_BLUE);
        for(int i=0; i<labels.length; i++) {
            labels[i] = new JLabel(empFields[i]);
            empValues[i] = new JTextField();
            card2.add(labels[i]);
            card2.add(empValues[i]);
        }
        card2.add(empBPanel);

            //card3
        JPanel card3 = new JPanel();
        card3.setLayout(new BoxLayout(card3, BoxLayout.Y_AXIS));
        card3.setBackground(Color.green);
        JPanel hours_panel = new JPanel();
        JLabel hours = new JLabel();

        hours.setText("current hours: " + Double.toString(currHours));
        hours_panel.add(hours);

        for(int i=0; i<projFields.length; i++) {
            labels[i] = new JLabel(projFields[i]);
            projValues[i] = new JTextField();
            card3.add(labels[i]);
            card3.add(projValues[i]);
        }
        card3.add(hours_panel);
        card3.add(projBPanel);


            //card4 - dependents
        JPanel card4 = new JPanel();
        card4.setLayout(new BoxLayout(card4, BoxLayout.Y_AXIS));
        card4.setBackground(Color.yellow);

        JRadioButton yes = new JRadioButton("Yes");
        JRadioButton no = new JRadioButton("No");
        Box box1 = Box.createHorizontalBox();
        box1.setAlignmentX(Component.CENTER_ALIGNMENT);
        box1.add(yes);
        box1.add(no);
        yes.setSelected(true);
        ButtonGroup group = new ButtonGroup();
        group.add(yes);
        group.add(no);
        card4.add(yes);
        card4.add(no);

        JPanel card4_subpanel = new JPanel();
        card4_subpanel.setLayout(new BoxLayout(card4_subpanel, BoxLayout.Y_AXIS));
        for(int i=0; i<5; i++) {
            labels[i] = new JLabel(depFields[i]);
            depValues[i] = new JTextField();
            card4_subpanel.add(labels[i]);
            card4_subpanel.add(depValues[i]);
        }
        card4_subpanel.add(depBPanel);
        card4.add(card4_subpanel);

            //ActionListeners
        yes.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                card4_subpanel.setVisible(true);
            }
        });
        no.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                card4_subpanel.setVisible(false);
            }
        });

        //card1
        JPanel card5 = new JPanel();
        card5.setLayout(new BoxLayout(card5, BoxLayout.Y_AXIS));
        card5.setBackground(LIGHT_GRAY);
        card5.add(printPanel);


            //set up cardLayout panel
        cards = new JPanel(new CardLayout());
        cards.add(card1, comboBoxItems[0]);
        cards.add(card2, comboBoxItems[1]);
        cards.add(card3, comboBoxItems[2]);
        cards.add(card4, comboBoxItems[3]);
        cards.add(card5, comboBoxItems[4]);

            //set up JComboBox
        cb = new JComboBox(comboBoxItems);
        cb.setSelectedIndex(0);
        listRenderer = new DefaultListCellRenderer();
        listRenderer.setHorizontalAlignment(DefaultListCellRenderer.CENTER);
        cb.setRenderer(listRenderer);

            //create a comboBoxPane
        comboBoxPane = new JPanel();
        comboBoxPane.add(cb);

        //set up primary panel
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(Color.blue);
        add(comboBoxPane);
        comboBoxPane.setVisible(false);
        add(cards);

        setPreferredSize(new Dimension(600, 600));


            //add EventListeners
        enterSSN.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String ssn_input = ssn.getText();
                String SSN;
                ArrayList<String> arr = new ArrayList<String>();
                boolean isManager = false;
                String stmt1 = "select mgrssn from department";

                try {
                    Statement s = conn.createStatement();

                    //  ResultSet rset = stmt.executeQuery("select 'Hello World' from dual");
                    ResultSet result = s.executeQuery(stmt1);

                    while (result.next()) {
                        SSN = result.getString(1);
                        arr.add(SSN);
                        //  System.out.println(SSN);
                    }
                } catch (SQLException sql_e) {
                    System.out.print("Exception thrown");
                }

                for (String DB_ssn : arr)
                    if (ssn_input.equals(DB_ssn)) {
                        isManager = true;
                        comboBoxPane.setVisible(true);
                        JOptionPane.showMessageDialog(card1, "Use drop-down menu to select database info.");
                    }
                System.out.println("isManager: " + isManager);
                if(!isManager){
                    JOptionPane.showMessageDialog(card1, "SSN does not authorize database modifications.");
                    System.exit(0);
                }
            }
        });


       // https://kodejava.org/how-do-i-add-an-action-listener-to-jcombobox/
        cb.addItemListener(new ItemListener(){
            public void itemStateChanged(ItemEvent e){
                CardLayout c1 = (CardLayout)(cards.getLayout());
                c1.show(cards, (String)e.getItem());
            }
        });

        enterEmp.addActionListener(new ActionListener(){
            //insert employee into database
            public void actionPerformed(ActionEvent e){

                String delete_query = "DELETE FROM works_on WHERE essn=?";
                String delete_query1 = "DELETE FROM dependent WHERE essn=?";
                String delete_query2 = "DELETE FROM employee WHERE ssn=?";
                try {
                    PreparedStatement ps = conn.prepareStatement(delete_query);
                    ps.setString(1,"567565678");
                    int n = ps.executeUpdate();
                    System.out.println("works_on deletion successful: " + n);
                    PreparedStatement ps1 = conn.prepareStatement(delete_query1);
                    ps1.setString(1,"567565678");
                    int n1 = ps1.executeUpdate();
                    System.out.println("dependent deletion successful: " + n1);
                    PreparedStatement ps2 = conn.prepareStatement(delete_query2);
                    ps2.setString(1,"567565678");
                    int n2 = ps2.executeUpdate();
                    System.out.println("employee deletion successful: " + n2);
                }catch(SQLException se){System.out.println(se);}

                    //change formats of selected attributes where necessary
                String empValues4;
                int empValues7,empValues9;
                empValues4 = empValues[4].getText(); //date
                java.sql.Date result = java.sql.Date.valueOf(empValues4);
                empValues7 = Integer.parseInt(empValues[7].getText()); //salary - number (java.math.BigDecimal)
                java.math.BigDecimal salary = java.math.BigDecimal.valueOf(empValues7);
                empValues9 = Integer.parseInt(empValues[9].getText()); //dno - number
                java.math.BigDecimal dno = java.math.BigDecimal.valueOf(empValues9);


                String query = "INSERT INTO employee VALUES(?,?,?,?,?,?,?,?,?,?,?)";
                try {
                    PreparedStatement ps = conn.prepareStatement(query);
                    ps.setString(1,empValues[0].getText());
                    ps.setString(2,empValues[1].getText());
                    ps.setString(3,empValues[2].getText());
                    ps.setString(4,empValues[3].getText());
                    ps.setDate(5,result);
                    ps.setString(6,empValues[5].getText());
                    ps.setString(7,empValues[6].getText());
                    ps.setBigDecimal(8,salary);
                    ps.setString(9,empValues[8].getText());
                    ps.setBigDecimal(10,dno);
                    ps.setString(11,null);
                    int n = ps.executeUpdate();
                    System.out.println("n: " + n);
                    if(n==1)
                        JOptionPane.showMessageDialog(card2, "Use drop-down menu to enter project info.");
                } catch (SQLException sql_e) {
                    System.out.print(sql_e);
                }
            }
        });

        enterProj.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){

                    //query to get remaining values (pnumber, plocation, dnum);
                    //combine prepareStatement with query
                String query0 = "SELECT pnumber FROM project WHERE pname = ?";
                String query1 = "INSERT INTO works_on VALUES(?,?,?)";
                java.math.BigDecimal pnumber = new java.math.BigDecimal(0);

                try {
                        //query DB to get pnum,plocation,dnum for corresponding user-entered pname
                    PreparedStatement ps0 = conn.prepareStatement(query0);
                    ps0.setString(1, projValues[0].getText());
                    ResultSet rs = ps0.executeQuery();
                    while(rs.next())
                        pnumber = rs.getBigDecimal(1);
                    System.out.println("pnumber: " + pnumber);

                        //insert works_on into DB
                    PreparedStatement ps1 = conn.prepareStatement(query1);
                    ps1.setString(1, empValues[3].getText()); //essn - String
                    ps1.setBigDecimal(2,pnumber); //pno - BigDecimal (equals pnumber)
                    double hrs = Double.parseDouble(projValues[1].getText());
                    ps1.setBigDecimal(3,java.math.BigDecimal.valueOf(hrs)); //hours - BigDecimal
                    if(currHours >= 40.0) {
                        JOptionPane.showMessageDialog(card3, "Maximum hours reached for this employee.");
                        return;     //breaks if currHours >= 40.0
                    }
                    currHours += hrs;

                    int n = ps1.executeUpdate();
                    System.out.println("updating Works_on successful: " + n);

                        //update employee hours on GUI
                    hours.setText("current hours: " + Double.toString(currHours));

                }catch(SQLException e1){System.out.println(e1);}
            }
        });

        enterDep.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                String query = "INSERT INTO dependent VALUES(?,?,?,?,?)";
                java.math.BigDecimal pnumber = new java.math.BigDecimal(0);

                try {
                        //update dependent
                    PreparedStatement ps0 = conn.prepareStatement(query);
                    ps0.setString(1, depValues[0].getText()); //essn - String
                    ps0.setString(2,depValues[1].getText()); //dependent_name - String
                    ps0.setString(3,depValues[2].getText()); //sex - String
                    java.sql.Date date = java.sql.Date.valueOf(depValues[3].getText());
                    ps0.setDate(4,date); //bdate - date
                    ps0.setString(5,depValues[4].getText()); //relationship - String
                    int n3 = ps0.executeUpdate();
                    System.out.println("dependent successfully updated: "+ n3);
                }catch(SQLException sql_e){System.out.println(sql_e);}
            }
        });

        printTables.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                try {
                    FileWriter myWriter = new FileWriter("/home/laura/Desktop/db_tables.txt");
                    String query = "SELECT * FROM employee";
                    try {
                        Statement st = conn.createStatement();
                        ResultSet rs = st.executeQuery(query);
                        ResultSetMetaData rsmd = rs.getMetaData();
                        int col_count = rsmd.getColumnCount();
                        myWriter.write("Employee Table\n");
                        System.out.println("Employee Table: ");
                        while (rs.next()) {
                            for (int i = 1; i < col_count; i++) {
                                myWriter.write(rs.getString(i) + " ");
                                System.out.print(rs.getString(i) + " ");
                            }
                            myWriter.write("\n");
                            System.out.println();
                        }

                        String query1 = "SELECT * FROM works_on";
                        Statement st1 = conn.createStatement();
                        ResultSet rs1 = st.executeQuery(query1);
                        ResultSetMetaData rsmd1 = rs1.getMetaData();
                        int col_count1 = rsmd.getColumnCount();
                        myWriter.write("\nWorks_on Table\n");
                        System.out.println("Works_on Table: ");
                        while (rs1.next()) {
                            for (int i = 1; i < col_count1; i++) {
                                myWriter.write(rs1.getString(i) + " ");
                                System.out.print(rs1.getString(i) + " ");
                            }
                            myWriter.write("\n");
                            System.out.println();
                        }

                        String query2 = "SELECT * FROM dependent";
                        Statement st2 = conn.createStatement();
                        ResultSet rs2 = st.executeQuery(query2);
                        ResultSetMetaData rsmd2 = rs2.getMetaData();
                        int col_count2 = rsmd.getColumnCount();
                        myWriter.write("\nDependent Table\n");
                        System.out.println("Dependent Table: ");
                        while (rs2.next()) {
                            for (int i = 1; i < col_count2; i++) {
                                myWriter.write(rs2.getString(i) + " ");
                                System.out.print(rs2.getString(i) + " ");
                            }
                            myWriter.write("\n");
                            System.out.println();
                        }
                    } catch (SQLException exception) {System.out.println(exception);}
                    myWriter.close();
                }catch(IOException io_e){System.out.println(io_e);}
            }
        });
    }

//
//    public void paintComponent(Graphics page) {
//        super.paintComponent(page);
//        page.setColor(Color.green);
//    }

}