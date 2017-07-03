# Ant-Based Crawler

It has been implemented the Ant Crawler algorithm for a university project. It can operate on standard internet and on the common crawl interchangably. It could use different methos to classify the web pages (2 at the moment) one based on a dictionary approach, the other use a custom function instead. The crawler saves all the pages in memory in order to gain efficency, but it can also forget them if you want potentially have infinite memory. It also can choose to work on a single root site or it can send its ant army on different sites. An interface to query expansion is also present tough we do not use it in the classification. It can retrieve hypernym, hyponym and synonym of relevant words (it filters for stopwords). At the end of the execution the crawler will print the ranking with its score and depth from the root page. You can also configure the crawler parameter as it will be explained below.

## Getting Started

Clone the repo and do a maven install, go on stc/antMain/Configuration.java and change the rootFolder to match the one you are currently in, then you can run it on the Standard Web

### Prerequisites

- If you want to execute the crawler on the CommonCrawl, you must add this dependecy to your project:
https://github.com/Vzzarr/CommonCrawl-ProgettoSII-final-with-fileconf2 , otherwise you can test it immediately on internet.

- If you want to build the graph on neo4j you need also to install it.

### Project Structure

* **antMain**: Contains the entry point of the application and the Configurations class where you can set various parameters
* **crawler**: Contains the ant crawler algorithm
* **graph**: Contains the adapter to the Web and the Common crawl, you can decide which to use in antMain.Configurations
* **graphBuilding**: Contains a class to create a Neo4j database of the common crawl
* **scorer**: Contains the implementation of 2 scorer, one use a Dictionary approach, the other uses a custom function that look on anchor text, title, description etc
* **seeds**: Contains a class to download the WARC for the graph building tool	
* **tests**: Test for the applications	
* **trash**: Unusued classes
* **util**: Utils function needed for our projects

### Configuration

Go in the antMain.Configuration.java class, you can see those parameters

```
    private int numberOfAnts = 10;
    private int maxPagesToVisit = 100;
    private int maxNumberOfIteration = 300;
    private double trailUpdateCoefficient = 0.1;
    private double randomInitValue = 0.5;
    private boolean cachePages = true;
    private boolean focusOnSingleSite = true;

  //private String suffix = "/forum/";
    private String suffix = "/";

  //private String seedUrl = "http://www.hwupgrade.it/forum/forumdisplay.php?f=22";
    private String seedUrl = "http://mangafox.me/directory/";

  //private String query = "modem";
    private String query = "Boku no hero Academia";

  //private String scoringMethod = "DictionaryScorer";
    private String scoringMethod = "PageScorer";

  //private String graphApi = "StandardWeb";
    private String graphApi = "CommonCrawlRepo";

    private String rootFolder = "F:\\Documenti\\Università\\II Anno\\SII\\Ant-Based-Crawler\\";
  //private String rootFolder = "/Users/alex/Documents/IdeaProjects/Ant-Based-Crawler/";
```

* **numberOfAnts**: it regulates the number of ants to spawn for each iteration
* **maxPagesToVisit**: it represents a upper bound to the pages you can visit. It's possible to visit less pages if you reach the max number of iteration prestabilited
* **maxNumberOfIteration**: The crawler cannot make a number of iteration that is greater than this number
* **trailUpdateCoefficient**: The greater it is, the faster the crawler will upgrade a certain path
* **randomInitValue**: It is the initial weight for a new edge
* **cachePages**: If true, the algorithm will cache in memory the pages found, otherwise it will delete them after a score has been assigned
* **focusOnSingleSite**: If true, the crawl is confined on a single seed site.
* **suffix**: On a link you have a dnsName and a relative path to the dns name like for example "dnsName/suffix/other/relative/path.html". You can choose to attach a suffix to the dns name, in order to confine the crawl in that suffix, usefull if you want to crawl inside a forum which is often represented as dnsName/forum. If you do not want to crawl with a suffix, leave it as "/".
* **seedUrl**: This is the starting page all the ants start their crawl
* **query**: This is the query that guides the search
* **scoringMethod**: This is the classifier chosen when the crawler want to assign a score to a new page. At the moment we can choose between a Dictonary approach scorer and a custom scorer that we made.
* **graphApi**: This is the interface that gives you the pages to crawl, you can choose between the "Standard Web" to access the internet, and "CommonCrawlRepo" to access the [common crawl](http://commoncrawl.org/)
* **rootFolder**: Last but not the least, you need to set the root folder of the application


## Built With

* [Maven](https://maven.apache.org/) - To install the dependencies
* [NEO4J](https://neo4j.com/) (Optional) - To save the graph on file system


## Authors

* Andrea Salvoni: [GitHub: Menion93] 
* Nicholas Tucci: [GitHub: Vzzarr]
* Alessandro Sgaraglia: [GitHub: AlexSgar]

## License

This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details
