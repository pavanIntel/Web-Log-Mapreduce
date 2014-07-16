package com.corelogic.weblog.parser;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.corelogic.weblog.core.LogParser;
import com.corelogic.weblog.domain.WebLog;
import com.corelogic.weblog.domain.WebLogicLog;

/*
 * Parser to Parse Log files.
 * 
 */
/**
 * 
 * public static String type1 = "[StatelessRemoteObject] [StatelessEJBObject]";
 * public static String type2 = "[BaseRemoteObject] preInvoke"; public static
 * String type3 = "[StatelessManager] In preInvoke"; public static String type4
 * = "[Pool]"; public static String type5 =
 * "[BaseRemoteObject] Manager.preInvoke"; public static String type6 =
 * "[BaseRemoteObject] prePostInvoke"; public static String type7 =
 * "[BaseEJBManager]"; public static String type8 =
 * "[StatelessManager] In postInvoke"; public static String type9 =
 * "[BaseRemoteObject] postInvokeTxRetry"; public static String type10 =
 * "Scheduling"; public static String type11 = "Scheduled"; public static String
 * type12 = "("; public static String type13 = "[EntityPool] Allocate"; public
 * static String type14 = "[BaseLocalObject] Setting"; public static String
 * type15 = "Stack trace"; public static String type16 =
 * "[StatelessSessionPool] allocate"; public static String type17 = "Test";
 * public static String type18 = "Received exception"; public static String
 * type19 = "[weblogic.servlet.internal.WebAppServletContext"; public static
 * String type20 = "Connection for pool"; public static String type21 =
 * "Received"; public static String type22 = "Created"; public static String
 * type23 = "UnknownRecord";
 * 
 */

public class WebLogParser implements LogParser {

	public static final int TYPE1 = 1;
	public static final int TYPE2 = 2;
	public static final int TYPE3 = 3;
	public static final int TYPE4 = 4;
	public static final int TYPE5 = 5;
	public static final int TYPE6 = 6;
	public static final int TYPE7 = 7;
	public static final int TYPE8 = 8;
	public static final int TYPE9 = 9;
	public static final int TYPE10 = 10;
	public static final int TYPE11 = 11;
	public static final int TYPE12 = 12;
	public static final int TYPE13 = 13;
	public static final int TYPE14 = 14;
	public static final int TYPE15 = 15;
	public static final int TYPE16 = 16;
	public static final int TYPE17 = 17;
	public static final int TYPE18 = 18;
	public static final int TYPE19 = 19;
	public static final int TYPE20 = 20;
	public static final int TYPE21 = 21;
	public static final int TYPE22 = 22;
	public static final int TYPE23 = 23;

	public static String type1 = "[StatelessRemoteObject] [StatelessEJBObject]";
	public static String type2 = "[BaseRemoteObject] preInvoke";
	public static String type3 = "[StatelessManager] In preInvoke";
	public static String type4 = "[Pool]";
	public static String type5 = "[BaseRemoteObject] Manager.preInvoke";
	public static String type6 = "[BaseRemoteObject] prePostInvoke";
	public static String type7 = "[BaseEJBManager]";
	public static String type8 = "[StatelessManager] In postInvoke";
	public static String type9 = "[BaseRemoteObject] postInvokeTxRetry";
	public static String type10 = "Scheduling";
	public static String type11 = "Scheduled";
	public static String type12 = "(";
	public static String type13 = "[EntityPool] Allocate";
	public static String type14 = "[BaseLocalObject] Setting";
	public static String type15 = "Stack trace";
	public static String type16 = "[StatelessSessionPool] allocate";
	public static String type17 = "Test";
	public static String type18 = "Received exception";
	public static String type19 = "[weblogic.servlet.internal.WebAppServletContext";
	public static String type20 = "Connection for pool";
	public static String type21 = "Received";
	public static String type22 = "Created";
	public static String type23 = "UnknownRecord";

	/*
	 * <[StatelessRemoteObject] [StatelessEJBObject] preInvoke with md:
	 * MethodDescriptor: Method:
	 * getPropertyListingIndicatorInfo(com.fares.common.model.PassportUserInfo,
	 * com.fares.smartsearch.ifc.model.ListingInData) Transaction attribute:
	 * TX_SUPPORTS Isolation Level: No Isolation Level Set Tx Timeout: 30000 on:
	 * [BaseRemoteObject] home:
	 * com.fares.smartsearch.ejb.SmartSearch_3wwr4_HomeImpl@1ecb96d5>
	 * <[BaseRemoteObject] preInvoke called with:[InvocationWrapper] callerTx:
	 * null invokeTx: null bean:null primaryKey: nullMethodDescriptor:
	 * MethodDescriptor: Method:
	 * getPropertyListingIndicatorInfo(com.fares.common.model.PassportUserInfo,
	 * com.fares.smartsearch.ifc.model.ListingInData) Transaction attribute:
	 * TX_SUPPORTS Isolation Level: No Isolation Level Set Tx Timeout: 30000
	 * on:[BaseRemoteObject] home:
	 * com.fares.smartsearch.ejb.SmartSearch_3wwr4_HomeImpl@1ecb96d5>
	 * <[StatelessManager] In preInvoke with timeout:30000 on manager:
	 * [StatelessManager] home/class:
	 * com.fares.smartsearch.ejb.SmartSearch_3wwr4_HomeImpl@1ecb96d5pool:
	 * weblogic.ejb.container.pool.StatelessSessionPool@1e3ca9cd - ejb name:
	 * 'SmartSearch' debug: true> <[Pool] SmartSearch - Returning bean from the
	 * pool: 'com.fares.smartsearch.ejb.SmartSearch_3wwr4_Impl@196d0d0c'>
	 * <[BaseRemoteObject] Manager.preInvoke returned a
	 * bean:com.fares.smartsearch.ejb.SmartSearch_3wwr4_Impl@196d0d0c>
	 * <[BaseRemoteObject] prePostInvoke called with txRetryCount = 0
	 * wrap:[InvocationWrapper] callerTx: null invokeTx: null
	 * bean:com.fares.smartsearch.ejb.SmartSearch_3wwr4_Impl@196d0d0c
	 * primaryKey: nullMethodDescriptor: MethodDescriptor: Method:
	 * getPropertyListingIndicatorInfo(com.fares.common.model.PassportUserInfo,
	 * com.fares.smartsearch.ifc.model.ListingInData) Transaction attribute:
	 * TX_SUPPORTS Isolation Level: No Isolation Level Set Tx Timeout: 30000
	 * Exception: null on: [BaseRemoteObject] home:
	 * com.fares.smartsearch.ejb.SmartSearch_3wwr4_HomeImpl@1ecb96d5>
	 * <[StatelessManager] In postInvoke on [StatelessManager] home/class:
	 * com.fares.smartsearch.ejb.SmartSearch_3wwr4_HomeImpl@1ecb96d5pool:
	 * weblogic.ejb.container.pool.StatelessSessionPool@1e3ca9cd - ejb name:
	 * 'SmartSearch' debug: true> <[BaseRemoteObject] postInvokeTxRetry
	 * returning with retry = false>
	 */

	public WebLog parse(String logLine) throws IOException {
		logLine.trim();
		return fill(logLine.trim());
	}

	private WebLog fill(String logLine) throws IOException {

		WebLogicLog webLog = new WebLogicLog();

		List<String> list = new RecordParser().parseRecord(logLine);
		for (int x = 0; x < list.size(); x++) {
			String entry = list.get(x).replace("\n", "\\n").replace("<", "");

			switch (x) {
			case 0:
				webLog = fillWebLogWithtimeStamp(webLog, entry);
				break;
			case 1:
				webLog.setSeverity(entry);
				break;
			case 2:
				webLog.setSubsystem(entry);
				break;
			case 3:
				webLog.setMachine_name(entry);
				break;
			case 4:
				webLog.setServer_name(entry);
				break;
			case 5:
				webLog = fillThreadId(webLog, entry);
				break;
			case 6:
				webLog.setUserid(entry);
				break;
			case 7:
				webLog.setTransactionid(entry);
				break;
			case 8:
				webLog.setDiagnosticid(entry);
				break;
			case 9:
				webLog.setRawtime_value(entry);
				break;
			case 10:
				webLog.setMessageid(entry);
				break;
			case 11:
				String line = removeLeadingChars(entry, '\b');
				int lineType = extractType(line);
				webLog.setLineType(lineType);
				if (lineType == TYPE23) {
					webLog.setExceptionMessage(line);
					return webLog;
				}
				webLog = fillWebLogWithMessageInformation(webLog, line);
				break;
			default:
				break;
			}
		}
		return webLog;
	}

	private int extractType(String logLine) {
		if (logLine.startsWith(type1))
			return TYPE1;
		if (logLine.startsWith(type2))
			return TYPE2;
		if (logLine.startsWith(type3))
			return TYPE3;
		if (logLine.startsWith(type4))
			return TYPE4;
		if (logLine.startsWith(type5))
			return TYPE5;
		if (logLine.startsWith(type6))
			return TYPE6;
		if (logLine.startsWith(type7))
			return TYPE7;
		if (logLine.startsWith(type8))
			return TYPE8;
		if (logLine.startsWith(type9))
			return TYPE9;
		if (logLine.startsWith(type10))
			return TYPE10;
		if (logLine.startsWith(type11))
			return TYPE11;
		if (logLine.startsWith(type12))
			return TYPE12;
		if (logLine.startsWith(type13))
			return TYPE13;
		if (logLine.startsWith(type14))
			return TYPE14;
		if (logLine.startsWith(type15))
			return TYPE15;
		if (logLine.startsWith(type16))
			return TYPE16;
		if (logLine.startsWith(type17))
			return TYPE17;
		if (logLine.startsWith(type18))
			return TYPE18;
		if (logLine.startsWith(type19))
			return TYPE19;
		if (logLine.startsWith(type20))
			return TYPE20;
		if (logLine.startsWith(type21))
			return TYPE21;
		if (logLine.startsWith(type22))
			return TYPE22;

		return TYPE23;
	}

	private String removeLeadingChars(String logLine, char ch) {
		byte bytes[] = logLine.getBytes();
		int count = 0;
		while (bytes[count] == ch) {
			count++;
		}
		String line = logLine.substring(count, logLine.length());
		return line;
	}

	/**
	 * <[ACTIVE] ExecuteThread: '60' for queue: 'weblogic.kernel.Default
	 * (self-tuning)'>
	 * 
	 * @param webLog
	 * @param entry
	 * @return
	 */
	private WebLogicLog fillThreadId(WebLogicLog webLog, String entry) {
		String tokens[] = entry.split(" ");
		if (tokens.length > 6) {
			String threadID = tokens[2];
			String threadNumber = stringFirstandLastCharacter(threadID);
			webLog.setThreadId(threadNumber);
		}
		return webLog;
	}

	private String stringFirstandLastCharacter(String threadID) {
		int length = threadID.length();

		return threadID.substring(1, length - 1);
	}

	/**
	 * [StatelessRemoteObject] [StatelessEJBObject]
	 * 
	 * @param webLog
	 * @param messageText
	 * @return
	 */
	private WebLogicLog fillWebLogWithMessageInformation(WebLogicLog webLog,
			String messageText) {

		if (messageText.isEmpty() || messageText.equals(""))
			return webLog;

		
		if (webLog.getLineType() == TYPE16) {
			return webLog;
		}

		String tokens[] = messageText.split(" ");

		if (webLog.getLineType() == TYPE15) {

			webLog = fillWebLogWithType15(webLog, tokens, messageText);
		}
		if (webLog.getLineType() == TYPE19) {

			webLog = fillWebLogWithType19(webLog, tokens, messageText);
		}

		if (tokens[0].length() == 0)
			return webLog;

	

		if (webLog.getLineType() == TYPE1) {
			webLog = fillWebLogWithType1(webLog, tokens);
		}
		if (webLog.getLineType() == TYPE2) {
			webLog = fillWebLogWithType2(webLog, tokens);
		}
		if (webLog.getLineType() == TYPE3) {
			webLog = fillWebLogWithType3(webLog, tokens);
		}
		if (webLog.getLineType() == TYPE4) {
			webLog = fillWebLogWithType4(webLog, tokens);
		}
		if (webLog.getLineType() == TYPE5) {
			webLog = fillWebLogWithType5(webLog, tokens);
		}

		if (webLog.getLineType() == TYPE6) {

			webLog = fillWebLogWithType6(webLog, tokens);
		}

		if (webLog.getLineType() == TYPE7) {

			webLog = fillWebLogWithType7(webLog, tokens, messageText);
		}
		if (webLog.getLineType() == TYPE8) {

			webLog = fillWebLogWithType8(webLog, tokens);
		}

		if (webLog.getLineType() == TYPE12) {

			webLog = fillWebLogWithType12(webLog, tokens, messageText);
		}
		if (webLog.getLineType() == TYPE13 || webLog.getLineType() == TYPE16) {

			webLog = fillWebLogWithType13(webLog, tokens);
		}
		if (webLog.getLineType() == TYPE14) {

			webLog = fillWebLogWithType14(webLog, tokens);
		}
		if (webLog.getLineType() == TYPE17) {

			webLog = fillWebLogWithType17(webLog, tokens);
		}

		if (messageText.toLowerCase().indexOf(WebLogicLog.EXCEPTION) != -1) {
			return fillWebLogWithExceptionData(webLog, messageText);
		}

		return webLog;
	}

	private WebLogicLog fillWebLogWithExceptionData(WebLogicLog webLog,
			String messageText) {
	
		webLog.setMessageType(WebLogicLog.EXCEPTION);
		if (messageText.startsWith(type15)) {
			webLog.setMessageType("System_Exception");
		}
		if (messageText.startsWith(type12)) {

			String exception = extractExceptionData(messageText);

			webLog.setExceptionName("ExceptionTrace");
			webLog.setExceptionMessage(messageText);
			String ejbName = extractejbInterface(messageText);

			webLog.setEjBName(ejbName);
			webLog.setException(exception);

			return webLog;
		}
		if (messageText.startsWith(type13)) {
			webLog.setExceptionName("ExceptionTrace");
			webLog.setExceptionMessage(messageText);
			return webLog;
		}

		
		if (messageText.startsWith(type6)) {
			String method = extractMethod(messageText);
			String exceptionName = extractExeption(messageText);
			String ejbName = extractHomeInterface(messageText);
			webLog.setMethodName(method);
			webLog.setEjBName(ejbName);
			webLog.setExceptionName(exceptionName);
			return webLog;
		}
		return webLog;
	}

	
	/*
	 * <(CliqSearchServlet.doGet:171) {O:1dfb7eaa T:10b3fae3 null}
	 * [1446] Error while processing the xml request for smart searchNo MLS
	 * Boards permitted for Addresses provided. : smartsearch-1.3.1
	 * com.fares.smartsearch.ifc.exception.SmartSearchException: No MLS Boards
	 * permitted for Addresses provided. at
	 * com.fares.smartsearch.impl.process.SmartSearchProcess
	 * .getPropertyListingInfo(SmartSearchProcess.java:1230) at
	 * com.fares.smartsearch
	 * .web.servlet.CliqSearchServlet.doGet(CliqSearchServlet.java:144) at
	 * com.fares
	 * .smartsearch.web.servlet.CliqSearchServlet.doPost(CliqSearchServlet
	 * .java:218) at
	 * javax.servlet.http.HttpServlet.service(HttpServlet.java:727) at
	 * javax.servlet.http.HttpServlet.service(HttpServlet.java:821) at
	 * weblogic.servlet
	 * .internal.StubSecurityHelper$ServletServiceAction.run(StubSecurityHelper
	 * .java:226) at
	 * weblogic.servlet.internal.ServletStubImpl.execute(ServletStubImpl
	 * .java:283) at
	 * weblogic.servlet.internal.WebAppServletContext$ServletInvocationAction
	 * .run(WebAppServletContext.java:3404) at
	 * weblogic.security.acl.internal.AuthenticatedSubject
	 * .doAs(AuthenticatedSubject.java:321) at
	 * weblogic.servlet.internal.WebAppServletContext
	 * .execute(WebAppServletContext.java:2046) at
	 * weblogic.servlet.internal.ServletRequestImpl
	 * .run(ServletRequestImpl.java:1398) at
	 * weblogic.work.ExecuteThread.execute(ExecuteThread.java:200) at
	 * weblogic.work.ExecuteThread.run(ExecuteThread.java:172) >
	 */
	private WebLogicLog fillWebLogWithType15(WebLogicLog webLog,
			String[] tokens, String messageText) {

		if (messageText.indexOf("Exception") != -1) {
		

			webLog.setMessageType("System_Exception");
			
			String exceptionName = extractException(messageText);
			webLog.setExceptionName(exceptionName);

			String methodName = extractMethodName(messageText);

			webLog.setMethodName(methodName);
			webLog.setLineType(TYPE15);

			// TODO
		
		}
		return webLog;
	}

	private WebLogicLog fillWebLogWithType19(WebLogicLog webLog,
			String[] tokens, String messageText) {

		if (messageText.indexOf("Exception") != -1) {
			// exception message

			if (tokens.length > 13) {
				StringBuilder exceptionName = new StringBuilder(tokens[11]);

				webLog.setExceptionName(exceptionName.toString());
		
			}
			webLog.setMessageType("System_Exception");
		

			String methodName = extractMethodName(messageText);

			webLog.setMethodName(methodName);
			webLog.setLineType(TYPE19);

		
		}
		return webLog;
	}

	private String extractException(String messageText) {

		int colonIndex = messageText.indexOf(":");
		int endcolonIndex = messageText.lastIndexOf("Exception:");

		if (messageText.contains("Exception")) {
			if (colonIndex > 1) {
				String colonInterface = messageText.substring(colonIndex + 6,
						endcolonIndex+9);
				
				return colonInterface;
			}
		}
		return null;
	}

	private WebLogicLog fillWebLogWithType12(WebLogicLog webLog,
			String[] tokens, String messageText) {

		if (messageText.indexOf("exception") == -1) {
			// exception message

			webLog.setMessageType(WebLogicLog.EXCEPTION);
			String method = extractMethod(messageText);

			// TODO
			String ejbName = extractHomeInterface(messageText);

			webLog.setMethodName(method);
			webLog.setEjBName(ejbName);
		}
		return webLog;
	}

	/**
	 * /** ####<Apr 28, 2013 2:00:00 AM PDT> <Debug> <EjbInvoke> <anaeur70>
	 * <WL10MP2-ServiceSTServer1> <[ACTIVE] ExecuteThread: '44' for queue:
	 * 'weblogic.kernel.Default (self-tuning)'> <<anonymous>> <> <>
	 * <1367139600032> <BEA-000000> <[BaseRemoteObject] prePostInvoke called
	 * with txRetryCount = 0 wrap:[InvocationWrapper] callerTx: null invokeTx:
	 * null
	 * bean:com.fares.administration.ejb.EmailServiceBean_cytgry_Impl@1deebdc5
	 * primaryKey: nullMethodDescriptor: MethodDescriptor: Method:
	 * getPendingEmails(java.lang.String) Transaction attribute: TX_SUPPORTS
	 * Isolation Level: No Isolation Level Set Tx Timeout: 30000 Exception: null
	 * on: [BaseRemoteObject] home:
	 * com.fares.administration.ejb.EmailServiceBean_cytgry_HomeImpl@15fc08da>
	 * 
	 * @param webLog
	 * @param tokens
	 * @param messageText
	 * @return
	 */
	private WebLogicLog fillWebLogWithType7(WebLogicLog webLog,
			String[] tokens, String messageText) {

		if (messageText.toLowerCase().indexOf("Exception") != -1) {
			// exception message

			
			webLog.setMessageType(WebLogicLog.EXCEPTION);
			String method = extractMethodName(messageText);
			String ejbName = extractHomeInterface(messageText);
		

			webLog.setMethodName(method);
			webLog.setEjBName(ejbName);
		

		} else
		{
			if (tokens.length > 4) {
StringBuilder exceptionName = new StringBuilder(tokens[4]);
			
	webLog.setExceptionName(exceptionName.toString());
		}
		}
		return webLog;
	}

	private String extractMethodName(String messageText) {

		int atIndex = messageText.indexOf(WebLogicLog.AT);
		int closingBraceIndex = messageText.indexOf(")");
		if (messageText.contains("Exception")) {
			if (atIndex > 1) {
				String atInterface = messageText.substring(atIndex
						+ WebLogicLog.AT.length(), closingBraceIndex + 1);

		

				return atInterface;
			}
		}
		return null;
	}

	private String extractHomeInterface(String messageText) {
		int homeIndex = messageText.indexOf(WebLogicLog.HOME);
		
		if (homeIndex > 1) {
			String homeInterface = messageText.substring(homeIndex
					+ WebLogicLog.HOME.length(), messageText.length());
			return homeInterface;
		}
		return null;
	}

	private String extractExceptionData(String messageText) {
		int openingBraceIndex = messageText.indexOf(type12);
		int closingBraceIndex = messageText.indexOf(")");

		String openingBraceInterface = messageText.substring(openingBraceIndex
				+ type12.length(), closingBraceIndex);

		

		return openingBraceInterface;

	}

	private String extractejbInterface(String messageText) {


		int atIndex = messageText.indexOf(WebLogicLog.AT);
		int closingBraceIndex = messageText.indexOf("java:");
		if (messageText.contains("Exception")) {
			if (atIndex > 1) {
				String atInterface = messageText.substring(atIndex
						+ WebLogicLog.AT.length(), closingBraceIndex + 10);

		
				return atInterface;
			}
		}
		return null;

	}

	private String extractExeption(String messageText) {
		int exceptionIndex = messageText.toLowerCase().indexOf(
				WebLogicLog.EXCEPTION.toLowerCase());
		if (exceptionIndex > 1) {
			String exceptionMessage1 = messageText.substring(exceptionIndex + 2
					+ WebLogicLog.EXCEPTION.length(), messageText.length());

			int firstSpaceIndex = exceptionMessage1.indexOf(" ");
			String excpetionMessage = exceptionMessage1.substring(0,
					firstSpaceIndex);

			return excpetionMessage;
		}
		return null;
	}

	private String extractMethod(String messageText) {
		int methodIndex = messageText.indexOf(WebLogicLog.METHOD);

		if (methodIndex > 1) {
			messageText.substring(methodIndex, messageText.length());
			String tempMethod = messageText.substring(methodIndex
					+ WebLogicLog.METHOD.length(), messageText.length());
			int closeBraceIndex = tempMethod.indexOf(")");
			if (closeBraceIndex > 1) {
				String methodName = tempMethod.substring(
						1 + WebLogicLog.METHOD.length(), closeBraceIndex + 1);
				return methodName;
			}
		}
		return null;
	}

	private WebLogicLog fillWebLogWithType1(WebLogicLog webLog, String[] tokens) {

		if (tokens.length > 23) {
			StringBuilder methodName = new StringBuilder(tokens[7]);
			methodName.append(" " + tokens[8]);
			webLog.setMethodName(methodName.toString());

		

			if (tokens[23].startsWith("home:")) {
				int endindex = tokens[24].indexOf("@");

				String ejBName = tokens[24].substring(0, endindex);

				webLog.setEjBName(ejBName);

			} else if (tokens[23].startsWith("[BaseRemoteObject]")) {
				int endindex = tokens[25].indexOf("@");
				String ejBName = tokens[25].substring(0, endindex);
				webLog.setEjBName(ejBName);

			} else if (tokens[23].startsWith("on:")) {
				int endindex = tokens[26].indexOf("@");
				String ejBName = tokens[26].substring(0, endindex);
				webLog.setEjBName(ejBName);

			} else {

				int endindex = tokens[23].indexOf("@");
				String ejBName = tokens[23].substring(0, endindex);
				webLog.setEjBName(ejBName);
			}
		}
		return webLog;
	}

	private WebLogicLog fillWebLogWithType2(WebLogicLog webLog, String[] tokens) {

		if (tokens.length > 28) {

			StringBuilder methodName = new StringBuilder(tokens[13]);

			webLog.setMethodName(methodName.toString());

			webLog.setEjBName(tokens[28]);
		}
		return webLog;
	}

	private WebLogicLog fillWebLogWithType3(WebLogicLog webLog, String[] tokens) {

		if (tokens.length > 14) {
			StringBuilder methodName = new StringBuilder(tokens[9]);

			webLog.setMethodName(methodName.toString());

			webLog.setEjBName(tokens[14]);
		}
		return webLog;
	}

	private WebLogicLog fillWebLogWithType4(WebLogicLog webLog, String[] tokens) {

		if (tokens.length > 8) {
			StringBuilder methodName = new StringBuilder(tokens[8]);

			webLog.setMethodName(methodName.toString());

		}
		return webLog;
	}

	private WebLogicLog fillWebLogWithType5(WebLogicLog webLog, String[] tokens) {

		if (tokens.length > 3) {
			StringBuilder methodName = new StringBuilder(tokens[4]);

			webLog.setMethodName(methodName.toString());

		}
		return webLog;
	}

	private WebLogicLog fillWebLogWithType6(WebLogicLog webLog, String[] tokens) {

		if (tokens.length > 35) {
			StringBuilder methodName = new StringBuilder(tokens[17]);

			methodName.append(" " + tokens[17]);

			webLog.setMethodName(methodName.toString());

			String beanName = tokens[12];

			webLog.setEjBName(tokens[35]);

		}
		return webLog;
	}

	private WebLogicLog fillWebLogWithType8(WebLogicLog webLog, String[] tokens) {

		if (tokens.length > 9) {
			StringBuilder methodName = new StringBuilder(tokens[6]);

			
			webLog.setMethodName(methodName.toString());

			String poolName = tokens[7];

			
			webLog.setEjBName(tokens[11]);

		}
		return webLog;
	}

	private WebLogicLog fillWebLogWithType13(WebLogicLog webLog, String[] tokens) {

		if (tokens.length > 2) {
			StringBuilder newName = new StringBuilder(tokens[3]);

			
		}
		return webLog;
	}

	private WebLogicLog fillWebLogWithType14(WebLogicLog webLog, String[] tokens) {

		if (tokens.length > 6) {
			StringBuilder hometo = new StringBuilder(tokens[4]);

			
			String eloName = tokens[6];

			

		}
		return webLog;
	}

	/**
	 * Apr 28, 2013 11:51:21 PM PDT > <Debug> <EjbInvoke> <anaeur70>
	 * <WL10MP2-ServiceSTServer1> <[ACTIVE] ExecuteThread: '21' for queue:
	 * 'weblogic.kernel.Default (self-tuning)'> <weblogic> <> <> <1367218281487>
	 * <BEA-000000> <[BaseRemoteObject] postInvokeTxRetry returning with retry =
	 * false
	 * 
	 * @param webLog
	 * @param entry
	 * @return
	 */

	private WebLogicLog fillWebLogWithType17(WebLogicLog webLog, String[] tokens) {

		if (tokens.length > 13) {
			StringBuilder hometo = new StringBuilder(tokens[14]);

			
			String exception = tokens[13];

			webLog.setException(exception);

			

		}
		return webLog;
	}

	private WebLogicLog fillWebLogWithtimeStamp(WebLogicLog webLog, String entry) {

		final DateTimeFormatter df = DateTimeFormat
				.forPattern("MMM dd, yyyy HH:mm:ss a z");
		final DateTime dateTime = df.withOffsetParsed().parseDateTime(entry);

		webLog.setDay(dateTime.getDayOfMonth());
		webLog.setYear(dateTime.getYear());

		Calendar calendar = dateTime.toGregorianCalendar();
		if (calendar.get(Calendar.AM_PM) == Calendar.PM) {
			webLog.setHour(dateTime.getHourOfDay() + 12);
		} else
			webLog.setHour(dateTime.getHourOfDay());

		webLog.setMonth(convertMonth(dateTime.getMonthOfYear()));

		

		return webLog;

	}

	private String convertMonth(int monthOfYear) {
		// TODO Auto-generated method stub
		String month;
		switch (monthOfYear) {
		case 1:
			month = "Jan";
			break;
		case 2:
			month = "Feb";
			break;
		case 3:
			month = "Mar";
			break;
		case 4:
			month = "Apr";
			break;
		case 5:
			month = "May";
			break;
		case 6:
			month = "Jun";
			break;
		case 7:
			month = "Jul";
			break;
		case 8:
			month = "Aug";
			break;
		case 9:
			month = "Sep";
			break;
		case 10:
			month = "Oct";
			break;
		case 11:
			month = "Nov";
			break;

		default:
			month = "Dec";
		}
		return month;
	}

}
