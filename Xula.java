import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class Xula {

    public static void main(String[] args) {
        Xula driver = new Xula();
        driver.run();
    }

    XulaScraper.scrapeCentennialCampaignImpact();
    
    public void run() {
        System.out.println("==================================================");
        System.out.println("   XULA Grammar Checker – Team Driver (Step 6)");
        System.out.println("   Preparing project files...");
        System.out.println("==================================================");

        final String projectDir = System.getProperty("user.dir");
        final String grammarDirPath = projectDir + File.separator + "GrammarFiles";
        File grammarDir = new File(grammarDirPath);

        if (!grammarDir.exists()) {
            boolean made = grammarDir.mkdir();
            if (made) {
                System.out.println("Created folder: " + grammarDirPath);
            } else {
                System.out.println("⚠ Could not create GrammarFiles folder. Proceeding anyway.");
            }
        }

        File nounsTarget = new File(grammarDir, "nouns.txt");
        if (!nounsTarget.exists()) {
            File nounsSource = new File(projectDir, "nouns2.txt");
            if (nounsSource.exists()) {
                try {
                    copyFile(nounsSource, nounsTarget);
                    System.out.println("Copied nouns2.txt -> GrammarFiles/nouns.txt");
                } catch (IOException e) {
                    System.out.println("⚠ Could not copy nouns2.txt: " + e.getMessage());
                }
            } else {
                System.out.println("⚠ nouns2.txt not found in project root – please add it.");
            }
        }

        File verbsTarget = new File(grammarDir, "verbs.txt");
        if (!verbsTarget.exists()) {
            File verbsSource = new File(projectDir, "verbs.txt");
            if (verbsSource.exists()) {
                try {
                    copyFile(verbsSource, verbsTarget);
                    System.out.println("Copied verbs.txt -> GrammarFiles/verbs.txt");
                } catch (IOException e) {
                    System.out.println("⚠ Could not copy verbs.txt: " + e.getMessage());
                }
            } else {
                System.out.println("⚠ verbs.txt not found in project root – please add it.");
            }
        }

        System.out.println("Environment ready. Launching Grammar checker...");
        System.out.println("--------------------------------------------------");

        try {
            Grammar.main(new String[0]);   
            System.out.println("There was a problem running the grammar checker: " + e.getMessage());
        }

        System.out.println("--------------------------------------------------");
        System.out.println("XULA driver finished.");
    }

    private static void copyFile(File src, File dest) throws IOException {
        try (FileInputStream in = new FileInputStream(src);
             FileOutputStream out = new FileOutputStream(dest)) {
            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = in.read(buffer)) != -1) {
                out.write(buffer, 0, bytesRead);
            }
        }
    }
}
