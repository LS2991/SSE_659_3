
public interface Observer {
	public void update();

	void update(String startDate, String endDate);
	void update(String string);

	public void updateMonthInfo(String buttonText, String monthText, boolean changeMonth);

	public void updateNew(Database db);

	void updateExisting(Database db);
}
