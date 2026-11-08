package Game;

import java.util.ArrayList;
import java.util.HashMap;
import java.io.Serializable;

public class Room implements Serializable {

	private String name;
	private ArrayList<Room> roomList = new ArrayList<Room>();
	private boolean isLocked = false;

	public HashMap<String, Item> itemList = new HashMap<>();

	
	public Room(String pName) {
		name = pName;
		roomList = new ArrayList<>();
		roomList.add(0, null);
		roomList.add(1, null);
		roomList.add(2, null);
		roomList.add(3, null);
		roomList.add(4, null);
		roomList.add(5, null);
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String pName) {
		name = pName;
	}
	
	public String getDescription() {
		return Game.roomDescriptions.get(name.replace(":", "").trim());
	}
	
	public void setDescription(String pDescription) {
		Game.roomDescriptions.put(name.replace(":", "").trim(), pDescription);
	}
	
	public Room getExit(Direction pDirection) {
		return roomList.get(pDirection.ordinal());
	}
	
	public void setExit(Room pRoom, Direction pDirection) {
		roomList.set(pDirection.ordinal(), pRoom);
	}
	
	public Item getItem(String pString) {
		return itemList.get(pString);
	}
	
	public void addItem(String pString, Item pItem) {
		itemList.put(pString, pItem);
	}
	
	public void removeItem(String pString) {
		itemList.remove(pString);
	}
	
	public ArrayList<Room> getRoomList() {
		return roomList;
	}

	public boolean isRoomLocked() {
		return isLocked;
	}

	public void lockRoom() {
		isLocked = true;
	}

	public void unlockRoom() {
		isLocked = false;
	}
	
	public String toString() {
		String str = getName() + getDescription();
		return str;
	}
}