# Spring MVC Archetype

# Use

`mvn archetype:generate -DarchetypeGroupId=com.uprr.ui -DarchetypeArtifactId=spring-mvc-archetype -DarchetypeCatalog=http://cmp-prod01.mvn.tla.uprr.com:10011/maven/content/repositories/snapshots/archetype-catalog.xml`

*  Validation example.  @Valid on post is not working.  Exception being swallowed.
*  index.html -> index.jsp:  Add links to requests that are being mapped in TrainController.
*  Tests need to be written - simple/mockito style