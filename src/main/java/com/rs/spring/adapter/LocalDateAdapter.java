/**
 * 
 */
package com.rs.spring.adapter;

import javax.xml.bind.annotation.adapters.XmlAdapter;

import org.joda.time.LocalDate;

/**
 * @author IBM_ADMIN
 * @date Sep 1, 2016
 */
public class LocalDateAdapter extends XmlAdapter<String, LocalDate> {

	public LocalDate unmarshal(String v) throws Exception {
		return new LocalDate(v);
	}

	public String marshal(LocalDate v) throws Exception {
		return v.toString();
	}
}
