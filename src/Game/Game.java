package Game;

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
	public static String state = "";
	public static boolean isRunning = true;
		
	public static void runGame(String pInput) {
		String input = pInput.toLowerCase().trim();
		input = input.replaceAll("\\bthe\\s+", "");
		input = input.replaceFirst("^(go|talk)\\s+to\\s+", "$1 ");
		input = input.replaceFirst("^take\\s+off\\b", "takeoff");
		if (input.isEmpty()) {
			state = "";
			return;
		}

		String[] splitInput = input.split("\\s+");
		String action = splitInput[0];
		String argument = splitInput.length > 1 ? splitInput[1] : "";

		if (argument.isEmpty()
				&& !action.equals("save")
				&& !action.equals("load")
				&& !action.equals("inventory")
				&& !action.equals("exit")) {
			state = "All commands require an argument.";
			return;
		}

		switch (action) {
			case "exit" -> {
				isRunning = false;
				state = "Goodbye.";
			}
			case "go" -> state = move(argument);
			case "take" -> state = takeItem(argument);
			case "takeoff" -> state = takeOffItem(argument);
			case "open" -> state = openItem(argument);
			case "read" -> state = readItem(argument);
			case "close" -> state = closeItem(argument);
			case "talk" -> state = talkTo(argument);
			case "use" -> state = useItem(argument);
			case "pet" -> state = petItem(argument);
			case "wear" -> state = wearItem(argument);
			case "give" -> state = giveItemFromCommand(input);
			case "inventory" -> state = getInventoryText();
			case "save" -> state = saveGame();
			case "load" -> state = loadGame();
			default -> state = "Unknown command.";
		}
	}

	public static void print(Object pObject) {
		System.out.println(pObject);
	}
	
	// Move in direction.
	public static String move(String pDirection) {
		Direction direction;
		
		switch(pDirection) {
			case "east" -> direction = Direction.EAST;
			case "west" -> direction = Direction.WEST;
			case "north" -> direction = Direction.NORTH;
			case "south" -> direction = Direction.SOUTH;
			case "up" -> direction = Direction.UP;
			case "down" -> direction = Direction.DOWN;
			default -> direction = null;
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
		return item.use();
	}

	public static String wearItem(String pItemName) {
		Item item = inventory.get(pItemName);
		if (item == null) {
			return "You do not have the " + pItemName + ".";
		}
		return item.wear();
	}

	public static String takeOffItem(String pItemName) {
		Item item = inventory.get(pItemName);
		if (item == null) {
			return "You do not have the " + pItemName + ".";
		}
		if (item instanceof Crown crown) {
			return crown.takeOff();
		}
		return "You cannot take off the " + pItemName + ".";
	}

	public static String giveItem(String pItemName, String pRecipientName) {
		Item offeredItem = inventory.get(pItemName);
		if (offeredItem == null) {
			return "You do not have the " + pItemName + ".";
		}
		if (currentRoom == null) {
			return "Could not give an item without a current room.";
		}

		if (offeredItem instanceof Crown
				&& currentRoom.getName().equals("The Castle Grounds: ")) {
			return offeredItem.itemToGive(offeredItem);
		}

		Item recipient = currentRoom.getItem(pRecipientName);
		if (recipient != null) {
			return offeredItem.giveItemTo(recipient);
		}

		NPC npc = currentRoom.getNPC(pRecipientName);
		if (offeredItem instanceof Meal
				&& pRecipientName.equals("guard")
				&& currentRoom.getName().equals("The Stables: ")) {
			return offeredItem.giveItemTo(null);
		}
		if (npc != null) {
			return "The " + npc.getName() + " does not accept that.";
		}

		return "Could not find the " + pRecipientName + " here.";
	}

	private static String giveItemFromCommand(String pInput) {
		int separatorIndex = pInput.indexOf(" to ");
		if (separatorIndex < "give ".length()) {
			return "Use: give <item> to <recipient>.";
		}

		String itemName = pInput.substring("give ".length(), separatorIndex).trim();
		String recipientName = pInput.substring(separatorIndex + " to ".length()).trim();
		if (itemName.isEmpty() || recipientName.isEmpty()) {
			return "Use: give <item> to <recipient>.";
		}
		return giveItem(itemName, recipientName);
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

	public static String talkTo(String pNPC) {
		NPC npc = currentRoom.getNPC(pNPC);
		return npc == null ? "Could not find " + pNPC + " in " + currentRoom.getName() + "." : npc.talk();
	}

	public static String petItem(String pItemName) {
		Item item = getActionItem(pItemName);
		return item == null ? "Could not find the " + pItemName + "." : item.pet();
	}

	private static Item getActionItem(String pItemName) {
		Item item = currentRoom.getItem(pItemName);
		if (item == null) {
			return inventory.get(pItemName);
		}
		return item;
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