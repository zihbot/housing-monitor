# Housing Monitor

The Housing Monitor is a 3-Tier application, which monitors the prices of house listings. The user can add new items to the watchlist, and see the current and older parameters of the listings.

## Structure
There are four folders in the project root. The explanation for them is in this section. 

### Database
Initialization scripts for the database. The Spring JPA cannot modify the database structure, so the table and view declarations are here, as well as the initial data.

### DataDownloader
Python backend scripts, with the data scraper and the convolutional neural network(s). It is responsible for providing the data for the layers above. It is running in a Flask environment, and processes REST requests.

### MonitorApp
Java backend based on Spring. Handles the user requests, runs scheduled tasks and connects to the database.

### WebUi
JavaScript frontend based on React. It provides the display of the data for the user, and makes requests for the MonitorApp.