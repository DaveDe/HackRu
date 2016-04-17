package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.bson.Document;
import org.json.JSONObject;
import com.mongodb.client.model.Projections;


import util.DBConnector;
import util.Utils;

/**
 * Servlet implementation class UserServlet
 */
@WebServlet("/user")
public class UserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public UserServlet() {
        super();
    }

    private enum Error{
    	BAD_USERNAME;
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		final String ERROR = "error_code";
		final String SUCCESS= "success";
		String username = request.getParameter("u");
		JSONObject resp = new JSONObject();
	
		
		if (Utils.isEmpty(username)){
			resp.put(SUCCESS, new Boolean(false));
			resp.put(ERROR, new Integer(Error.BAD_USERNAME.ordinal()));
		}else{
			Document searchQuery = new Document();
			searchQuery.put("username", username);
			Document result = DBConnector.getUsers(searchQuery)
						.projection(Projections.fields(Projections.excludeId(),
								Projections.include("profilePic","name"))).first();
			if (result == null){
				resp.put(SUCCESS, new Boolean(false));
				resp.put(ERROR, new Integer(Error.BAD_USERNAME.ordinal()));
			}else{
				resp = new JSONObject(result.toJson());
				resp.put(SUCCESS, new Boolean(true));
				resp.put("profile-picture",resp.remove("profilePic"));
			}
		}
		response.setContentType("text/json");
		response.getWriter().write(resp.toString());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

}
