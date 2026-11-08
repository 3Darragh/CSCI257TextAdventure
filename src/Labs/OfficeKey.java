package Labs;

public class OfficeKey extends Item {
    public OfficeKey() {
        super("officekey", Item.g_NOTHEAVY);
    }

    @Override
    public String use() {
        Room officeRoom = Game.currentRoom.getExit(Direction.WEST);

        if (officeRoom == null || !officeRoom.getName().equals("Office: ")) {
            return "You cannot use that here.";
        }

        if (officeRoom.isRoomLocked()) {
            officeRoom.unlockRoom();
            return "You unlocked the Office.";
        }

        return "The office key was already used.";
    }
}
