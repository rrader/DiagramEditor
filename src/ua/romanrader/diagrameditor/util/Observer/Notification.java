package ua.romanrader.diagrameditor.util.Observer;

public class Notification {
	private Object sender;
	private String name;
	private Object userData;
	
	public Notification(Object sender, String name, Object userData) {
		super();
		this.sender = sender;
		this.name = name;
		this.userData = userData;
	}
	
	public Object getSender() {
		return sender;
	}
	public void setSender(Object sender) {
		this.sender = sender;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Object getUserData() {
		return userData;
	}
	public void setUserData(Object userData) {
		this.userData = userData;
	}
}
