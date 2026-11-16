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

	public String use() {
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

	public String wear() {
		return "You cannot wear this item.";
	}

	public String giveItemTo(Item recipient) {
		return recipient.itemToGive(this);
	}

	public String itemToGive(Item offeredItem) {
		return "The " + name + " does not accept that.";
	}

	public String pet() {
		return "You cannot pet this item.";
	}
}