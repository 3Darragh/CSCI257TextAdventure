package Labs;

public class Brother extends NPC {
    public Brother() {
        super("brother");
    }

    @Override
    public String talk() {
        String[] options = {
            "How do I get into the attic?",
            "Why do we live in a house where almost every room is locked?",
            "What is your problem?"
        };

        String[] responses = {
            "You must take the tool from the office to unlock the ladder in the bedroom.",
            "I do not know but I hate living here.",
            "You."
        };

        String option = getResponse(options, responses);
        String response;

        switch (option) {
            case "How do I get into the attic?" -> response = responses[0];
            case "Why do we live in a house where almost every room is locked?" -> response = responses[1];
            case "What is your problem?" -> response = responses[2];
            default -> response = "option";
        }

        return say(response);
    }
}
