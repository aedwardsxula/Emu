import static org.junit.Assert.*;
import org.junit.Test;

public class ScraperTest {

    private static final String SAMPLE_HTML =
        "<html><body><h2>Centennial Campaign Impact</h2>" +
        "<div>Campaign Goal: $500,000,000</div>" +
        "<div>Total Raised: $105,000,000</div>" +
        "<div>Updated: January 17, 2025</div>" +
        "<ul><li>Scholarships awarded: 312</li><li>Endowed chairs funded: 7</li></ul>" +
        "</body></html>";

    private static final String ALT_HTML_MILLION =
        "<html><body>Centennial Goal 500 million Raised so far 100 million Updated: 2024-03-06</body></html>";

    private static final String NO_NUMBERS_HTML =
        "<html><body><h2>Impact</h2><p>Learn more about our Centennial Campaign.</p></body></html>";

    private static final String MIXED_LABELS_HTML =
        "<html><body>To Date: $101,000,000 in support of a $500,000,000 Goal Updated: 2025-05-08 " +
        "New buildings funded: 3 Research grants: 24</body></html>";

    @Test public void parse_basic_sample() {
        Impact impact = Scraper.parse(SAMPLE_HTML);
        assertEquals(Integer.valueOf(500_000_000), impact.goal);
        assertEquals(Integer.valueOf(105_000_000), impact.raised);
        assertEquals("January 17, 2025", impact.updated);
        assertEquals(Integer.valueOf(312), impact.metrics.get("Scholarships awarded"));
        assertEquals(Integer.valueOf(7), impact.metrics.get("Endowed chairs funded"));
    }

    @Test public void parse_time_iso_and_million_word() {
        Impact impact = Scraper.parse(ALT_HTML_MILLION);
        assertEquals(Integer.valueOf(500_000_000), impact.goal);
        assertEquals(Integer.valueOf(100_000_000), impact.raised);
        assertEquals("2024-03-06", impact.updated);
    }

    @Test public void parse_no_numbers_graceful() {
        Impact impact = Scraper.parse(NO_NUMBERS_HTML);
        assertNull(impact.goal);
        assertNull(impact.raised);
        assertNotNull(impact.metrics);
    }

    @Test public void parse_mixed_labels() {
        Impact impact = Scraper.parse(MIXED_LABELS_HTML);
        assertEquals(Integer.valueOf(101_000_000), impact.raised);
        assertEquals(Integer.valueOf(500_000_000), impact.goal);
        assertEquals("2025-05-08", impact.updated);
    }

    @Test public void tolerates_whitespace() {
        String html = SAMPLE_HTML.replace("$500,000,000", "  $500,000,000   ");
        Impact impact = Scraper.parse(html);
        assertEquals(Integer.valueOf(500_000_000), impact.goal);
    }

    @Test public void metrics_have_at_least_two() {
        Impact impact = Scraper.parse(SAMPLE_HTML);
        assertTrue(impact.metrics.size() >= 2);
    }

    @Test public void labels_case_insensitive() {
        String html = SAMPLE_HTML.replace("Total Raised", "total raised");
        Impact impact = Scraper.parse(html);
        assertEquals(Integer.valueOf(105_000_000), impact.raised);
    }

    @Test public void million_multiplier_applies() {
        Impact impact = Scraper.parse("Goal 500 million Raised 100 million");
        assertEquals(Integer.valueOf(500_000_000), impact.goal);
        assertEquals(Integer.valueOf(100_000_000), impact.raised);
    }

    @Test public void ignores_goal_raised_in_metrics() {
        Impact impact = Scraper.parse("Goal: 1,000 Raised: 2,000 Scholarships awarded: 10");
        assertEquals(Integer.valueOf(10), impact.metrics.get("Scholarships awarded"));
        assertNull(impact.metrics.get("Goal"));
    }

    @Test public void date_may_be_absent() {
        Impact impact = Scraper.parse("Goal: 1,000 Raised: 2,000");
        assertNull(impact.updated);
    }
}
