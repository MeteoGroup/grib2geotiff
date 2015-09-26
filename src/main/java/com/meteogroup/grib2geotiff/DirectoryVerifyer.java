package com.meteogroup.grib2geotiff;

import java.io.File;
import java.io.IOException;

/**
 * Created by danielt on 26.09.15.
 */
public class DirectoryVerifyer {

    /**
     *
     * @param inputDirectory
     * @throws IOException
     */
    public static void verifyInputDirectory(String inputDirectory) throws IOException{
        File dir = new File(inputDirectory);
        if(!dir.exists()){
            throw new IOException("Selected input path does not exist: "+dir.getAbsolutePath());
        }
        if(!dir.isDirectory()){
            throw new IOException("Selected input path is not a directory: "+dir.getAbsolutePath());
        }
        if(!dir.canRead()){
            throw new IOException("Selected input directory is not readable: "+dir.getAbsolutePath());
        }
    }

    /**
     *
     * @param inputDirectory
     * @throws IOException
     */
    public static void verifyOutputDirectory(String inputDirectory) throws IOException{
        File dir = new File(inputDirectory);
        if(!dir.isDirectory()){
            throw new IOException("Selected output path is not a directory: "+dir.getAbsolutePath());
        }
        if(!dir.canWrite()){
            throw new IOException("Selected output directory is not writable: "+dir.getAbsolutePath());
        }
    }

}
