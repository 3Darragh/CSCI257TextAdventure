package Labs;

public class Notebook extends Item {
    private boolean wasRead;

    public Notebook() {
        super("notebook", g_NOTHEAVY);
        wasRead = false;
    }

    // Override read.
    @Override 
    public String read() {
        if (!Game.inventory.values().contains(this)) {
            return "You must have the notebook to read it.";
        } else if (wasRead == true) {
            return "You have read the notebook again and found nothing new.";
        } else {
            wasRead = true;
            Game.addInventoryItem(new Item("password", g_NOTHEAVY));
            return "You have read the book and found a password.";
        }
    }
}
