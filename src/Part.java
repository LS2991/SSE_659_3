
public class Part {
	String name;
	int cumulative;
	int[] monthlyTotals;
	
	public Part() {
		name = "none";
		cumulative = 0;
	}
	public Part(String name, int numMonths) {
		this.name = name;
		cumulative = 0;
		monthlyTotals = new int[numMonths];
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setTotalMonths(int numMonths) {
		monthlyTotals = new int[numMonths];
	}
	
	public void setMonthlyTotals(int month, int in, int out) {
		if(monthlyTotals[month - 1] == 0) {
			monthlyTotals[month - 1] = (in - out);
			System.out.println("IN if: " + (in - out));
			System.out.println("IN if: " + monthlyTotals[month - 1]);
			cumulative += monthlyTotals[month - 1];
			System.out.println("IN if: " + cumulative);
		}
		
		else {
			cumulative -= monthlyTotals[month - 1];
			System.out.println("OUT if: " + cumulative);
			System.out.println("OUT if: " + in);
			monthlyTotals[month - 1] = (in - out);
			System.out.println("OUT if: " + (in - out));
			System.out.println("OUT if: " + monthlyTotals[month - 1]);
			cumulative += monthlyTotals[month - 1];
			System.out.println("OUT if: " + cumulative);
		}
	}
	
	public String getName() {
		return name;
	}
	
	public int getMonthyTotal(int month) {
		return monthlyTotals[month - 1];
	}
	
	public int[] getMonthlyTotalsArray() {
		return monthlyTotals;
	}
	
	public int getCumulativeTotal() {
		return cumulative;
	}
}
