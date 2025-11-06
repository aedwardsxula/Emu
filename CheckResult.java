import java.util.ArrayList;
import java.util.List;


public class CheckResult {

    private boolean passed;
    private List<String> messages;

    
    public CheckResult() {
        this.passed = true;              
        this.messages = new ArrayList<>(); 
    }



    
    public void addMessage(String message) {
        this.messages.add(message);
        this.passed = false;
    }

 
    
    }
 
