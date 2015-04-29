import static org.junit.Assert.*;

import org.junit.Test;


public class UnitTests {

	@Test
	public void test() {
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

}
