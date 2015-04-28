import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class Database {
	Connection c;
	Statement stmt = null;
	//creates database named parts.db
		public Database(String fileName) {
			c = null;
		    try {
		      Class.forName("org.sqlite.JDBC");
		      c = DriverManager.getConnection("jdbc:sqlite:" + fileName + ".db");
		      c.setAutoCommit(false);
		    } catch ( Exception e ) {
		      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
		      System.exit(0);
		    }
		    System.out.println("Opened database successfully");
		}
		
		public void createTable() {
			try {
				stmt = c.createStatement();
				String sql = "CREATE TABLE IF NOT EXISTS INVENTORY" +
							 " (DATE  TEXT NOT NULL, " +
							 " PRODUCT 	TEXT NOT NULL, " +
							 " INSTALLS	INTEGER	DEFAULT 0, " +
							 " SHIPMENTS INTEGER DEFAULT 0, " +
							 " MONTH_TOTAL INTEGER DEFAULT 0, " +
							 " PRIMARY KEY(DATE, PRODUCT))";
				
				stmt.executeUpdate(sql);
				
				sql = "CREATE TABLE IF NOT EXISTS DATES" +
					  " (START_DATE TEXT NOT NULL, " +
					  " END_DATE TEXT NOT NULL, " +
					  " PRIMARY KEY(START_DATE, END_DATE))";
				
				stmt.execute(sql);
				//stmt.close();
				//c.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		public void insert(String date, String product, int installs, int shipments, int cummulative) {
			try {
				stmt = c.createStatement();
				String sql = "INSERT OR REPLACE INTO INVENTORY (DATE,PRODUCT,INSTALLS,SHIPMENTS,MONTH_TOTAL) " +
							 " VALUES (COALESCE((SELECT DATE FROM INVENTORY WHERE DATE = " + "'" + date + "'" + ")," + "'" + date + "')," + 
							 		   "COALESCE((SELECT PRODUCT FROM INVENTORY WHERE PRODUCT = "+ "'" + product + "'" + ")," + "'" + product + "')," + 
							 		   	installs + "," + shipments + "," + cummulative + ");";
				stmt.executeUpdate(sql);
				//stmt.close();
				c.commit();
				//c.close();
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		public void insertDates(String startDate, String endDate) {
			try {
				stmt = c.createStatement();
				String sql = "INSERT INTO DATES (START_DATE,END_DATE) " + 
						     " VALUES (" + "'" + startDate + "'," + "'" + endDate + "');";
				
				stmt.executeUpdate(sql);
				
				c.commit();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		public ResultSet select(String date, String product) {
			ResultSet rs = null;
			try {
				stmt = c.createStatement();
				rs = stmt.executeQuery("SELECT * FROM INVENTORY WHERE DATE = " + "'" + date + "'" + "AND PRODUCT = " + "'" + product + "'" + ";");
				//stmt.close();
				//c.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
//			try {
//				System.out.println("sdfsdf: " + rs.getString("PRODUCT"));
//			} catch (SQLException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
			return rs;
		}
		
		public ResultSet selectProduct(String product) {
			ResultSet rs = null;
			try {
				stmt = c.createStatement();
				rs = stmt.executeQuery("SELECT * FROM INVENTORY WHERE PRODUCT = " + "'" + product + "';");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return rs;
		}
		
		public ResultSet selectDates() {
			ResultSet rs = null;
			
			try {
				stmt = c.createStatement();
				rs = stmt.executeQuery("SELECT * FROM DATES;");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return rs;
					
		}
}
