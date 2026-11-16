
package Labs;

import java.awt.*;
import javax.swing.*;

public class GameGUI extends JFrame {

    // The parts of the GUI
    JTextArea description;
    JTextArea output;
    JTextField command;

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

        // 3. Create the output area
        output = new JTextArea("Welcome to the game!");
        output.setEditable(false);
        output.setLineWrap(true);
        output.setWrapStyleWord(true);

        // 5. Create the command input
        command = new JTextField();
        command.addActionListener(e -> {
            Game.runGame(command.getText());
            output.setText(Game.state);
            description.setText(Game.currentRoom.getDescription());
            command.setText("");
            if (!Game.isRunning) {
                dispose();
            }
        });

        // 6. Add titles and scroll bars
        JScrollPane descriptionScroll = new JScrollPane(description);
        descriptionScroll.setBorder(
            BorderFactory.createTitledBorder("Current Room")
        );

        JScrollPane outputScroll = new JScrollPane(output);
        outputScroll.setBorder(
            BorderFactory.createTitledBorder("Output")
        );

        // 7. Put description and output side by side
        JPanel center = new JPanel(new GridLayout(1, 2));
        center.add(descriptionScroll);
        center.add(outputScroll);

        // 8. Create the bottom panel
        JPanel bottom = new JPanel(new BorderLayout(5, 5));

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
    }

    public static void main(String[] args) {
        Game.loadRoomDescriptions();
        Game.currentRoom = World.buildWorld();

        SwingUtilities.invokeLater(() -> {
            new GameGUI().setVisible(true);
        });
    }
}
