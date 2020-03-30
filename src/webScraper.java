import org.json.simple.JSONObject;
import org.jsoup.*;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


import java.nio.file.Files;
import java.nio.file.Paths;

public class webScraper {
    public static final String URL = "https://abr.business.gov.au/ABN/View?abn=";

    public static void main(String[] args) {
        if (args.length != 1) {
            System.err.println("Not Enough Args.");
            System.exit(1);
        }
        String urlArgument = URL + args[0];
        JSONObject extractedValues = new JSONObject();
        try {
            Document doc = Jsoup.connect(urlArgument).get();
            Elements value = doc.select("tbody tr td");

            for (int i = 0; i < 5; i++) {
                Element temp = value.get(i);
                if (i == 0) {
                     extractedValues.put("Entity name",temp.getElementsByTag("span").get(0).text());
                }
                if (i == 1) {
                    extractedValues.put("ABN status",temp.text());
                }
                if (i == 2) {
                    extractedValues.put("Entity type",temp.getElementsByTag("a").get(0).text());
                }
                if (i == 3) {
                    extractedValues.put("Goods & Services Tax (GST)",temp.text());
                }
                if (i == 4) {
                    extractedValues.put("Main business location",temp.getElementsByTag("span").get(0).text());
                }
            }
            Files.write(Paths.get("output.json"), extractedValues.toJSONString().getBytes());
        } catch (Exception e) {
            System.err.println(e);
        }

    }
}
