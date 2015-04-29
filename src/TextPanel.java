import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class TextPanel extends JPanel implements ActionListener, Observer {
	
	JLabel inText, outText, monthCounter;
	JButton submit;
	JTextField inField, outField;
	int in, out;
	NewDate currMonth, endMonth, startMonth;
	String nameFromButtonPanel, startMonthText, endMonthText;
	ResultSet rs = null;
	Database db = null;
	boolean changeMonth = false;
	
	public TextPanel() {
		setLayout(new GridLayout(3,2));
		//currMonth = startMonth;
		
		//db = new Database();
		//db.createTable();
		
		inText = new JLabel("Items Shipped: ");
		outText = new JLabel("Item Installed: ");
		//monthCounter = new JLabel("Month: " + currMonth.toString());
		monthCounter = new JLabel();
			
		submit = new JButton("Submit");
		submit.setVerticalTextPosition(AbstractButton.CENTER);
		submit.setHorizontalAlignment(AbstractButton.CENTER);
		submit.addActionListener(this);
		submit.setActionCommand("Submit");

		inField = new JTextField(10);
		outField = new JTextField(10);
			
		add(inText);
		add(inField);
		add(outText);
		add(outField);
		add(monthCounter);
		add(submit);
	}
	
	public void actionPerformed(ActionEvent e) {
		if(currMonth.compareTo(endMonth) != 0) {
		

			if(error_checking(inField.getText()) && error_checking(outField.getText()))
			{
				in = Integer.parseInt(inField.getText());
				out = Integer.parseInt(outField.getText());
				//inserts values in to database
				db.insert(currMonth.toString(), nameFromButtonPanel, out, in, (in - out));
				
				rs = db.select(currMonth.toString(), nameFromButtonPanel);
				ResultSet rs2 = db.select(currMonth.toString(), "Sensor Panel");
				if(changeMonth) {
					changeMonth = false;
					//monthCounter.setText("Month: " + currMonth.toString());
				}
				else {
					currMonth.incMonth();
					monthCounter.setText("Month: " + currMonth.toString());
				}
				
				try {
					while(rs2.next()) {
						String date = rs2.getString("DATE");
						String product = rs2.getString("PRODUCT");
						int in = rs2.getInt("SHIPMENTS");
						int out = rs2. getInt("INSTALLS");
						
						System.out.println("text " + date);
						System.out.println("text " + product);
						System.out.println("text " + in);
						System.out.println("text " + out);
					}
				} catch (SQLException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
				try {
					while (rs.next()) {
						String date = rs.getString("DATE");
						String product = rs.getString("PRODUCT");
						int in = rs.getInt("SHIPMENTS");
						int out = rs. getInt("INSTALLS");
						
						System.out.println("text " + date);
						System.out.println("text " + product);
						System.out.println("text " + in);
						System.out.println("text " + out);
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				System.out.println("NOT RESET");
			}
			else
				monthCounter.setText("Please check values");
		}
		
		else {
			if(error_checking(inField.getText()) && error_checking(outField.getText()))
			{
			in = Integer.parseInt(inField.getText());
			out = Integer.parseInt(outField.getText());
			db.insert(currMonth.toString(), nameFromButtonPanel, out, in, (in - out));
			
			
			//rs = db.select(currMonth.toString(), nameFromButtonPanel);
			
			monthCounter.setText("Month: " + currMonth.toString());
//			try {
//				while (rs.next()) {
//					
//					String date = rs.getString("DATE");
//					String product = rs.getString("PRODUCT");
//					int in = rs.getInt("SHIPMENTS");
//					int out = rs. getInt("INSTALLS");
//					
//					System.out.println("text " + date);
//					System.out.println("text " + product);
//					System.out.println("text " + in);
//					System.out.println("text " + out);
//				}
//			} catch (SQLException e1) {
//				// TODO Auto-generated catch block
//				e1.printStackTrace();
//			}
			resetMonthCount();
			System.out.println("RESET AGAIN");
			
			if(changeMonth)
				changeMonth = false;
			}
			else
				monthCounter.setText("Please check values");
		}
	}
	
	public int getProdIn() {
		return in;
	}
	
	public int getProdOut() {
		return out;
	}
	
	public void setProdIn() {
	    try{
	    	in = Integer.parseInt(inField.getText());
	    }catch(NumberFormatException e){
	    	monthCounter.setText("Please check values");
	    }
	}
	
	public void setProdOut() {
	    try{
	    	in = Integer.parseInt(outField.getText());
	    }catch(NumberFormatException e){
	    	monthCounter.setText("Please check values");
	    }
	}
	
	public JButton getButton() {
		return submit;
	}
	
	public void setMonthLabel(String string) {
		monthCounter.setText(string);
	}
	
	public NewDate getCurrMonth() {
		return currMonth;
	}
	
	public JTextField getInField() {
		return inField;
	}
	
	public JTextField getOutField() {
		return outField;
	}
	
	public String getNameFromButtonPanel() {
		return nameFromButtonPanel;
	}
	
	public Database getDatabase() {
		return db;
	}
	
	public void setCurrMonth(NewDate month) {
		currMonth = month;
	}
	public void resetMonthCount() {
		currMonth = new NewDate(startMonth.toString());
	}

	public void update(String startDate, String endDate) {
		// TODO Auto-generated method stub
		startMonth = new NewDate(startDate);
		startMonthText = startDate;
		endMonth = new NewDate(endDate);
		currMonth = new NewDate(startDate);
		monthCounter.setText("Month: " + currMonth.toString());
		db.insertDates(startDate, endDate);
	}

	public void update() {
		// TODO Auto-generated method stub
		//Blank Method
	}

	public void update(String string) {
		// TODO Auto-generated method stub
		nameFromButtonPanel = string;
		monthCounter.setText("Month: " + currMonth.toString());
	}

	public void updateMonthInfo(String buttonText, String monthText, boolean changeMonth) {
		// TODO Auto-generated method stub
		nameFromButtonPanel = buttonText;
		currMonth = new NewDate(monthText);
		this.changeMonth = changeMonth;
		System.out.println("Change month: " + currMonth.toString());
		monthCounter.setText("Month: " + currMonth.toString());
	}

	public void updateExisting(Database db) {
		// TODO Auto-generated method stub
		this.db = db;
		
		rs = db.selectDates();
		
		//Populates dates to avoid Nullpointer exception and allow for the builder class to function
		try {
			while(rs.next()) {
				String startDate = rs.getString("START_DATE");
				String endDate = rs.getString("END_DATE");
				
				startMonth = new NewDate(startDate);
				startMonthText = startDate;
				endMonth = new NewDate(endDate);
				currMonth = new NewDate(startDate);
				monthCounter.setText("Month: " + currMonth.toString());
				
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	public void updateNew(Database db) {
		// TODO Auto-generated method stub
		this.db = db;
	}
	public boolean error_checking(String value)
	{
		int number;
	    try{
	    	number = Integer.parseInt(value);
	    }catch(NumberFormatException e){
	    	monthCounter.setText("Please check values");
	    	return false;
	    }
	    
		if(number < 0)
			return false;
		
		return true;
	}
}
