# StockMarketGameSimulation

## Current problems
- To access Stock API's and get enough tokens for this application, it costs a lot of $$$$$.
  - To solve this issue, I simply restrict the amount of tickers to 1000 to hopefully not exceed the api limit.
  - Still will have the problem of only being able to access the API a set amount of times, and only 5 times a minute.
  - A large problem currently is the limitations I have through my tier. To be able to expand to the cool stuff such as real-time, historical data, and news, I will need to purchase a higher tier api key.
  - Web scraping is NOT an option - websites don't like it
- ~~If you buy a stock which you previously bought and currently own, it will not merge that purchase with the current record.~~
  - ~~This can be fixed by simply seeing if the stock already exists in the db, if it does just update the current record.~~

## To-Do
- Upgrade the portfolio functionality such that it shows your current portfolio value TOTAL, not just individually.
  - Since it is not real time, I could probably get away with updating the portfolio when viewStocks() is called.
  - There is no need of constantly updating behind the seens as it is just not needed waste.
- Implement a feature in which a detailed history of trades/profit/losses are included
- Some sort of news stock API might be interesting to explore.
  - Obviously could not do this by web scraping since API limitations and websites will not like it.
- Ability to sell shares (obviously a priority)
- Historical Data which would allow to show an overall market trend.
- Add testing
  - Realistically, this should be done last as there are a lot of moving parts and I am the only one working on this.
