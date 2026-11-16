package Game;

import java.util.ArrayList;

public class World {
	private static Room grounds;
	private static Room stables;
	private static Room quarters;
	private static Room hall;
	private static Room spire;
	private static Room throne;

	public static ArrayList<Room> roomList;

	public World() {
		grounds = new Room("The Castle Grounds: ");
		stables = new Room("The Stables: ");
		quarters = new Room("The Living Quarters: ");
		hall = new Room("The Dining Hall: ");
		spire = new Room("The Spire: ");
		throne = new Room("The Throne Room: ");

		roomList = new ArrayList<>(6);
		roomList.add(grounds);
		roomList.add(stables);
		roomList.add(quarters);
		roomList.add(hall);
		roomList.add(spire);
		roomList.add(throne);
	}
	
	public static Room buildWorld() {
		new World();
		
		// The Grounds.
		// ----- Set Exits.
		grounds.setExit(stables, Direction.WEST);
		grounds.setExit(quarters, Direction.SOUTH);
		
		// Stables.
		// ----- Set Exits.
		stables.setExit(grounds, Direction.EAST);
		stables.setExit(hall, Direction.SOUTH);
		stables.setExit(spire, Direction.UP);
		
		// ----- Create Items.
		Horse horse = new Horse();

		// ----- Add Items.
		stables.addItem(horse.getName(), horse);
		
		// ----- Set Lock.
		spire.lockRoom();
		
		// Quarters.
		// ----- Set Exits.
		quarters.setExit(grounds, Direction.NORTH);
		quarters.setExit(hall, Direction.WEST);

		// ----- Create Items.
		ThroneKey thronekey = new ThroneKey(throne);

		// ----- Add Items.
		quarters.addItem(thronekey.getName(), thronekey);
		
		// Hall.
		hall.setExit(quarters, Direction.EAST);
		hall.setExit(stables, Direction.NORTH);
		hall.setExit(throne, Direction.SOUTH);

		// ----- Create Items.
		Meal meal = new Meal();

		// ----- Add Items.
		hall.addItem(meal.getName(), meal);
		
		// Spire.
		// ----- Set Exits.
		spire.setExit(stables, Direction.DOWN);

		// ----- Create Items.
		Item book = new Item("book", Item.g_NOTHEAVY);

		// ----- Add Items.
		throne.addItem(book.getName(), book);

		
		spire.lockRoom();
		
		// Throne Room.
		// ----- Set Exits.
		throne.setExit(hall, Direction.NORTH);
		
		// ----- Create Items.
		Crown crown = new Crown();
		throne.addItem(crown.getName(), crown);
		
		// ----- Add Items.
		Item throneChair = new Item("throne", Item.g_NOTHEAVY);
		throne.addItem(throneChair.getName(), throneChair);

		// ----- Set Lock.
		throne.lockRoom();
		
		return grounds;
	}
}