package guru.qa.model;
import java.util.List;
public class Analyzes {
    public String name;
    public int referenceValues;
    public boolean isaAnalyzes;
    public List<String> measurement;
    public Analyzes.Id id;

    public static class Id {
        public int sequenceNumber;
        public String dateEvent;
    }
}
