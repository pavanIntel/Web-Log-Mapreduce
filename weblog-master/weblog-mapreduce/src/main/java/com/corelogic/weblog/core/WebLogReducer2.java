package com.corelogic.weblog.core;

import java.io.IOException;

import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.Reducer.Context;

import com.corelogic.weblog.domain.WebLogicLog;


/*
 * For all the keys, count the sum
 * for a day, hour and count; Day is the key
 * 
 * Same method can be used for count of EJBNames
 * 
 */
public class WebLogReducer2 extends Reducer<Text, WebLogicLog, NullWritable, Text> {

	/*
	public void reduce(Text key, Iterable<WebLogicLog> textIterator, Context context)
			throws IOException, InterruptedException {

		StringBuffer sbf = new StringBuffer();
		Long sum = new Long(0);
		for (WebLogicLog val : textIterator) {
			sbf.append(key.toString());
			sbf.append(",");
			sbf.append(new Text(new Text(sum.toString()).toString()));
		}
		context.write(NullWritable.get(),sbf.toString());
	}
	*/
	
}
