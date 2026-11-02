package Labs;

import java.util.ArrayList;

public class World {
	
	public static Room buildWorld() {
		Room livingRoom = new Room("Living Room: ");
		Room office = new Room("Office: ");
		Room kitchen = new Room("Kitchen: ");
		Room bedRoom = new Room("Bedroom: ");
		Room garage = new Room("Garage: ");
		Room attic = new Room("Attic: ");
		
		// Living Room.
			// Set Exits.
		livingRoom.setExit(office, Direction.WEST);
		livingRoom.setExit(kitchen, Direction.EAST);
		livingRoom.setExit(bedRoom, Direction.UP);
		
			// Create Items.
		Item officeKey = new OfficeKey();
		Item musicBox = new Item ("musicbox", Item.g_NOTHEAVY);
		Item couch = new Item("couch", Item.g_HEAVY);
		
			// Add Items.
		livingRoom.addItem(officeKey.getName(), officeKey);
		livingRoom.addItem(musicBox.getName(), musicBox);
		livingRoom.addItem(couch.getName(), couch);
		
		// Office.
			// Set Exits.
		office.setExit(livingRoom, Direction.EAST);

			// Set Locks.
		office.lockRoom();
		
			// Create Items.
		Item tool = new Tool();
		
		office.addItem(tool.getName(), tool);
		
		// Bedroom.
			// Set Exits.
		bedRoom.setExit(livingRoom, Direction.DOWN);
		bedRoom.setExit(attic, Direction.UP);
		
			// Create Items.
		Item bed = new Item("bed", Item.g_HEAVY);
		
		bedRoom.addItem(bed.getName(), bed);
		
		// Kitchen.
			// Set Exits.
		kitchen.setExit(livingRoom, Direction.WEST);
		kitchen.setExit(garage, Direction.SOUTH);
		
			// Create Items.
		Item flashLight = new Item("flashlight", Item.g_NOTHEAVY);
		Item knife = new Item("knife", Item.g_NOTHEAVY);
		Item orb = new Item("orb", Item.g_NOTHEAVY);
		
		kitchen.addItem(flashLight.getName(), flashLight);
		kitchen.addItem(knife.getName(), knife);
		kitchen.addItem(orb.getName(), orb);
		
		// Garage.
			// Set Exits.
		garage.setExit(kitchen, Direction.NORTH);

			// Set Locks.
		garage.lockRoom();
		
		// Attic.
			// Set Exits.
		attic.setExit(bedRoom, Direction.DOWN);

			// Set Locks.
		attic.lockRoom();
		
		// Set starting room to living room.
		return livingRoom;
	}
	
	// Puts all rooms and their properties into one arrayList.
	public static ArrayList<Room> createRoomArrayList() {
		ArrayList<Room> roomArrayList = new ArrayList<Room>();
		Room world = buildWorld();
		
		// Adds them to the list.
		roomArrayList.add(world); // LivingRoom
		roomArrayList.add(world.getExit(Direction.WEST)); //Office.
		roomArrayList.add(world.getExit(Direction.EAST)); // Kitchen.
		roomArrayList.add(world.getExit(Direction.EAST).getExit(Direction.SOUTH)); // Garage.
		roomArrayList.add(world.getExit(Direction.UP)); // Bedroom.
		roomArrayList.add(world.getExit(Direction.UP).getExit(Direction.UP)); // Attic.
		
		return roomArrayList;
	}
	
	public static void main(String[] args) {
		Game.loadRoomDescriptions();
		
		Room currentRoom = buildWorld();
		
		// Living Room.
		
		// Office.
		currentRoom = currentRoom.getExit(Direction.WEST);
		
		// Kitchen.
		currentRoom = currentRoom.getExit(Direction.EAST);
		currentRoom = currentRoom.getExit(Direction.EAST);
		
		// Garage.
		currentRoom = currentRoom.getExit(Direction.SOUTH);
		
		// Bedroom.
		currentRoom = currentRoom.getExit(Direction.NORTH);
		currentRoom = currentRoom.getExit(Direction.WEST);
		currentRoom = currentRoom.getExit(Direction.UP);
		
		// Attic.
		currentRoom = currentRoom.getExit(Direction.UP);
	}
}