/**
 * 
 */
package com.rs.spring.batchjob;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionException;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.configuration.JobLocator;
import org.springframework.batch.core.launch.JobLauncher;

/**
 * @author IBM_ADMIN
 * @date Sep 1, 2016
 * 
 *       This class contains actual spring batch job logic. Method
 *       <b>performJob</b> will be called by scheduler periodically. On each
 *       run, we check a specific input directory where input files can be
 *       found. If files are present, then we create a map containing the actual
 *       file name plus a date (to make it unique among job parameters), and
 *       pass this map as an input to JobParameters followed by launching the
 *       job using JobLauncher. This map we will be referring to in spring
 *       context to create a reference to actual file resource.
 */
public class SpringBatchJob {

	private static final Logger log = Logger.getLogger(SpringBatchJob.class);

	private String jobName;

	private JobLocator jobLocator;

	private JobLauncher jobLauncher;

	private File contentDirectory;

	private String directoryPath = "C:/Test/Server/InputFiles";

	boolean fileFound = false;

	public void init() {
		// init the directory path for the files to be processed
		contentDirectory = new File(directoryPath);
	}

	public void performJob() {

		log.info("Starting ExamResult Job");

		try {

			if (contentDirectory == null || !contentDirectory.isDirectory()) {
				log.error("Input directory doesn't exist. Job ExamResult terminated");
			}

			fileFound = false;

			for (File file : contentDirectory.listFiles()) {
				if (file.isFile()) {
					System.out.println("File found :" + file.getAbsolutePath());
					fileFound = true;

					JobParameter param = new JobParameter(file.getAbsolutePath());
					Map<String, JobParameter> map = new HashMap<String, JobParameter>();
					map.put("examResultInputFile", param);
					map.put("date", new JobParameter(new Date()));

					JobExecution result = jobLauncher.run(jobLocator.getJob(jobName), new JobParameters(map));
					log.info("ExamResult Job completetion details : " + result.toString());
				}
			}
			if (!fileFound) {
				log.info("No Input file found, Job terminated.");
			}
		} catch (JobExecutionException ex) {
			log.error("ExamResult Job halted with following excpetion :" + ex);
		}
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

	public void setJobLocator(JobLocator jobLocator) {
		this.jobLocator = jobLocator;
	}

	public void setJobLauncher(JobLauncher jobLauncher) {
		this.jobLauncher = jobLauncher;
	}

}
