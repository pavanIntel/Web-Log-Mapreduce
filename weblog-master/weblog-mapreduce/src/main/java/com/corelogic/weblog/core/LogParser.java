package com.corelogic.weblog.core;

import java.io.IOException;

import com.corelogic.weblog.domain.WebLog;

public interface LogParser {
	WebLog	parse(String logLine) throws IOException;
}
