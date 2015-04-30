import static org.junit.Assert.*;

import java.awt.event.ActionEvent;
import java.io.IOException;

import org.junit.Test;


public class UnitTests {

	@Test
	public void Texttest() {
		TextPanel Test = new TextPanel();
		Test.inField.setText("10");
		Test.outField.setText("-3");
		
		assertFalse(Test.error_checking(Test.outField.getText()));
		assertTrue(Test.error_checking(Test.inField.getText()));
		
		Test.inField.setText("");
		Test.outField.setText("w");
		
		assertFalse(Test.error_checking(Test.outField.getText()));
		assertFalse(Test.error_checking(Test.inField.getText()));

	}

	@Test
	public void MonthlyTotaltest() {
		Part Test = new Part();
		
		Test.setTotalMonths(2);
		Test.setMonthlyTotals(0, 1, 2);
		Test.setMonthlyTotals(1, 3, 4);
		
		assertEquals(Test.monthlyTotals[0], -1);
	}
	
	/*@Test
	public void EditMonthtest() throws IOException {
		ButtonsPanel buttonPane = new ButtonsPanel(); 
		TextPanel textPane = new TextPanel();
		TextPanel2 firstPane = new TextPanel2();
		openDBPanel openDBPane = new openDBPanel();
		
		firstPane.endMonthsInput.setText("5/2015");
		firstPane.startMonthsInput.setText("2/2015");
		firstPane.actionPerformed((ActionEvent) firstPane.submit.getAction());
		PartsInputGUI Test = new PartsInputGUI(buttonPane, textPane, firstPane, openDBPane);
		
		buttonPane.editMonthField.setText("3/2015");
		Test.actionPerformed((ActionEvent) buttonPane.buttonArray[0].getAction());
		
		assertTrue(Test.changeMonth == true);
		
		buttonPane.editMonthField.setText("7/2015");
		Test.actionPerformed((ActionEvent) buttonPane.buttonArray[0].getAction());
		assertTrue(Test.changeMonth == false);
		
		
	}*/
}
