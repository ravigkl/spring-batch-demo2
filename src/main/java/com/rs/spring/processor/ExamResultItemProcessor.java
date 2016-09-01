/**
 * 
 */
package com.rs.spring.processor;

import org.apache.log4j.Logger;
import org.springframework.batch.item.ItemProcessor;

import com.rs.spring.model.ExamResult;

/**
 * @author IBM_ADMIN
 * @date Sep 1, 2016
 */
public class ExamResultItemProcessor implements ItemProcessor<ExamResult, ExamResult> {

	private static final Logger log = Logger.getLogger(ExamResultItemProcessor.class);

	@Override
	public ExamResult process(ExamResult result) throws Exception {
		log.info("Processing result :" + result);

		/* put the business logic for the processing and filtering
		 * Only return results which are more than 75%
		 * 
		 */
		if (result.getPercentage() < 75) {
			return null;
		}

		return result;
	}
}
