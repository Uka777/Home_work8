package guru.qa.model;
import java.util.List;
public class Analyzes {
    public String name;
    public int age;
    public boolean isGoodTeacher;
    public List<String> lessons;
    public Teacher.Passport passport;

    public static class Passport {
        public int number;
        public String issueDate;
    }
}
