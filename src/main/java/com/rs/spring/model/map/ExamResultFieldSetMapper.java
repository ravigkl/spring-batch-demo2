/**
 * 
 */
package com.rs.spring.model.map;


import org.joda.time.LocalDate;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.validation.BindException;

import com.rs.spring.model.ExamResult;

/**
 * @author IBM_ADMIN
 * @date Sep 1, 2016
 */
public class ExamResultFieldSetMapper implements FieldSetMapper<ExamResult>{

	@Override
	public ExamResult mapFieldSet(FieldSet fieldSet) throws BindException {
		 ExamResult result = new ExamResult();
	        result.setStudentName(fieldSet.readString(0));
	        result.setDob(new LocalDate(fieldSet.readDate(1,"dd/MM/yyyy")));
	        result.setPercentage(fieldSet.readDouble(2));
	        return result;
	}
}
