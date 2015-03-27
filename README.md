#EasyCukes
[![Build Status](https://travis-ci.org/aneveux/easycukes.svg)](https://travis-ci.org/aneveux/easycukes)

##About
EasyCukes is just a framework aiming at making Cucumber even easier than what it already is.

The global idea behind this framework is to provide some generic code you could use in order to test software thanks to [Cucumber](http://cukes.info/). If you don't know about this framework and would like to start using [BDD](https://en.wikipedia.org/wiki/Behavior-driven_development), or simply make some integration/acceptance tests in your project, you should probably have a look at [Cucumber documentation](https://github.com/cucumber/cucumber/wiki).

This framework will simply give you some help while dealing with stuff like REST APIs, web GUIs, etc. It relies on standard libraries and tools such as [Selenium](http://docs.seleniumhq.org/), for example, and simply allows to ease their usage.

##Documentation
All of our documentation can be found on [our wiki](https://github.com/awltech/easycukes/wiki):

- [Building EasyCukes](https://github.com/awltech/easycukes/wiki/Building-EasyCukes)
- EasyCukes-Commons:
	- [Available steps](https://github.com/awltech/easycukes/wiki/EasyCukes-Commons-available-steps)
	- [Generating data for tests](https://github.com/awltech/easycukes/wiki/Generating-data-for-tests)
	- [Sharing data within tests](https://github.com/awltech/easycukes/wiki/Sharing-data-within-tests)
	- [Configuration files](https://github.com/awltech/easycukes/wiki/EasyCukes-configuration-files)
- EasyCukes-REST:
	- [Available steps](https://github.com/awltech/easycukes/wiki/EasyCukes-REST-available-steps)
	- [Project creation](https://github.com/awltech/easycukes/wiki/EasyCukes-REST-project-creation)
- EasyCukes-SCM:
	- [Available steps](https://github.com/awltech/easycukes/wiki/EasyCukes-SCM-available-steps)
- EasyCukes-Selenium:
	- [Available steps](https://github.com/awltech/easycukes/wiki/EasyCukes-Selenium-available-steps)
	- [Project creation](https://github.com/awltech/easycukes/wiki/EasyCukes-Selenium-project-creation)
- EasyCukes-DBUnit:
	- [DBUnit integration](https://github.com/awltech/easycukes/wiki/EasyCukes-&-DbUnit-integration)
- Advanced:
	- [Extensibility](https://github.com/awltech/easycukes/wiki/Extensibility-of-EasyCukes-framework)
	- [Tooling](https://github.com/awltech/easycukes/wiki/Tooling-for-developing-with-EasyCukes)
	- [Arquillian](https://github.com/awltech/easycukes/wiki/Arquillian-integration)
- DEV:
	- [Deploying to m2 repo](https://github.com/awltech/easycukes/wiki/%5BDEV%5D-Deploying-EasyCukes-to-github-m2-repo)

##Usage
EasyCukes is delivered on its own Maven repository that you can add in your pom.xml:

```xml
    <repository>
        <id>easycukes-mvn-repo</id>
        <url>https://raw.github.com/awltech/easycukes/mvn-repo/</url>
        <snapshots>
            <enabled>true</enabled>
            <updatePolicy>always</updatePolicy>
        </snapshots>
    </repository>
```

##Contribution
All [feedbacks](https://github.com/awltech/easycukes/issues) are welcome.

If you'd like to contribute, don't hesitate to [fork](https://github.com/awltech/easycukes/fork) & pullrequest ;)

##Thanks
We'd really like to thank all of those projects for the inspiration they gave us:
- http://cukes.info/
- https://github.com/kowalcj0/cucumber-testng-parallel-selenium
- https://github.com/sameer49/selenium-cucumber-ruby
- https://github.com/grantcurrey/cucumber-rest 
- https://github.com/cukespace/cukespace
