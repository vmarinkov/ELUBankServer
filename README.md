ELUBank
=======
	Преди да можете да компилирате проекта трябва да си запишете JDBC driver:
	Download - http://dev.mysql.com/downloads/connector/j/
	Слад като го свалите и инсталирате, трябва да го добавите в проеката:
	Projects->Libraries->Add Library... и там търсите MySQL JDBC Driver

	След това е хубаво да си запишете XAMPP и да си импортнете .sql DB файла качен в проекта.
	(За да не се налага да си правите цялата база наново)


Bank ELU - CSCB Project


	dbman class -> 
	dbConnect() 	- свързва се към db
	dbQuery()  		- SELECT
	dbExecute() 	- INSERT, UPDATE && DELETE
	dbDisconnect()	- затваря конекцията към db

	userman class ->
	loginUser() - логване в системата, да се провери дали паролата и потребителя са ок, или дали е активен?
	requestUser() - заявка / регистрация на нов потребител
	editUser() - редакция на потребител
	getTransactionLog() - връща масив с информация за транзакциите
	requestUserDel() - заявка за закриване на потребителски акаунт + неговите сметки.
	
	adminman class ->
	approveUser() - одобрява потребителски рекуест за нов потребител.
	removeUser()  - премахване на съществиващ потребител.
	листва съдържанието на потребителите и техните сметки със наличности

	bnbparser class ->
	downloadXml() - изтегля последната валутна информация от сайта на БНБ
	parseXML() - парсва информацията от xml файла и го обновява в DB.

--

	Две приложения - клиент и сървър.

	Клиентското приложение може:
	Да се логваш
	Да рикуестваш акаунт

	След като се логнеш:
	Да проверш наличните си суми и сметки.
	Да си откриеш сметка
	Теглене / внасяне
	Да провериш транзакциите по сметките
	Да си редактираш профила

	
	
	TASKS:
	
	BNBPARSER: Да направим обновяването на валутата в базата от данни - bnbparser class
	 COMMENTS: Javadoc ++ comments
		DB: Да се оптимизира конекцията до дб, да е отворена докато се работи и да се затваря чак накрая при
		спирането на сървъра.
		Да се направят заявки с използване на Prepared Statements
		(http://docs.oracle.com/javase/tutorial/jdbc/basics/prepared.html)
		ADMIN: В потребителския интерфейс да има списък на зявките.
	Client/Server communication: Да видим как работи SOAP и да го имплементираме в клеинт-сървър мултитред
				     архитектурата.
				     Да проучим REST API, като евентуален заместител на SOAP 
				     (http://docs.oracle.com/javaee/6/tutorial/doc/gipzz.html)
	                             Ако и 2те ни се сложат прекалоно трудоемки за реализация - ползваме 
	                             сегашният прост чат многонишков клиент/сървър.
	Евентуално .ini файл в който да се съхранят.
	
	Usefull links:
	JDBC and MySQL tutorial - http://zetcode.com/db/mysqljava/
	Prepared Statements - http://docs.oracle.com/javase/tutorial/jdbc/basics/prepared.html
	Store Procedures - http://docs.oracle.com/javase/tutorial/jdbc/basics/storedprocedures.html
