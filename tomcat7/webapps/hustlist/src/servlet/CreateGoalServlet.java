package servlet;

import java.io.IOException;
import java.util.Arrays;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.bson.Document;
import org.json.JSONObject;

import com.mongodb.BasicDBObject;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import com.mongodb.client.model.Updates.*;

import util.DBConnector;

/**
 * Servlet implementation class CreateGoalServlet
 */
@WebServlet("/user/createGoal")
public class CreateGoalServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CreateGoalServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String username = request.getParameter("u");
		String title = request.getParameter("title");
		String desc = request.getParameter("description");
		String startTime = request.getParameter("start_time");
		String endTime = request.getParameter("end_time");
		
		Document insert = new Document();
		insert.append("title",title).append("description", desc)
				.append("startTime", startTime).append("endTime",endTime);
		Document searchQuery = new Document().append("username", username);
		DBConnector.getDB().getCollection(DBConnector.USERS).updateOne(Filters.eq("username",username),Updates.push("goals", insert));
		
		JSONObject resp =new JSONObject();
		resp.put("success", true);
		response.setContentType("text/json");
		response.getWriter().write(resp.toString());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

}
