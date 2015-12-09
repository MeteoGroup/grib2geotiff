package com.meteogroup.grib2geotiff.grib;

import org.junit.Test;
import ucar.grib.grib2.Grib2Record;
import ucar.unidata.io.RandomAccessFile;

import java.io.File;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * Created by danielt on 14.09.15.
 */
public class GribFileAccessTest {

  private static final String IN = "src/main/resources/GoldenTestData/";
  private static final String FILE_2p5_f000 = "gfs.t18z.pgrb2.2p50.f000";
  private static final String FILE_1p0_f003 = "gfs.t18z.pgrb2.1p00.f003";
  private static final String FILE_2p5_f006 = "gfs.t18z.pgrb2.2p50.f006";

  /**
   * @throws Exception
   */
  @Test
  public void gribfiles_are_detected_properly() throws Exception {

    //given
    GribFileAccess grib = new GribFileAccess();
    grib.setInputDirectory(new File(IN));

    //when
    List<RandomAccessFile> validGribFiles = grib.getRafFiles();

    //then
    assertEquals(3, validGribFiles.size());
  }

  /**
   * @throws Exception
   */
  @Test
  public void record_count_is_correct() throws Exception {

    //given
    GribFileAccess grib = new GribFileAccess();
    grib.setInputDirectory(new File(IN));

    //when
    List<RandomAccessFile> validGribFiles = grib.getRafFiles();

    //then
    for (RandomAccessFile gribFile : validGribFiles) {
      List<Grib2Record> records = grib.getRecords(gribFile);
      if (gribFile.getLocation().endsWith(FILE_2p5_f000)) {
        assertEquals(321, records.size());
      } else if (gribFile.getLocation().endsWith(FILE_1p0_f003)) {
        assertEquals(365, records.size());
      } else if (gribFile.getLocation().endsWith(FILE_2p5_f006)) {
        assertEquals(365, records.size());
      } else {
        fail("test file not registered: " + gribFile.getLocation());
      }

    }
  }


}
