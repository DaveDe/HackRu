package servlet;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.json.JSONArray;
import org.json.JSONObject;

import com.mongodb.client.AggregateIterable;
import com.mongodb.client.FindIterable;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Projections;

import util.DBConnector;
import util.Utils;
import static java.util.Arrays.asList;

/**
 * Servlet implementation class UserGoalServlet
 */
@WebServlet("/user/goals")
public class UserGoalServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserGoalServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	private enum Error{
		BAD_INPUT,
		WRONG_USERNAME
		
	}
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username = request.getParameter("u");
		String progress = request.getParameter("progress");
		String after = request.getParameter("after");
		String access = request.getParameter("access");
		
		final String ERROR="error_code";
		final String SUCCESS = "success";
		
		JSONObject resp = new JSONObject();
		if (Utils.isEmpty(username)){
			resp.put(ERROR, new Integer(Error.BAD_INPUT.ordinal()));
			resp.put(SUCCESS,new Boolean(false));
		}else{
			Document searchQuery = new Document();
			searchQuery.append("username", username);

			Bson condition = null;
			
			if(!Utils.isEmpty(progress) ){
				switch(progress){
				case "done":
					condition = Filters.eq("goals.done",true);
					break;
				case "not_done":
					condition = Filters.eq("goals.done",false);
					break;
				case "overdue":
					condition = Filters.lt("goals.endTime", new Date());
					break;
				}
			}
			if (!Utils.isEmpty(after)){
				if (condition==null){
					condition = Filters.gte("goals.startTime", after);
				}else{
					condition= Filters.and(condition,Filters.gte("goals.startTime", after));
				}
			}
			if (condition==null){
				condition = Filters.eq("username", username);
			}else{
				condition = Filters.and(condition,Filters.eq("username",username));
			}
			Document goals = DBConnector.getDB().getCollection(DBConnector.USERS).find(condition).first();
			JSONObject result = new JSONObject(goals.toJson());
			if (result.has("goals")){
				resp.put("goals",result.getJSONArray("goals") );
			}else{
				resp.put("goals", new JSONArray());
			}
		}
		response.setContentType("text/json");
		response.getWriter().write(resp.toString());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username = request.getParameter("u");
		String progress = request.getParameter("progress");
		String after = request.getParameter("after");
		String access = request.getParameter("access");
		
		final String ERROR="error_code";
		final String SUCCESS = "success";
		
		JSONObject resp = new JSONObject();
		if (Utils.isEmpty(username)){
			resp.put(ERROR, new Integer(Error.BAD_INPUT.ordinal()));
			resp.put(SUCCESS,new Boolean(false));
		}else{
			Document searchQuery = new Document();
			searchQuery.append("username", username);

			Bson condition = null;
			
			if(!Utils.isEmpty(progress) ){
				switch(progress){
				case "done":
					condition = Filters.eq("goals.done",true);
					break;
				case "not_done":
					condition = Filters.eq("goals.done",false);
					break;
				case "overdue":
					condition = Filters.lt("goals.endTime", new Date());
					break;
				}
			}
			if (!Utils.isEmpty(after)){
				if (condition==null){
					condition = Filters.gte("goals.startTime", after);
				}else{
					condition= Filters.and(condition,Filters.gte("goals.startTime", after));
				}
			}
			if (condition==null){
				condition = Filters.eq("username", username);
			}else{
				condition = Filters.and(condition,Filters.eq("username",username));
			}
			Document goals = DBConnector.getDB().getCollection(DBConnector.USERS).find(condition).first();
			JSONObject result = new JSONObject(goals.toJson());
			if (result.has("goals")){
				resp.put("goals",result.getJSONArray("goals") );
			}else{
				resp.put("goals", new JSONArray());
			}
		}
		response.setContentType("text/json");
		response.getWriter().write(resp.toString());
	}

}
