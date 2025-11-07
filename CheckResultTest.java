import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

public class CheckResultTest {

    // 1. new object should start as passed
    @Test
    public void constructor_startsPassed() {
        CheckResult cr = new CheckResult();
        assertTrue(cr.isPassed());
    }

    // 2. new object should have empty messages
    @Test
    public void constructor_startsWithEmptyMessages() {
        CheckResult cr = new CheckResult();
        assertEquals(0, cr.getMessages().size());
    }

// 3. adding one message stores it
    @Test
    public void addMessage_storesMessage() {
        CheckResult cr = new CheckResult();
        cr.addMessage("Missing period.");
        assertEquals("Missing period.", cr.getMessages().get(0));
    }

    // 4. adding one message makes it fail
    @Test
    public void addMessage_setsPassedFalse() {
        CheckResult cr = new CheckResult();
        cr.addMessage("Missing subject.");
        assertFalse(cr.isPassed());
    }
    // 5. adding two messages stores both
    @Test
    public void addTwoMessages_bothStored() {
        CheckResult cr = new CheckResult();
        cr.addMessage("msg1");
        cr.addMessage("msg2");
        assertEquals(2, cr.getMessages().size());
    }

    // 6. adding two messages keeps order
    @Test
    public void addTwoMessages_keepsOrder() {
        CheckResult cr = new CheckResult();
        cr.addMessage("first");
        cr.addMessage("second");
        assertEquals("first", cr.getMessages().get(0));
        assertEquals("second", cr.getMessages().get(1));
    }
    // 7. isPassed stays false after multiple messages
    @Test
    public void multipleMessages_staysFailed() {
        CheckResult cr = new CheckResult();
        cr.addMessage("one");
        cr.addMessage("two");
        assertFalse(cr.isPassed());
    }

    // 8. getMessages returns same list that grows
    @Test
    public void getMessages_reflectsNewMessages() {
        CheckResult cr = new CheckResult();
        List<String> msgs = cr.getMessages();
        cr.addMessage("added later");
        assertEquals(1, msgs.size());
    }
    // 9. messages list can be read without exceptions
    @Test
    public void getMessages_notNull() {
        CheckResult cr = new CheckResult();
        assertNotNull(cr.getMessages());
    }

    // 10. addMessage accepts short message
    @Test
    public void addMessage_shortMessage() {
        CheckResult cr = new CheckResult();
        cr.addMessage("a");
        assertEquals("a", cr.getMessages().get(0));
    }
    
    11. addMessage accepts longer message
    @Test
    public void addMessage_longMessage() {
        CheckResult cr = new CheckResult();
        String longMsg = "This is a longer grammar error message.";
        cr.addMessage(longMsg);
        assertEquals(longMsg, cr.getMessages().get(0));
    }

    12. after construction, messages size is zero
    @Test
    public void messagesSize_zeroAtStart() {
        CheckResult cr = new CheckResult();
        assertEquals(0, cr.getMessages().size());
    }

     // 13. after one add, messages size is one
    @Test
    public void messagesSize_oneAfterAdd() {
        CheckResult cr = new CheckResult();
        cr.addMessage("x");
        assertEquals(1, cr.getMessages().size());
    }

    // 14. after three adds, messages size is three
    @Test
    public void messagesSize_threeAfterThreeAdds() {
        CheckResult cr = new CheckResult();
        cr.addMessage("1");
        cr.addMessage("2");
        cr.addMessage("3");
        assertEquals(3, cr.getMessages().size());
    }

    // 15. isPassed true before any adds
    @Test
    public void isPassed_trueBeforeAdds() {
        CheckResult cr = new CheckResult();
        assertTrue(cr.isPassed());
    }

     // 16. isPassed false immediately after first add
    @Test
    public void isPassed_falseImmediateAfterAdd() {
        CheckResult cr = new CheckResult();
        cr.addMessage("error");
        assertFalse(cr.isPassed());
    }

    // 17. getMessages returns List interface type
    @Test
    public void getMessages_isAList() {
        CheckResult cr = new CheckResult();
        assertTrue(cr.getMessages() instanceof List);
    }

     // 18. addMessage can be called multiple times in a row
    @Test
    public void addMessage_manyTimes_noCrash() {
        CheckResult cr = new CheckResult();
        for (int i = 0; i < 5; i++) {
            cr.addMessage("err " + i);
        }
        assertEquals(5, cr.getMessages().size());
        assertFalse(cr.isPassed());
    }
    


}