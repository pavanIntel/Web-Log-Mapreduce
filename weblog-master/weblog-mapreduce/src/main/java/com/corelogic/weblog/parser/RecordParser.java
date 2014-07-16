package com.corelogic.weblog.parser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RecordParser {
	long start = 0;
	long end = 0;
	private byte[] startTag;
	private byte[] endTag;
	public static final String START_TAG_KEY = "<";
	public static final String END_TAG_KEY = ">";
	List<String> tokenList = new ArrayList<String>();
	int currentCount = 0;

	String line;

	private StringBuilder buffer2 = new StringBuilder();

	public List<String> parseRecord(String value) throws IOException {

		line = value;
		start = 0;
		end = value.length();
		startTag = START_TAG_KEY.getBytes("utf-8");
		endTag = END_TAG_KEY.getBytes("utf-8");

		while (start < end && readUntilMatch(startTag, false)) {
			try {
				// buffer.write(startTag);
				if (readUntilMatch(endTag, true)) {
					String data = buffer2.substring(0, buffer2.length() - 1);
					tokenList.add(data.toString());
				}
			} finally {
				buffer2 = new StringBuilder();
			}
		}
		return tokenList;
	}

	private boolean readUntilMatch(byte[] match, boolean withinBlock)
			throws IOException {
		int i = 0;
		while (true) {
			if (currentCount == end) {
				return false;
			}
			char b = line.charAt(currentCount);
			currentCount++;
			// end of file:

			// save to buffer:
			if (withinBlock) {
				buffer2.append(b);
			}

			// check if we're matching:
			if (b == match[i]) {
				i++;
				if (i >= match.length) {
					return true;
				}
			} else {
				i = 0;
			}

			// see if we've passed the stop point:
			if (!withinBlock && i == 0 && currentCount >= end) {
				return false;
			}
		}
	}
}
