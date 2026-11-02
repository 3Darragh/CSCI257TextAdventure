package Labs;

public class Tool extends Item {
    public Tool() {
        super("tool", Item.g_NOTHEAVY);
    }

    @Override
    public String use(Room currentRoom) {
        Room upRoom = currentRoom.getExit(Direction.UP);

        // If there is no room to the west.
        if (upRoom == null) {
            return "You cannot use that here.";
        }

        // Only locked room up of another is the attic.
        if (upRoom.isRoomLocked()) {
            upRoom.unlockRoom();
            return "You unlocked the Attic.";
        }

        // Key was already used.
        return "The tool was already used.";
    }
}