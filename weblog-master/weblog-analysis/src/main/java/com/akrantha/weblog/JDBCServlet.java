package com.akrantha.weblog;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import org.apache.log4j.Logger;

public class JDBCServlet extends HttpServlet {
	private static Logger logger = Logger.getLogger(JDBCServlet.class);

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		JSONObject tempResult = new JSONObject();
		try {
			tempResult.put("success", "true");

			tempResult.put("totalWords", 23);
			// Map<Long,Double> countVsAggregateSumMap = new TreeMap<Long,
			// Double>();
			// for (Map.Entry<Long,Long> entry :
			// namedEntityRecognizerService.getCountVsAggregateSumMap().entrySet())
			// {
			// countVsAggregateSumMap.put(entry.getKey() ,
			// (entry.getValue()*100.0)/totalWords);
			// }
			tempResult.put("countVsAggregateSum", 100);
			// Write response data as JSON.
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			PrintWriter out = response.getWriter();
			out.write(tempResult.toString());
		} catch (JSONException e) {
			logger.error("Couldn't create json response");
		}
	}

}
