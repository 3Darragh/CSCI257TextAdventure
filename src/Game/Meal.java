package Game;

public class Meal extends Item {
    public Meal() {
        super("meal", g_NOTHEAVY);
    }

    @Override
    public String giveItemTo(Item pRecipient) {
        if (pRecipient != null && pRecipient.getName().equals("horse")) {
            return super.giveItemTo(pRecipient);
        }

        if (!Game.currentRoom.getName().equals("The Stables: ")) {
            if (pRecipient == null) {
                return "Could not find that recipient in the stables.";
            } else {
                super.giveItemTo(pRecipient);
            }
        }

        Room spire = Game.currentRoom.getExit(Direction.UP);
        if (spire == null) {
            return "There is no room to unlock here.";
        }

        Game.inventory.remove(getName());
        if (spire.isRoomLocked()) {
            spire.unlockRoom();
            return "You gave the meal to the guard. He unlocked the Spire for you.";
        }
        return "You gave the guard a second meal. He is thankful but the Spire is already unlocked.";
    }
}
