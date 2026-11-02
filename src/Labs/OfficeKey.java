package Labs;

public class OfficeKey extends Item {
    public OfficeKey() {
        super("officekey", Item.g_NOTHEAVY);
    }

    @Override
    public String use(Room currentRoom) {
        Room westRoom = currentRoom.getExit(Direction.WEST);

        // If there is no room to the west.
        if (westRoom == null) {
            return "You cannot use that here.";
        }

        // Only locked room west of another is the office.
        if (westRoom.isRoomLocked()) {
            westRoom.unlockRoom();
            return "You unlocked the Office.";
        }

        // Key was already used.
        return "The office key was already used.";
    }
}
