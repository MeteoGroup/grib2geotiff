package com.meteogroup.grib2geotiff.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;

import com.meteogroup.grib2geotiff.RecordMetadata;

public class TargetPathGenerator {

	private final static SimpleDateFormat ISO8601_WINDOWS = new SimpleDateFormat("yyyy-MM-dd'T'HH-mm-ss'Z'");
	private final static SimpleDateFormat ISO8601_UNIX = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
	
	public static String generateTargePath(RecordMetadata metadata) {
		SimpleDateFormat sdf = getDataFormatByOs();
		StringBuffer targetDirectory = generateTargetDirectory(metadata, sdf);
		StringBuffer targetFilename = generateTargetFileName(metadata, sdf);
		return targetDirectory.toString()
				+ "/"
				+ targetFilename.toString();
	}

	private static StringBuffer generateTargetFileName(RecordMetadata metadata,
			SimpleDateFormat sdf) {
		StringBuffer targetFilename = new StringBuffer(metadata.getName());
		
		if(metadata.getLevelType1() != null) {
		    BigDecimal bd = new BigDecimal(metadata.getLevelValue1())
		            .stripTrailingZeros()
		            .setScale(0, RoundingMode.HALF_UP);
		
		    targetFilename.append("_")
		            .append(bd.longValue());
		}
		
		if(metadata.getLevelType2() != null){
		    BigDecimal bd = new BigDecimal(metadata.getLevelValue2())
		            .stripTrailingZeros()
		            .setScale(0, RoundingMode.HALF_UP);
		    targetFilename.append("_")
		            .append(bd.longValue());
		}
		
		targetFilename.append("_")
		        .append(sdf.format(metadata.getForecastTime()));
		return targetFilename;
	}

	private static StringBuffer generateTargetDirectory(
			RecordMetadata metadata, SimpleDateFormat sdf) {
		StringBuffer targetDirectory = new StringBuffer()
        .append(sdf.format(metadata.getReferenceTime())).append("/")
        .append(metadata.getCategory()).append("/")
        .append(metadata.getName());
		return targetDirectory;
	}

	private static SimpleDateFormat getDataFormatByOs() {
		SimpleDateFormat sdf = ISO8601_UNIX;
		if (System.getProperty("os.name").startsWith("Windows")) {
			sdf = ISO8601_WINDOWS;
	    }
		return sdf;
	}
	
}
