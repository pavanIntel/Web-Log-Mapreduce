package com.corelogic.weblog.domain;



public class WebLogicLog extends WebLog {
	public static final String NORMAL = "Normal";
	public static final String EXCEPTION = "exception";
	public static final String HOME = "home:";
	public static final String AT = "at ";
	public static final String METHOD = "Method:";

	private String timestamp;
	private int day;
	private String month;
	private int year;
	private int hour;
	private String severity;
	private String subsystem;
	private String machine_name;
	private String server_name;
	private String threadId;
	private String userid;
	private String transactionid;
	private String diagnosticid;
	private String rawtime_value;
	private String messageid;
	private String ejBName;
	private String exceptionMessage;
	private String methodName;
	private	int	lineType;
	private	String	messageType = NORMAL;
	private	String	exceptionName ;
	private	String	exception ;
	
	public static String	FIND = "STATELESS";
	public static String	PRE	= "PRE";
	public static String	POST = "POST";
	
	
	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public int getDay() {
		return day;
	}

	public void setDay(int day) {
		this.day = day;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public int getYear() {
		return year;
	}

	public String getException() {
		return exception;
	}

	public void setException(String exception) {
		this.exception = exception;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public int getHour() {
		return hour;
	}

	public void setHour(int hour) {
		this.hour = hour;
	}

	public String getSeverity() {
		return severity;
	}

	public void setSeverity(String severity) {
		this.severity = severity;
	}

	public String getSubsystem() {
		return subsystem;
	}

	public void setSubsystem(String subsystem) {
		this.subsystem = subsystem;
	}

	public String getMachine_name() {
		return machine_name;
	}

	public void setMachine_name(String machine_name) {
		this.machine_name = machine_name;
	}

	public String getServer_name() {
		return server_name;
	}

	public void setServer_name(String server_name) {
		this.server_name = server_name;
	}

	public String getThreadId() {
		return threadId;
	}

	public void setThreadId(String threadId) {
		this.threadId = threadId;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getTransactionid() {
		return transactionid;
	}

	public void setTransactionid(String transactionid) {
		this.transactionid = transactionid;
	}

	public String getDiagnosticid() {
		return diagnosticid;
	}

	public void setDiagnosticid(String diagnosticid) {
		this.diagnosticid = diagnosticid;
	}

	public String getRawtime_value() {
		return rawtime_value;
	}

	public void setRawtime_value(String rawtime_value) {
		this.rawtime_value = rawtime_value;
	}

	public String getMessageid() {
		return messageid;
	}

	public void setMessageid(String messageid) {
		this.messageid = messageid;
	}

	public String getEjBName() {
		return ejBName;
	}

	public void setEjBName(String ejBName) {
		this.ejBName = ejBName;
	}

	public String getExceptionMessage() {
		return exceptionMessage;
	}

	public void setExceptionMessage(String exceptionMessage) {
		this.exceptionMessage = exceptionMessage;
	}

	public int getLineType() {
		return lineType;
	}

	public void setLineType(int lineType) {
		this.lineType = lineType;
	}

	public String getMessageType() {
		return messageType;
	}

	public void setMessageType(String messageType) {
		this.messageType = messageType;
	}

	public String getExceptionName() {
		return exceptionName;
	}

	public void setExceptionName(String exceptionName) {
		this.exceptionName = exceptionName;
	}

}
