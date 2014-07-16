How to Run
==========

### First Start the Cluster

```bash
#cd $HADOOP_HOME
#bin/start-all.sh
```


###copy the input files to hadoop before running the mapreduce program

```bash
#cd weblog-mapreduce
```
Remove 'input' directory if already exists in HDFS

```bash
#hadoop fs -rmr input
``` 

###Load Data into the HDFS

copy data from 'input' directory in local file system into HDFS

```bash
#hadoop fs -put /opt/hadoop/projects/weblog/weblog-mapreduce/input input
```

###Run the mapreduce program;
The program converts the WebLog files to flat data that can be queried by Hive.
Execute the following commands from the weblog-mapreduce directory.

###Compile and package the Mapreduce code

```bash

#mvn clean
#mvn compile package appassembler:assemble
```

###If output file exists remove it

```bash
#hadoop fs -rmr /user/ubuntu/weblogoutput
#hadoop fs -rmr /user/ubuntu/transactiontimeoutput
```

###Running the Mapreduce job

```bash
chmod +x target/appassembler/bin/weblog
chmod +x target/appassembler/bin/transactiontime

target/appassembler/bin/weblog -conf config/hadoop-cluster.xml input weblogoutput
target/appassembler/bin/transactiontime -conf config/hadoop-cluster.xml input transactiontimeoutput
```

###Check the output

```bash
#hadoop fs -ls /user/ubuntu/weblogoutput/part* 
```

###verify the output using the following command

```bash
#hadoop fs -cat /user/ubuntu/transactiontimeoutput/part*
```

####Go to hive section for queries

