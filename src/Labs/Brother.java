package Labs;

public class Brother extends NPC {
    public Brother() {
        super("brother");
    }

    @Override
    public String talk() {
        String response;
        switch (getConversationNumber()) {
            case 0 -> response = getConversation0();
            case 1 -> response = getConversation1();
            case 2 -> response = getConversation2();
            case 3 -> response = getConversation3();
            default -> response = "";
        }
        return response;
    }

    public String optionLookup(int pOption) {
        String option;
        switch (pOption) {
            case 1 -> option = "How do I get into the attic?";
            case 2 -> option = "How do I get into the office?";
            case 3 -> option = "Where is the orb?";
            case 4 -> option = "Yes.";
            case 5 -> option = "No.";
            case 6 -> option = "Do you still need help getting into the attic?";
            case 7 -> option = "Do you still need help getting into the office?";
            case 8 -> option = "Did you find the orb?";
            default -> option = "";
        }
        return option;
    }

    public String responseLookup(int pResponse) {
        String response;
        switch (pResponse) {
            case 1 -> response = "You must find the tool from the office to unlock the attic door the bedroom.";
            case 2 -> response = "You must find the officekey in this room and use it to unlock the door.";
            case 3 -> response = "The orb is in the kitchen.";
            case 4 -> response = "Good luck getting into the attic then.";
            case 5 -> response = "Good luck getting into the office then.";
            case 6 -> response = "The orb is mysterious and I do not know its purpose. I am glad you found it.";
            case 7 -> response = "Come back to me when you found the orb, or if you need help finding it.";
            default -> response = "";
        }
        return response;
    }

    public String getConversation0() {
        String[] options = { 
            optionLookup(1), 
            optionLookup(2), 
            optionLookup(3) 
        };

        int choice = getResponse(options);
        switch (choice) {
            case 1 -> {
                setConversationNumber(1);
                return responseLookup(1);
            }
            case 2 -> {
                setConversationNumber(2);
                return responseLookup(2);
            }
            case 3 -> {
                setConversationNumber(3);
                return responseLookup(3);
            }
            default -> {
                return "Please input 1, 2, or 3.";
            }
        }
    }

    public String getConversation1() {
        String[] options = {
        	optionLookup(4),
        	optionLookup(5)
        };
        
        int answer = getResponse(optionLookup(6), options);

        switch (answer) {
            case 1 -> {
            	return responseLookup(1);
            }
            case 2 -> {
            	setConversationNumber(0);
            	return responseLookup(4);
            }
            default -> {
            	return "Please input 1 or 2.";
            }
        }
    }
    
    public String getConversation2() {
    	String[] options = {
            optionLookup(4),
            optionLookup(5)
        };
            
        int answer = getResponse(optionLookup(7), options);

        switch (answer) {
        	case 1 -> {
        		return responseLookup(2);
            }
            case 2 -> {
            	setConversationNumber(0);
                return responseLookup(5);
            }
            default -> {
            	return "Please input 1 or 2.";
            }
        }
    }
    
    public String getConversation3() {
    	String[] options = {
            optionLookup(4),
            optionLookup(5),
            optionLookup(3)
        };
            
        int answer = getResponse(optionLookup(8), options);

        switch (answer) {
        	case 1 -> {
            	setConversationNumber(0);
        		return responseLookup(6);
            }
            case 2 -> {
                return responseLookup(7);
            }
            case 3 -> {
            	return responseLookup(3);
            }
            default -> {
            	return "Please input 1, 2, or 3.";
            }
        }
    }
}
