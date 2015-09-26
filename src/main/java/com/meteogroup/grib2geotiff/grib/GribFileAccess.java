package com.meteogroup.grib2geotiff.grib;

import ucar.grib.grib2.*;
import ucar.unidata.io.RandomAccessFile;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.logging.Logger;

/**
 * Created by danielt on 14.09.15.
 */
public class GribFileAccess {

    private File inputDirectory;

    private static final Logger LOGGER = Logger.getLogger(GribFileAccess.class.getName());

    /**
     *
     * @param inputDirectory
     */
    public void setInputDirectory(File inputDirectory) {
        this.inputDirectory = inputDirectory;
    }

    /**
     *
     * @param gfsFile
     * @param record
     * @return
     * @throws IOException
     */
    public float[] getGfsRecordData(RandomAccessFile gfsFile, Grib2Record record) throws IOException{
        Grib2Data gd = new Grib2Data(gfsFile);
        Grib2IdentificationSection id = record.getId();
        return gd.getData(record.getGdsOffset(), record.getPdsOffset(), id.getRefTime());
    }

    /**
     *
     * @return
     * @throws Exception
     */
    public List<RandomAccessFile> getRafFiles() throws Exception {
        LOGGER.info("start scanning folder " + inputDirectory.getAbsolutePath());
        List<RandomAccessFile> validRafFiles = new ArrayList<RandomAccessFile>();
        File[] files = inputDirectory.listFiles();
        if(files == null || files.length == 0){
            return validRafFiles;
        }

        for (File file : files) {
            RandomAccessFile validRaf = getValidGribfile(file);

            if (validRaf != null) {
                LOGGER.info("use " + file.getAbsolutePath());
                validRafFiles.add(validRaf);
            } else {
                LOGGER.info("skip " + file.getAbsolutePath());
            }
        }
        return validRafFiles;
    }

    /**
     *
     * @param file
     * @return
     * @throws Exception
     */
    private RandomAccessFile getValidGribfile(File file) throws Exception{
        if(!file.exists()
                || file.isDirectory()
                || file.getName().endsWith(".aux.xml")){
            return null;
        }
        RandomAccessFile raf = new RandomAccessFile(file.getAbsolutePath(), "r");
        raf.order(RandomAccessFile.BIG_ENDIAN);

        if(Grib2Input.isValidFile(raf)){
            return raf;
        }else{
            return null;
        }

    }

    /**
     *
     * @param gfsFile
     * @return
     * @throws Exception
     */
    public List<Grib2Record> getRecords(RandomAccessFile gfsFile) throws Exception{
        Grib2Input g2i = new Grib2Input(gfsFile);
        g2i.scan(false, false);
        return g2i.getRecords();
    }

}
