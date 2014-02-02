
ELUBank
=======
	Преди да можете да компилирате проекта трябва да си запишете JDBC driver:
	Download - http://dev.mysql.com/downloads/connector/j/
	Слад като го свалите и инсталирате, трябва да го добавите в проеката:
	Projects->Libraries->Add Library... и там търсите MySQL JDBC Driver

	След това е хубаво да си запишете XAMPP и да си импортнете .sql DB файла качен в проекта.
	(За да не се налага да си правите цялата база наново)
	
	
	TASKS(current):
	
	
	След 2 дена яко писане на код... имам чувството, че нищо не съм написал като гледам какво още остава,
	а именно:

	- да направим parseXML() да се изпълнява на даден период от време докато сървъра работи,
	- да направим и евентуалните лихви да се добавят коректно.
	- Сървърен админ GUI с възможност за следене на server log.
	- Някакъв начин за предаване на IP/PORT в клиентското приложение (.ini файл или с параметри или нз.)
	- jar-овете да превърнем в EXE-та за двата проета
	- Да направим пълна функционалност на TransactionsMgmt и AccountsMgmt
	- Евентуално да разширим функционалността на UserMgmt да може, да запълва Transactions[] и Accounts[] масиви за 	дадения юзър

	 [Момичетата] да си кръстят всички елементи във формите(Лейбълите също) ВСИЧКИ! пр: btnLogin, lblUsername, 		txtUsername, tblUserAccounts...
	това е наистина важно, няколко пъти го споменавам и все не искате да го направите. Той също ще го гледа.
	Важно е защото после като/ако се работи с тия обекти е малко тъпо да се търси "JButton16" - какво прави?
	- [Момичетата] да се донаправят формите и да си махнат ненужно генериранети методи (има такива)
	- [Момичетата] Формите да се направят изцяло на Български (лейбъли, съобщения, имена на бутони, чек-бутони,
	 падащи менюта ако е необходимо). Оправихме проблема с бългърския (Мерси на Бубето).
	- [Момичетата] Javadoc Коментари няма никакви. Това ще е важно после за документацията.
	(Пишете "/**" + enter преди даден метод/клас/променлива и Netbeans автоматично ще ви генерира javadoc коментар,
	 след което допълвате информация)

	->>>> Към всички, тъй като накрая ще имаме малко време и за документация. Предлагам всички да се включим.

	- [!!!] Накрая да обработим някой от изключителните случаи (exceptions)

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

	
	TASKS(old):
	
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
	
