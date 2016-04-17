package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import util.DBConnector;
import util.Utils;
import static com.mongodb.client.model.Filters.*;

import org.bson.Document;
import org.json.JSONObject;

import com.sun.org.apache.bcel.internal.generic.NEW;

import util.Utils;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet(value = "/login")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private enum Error{
		EMPTY_USERNAME,
		EMPTY_PASSWORD,
		WRONG_USERNAME_OR_PASSWORD
	}
 
    public LoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		final String ERROR = "error_code";
		final String LOGIN = "login";
		
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		JSONObject resp = new JSONObject();
		if (Utils.isEmpty(username)){
			resp.put("login", new Boolean(false));
			resp.put(ERROR, new Integer(Error.EMPTY_USERNAME.ordinal()));
		}else if (Utils.isEmpty(password)){
			resp.put("login", new Boolean(false));
			resp.put(ERROR, new Integer( Error.EMPTY_PASSWORD.ordinal()));
		}else{
			password = Utils.encodePassword(password);
			Document searchQuery = new Document("username",username).append("password", password);
			Document doc = DBConnector.getUsers(searchQuery).first();
			if (doc == null){
				resp.put("login", new Boolean(false));
				resp.put(ERROR,new Integer(Error.WRONG_USERNAME_OR_PASSWORD.ordinal()));
			}else{
				resp.put("login", new Boolean(true));
			}
		}
		response.setContentType("text/json");
		response.getWriter().write(resp.toString());
	}
}
