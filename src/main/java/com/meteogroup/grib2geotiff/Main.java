package com.meteogroup.grib2geotiff;

import org.apache.commons.cli.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

/**
 * Created by danielt on 14.09.15.
 */
public class Main {

    static final Logger LOGGER = LoggerFactory.getLogger(Main.class);

    /**
     *
     * Main Method
     *
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception{

        Options options = createOptions();

        CommandLineParser parser = new DefaultParser();

        try{
            CommandLine cmd = parser.parse(options, args);
            String inDirectory = cmd.getOptionValue("in", "");
            String outDirectory = cmd.getOptionValue("out", "");

            validateDirectories(inDirectory, outDirectory);

            GribAtomizer atomizer = new GribAtomizer(
                    new File(inDirectory),
                    new File(outDirectory));
            atomizer.atomize();

        }catch(ParseException e){
            new HelpFormatter().printHelp("gfs-atomizer", options);
        }
    }

    /**
     *
     * @param inDirectory
     * @param outDirectory
     */
    private static void validateDirectories(String inDirectory, String outDirectory) {
        try {
            DirectoryVerifyer.verifyInputDirectory(inDirectory);
            DirectoryVerifyer.verifyInputDirectory(inDirectory);
        } catch (IOException e) {
            LOGGER.info(e.getMessage(), e);
            e.printStackTrace();
        }

    }

    /**
     *
     * @return
     */
    private static Options createOptions() {
        Options options = new Options();
        options.addOption("help", false, "help");
        options.addOption("in", true, "input directory with some GFS Gribfiles");
        options.addOption("out", true, "output directory for atomized Geotiffs");
        return options;
    }

}
