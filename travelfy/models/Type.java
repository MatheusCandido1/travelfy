package travelfy.models;

public class Type {
	
	String id;
	String name;
	int thrillLevel;
	
	Type() {}
	
	public String getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getThrillLevel() {
		return thrillLevel;
	}
	public void setThrillLevel(int thrillLevel) {
		this.thrillLevel = thrillLevel;
	}
	
}
