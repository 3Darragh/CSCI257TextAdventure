package Game;

import java.util.ArrayList;
import java.util.Arrays;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Scanner;
import java.util.HashMap;

public class Game {
	public static HashMap<String, String> roomDescriptions = new HashMap<>();
	public static Room currentRoom;
	private static HashMap<String, Item> inventory = new HashMap<>();
	private static ArrayList<Room> world = World.createRoomArrayList();
		
	public static void runGame() {
		Scanner scanner = new Scanner(System.in);
	
		boolean isExit = false;
		
		// While game is running.
		while(isExit == false) {
			// Assign input, modify and trim, split input.
			String input = scanner.nextLine().toLowerCase().trim();
			input = input.replaceAll("\\b(?:to|the)\\s+", "");
			String[] splitInput = input.split("\\s+");
			
			// Handle the exit command.
			if (splitInput[0].equals("exit")) {
				isExit = true;
				continue;
			}
			
			// Move in direction.
			if (splitInput[0].equals("go") && splitInput.length > 1) {
				move(splitInput[1]);
			}
			
			// Take item.
			if (splitInput[0].equals("take") && splitInput.length > 1) {
				takeItem(splitInput[1]);
			}
			
			// Save game.
			if (splitInput[0].equals("save")) {
				saveGame();
			}
			
			// Load game.
			if (splitInput[0].equals("load")) {
				loadGame();
			}
		}
		
		scanner.close();
	}
	
	// Move in direction.
	public static String move(String pDirection) {
		Direction direction;
		
		switch(pDirection) {
		case "east":
			direction = Direction.EAST;
			break;
		
		case "west":
			direction = Direction.WEST;
			break;
		
		case "north":
			direction = Direction.NORTH;
			break;
		
		case "south":
			direction = Direction.SOUTH;
			break;
		
		case "up":
			direction = Direction.UP;
			break;
		
		case "down":
			direction = Direction.DOWN;
			break;
		
		default:
			return "Unknown direction: " + pDirection + ".";
		}
		
	    if (currentRoom.getExit(direction) != null) {
	        currentRoom = currentRoom.getExit(direction);
	        return "You moved " + pDirection + ".";
	    } else {
	        return "You cannot move that way.";
	    }
	}
	
	// Take an item from currentRoom to inventory.
	public static String takeItem(String itemName) {
	    Item item = currentRoom.getItem(itemName);

	    if (item == null) {
	        return "Could not find the " + itemName;
	    } else if (item.isHeavy()) {
	        return "You cannot lift the " + item.getName();
	    } else {
	        inventory.put(item.getName(), item);
	        currentRoom.removeItem(itemName);
	        return "You took " + item.getName() + ".";
	    }
	}
	
	// Returns the inventory in text for the GUI.
	public static String getInventoryText() {
	    if (inventory.isEmpty()) {
	        return "You do not have any items!";
	    }

	    String text = "";

	    for (Item item : inventory.values()) {
	        text += item.getName() + "\n";
	    }

	    return text;
	}

	// Gets the descriptions from Descriptions.txt and adds them to roomDescriptions.
	public static void loadRoomDescriptions() {
		try {
			Scanner fileScanner = new Scanner(new File("GameDescriptions.txt"));
			fileScanner.useDelimiter("#");
			
			// While there is a next line.
			while (fileScanner.hasNext()) {
				// Get the text for the room and split it.
				String roomText = fileScanner.next().trim();
				String[] roomLines = roomText.split("\\R", 2);
				
				// Add the first line as the key, then the rest as the descriptions.
				if (roomLines.length == 2) {
					roomDescriptions.put(roomLines[0].trim(), roomLines[1].trim());
				}
			}
			fileScanner.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	// Saves the game.
	public static String saveGame() {
		File saveFile = new File("saveFile.sav");

		try (ObjectOutputStream outputStream =
				new ObjectOutputStream(new FileOutputStream(saveFile))) {
			outputStream.writeObject(currentRoom);
			outputStream.writeObject(inventory);
			outputStream.writeObject(world);
			return "Game saved.";
		} catch (Exception ex) {
			return "Could not save game: " + ex.getMessage();
		}
	}
	
	// Loads the game.
	public static String loadGame() {
		File saveFile = new File("saveFile.sav");

		try (ObjectInputStream inputStream =
				new ObjectInputStream(new FileInputStream(saveFile))) {
			currentRoom = (Room) inputStream.readObject();
			inventory = (HashMap<String, Item>) inputStream.readObject();
			world = (ArrayList<Room>) inputStream.readObject();
			return "Game loaded.";
		} catch (Exception ex) {
			return "Could not load game: " + ex.getMessage();
		}
	}
	
	public static void main(String[] args) {
		loadRoomDescriptions();
		currentRoom = World.buildWorld();
		runGame();
	}
}