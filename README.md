# PerfectoIvySample

This sample project is designed to get you up and running within few simple steps.

Begin with installing the dependencies below, and continue with the Getting Started procedure below.

### Dependencies
There are several prerequisite dependencies you should install on your machine prior to starting to work with this project:

* [Java 8](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)

* An IDE to write your tests on - [Eclipse](http://www.eclipse.org/downloads/packages/eclipse-ide-java-developers/marsr) or [IntelliJ](https://www.jetbrains.com/idea/download/#)

* [Ant](https://ant.apache.org/)
 
#### Optional Installations
* For source control management, you can install [git](https://git-scm.com/downloads).
* To be able to interact with a real device from Perfecto cloud directly from your IDE, and use Perfecto Reporting, install [Perfecto CQ Lab Plugin](https://www.perfectomobile.com/ni/resources/downloads/add-ins-plugins-and-extensions) for your IDE.

## Downloading the Sample Project

* [Clone](https://github.com/PerfectoMobileSA/PerfectoIvySample.git) the repository.

* After downloading and unzipping the project to your computer, open it from your IDE by choosing the folder 

**********************
# Getting Started

## Running sample as is


* Open PerfectoAppiumTest.java and PerfectoSeleniumTest.java</p>

* Search for the below line and replace `<<cloud name>>` with your perfecto cloud name (e.g. demo) or pass it as maven properties: `-DcloudName=<<cloud name>>`</br>  
		&nbsp;&nbsp;	&nbsp;&nbsp; String cloudName = `"<<cloud name>>"`;
		</br>
		</p>
* Search for the below line and replace `<<SECURITY TOKEN>>` with your perfecto [security token](https://developers.perfectomobile.com/display/PD/Generate+security+tokens) or pass it as maven properties: `-DsecurityToken=<<SECURITY TOKEN>>` </br></p>
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; String securityToken = `"<<SECURITY TOKEN>>"`;
	</br>

Note: Refer to official documentation on how to execute from eclipse / IntelliJ. </br>
* Run build.xml with the below ant targets & properties when: </p>
   a. If credentials are hardcoded:
		
		testng-execution
		
   &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;b. If credentials are passed as parameters:
		
		testng-execution
		-DcloudName=${cloudName}
		-DsecurityToken=${securityToken}
		-DtestNGXmlFile=testng_Perfecto.xml
</p>

* Ant will automatically kick start the parallel execution of different examples inside perfecto package in parallel if `-DtestngXmlFile=testng.xml` is passed as ant properties, if you want to run only perfecto scripts just pass this: `-DtestngXmlFile=testng_Perfecto.xml` as ant properties. (this is the default behaviour) </p>

* CI dashboard integration can be performed by supplying the below properties to top-level Ant Targets or Ivy Module Configuration:

		testng-execution
		-DcloudName=${cloudName}
		-DtestNGXmlFile=testng_Perfecto.xml
		-DsecurityToken=${securityToken}
		-Dreportium-job-name=${JOB_NAME} 
		-Dreportium-job-number=${BUILD_NUMBER} 
		-Dreportium-job-branch=${GIT_BRANCH} 
		-Dreportium-tags=${myTag}
