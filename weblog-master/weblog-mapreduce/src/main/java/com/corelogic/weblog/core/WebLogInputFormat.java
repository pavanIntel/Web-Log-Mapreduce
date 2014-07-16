package com.corelogic.weblog.core;

import java.io.IOException;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.InputSplit;
import org.apache.hadoop.mapreduce.RecordReader;
import org.apache.hadoop.mapreduce.TaskAttemptContext;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;

/**
 * Reads records that are delimited by a specifc begin/end tag.
 */
public class WebLogInputFormat extends TextInputFormat {

	@Override
	public RecordReader<LongWritable, Text> createRecordReader(
			InputSplit split, TaskAttemptContext context) {
		try {
			return new WebLogRecordReader((FileSplit) split,
					context.getConfiguration());
		} catch (IOException ioe) {
			System.out.println("Error while creating XmlRecordReader" + ioe);
			return null;
		}
	}
}