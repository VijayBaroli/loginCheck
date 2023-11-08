import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

@WebServlet("/login")
public class LoginPage extends HttpServlet{
	
	protected void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		String uid = req.getParameter("uid");
		String pass = req.getParameter("pass");
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection cn = DriverManager.getConnection("jdbc:mysql://localhost:3306/jdbc2?createDatabaseIfNotExist=true", "root", "mysql");
			PreparedStatement pst = cn.prepareStatement("select * from user where uid = ?");
			pst.setString(1, uid);
			ResultSet rst = pst.executeQuery();
			
			PrintWriter pr = res.getWriter();
			pr.println("<html><head>");
			pr.println("</head><body>");
			if(rst.next()) {
				if(pass.equals(rst.getString(3))) {
					res.sendRedirect("home.html");
				}
				else {
					pr.println("<h3 style='color:red'>Incorrect password</h3>");
					RequestDispatcher rd = req.getRequestDispatcher("index.html");
					rd.include(req, res);
				}
			}
			else {
				pr.println("<h3 style='color:red'>User id does not exist</h3>");
				RequestDispatcher rd = req.getRequestDispatcher("index.html");
				rd.include(req, res);
			}
			pr.println("<body></html>");
			
		} catch (Exception e) {
			System.out.println(e);
		}
	}
}
