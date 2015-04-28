import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;

import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class openDBPanel extends JPanel implements ActionListener ,Subject {

	JButton openExisting, openNew;
	JTextField openDatabaseField, createDatabaseField;
	
	Database db;
	ArrayList<Observer> observers;
	public openDBPanel() {
		setLayout(new GridLayout(2,2));
		
		observers = new ArrayList<Observer>();
		openExisting = new JButton("Open Database");
		openExisting.setVerticalTextPosition(AbstractButton.CENTER);
		openExisting.setHorizontalAlignment(AbstractButton.CENTER);
		openExisting.addActionListener(this);
		openExisting.setActionCommand("existing");
		
		openNew = new JButton("Create Database");
		openNew.setVerticalTextPosition(AbstractButton.CENTER);
		openNew.setHorizontalAlignment(AbstractButton.CENTER);
		openNew.addActionListener(this);
		openNew.setActionCommand("new");
		
		openDatabaseField = new JTextField(10);
		createDatabaseField = new JTextField(10);
		
		add(openDatabaseField);
		add(openExisting);
		add(createDatabaseField);
		add(openNew);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		String action = e.getActionCommand();
		
		if(action.equals("existing")) {
			db = new Database(openDatabaseField.getText());
			db.createTable();
			notifyDatabaseExisting(db);
			System.out.println("OPEN");
		}
		else if(action.equals("new")) {
			db = new Database(createDatabaseField.getText());
			db.createTable();
			notifyDatabaseNew(db);
			System.out.println("NEW");
		}
	}
	
	public JButton getExistingButton() {
		return openExisting;
	}
	
	public JButton getNewButton() {
		return openNew;
	}
	@Override
	public void registerObserver(Observer o) {
		// TODO Auto-generated method stub
		observers.add(o);
	}

	@Override
	public void removeObserver(Observer o) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void notifyObservers() {
		// TODO Auto-generated method stub
		
	}
	
	public void notifyDatabaseExisting(Database db) {
		for(int x = 0; x < observers.size(); x++) {
			observers.get(x).updateExisting(db);
		}
	}
	
	public void notifyDatabaseNew(Database db) {
		for(int x = 0; x < observers.size(); x++) {
			observers.get(x).updateNew(db);
		}
	}

	@Override
	public void notifyObservers(String start1, String string2) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void notifyObservers(String string) {
		// TODO Auto-generated method stub
		
	}
	
//	private void showSaveFileDialog() {
//		JFileChooser fileChooser = new JFileChooser();
//		fileChooser.setDialogTitle("Specify a file to save");
//		File fileToSave = null;
//		String ext = ".db";
//		int userSelection = fileChooser.showSaveDialog(this);
//		if (userSelection == JFileChooser.APPROVE_OPTION) {
//			fileToSave = fileChooser.getSelectedFile();
//			System.out.println("Save as file: " + fileToSave.getAbsolutePath());
//		}
//		if(fileToSave.getName().contains(ext))
//			db = new Database(fileToSave.getName());
//		else {
//			fileToSave = new File(fileChooser.getSelectedFile() + ext);
//			db = new Database(fileToSave.getName());
//		}
//		db.createTable();
//		notifyDatabase(db);
//	}
//	
//	private void showOpenFileDialog() {
//		JFileChooser fileChooser = new JFileChooser();
//		JFrame frame = new JFrame("fileChooser");
//		fileChooser.setDialogTitle("Specify a file to Open");
//		File fileToOpen = null;
//		int userSelection = fileChooser.showOpenDialog(frame);
//		if (userSelection == JFileChooser.APPROVE_OPTION) {
//			fileToOpen = fileChooser.getSelectedFile();
//			frame.dispose();
//			System.out.println("File Opened: " + fileToOpen.getAbsolutePath());
//		}
//		db = new Database(fileToOpen.getName());
//		db.createTable();
//		notifyDatabase(db);
//	}
}
