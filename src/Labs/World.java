package Labs;

import java.util.ArrayList;

public class World {
		private static Room livingRoom;
		private static Room office;
		private static Room kitchen;
		private static Room bedRoom;
		private static Room garage;
		private static Room attic;

		public static ArrayList<Room> roomList;

		public World() {
			livingRoom = new Room("Living Room: ");
			office = new Room("Office: ");
			kitchen = new Room("Kitchen: ");
			bedRoom = new Room("Bedroom: ");
			garage = new Room("Garage: ");
			attic = new Room("Attic: ");

			roomList = new ArrayList<>(6);
			roomList.add(livingRoom);
			roomList.add(office);
			roomList.add(kitchen);
			roomList.add(garage);
			roomList.add(bedRoom);
			roomList.add(attic);
		}
	
	public static Room buildWorld() {
		new World();

		// Living Room.
		// ----- Set Exits.
		World.livingRoom.setExit(office, Direction.WEST);
		World.livingRoom.setExit(kitchen, Direction.EAST);
		World.livingRoom.setExit(bedRoom, Direction.UP);
		
		// ----- Create Items.
		Item officeKey = new OfficeKey();
		Item musicBox = new Item ("musicbox", Item.g_NOTHEAVY);
		Item couch = new Item("couch", Item.g_HEAVY);
		
		// ----- Add Items.
		World.livingRoom.addItem(officeKey.getName(), officeKey);
		World.livingRoom.addItem(musicBox.getName(), musicBox);
		World.livingRoom.addItem(couch.getName(), couch);

		// ----- Create NPCs.
		NPC brother = new Brother();

		// ----- Add NPCs.
		World.livingRoom.addNPC(brother.getName(), brother);
		
		// Office.
		// ----- Set Exits.
		World.office.setExit(livingRoom, Direction.EAST);

		// ----- Set Lock.
		World.office.lockRoom();
		
		// ----- Create Items.
		Item tool = new Tool();
		
		// ----- Add Items.
		World.office.addItem(tool.getName(), tool);
		
		// Bedroom.
		// ----- Set Exits.
		World.bedRoom.setExit(livingRoom, Direction.DOWN);
		World.bedRoom.setExit(attic, Direction.UP);
		
		// ----- Create Items.
		Item bed = new Item("bed", Item.g_HEAVY);
		
		// ----- Add Items.
		World.bedRoom.addItem(bed.getName(), bed);
		
		// Kitchen.
		// ----- Set Exits.
		World.kitchen.setExit(livingRoom, Direction.WEST);
		World.kitchen.setExit(garage, Direction.SOUTH);
		
		// ----- Create Items.
		Item flashLight = new Item("flashlight", Item.g_NOTHEAVY);
		Item knife = new Item("knife", Item.g_NOTHEAVY);
		Item orb = new Item("orb", Item.g_NOTHEAVY);
		
		// ----- Add Items.
		World.kitchen.addItem(flashLight.getName(), flashLight);
		World.kitchen.addItem(knife.getName(), knife);
		World.kitchen.addItem(orb.getName(), orb);
		
		// Garage.
		// ----- Set Exits.
		World.garage.setExit(kitchen, Direction.NORTH);

		// ----- Set Lock.
		World.garage.lockRoom();
		
		// Attic.
		// ----- Set Exits.
		World.attic.setExit(bedRoom, Direction.DOWN);

		// ----- Create Items.
		Safe safe = new Safe();

		// ----- Add Items.
		World.attic.addItem(safe.getName(), safe);

		// ----- Set Lock.
		World.attic.lockRoom();

		// Set starting room to living room.
		return World.livingRoom;
	}
	
	public static void main(String[] args) {
		Game.loadRoomDescriptions();
		
		// Living Room.
		Room currentRoom = buildWorld();
		
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