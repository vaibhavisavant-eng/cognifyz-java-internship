import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Scanner;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class WebAnalyzer {

    static Scanner sc = new Scanner(System.in);

    // Maximum results displayed in the terminal
    static final int MAX_DISPLAY_RESULTS = 50;

    public static void main(String[] args) {

        System.out.println("========================================");
        System.out.println("        INTERACTIVE WEB ANALYZER");
        System.out.println("========================================");

        boolean running = true;

        while (running) {

            String url = readUrl();

            Document document = connectToWebsite(url);

            if (document == null) {
                continue;
            }

            boolean analyzing = true;

            while (analyzing) {

                displayMenu();

                int choice = readInteger(
                        "Enter your choice: ", 1, 8);

                switch (choice) {

                    case 1:
                        showTitle(document);
                        break;

                    case 2:
                        showHeadings(document);
                        break;

                    case 3:
                        showLinks(document, url);
                        break;

                    case 4:
                        showImages(document);
                        break;

                    case 5:
                        showStatistics(document, url);
                        break;

                    case 6:
                        showCompleteSummary(document, url);
                        break;

                    case 7:
                        analyzing = false;
                        System.out.println();
                        System.out.println(
                                "Returning to URL input...");
                        break;

                    case 8:
                        analyzing = false;
                        running = false;
                        break;
                }
            }
        }

        System.out.println();
        System.out.println("========================================");
        System.out.println(" Thank you for using Web Analyzer!");
        System.out.println("========================================");

        sc.close();
    }

    // ========================================
    // URL INPUT
    // ========================================

    static String readUrl() {

        while (true) {

            System.out.println();

            System.out.print(
                    "Enter website URL: ");

            String url = sc.nextLine().trim();

            if (url.isEmpty()) {

                System.out.println(
                        "URL cannot be empty.");

                continue;
            }

            // Add https:// if the user enters
            // a normal domain without a protocol.
            if (!url.startsWith("http://")
                    && !url.startsWith("https://")) {

                url = "https://" + url;
            }

            if (!isValidUrl(url)) {

                System.out.println(
                        "Invalid URL format."
                        + " Example: https://example.com");

                continue;
            }

            return url;
        }
    }

    // ========================================
    // URL VALIDATION
    // ========================================

    static boolean isValidUrl(String url) {

        try {

            URI uri = new URI(url);

            String scheme = uri.getScheme();

            String host = uri.getHost();

            return (scheme.equalsIgnoreCase("http")
                    || scheme.equalsIgnoreCase("https"))
                    && host != null
                    && !host.isEmpty();

        } catch (URISyntaxException e) {

            return false;
        }
    }

    // ========================================
    // CONNECT TO WEBSITE
    // ========================================

    static Document connectToWebsite(String url) {

        System.out.println();
        System.out.println(
                "Connecting to: " + url);

        try {

            Connection connection =
                    Jsoup.connect(url)
                            .userAgent(
                                    "Mozilla/5.0 "
                                    + "(Windows NT 10.0; Win64; x64) "
                                    + "AppleWebKit/537.36 "
                                    + "Chrome/120 Safari/537.36")
                            .timeout(10000)
                            .followRedirects(true);

            Connection.Response response =
                    connection.execute();

            int statusCode =
                    response.statusCode();

            if (statusCode < 200
                    || statusCode >= 400) {

                System.out.println(
                        "Unable to access the website.");

                System.out.println(
                        "HTTP Status: "
                        + statusCode
                        + " "
                        + response.statusMessage());

                return null;
            }

            Document document =
                    response.parse();

            System.out.println(
                    "Connection successful!");

            System.out.println(
                    "HTTP Status: "
                    + statusCode
                    + " "
                    + response.statusMessage());

            return document;

        } catch (IOException e) {

            System.out.println();
            System.out.println(
                    "Unable to connect to the website.");

            System.out.println(
                    "Possible reasons:");

            System.out.println(
                    "- Check your internet connection.");

            System.out.println(
                    "- The website may be unavailable.");

            System.out.println(
                    "- The website may block automated requests.");

            System.out.println(
                    "- The URL may be incorrect.");

            return null;
        }
    }

    // ========================================
    // MENU
    // ========================================

    static void displayMenu() {

        System.out.println();
        System.out.println(
                "========================================");

        System.out.println(
                "            ANALYZER MENU");

        System.out.println(
                "========================================");

        System.out.println(
                "1. View Page Title");

        System.out.println(
                "2. View Headings");

        System.out.println(
                "3. View Links");

        System.out.println(
                "4. View Images");

        System.out.println(
                "5. View Page Statistics");

        System.out.println(
                "6. View Complete Summary");

        System.out.println(
                "7. Analyze Another URL");

        System.out.println(
                "8. Exit");

        System.out.println(
                "========================================");
    }

    // ========================================
    // TITLE
    // ========================================

    static void showTitle(Document document) {

        System.out.println();
        System.out.println(
                "----------- PAGE TITLE -----------");

        String title = document.title();

        if (title.isEmpty()) {

            System.out.println(
                    "No page title found.");

        } else {

            System.out.println(
                    "Title: " + title);
        }
    }

    // ========================================
    // HEADINGS
    // ========================================

    static void showHeadings(Document document) {

        System.out.println();
        System.out.println(
                "----------- HEADINGS -----------");

        Elements headings =
                document.select("h1, h2, h3, h4, h5, h6");

        if (headings.isEmpty()) {

            System.out.println(
                    "No headings found.");

            return;
        }

        int count = 0;

        for (Element heading : headings) {

            count++;

            if (count > MAX_DISPLAY_RESULTS) {

                System.out.println(
                        "...More headings not displayed.");

                break;
            }

            System.out.println(
                    count
                    + ". ["
                    + heading.tagName().toUpperCase()
                    + "] "
                    + heading.text());
        }

        System.out.println();
        System.out.println(
                "Total headings found: "
                + headings.size());
    }

    // ========================================
    // LINKS
    // ========================================

    static void showLinks(
            Document document,
            String pageUrl) {

        System.out.println();
        System.out.println(
                "----------- LINKS -----------");

        Elements links =
                document.select("a[href]");

        if (links.isEmpty()) {

            System.out.println(
                    "No links found.");

            return;
        }

        int internalLinks = 0;
        int externalLinks = 0;

        int displayed = 0;

        for (Element link : links) {

            String absoluteUrl =
                    link.absUrl("href");

            if (absoluteUrl.isEmpty()) {

                continue;
            }

            if (isSameDomain(
                    pageUrl,
                    absoluteUrl)) {

                internalLinks++;

            } else {

                externalLinks++;
            }

            if (displayed < MAX_DISPLAY_RESULTS) {

                displayed++;

                String text =
                        link.text().trim();

                if (text.isEmpty()) {

                    text = "[No link text]";
                }

                System.out.println();
                System.out.println(
                        displayed + ". " + text);

                System.out.println(
                        "   URL: "
                        + absoluteUrl);
            }
        }

        if (links.size() > MAX_DISPLAY_RESULTS) {

            System.out.println();
            System.out.println(
                    "Showing first "
                    + MAX_DISPLAY_RESULTS
                    + " links.");
        }

        System.out.println();
        System.out.println(
                "Total Links    : "
                + links.size());

        System.out.println(
                "Internal Links : "
                + internalLinks);

        System.out.println(
                "External Links : "
                + externalLinks);
    }

    // ========================================
    // IMAGES
    // ========================================

    static void showImages(Document document) {

        System.out.println();
        System.out.println(
                "----------- IMAGES -----------");

        Elements images =
                document.select("img[src]");

        if (images.isEmpty()) {

            System.out.println(
                    "No images found.");

            return;
        }

        int displayed = 0;

        for (Element image : images) {

            if (displayed >= MAX_DISPLAY_RESULTS) {

                break;
            }

            displayed++;

            String imageUrl =
                    image.absUrl("src");

            String altText =
                    image.attr("alt").trim();

            if (altText.isEmpty()) {

                altText = "[No alt text]";
            }

            System.out.println();
            System.out.println(
                    displayed + ". Image");

            System.out.println(
                    "   URL: " + imageUrl);

            System.out.println(
                    "   Alt: " + altText);
        }

        if (images.size() > MAX_DISPLAY_RESULTS) {

            System.out.println();
            System.out.println(
                    "Showing first "
                    + MAX_DISPLAY_RESULTS
                    + " images.");
        }

        System.out.println();
        System.out.println(
                "Total Images: "
                + images.size());
    }

    // ========================================
    // PAGE STATISTICS
    // ========================================

    static void showStatistics(
            Document document,
            String pageUrl) {

        System.out.println();
        System.out.println(
                "----------- PAGE STATISTICS -----------");

        String text =
                document.body() == null
                        ? ""
                        : document.body().text();

        int characters =
                text.length();

        int words =
                countWords(text);

        int headings =
                document.select(
                        "h1, h2, h3, h4, h5, h6").size();

        int links =
                document.select("a[href]").size();

        int images =
                document.select("img[src]").size();

        int internalLinks = 0;
        int externalLinks = 0;

        for (Element link :
                document.select("a[href]")) {

            String absoluteUrl =
                    link.absUrl("href");

            if (absoluteUrl.isEmpty()) {

                continue;
            }

            if (isSameDomain(
                    pageUrl,
                    absoluteUrl)) {

                internalLinks++;

            } else {

                externalLinks++;
            }
        }

        System.out.println(
                "Characters     : " + characters);

        System.out.println(
                "Words          : " + words);

        System.out.println(
                "Headings       : " + headings);

        System.out.println(
                "Links          : " + links);

        System.out.println(
                "Images         : " + images);

        System.out.println(
                "Internal Links : " + internalLinks);

        System.out.println(
                "External Links : " + externalLinks);
    }

    // ========================================
    // COMPLETE SUMMARY
    // ========================================

    static void showCompleteSummary(
            Document document,
            String pageUrl) {

        System.out.println();
        System.out.println(
                "========================================");

        System.out.println(
                "          COMPLETE SUMMARY");

        System.out.println(
                "========================================");

        System.out.println(
                "URL:");

        System.out.println(
                pageUrl);

        System.out.println();

        System.out.println(
                "PAGE TITLE:");

        String title = document.title();

        if (title.isEmpty()) {

            System.out.println(
                    "No title found.");

        } else {

            System.out.println(title);
        }

        System.out.println();

        System.out.println(
                "PAGE STATISTICS:");

        String text =
                document.body() == null
                        ? ""
                        : document.body().text();

        int words =
                countWords(text);

        int headings =
                document.select(
                        "h1, h2, h3, h4, h5, h6").size();

        int links =
                document.select("a[href]").size();

        int images =
                document.select("img[src]").size();

        System.out.println(
                "Words      : " + words);

        System.out.println(
                "Headings   : " + headings);

        System.out.println(
                "Links      : " + links);

        System.out.println(
                "Images     : " + images);

        System.out.println();

        System.out.println(
                "Use the analyzer menu to inspect "
                + "individual elements.");
    }

    // ========================================
    // COUNT WORDS
    // ========================================

    static int countWords(String text) {

        if (text == null
                || text.trim().isEmpty()) {

            return 0;
        }

        String[] words =
                text.trim().split("\\s+");

        return words.length;
    }

    // ========================================
    // SAME DOMAIN CHECK
    // ========================================

    static boolean isSameDomain(
            String originalUrl,
            String linkUrl) {

        try {

            URI original =
                    new URI(originalUrl);

            URI link =
                    new URI(linkUrl);

            String originalHost =
                    original.getHost();

            String linkHost =
                    link.getHost();

            if (originalHost == null
                    || linkHost == null) {

                return false;
            }

            return originalHost.equalsIgnoreCase(
                    linkHost);

        } catch (URISyntaxException e) {

            return false;
        }
    }

    // ========================================
    // INTEGER INPUT
    // ========================================

    static int readInteger(
            String message,
            int min,
            int max) {

        while (true) {

            System.out.print(message);

            String input =
                    sc.nextLine().trim();

            if (input.isEmpty()) {

                System.out.println(
                        "Input cannot be empty.");

                continue;
            }

            try {

                int value =
                        Integer.parseInt(input);

                if (value < min
                        || value > max) {

                    System.out.println(
                            "Please enter a number between "
                            + min
                            + " and "
                            + max
                            + ".");

                    continue;
                }

                return value;

            } catch (NumberFormatException e) {

                System.out.println(
                        "Invalid input! "
                        + "Please enter a whole number.");
            }
        }
    }
}
