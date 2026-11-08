package Labs;

public class Tool extends Item {
    public Tool() {
        super("tool", Item.g_NOTHEAVY);
    }

    @Override
    public String use(Room currentRoom) {
        Room attic = currentRoom.getExit(Direction.UP);

        if (attic == null || !attic.getName().equals("Attic: ")) {
            return "You cannot use that here.";
        }

        if (attic.isRoomLocked()) {
            attic.unlockRoom();
            return "You unlocked the Attic.";
        }

        return "The tool was already used.";
    }
}