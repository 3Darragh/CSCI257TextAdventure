package Labs;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Scanner;

public class Game {
	public static HashMap<String, String> roomDescriptions = new HashMap<>();
	public static Room currentRoom;
	public static HashMap<String, Item> inventory = new HashMap<>();
		
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

			// Open an item.
			if (splitInput[0].equals("open") && splitInput.length > 1) {
				System.out.println(openItem(splitInput[1]));
			}

			// Read an item.
			if (splitInput[0].equals("read") && splitInput.length > 1) {
				System.out.println(readItem(splitInput[1]));
			}

			// Close an item.
			if (splitInput[0].equals("close") && splitInput.length > 1) {
				System.out.println(closeItem(splitInput[1]));
			}

			// Use an item.
			if (splitInput[0].equals("use") && splitInput.length > 1) {
				System.out.println(useItem(splitInput[1]));
			}
			
			// Save game.
			if (splitInput[0].equals("save")) {
				System.out.println(saveGame());
			}
			
			// Load game.
			if (splitInput[0].equals("load")) {
				System.out.println(loadGame());
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
			return "";
		}
		
	    if (currentRoom.getExit(direction) != null) {
			if (currentRoom.getExit(direction).isRoomLocked() == false) {
				currentRoom = currentRoom.getExit(direction);
				return "You moved " + pDirection + ".";
			} else {
				return "The door to that room is locked.";
			}
	    } else {
	        return "You cannot move that way.";
	    }
	}
	
	// Take an item from currentRoom to inventory.
	public static String takeItem(String pItemName) {
	    Item item = currentRoom.getItem(pItemName);

	    if (item == null) {
	        return "Could not find the " + pItemName;
	    } else if (item.isHeavy()) {
	        return "You cannot lift the " + item.getName();
	    } else {
	        addInventoryItem(item);
	        currentRoom.removeItem(pItemName);
	        return "You took " + item.getName() + ".";
	    }
	}

	public static void addInventoryItem(Item pItem) {
		inventory.put(pItem.getName(), pItem);
	}

	public static String useItem(String pItemName) {
		Item item = inventory.get(pItemName);
		if (item == null) {
			return "You do not have the " + pItemName + ".";
		}
		return item.use(currentRoom);
	}

	public static String openItem(String pItemName) {
		Item item = getActionItem(pItemName);
		return item == null ? "Could not find the " + pItemName + "." : item.open();
	}

	public static String readItem(String pItemName) {
		Item item = getActionItem(pItemName);
		return item == null ? "Could not find the " + pItemName + "." : item.read();
	}

	public static String closeItem(String pItemName) {
		Item item = getActionItem(pItemName);
		return item == null ? "Could not find the " + pItemName + "." : item.close();
	}

	private static Item getActionItem(String pItemName) {
		Item item = currentRoom.getItem(pItemName);
		return item == null ? inventory.get(pItemName) : item;
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
			Scanner fileScanner = new Scanner(new File("Descriptions.txt"));
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
		if (currentRoom == null) {
			return "Could not save game: no current room.";
		}

		File saveFile = new File("saveFile.sav");
		try (ObjectOutputStream outputStream =
				new ObjectOutputStream(new FileOutputStream(saveFile))) {
			outputStream.writeObject(currentRoom);
			outputStream.writeObject(inventory);
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
			Room loadedRoom = (Room) inputStream.readObject();
			@SuppressWarnings("unchecked")
			HashMap<String, Item> loadedInventory =
					(HashMap<String, Item>) inputStream.readObject();

			currentRoom = loadedRoom;
			inventory = loadedInventory;
			return "Game loaded.";
		} catch (Exception ex) {
			return "Could not load game: " + ex.getMessage();
		}
	}
	
	public static void main(String[] pArgs) {
		GameGUI.main(pArgs);
	}
}