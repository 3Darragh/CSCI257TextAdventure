
package Game;

import java.awt.*;
import javax.swing.*;

public class GameGUI extends JFrame {

    // The parts of the GUI
    JTextArea description;
    JTextArea inventory;
    JTextField command;
    JLabel output;

    public GameGUI() {

        // 1. Set up the window
        setTitle("Text Adventure");
        setSize(700, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // 2. Create the room description area
        description = new JTextArea();
        description.setEditable(false);
        description.setLineWrap(true);
        description.setWrapStyleWord(true);

        // 3. Create the inventory area
        inventory = new JTextArea();
        inventory.setEditable(false);

        // 4. Create the output section
        output = new JLabel("Welcome to the game!");

        // 5. Create the command input
        command = new JTextField();

        command.addActionListener(e -> {
            String input = command.getText().toLowerCase().trim();
            input = input.replaceAll("\\bthe\\s+", "");

            if (input.startsWith("go ")) {
                output.setText(Game.move(input.substring(3)));

            } else if (input.startsWith("take ")) {
                output.setText(Game.takeItem(input.substring(5)));

            } else if (input.startsWith("open ")) {
                output.setText(Game.openItem(input.substring(5)));

            } else if (input.startsWith("read ")) {
                output.setText(Game.readItem(input.substring(5)));

            } else if (input.startsWith("close ")) {
                output.setText(Game.closeItem(input.substring(6)));

            } else if (input.startsWith("use ")) {
                output.setText(Game.useItem(input.substring(4)));

            } else if (input.equals("inventory")) {
                output.setText("You checked your inventory.");

            } else if (input.equals("exit")) {
                dispose();
                return;
            } else if (input.equals("save")) {
            	output.setText(Game.saveGame());
            } else if (input.equals("load")) {
            	output.setText(Game.loadGame());
            } else if (!input.isEmpty()) {
                output.setText("Unknown command.");
            }

            // Refresh the room description
            description.setText(Game.currentRoom.getDescription());

            // Refresh the inventory
            inventory.setText(Game.getInventoryText());

            // Clear the command input
            command.setText("");
        });

        // 6. Add titles and scroll bars
        JScrollPane descriptionScroll = new JScrollPane(description);
        descriptionScroll.setBorder(
            BorderFactory.createTitledBorder("Current Room")
        );

        JScrollPane inventoryScroll = new JScrollPane(inventory);
        inventoryScroll.setBorder(
            BorderFactory.createTitledBorder("Inventory")
        );

        // 7. Put description and inventory side by side
        JPanel center = new JPanel(new GridLayout(1, 2));
        center.add(descriptionScroll);
        center.add(inventoryScroll);

        // 8. Create the bottom panel
        JPanel bottom = new JPanel(new BorderLayout(5, 5));

        // Output message above the command input
        bottom.add(output, BorderLayout.NORTH);

        // Command label and input field
        JPanel commandPanel = new JPanel(new BorderLayout());
        commandPanel.add(new JLabel("Command: "), BorderLayout.WEST);
        commandPanel.add(command, BorderLayout.CENTER);

        bottom.add(commandPanel, BorderLayout.SOUTH);

        // Add some spacing around the bottom panel
        bottom.setBorder(
            BorderFactory.createEmptyBorder(5, 10, 10, 10)
        );

        // 9. Add everything to the window
        add(center, BorderLayout.CENTER);
        add(bottom, BorderLayout.SOUTH);

        // 10. Load the starting room description
        description.setText(Game.currentRoom.getDescription());

        // Load the starting inventory
        inventory.setText(Game.getInventoryText());
    }

    public static void main(String[] args) {
        Game.loadRoomDescriptions();
        Game.currentRoom = World.buildWorld();

        SwingUtilities.invokeLater(() -> {
            new GameGUI().setVisible(true);
        });
    }
}
