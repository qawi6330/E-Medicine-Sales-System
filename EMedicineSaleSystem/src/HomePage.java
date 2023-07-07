import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
public class HomePage extends JFrame { 
	private int userId;
	public HomePage(int userId) {
		this.userId=userId;
		setTitle("User Dashboard");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(500, 400);
		setLocationRelativeTo(null);
		setVisible(true);
 // Create menu bar
		JMenuBar menuBar = new JMenuBar();
 // Create menus
		JMenu userMenu = new JMenu("User");
		JMenu ordersMenu = new JMenu("Orders");
		JMenu drugsMenu = new JMenu("Available drugs");
 // Create menu items for Farmer menu
		JMenuItem viewOrdersItem = new JMenuItem("View Orders");
 // Create menu items for Farm menu
		JMenuItem orderInsertItem = new JMenuItem("Insert Order");
		JMenuItem orderUpdateItem = new JMenuItem("Update Order");
		JMenuItem orderDeleteItem = new JMenuItem("Delete Order");
		JMenuItem avDrugItem = new JMenuItem("View");
 // Add action listeners to menu items
		viewOrdersItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				viewOrdersDialog();
			}
		});
		orderInsertItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showInsertOrderDialog();
			}
		});
		orderUpdateItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showUpdateOrderDialog();
			}
		});
		orderDeleteItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showDeleteOrderDialog();
			}
		});
		avDrugItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new DrugsAv();
			}
		});
 // Add menu items to menus
		userMenu.add(viewOrdersItem);
		ordersMenu.add(orderInsertItem);
		ordersMenu.add(orderUpdateItem);
		ordersMenu.add(orderDeleteItem);
		drugsMenu.add(avDrugItem);
 // Add menus to menu bar
		menuBar.add(userMenu);
		menuBar.add(ordersMenu);
		menuBar.add(drugsMenu);
 // Set the menu bar for the frame
		setJMenuBar(menuBar);
	}
	private void viewOrdersDialog()
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
			ResultSet rs=stmt.executeQuery("select code,name,quantity from history_sales"); 
			StringBuilder sb = new StringBuilder();
			while(rs.next())
			{
				int x=rs.getInt(1);
				String m=rs.getString(2);
				int n=rs.getInt(3);
	 
				sb.append(x).append(": ").append(m).append(",").append(n).append(".\n");
			}
			JTextArea textArea = new JTextArea(sb.toString());
			textArea.setEditable(false);
			JScrollPane scrollPane = new JScrollPane(textArea);
			scrollPane.setPreferredSize(new Dimension(400, 300));
			JOptionPane.showMessageDialog(this, scrollPane, "Orders", JOptionPane.PLAIN_MESSAGE);
			con.close();
			System.out.println("Connection closed successfully.");
		} catch (ClassNotFoundException e) {
			System.err.println("Failed to load JDBC driver: " + e.getMessage());
		} catch (SQLException e) {
			System.err.println("Failed to connect to the database: " + e.getMessage());
		}
	 	}
	 	private void showInsertOrderDialog() {
	 		JPanel panel = new JPanel();
	 		panel.setLayout(new GridLayout(4, 2));
	 // Insert Farm components
	 		JLabel drugCodeLabel = new JLabel("Drug code");
	 		JTextField drugCodeField = new JTextField(20);
	 		JLabel drugNameLabel = new JLabel("Drug Name:");
	 		JTextField drugNameField = new JTextField(20);
	 		JLabel drugQuantityLabel = new JLabel("Drug quantity");
	 		JTextField drugQuantityField = new JTextField(20);
	 		JButton addOrderButton = new JButton("Add Order");
	 		addOrderButton.addActionListener(new ActionListener() {
	 			public void actionPerformed(ActionEvent e) {
	 				String drugCode = drugCodeField.getText();
	 				String drugName = drugNameField.getText();
	 				String drugQuantity = drugQuantityField.getText();
	 // Perform insertion logic here
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
	 					int a=stmt.executeUpdate("insert into history_sales(code,name,quantity) values("+drugCode+",'"+drugName+"',"+drugQuantity+")"); 
	 					if(a>0)
	 						JOptionPane.showMessageDialog(null, "Order added successfully", "Message", JOptionPane.INFORMATION_MESSAGE);
	 					else
	 						JOptionPane.showMessageDialog(null, "Order details should be unique", "Error", JOptionPane.ERROR_MESSAGE);
	 // Close the connection
	 					con.close();
	 					System.out.println("Connection closed successfully.");
	 				} catch (ClassNotFoundException s) {
	 					System.err.println("Failed to load JDBC driver: " + s.getMessage());
	 				} catch (SQLException s) {
	 					System.err.println("Failed to connect to the database: " + s.getMessage());
	 				}
	 // Clear the fields
	 				drugCodeField.setText("");
	 				drugNameField.setText("");
	 				drugQuantityField.setText("");
	 			}
	 		});
	 // Add components to the panel
	 		panel.add(drugCodeLabel);
	 		panel.add(drugCodeField);
	 		panel.add(drugNameLabel);
	 		panel.add(drugNameField);
	 		panel.add(drugQuantityLabel);
	 		panel.add(drugQuantityField);
	 		panel.add(new JLabel());
	 		panel.add(addOrderButton);
	 		JOptionPane.showOptionDialog(null, panel, "Insert Order", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, new Object[]{}, null);
	 	}
	 	private void showUpdateOrderDialog() {
	 		JPanel panel = new JPanel();
	 		panel.setLayout(new GridLayout(4, 2));
	 // Update Farm components
	 		JLabel drugCodeLabel = new JLabel("Drug Code");
	 		JTextField drugCodeField = new JTextField(20);
	 		JLabel drugNameLabel = new JLabel("Drug Name:");
	 		JTextField drugNameField = new JTextField(20);
	 		JLabel drugQuantityLabel = new JLabel("Drug Quantity");
	 		JTextField drugQuantityField = new JTextField(20);
	 		JButton updateOrderButton = new JButton("Update Order");
	 		updateOrderButton.addActionListener(new ActionListener() {
	 			public void actionPerformed(ActionEvent e) {
	 				String drugCode = drugCodeField.getText();
	 				String drugName = drugNameField.getText();
	 				String drugQuantity = drugQuantityField.getText();
	 // Perform update logic here
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
	 					int a = stmt.executeUpdate("UPDATE history_sales SET name='" + drugName + "',quantity ='" + drugQuantity + "'WHERE code=" +drugCode ); 
	 					if(a>0)
	 						JOptionPane.showMessageDialog(null, "Order updated successfully", "Message", JOptionPane.INFORMATION_MESSAGE);
	 					else
	 						JOptionPane.showMessageDialog(null, "Order not found", "Error", JOptionPane.ERROR_MESSAGE);
		 // Close the connection
	 					con.close();
	 					System.out.println("Connection closed successfully.");
	 				} catch (ClassNotFoundException s) {
	 					System.err.println("Failed to load JDBC driver: " + s.getMessage());
	 				} catch (SQLException s) {
	 					System.err.println("Failed to connect to the database: " + s.getMessage());
	 				}
		 // Clear the fields
	 				drugCodeField.setText("");
	 				drugNameField.setText("");
	 				drugQuantityField.setText("");
	 			}
	 		});
		 // Add components to the panel
	 		panel.add(drugCodeLabel);
	 		panel.add(drugCodeField);
	 		panel.add(drugNameLabel);
	 		panel.add(drugNameField);
	 		panel.add(drugQuantityLabel);
	 		panel.add(drugQuantityField);
	 		panel.add(new JLabel());
	 		panel.add(updateOrderButton);
	 		JOptionPane.showOptionDialog(null, panel, "Update Order", JOptionPane.DEFAULT_OPTION, 
	 				JOptionPane.PLAIN_MESSAGE, null, new Object[]{}, null);
		 	}
	 	private void showDeleteOrderDialog() {
	 		JPanel panel = new JPanel();
	 		panel.setLayout(new GridLayout(2, 2));
		 // Delete Farm components
	 		JLabel drugCodeLabel = new JLabel("Drug Code");
	 		JTextField drugCodeField = new JTextField(20);
	 		JButton deleteOrderButton = new JButton("Delete Order");
	 		deleteOrderButton.addActionListener(new ActionListener() {
	 			public void actionPerformed(ActionEvent e) {
	 				String drugCode = drugCodeField.getText();
		 // Perform delete logic here
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
	 					int a=stmt.executeUpdate("delete from history_sales where code="+drugCode+""); 
	 					if(a>0)
	 						JOptionPane.showMessageDialog(null, "Deleted successfully", "Message", JOptionPane.INFORMATION_MESSAGE);
	 					else
	 						JOptionPane.showMessageDialog(null, "Order not found", "Error", JOptionPane.ERROR_MESSAGE);
		 // Close the connection
	 					con.close();
	 					System.out.println("Connection closed successfully.");
	 				} catch (ClassNotFoundException s) {
	 					System.err.println("Failed to load JDBC driver: " + s.getMessage());
	 				} catch (SQLException s) {
	 					System.err.println("Failed to connect to the database: " + s.getMessage());
	 				}
		// Clear the field
	 				drugCodeField.setText("");
	 			}
	 		});
		 // Add components to the panel
	 		panel.add(drugCodeLabel);
	 		panel.add(drugCodeField);
	 		panel.add(new JLabel());
	 		panel.add(deleteOrderButton);
	 		JOptionPane.showOptionDialog(null, panel, "Delete Order", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, new Object[]{}, null);
		 	}
		 /*public static void main(String[] args) {
		 SwingUtilities.invokeLater(new Runnable() {
		 public void run() {
		 new Home().setVisible(true);
		 }
		 });
		 }*/
		}


