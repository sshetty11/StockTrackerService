# StockTrackerService
The frontend can be accessed at https://github.com/sshetty11/StockTrackerWeb.
The backend/frontend has been deployed & is accessible at http://www.stockvals.com

## Testing Locally
* Run mvn clean && mvn package
* Populate a envs.json file with a 'bing-api' key (ex: 'bing-api': <key>)
* Run java -jar target/StockTracker-0.0.1-SNAPSHOT.jar server config.yml 
* Server will run on port 9000
