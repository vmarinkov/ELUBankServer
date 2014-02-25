ELUBank
=======
	Преди да можете да компилирате проекта трябва да си запишете JDBC driver:
	Download - http://dev.mysql.com/downloads/connector/j/
	Слад като го свалите и инсталирате, трябва да го добавите в проеката:
	Projects->Libraries->Add Library... и там търсите MySQL JDBC Driver
	
	или просто да си го свалите през netbeans->team->git->clone

	След това е хубаво да си запишете XAMPP и да си импортнете .sql DB файла качен в проекта.
	(За да не се налага да си правите цялата база наново)
	
	
	TASKS(current):
	
	!!! http://en.wikipedia.org/wiki/Log4j
	
           IMPORTANT: Относно документацията - пробвах да генерирам javadoc от NetBeans и имаше следните грешки:
         - оказва се, че когато ползваме default package всички *.java файлове в главната source директория на проекта
           не влизат в документацията и generate javadoc дава грешка. Вижте последния commit в клиент частта как да се оправи.
           https://github.com/Lyubomira/ELUBankClient/commit/4e588334617c0158324c48850a587fdb397efeb5
         - не слагайте тирета при описанието на параметрите на методите например @param accList - еди какво си. javadoc генератора си ги
           слага сам и иначе става двойно тире.
         - В User,Accounts и тн. класовете е сложен коментар на вътрешно поле с @param, което Javadoc  не го приема. Вижте как съм променила
           User класа, така трябва да се промени. Аз ще подменя и другите класове при нас, после може copy/paste.

	✔ [done] да направим parseXML() да се изпълнява на даден период от време докато сървъра работи, /васко/
	✔ [done] да направим и евентуалните лихви да се добавят коректно. /васко/
	✔ [done] Да направим пълна функционалност на TransactionsMgmt и AccountsMgmt /васко/
	✔ [done] Евентуално да разширим функционалността на UserMgmt да може, да запълва Transactions[] и Accounts[] 		масиви за дадения юзър /васко/

	- Сървърен админ GUI с възможност за следене на server log. /или да го пуснем под линукс (миглен)/ MySql 		credentials /
	- Някакъв начин за предаване на IP/PORT в клиентското приложение (.ini файл или с параметри или нз.) /Миглен/
	✔ [done] jar-овете да превърнем в EXE-та за двата проета /миглен/

	- [някой / евентуално] всеки път когато се изпълнява заявка от клиента -> към сървъра (SSLClient()) да се 		появява някакъв	message box, който да казва: че в момента заявката се обработва; статуса на заявката;
	ако може и някаква анимацийка (пясъчен часовник .gif) да се върти 

	✔ [Момичетата] да си кръстят всички елементи във формите(Лейбълите също) ВСИЧКИ! пр: btnLogin, lblUsername, 		txtUsername, tblUserAccounts...
	✔ [Момичетата] да се донаправят формите и да си махнат ненужно генериранети методи (има такива)
	✔ [Момичетата] Формите да се направят изцяло на Български (лейбъли, съобщения, имена на бутони, чек-бутони, 		падащи менюта ако е необходимо). Оправихме проблема с бългърския (Мерси на Бубето).
	- [Момичетата] Javadoc Коментари няма никакви. Това ще е важно после за документацията.
	(Пишете "/**" + enter преди даден метод/клас/променлива и Netbeans автоматично ще ви генерира javadoc коментар, 	след което допълвате информация)
        Само да кажа за коментарите - Javadoc коментари (които започват с /**) се пишат само за методи, класове или 		променливи на класа.
        Другите вътрешни коментари трябва да започват с "//" или да са от типа /* Коментар */ за многоредов коментар.
	->>>> Към всички, тъй като накрая ще имаме малко време и за документация. Предлагам всички да се включим.

	- [!!!] Накрая да обработим някой от изключителните случаи (exceptions)

        - 07.02.2014 (Ели) - Направила съм Функционалността за смяна на парола и обновяване на потребителския профил. 		Добавих няколко неща в сървърната част,
        за да може горните неща да работят. Добавих два нови метода в UserMgmt класа (updateUser() и updatePass()). 		Модифицирах и двата метода getUserByEgn() и
        getUserByUsername() да връщат и username-a като информация, за да мога да го ползвам в клиентската част при 		проверката за валидна стара парола. 
        Направих го да полва "login" заявката - все едно преди смяната на паролата още веднъж пускам "login" и ако ми 		върне getLoggedIn() == true значи 
        въведената стара парола е валидна. Модифицирах и SSLServerThread да обработва новите заявки "update" и "updatePa		ss".
        Смених малко и в LoginFrame - там методът, който беше за взимане на паролата от password полето при мен пищеше, 	че е "deprecated". Прочетох в документацията,
        че вече трябвало да се ползва друг метод - getPassword() и съм го модифицирала, за да не изкарва предупреждениет		о като се run-ва. Повече информация ето тук:
        http://docs.oracle.com/javase/7/docs/api/javax/swing/JPasswordField.html#getText%28%29
        
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
	
