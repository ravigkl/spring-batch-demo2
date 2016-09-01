Spring Batch & Quartz Scheduler Example (Tasklet usage)

In this tutorial we will learn about how to integrate Spring Batch with Quartz Scheduler to run batch job periodically. We will also see how to handle real world scenario where periodically, files are received dynamically (they may have different names on each run) in some input folder which can reside on file system (not necessarily the application classpath). We will see JobParameter usage to handle this scenario. We will also witness usage of Tasklet to archive file once they are processed. We will read (one or more) flat files (on each run) using FlatFileItemReader and write(append) all the records to a common output flat file using FlatFileItemWriter.
Step 1: Create project directory structure
We will be reading the dynamic flat files from directory on file system (C:/Test/Server/InputFiles) and writing/appending the result of each run to output flat file (project/csv/examResultOutput.txt)
Step 2: Update pom.xml to include required dependencies
Step 3: Prepare the input flat files and corresponding domain object / mapped POJO
Step 4: Create a FieldSetMapper
FieldSetMapper is responsible for mapping each field form the input to a domain object
Step 5: Create an ItemProcessor
ItemProcessor is Optional, and called after item read but before item write. It gives us the opportunity to perform a business logic on each item. In our case, for example, we will filter out all the records whose percentage is less than 75. So final result will only have records with percentage >= 75.
Step 6: Create actual spring batch job
	The actual class that perform the business logic to schedule the job if available
Step 7: Add a Tasklet to archive file once processed
Tasklet execute method is called once the STEP above it (in job) is completed successfully. In our case, once we have processed our input file, the file will be archived (moved to a different folder C:/Test/Server/ArchivedFiles with timestamp suffixed). Notice the usage of jobParameters to get the filename of file we processed in previous STEP.
Step 8: Add Quartz related classes
Quartz scheduler calls (on each scheduled run) executeInternal method of class implementing QuartzJobBean. In this method, we are simply calling performJob of our Spring batch job class we created in step6. But we need some way to tell Scheduler about how to find our spring batch job bean which will be declared as a bean in spring context. To do that, we have used an implementation of ApplicationContextAware.

And finally, we also need to configure Quartz CRON trigger to specify when and with which periodicity the job should run.

Step 9: Add Spring application context for Quartz and Spring Batch
Most interesting peace of configuration in batch-context file is: 
<bean id="inputExamResultJobFile" class="org.springframework.core.io.FileSystemResource" scope="step">
    <constructor-arg value="#{jobParameters[examResultInputFile]}"/>
</bean>
which uses jobParameters along with Spring expression language to refer to the file resource whose name was set (as jobParameter) in our batch job in step6. Note that you don’t find such flexibility with regular FlatFileItemReader or MultiResourceItemReader configuration (using file: or classpath: to refer to actual resource) which once referred to a resource cannot recognize a new resource on next run.
Rest of configuration is pretty obvious. We have declared a FlatFileItemReader (whose resource property now refers to the bean ‘inputExamResultJobFile’ described above) to read input flat files, FlatFileItemWriter to write the records to a specific file.We have also added a Tasklet in our job which we use to archive the file once processed.

In quartz-context.xml, we have declared a SchedulerFactoryBean which schedules the configured jobs (via jobDetails property) for mentioned time/peiodicity (using triggers property). In this example, job will be run each minute. Rest is the declaration of beans we have created above. Please visit Quartz Documentation to learn more about Quartz scheduler usage.