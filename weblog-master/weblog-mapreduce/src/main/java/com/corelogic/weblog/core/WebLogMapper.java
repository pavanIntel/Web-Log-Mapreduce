package com.corelogic.weblog.core;

import java.io.IOException;
import java.util.List;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import com.corelogic.weblog.domain.WebLogicLog;
import com.corelogic.weblog.parser.RecordParser;
import com.corelogic.weblog.parser.WebLogParser;

public class WebLogMapper extends
		Mapper<LongWritable, Text, NullWritable, Text> {
	Text data = new Text();
	public static final int RETAIlER_INDEX = 0;

	@Override
	public void map(LongWritable longWritable, Text text, Context context)
			throws IOException, InterruptedException {

		// Continue till matching the tokens

		WebLogicLog webLog = (WebLogicLog) new WebLogParser().parse(text
				.toString());

		writeWebLog(webLog, context);
		StringBuilder data = new StringBuilder();

		// List<String> list = new RecordParser().parseRecord(text.toString());
		// for (int x = 0; x < list.size(); x++) {
		// String entry = list.get(x).replace("\n","\\n");
		// if (x < list.size() - 1) {
		// data.append(entry + "|");
		// }
		// else
		// data.append(entry);
		// }
		// context.write(NullWritable.get(), new Text(data.toString()));
	}

	private void writeWebLog(WebLogicLog webLog, Context context)
			throws IOException, InterruptedException {
		// TODO Auto-generated method stub
		StringBuilder data = new StringBuilder();

		data.append(webLog.getMonth() + "|" + webLog.getDay() + "|"

		+ webLog.getYear() + "|" + webLog.getHour() + "|"
				+ webLog.getLineType() + "|" + webLog.getSeverity() + "|"
				+ webLog.getSubsystem() + "|" + webLog.getMachine_name() + "|"
				+ webLog.getServer_name() + "|" + webLog.getThreadId() + "|"
				+ webLog.getUserid() + "|" + webLog.getTransactionid() + "|"
				+ webLog.getDiagnosticid() + "|" + webLog.getRawtime_value()
				+ "|" + webLog.getMessageid() + "|" + webLog.getMessageType()
				+ "|" + webLog.getException() + "|" + webLog.getEjBName() + "|"
				+ webLog.getMethodName() + "|" + webLog.getExceptionName()
				+ "|" + webLog.getExceptionMessage());

		context.write(NullWritable.get(), new Text(data.toString()));
	}
}