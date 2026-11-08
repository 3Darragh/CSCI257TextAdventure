package Game;

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
	
	@Override 
	public String toString() {
		String str = name;
		return str;
	}

	public String use(Room currentRoom) {
		return "You cannot use this item.";
	}

	public String open() {
		return "You cannot open this item.";
	}

	public String close() {
		return "You cannot close this item.";
	}

	public String read() {
		return "You cannot read this item.";
	}

	// You can feed the meal to a specific NPC for them to give you access to a room.
	// I know we are making NPC's later, so I'm also letting you feed the horse.
	public String feed() {
		return "You cannot feed this NPC.";
	}
}