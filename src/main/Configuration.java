package main; /**
 * Created by Andrea on 05/03/2017.
 *
 * This class define the behaviour of the ant based crawler
 *
 */

public class Configuration
{
    private int numberOfAnts = 10;
    private int maxPagesToVisit = 1000;
    private int maxNumberOfIteration = 100;
    private double trailUpdateCoefficient = 0.1;
    private double randomInitValue = 0.5;
    private boolean cachePages = true;
    private boolean focusOnSingleSite = true;

    private String suffix = "/forum/";
    //private String suffix = "/";

    //private String seedUrl = "http://www.hwupgrade.it/forum/forumdisplay.php?f=22";
    private String seedUrl = "http://www.hwupgrade.it/forum/";

    //private String query = "modem";
    private String query = "modem tp-link";

    //private String scoringMethod = "DictionaryScorer";
    private String scoringMethod = "PageScorer";

    private String graphApi = "StandardWeb";
    //private String graphApi = "CommonCrawlRepo";

    private String rootFolder = "F:\\Documenti\\Università\\II Anno\\SII\\CrawlerWeb\\";



    public int getNumberOfAnts() {
        return numberOfAnts;
    }

    public int getMaxPagesToVisit() {
        return maxPagesToVisit;
    }

    public int getMaxNumberOfIteration(){ return maxNumberOfIteration; }

    public String getScoringMethod() {
        return scoringMethod;
    }

    public boolean canCachePages(){ return cachePages;}

    public String getGraphApi(){ return graphApi; }

    public double getTrailUpdateCoefficient() {return trailUpdateCoefficient; }

    public double getRandomInitValue() { return randomInitValue; }

    public String getSeedUrl(){ return seedUrl; }

    public String getQuery() {
        return query;
    }

    public boolean isFocusingOnSingleSite(){ return focusOnSingleSite; }

    public String getSuffix() {
        return suffix;
    }

    public String getRootFolder() {
        return rootFolder;
    }
}
