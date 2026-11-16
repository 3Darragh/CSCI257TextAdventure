package Game;

import javax.swing.JOptionPane;

public class NPC {
    private String name;

    public NPC(String pName) {
        name = pName;
    }

    public String getName() {
        return name;
    }

    public String say(String pDialog) {
        return name.toUpperCase() + "\n" + pDialog;
    }

    public String talk() {
        return say("You cannot talk to " + name + ".");
    }

    public String getResponse(String[] pOptions, String[] pResponses) {
        String response;
        String options = JOptionPane.showInputDialog(null, 
            "Choose an option: \n" +
            pOptions[0] + "\n" +
            pOptions[1] + "\n" +
            pOptions[2] + "\n"
        );

        switch (options) {
            case "1" -> response = pOptions[0];
            case "2" -> response = pOptions[1];
            case "3" -> response = pOptions[2];
            default -> response = "Please input 1, 2, or 3.";
        }

        return response;
    }
}
