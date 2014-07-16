Running Hive Queries
====================
###Getting the analysis resutls

* Go to the 'run_queries.sh' file location

The analysis results can be obtained by running sql queries

```bash
#./run_queries.sh  /opt/hadoop/projects/weblog/output-data

```

The output of these queries is available at


###create tables and load data locally
Inorder to debug the hive queries and make deveopment faster, you can run the hive queries by creating the table locally.


###Runnig Hive Scripts

* Invoke hive  shell

```bash
#hive
```

```bash
hive>SET mapred.job.tracker=local;
hive>
CREATE TABLE IF NOT EXISTS weblog258(       
 month string,
 day string,
 year int,
 hour int,
 recordtype string,
 severity string,
 subsystem string,
 machine_name string,
 server_name  string,
 threadid int,
 userid string,
 transactionid string,
 diagnosticid string,
 rawtime_value string,
 messageid string,
 messagetype string,
 exception string,
 ejbname string,
 methodname string,
 exceptionname string, 
 exceptionmessage string)
ROW FORMAT DELIMITED
FIELDS TERMINATED BY '|'
 LINES TERMINATED BY '\n';

hive>LOAD DATA LOCAL INPATH '/opt/hadoop/projects/weblog/weblog-mapreduce/input/' INTO TABLE weblog258;

```
```bash

CREATE TABLE IF NOT EXISTS weblog(       
logdate string,
execution_time int,
hour int,
exception_name string,
method_name string,
ejb_name string)
ROW FORMAT DELIMITED
FIELDS TERMINATED BY '|'
LINES TERMINATED BY '\n';

hive>LOAD DATA LOCAL INPATH '/opt/hadoop/projects/weblog/weblog-mapreduce/input/' INTO TABLE weblog;

```


