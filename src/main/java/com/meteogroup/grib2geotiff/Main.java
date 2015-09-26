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
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception{
        LOGGER.info("args: " + Arrays.toString(args));

        Options options = createOptions();

        CommandLineParser parser = new DefaultParser();

        try{
            CommandLine cmd = parser.parse(options, args);
            if(cmd.hasOption("in")){
                LOGGER.info("in is drin!!!! ");
            }
            if(cmd.hasOption("out")){
                LOGGER.info("out is drin!!!! ");
            }
            String inDirectory = cmd.getOptionValue("in", "");
            String outDirectory = cmd.getOptionValue("out", "");
            LOGGER.info("in = " + inDirectory);
            LOGGER.info("out = " + outDirectory);

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
        options.addOption("in", true, "input directory with some GFS files");
        options.addOption("out", true, "output directory for atomized GFS files");
        return options;
    }

}
