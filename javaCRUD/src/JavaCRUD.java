import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.*;

public class JavaCRUD {
    private JPanel Main;
    private JTextField txtname;
    private JTextField txtprice;
    private JTextField txtqty;
    private JButton saveButton;
    private JButton deleteButton;
    private JButton updateButton;
    private JTextField txtpid;
    private JButton SEARCHButton;

    public static void main(String[] args) {
        JFrame frame = new JFrame("JavaCRUD");
        frame.setContentPane(new JavaCRUD().Main);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    Connection con;
    PreparedStatement pst;
    public JavaCRUD() {
        Connect();

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String name,price,qty;

                name = txtname.getText();
                price = txtprice.getText();
                qty = txtqty.getText();

                try {
                    pst = con.prepareStatement("insert into Products(pname,price,qty)values(?,?,?)");
                    pst.setString(1, name);
                    pst.setString(2, price);
                    pst.setString(3, qty);
                    pst.executeUpdate();
                    JOptionPane.showMessageDialog(null,"Record Added!!!!");

                    txtname.getText();
                    txtprice.getText();
                    txtqty.getText();
                    txtname.requestFocus();
                }

                catch (SQLException e1){
                    e1.printStackTrace();
                }


            }
        });
        SEARCHButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                try {
                    String pid = txtpid.getText();

                    pst = con.prepareStatement("select pname,price,qty from Products where pid = ?");
                    pst.setString(1, pid);
                    ResultSet rs = pst.executeQuery();

                    if (rs.next() == true) {
                        String name = rs.getString(1);
                        String price = rs.getString(2);
                        String qty = rs.getString(3);

                        txtname.setText(name);
                        txtprice.setText(price);
                        txtqty.setText(qty);

                    } else {
                        txtname.setText("");
                        txtprice.setText("");
                        txtqty.setText("");
                        JOptionPane.showMessageDialog(null,"Invalid Product ID!!!");
                    }
                }

                catch (SQLException e1){
                    e1.printStackTrace();
                }


            }
        });
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String pid,name,price,qty;

                name = txtname.getText();
                price = txtprice.getText();
                qty = txtqty.getText();
                pid = txtpid.getText();

                try {
                    pst = con.prepareStatement("update Products set pname = ?,price = ?,qty = ? where pid = ?");
                    pst.setString(1, name);
                    pst.setString(2, price);
                    pst.setString(3, qty);
                    pst.setString(4, pid);

                    pst.executeUpdate();
                    JOptionPane.showMessageDialog(null,"Record Updated!!!!");

                    txtname.setText("");
                    txtprice.setText("");
                    txtqty.setText("");
                    txtname.requestFocus();
                    txtpid.setText("");
                }

                catch (SQLException e1){
                    e1.printStackTrace();
                }




            }
        });
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

               String bid;

               bid = txtpid.getText();

                try {
                    pst = con.prepareStatement("delete from products where pid = ?");

                    pst.setString(1, bid);

                    pst.executeUpdate();
                    JOptionPane.showMessageDialog(null,"Record Deleted!!!!");

                    txtname.setText("");
                    txtprice.setText("");
                    txtqty.setText("");
                    txtname.requestFocus();
                    txtpid.setText("");
                }

                catch (SQLException e1){
                    e1.printStackTrace();
                }

            }
        });
    }



    public void Connect()
    {
        try{
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost/crud", "root", "");
            System.out.println("Success");
        }
        catch (ClassNotFoundException | SQLException ex)
        {
            ex.printStackTrace();
        }
    }
}
