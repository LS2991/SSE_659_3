import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;

import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class TextPanel2 extends JPanel implements ActionListener, Subject{
	JLabel startMonths, endMonths;
	JTextField startMonthsInput, endMonthsInput;
	JButton submit;
	int numMonths;
	int maxMonths;
	NewDate startDate;
	NewDate endDate;
	Calendar startCalendar, endCalendar;
	HashMap<String, Integer> months = new HashMap<String, Integer>();
	ArrayList<Observer> observers;
	
	public TextPanel2() {
		
		observers = new ArrayList<Observer>();
		setLayout(new GridLayout(3,2));
		
		startMonths = new JLabel("Enter start date (mm/yyyy): ");
		startMonthsInput = new JTextField(10);
		
		endMonths = new JLabel("Enter end date (mm/yyyy): ");
		endMonthsInput = new JTextField(10);
		
		submit = new JButton("Submit");
		submit.setVerticalTextPosition(AbstractButton.CENTER);
		submit.setHorizontalAlignment(AbstractButton.CENTER);
		submit.addActionListener(this);
		submit.setActionCommand("Submit2");
		
		add(startMonths);
		add(startMonthsInput);
		add(endMonths);
		add(endMonthsInput);
		add(submit);
	}

	public void actionPerformed(ActionEvent e) {
		
		
//		startDate = new NewDate(startMonthsInput.getText());
//		endDate = new NewDate(endMonthsInput.getText());
//		
////		startCalendar = new GregorianCalendar();
////		startCalendar.setTime(startDate);
////		endCalendar = new GregorianCalendar();
////		endCalendar.setTime(endDate);
//		
//		int m2 = endDate.getYear() * 12 + endDate.getMonth();
//		int m1 = startDate.getYear() * 12 + startDate.getMonth();
//		
//		numMonths = m2 - m1 + 1;
//		maxMonths = numMonths;
		
		NewDate initDate = new NewDate(startMonthsInput.getText());
		int count = 1;
		System.out.println(maxMonths + " " + numMonths);
		while(initDate.compareTo(endDate) < 2) {
			System.out.println(initDate.toString());
			months.put(initDate.toString(), count);
			
			System.out.println("Month val: " + months.get(initDate.toString()));
			initDate.incMonth();
			
			count++;
		}
		//months.put(initDate.toString(), count);
		//System.out.println(startDate.toString());
		
		notifyObservers(startDate.toString(), endDate.toString());
	}
	
	public JButton getButton() {
		return submit;
	}
	
	public int getNumMonths() {
		return numMonths;
	}
	
	public int getMaxMonths() {
		return maxMonths;
	}
	
	public HashMap<String, Integer> getMonthMap() {
		return months;
	}
	
	public void setMaxMonths() {
		
		startDate = new NewDate(startMonthsInput.getText());
		endDate = new NewDate(endMonthsInput.getText());
		
//		startCalendar = new GregorianCalendar();
//		startCalendar.setTime(startDate);
//		endCalendar = new GregorianCalendar();
//		endCalendar.setTime(endDate);
		
		int m2 = endDate.getYear() * 12 + endDate.getMonth();
		int m1 = startDate.getYear() * 12 + startDate.getMonth();
		
		numMonths = m2 - m1 + 1;
		maxMonths = numMonths;
		
		//System.out.print(maxMonths);
	}
	
	public void setMaxMonths(String startDate, String endDate) {
		this.startDate = new NewDate(startDate);
		this.endDate = new NewDate(endDate);
		
//		startCalendar = new GregorianCalendar();
//		startCalendar.setTime(startDate);
//		endCalendar = new GregorianCalendar();
//		endCalendar.setTime(endDate);
		
		int m2 = this.endDate.getYear() * 12 + this.endDate.getMonth();
		int m1 = this.startDate.getYear() * 12 + this.startDate.getMonth();
		
		numMonths = m2 - m1 + 1;
		maxMonths = numMonths;
		
		NewDate initDate = new NewDate(startDate);
		int count = 1;
		System.out.println(maxMonths + " " + numMonths);
		while(initDate.compareTo(this.endDate) < 2) {
			System.out.println(initDate.toString());
			months.put(initDate.toString(), count);
			
			System.out.println("Month val: " + months.get(initDate.toString()));
			initDate.incMonth();
			
			count++;
		}
	}
	
	public NewDate getStartDate() {
		return startDate;
	}
	
	public NewDate getEndDate() {
		return endDate;
	}
	
	public Calendar getStartCalendar() {
		return startCalendar;
	}
	
	public Calendar getEndCalendar() {
		return endCalendar;
	}
	
	public String getKeyFromValue(int value) {
		for(String key : months.keySet()) {
			if(months.get(key).equals(value))
				return key;
		}
		return null;
	}

	public void registerObserver(Observer o) {
		// TODO Auto-generated method stub
		observers.add(o);
	}

	public void removeObserver(Observer o) {
		// TODO Auto-generated method stub
		
	}

	public void notifyObservers(String startDate,String endDate) {
		// TODO Auto-generated method stub
		for(int x = 0; x < observers.size(); x++) {
			observers.get(x).update(startDate, endDate);
		}
	}

	public void notifyObservers() {
		// TODO Auto-generated method stub
		
	}

	public void notifyObservers(String string) {
		// TODO Auto-generated method stub
		
	}
}
