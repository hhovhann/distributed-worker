# distributed-worker
MVP(POC) for distributed workers project

## Technology stack
* Java 11
* Spring Boot 2.3.4.RELEASE

### Run application
Run from shell/terminal please run ```./scripts/run.sh```

### Run Tests
mvn clean test

### Application workflow
After running application through the run.sh the business logic will be following

* 1. By default ```worker.in.memory.data.enabled == true```, means active datasource is h2 in memory database and seed data will happen in initialization runtime, if we need to use other sources should disable configuration and define proper data source configurations in ```application.properties```.
* 2. Get next available job
* 3. Call the URL for the job
* 4. Store the returned status

# Nice to have in future versions
* 1. More Tests: Good to have unit, integration, automation, performance tests coverages near to 100%
* 2. Using Integrations: Can be integrated with frameworks like quartz, spring batch with keeping in mind that we will have additional wrapping with all heavy stuff :)
* 3. Increase performance: findAll functionality as well as similar functionalities can be cacheable.

Thanks for your attention
