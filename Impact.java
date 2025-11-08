import java.util.*;

public class Impact {
    public Integer goal;
    public Integer raised;
    public String updated;
    public Map<String,Integer> metrics;

    public Impact(Integer goal, Integer raised, String updated, Map<String,Integer> metrics) {
        this.goal = goal;
        this.raised = raised;
        this.updated = updated;
        this.metrics = metrics;
    }

    @Override
    public String toString() {
        return "Goal: " + goal + "\nRaised: " + raised + "\nUpdated: " + updated + "\nMetrics: " + metrics;
    }
}

