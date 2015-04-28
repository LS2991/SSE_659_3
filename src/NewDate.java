import java.util.StringTokenizer;


public class NewDate {
	
	int month, year;
	
	public NewDate(String date) {
		StringTokenizer tokenizer = new StringTokenizer(date, "/");
		month = Integer.parseInt(tokenizer.nextToken());
		year = Integer.parseInt(tokenizer.nextToken());
		
	}
	
	public int getMonth() {
		return month;
	}
	
	public int getYear() {
		return year;
	}
	
	public void incMonth() {
		month++;
		if(month > 12) {
			month = 1;
			year++;
		}
	}
	
	//return 0 if equal. return 1 if less than. return 2 if greater than
	public int compareTo(NewDate date){
		
		if (year < date.getYear())
			return 1;
		else if (year > date.getYear())
			return 2;
		else if (date.getYear() == year && date.getMonth() != month) {
			if(month < date.getMonth())
				return 1;
			else
				return 2;
		}
		else
			return 0;
	}
	
	public String toString() {
		return (month + "/" + year);
	}

}
