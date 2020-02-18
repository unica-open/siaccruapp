# Configurations
All configuration MUST be set in the `buildfiles/<env>.properties` file used for compilation
- current.env = the currently executing environment
- nome.ambiente = the name of the environment
- datasource.jndi-url = no more used. May be left to blank or to a default value
- messageSources.cacheSeconds = no more used. May be left to -1
- flag-debug = the Java compiler flag to activate debug symbols (on/off)
- flag-optimize = the Java compiler flag to activate compile-time optimizations (on/off)
- flag-compress = the Java packager flag to activate packaging compressions for
    WAR/EAR/JAR files (on/off)
- context-path = The context path of the EAR file
- remincl.resource.provider = URL to the remote resources
- sso.filter.name = Name for the SSO filter
- sso.filter.url.pattern = URL pattern for the SSO filter
    (specify a non-existing extension to prevent SSO checks)
- sso.loginHandler = fully-qualified class name for the SSO handler
- sso.logout.url = The SSO logout
- endpoint.url.service.core = Endpoint for the COR backend service
- opasincService.endpoint = Endpoint for asynchronous operations
- jspath = the path for the local JavaScript files (for proxying support)
- jspathexternal = the path for the external JavaScript files (for proxying support)
