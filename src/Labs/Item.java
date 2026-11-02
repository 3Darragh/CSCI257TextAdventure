package Labs;

import java.io.Serializable;

public class Item implements Serializable {
	private String name;
	private Boolean isHeavy;
	
	public static Boolean g_HEAVY = true;
	public static Boolean g_NOTHEAVY = false;
	
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

	public String use(Room currentRoom) {
		return "You cannot use this item.";
	}
}