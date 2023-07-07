import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class emedicine_main extends JFrame {
    private JTextField signUpUserIdField;
    private JTextField signUpUserNameField;
    private JTextField signUpAddressField;
    private JPasswordField signUpPasswordField;
	private JTextField signUpPhoneField;
    private JTextField loginUserIdField;
    private JPasswordField loginPasswordField;

    // Database to store player information (In-memory storage for simplicity)
    //private Map<String, Player> playerDatabase;

    public emedicine_main() {
        // Initialize the database
        //playerDatabase = new HashMap<>();

        setTitle("E-Medicine Sales System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);

        // Create panels for sign up and login components
        JPanel signUpPanel = createSignUpPanel();
        JPanel loginPanel = createLoginPanel();

        // Create tabbed pane to switch between sign up and login
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Sign Up", signUpPanel);
        tabbedPane.addTab("Login", loginPanel);

        // Add tabbed pane to the frame
        getContentPane().add(tabbedPane);
    }

    private JPanel createSignUpPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(8, 2));

        // Sign Up components
        JLabel signUpUserIdLabel = new JLabel("User ID:");
        signUpUserIdField = new JTextField(20);
        JLabel signUpUserNameLabel = new JLabel("User Name:");
        signUpUserNameField = new JTextField(20);
        JLabel signUpAddressLabel = new JLabel("Address:");
        signUpAddressField = new JTextField(20);
        JLabel signUpPasswordLabel = new JLabel("Password:");
        signUpPasswordField = new JPasswordField(20);
		JLabel signUpPhoneLabel = new JLabel("Phone:");
        signUpPhoneField = new JTextField(20);
		JButton signUpButton = new JButton("Sign up");

        // Sign Up button action listener
        signUpButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String userId = signUpUserIdField.getText();
                String userName = signUpUserNameField.getText();
                String address = signUpAddressField.getText();
                String password = new String(signUpPasswordField.getPassword());
				String phone = signUpPhoneField.getText();


                if (userId.isEmpty() || userName.isEmpty() || address.isEmpty() || password.isEmpty() || phone.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Please fill all the details", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                else
                {
                	        String driverClassName = "oracle.jdbc.driver.OracleDriver";
                	        String url = "jdbc:oracle:thin:@localhost:1521:xe";
                	        String username = "qawi";
                	        String pass = "qawi";

                	        try {
                	            // Load the JDBC driver
                	            Class.forName(driverClassName);

                	            // Establish a connection to the database
                	            Connection con = DriverManager.getConnection(url, username, pass);

                	            // Perform database operations using the connection
                				Statement stmt=con.createStatement();  
                				int a=stmt.executeUpdate("insert into users values('"+userId+"','"+password+"','"+phone+"','"+userName+"','"+address+"')");  
                				if(a>0)
                					JOptionPane.showMessageDialog(null, "Sign up successfull", "Message", JOptionPane.INFORMATION_MESSAGE);
                				else
                					JOptionPane.showMessageDialog(null, "Failed", "Error", JOptionPane.ERROR_MESSAGE);
                	            // Close the connection
                	            con.close();

                	            System.out.println("Connection closed successfully.");
                	        } catch (ClassNotFoundException c) {
                	            System.err.println("Failed to load JDBC driver: " + c.getMessage());
                	        } catch (SQLException c) {
                	            System.err.println("Failed to connect to the database: " + c.getMessage());
                	        }
                	    }
         
         
                JOptionPane.showMessageDialog(null, "Sign In Successful", "Success", JOptionPane.INFORMATION_MESSAGE);


                // Clear the sign up fields
                signUpUserIdField.setText("");
                signUpUserNameField.setText("");
                signUpAddressField.setText("");
				signUpPhoneField.setText("");
                signUpPasswordField.setText("");

                JOptionPane.showMessageDialog(null, "Sign up successful!");
            }
        });
        panel.add(signUpUserIdLabel);
        panel.add(signUpUserIdField);
        panel.add(signUpUserNameLabel);
        panel.add(signUpUserNameField);
        panel.add(signUpAddressLabel);
        panel.add(signUpAddressField);
        panel.add(signUpPasswordLabel);
        panel.add(signUpPasswordField);
		panel.add(signUpPhoneLabel);
        panel.add(signUpPhoneField);
        panel.add(new JLabel());
        panel.add(signUpButton);
		return panel;
    }

    private JPanel createLoginPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(6, 2));

        // Login components
        JLabel loginUserIdLabel = new JLabel("User ID:");
        loginUserIdField = new JTextField(20);
        JLabel loginPasswordLabel = new JLabel("Password:");
        loginPasswordField = new JPasswordField(20);

        JButton loginButton = new JButton("Login");
		
        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String userId = loginUserIdField.getText();
                String password = new String(loginPasswordField.getPassword());

                if (userId.isEmpty() || password.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Please enter ID and password", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                else {
                String driverClassName = "oracle.jdbc.driver.OracleDriver";
                String url = "jdbc:oracle:thin:@localhost:1521:xe";
                String username = "qawi";
                String pass = "qawi";

                try {
                    // Load the JDBC driver
                    Class.forName(driverClassName);
                    
                    // Establish a connection to the database
                    Connection con = DriverManager.getConnection(url, username, pass);

                    // Perform database operations using the connection
        			Statement stmt=con.createStatement();  
        			ResultSet rs=stmt.executeQuery("select password from users where id="+userId+"and password='"+password+"'");  
        			if(rs.next())
        			{
        					
        				JOptionPane.showMessageDialog(null, "Login is Successful", "Success", JOptionPane.INFORMATION_MESSAGE);
        				setVisible(false);
        				System.out.print("Successfull");
        				new UserManage();
        			}
        			else
        				JOptionPane.showMessageDialog(null, "Fail", "Error", JOptionPane.ERROR_MESSAGE);
                    // Close the connection
                    con.close();

                    System.out.println("Connection closed successfully.");
                } catch (ClassNotFoundException c) {
                    System.err.println("Failed to load JDBC driver: " + c.getMessage());
                } catch (SQLException c) {
                    System.err.println("Failed to connect to the database: " + c.getMessage());
                }
            }

                // Clear the login fields
                loginUserIdField.setText("");
                loginPasswordField.setText("");
            }
        });

        // Add components to the login panel
        panel.add(loginUserIdLabel);
        panel.add(loginUserIdField);
        panel.add(loginPasswordLabel);
        panel.add(loginPasswordField);
        panel.add(new JLabel()); // Empty label for spacing
        panel.add(loginButton);

        return panel;
    }


   /* public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new emedicine_main().setVisible(true);
            }
        });*/
    
public static void main(String[] args) {
    SwingUtilities.invokeLater(new Runnable() {
        public void run() {
            new emedicine_main().setVisible(true);
        }
    });
}
}