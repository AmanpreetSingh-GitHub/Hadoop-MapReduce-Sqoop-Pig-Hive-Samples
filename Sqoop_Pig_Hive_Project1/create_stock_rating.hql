use sqoop_pig_hive_project1;

create external table if not exists stock_rating
(
	stock_symbol string,
	year int,
	prating	int,
	drating int
)
row format delimited
fields terminated by '\t'
location '/stock_rating';
