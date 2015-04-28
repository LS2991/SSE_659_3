import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;


public class PartsInputClient{

	public static void main(String[] args) throws IOException {
		ButtonsPanel buttonPane = new ButtonsPanel();
		TextPanel2 firstPane = new TextPanel2();
		TextPanel textPane = new TextPanel();
		openDBPanel openDBPane = new openDBPanel();
		
		new PartsInputGUI(buttonPane, textPane, firstPane, openDBPane);
		
		//DBMethods.createDatabase();
	}
	
	

}
