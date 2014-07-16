# !/bin/sh
# Summary of Web Log Number of Users

echo "Output is available at $1"

hive -e "drop database weblog";

hive -e "create database weblog";

hive -e "use weblog";

hive -e "
CREATE EXTERNAL TABLE IF NOT EXISTS weblog.weblog258(       
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
 LINES TERMINATED BY '\n'
 LOCATION  '/user/ubuntu/weblogoutput/'";


hive -e "
CREATE EXTERNAL TABLE IF NOT EXISTS weblog.weblog(       
logdate string,
execution_time int,
hour int,
exception_name string,
method_name string,
ejb_name string)
ROW FORMAT DELIMITED
FIELDS TERMINATED BY '|'
LINES TERMINATED BY '\n'
LOCATION  '/user/ubuntu/transactiontimeoutput/'";

echo 'Total_Number_Of_users' '	' > $1/summary.tsv
hive -S -e "select count(DISTINCT userid) from weblog.weblog258" >> $1/summary.tsv;

# Summary of Web Log transactions

echo 'Total_Transactions' '	' >> $1>>summary.tsv;
hive -S -e "select count(*)  from weblog.weblog258" >> $1/summary.tsv;


# Summary of Web Log exceptions

echo 'Total_Number_Of_Exceptions' '	' >> $1/summary.tsv;

hive -S -e "select sum(exceptionname_count) from (select exceptionname,count(*) as exceptionname_count from weblog.weblog258 web where exceptionname!='null' group by exceptionname)web" >> $1/summary.tsv;

#Summary of Web Log Ejbnames

echo 'Total_Number_Of_Ejbnames' '	' >> $1/summary.tsv;
hive -S -e "select sum(ejbname_count) from (select ejbname,count(*) as ejbname_count from weblog.weblog258 web where ejbname!='null' and recordtype=1 group by ejbname)web" >> $1/summary.tsv;



#Summary of Web Log Top_Three_Ejbscalled


echo 'Top_Three_Ejbscalled' '	' >> $1/summary.tsv;
hive -S -e "select ejbname,ejbname_count from (select ejbname,count(*) as ejbname_count from weblog.weblog258 web where ejbname!='null' and recordtype=1 group by ejbname)web order by ejbname_count DESC limit 3" / $1/summary.tsv;


#Summary of Web Log Top_Three_Exceptioncalls

echo  'Top_Three_Exceptioncalls' '	' >> $1/summary.tsv;
hive -S -e "select exceptionname,count from(select exceptionname,count(*) as count from weblog.weblog258 web where exceptionname!='null' group by exceptionname)web order by count DESC limit 3" >> $1/summary.tsv;

# Summary of Web Log Total_SystemExceptions

echo 'Total_SystemExceptions' '	' >> $1/summary.tsv;
hive -S -e "select count(*) from weblog.weblog258 where messagetype='System_Exception'" >> $1/summary.tsv;


# Summary of Web Log Total_Number_Of_Machines

echo 'Total_Number_Of_Machines' '	' >> $1/summary.tsv;
hive -S -e "select count(DISTINCT machine_name) from weblog.weblog258" >> $1/summary.tsv;

# Summary of Web Log Total_Number_Of_Machines

echo 'Total_Number_Of_Servers' '	' >> $1/summary.tsv;
hive -S -e "select count(DISTINCT server_name) from weblog.weblog258 " >> $1/summary.tsv;
 
# Summary of Web Log Top_Three_methodcalls

echo 'Top_Three_methodcalls' '	' 'count_Of_Called' '	' >> $1/summary.tsv;

hive -S -e "select methodname,count from(select methodname,count(*) as count from weblog.weblog258 web where methodname!='null' and recordtype=1 group by methodname)web order by count DESC limit 3" >> $1/summary.tsv;

# Queries for the histogram :
#  # HISTOGRAMS#  #  

# Total number of transactions per day
echo 'Day' '	' 'Total_Number_Of_Transactions' '	' > $1/transactions.tsv
hive -S -e "select  day, count(*) from weblog.weblog258 where recordtype=1 group by  day" >> $1/transactions.tsv

# Total number of exceptions per day
echo 'Day' '	' 'Total_Number_Of_ExceptionTrace_Exceptions' '	' > $1/daywise_no_of_exceptions.tsv;
hive -S -e "select  day, count(*) from weblog.weblog258 where exceptionname='ExceptionTrace' group by  day" >> $1/daywise_no_of_exceptions.tsv;

# Total number of Methodcalls per day

echo 'Day' '	' 'Method_Names' 'Number_Of_Calls' > $1/daywise_methodcalls.tsv;
hive -S -e "select  day, methodname , count(*) from weblog.weblog258 where recordtype=1 group by  day,methodname" >> $1/daywise_methodcalls.tsv;

# Total number of Ejbnames per day

echo 'Day' '	' 'Ejb_Name' 'Number_Of_Calls' > $1/daywise_ejbnames.tsv;
hive -S -e "select  day, ejbname , count(*) from weblog.weblog258 where recordtype=1 group by  day,ejbname" >> $1/daywise_ejbnames.tsv;

# Total number of System_Exceptions per day

echo 'Day' '	' 'Number_Of_System_Exceptions' '	' > $1/daywise_no_of_systexceptions.tsv;
hive -S -e "select  day, count(*) from weblog.weblog258 where messagetype='System_Exception' group by  day" >> $1/daywise_no_of_systexceptions.tsv;

# Number_Of_Transactions per Daywise and hourwise
echo 'Day' '	'Hour' '	' 'Number_Of_Transactions' '	' > $1/daywise_hourwise_no_of_tx.tsv;
hive -S -e "select  day,hour, count(*) from weblog.weblog258 where recordtype=1 group by day,hour" >> $1/daywise_hourwise_no_of_tx.tsv;

# Number_Of_Exceptions per Daywise and hourwise

echo 'Day' '	'Hour' '	' 'Number_Of_ExceptionTrace_Exceptions' '	' > $1/daywise_hourwise_no_of_exception.tsv;

hive -S -e "select  day,hour, count(*) from weblog.weblog258 where exceptionname='ExceptionTrace' group by  day,hour" >> $1/daywise_hourwise_no_of_exception.tsv;


#  Number_Of_Methodcalls per Daywise and hourwise

echo 'Day' '	' 'Hour' '	' 'Method_Names' 'Number_Of_Calls' > $1/daywise_hourwise_no_of_methodcalls.tsv;
hive -S -e "select  day,hour, methodname ,ha count(*) from weblog.weblog258 where recordtype=1 group by  day,hour,methodname" >> $1/daywise_hourwise_no_of_methodcalls.tsv;

#  Number_Of_Ejb_Name per Daywise and hourwise

echo 'Day' '	' 'Hour' '	' 'Ejb_Name' 'Number_Of_Calls' > $1/daywise_hourwise_no_of_ejbnames;

hive -S -e "select  day,hour, ejbname , count(*) from weblog.weblog258 where recordtype=1 group by  day,hour,ejbname" >> $1/daywise_hourwise_no_of_ejbnames;

#  Number of System_Exceptions per Daywise and hourwise

echo 'Day' '	' 'Hour' '	' 'Number_Of_System_Exceptions'  > $1/daywise_hourwise_no_of_systemexceptions.tsv;

hive -S -e "select  day,hour, count(*) from weblog.weblog258 where messagetype='System_Exception' group by  day,hour" >> $1/daywise_hourwise_no_of_systemexceptions.tsv;


echo '' '	' > $1/top_twntyfive_methods_execution_.tsv
hive -S -e "select method_name,execution_time from weblog.weblog  order by execution_time DESC limit 25" >> $1/top_five_methods_execution_morethan_twosec.tsv;



