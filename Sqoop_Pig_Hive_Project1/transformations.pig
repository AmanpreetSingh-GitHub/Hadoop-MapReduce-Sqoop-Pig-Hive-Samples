prices = load '/Sqoop_Pig_Hive_Project1/daily_price' using PigStorage(',') as (xchange:chararray, stock_symbol:chararray, trade_date:chararray,  opening_price:float, high_price:float, low_price:float, closing_price:float, stock_volume:long,adjusted_close:float);

dividends = load '/Sqoop_Pig_Hive_Project1/dividends' using PigStorage(',') as (xchange:chararray, stock_symbol:chararray, div_date:chararray, dividend:float);

prices1 = foreach prices generate stock_symbol, SUBSTRING(trade_date,0,4) as year, closing_price;

prices1_grp = group prices1 by (stock_symbol, year);

prices2 = foreach prices1_grp generate group.stock_symbol as stock_symbol, group.year as year, (MAX(prices1.closing_price)-MIN(prices1.closing_price)) as variance, AVG(prices1.closing_price) as avg_closing_price, (((MAX(prices1.closing_price)-MIN(prices1.closing_price))/AVG(prices1.closing_price))*100) as per_variance;

div1 = foreach dividends generate stock_symbol,SUBSTRING(div_date,0,4) as year, dividend;

div1_grp = group div1 by (stock_symbol, year);

div2 = foreach div1_grp generate group.stock_symbol as stock_symbol, group.year as year, SUM(div1.dividend) as final_dividend;

prices_rating = foreach prices2 generate stock_symbol, year, (per_variance <= 5 ? 10 : (per_variance > 5 AND per_variance <= 10 ? 9 : (per_variance > 10 AND per_variance <= 15 ? 8 : (per_variance > 15 AND per_variance <= 20 ? 7 : (per_variance > 20 AND per_variance <= 25 ? 6 : (per_variance > 25 AND per_variance <= 30 ? 5 : (per_variance > 30 AND per_variance <= 35 ? 4 : (per_variance > 35 AND per_variance <= 40 ? 3 : (per_variance > 40 AND per_variance <= 45 ? 2 : (per_variance > 45 AND per_variance <= 50 ? 1 : 0)))))))))) as prating;

div_rating = foreach div2 generate stock_symbol, year, (final_dividend >= 1 ? 10 : (final_dividend < 1 and final_dividend >= .9 ? 9 : (final_dividend < .9 and final_dividend >= .8 ? 8 : (final_dividend < .8 and final_dividend >= .7 ? 7 : (final_dividend < .7 and final_dividend >= .6 ? 6 : (final_dividend < .6 and final_dividend >= .5 ? 5 : (final_dividend < .5 and final_dividend >= .4 ? 4 : (final_dividend < .4 and final_dividend >= .3 ? 3 : (final_dividend < .3 and final_dividend >= .2 ? 2 : (final_dividend < .2 and final_dividend >= .1 ? 1 : 0)))))))))) as drating;

rating = join prices_rating by (stock_symbol, year) full, div_rating by (stock_symbol, year);

rating_final = foreach rating generate (prices_rating::stock_symbol is null ? div_rating::stock_symbol : prices_rating::stock_symbol) as stock_symbol, (prices_rating::year is null ? div_rating::year : prices_rating::year) as year, prices_rating::prating as prating, div_rating::drating as drating;

store rating_final into '/stock_rating';
