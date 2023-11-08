import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/register")
public class RegisteredPage extends HttpServlet{
	protected void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection cn = DriverManager.getConnection("jdbc:mysql://localhost:3306/jdbc2?createDatabaseIfNotExist=true", "root", "mysql");
			Statement st = cn.createStatement();
			st.execute("create table if not exists user(uid varchar(30) primary key, name varchar(30), password varchar(30))");
			PreparedStatement pst = cn.prepareStatement("insert into user value(?,?,?)");
			
			String uid = req.getParameter("uid");
			String name = req.getParameter("name");
			String pass = req.getParameter("pass");
			
			pst.setString(1, uid);
			pst.setString(2, name);
			pst.setString(3, pass);
			pst.executeUpdate();
			
			PrintWriter pr = res.getWriter();
			pr.println("<html><head>");
			pr.println("</head><body>");
			pr.println("<h1 style ='color:green; margin:auto'>You are registered succesfully</h1>");
			pr.println("<a href=\"index.html\">Go to login page</a>");
			pr.println("<body></html>");
			
		} catch (Exception e) {
			System.out.println(e);
		}
	}
}
