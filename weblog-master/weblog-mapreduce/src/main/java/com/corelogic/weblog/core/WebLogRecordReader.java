package com.corelogic.weblog.core;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.DataOutputBuffer;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.InputSplit;
import org.apache.hadoop.mapreduce.RecordReader;
import org.apache.hadoop.mapreduce.TaskAttemptContext;

/**
 * WebLogRecorder to read and split the line into tokens
 * 
 */
public class WebLogRecordReader extends RecordReader<LongWritable, Text> {
	private final byte[] startTag;
	private final byte[] endTag;
	private final long start;
	private final long end;
	private final FSDataInputStream fsin;
	private final DataOutputBuffer buffer = new DataOutputBuffer();
	public static final String START_TAG_KEY = "####";
	public static final String END_TAG_KEY = "####";

	private LongWritable currentKey;
	private Text currentValue;
	private boolean firstTime = true;
	private boolean lastbyte = false;

	public WebLogRecordReader(
			org.apache.hadoop.mapreduce.lib.input.FileSplit split,
			Configuration configuration) throws IOException {
		// startTag = configuration.get(START_TAG_KEY).getBytes("utf-8");
		// endTag = configuration.get(END_TAG_KEY).getBytes("utf-8");

		startTag = START_TAG_KEY.getBytes("utf-8");
		endTag = END_TAG_KEY.getBytes("utf-8");

		// open the file and seek to the start of the split
		start = split.getStart();
		end = start + split.getLength();
		Path file = split.getPath();
		FileSystem fs = file.getFileSystem(configuration);
		fsin = fs.open(split.getPath());
		fsin.seek(start);
	}

	public boolean next(LongWritable key, Text value) throws IOException {
		if (fsin.getPos() < end) {
			if (firstTime) {
				if (readUntilMatch(startTag, true))
					firstTime = false;
			}
			try {
				// buffer.write(startTag);
				if (readUntilMatch(endTag, false)) {
					key.set(fsin.getPos());
					if (!lastbyte)
						value.set(buffer.getData(), 0, buffer.getLength()
								- END_TAG_KEY.length());
					else
						value.set(buffer.getData(), 0, buffer.getLength());
					return true;
				}
			} finally {
				buffer.reset();
			}
		}
		return false;
	}

	public LongWritable createKey() {
		return new LongWritable();
	}

	public Text createValue() {
		return new Text();
	}

	public long getPos() throws IOException {
		return fsin.getPos();
	}

	public void close() throws IOException {
		fsin.close();
	}

	public float getProgress() throws IOException {
		return (fsin.getPos() - start) / (float) (end - start);
	}

	private boolean readUntilMatch(byte[] match, boolean firstTime)
			throws IOException {
		int i = 0;
		boolean endTagMatch = false;
		while (true) {

			int b = fsin.read();
			// end of file:
			if (b == -1) {
				lastbyte = true;
				return false;
			}
			// save to buffer:
			if (!firstTime)
				buffer.write(b);

			// check if we're matching:
			if (b == match[i]) {
				i++;
				if (i >= match.length)
					endTagMatch = true;
			} else
				i = 0;

			if (endTagMatch)
				return true;

			if ((fsin.getPos() >= end) && (buffer.getLength() > 0)) { //last line
				lastbyte = true;
				return true;
			}

			if (!endTagMatch && fsin.getPos() >= end) { //empty line
				lastbyte = true;
				return false;
			}
		}
	}

	@Override
	public void initialize(InputSplit split, TaskAttemptContext context)
			throws IOException, InterruptedException {
	}

	@Override
	public boolean nextKeyValue() throws IOException, InterruptedException {
		currentKey = new LongWritable();
		currentValue = new Text();
		return next(currentKey, currentValue);
	}

	@Override
	public LongWritable getCurrentKey() throws IOException,
			InterruptedException {
		return currentKey;
	}

	@Override
	public Text getCurrentValue() throws IOException, InterruptedException {
		return currentValue;
	}
}
