package com.meteogroup.grib2geotiff;

import com.meteogroup.grib2geotiff.geotiff.GeoTiffExporter;
import com.meteogroup.grib2geotiff.grib.GribFileAccess;
import com.meteogroup.grib2geotiff.grib.GribMetadataReader;
import ucar.grib.grib2.Grib2Record;
import ucar.unidata.io.RandomAccessFile;

import java.io.File;
import java.util.List;

/**
 * Created by danielt on 14.09.15.
 */
public class GribAtomizer {

    GribFileAccess gribAccessor = new GribFileAccess();

    GribMetadataReader metadataReader = new GribMetadataReader();

    GeoTiffExporter geoTiffExporter = new GeoTiffExporter();

    public GribAtomizer(File inDirectory, File outDirectory) throws Exception{
        this.gribAccessor.setInputDirectory(inDirectory);
        this.geoTiffExporter.setOutDirectory(outDirectory);
    }

    public void atomize() throws Exception{
        List<RandomAccessFile> gfsFiles = gribAccessor.getRafFiles();
        for (RandomAccessFile gfsFile : gfsFiles){
            List<Grib2Record> records = gribAccessor.getRecords(gfsFile);
            for(Grib2Record record : records){
                RecordMetadata metadata = metadataReader.getGfsRecordMetadata(record);
                float[] data = gribAccessor.getGfsRecordData(gfsFile, record);
                geoTiffExporter.createGeoTiff(data, metadata);
            }
        }
    }

}
