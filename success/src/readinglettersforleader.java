import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class readinglettersforleader extends HttpServlet {
public void doGet(HttpServletRequest req,HttpServletResponse res) throws IOException
{PrintWriter out = res.getWriter();
res.setContentType("text/html");
	try
	{
		Class.forName("oracle.jdbc.driver.OracleDriver");
		Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521/xe","system","sql");
		//out.println("connected");
		Statement smt = con.createStatement();
		//out.println(username);
		//out.println(password);
		/*choosing table in database according to designation*/
		ResultSet rs;
	rs = smt.executeQuery("Select * from leaderletters");
	int flag = 0;
	while(rs.next())
	{
    flag = 1;
 out.println("username : "+rs.getString(1));
 out.println("subject : "+rs.getString(2));
 out.println("body : "+rs.getString(3));
 out.print("<form action = 'accept'><input type = 'hidden' name = 'username' value = '"+rs.getString(1)+"'><input type = 'hidden' name = 'subject' value = '"+rs.getString(2)+"'><input type = 'submit' value = 'accept'></form> ");
 out.println("<form action = 'delete'><input type = 'hidden' name = 'username' value = '"+rs.getString(1)+"'><input type = 'hidden' name = 'subject' value = '"+rs.getString(2)+"'><input type = 'submit' value = 'reject'></form> ");
 out.println("----------------------");
 out.print("<br/>");
	}
	con.close();
	if(flag==0)
		out.println("no letters to display");
}catch(Exception e)
{
	out.println("Our application is failed due to:" + e);
}
}
}
