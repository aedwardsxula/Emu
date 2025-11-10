import java.nio.file.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.regex.*;

public class Scraper {

    public static Impact parse(String html) {
        String text = html.replaceAll("<[^>]+>", " ");
        text = text.replaceAll("\\s+", " ").trim();

        Integer goal   = findAmount(text, "Campaign Goal|Centennial Goal|Goal");
        Integer raised = findAmount(text, "Total Raised|Raised|To Date|Amount Raised|Funds Raised");
        String updated = findDate(text);
        Map<String,Integer> metrics = findMetrics(text);

        return new Impact(goal, raised, updated, metrics);
    }

    public static Impact fromFile(String filename) throws Exception {
        String html = new String(Files.readAllBytes(Paths.get(filename)), StandardCharsets.UTF_8);
        return parse(html);
    }

    private static Integer findAmount(String text, String labels) {
        Pattern p = Pattern.compile("(?i)(" + labels + ").{0,120}?([0-9][0-9,]*)\\s*(million|m)?");
        Matcher m = p.matcher(text);
        if (!m.find()) return null;
        int base = Integer.parseInt(m.group(2).replace(",", ""));
        boolean million = m.group(3) != null && !m.group(3).isEmpty();
        if (million && base < 10_000_000) base *= 1_000_000;
        return base;
    }

    private static String findDate(String text) {
        Matcher m1 = Pattern.compile("(?i)Updated[: ]+([A-Za-z]{3,9}\\s+\\d{1,2},\\s+\\d{4})").matcher(text);
        if (m1.find()) return m1.group(1).trim();
        Matcher m2 = Pattern.compile("(?i)Updated[: ]+(\\d{4}-\\d{2}-\\d{2})").matcher(text);
        if (m2.find()) return m2.group(1).trim();
        return null;
    }

    private static Map<String,Integer> findMetrics(String text) {
        Map<String,Integer> map = new LinkedHashMap<>();
        Pattern p = Pattern.compile("([A-Za-z ]{3,40})[:]\\s*([0-9][0-9,]*)");
        Matcher m = p.matcher(text);
        while (m.find()) {
            String k = m.group(1).trim();
            String v = m.group(2).replace(",", "");
            if (!k.equalsIgnoreCase("goal") && !k.equalsIgnoreCase("raised")
                    && !k.equalsIgnoreCase("to date") && !k.equalsIgnoreCase("amount raised")
                    && !k.equalsIgnoreCase("total raised") && !k.equalsIgnoreCase("funds raised")) {
                map.put(k, Integer.parseInt(v));
            }
        }
        return map;
    }

    public static void main(String[] args) throws Exception {
        Impact i = fromFile("sample.html");
        System.out.println(i);
    }
}

