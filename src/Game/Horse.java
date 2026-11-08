package Game;

public class Horse extends Item {
    public Horse() {
        super("horse", g_HEAVY);
    }

    @Override
    public String feed() {
        if (!Game.currentRoom.getName().equals("Stables: ")) {
            return "You cannot give food to anyone in this room.";
        } else if ((!Game.inventory.keySet().contains("meal"))) {
            return "You do not have a meal to give to the horse.";
        } else {
            return "You gave the meal to the horse. It did not give you anything in return.";
        }
    }

    public String pet() {
        return "Horse: Neigh!";
    }
}