package com.corelogic.weblog.transactiontime;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

/**
 * 
 * 
 * Usage of the TransactionTimeDriver
 * 
 * copy the files to hadoop before running on cluster #hadoop fs -rmr input
 * hadoop fs -put input input #target/appassembler/bin/TransactionTimeDriver -conf
 * config/hadoop-cluster.xml input #output hadoop fs -rmr /user/hadoop/output
 * #target/appassembler/bin/TransactionTimeDriver -conf config/hadoop-cluster.xml input
 * output #hadoop fs -ls /user/hadoop/output #hadoop fs -cat
 * /user/hadoop/output/part-r-00000
 * 
 * @author sivaram
 */

public class TransactionTimeDriver extends Configured implements Tool {

    public int run(String[] args) throws Exception {

	if (args.length < 2) {
	    System.err
		    .println("Usage: TransactionTimeDriver <input path> <output path>");
	    ToolRunner.printGenericCommandUsage(System.err);
	    return -1;
	}
	Configuration conf = getConf();
	if (conf == null) {
	    conf = new Configuration();
	}
	Job job1 = new Job(conf);
	job1.setJobName("TransactionTime Driver");
	job1.setInputFormatClass(TransactionTimeInputFormat.class);
	job1.setMapperClass(com.corelogic.weblog.transactiontime.TransactionTimeMapper.class);
	job1.setMapOutputKeyClass(NullWritable.class);
	job1.setMapOutputValueClass(Text.class);

	FileInputFormat.setInputPaths(job1, new Path(args[0]));
	Path interoutputPath = new Path(args[1]);
	interoutputPath.getFileSystem(job1.getConfiguration()).delete(
		interoutputPath, true);

	FileOutputFormat.setOutputPath(job1, interoutputPath);
	return (job1.waitForCompletion(true) ? 0 : -1);

    }

    public static void main(String[] args) throws Exception {
	int exitCode = ToolRunner.run(new Configuration(), new TransactionTimeDriver(),
		args);
	System.exit(exitCode);
    }

}