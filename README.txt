                  Test Webapp using Modular GWT & Spring/JPA
                  ==========================================

Purpose
-------

This TestApp is an exploration of several techniques I have seen on the web
combined together to form the foundation of future GWT/Spring/JPA webapps.


Goals
------

  * Use Maven modules to separate distinct chunks:
    -- See: https://github.com/tbroyer/gwt-maven-archetypes
    The app is organized into three maven modules:
    -- TestApp-shared: Service interfaces and Domain objects
    -- TestApp-client: (GWT UI) code
    -- TestApp-server: backend Service implementation and webapp/GWT files
  * Use Spring services for the RPC implementations
    -- See: http://www.javacodegeeks.com/2010/05/gwt-2-spring-3-jpa-2-hibernate-35-2.html
  * Use JPA Entities as objects passed across RPC boundary
  * Add logging configuration
  * Modernize Spring and JPA DAO (also remove unused methods)
TODO:
  * Add lazy loaded JPA relationship (eg, Department -> Employee)
    -- This will inform the need for the next step (JPACloner)
  * Use JpaCloner to prevent GWT serialization problems of lazy loading
    -- See: https://github.com/nociar/jpa-cloner
  * Use MVP separation of concerns
  * Use GWT Places & Activities
  * Use ServerResponse to capture Server-side problems and exceptions
    -- Use AOP to wrap methods that return an SR to capture unexpected exceptions
  * Others???


GWT Dev Mode
------------

DevMode is being phased out.  It requires a browser plugin that frequently
is not kept up-to-date.

1) Compile the system
mvn clean install -Dgwt.compiler.skip

2) Run the server in Tomcat
mvn tomcat7:run -Ddev

3) Run the client (in a separate terminal)
mvn gwt:run -Ddev


GWT Super Dev Mode
------------------

SuperDevMode is the new kid on the block.  It does not require a browser
plugin so --theoretically-- can be run in any browser.

1) Compile the system
mvn clean install -Dgwt.draftCompile

2) Run the server in Tomcat
mvn tomcat7:run -Ddev

3) Run the client (in a separate terminal)
mvn gwt:run-codeserver -Ddev

4) Launch webapp in *any* browser: http://localhost:8080/

5) Click on "Dev Mode On" bookmark (not sure why this is necessary)


To Build Production WAR
-----------------------

1) mvn clean package

WAR file is generated in the TestApp-server/target/ directory.
