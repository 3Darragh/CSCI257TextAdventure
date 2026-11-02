package Game;

import java.io.Serializable;

public class Item implements Serializable {
	private String name;
	private Boolean isHeavy;
	
	public static Boolean gHeavy = true;
	public static Boolean gNotHeavy = false;
	
	public Item(String pName, Boolean pIsHeavy) {
		name = pName;
		isHeavy = pIsHeavy;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String pName) {
		name = pName;
	}
	
	public Boolean isHeavy() {
		return isHeavy;
	}
	
	public String toString() {
		String str = name;
		return str;
	}
}