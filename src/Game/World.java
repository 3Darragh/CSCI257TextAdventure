package Game;

import java.util.ArrayList;

public class World {
	
	public static Room buildWorld() {
		
		Room grounds = new Room(
				"The Castle Grounds: "
		);
		
		Room stables = new Room(
				"The Stables: "
		);
		
		Room quarters = new Room(
				"The Living Quarters: "
		);
		
		Room hall = new Room(
				"The Dining Hall: "
		);
		
		Room spire = new Room(
				"The Spire: "
		);
		
		Room throne = new Room(
				"The Throne Room: "
		);
		
		// The Grounds.
		grounds.setExit(stables, Direction.WEST);
		grounds.setExit(quarters, Direction.SOUTH);
		
		
		// Stables.
		stables.setExit(grounds, Direction.EAST);
		stables.setExit(hall, Direction.SOUTH);
		stables.setExit(spire, Direction.UP);
		
		// Horse item.
		Item horse = new Item("horse", Item.gHeavy);
		stables.addItem(horse.getName(), horse);
		
		// Quarters.
		quarters.setExit(grounds, Direction.NORTH);
		quarters.setExit(hall, Direction.WEST);
		
		// Hall.
		hall.setExit(quarters, Direction.EAST);
		hall.setExit(stables, Direction.NORTH);
		hall.setExit(throne, Direction.SOUTH);
		
		// Spire.
		spire.setExit(stables, Direction.DOWN);
		
		// Book item.
		Item book = new Item("book", Item.gNotHeavy);
		throne.addItem(book.getName(), book);
		
		// Throne.
		throne.setExit(hall, Direction.NORTH);
		
		// Crown item.
		Item crown = new Item("crown", Item.gNotHeavy);
		throne.addItem(crown.getName(), crown);
		
		// Throne item.
		Item throneChair = new Item("throne", Item.gHeavy);
		throne.addItem(throneChair.getName(), throneChair);
		
		// Set starting room to living room.
		return grounds;
	}
	
	// Puts all rooms and their properties into one arrayList.
	public static ArrayList<Room> createRoomArrayList() {
		ArrayList<Room> roomArrayList = new ArrayList<Room>();
		Room world = buildWorld();
		
		// Adds them to the list.
		roomArrayList.add(world); // Grounds
		roomArrayList.add(world.getExit(Direction.WEST)); // Stables
		roomArrayList.add(world.getExit(Direction.SOUTH)); // Quarters
		roomArrayList.add(world.getExit(Direction.WEST).getExit(Direction.SOUTH)); // Hall
		roomArrayList.add(world.getExit(Direction.WEST).getExit(Direction.SOUTH).getExit(Direction.SOUTH)); // Throne
		roomArrayList.add(world.getExit(Direction.WEST).getExit(Direction.UP)); // Spire

		return roomArrayList;
	}
}