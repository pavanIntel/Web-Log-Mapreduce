package com.corelogic.weblog.transactiontime;

import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.compress.CompressionCodec;
import org.apache.hadoop.io.compress.CompressionCodecFactory;
import org.apache.hadoop.mapreduce.InputSplit;
import org.apache.hadoop.mapreduce.RecordReader;
import org.apache.hadoop.mapreduce.TaskAttemptContext;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;
import org.apache.hadoop.util.LineReader;

/**
 * WebLogRecorder to read and split the line into tokens
 * 
 */
public class TransactionTimeRecordReader extends
		RecordReader<LongWritable, Text> {

	private static final Log LOG = LogFactory
			.getLog(TransactionTimeRecordReader.class);
	private CompressionCodecFactory compressionCodecs = null;
	private long start;
	private long pos;
	private long end;
	private LineReader in;
	private int maxLineLength;
	private LongWritable key = null;
	private Text value = null;

	private String type1 = "[StatelessRemoteObject] [StatelessEJBObject]";
	private String type8 = "[StatelessManager] In postInvoke";
	private String type7 = "[BaseEJBManager]";

	public void initialize(InputSplit genericSplit, TaskAttemptContext context)
			throws IOException {
		FileSplit split = (FileSplit) genericSplit;
		Configuration job = context.getConfiguration();
		this.maxLineLength = job.getInt("mapred.linerecordreader.maxlength",
				Integer.MAX_VALUE);
		start = split.getStart();
		end = start + split.getLength();
		final Path file = split.getPath();
		compressionCodecs = new CompressionCodecFactory(job);
		final CompressionCodec codec = compressionCodecs.getCodec(file);

		// open the file and seek to the start of the split
		FileSystem fs = file.getFileSystem(job);
		FSDataInputStream fileIn = fs.open(split.getPath());
		boolean skipFirstLine = false;
		if (codec != null) {
			in = new LineReader(codec.createInputStream(fileIn), job);
			end = Long.MAX_VALUE;
		} else {
			if (start != 0) {
				skipFirstLine = true;
				--start;
				fileIn.seek(start);
			}
			in = new LineReader(fileIn, job);
		}
		if (skipFirstLine) { // skip first line and re-establish "start".
			start += in.readLine(new Text(), 0,
					(int) Math.min((long) Integer.MAX_VALUE, end - start));
		}
		this.pos = start;
	}

	public boolean nextKeyValue() throws IOException {
		if (key == null) {
			key = new LongWritable();
		}
		key.set(pos);
		// if (value == null) {
		// value = new Text();
		// }
		int newSize = 0;

		Text tempText = new Text();
		Text type1Line = new Text();
		Text type7Line = new Text();
		Text type8Line = new Text();
		boolean gotType1 = false;
		boolean gotType8 = false;
		boolean gotType7 = false;

		while (pos < end) {
			newSize = in.readLine(tempText, maxLineLength,
					Math.max((int) Math.min(Integer.MAX_VALUE, end - pos),
							maxLineLength));
			if (newSize == 0) {
				break;
			}
			pos += newSize;

			// line too long. try again
			LOG.info("Skipped line of size " + newSize + " at pos "
					+ (pos - newSize));

			String line = tempText.toString();

			// Logic to assemble two lines together....
			if (line.contains(type1)) {
				type1Line = new Text(tempText);
				gotType1 = true;
				continue;
			}

			if (line.contains(type7)) {
				if (gotType1) {
					type7Line = new Text(tempText);
					gotType7 = true;
				}
			}

			if (line.contains(type8)) {
				if (gotType1) {
					type8Line = new Text(tempText);
					gotType8 = true;
				}
			}

			if (gotType1 && gotType7 && gotType8)
				break; // continue till we got type8

			if (gotType1 && gotType8)
				break; // continue till we got type8
		}

		if (gotType1 && gotType7 && gotType8) {
			StringBuilder builder = new StringBuilder(type1Line.toString());
			builder.append("||");
			builder.append(type7Line.toString());
			builder.append("||");
			builder.append(type8Line.toString());
			value = new Text(builder.toString());
		} else if (gotType1 && gotType8) {
			StringBuilder builder = new StringBuilder(type1Line.toString());
			builder.append("||");
			builder.append(type8Line.toString());
			value = new Text(builder.toString());
		}
		if (newSize == 0) {
			key = null;
			value = null;
			return false;
		} else {
			return true;
		}
	}

	@Override
	public LongWritable getCurrentKey() {
		return key;
	}

	@Override
	public Text getCurrentValue() {
		return value;
	}

	/**
	 * Get the progress within the split
	 */
	public float getProgress() {
		if (start == end) {
			return 0.0f;
		} else {
			return Math.min(1.0f, (pos - start) / (float) (end - start));
		}
	}

	public synchronized void close() throws IOException {
		if (in != null) {
			in.close();
		}
	}

}
