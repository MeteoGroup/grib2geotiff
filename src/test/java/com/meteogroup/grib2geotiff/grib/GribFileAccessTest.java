package com.meteogroup.grib2geotiff.grib;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import ucar.unidata.io.RandomAccessFile;

import java.io.File;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by danielt on 14.09.15.
 */
public class GribFileAccessTest {

    private static final String IN = "src/main/resources/GoldenTestData/";

    @Test
    public void gribfiles_are_detected_properly() throws Exception{
        //given
        GribFileAccess grib = new GribFileAccess();
        System.out.println("path: " + new File(IN).getAbsolutePath());
        grib.setInputDirectory(new File(IN));
        //when
        List<RandomAccessFile> validGribFiles = grib.getRafFiles();
        //then
        assertEquals(3,validGribFiles.size());
    }

}
