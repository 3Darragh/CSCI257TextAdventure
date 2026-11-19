package Labs;

import javax.swing.JOptionPane;

public class NPC {
    private String name;
    private int conversationNumber;

    public NPC(String pName) {
        name = pName;
        conversationNumber = 0;
    }

    public String getName() {
        return name;
    }

    public int getConversationNumber() {
        return conversationNumber;
    }

    public void setConversationNumber(int pConversationNumber) {
        conversationNumber = pConversationNumber;
    }

    public String say(String pDialog) {
        return name.toUpperCase() + "\n" + pDialog;
    }

    public String talk() {
        return say("You cannot talk to " + name + ".");
    }

    public int getResponse(String[] pOptions) {
        return getResponse("Choose an option:", pOptions);
    }

    public int getResponse(String pPrompt, String[] pOptions) {
        StringBuilder prompt = new StringBuilder(pPrompt);
        for (int i = 0; i < pOptions.length; i++) {
            prompt.append("\n").append(i + 1).append(". ").append(pOptions[i]);
        }

        String selection = JOptionPane.showInputDialog(null, prompt.toString());
        if (selection == null) {
            return 0;
        }

        try {
            int choice = Integer.parseInt(selection.trim());
            return choice >= 1 && choice <= pOptions.length ? choice : 0;
        } catch (NumberFormatException e) {
            return 0;
        }
    }
}
