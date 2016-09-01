/**
 * 
 */
package com.rs.spring.scheduler;

import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.rs.spring.batchjob.SpringBatchJob;
import com.rs.spring.util.ApplicationContextUtil;

/**
 * @author IBM_ADMIN
 * @date Sep 1, 2016
 */
public class SchedulerJob extends QuartzJobBean {
	
	private static final Logger log = Logger.getLogger(SchedulerJob.class);

	private String batchJob;

	public void setBatchJob(String batchJob) {
		this.batchJob = batchJob;
	}

	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		ApplicationContext applicationContext = ApplicationContextUtil.getApplicationContext();
        SpringBatchJob job = applicationContext.getBean(batchJob, SpringBatchJob.class);
        log.info("Quartz job started: "+ job);
         
        try{
            job.performJob();
             
        }catch(Exception exception){
            log.info("Job "+ batchJob+" could not be executed : "+ exception.getMessage());
        }
        log.info("Quartz job end");
	}
}
