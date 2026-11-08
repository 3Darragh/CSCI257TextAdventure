package Labs;

public class OfficeKey extends Item {
    public OfficeKey() {
        super("officekey", Item.g_NOTHEAVY);
    }

    @Override
    public String use(Room currentRoom) {
        Room westRoom = currentRoom.getExit(Direction.WEST);

        if (westRoom == null || !westRoom.getName().equals("Office: ")) {
            return "You cannot use that here.";
        }

        if (westRoom.isRoomLocked()) {
            westRoom.unlockRoom();
            return "You unlocked the Office.";
        }

        return "The office key was already used.";
    }
}
