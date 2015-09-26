package com.meteogroup.grib2geotiff.grib;

import com.meteogroup.grib2geotiff.RecordMetadata;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import ucar.grib.grib2.*;

import javax.inject.Singleton;
import java.awt.geom.Rectangle2D;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by danielt on 25.09.15.
 */
public class GribMetadataReader {

    private final static SimpleDateFormat TIMEFORMAT_ISO8601 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");

    private static Grib2IndicatorSection is;
    private static Grib2IdentificationSection id;
    private static Grib2GDSVariables gdsv;
    private static Grib2Pds pdsv;
    private static Grib2ProductDefinitionSection pds;
    private static Grib2GridDefinitionSection gds;

    /**
     *
     * @param record
     * @return
     */
    public static RecordMetadata getGfsRecordMetadata(Grib2Record record) {
        inferMetadataObjects(record);

        RecordMetadata metadata = new RecordMetadata();
        applyCommonMetadata(metadata);
        applyTimeMetadata(metadata);
        applyLevelMetadata(metadata);
        applyBboxMetadata(metadata);

        return metadata;
    }

    /**
     *
     * @param record
     */
    private static void inferMetadataObjects(Grib2Record record) {
        is = record.getIs();
        id = record.getId();
        gds = record.getGDS();
        pds = record.getPDS();
        pdsv = pds.getPdsVars();
        gdsv = gds.getGdsVars();
    }

    /**
     *
     * @param metadata
     */
    private static void applyBboxMetadata(RecordMetadata metadata) {
        metadata.setBbox(new Rectangle2D.Double(gdsv.getLo1(), gdsv.getLa1(), gdsv.getLo2(), gdsv.getLa2()));
    }

    /**
     *
     * @param metadata
     */
    private static void applyLevelMetadata(RecordMetadata metadata) {
        String level1Type = pdsv.getLevelType1() + " "+ Grib2Tables.codeTable4_5(pdsv.getLevelType1());
        if(pdsv.getLevelType1() != 255) {
            metadata.setLevelType1(Grib2Tables.codeTable4_5(pdsv.getLevelType1()));
            metadata.setLevelValue1(pdsv.getLevelValue1());
        }
        if(pdsv.getLevelType2() != 255) {
            metadata.setLevelType2(Grib2Tables.codeTable4_5(pdsv.getLevelType2()));
            metadata.setLevelValue2(pdsv.getLevelValue2());
        }
    }

    /**
     *
     * @param metadata
     */
    private static void applyTimeMetadata(RecordMetadata metadata) {
        TIMEFORMAT_ISO8601.setTimeZone(TimeZone.getTimeZone("UTC"));
        DateTime referenceTimeUtc = new DateTime(pdsv.getReferenceTime(), DateTimeZone.UTC);
        metadata.setReferenceTime(TIMEFORMAT_ISO8601.format(referenceTimeUtc.getMillis()));
        DateTime forecastTimeUtc = new DateTime(referenceTimeUtc.plusHours(pdsv.getForecastTime()));
        metadata.setForecastTime(TIMEFORMAT_ISO8601.format(forecastTimeUtc.getMillis()));
    }

    /**
     *
     * @param metadata
     */
    private static void applyCommonMetadata(RecordMetadata metadata) {
        metadata.setId(pdsv.getParameterNumber());
        metadata.setCategory(ParameterTable.getCategoryName(is.getDiscipline(), pdsv.getParameterCategory()));
        metadata.setName(ParameterTable.getParameterName(is.getDiscipline(), pdsv.getParameterCategory(), pdsv.getParameterNumber()));
        metadata.setUnit(ParameterTable.getParameterUnit(is.getDiscipline(), pdsv.getParameterCategory(), pdsv.getParameterNumber()));
        metadata.setColumns(gdsv.getNx());
        metadata.setRows(gdsv.getNy());
    }

}
