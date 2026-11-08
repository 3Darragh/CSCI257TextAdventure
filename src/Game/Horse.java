package Game;

public class Horse extends Item {
    public Horse() {
        super("horse", g_HEAVY);
    }

    @Override
    public String itemToGive(Item pItem) {
        if (!(pItem instanceof Meal)) {
            return "The horse has no use for that.";
        }
        Game.inventory.remove(pItem.getName());
        Room diningHall = Game.currentRoom.getExit(Direction.SOUTH);

        // Shitty work around because take() removes the meal from the dining hall and I need to
        // add it back. TO DO: Clean up my code sometime this week after talking to Fischbach.
        if (diningHall != null && diningHall.getItem("meal") == null) {
            Meal replacementMeal = new Meal();
            diningHall.addItem(replacementMeal.getName(), replacementMeal);
        }
        return "You gave the meal to the horse. It did not give you anything in return.";
    }

    @Override
    public String pet() {
        return "Horse: Neigh!";
    }
}