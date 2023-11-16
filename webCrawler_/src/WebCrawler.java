import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.*;
import java.util.*;


public class WebCrawler {
    private static final int MAX_DEPTH = 3;
    private Set<String> visitedURLs;

    public WebCrawler() {
        this.visitedURLs = new HashSet<>();
    }

    public void crawl(String seedURL, int depth) {
        if (depth <= MAX_DEPTH) {
            System.out.println("Depth: " + depth + " - Visiting: " + seedURL);

            try {
                Document document = Jsoup.connect(seedURL).get();
                visitedURLs.add(seedURL);

                Elements links = document.select("a[href]");
                for (Element link : links) {
                    String nextURL = link.absUrl("href");
                    if (!visitedURLs.contains(nextURL)) {
                        crawl(nextURL, depth + 1);
                    }
                }
            } catch (IOException e) {
                System.err.println("Error fetching URL: " + seedURL);
            }
        }
    }
    public static void main(String[] args) {
        WebCrawler webCrawler = new WebCrawler();

        // Inserisci il tuo seed URL qui
        String seedURL = "https://example.com";
        webCrawler.crawl(seedURL, 1);
    }
}
