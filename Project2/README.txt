DB Name - EAIProj2

User DB
User - DBuser
Pass - DBpass

User JBoss
User - JBOSSuser
Pass - JBOSSpass

----------------------------------------------
Persistence/JBoss configuration: Use with EJBs
----------------------------------------------
Persistence.xml:
  - You need a JTA data source that matches the data source in the JBoss configuration file (standalone*.xml). Example: "java:/mydb"
  - Persistence unit transaction type must be "JTA"
  - You only need "hibernate.dialect" and "hibernate.hbm2ddl.auto" properties. 
    "javax.persistence.*" don't seem to be necessary if you have the JBoss configured properly
	
standalone*.xml:
  - Data source with jndi-name="java:/mydb" and jta="true"
  - Specify <connection-url> to point to your database. Example:
    <connection-url>jdbc:mysql://localhost:3306/EAIProj2</connection-url>
  - Set security user and pass. Example:
	<security>
		<user-name>DBuser</user-name>
		<password>DBpass</password>
	</security>

Final datasource below

	<datasource jta="true" jndi-name="java:/mydb" pool-name="my_pool" enabled="true" use-java-context="true" use-ccm="true">
		<connection-url>jdbc:mysql://localhost:3306/EAIProj2</connection-url>
		<driver>mysql</driver>
		<security>
			<user-name>DBuser</user-name>
			<password>DBpass</password>
		</security>
		<statement>
			<prepared-statement-cache-size>100</prepared-statement-cache-size>
			<share-prepared-statements>true</share-prepared-statements>
		</statement>
	</datasource>




