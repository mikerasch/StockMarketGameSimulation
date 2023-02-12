# StockMarketGameSimulation

## Current problems
- To access Stock API's and get enough tokens for this application, it costs a lot of $$$$$.
  - To solve this issue, I simply restrict the amount of tickers to 1000 to hopefully not exceed the api limit.
  - Still will have the problem of only being able to access the API a set amount of times, and only 5 times a minute. 
- If you by a stock which you previously bought and currently own, it will not merge that purchase with the current record.
  - This can be fixed by simply seeing if the stock already exists in the db, if it does just update the current record.
