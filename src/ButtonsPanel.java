import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

	
public class ButtonsPanel extends JPanel implements ActionListener, Subject {
	
	protected JButton[] buttonArray = new JButton[11];
	boolean clicked = false;
	JButton button;
	JLabel editMonthLabel;
	JTextField editMonthField;
	ArrayList<Observer> observers;
	
	public ButtonsPanel() {
		setLayout(new GridLayout(7,2));
		observers = new ArrayList<Observer>();
		for(int x = 0; x < buttonArray.length - 1; x++) {
			JButton button = new JButton();
			button.setVerticalTextPosition(AbstractButton.CENTER);
			button.setHorizontalAlignment(AbstractButton.CENTER);
			button.addActionListener(this);
			button.setActionCommand("buttonPane");
			add(button);
			buttonArray[x] = button;
		}
		
		JButton output = new JButton("Output to Excel");
		editMonthLabel = new JLabel("Enter month to be edited. Then click object to change");
		editMonthField = new JTextField();
		output.addActionListener(this);
		output.setActionCommand("output");
		add(output);
		add(new JLabel());
		add(editMonthLabel);
		add(editMonthField);
		buttonArray[10] = output;
		
		buttonArray[0].setText("Sensor Panel");
		buttonArray[1].setText("JHMCS Panel");
		buttonArray[2].setText("Hydraulic Panel");
		buttonArray[3].setText("Bit Control Panel");
		buttonArray[4].setText("Instrument Panel");
		buttonArray[5].setText("Instrument Panel Kits");
		buttonArray[6].setText("Glare Shield");
		buttonArray[7].setText("Wire Harness Robins");
		buttonArray[8].setText("Wire Harness Unicor");
		buttonArray[9].setText("Misc Kits");
	}
	
	public void actionPerformed(ActionEvent e) {
		button = (JButton) e.getSource();
		if(!editMonthField.getText().equals("")) {
			notifyMonthChange(button.getText(), editMonthField.getText());
			editMonthField.setText("");
		}
		else
			notifyObservers(button.getText());
		System.out.println("buttonpanle");
		
	}
	
//	public JButton getPrevButtonPress() {
//		return prevButton;
//	}
	
	public JTextField getEditMonthField() {
		return editMonthField;
	}
	
	public boolean getClicked() {
		return clicked;
	}
	
	public JButton[] getButtonArray() {
		return buttonArray;
	}

	public void registerObserver(Observer o) {
		// TODO Auto-generated method stub
		observers.add(o);
	}

	public void removeObserver(Observer o) {
		// TODO Auto-generated method stub
		
	}

	public void notifyObservers() {
		// TODO Auto-generated method stub
		
	}

	public void notifyObservers(String startDate, String endDate) {
		// TODO Auto-generated method stub
		
	}

	public void notifyObservers(String string) {
		// TODO Auto-generated method stub
		for(int x = 0; x < observers.size(); x++) {
			observers.get(x).update(string);
		}
	}
	
	public void notifyMonthChange(String buttonText, String monthText) {
		boolean changeMonth = true;
		for(int x = 0; x < observers.size(); x++) {
			observers.get(x).updateMonthInfo(buttonText, monthText, changeMonth);
		}
	}
	
}
