package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.bson.Document;
import org.json.JSONObject;

import util.DBConnector;
import util.Utils;

/**
 * Servlet implementation class CreateUserServlet
 */
@WebServlet("/user/create")
public class CreateUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CreateUserServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    private enum Error{
    	BAD_REQUEST,
    	ACCOUNT_EXISTED
    }
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String profilePic = request.getParameter("profile_picture");
		String name = request.getParameter("name");
		final String CREATED = "created";
		final String ERROR = "error_code";
		JSONObject resp = new JSONObject();
		if (Utils.isEmpty(username) || Utils.isEmpty(password) || Utils.isEmpty(profilePic) || Utils.isEmpty(name)){
			resp.put(CREATED, new Boolean(false));
			resp.put(ERROR, new Integer(Error.BAD_REQUEST.ordinal()));
		}else {
			Document searchQuery = new Document().append("username", username);
			Document result = DBConnector.getUsers(searchQuery).first();
			if (result != null){
				resp.put(CREATED, new Boolean(false));
				resp.put(ERROR, new Integer(Error.ACCOUNT_EXISTED.ordinal()));
			}else{
				Document insertDoc = searchQuery;
				insertDoc.append("password", Utils.encodePassword(password));
				insertDoc.append("profilePic",profilePic);
				insertDoc.append("name", name);
				DBConnector.getDB().getCollection(DBConnector.USERS).insertOne(insertDoc);
				resp.put(CREATED, new Boolean(true));
			}
		}
		response.setContentType("text/json");
		response.getWriter().write(resp.toString());
		
	}

}
