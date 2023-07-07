import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.table.DefaultTableModel;
public class UserManage extends JFrame{
	
	private DefaultTableModel tableModel;
    private JTable studentTable;
    private JTextField rollNumberField=new JTextField(20);
    private JButton searchButton;
	private JLabel code=new JLabel(" code:");
	private JTextField code_txt=new JTextField(20);
	private JLabel drug_name=new JLabel("drug Name:");
	private JTextField drug_name_txt=new JTextField(20);
	private JLabel dose=new JLabel("dose:");
	private JTextField dose_txt=new JTextField(20);
	private JLabel qty=new JLabel("quantity:");
	private JTextField qty_txt=new JTextField(20);
	private JButton submit=new JButton("Insert");
	private JButton modify=new JButton("Modify");
	private JButton delete=new JButton("Delete");
	private JMenuBar menubar=new JMenuBar();
	private JButton Avdrugs=new JButton("Show Drugs");
	private JPanel p1;
	private JPanel p2;
	private JPanel p3;
	private JPanel p4;
	private JPanel p5;
	private JPanel p6;
	private JPanel p7;
	private JPanel p8;
	private JPanel p9;
	
	public UserManage()
	{
		setTitle("Dashboard");
		setSize(700,300);
		setVisible(true);
		setLayout(new FlowLayout(0,1, getDefaultCloseOperation()));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		/*menubar.add(ipm);*/
     	menubar.add(Avdrugs);
		setJMenuBar(menubar);
		p1=new JPanel();
		p2=new JPanel();
		p3=new JPanel();
		p4=new JPanel();
		p5=new JPanel();
		p6=new JPanel();
		p7=new JPanel();
		p8=new JPanel();
		p9=new JPanel();
		p1.add(code);
		p1.add(code_txt);
		p1.add(drug_name);
		p1.add(drug_name_txt);
		p2.add(dose);
		p2.add(dose_txt);
		p2.add(qty);
		p2.add(qty_txt);
		p7.add(submit);
		p7.add(modify);
		p7.add(delete);
		add(p1);
		add(p2);
		add(p3);
	    add(p4);
	    add(p5);
	    add(p6);
	    add(p7);
	    
		try{
		Class.forName("oracle.jdbc.OracleDriver");
			Connection con= DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","qawi","qawi");
			Statement stmt=con.createStatement();
		}
		catch(SQLException e){System.out.println(e);}
	catch(Exception ex){System.out.println(ex);}
		submit.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){			
			try{
				Connection conn= DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","qawi","qawi");
			Statement stmt=conn.createStatement();
				String sql = "INSERT INTO drugs(code, name, dose, quantity) " +
             "VALUES ('" + code_txt.getText() + "', '" + drug_name_txt.getText() + "', '" + dose_txt.getText() + "', '" + qty_txt.getText() + "')";
				
			int a=stmt.executeUpdate(sql);
			if(a>0)
				JOptionPane.showMessageDialog(null, "Insertion successfull", "Message", JOptionPane.INFORMATION_MESSAGE);
			else
				JOptionPane.showMessageDialog(null, "Failed", "Error", JOptionPane.ERROR_MESSAGE);

				//conn.commit();
			}
			catch(SQLException sqle)
			{
				System.out.println("Could not insert tuple"+sqle);
				JOptionPane.showMessageDialog(null, "Fill all the fields", "Error", JOptionPane.ERROR_MESSAGE);
			}
			
	catch(Exception ex){System.out.println(ex);}
			}
				});
		modify.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        try {
		            Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "qawi", "qawi");

		            // Start the transaction
		            conn.setAutoCommit(false);

		            String updateStudentQuery = "UPDATE drugs SET name=?, dose=?, quantity=? WHERE code=?";
		            try (PreparedStatement updateStudentStmt = conn.prepareStatement(updateStudentQuery))
		                {

		                updateStudentStmt.setString(1, code_txt.getText());
		                updateStudentStmt.setString(2, drug_name_txt.getText());
		                updateStudentStmt.setString(3, dose_txt.getText());
		                updateStudentStmt.setString(4, qty_txt.getText());
		 
                        
		                
		                
		                updateStudentStmt.executeUpdate();
		                conn.commit();
		                JOptionPane.showMessageDialog(null, "Data updated successfully!");
		            } catch (SQLException ex) {
		                // Rollback the transaction if any update fails
		                conn.rollback();
		                ex.printStackTrace();
		                JOptionPane.showMessageDialog(null, "Failed to update data!");
		            } finally {
		                // Enable auto-commit after the transaction
		                conn.setAutoCommit(true);
		            }
		        } catch (SQLException ex) {
		            ex.printStackTrace();
		        }
		    }
		});


			
           
           

				//conn.commit();
			
		    delete.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
			try{
				Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "qawi", "qawi");
            Statement stmt = conn.createStatement();
            String sql = "DELETE FROM drugs WHERE code='" + code_txt.getText()+"'";
            int a=stmt.executeUpdate(sql);
            if(a>0)
				JOptionPane.showMessageDialog(null, "Delete successfull", "Message", JOptionPane.INFORMATION_MESSAGE);
            else
				JOptionPane.showMessageDialog(null, "Enter the drug code to delete", "Error", JOptionPane.ERROR_MESSAGE);
            conn.close();
			}
			catch(SQLException sqle)
			{
				System.out.println("Could not delete tuple"+sqle);
				JOptionPane.showMessageDialog(null, "Delete failed", "Error", JOptionPane.ERROR_MESSAGE);
			}
	catch(Exception ex){
		System.out.println(ex);
		JOptionPane.showMessageDialog(null, "Delete failed", "Error", JOptionPane.ERROR_MESSAGE);
		}
			}
		});
		
		Avdrugs.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
               // Show all the questions
            	
			        //Set up the JFrame
            	 new DrugsAv();
            
            }
		} );
	}
		


public static void main(String args[]){
	SwingUtilities.invokeLater(new Runnable(){// Run the GUI  in the Event-Dispatching thread for thread-safety
		public void run(){new UserManage();}
		});
}
		}