/**
 * 
 */
package com.rs.spring.quartz;

import org.quartz.CronScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.springframework.beans.factory.FactoryBean;

/**
 * @author IBM_ADMIN
 * @date Sep 1, 2016
 */
public class CronTriggerFactoryBean implements FactoryBean<Trigger> {

	private final String jobName;

	private final String cronExpression;

	public CronTriggerFactoryBean(String jobName, String cronExpression) {
		this.jobName = jobName;
		this.cronExpression = cronExpression;
	}

	@Override
	public Trigger getObject() throws Exception {
		return TriggerBuilder.newTrigger().forJob(jobName, "DEFAULT").withIdentity(jobName + "Trigger", "DEFAULT")
				.withSchedule(CronScheduleBuilder.cronSchedule(cronExpression)).build();
	}

	@Override
	public Class<?> getObjectType() {
		return Trigger.class;
	}

	@Override
	public boolean isSingleton() {
		return false;
	}

}
