package com.supervisor.domain.impl;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Random;

import com.supervisor.sdk.utils.CodeGenerator;
import org.apache.commons.lang.StringUtils;

import com.supervisor.domain.DataSheet;
import com.supervisor.domain.DataSheetModel;

public final class DataSheetRandomSequence implements CodeGenerator {

	private final DataSheetModel model;
	private final LocalDate date;
	private static Random random = new Random();
	
	public DataSheetRandomSequence(final DataSheetModel model, final LocalDate date) {
		this.model = model;
		this.date = date;
	}
	
	@Override
	public String generate() throws IOException {
		
		// générer une nouvelle référence
		String reference;
		
		do {
			reference = String.format(
					"%s-%s-%s%s%s%s%s%s", 
					model.code(), 
					date.getYear(), 
					StringUtils.leftPad(String.valueOf(date.getMonth().getValue()), 2, "0"), 
					StringUtils.leftPad(String.valueOf(date.getDayOfMonth()), 2, "0"), 
					StringUtils.leftPad(String.valueOf(LocalTime.now().getHour()), 2, "0"), 
					StringUtils.leftPad(String.valueOf(LocalTime.now().getMinute()), 2, "0"), 
					StringUtils.leftPad(String.valueOf(LocalTime.now().getSecond()), 2, "0"), 
					StringUtils.leftPad(String.valueOf(random.nextInt(100)), 2, "0")
			);
		} while (model.sheets().where(DataSheet::reference, reference).any());	
		
		return reference;
		
	}

}
