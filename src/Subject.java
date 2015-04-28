
public interface Subject {
	public void registerObserver(Observer o);
	public void removeObserver(Observer o);
	public void notifyObservers();
	public void notifyObservers(String start1, String string2);
	public void notifyObservers(String string);
}
