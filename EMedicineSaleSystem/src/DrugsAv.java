import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import javax.swing.table.DefaultTableModel;

public class DrugsAv extends JFrame {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private DefaultTableModel tableModel;
    private JTable drugTable;
    private JButton searchButton;

    public DrugsAv() {
        // Set up the JFrame
        setTitle("Available Drugs");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(600, 400));

        // Initialize the table model and JTable
        tableModel = new DefaultTableModel();
        tableModel.addColumn("Drug Code");
        tableModel.addColumn("Drug Name");
        tableModel.addColumn("Quantity");
		tableModel.addColumn("Dose");
        drugTable = new JTable(tableModel);

        // Set up the roll number input field and search button
        searchButton = new JButton("Search");

        // Add ActionListener to the search button
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchStudent();
            }
        });

        // Set up the layout and add the components
        JPanel topPanel = new JPanel();
        topPanel.add(searchButton);

        setLayout(new BorderLayout());
        add(topPanel, BorderLayout.NORTH);
        add(new JScrollPane(drugTable), BorderLayout.CENTER);

        // Display the JFrame
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void searchStudent() {

        // Clear the table before performing a new search
        tableModel.setRowCount(0);

        // Set up the database connection and retrieve student details
        try {
            Connection connection = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","qawi","qawi");
            PreparedStatement preparedStatement = connection.prepareStatement("Select * from drugs");
            //preparedStatement.setString(1, code);
            ResultSet resultSet = preparedStatement.executeQuery();
            boolean found = false;
            while (resultSet.next()) {
                String drugCode = resultSet.getString("code");
                String drugName = resultSet.getString("name");
                String quantity = resultSet.getString("quantity");
				String dose = resultSet.getString("dose");
                tableModel.addRow(new Object[]{drugCode, drugName,quantity,dose});
                found = true;
            }
            if (!found) {
                JOptionPane.showMessageDialog(this, "Not found", "Search Result", JOptionPane.INFORMATION_MESSAGE);
            }

            resultSet.close();
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
        	 e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new DrugsAv());
    }
}