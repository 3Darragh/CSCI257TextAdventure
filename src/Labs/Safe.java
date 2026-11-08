package Labs;

public class Safe extends Item {
    private boolean isOpen;
    private boolean wasOpen;

    public Safe() {
        super("safe", Item.g_HEAVY);
        isOpen = false;
        wasOpen = false;
    }

    @Override
    public String open() {
        if (!Game.currentRoom.getName().equals("Attic: ")) {
            return "There is no safe in this room to open.";
        } else if (isOpen) {
            return "The safe is already open.";
        } else {
            if (wasOpen) {
                isOpen = true;
                return "You have opened the safe again but found nothing new.";
            } else {
                isOpen = true;
                wasOpen = true;

                Notebook notebook = new Notebook();
                Game.currentRoom.addItem("notebook", notebook);

                return "You have opened the safe. There is a notebook inside!";
            }
        }
    }

    @Override 
    public String close() {
        if (!Game.currentRoom.getName().equals("Attic: ")) {
            return "There is no safe in this room to close.";
        } else if (!isOpen) {
            return "The safe is already closed.";
        } else {
            isOpen = false;
            return "You have closed the safe.";
        }
    }
}
