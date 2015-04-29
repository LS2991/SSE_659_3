import static org.junit.Assert.*;

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
}
