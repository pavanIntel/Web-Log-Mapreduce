package com.corelogic.weblog.core;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

import com.corelogic.weblog.core.WebLogInputFormat;

/**
 * 
 * 
 * Usage of the WebLogDriver
 * 
 * copy the files to hadoop before running on cluster
 * #hadoop fs -rmr input hadoop fs -put input input
 * #target/appassembler/bin/WebLogDriver -conf config/hadoop-cluster.xml input
 * #output hadoop fs -rmr /user/reddyraja/output
 * #target/appassembler/bin/WebLogDriver -conf config/hadoop-cluster.xml input output 
 * #hadoop fs -ls /user/reddyraja/output 
 * #hadoop fs -cat /user/reddyraja/output/part-r-00000
 * 
 * @author sivaram
 */

public class WebLogDriver extends Configured implements Tool {

	public int run(String[] args) throws Exception {

		if (args.length < 2) {
			System.err
					.println("Usage: WebLogDriver <input path> <output path>");
			ToolRunner.printGenericCommandUsage(System.err);
			return -1;
		}
		Configuration conf = getConf();
		if (conf == null) {
			conf = new Configuration();
		}
		Job job1 = new Job(conf);
		job1.setJobName("WebLog Driver");

		job1.setInputFormatClass(WebLogInputFormat.class);
		job1.setMapperClass(com.corelogic.weblog.core.WebLogMapper.class);

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
		int exitCode = ToolRunner.run(new Configuration(), new WebLogDriver(),
				args);
		System.exit(exitCode);
	}

}