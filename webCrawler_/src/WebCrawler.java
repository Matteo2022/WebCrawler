import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.*;
import java.util.*;


public class WebCrawler {
    private static final int profondita_MAX = 3;
    private Set<String> visitedURLs;    //Set<> garantisce elementi unici, è un'interfaccia

    public WebCrawler() {
        this.visitedURLs = new HashSet<>();
    }

    public void crawl(String seedURL, int profondita) {
        if (profondita <= profondita_MAX) {
            System.out.println("Profondità: " + profondita + " - Link visitato: " + seedURL);
            try {
                Document document = Jsoup.connect(seedURL).get();   //creo connessione al link passato ed ottengo il documento html
                visitedURLs.add(seedURL);
                Elements links = document.select("a[href]");    //seleziona i link presenti nel documento con tag <a>
                for (Element link : links) {
                    String nextURL = link.absUrl("href");   //ottengo l'URL assoluto del link
                    if (!visitedURLs.contains(nextURL)) {
                        crawl(nextURL, profondita + 1);
                    }
                }
            } catch (IOException e) {
                System.err.println("Error fetching URL: " + seedURL);
            }
        }
    }
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        WebCrawler webCrawler = new WebCrawler();
        System.out.println("Quale link vuoi visitare: ");
        String seedUrl = scanner.nextLine();
        webCrawler.crawl(seedUrl, 1);
        scanner.close();
    }
}
