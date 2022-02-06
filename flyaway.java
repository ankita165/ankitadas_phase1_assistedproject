DAO Class
package com.flightbooking.database;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.simplilearn.workshop.util.StringUtil;
public class ConnectionUtil {
public Connection con=null;
public Statement st=null;
public ConnectionUtil() throws ClassNotFoundException, SQLException{
Class.forName("com.mysql.cj.jdbc.Driver");
con=DriverManager.getConnection("jdbc:mysql://localhost:3306/flyaway","root
","9866");
System.out.println("connection established with database");
st=con.createStatement();
}
public List<String[]> getAvailableFlights(String f, String t, String d) {
List<String[]> flights=new ArrayList<>();
String query="SELECT * FROM flyaway.flights where fromf='"+f+"' and
tof='"+t+"' and datef='"+d+"'";
try {
ResultSet rs=st.executeQuery(query);
if(rs.next()) {
String[] flight=new String[3];
flight[0]=rs.getString("name");
flight[1]=rs.getString("timef");
flight[2]=rs.getString("price");
flights.add(flight);
return flights;
}
} catch (SQLException e) {
e.printStackTrace();
}
return null;
}
public HashMap<String, String> checkUser(String email, String password) {
HashMap<String,String> user=null;
String query="select * from user where email='"+email+"' and
password='"+password+"'";
try {
ResultSet rs=st.executeQuery(query);
if(rs.next()) {
user=new HashMap<>();
user.put("name", rs.getString("name"));
user.put("email",rs.getString("email"));
user.put("phno",rs.getString("phno"));
user.put("adno",rs.getString("adno"));
}
return user;
} catch (SQLException e) {
e.printStackTrace();
}
return user;
}
public boolean insertUser(HashMap<String, String> user) {
String query="INSERT INTO user (email, password, name, phno, adno)
values('"+user.get("email")+"','"+user.get("password")+"','"+user.get("name")+"','
"+user.get("phno")+"','"+user.get("adno")+"')";
try {
st.execute(query);
return true;
} catch (SQLException e) {
e.printStackTrace();
}
return false;
}
public boolean checkAdmin(String email, String password) {
try {
ResultSet rs=st.executeQuery("select * from admin where
email='"+email+"' and password='"+password+"'");
if(rs.next())
return true;
} catch (SQLException e) {
e.printStackTrace();
}
return false;
}
public boolean changeAdminPassword(String email, String password) {
try {
ResultSet rs=st.executeQuery("select * from admin where
email='"+email+"'");
if(!rs.next()) {
return false;
}
st.execute("update admin set password='"+password+"' where
email='"+email+"'");
return true;
} catch (SQLException e) {
e.printStackTrace();
}
return false;
}
public boolean insertFlight(HashMap<String, String> flight) throws SQLException {
//PreparedStatement stm=con.prepareStatement("INSERT INTO 'flyaway.flights'
('name', 'from', 'to', 'date', 'time', 'price') values(?,?,?,?,?,?)");
//String sql="INSERT INTO flights ('name','from','to','date','time','price')
values('"+flight.get("name")+"','"+flight.get("from")+"','"+flight.get("to")+"','"
+flight.get("date")+"','"+flight.get("time")+"','"+flight.get("price")+"');";
String query1 = "INSERT INTO flights (name, fromf, tof, datef, timef, price)
VALUES" + " ('"
+ StringUtil.fixSqlFieldValue(flight.get("name")) + "'," + " '"
+ StringUtil.fixSqlFieldValue(flight.get("from")) + "'," + " '"
+ StringUtil.fixSqlFieldValue(flight.get("to")) + "'," + " '" +
StringUtil.fixSqlFieldValue(flight.get("date")) + "'," + " '"
+ StringUtil.fixSqlFieldValue(flight.get("time")) + "'," + " '"
+ StringUtil.fixSqlFieldValue(flight.get("price")) + "')";
//String sql="INSERT INTO `flyaway`.`flights` (`name`, `fromf`, `tof`,
`datef`, `timef`, `price`) VALUES ('indigo', 'hyd', 'viz', '2021-04-08', '10:00',
'2000');";
System.out.println(flight.get("date"));
System.out.println(flight.get("time"));
//String query1="INSERT into flyaway.flights (name,from,to,date,time,price)
values('indigo','hyd','viz','24-02-2022','10:30','2000')";
try {
//stm.execute();
st.execute(query1);
return true;
} catch (SQLException e) {
System.out.print("error");
e.printStackTrace();
}
return false;
}
}
SERVLETS
FilghtInsert.java
package com.flightbooking.servlets;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.flightbooking.database.ConnectionUtil;
@WebServlet("/InsertFlight")
public class FilghtInsert extends HttpServlet {
private static final long serialVersionUID = 1L;
protected void doPost(HttpServletRequest request, HttpServletResponse response)
throws ServletException, IOException {
String name=request.getParameter("name");
String from=request.getParameter("from");
String to=request.getParameter("to");
String departure=request.getParameter("departure");
String time=request.getParameter("time");
String price=request.getParameter("price");
HashMap<String,String> flight=new HashMap<>();
flight.put("name", name);
flight.put("from", from);
flight.put("to", to);
flight.put("date", departure);
flight.put("time", time);
flight.put("price", price);
try {
ConnectionUtil dao=new ConnectionUtil();
HttpSession session=request.getSession();
if(dao.insertFlight(flight)) {
session.setAttribute("message", "Flight Added
Successfully");
}
else {
session.setAttribute("message", "Invalid Details");
}
} catch (ClassNotFoundException | SQLException e) {
// TODO Auto-generated catch block
System.out.print("error");
e.printStackTrace();
}
response.sendRedirect("AdminHome.jsp");
}
}
ForgotPassword.java
package com.flightbooking.servlets;
import javax.servlet.annotation.WebServlet;
import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.flightbooking.database.ConnectionUtil;
@WebServlet("/ForgotPassword")
public class ForgotPassword extends HttpServlet {
private static final long serialVersionUID = 1L;
protected void doPost(HttpServletRequest request, HttpServletResponse response)
throws ServletException, IOException
{
String email=request.getParameter("email");
String password=request.getParameter("password");
try {
ConnectionUtil dao=new ConnectionUtil();
HttpSession session=request.getSession();
if(dao.changeAdminPassword(email,password)) {
session.setAttribute("message", "Password Changed
Successfully");
}
else {
session.setAttribute("message", "Invalid Details");
}
} catch (ClassNotFoundException | SQLException e) {
// TODO Auto-generated catch block
e.printStackTrace();
}
response.sendRedirect("AdminPage.jsp");
}
}
ListOfFlights.java
package com.flightbooking.servlets;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.flightbooking.database.ConnectionUtil;
@WebServlet("/FlightList")
public class ListOfFlights extends HttpServlet {
private static final long serialVersionUID = 1L;

protected void doPost(HttpServletRequest request, HttpServletResponse
response) throws ServletException, IOException {
String from=request.getParameter("from");
String to=request.getParameter("to");
String departure=request.getParameter("departure");
try {
ConnectionUtil dao = new ConnectionUtil();
List<String[]> flights=dao.getAvailableFlights(from, to,
departure);
HttpSession session=request.getSession();
session.setAttribute("flights", flights);
} catch (ClassNotFoundException | SQLException e) {
// TODO Auto-generated catch block
e.printStackTrace();
}
response.sendRedirect("FlightList.jsp");
}
}
LoginOfAdmin.java
package com.flightbooking.servlets;
import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.flightbooking.database.ConnectionUtil;
@WebServlet("/AdminLogin")
public class LoginOfAdmin extends HttpServlet {
private static final long serialVersionUID = 1L;

protected void doPost(HttpServletRequest request, HttpServletResponse response)
throws ServletException, IOException {
 String email=request.getParameter("email");
String password=request.getParameter("password");
try {
ConnectionUtil dao=new ConnectionUtil();
if(dao.checkAdmin(email,password)) {
response.sendRedirect("AdminHome.jsp");
}
else {
HttpSession session=request.getSession();
session.setAttribute("message", "Invalid Details");
response.sendRedirect("AdminPage.jsp");
}
} catch (ClassNotFoundException | SQLException e) {
// TODO Auto-generated catch block
e.printStackTrace();
}
}
}
LoginOfUser.java
package com.flightbooking.servlets;
import javax.servlet.annotation.WebServlet;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.flightbooking.database.ConnectionUtil;
@WebServlet("/UserLogin")
public class LoginOfUser extends HttpServlet {
private static final long serialVersionUID = 1L;
protected void doPost(HttpServletRequest request, HttpServletResponse response)
throws ServletException, IOException {
String email=request.getParameter("email");
String password=request.getParameter("password");
try {
ConnectionUtil dao=new ConnectionUtil();
HashMap<String,String> user=dao.checkUser(email,password);
HttpSession session=request.getSession();
if(user!=null) {
session.setAttribute("user", user);
response.sendRedirect("HomePage.jsp");
}
else {
session.setAttribute("message", "Invalid Details");
response.sendRedirect("UserPage.jsp");
}
} catch (ClassNotFoundException | SQLException e) {
// TODO Auto-generated catch block
e.printStackTrace();
}
}
}
Logout.java
package com.flightbooking.servlets;
import javax.servlet.annotation.WebServlet;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
@WebServlet("/Logout")
public class Logout extends HttpServlet {
private static final long serialVersionUID = 1L;
protected void doGet(HttpServletRequest request, HttpServletResponse response)
throws ServletException, IOException {
HttpSession session=request.getSession();
session.setAttribute("user", null);
response.sendRedirect("HomePage.jsp");
}
}
RegistrationOfUser.java
package com.flightbooking.servlets;
import javax.servlet.annotation.WebServlet;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.flightbooking.database.ConnectionUtil;
@WebServlet("/UserRegistration")
public class RegistrationOfUser extends HttpServlet {
private static final long serialVersionUID = 1L;
protected void doPost(HttpServletRequest request, HttpServletResponse
response) throws ServletException, IOException {
String email=request.getParameter("email");
String password=request.getParameter("password");
String name=request.getParameter("name");
String phno=request.getParameter("phno");
String adno=request.getParameter("adno");
HashMap<String,String> user=new HashMap<>();
user.put("email", email);
user.put("password", password);
user.put("name", name);
user.put("phno", phno);
user.put("adno", adno);
try {
ConnectionUtil dao=new ConnectionUtil();
boolean result=dao.insertUser(user);
HttpSession session=request.getSession();
if(result) {
session.setAttribute("message", "User Added
Successfully");
}
else {
session.setAttribute("message","Invalid Details");
}
} catch (ClassNotFoundException | SQLException e) {
// TODO Auto-generated catch block
e.printStackTrace();
}
response.sendRedirect("UserPage.jsp");
}
}
StringUtil.java
package com.simplelearn.util;
public class StringUtil {
public static String fixSqlFieldValue(String value) {
if (value == null) {
return null;
}
int length = value.length();
StringBuffer fixedValue = new StringBuffer((int)(length*1.1));
for(int i = 0 ; i < length ;i++) {
char c = value.charAt(i);
if ( c == '\'') {
fixedValue.append("''");
}else {
fixedValue.append(c);
}
}
return fixedValue.toString();
}
public static String encodeHtmlTag(String tag) {
if (tag==null)
return null;
int length = tag.length();
StringBuffer encodedTag = new StringBuffer(2 * length);
for(int i = 0 ; i < length;i++) {
char c = tag.charAt(i);
if(c=='<')
encodedTag.append("<");
else if(c=='>')
encodedTag.append(">");
else if(c=='&')
encodedTag.append("&amp;");
else if(c=='"')
encodedTag.append("&quot;");
else if(c==' ')
encodedTag.append("&nbsp;");
else
encodedTag.append(c);
}
return encodedTag.toString();
}
}
JSP
AdminHome.jsp
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
 pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Admin Home</title>
</head>
<body >
<br>
<a href=HomePage.jsp style="color:black;text-decoration:none ;font-size:35px;fontweight:bold;">Flight Booking</a>
<br><br>
<h1>Insert New Flight Details</h1>
<div style="border:3px solid black;width:25%;border-radius:20px;padding:20px"
align="center">
<form action=InsertFlight method=post>
<label for=name>Name :-</label> <input type="text" name=name id=name
/><br><br>
<label for=from>From :-</label> <input type="text" name=from id=from
/><br><br>
<label for=to>To :-</label> <input type="text" name=to id=to /><br><br>
<label for=departure>Departure :-</label> <input type="date" name=departure
id=departure /><br><br>
<label for=time>Time :-</label> <input type="time" name=time id=time
/><br><br>
<label for=price>Price :-</label> <input type="text" name=price id=price
/><br><br>
<input type=submit value=submit /> <input type=reset />
</form>
</div>
<%
String message=(String)session.getAttribute("message");
if(message!=null){
%>
<p style="color:silver;"><%=message %></p>
<%
session.setAttribute("message", null);
}
%>
</body>
</html>
AdminPage.jsp
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
 pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Admin Page</title>
</head>
<body >
<br>
<a href=HomePage.jsp style="color:black;text-decoration:none ;font-size:35px;fontweight:bold;">Flight Booking</a>
<br><br>
<h2>Admin Login</h2>
<div style="border:3px solid black;width:25%;border-radius:20px;padding:20px"
align="center">
<form action=AdminLogin method=post>
<label for=email>Email :-</label> <input type="email" name=email id=email
/><br><br>
<label for=pass>Password :-</label> <input type="password" name=password
id=pass /><br><br>
<input type=submit value=submit /> <input type=reset />
</form>
</div>
<a href=ForgotPassword.jsp style="font-size:25;color:red;">Forgot Password</a>
<%
String message=(String)session.getAttribute("message");
if(message!=null){
%>
<p style="color:silver;"><%=message %></p>
<%
session.setAttribute("message", null);
}
%>
</body>
</html>
BookFlight.jsp
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
 pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.*" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>FlyAway</title>
</head>
<body >
<br>
<a href=HomePage.jsp style="color:black;text-decoration:none ;font-size:35px;fontweight:bold;">Flight Booking</a>
<br><br>
<%
@SuppressWarnings("unchecked")
HashMap<String,String>
user=(HashMap<String,String>)session.getAttribute("user");
if(user==null){
response.sendRedirect("UserPage.jsp");
}
%>
<p align="center" style="color:green;font-size:40px;font-weight:bold">Flight
Booked Successfully</p>
</body>
</html>
FlightList.jsp
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
 pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<%@ page import="java.util.*" %>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Flight List</title>
</head>
<body >
<br>
<a href=HomePage.jsp style="color:black;text-decoration:none;font-size:35px;fontweight:bold;">Flight Booking</a>
<br><br>
<%
@SuppressWarnings("unchecked")
List<String[]> flights=(List<String[]>)session.getAttribute("flights");
if(flights!=null){
%>
<h1>Available Flights</h1>
<table border="1">
<tr>
<th>Name</th>
<th>Time</th>
<th>Price</th>
</tr>
<%
for(String[] flight:flights){
%>
<tr>
<td><%=flight[0]%></td>
<td><%=flight[1]%></td>
<td><%=flight[2]%></td>
</tr>
</table>
<a href=BookFlight.jsp>Book Now</a>
<%
}
%>
<%
}
else{
%>
<h1>There are no available flights</h1>
<%
}
%>
</body>
</html>
ForgotPassword.jsp
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
 pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body >
<br>
<a href=HomePage.jsp style="color:black;text-decoration:none ;font-size:35px;fontweight:bold;">Flight Booking</a>
<br><br>
<div style="border:3px solid black;width:25%;border-radius:20px;padding:20px"
align="center">
<form action=ForgotPassword method=post>
<label for=email>Email :-</label> <input type="email" name=email id=email
/><br><br>
<label for=pass>New Password :-</label> <input type="password"
name=password id=pass /><br><br>
<input type=submit value=submit /> <input type=reset />
</form>
</div>
</body>
</html>
HomePage.jsp
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
 pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.*" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Flight Booking</title>
</head>
<body >
<h1>FlyAway</h1>
<div align="right">
<a href="AdminPage.jsp">Admin Login</a>
</div>
<%
@SuppressWarnings("unchecked")
HashMap<String,String>
user=(HashMap<String,String>)session.getAttribute("user");
if(user!=null){
%>
<p>Welcome <%=user.get("name") %></p>
<a href="Logout">Logout</a>
<%
}else{
%>
<a href=UserPage.jsp>User Login</a>
<%
}
%>
<br><br>
<div style="border:5px solid black;width:25%;border-radius:20px;padding:20px"
align="center">
<form action=FlightList method=post>
<label for=from>From :-</label> <input type=text name=from
id=from/><br><br>
<label for=to>To :-</label> <input type=text name=to id=to/><br><br>
<label for=departure>Departure :-</label> <input type=date name=departure
id=departure/><br><br>
<label for=travellers>Travellers :-</label> <input type=number
name=travellers id=travellers/><br><br>
<input type=submit value=Search /> <input type=reset />
</form>
</div>
</body>
</html>
UserPage.jsp
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
 pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body >
<br>
<a href=HomePage.jsp style="color:black;text-decoration:none ;font-size:35px;fontweight:bold;">Flight Booking</a>
<br><br>
<h2>User Login</h2>
<div style="border:2px solid black;width:25%;border-radius:20px;padding:20px"
align="center">
<form action=UserLogin method=post >
 <table >
 <tr>
 <td><label for=email>Email</label><br></td>
 <td><input type="email" name=email id=email /></td>
 </tr>
 <tr>
 <td><label for=pass>Password</label><br></td>
 <td><input type="password" name=password id=pass /></td>
 </tr>

 <tr>
 <td><input type=submit value=submit /></td>
 <td><input type=reset /></td>
 </tr>
 </table>
</form>
</div>
<br><br>
<i>New User-Create account</i>
<h4><a href=UserRegistration.jsp style="font-size:25;color:red;">Create
Account</a></h4>
<%
String message=(String)session.getAttribute("message");
if(message!=null){
%>
<p style="color:silver;"><%=message %></p>
<%
session.setAttribute("message", null);
}
%>
</body>
</html>
UserRegistration.jsp
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
 pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body >
<br>
<a href=HomePage.jsp style="color:black;text-decoration:none ;font-size:35px;fontweight:bold;">Flight Booking</a>
<br><br>
<div style="border:3px solid black;width:25%;border-radius:20px;padding:20px"
align="center">
<form action=UserRegistration method=post>
<label for=email>Email :-</label> <input type="email" name=email id=email
/><br><br>
<label for=pass>Password :-</label> <input type="password" name=password
id=pass /><br><br>
<label for=name>Name :-</label> <input type="text" name=name id=name
/><br><br>
<label for=phno>Phone No. :-</label> <input type="text" name=phno id=phno
/><br><br>
<label for=adno>Aadhaar No. :-</label> <input type="text" name=adno id=adno
/><br><br>
<input type=submit value=submit /> <input type=reset />
</form>
</div>
</body>
</html>
web.xml
<?xml version="1.0" encoding="UTF-8"?>
<web-app xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
xmlns="http://xmlns.jcp.org/xml/ns/javaee"
xsi:schemaLocation="http://xmlns.jcp.org/xml/ns/javaee
http://xmlns.jcp.org/xml/ns/javaee/web-app_4_0.xsd" version="4.0">
 <display-name>Flight Booking</display-name>
<welcome-file-list>
 <welcome-file>UserRegistration.jsp</welcome-file>
 <welcome-file>AdminHome.jsp</welcome-file>
 <welcome-file>AdminPage.jsp</welcome-file>
 <welcome-file>FlightList.jsp</welcome-file>
 <welcome-file>BookFlight.jsp</welcome-file>
 <welcome-file>ForgotPassword.jsp</welcome-file>
 <welcome-file>HomePage.jsp</welcome-file>
 <welcome-file>UserPage.jsp</welcome-file>
 </welcome-file-list>
</web-app>
pom.xml
<project xmlns="http://maven.apache.org/POM/4.0.0"
xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
xsi:schemaLocation="http://maven.apache.org/POM/4.0.0
https://maven.apache.org/xsd/maven-4.0.0.xsd">
 <modelVersion>4.0.0</modelVersion>
 <groupId>org.simplilearn.workshop</groupId>
 <artifactId>FlyAway</artifactId>
 <version>1.0-SNAPSHOT</version>
 <packaging>war</packaging>
 <name>FlyAway</name>
 <description>FlyAway is a ticket-booking portal that lets people book flights on
their website.</description>
 <properties>
<maven.compiler.source>1.8</maven.compiler.source>
<maven.compiler.target>1.8</maven.compiler.target>
</properties>
<build>
 <plugins>
 <plugin>
 <groupId>org.apache.maven.plugins</groupId>
 <artifactId>maven-war-plugin</artifactId>
 <version>2.6</version>
 <configuration>
 <failOnMissingWebXml>false</failOnMissingWebXml>
 </configuration>
 </plugin>
 </plugins>
 </build>
<dependencies>
 <dependency>
 <groupId>javax.servlet.jsp</groupId>
 <artifactId>javax.servlet.jsp-api</artifactId>
 <version>2.3.3</version>
 <scope>provided</scope>
 </dependency>
<dependency>
<groupId>javax.servlet</groupId>
<artifactId>javax.servlet-api</artifactId>
<version>4.0.1</version>
<scope>provided</scope>
</dependency>
<dependency>
 <groupId>mysql</groupId>
 <artifactId>mysql-connector-java</artifactId>
 <version>8.0.23</version>
</dependency>
</dependencies>
</project>