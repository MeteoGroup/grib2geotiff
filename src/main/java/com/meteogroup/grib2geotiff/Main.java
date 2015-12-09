package com.meteogroup.grib2geotiff;

import org.apache.commons.cli.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;

/**
 * Created by danielt on 14.09.15.
 */
public class Main {

  static final Logger LOGGER = LoggerFactory.getLogger(Main.class);

  /**
   * Main Method
   *
   * @param args
   * @throws Exception
   */
  public static void main(String[] args) {

    Options options = createOptions();
    new HelpFormatter().printHelp("java -jar grib2geotiff.jar", options);

    CommandLineParser parser = new DefaultParser();

    try {
      CommandLine cmd = parser.parse(options, args);

      String inDirectory = cmd.getOptionValue("in", "");
      String outDirectory = cmd.getOptionValue("out", "");

      File inFile = new File(inDirectory);
      File outFile = new File(outDirectory);

      LOGGER.info("\nused input:  " + new File(inFile.getAbsolutePath()));
      LOGGER.info("used output: " + new File(outFile.getAbsolutePath()) + "\n");

      validateDirectories(inDirectory, outDirectory);

      GribAtomizer atomizer = new GribAtomizer(inFile, outFile);
      atomizer.atomize();

    } catch (ParseException e) {
      LOGGER.info("Unexpected Error due runtime: " + e.getLocalizedMessage());
      LOGGER.error("Unexpected Error due runtime: " + e.getLocalizedMessage(), e);
    } catch (Exception e) {
      LOGGER.info("Unexpected Error due runtime: " + e.getLocalizedMessage());
      LOGGER.error("Unexpected Error due runtime: " + e.getLocalizedMessage(), e);
    }
  }

  /**
   * @param inDirectory
   * @param outDirectory
   */
  private static void validateDirectories(String inDirectory, String outDirectory) throws IOException {
    DirectoryVerifyer.verifyInputDirectory(inDirectory);
    DirectoryVerifyer.verifyInputDirectory(inDirectory);
  }

  /**
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
