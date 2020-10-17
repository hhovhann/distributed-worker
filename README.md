# distributed-worker
MVP(POC) for distributed workers project

### Technology stack
* Java 11
* Spring Boot 2.3.4.RELEASE

### Start application
Run from shell/terminal please run ```./scripts/start-application.sh```

### Start Tests
mvn clean test
Run from shell/terminal please run ```./scripts/start-test.sh```


### Application workflow
After running application through the run.sh the business logic will be following

* By default ```worker.in.memory.data.enabled == true```, means active datasource is h2 in memory database and seed data will happen in initialization runtime, if we need to use other sources should disable configuration and define proper data source configurations in ```application.properties```.
* Get next available job
* Call the URL for the job
* Store the returned status

### System Design and Patterns
For system design used classical patterns from GOF as well as Spring built in patterns

* Spring built in design patterns
* Template method pattern - Job Template
* Dependency Injection - all services with implementations

### Nice to have in future versions

* More Tests: Good to have unit, integration, automation, performance tests coverages near to 100%
* Using Integrations: Can be integrated with frameworks like quartz, spring batch with keeping in mind that we will have additional wrapping with all heavy stuff :)
* Increase performance: findAll functionality as well as similar functionalities can be cacheable.

Thanks for your attention
