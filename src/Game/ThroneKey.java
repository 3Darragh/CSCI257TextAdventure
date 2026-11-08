package Game;

public class ThroneKey extends Item {
    private final Room throneRoom;

    public ThroneKey(Room throneRoom) {
        super("thronekey", g_NOTHEAVY);
        this.throneRoom = throneRoom;
    }

    @Override
    public String use() {
        if (throneRoom.isRoomLocked()) {
            throneRoom.unlockRoom();
            return "You have unlocked the throne room!";
        }

        return "The throne room is already unlocked.";
    }
}