sqoop import --connect jdbc:mysql://localhost/liveproject1 --username root --password password --table daily_price --target-dir /Sqoop_Pig_Hive_Project1/daily_price -m 1

sqoop import --connect jdbc:mysql://localhost/liveproject1 --username root --password password --table dividends --target-dir /Sqoop_Pig_Hive_Project1/dividends -m 1