package com.corelogic.weblog.core;

import java.io.IOException;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class WebLogReducer extends Reducer<Text, Text, Text, Text> {
	@Override
	public void reduce(Text text, Iterable<Text> textIterator, Context context)
			throws IOException, InterruptedException {

		StringBuffer sbf = new StringBuffer();
		for (Text val : textIterator) {
			sbf.append(val.toString());
			sbf.append(",");

		}
		context.write(text, new Text(sbf.toString()));
	}
}
