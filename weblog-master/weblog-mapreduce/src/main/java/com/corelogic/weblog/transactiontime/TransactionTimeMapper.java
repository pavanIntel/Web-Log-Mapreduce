package com.corelogic.weblog.transactiontime;

import java.io.IOException;
import java.util.Calendar;
import java.util.StringTokenizer;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import com.corelogic.weblog.transactiontime.domain.TransactionTimeLog;

public class TransactionTimeMapper extends
		Mapper<LongWritable, Text, NullWritable, Text> {
	Text dataVal = new Text();
	public static final int RETAIlER_INDEX = 0;

	private String type1 = "[StatelessRemoteObject] [StatelessEJBObject]";
	private String type7 = "[BaseEJBManager]";
	private String type8 = "[StatelessManager] In postInvoke";
	boolean gotType7 = false;

	@Override
	public void map(LongWritable longWritable, Text text, Context context)
			throws IOException, InterruptedException {

		// Continue till matching the tokens

		StringTokenizer records = new StringTokenizer(text.toString(), "||");
		long sec[] = new long[3];
		int secCount = 0;
		long timeToexcute;
		String exceptionName = " ";
		String methodName = " ";
		while (records.hasMoreTokens()) {
			TransactionTimeLog transactionTime = parseRecord(records
					.nextToken());
			sec[secCount] = transactionTime.getTimeInSeconds();
			if (transactionTime.getLineType() == 7) {
				exceptionName = "true_" + transactionTime.getExceptionName();

				gotType7 = true;
			}
			if (transactionTime.getLineType() == 8 && gotType7) {
				transactionTime.setExceptionName(exceptionName);
				transactionTime.setMethodName(methodName);

			}
			if (transactionTime.getLineType() == 1) {
				methodName = transactionTime.getMethodName();
				if (methodName.contains("Transaction")) {
					methodName = methodName.substring(0,
							methodName.indexOf(')') + 1);
				}

			}

			if (gotType7) {

				if (secCount == 2) {

					timeToexcute = sec[2] - sec[0];
					transactionTime.setTimeInSeconds(timeToexcute);
					transactionTime.setExceptionName(exceptionName);
					transactionTime.setMethodName(methodName);
					dataVal.set(transactionTime.toString());
					context.write(NullWritable.get(), dataVal);
					secCount = 0;
				}

			} else {
				if (secCount == 1) {

					timeToexcute = sec[1] - sec[0];
					transactionTime.setTimeInSeconds(timeToexcute);
					transactionTime.setExceptionName("false");
					transactionTime.setMethodName(methodName);
					dataVal.set(transactionTime.toString());
					context.write(NullWritable.get(), dataVal);
					secCount = 0;
				}
			}
			secCount++;
		}
	}

	private TransactionTimeLog parseRecord(String token) {

		StringTokenizer fields = new StringTokenizer(token, ">");

		int fieldCount = 0;

		TransactionTimeLog transactionTime = new TransactionTimeLog();
		while (fields.hasMoreTokens()) {

			fieldCount++;

			switch (fieldCount) {
			case 1:
				String timeStamp = fields.nextToken();
				// transactionTime.setTimestamp(timeStamp.substring(timeStamp.indexOf("<")
				// + 1));
				transactionTime = fillWebLogWithtimeStamp(transactionTime,
						timeStamp.substring(timeStamp.indexOf("<") + 1));
				break;
			case 2:
				fields.nextToken();
				// transactionTime.setSeverity(fields.nextToken());
				break;
			case 3:
				fields.nextToken();
				// transactionTime.setSubsystem(fields.nextToken());
				break;
			case 4:
				fields.nextToken();
				// transactionTime.setMachine_name(fields.nextToken());
				break;
			case 5:
				fields.nextToken();
				// transactionTime.setServer_name(fields.nextToken());
				break;
			case 6:

				String threadId = fields.nextToken();
				transactionTime.setThreadId(threadId.substring(
						threadId.indexOf("'") + 1, threadId.indexOf("' ") + 1));
				break;
			case 7:
				fields.nextToken();
				// transactionTime.setUserid(fields.nextToken());
				break;
			case 8:
				fields.nextToken();
				// transactionTime.setDiagnosticid(fields.nextToken());
				break;
			case 9:
				fields.nextToken();
				// transactionTime.setRawtime_value(fields.nextToken());
				break;
			case 10:
				fields.nextToken();
				// transactionTime.setRawtime_value(fields.nextToken());
				break;

			case 11:
				fields.nextToken();
				// transactionTime.setRawtime_value(fields.nextToken());
				break;

			case 12:

				String messageText = fields.nextToken();

				transactionTime.setMessageid(messageText);

				if (messageText.contains(type1)) {

					transactionTime.setLineType(1);

					String tokens[] = messageText.split(" ");
					transactionTime = fillWebLogWithType1(transactionTime,
							tokens);

				}

				if (messageText.contains(type7)) {

					transactionTime.setLineType(7);

					String tokens[] = messageText.split(" ");
					transactionTime = fillWebLogWithType7(transactionTime,
							tokens, messageText);

				}

				if (messageText.contains(type8)) {

					transactionTime.setLineType(8);

					String tokens[] = messageText.split(" ");
					transactionTime = fillWebLogWithType8(transactionTime,
							tokens, messageText);

				}

				break;
			default:
				fields.nextToken();
				break;

			}

		}
		return transactionTime;

	}

	private TransactionTimeLog fillWebLogWithType7(
			TransactionTimeLog transactionTime, String[] tokens, String record) {

		System.out.println("record::" + record);
		if (record.toLowerCase().indexOf("Exception") != -1) {
			// exception message

			transactionTime.setMessageType(TransactionTimeLog.EXCEPTION);

		} else {

			if (record.contains("exception:")) {

				String exceptionName = record.substring(record.indexOf(":"),
						record.lastIndexOf(" "));
				transactionTime.setExceptionName(exceptionName);
			} else {
				if (record.contains("Setting")) {
				}

				else {
					String exceptionName = record
							.substring(record.indexOf("]") + 1);
					transactionTime.setExceptionName(exceptionName);
				}

			}
		}

		return transactionTime;
	}

	private TransactionTimeLog fillWebLogWithtimeStamp(
			TransactionTimeLog transactionTime, String entry) {
		int hour;
		long min;
		long sec;
		long timeInSeconds;
		final DateTimeFormatter df = DateTimeFormat
				.forPattern("MMM dd, yyyy HH:mm:ss a z");
		final DateTime dateTime = df.withOffsetParsed().parseDateTime(entry);

		transactionTime.setDay(dateTime.getDayOfMonth());
		transactionTime.setYear(dateTime.getYear());
		transactionTime.setMonth(dateTime.getMonthOfYear());

		Calendar calendar = dateTime.toGregorianCalendar();
		if (calendar.get(Calendar.AM_PM) == Calendar.PM) {

			hour = dateTime.getHourOfDay() + 12;
			transactionTime.setHour(hour);
			min = dateTime.getMinuteOfDay();
			sec = dateTime.getSecondOfDay();
			timeInSeconds = (hour * 3600) + (min * 60) + sec;
			transactionTime.setTimeInSeconds(timeInSeconds);

		} else {

			hour = dateTime.getHourOfDay();
			transactionTime.setHour(hour);
			min = dateTime.getMinuteOfDay();
			sec = dateTime.getSecondOfDay();
			timeInSeconds = (hour * 3600) + (min * 60) + sec;
			transactionTime.setTimeInSeconds(timeInSeconds);
		}
		return transactionTime;

	}

	private TransactionTimeLog fillWebLogWithType8(
			TransactionTimeLog transactionTime, String[] tokens, String record) {

		if (tokens.length > 9) {
			String ejbname = tokens[7];

			if (ejbname.indexOf("@") != -1) {

				transactionTime.setEjBName(ejbname.substring(0,
						ejbname.indexOf("@")));
			}

		}

		return transactionTime;
	}

	private TransactionTimeLog fillWebLogWithType1(
			TransactionTimeLog transactionTime, String[] tokens) {

		if (tokens.length > 23) {
			StringBuilder methodName = new StringBuilder(tokens[8]);
			String method = methodName.toString();
			if (method.contains(") Transaction")) {
				transactionTime.setMethodName(method);
			} else {
				methodName.append(" " + tokens[9]);
				transactionTime.setMethodName(methodName.toString());
			}

			if (tokens[23].startsWith("home:")) {
				int endindex = tokens[24].indexOf("@");
				String ejBName = tokens[24].substring(0, endindex);
				transactionTime.setEjBName(ejBName);

			} else if (tokens[23].startsWith("[BaseRemoteObject]")) {
				int endindex = tokens[25].indexOf("@");
				String ejBName = tokens[25].substring(0, endindex);
				transactionTime.setEjBName(ejBName);

			} else if (tokens[23].startsWith("on:")) {
				int endindex = tokens[26].indexOf("@");
				String ejBName = tokens[26].substring(0, endindex);
				transactionTime.setEjBName(ejBName);

			} else {

				int endindex = tokens[23].indexOf("@");
				String ejBName = tokens[23].substring(0, endindex + 1);
				transactionTime.setEjBName(ejBName);
			}
		}
		return transactionTime;
	}

}
