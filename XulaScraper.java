import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;


public class XulaScraper {

    
    private static final String DEFAULT_URL = "https://www.xula.edu/about/centennial.html";
    private static final String DEFAULT_OUTPUT = "xula_centennial_campaign_impact.txt";

    
    public static String scrapeCentennialCampaignImpact() {
        return scrapeCentennialCampaignImpact(DEFAULT_URL, DEFAULT_OUTPUT);
    }

    
    public static String scrapeCentennialCampaignImpact(String pageUrl, String outputFile) {
        StringBuilder sb = new StringBuilder();
        HttpURLConnection conn = null;

        try {
            
            URL url = new URL(pageUrl);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("User-Agent", "Mozilla/5.0");

            
            try (BufferedReader reader = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()))) {

                String line;
                while ((line = reader.readLine()) != null) {
                    sb.append(line).append(System.lineSeparator());
                }
            }

            String pageContent = sb.toString();

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile))) {
                writer.write(pageContent);
            }

            System.out.println("[OK] Scraped Centennial Campaign Impact page and saved to " + outputFile);
            return pageContent;

        } catch (IOException e) {
            System.out.println("[ERROR] Could not scrape page: " + e.getMessage());
            return "";
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
    }
}
