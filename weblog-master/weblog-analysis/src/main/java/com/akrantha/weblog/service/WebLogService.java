package com.akrantha.weblog.service;

import java.util.Map;

public interface WebLogService {
	
	public Map<String, Integer> getTrasactionCount(String fromDate, String toDate, String type );

}
