import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.security.interfaces.ECPublicKey;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import java.io.File; 
import java.io.IOException;
import java.util.Calendar;
import java.util.Date; 

import jxl.*; 
import jxl.write.*;
import jxl.write.Number;
import jxl.write.biff.RowsExceededException;


public class PartsInputGUI extends JFrame implements ActionListener, Observer{
	
	Container contentPane;
	ButtonsPanel buttonPane;
	TextPanel textPane;
	TextPanel2 firstPane;
	openDBPanel openDBPane;
	JButton submit, submit2, openExisting, openNew;
	JButton[] buttonArray;
	InventoryBuilder builder;
	InventoryPartsList list;
	boolean changeMonth = false;
	Database db;
	ResultSet rs = null;
	
	public PartsInputGUI(ButtonsPanel buttonPane, TextPanel textPane, TextPanel2 firstPane, openDBPanel openDBPane) throws IOException {
		this.buttonPane = buttonPane;
		this.textPane = textPane;
		this.firstPane = firstPane;
		this.openDBPane = openDBPane;
		
		//db = textPane.getDatabase();
		
		firstPane.registerObserver(textPane);
		buttonPane.registerObserver(textPane);
		openDBPane.registerObserver(textPane);
		openDBPane.registerObserver(this);
		
		builder = new InventoryBuilder();
		
		submit = textPane.getButton();
		submit2 = firstPane.getButton();
		openExisting = openDBPane.getExistingButton();
		openNew = openDBPane.getNewButton();
		buttonArray = buttonPane.getButtonArray();
		
		contentPane = getContentPane();
		submit.addActionListener(this);
		submit2.addActionListener(this);
		openExisting.addActionListener(this);
		openNew.addActionListener(this);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		for(int x = 0; x < buttonArray.length; x++) {
			buttonArray[x].addActionListener(this);
		}
		
		contentPane.add(openDBPane);
        pack();
        setVisible(true);
	}
	
	//Handle changing what is viewed in the GUI
	public void setPane(JPanel panel) {
        
        contentPane.removeAll();
        contentPane.add(panel);
        pack();
        setVisible(true);
	}

	public void actionPerformed(ActionEvent e) {

		String action = e.getActionCommand();
		
		if(action.equals("existing")) {
			setPane(buttonPane);
			System.out.println("OPEN1");
			//Sets populates parts list to avoid nullpointer exceptions
			//firstPane.setMaxMonths();
			
		}
		if(action.equals("new")) {
			setPane(firstPane);
			System.out.println("NEW1");
		}
		
		if(action.equals("Submit2")) {
			
			//Sets populates parts list to avoid nullpointer exceptions
			firstPane.setMaxMonths();
			System.out.println(firstPane.getMaxMonths());
			builder.setSensorPanel(buttonArray[0].getText(), firstPane.getMaxMonths());
			builder.setJHMCSPanel(buttonArray[1].getText(), firstPane.getMaxMonths());
			builder.setHydraulicPanel(buttonArray[2].getText(), firstPane.getMaxMonths());
			builder.setBitControlPanel(buttonArray[3].getText(), firstPane.getMaxMonths());
			builder.setInstrumentPanel(buttonArray[4].getText(), firstPane.getMaxMonths());
			builder.setInstrumentPanelKits(buttonArray[5].getText(), firstPane.getMaxMonths());
			builder.setGlareShield(buttonArray[6].getText(), firstPane.getMaxMonths());
			builder.setWireHarnessRobins(buttonArray[7].getText(), firstPane.getMaxMonths());
			builder.setWireHarnessUnicor(buttonArray[8].getText(), firstPane.getMaxMonths());
			builder.setMiscKit(buttonArray[9].getText(), firstPane.getMaxMonths());
			setPane(buttonPane);
			//textPane.setCurrMonth(firstPane.getStartDate());
			
		}
		
		
		if(action.equals("buttonPane")) {
			if(!buttonPane.getEditMonthField().getText().equals("")) {
				if(firstPane.getMonthMap().get(buttonPane.getEditMonthField().getText().toString()) != null)
				{
					changeMonth = true;
					setPane(textPane);
				}
				else
					JOptionPane.showMessageDialog(this, "Not a valid month, please enter a valid month");
			}
			else
				setPane(textPane);
 
		}
		
		//Outputs to an Excel file
		if(action.equals("output")) {
			WritableWorkbook workbook = null;
			WritableSheet sheet = null;
			int cellWidth = 35;
			try {
				workbook = Workbook.createWorkbook(showSaveFileDialog());
				//showSaveFileDialog();
				sheet = workbook.createSheet("First Sheet", 0);
			} catch (IOException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			
			list = builder.build();
			Part[] partsList = list.getList();
			
			
			//System.out.println(list.getList()[0].getMonthlyTotalsArray()[0]);
			
			//Adds the item names to the spreadsheet
			for(int row = 0; row < list.getList().length; row++) {
				Label label = new Label(0, row+1, list.getList()[row].getName());
				try {
					sheet.addCell(label);
				} catch (WriteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			
			//Adds dates to excel sheet and formats cells
			NewDate startDate = new NewDate(firstPane.getStartDate().toString());
			NewDate endDate = new NewDate(firstPane.getEndDate().toString());
			
			sheet.setColumnView(0, cellWidth);
			
			int column = 1;
			System.out.println(startDate.toString() + " excel");
			while(startDate.compareTo(endDate) != 2) {
				
				Label label = new Label(column, 0, startDate.toString() + " (Shipped - Installed = Total)");
				try {
					sheet.setColumnView(column, cellWidth);
					sheet.addCell(label);
				} catch (RowsExceededException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (WriteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				startDate.incMonth();
				column++;
			}
			
			Label cummulativeLabel = new Label(firstPane.getMaxMonths() + 1, 0, "Cummulative");
			try {
				sheet.setColumnView(firstPane.getMaxMonths() + 1, cellWidth);
				sheet.addCell(cummulativeLabel);
			} catch (RowsExceededException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			} catch (WriteException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			for(int col = 1; col <= firstPane.getMaxMonths(); col++) {
				String date = firstPane.getKeyFromValue(col);
				System.out.println("OUTPUT TO EXCEL " + date);
				for(int row = 0; row < partsList.length; row++) {
					String product = partsList[row].getName();
					System.out.println("PrintProd: " + date);
					System.out.println("PrintProd: " + product);
					rs = db.select(date, product);
					try {
						while(rs.next()) {
							String monthlyTotal = Integer.toString(rs.getInt("MONTH_TOTAL"));
							String installed = Integer.toString(rs.getInt("INSTALLS"));
							String shipped = Integer.toString(rs.getInt("SHIPMENTS"));
							Label label = new Label(col, row+1, (shipped + " - " + installed + " = " + monthlyTotal));
							sheet.addCell(label);
						}
					} catch (SQLException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					} catch (RowsExceededException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (WriteException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
			
			//adds cumulative total after cycle
			int sum = 0;
			for(int row = 0; row < partsList.length; row++) {
				
				rs = db.selectProduct(partsList[row].getName());
				
				//finds cumulative amount
				try {
					while(rs.next()) {
						
						int monthlyTotal = rs.getInt("MONTH_TOTAL");
						sum += monthlyTotal; 
					}
				} catch (SQLException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
				Number cumNumber = new Number(firstPane.getMaxMonths() + 1, row+1, sum);
				try {
					sheet.addCell(cumNumber);
				} catch (RowsExceededException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (WriteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				sum = 0;
			}
			//creates all necessary cells in the spreadsheet

			try {
				workbook.write();
				workbook.close();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (WriteException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
		if(action.equals("Submit") && textPane.getCurrMonth().compareTo(firstPane.getEndDate()) == 0) {
			textPane.setProdIn();
			textPane.setProdOut();
			
			
			
			
			setPane(buttonPane);
			setBuilderValues();
			
			//textPane.resetMonthCount();
			//System.out.println("RESET");
		}
		
		else if(action.equals("Submit") && changeMonth) {
			if(firstPane.getMonthMap().get(textPane.getCurrMonth().toString()) != null)
			{
				textPane.setProdIn();
				textPane.setProdOut();
				
				
				setBuilderValues();
				setPane(buttonPane);
				
				//textPane.resetMonthCount();
				
				changeMonth = false;
				buttonPane.getEditMonthField().setText("");
			}

		}
		
		else if(action.equals("Submit")){
			textPane.setProdIn();
			textPane.setProdOut();
			
			setBuilderValues();
			
		}
	}
	
	public void setBuilderValues() {
		
		//System.out.println(button.getActionCommand());
		//builder.setSensorPanel(button.getText(), firstPane.getMaxMonths());
		if(textPane.getNameFromButtonPanel().equals(buttonArray[0].getText())) {		
			setBuilderValuesHelper(builder.getSensorPanel());
		}
		else if(textPane.getNameFromButtonPanel().equals(buttonArray[1].getText())) {		
			setBuilderValuesHelper(builder.getJHMCSPanel());
		}
		else if(textPane.getNameFromButtonPanel().equals(buttonArray[2].getText())) {		
			setBuilderValuesHelper(builder.getHydraulicPanel());
		}
		else if(textPane.getNameFromButtonPanel().equals(buttonArray[3].getText())) {		
			setBuilderValuesHelper(builder.getBitControlPanel());
		}
		else if(textPane.getNameFromButtonPanel().equals(buttonArray[4].getText())) {		
			setBuilderValuesHelper(builder.getInstrumentPanel());
		}
		else if(textPane.getNameFromButtonPanel().equals(buttonArray[5].getText())) {		
			setBuilderValuesHelper(builder.getInstrumentPanelKits());
		}
		else if(textPane.getNameFromButtonPanel().equals(buttonArray[6].getText())) {		
			setBuilderValuesHelper(builder.getGlareShield());
		}
		else if(textPane.getNameFromButtonPanel().equals(buttonArray[7].getText())) {		
			setBuilderValuesHelper(builder.getWireHarnessRobins());
		}
		else if(textPane.getNameFromButtonPanel().equals(buttonArray[8].getText())) {		
			setBuilderValuesHelper(builder.getWireHarnessUnicor());
		}
		else if(textPane.getNameFromButtonPanel().equals(buttonArray[9].getText())) {		
			setBuilderValuesHelper(builder.getMiscKit());
		}
		
	}
	
	public void setBuilderValuesHelper(Part part) {
		int currMonthNum = 0;
		if(firstPane.getMonthMap().get(textPane.getCurrMonth().toString()) != null)
		{
			currMonthNum = firstPane.getMonthMap().get(textPane.getCurrMonth().toString());
			System.out.println(textPane.getCurrMonth().toString());
			//System.out.println(textPane.getCurrMonth().toString());
			System.out.println("CURR MONTH " + currMonthNum);
			System.out.println("ARRAY CHECK" + part.getMonthyTotal(currMonthNum));
			part.setMonthlyTotals(currMonthNum, textPane.getProdIn(), textPane.getProdOut());
			//System.out.println(textPane.getMonthCount());
			//System.out.println(textPane.getProdIn() +" " + textPane.getProdOut());
			//System.out.println(part.getMonthyTotal(textPane.getMonthCount()));
			//System.out.println(part.getCumulativeTotal());
		}
	}
	
	public InventoryPartsList getList() {
		return list;
	}
	
	private File showSaveFileDialog() {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setDialogTitle("Specify a file to save");
		File fileToSave = null;
		String ext = ".xls";
		int userSelection = fileChooser.showSaveDialog(this);
		if (userSelection == JFileChooser.APPROVE_OPTION) {
			fileToSave = fileChooser.getSelectedFile();
			System.out.println("Save as file: " + fileToSave.getAbsolutePath());
		}
		if(fileToSave.getName().contains(ext))
			return fileToSave;
		else {
			fileToSave = new File(fileChooser.getSelectedFile() + ext);
			return fileToSave;
		}
	}

	public void update() {
		// TODO Auto-generated method stub
		
	}

	public void update(String startDate, String endDate) {
		// TODO Auto-generated method stub
		
	}

	public void update(String string) {
		// TODO Auto-generated method stub
		
	}

	public void updateMonthInfo(String buttonText, String monthText, boolean changeMonth) {
		// TODO Auto-generated method stub
		
	}

	public void updateExisting(Database db) {
		// TODO Auto-generated method stub
		this.db = db;
		rs = db.selectDates();
		
		//Populates dates to avoid Nullpointer exception and allow for the builder class to function
		try {
			while(rs.next()) {
				String startDate = rs.getString("START_DATE");
				System.out.println("DB CHECK START " + startDate);
				String endDate = rs.getString("END_DATE");
				System.out.println("DB CHECK END " + endDate);
				firstPane.setMaxMonths(startDate, endDate);
				System.out.println("MAX MONTHS " + firstPane.getMaxMonths());
				
				System.out.println("FIRST MONTH CHECK " + firstPane.getMaxMonths());
				System.out.println(firstPane.getMaxMonths());
				builder.setSensorPanel(buttonArray[0].getText(), firstPane.getMaxMonths());
				builder.setJHMCSPanel(buttonArray[1].getText(), firstPane.getMaxMonths());
				builder.setHydraulicPanel(buttonArray[2].getText(), firstPane.getMaxMonths());
				builder.setBitControlPanel(buttonArray[3].getText(), firstPane.getMaxMonths());
				builder.setInstrumentPanel(buttonArray[4].getText(), firstPane.getMaxMonths());
				builder.setInstrumentPanelKits(buttonArray[5].getText(), firstPane.getMaxMonths());
				builder.setGlareShield(buttonArray[6].getText(), firstPane.getMaxMonths());
				builder.setWireHarnessRobins(buttonArray[7].getText(), firstPane.getMaxMonths());
				builder.setWireHarnessUnicor(buttonArray[8].getText(), firstPane.getMaxMonths());
				builder.setMiscKit(buttonArray[9].getText(), firstPane.getMaxMonths());
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	public void updateNew(Database db) {
		// TODO Auto-generated method stub
		this.db = db;
		rs = db.selectDates();
	}

}
