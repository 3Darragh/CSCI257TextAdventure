package Game;

public class Crown extends Item {
    private boolean isOnHead;

    public Crown() {
        super("crown", g_NOTHEAVY);
    }

    public String wear() {
        if (isOnHead) {
            return "You are already wearing the crown.";
        } else {
        isOnHead = true;
        return "You put on the crown. You feel like a king";
        }
    }

    public String takeOff() {
        if (!isOnHead) {
            return "You are not wearing the crown.";
        } else {
        isOnHead = false;
        return "You take off the crown. The feeling of power leaves you.";
        }
    }

    public boolean isWorn() {
        return isOnHead;
    }

    @Override
    public String itemToGive(Item pOfferedItem) {
	    if (pOfferedItem instanceof Crown
	            && Game.currentRoom != null
	            && Game.currentRoom.getName().equals("The Castle Grounds: ")) {
	        Game.inventory.remove(getName());
	        return "You gave the crown away in the castle grounds.";
	    }
	    return super.itemToGive(pOfferedItem);
	}
}
