drop database Lab2
create database Lab2
go
use Lab2
go

create table TblRole (
	id int primary key,
	roleName varchar(20) not null
)

create table TblUser (
	email varchar(50) primary key,
	name nvarchar(MAX) not null,
	password nvarchar(MAX) not null,
	status bit default 1,
	roleId int foreign key references TblRole(id),
)

create table TblSubject (
	id varchar(50) primary key not null,
	name nvarchar(MAX) not null,
	timeToTakeQuiz int not null check(timeToTakeQuiz > 15),
	totalOfQuestionsPerQuiz int not null check (totalOfQuestionsPerQuiz > 5),
	pointPerQuestion float not null,
	status bit default 1
)

create table TblQuestion (
	id varchar(50) primary key not null,
	question_content nvarchar(MAX) not null,
	answerA nvarchar(MAX) not null,
	answerB nvarchar(MAX) not null,
	answerC nvarchar(MAX) not null,
	answerD nvarchar(MAX) not null,
	correctAnswer int check (correctAnswer > 0 and correctAnswer < 5),
	status bit default 1,
	subID varchar (50) foreign key references TblSubject(id)
)

create table TblQuiz (
	id varchar(50) primary key not null,
	userId varchar(50) foreign key references TblUser(email),
	subjectId varchar(50) foreign key references TblSubject(id),
	startTime datetime not null,
	endTime datetime not null,
	point int check (point >= 0 and point <= 100),
)

create table TblAnswer (
	id varchar(50) primary key,
	quizId varchar(50) foreign key references TblQuiz(id),
	questionId varchar(50) foreign key references TblQuestion(id),
	isCorrect bit not null,
	choice int check (choice > -1 and choice < 5) default 0,
)

insert TblRole(id, roleName) values (1, 'STUDENT'), (2, 'ADMIN')
insert TblSubject(id, name, status, timeToTakeQuiz, totalOfQuestionsPerQuiz, pointPerQuestion)
values ('PRJ321', 'Java Web', 1, 5, 40, 2.5),
		('PRJ311', 'Java Desktop', 1, 5, 50, 2),
		('PRN292', 'C#.NET', 1, 5, 20, 5)

insert TblQuestion(id, question_content, answerA, answerB, answerC, answerD, correctAnswer, status, subId)
values ('PRJ321_Question_1', 'Class HttpServlet defines the methods _____ and _______ to response to get and post request from a client.', 'DoGet, DoPost', 'doGet, doPost', 'doGET, doPOST', 'Get, Post', 2, 1, 'PRJ321'),
('PRJ321_Question_2', 'Classes HttpServlet and GenericServlet implement the ___ interface.', 'Servlet', 'Http', 'HttpServletRequest', 'HttpServletResponse', 1, 1, 'PRJ321'),
('PRJ321_Question_3', 'Name the default value of the scope attribute of <jsp:useBean>', 'page', 'session', 'application', 'request', 1, 1, 'PRJ321'),
('PRJ321_Question_4', 'Which jsp tag can be used to set bean property?', 'jsp:useBean', 'jsp:property', 'jsp:setProperty', 'jsp:useBean.setProperty', 4, 1, 'PRJ321'),
('PRJ321_Question_5', 'Which element defined within the taglib element of taglib descriptor file is required? Select one correct answer.', 'Tag', 'Description', 'Validator', 'Name', 1, 1, 'PRJ321'),
('PRJ321_Question_6', 'HttpServletRequest.getSession() method returns a_____object.', 'HttpServletSession.', 'HttpResponseSession.', 'HttpRequestSession.', 'HttpSession.', 4, 1, 'PRJ321'),
('PRJ321_Question_7', 'Which is NOT true about stateless session beans?', 'They are used to represent data stored in a RDBMS', 'They are used to implement business logic', 'They are an Enterprises Beans', 'They are CANNOT hold client state', 1, 1, 'PRJ321'),
('PRJ321_Question_8', 'Which Java technology provides a unified interface to multiple naming and directory services?', 'JNI', 'JDBC', 'JavaMail', 'JNDI', 4, 1, 'PRJ321'),
('PRJ321_Question_9', 'A(n)_enables a web application to obtain a Connection to a database.', 'DataSource', 'Netbean', 'Eclipse', 'Web server', 1, 1, 'PRJ321'),
('PRJ321_Question_10', 'Which is NOT provided by the EJB tier in a multitier JEE (J2EE) application?', 'XML Parsing', 'Concurrency control', 'Transaction management', 'Security', 4, 1, 'PRJ321'),
('PRJ321_Question_11', 'Which type of JEE (or J2EE) component is used to store business data persistently?', 'Stateful session beans', 'Java Server Pages', 'Stateles session beans', 'Entity Bean', 4, 1, 'PRJ321'),
('PRJ321_Question_12', 'Which is true about JDBC?', ' The JDBC API is an extension of the ODBC API', 'All JDBC drivers are pure Java.', 'JDBC is used to connect to MOM (Message-Oriented Middleware Product)', 'The JDBC API is included in J2SE', 4, 1, 'PRJ321'),
('PRJ321_Question_13', 'Data Integrity is the biggest issue for your web application. What will you do?', 'Use HTTPS instead of HTTP.', 'Use LDAP to store user credentials.', 'Use HTTP digest authentication.', 'Use form-based authentication.', 4, 1, 'PRJ321'),
('PRJ321_Question_14', 'Which is NOT the main type of JSP constructs that you embed in a page?', 'directives', 'scripting elements', 'HTML code', 'actions', 4, 1, 'PRJ321'),
('PRJ321_Question_15', 'Servlet Container calls the init method on a servlet instance_', 'For each request to the servlet', 'For each request to the servlet that causes a new thread to be created', 'Only once in the life time of the servlet instance', 'If the request is from the user whose session has expired.', 3, 1, 'PRJ321'),
('PRJ321_Question_16', 'Which of the following is NOT a valid attribute for a useBean tag?', 'className', 'beanName', 'scope', '1', 1, 1, 'PRJ321'),
('PRJ321_Question_17', 'Which statement is true about EJB 3.0 containers?', 'The Java 2D API is guaranteed to be available for session beans', 'Java Telephony API is guaranteed to be available for session and message beans', 'javax.naming.initialContextis guaranteed to provide a JNDI name space', 'All', 3, 1, 'PRJ321'),
('PRJ321_Question_18', 'JDBC supports ______ and ______ models.', ' Three-tier and four-tier', 'Two-tier and three-tier', 'None of the other choices', 'Single-tier and two-tier', 2, 1, 'PRJ321'),
('PRJ321_Question_19', 'The ______ class is the primary class that has the driver information.', 'None of the other choices', 'Driver', 'DriverManager', 'ODBCDriver', 3, 1, 'PRJ321'),
('PRJ321_Question_20', ' Which HTTP method gets invoked when a user clicks on a link? Select the one correct answer.', 'GET method', 'POST method', 'HEAD method', 'PUT method', 1, 1, 'PRJ321'),
('PRJ321_Question_21', ' Which HTTP method gets invoked when a user clicks on a link? Select the one correct answer.', 'GET method', 'POST method', 'HEAD method', 'PUT method', 1, 1, 'PRJ321'),
('PRJ321_Question_22', ' Which HTTP method gets invoked when a user clicks on a link? Select the one correct answer.', 'GET method', 'POST method', 'HEAD method', 'PUT method', 1, 1, 'PRJ321'),
('PRJ321_Question_23', ' Which HTTP method gets invoked when a user clicks on a link? Select the one correct answer.', 'GET method', 'POST method', 'HEAD method', 'PUT method', 1, 1, 'PRJ321'),
('PRJ321_Question_24', ' Which HTTP method gets invoked when a user clicks on a link? Select the one correct answer.', 'GET method', 'POST method', 'HEAD method', 'PUT method', 1, 1, 'PRJ321'),
('PRJ321_Question_25', ' Which HTTP method gets invoked when a user clicks on a link? Select the one correct answer.', 'GET method', 'POST method', 'HEAD method', 'PUT method', 1, 1, 'PRJ321'),
('PRJ311_Question_1', 'Class HttpServlet defines the methods _____ and _______ to response to get and post request from a client.', 'DoGet, DoPost', 'doGet, doPost', 'doGET, doPOST', 'Get, Post', 2, 1, 'PRJ311'),
('PRJ311_Question_2', 'Classes HttpServlet and GenericServlet implement the ___ interface.', 'Servlet', 'Http', 'HttpServletRequest', 'HttpServletResponse', 1, 1, 'PRJ311'),
('PRJ311_Question_3', 'Name the default value of the scope attribute of <jsp:useBean>', 'page', 'session', 'application', 'request', 1, 1, 'PRJ311'),
('PRJ311_Question_4', 'Which jsp tag can be used to set bean property?', 'jsp:useBean', 'jsp:property', 'jsp:setProperty', 'jsp:useBean.setProperty', 4, 1, 'PRJ311'),
('PRJ311_Question_5', 'Which element defined within the taglib element of taglib descriptor file is required? Select one correct answer.', 'Tag', 'Description', 'Validator', 'Name', 1, 1, 'PRJ311'),
('PRJ311_Question_6', 'HttpServletRequest.getSession() method returns a_____object.', 'HttpServletSession.', 'HttpResponseSession.', 'HttpRequestSession.', 'HttpSession.', 4, 1, 'PRJ311'),
('PRJ311_Question_7', 'Which is NOT true about stateless session beans?', 'They are used to represent data stored in a RDBMS', 'They are used to implement business logic', 'They are an Enterprises Beans', 'They are CANNOT hold client state', 1, 1, 'PRJ311'),
('PRJ311_Question_8', 'Which Java technology provides a unified interface to multiple naming and directory services?', 'JNI', 'JDBC', 'JavaMail', 'JNDI', 4, 1, 'PRJ311'),
('PRJ311_Question_9', 'A(n)_enables a web application to obtain a Connection to a database.', 'DataSource', 'Netbean', 'Eclipse', 'Web server', 1, 1, 'PRJ311'),
('PRJ311_Question_10', 'Which is NOT provided by the EJB tier in a multitier JEE (J2EE) application?', 'XML Parsing', 'Concurrency control', 'Transaction management', 'Security', 4, 1, 'PRJ311'),
('PRJ311_Question_11', 'Which type of JEE (or J2EE) component is used to store business data persistently?', 'Stateful session beans', 'Java Server Pages', 'Stateles session beans', 'Entity Bean', 4, 1, 'PRJ311'),
('PRJ311_Question_12', 'Which is true about JDBC?', ' The JDBC API is an extension of the ODBC API', 'All JDBC drivers are pure Java.', 'JDBC is used to connect to MOM (Message-Oriented Middleware Product)', 'The JDBC API is included in J2SE', 4, 1, 'PRJ311'),
('PRJ311_Question_13', 'Data Integrity is the biggest issue for your web application. What will you do?', 'Use HTTPS instead of HTTP.', 'Use LDAP to store user credentials.', 'Use HTTP digest authentication.', 'Use form-based authentication.', 4, 1, 'PRJ311'),
('PRJ311_Question_14', 'Which is NOT the main type of JSP constructs that you embed in a page?', 'directives', 'scripting elements', 'HTML code', 'actions', 4, 1, 'PRJ311'),
('PRJ311_Question_15', 'Servlet Container calls the init method on a servlet instance_', 'For each request to the servlet', 'For each request to the servlet that causes a new thread to be created', 'Only once in the life time of the servlet instance', 'If the request is from the user whose session has expired.', 3, 1, 'PRJ311'),
('PRJ311_Question_16', 'Which of the following is NOT a valid attribute for a useBean tag?', 'className', 'beanName', 'scope', '1', 1, 1, 'PRJ311'),
('PRJ311_Question_17', 'Which statement is true about EJB 3.0 containers?', 'The Java 2D API is guaranteed to be available for session beans', 'Java Telephony API is guaranteed to be available for session and message beans', 'javax.naming.initialContextis guaranteed to provide a JNDI name space', 'All', 3, 1, 'PRJ311'),
('PRJ311_Question_18', 'JDBC supports ______ and ______ models.', ' Three-tier and four-tier', 'Two-tier and three-tier', 'None of the other choices', 'Single-tier and two-tier', 2, 1, 'PRJ311'),
('PRJ311_Question_19', 'The ______ class is the primary class that has the driver information.', 'None of the other choices', 'Driver', 'DriverManager', 'ODBCDriver', 3, 1, 'PRJ311'),
('PRJ311_Question_20', ' Which HTTP method gets invoked when a user clicks on a link? Select the one correct answer.', 'GET method', 'POST method', 'HEAD method', 'PUT method', 1, 1, 'PRJ311'),
('PRJ311_Question_21', ' Which HTTP method gets invoked when a user clicks on a link? Select the one correct answer.', 'GET method', 'POST method', 'HEAD method', 'PUT method', 1, 1, 'PRJ311'),
('PRJ311_Question_22', ' Which HTTP method gets invoked when a user clicks on a link? Select the one correct answer.', 'GET method', 'POST method', 'HEAD method', 'PUT method', 1, 1, 'PRJ311'),
('PRJ311_Question_23', ' Which HTTP method gets invoked when a user clicks on a link? Select the one correct answer.', 'GET method', 'POST method', 'HEAD method', 'PUT method', 1, 1, 'PRJ311'),
('PRJ311_Question_24', ' Which HTTP method gets invoked when a user clicks on a link? Select the one correct answer.', 'GET method', 'POST method', 'HEAD method', 'PUT method', 1, 1, 'PRJ311'),
('PRJ311_Question_25', ' Which HTTP method gets invoked when a user clicks on a link? Select the one correct answer.', 'GET method', 'POST method', 'HEAD method', 'PUT method', 1, 1, 'PRJ311'),
('PRN292_Question_1', 'Class HttpServlet defines the methods _____ and _______ to response to get and post request from a client.', 'DoGet, DoPost', 'doGet, doPost', 'doGET, doPOST', 'Get, Post', 2, 1, 'PRN292'),
('PRN292_Question_2', 'Classes HttpServlet and GenericServlet implement the ___ interface.', 'Servlet', 'Http', 'HttpServletRequest', 'HttpServletResponse', 1, 1, 'PRN292'),
('PRN292_Question_3', 'Name the default value of the scope attribute of <jsp:useBean>', 'page', 'session', 'application', 'request', 1, 1, 'PRN292'),
('PRN292_Question_4', 'Which jsp tag can be used to set bean property?', 'jsp:useBean', 'jsp:property', 'jsp:setProperty', 'jsp:useBean.setProperty', 4, 1, 'PRN292'),
('PRN292_Question_5', 'Which element defined within the taglib element of taglib descriptor file is required? Select one correct answer.', 'Tag', 'Description', 'Validator', 'Name', 1, 1, 'PRN292'),
('PRN292_Question_6', 'HttpServletRequest.getSession() method returns a_____object.', 'HttpServletSession.', 'HttpResponseSession.', 'HttpRequestSession.', 'HttpSession.', 4, 1, 'PRN292'),
('PRN292_Question_7', 'Which is NOT true about stateless session beans?', 'They are used to represent data stored in a RDBMS', 'They are used to implement business logic', 'They are an Enterprises Beans', 'They are CANNOT hold client state', 1, 1, 'PRN292'),
('PRN292_Question_8', 'Which Java technology provides a unified interface to multiple naming and directory services?', 'JNI', 'JDBC', 'JavaMail', 'JNDI', 4, 1, 'PRN292'),
('PRN292_Question_9', 'A(n)_enables a web application to obtain a Connection to a database.', 'DataSource', 'Netbean', 'Eclipse', 'Web server', 1, 1, 'PRN292'),
('PRN292_Question_10', 'Which is NOT provided by the EJB tier in a multitier JEE (J2EE) application?', 'XML Parsing', 'Concurrency control', 'Transaction management', 'Security', 4, 1, 'PRN292'),
('PRN292_Question_11', 'Which type of JEE (or J2EE) component is used to store business data persistently?', 'Stateful session beans', 'Java Server Pages', 'Stateles session beans', 'Entity Bean', 4, 1, 'PRN292'),
('PRN292_Question_12', 'Which is true about JDBC?', ' The JDBC API is an extension of the ODBC API', 'All JDBC drivers are pure Java.', 'JDBC is used to connect to MOM (Message-Oriented Middleware Product)', 'The JDBC API is included in J2SE', 4, 1, 'PRN292'),
('PRN292_Question_13', 'Data Integrity is the biggest issue for your web application. What will you do?', 'Use HTTPS instead of HTTP.', 'Use LDAP to store user credentials.', 'Use HTTP digest authentication.', 'Use form-based authentication.', 4, 1, 'PRN292'),
('PRN292_Question_14', 'Which is NOT the main type of JSP constructs that you embed in a page?', 'directives', 'scripting elements', 'HTML code', 'actions', 4, 1, 'PRN292'),
('PRN292_Question_15', 'Servlet Container calls the init method on a servlet instance_', 'For each request to the servlet', 'For each request to the servlet that causes a new thread to be created', 'Only once in the life time of the servlet instance', 'If the request is from the user whose session has expired.', 3, 1, 'PRN292'),
('PRN292_Question_16', 'Which of the following is NOT a valid attribute for a useBean tag?', 'className', 'beanName', 'scope', '1', 1, 1, 'PRN292'),
('PRN292_Question_17', 'Which statement is true about EJB 3.0 containers?', 'The Java 2D API is guaranteed to be available for session beans', 'Java Telephony API is guaranteed to be available for session and message beans', 'javax.naming.initialContextis guaranteed to provide a JNDI name space', 'All', 3, 1, 'PRN292'),
('PRN292_Question_18', 'JDBC supports ______ and ______ models.', ' Three-tier and four-tier', 'Two-tier and three-tier', 'None of the other choices', 'Single-tier and two-tier', 2, 1, 'PRN292'),
('PRN292_Question_19', 'The ______ class is the primary class that has the driver information.', 'None of the other choices', 'Driver', 'DriverManager', 'ODBCDriver', 3, 1, 'PRN292'),
('PRN292_Question_20', ' Which HTTP method gets invoked when a user clicks on a link? Select the one correct answer.', 'GET method', 'POST method', 'HEAD method', 'PUT method', 1, 1, 'PRN292'),
('PRN292_Question_21', ' Which HTTP method gets invoked when a user clicks on a link? Select the one correct answer.', 'GET method', 'POST method', 'HEAD method', 'PUT method', 1, 1, 'PRN292'),
('PRN292_Question_22', ' Which HTTP method gets invoked when a user clicks on a link? Select the one correct answer.', 'GET method', 'POST method', 'HEAD method', 'PUT method', 1, 1, 'PRN292'),
('PRN292_Question_23', ' Which HTTP method gets invoked when a user clicks on a link? Select the one correct answer.', 'GET method', 'POST method', 'HEAD method', 'PUT method', 1, 1, 'PRN292'),
('PRN292_Question_24', ' Which HTTP method gets invoked when a user clicks on a link? Select the one correct answer.', 'GET method', 'POST method', 'HEAD method', 'PUT method', 1, 1, 'PRN292'),
('PRN292_Question_25', ' Which HTTP method gets invoked when a user clicks on a link? Select the one correct answer.', 'GET method', 'POST method', 'HEAD method', 'PUT method', 1, 1, 'PRN292')
