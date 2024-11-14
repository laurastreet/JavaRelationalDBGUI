import java.sql.*;
import java.io.*;
import java.util.*;

public class JDBC_Pt1 {
    public static void main(String args[]) throws SQLException, IOException{
        //this stmt loads the JDBC driver in memory (supposedly)
        try{
            Class.forName("oracle.jdbc.driver.OracleDriver");
        }catch(ClassNotFoundException x) {
            System.out.println("Driver could not be loaded"   + x);
        }
        String dbacct, passwrd, lname, SSN;
        double salary,hours;
        salary = 0.0;
        hours = 0.0;
        dbacct = "lstreet";
        passwrd = "eetseety";
        lname = "";

        Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@artemis.vsnet.gmu.edu:1521/vse18c.vsnet.gmu.edu", dbacct, passwrd);
        String stmt1 = "select lname, SSN from employee, department where dname='Research' and dno=dnumber";
            //expected result = Narayan, Wong
        String stmt2 = "select lname,SSN,hours from employee,dept_locations,project,works_on where dlocation='Houston' and pname='ProductZ' " +
                "and dnumber=dnum and dno=dnumber and dno=dnum and pnumber=pno and ssn=essn";
                //DNO=1";
        //https://razorsql.com/articles/oracle_jdbc_connect.html
        System.out.println("connected.");
        Statement s = conn.createStatement();

      //  ResultSet rset = stmt.executeQuery("select 'Hello World' from dual");
        ResultSet result = s.executeQuery(stmt2);
        while(result.next()) {
            lname = result.getString(1);
            SSN = result.getString(2);
            hours = result.getDouble(3);
            System.out.println(lname + ", " + SSN + ", " + hours);
        }
        result.close();
        conn.close();
        System.out.println("Your JDBC installation is correct.");

        /**String stmt1 = "select Lname, Salary from Employee where SSn = ?";
        PreparedStatement p = conn.prepareStatement(stmt1);
        //	SSN = readEntry("enter a Social Security Number: ");
        p.clearParameters();
        p.setString(1,SSN);
        ResultSet r = p.executeQuery();
        while(r.next()) {
            lname = r.getString(1);
            //salary = r.getDouble(2);
            System.out.println(lname);
        }*/
    }

}
