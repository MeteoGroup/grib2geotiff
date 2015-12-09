package com.meteogroup.grib2geotiff.geotiff;

import com.meteogroup.grib2geotiff.RecordMetadata;

/**
 * Created by danielt on 09.12.15.
 */
public class GeoTiffDescriptionText {

  /**
   * @param metadata
   * @return
   */
  public static String createDescriptionText(RecordMetadata metadata) {
    StringBuffer desc = new StringBuffer()
        .append("Parameter ID ")
        .append(metadata.getId())
        .append(": ")
        .append(metadata.getName())
        .append(" in ")
        .append(metadata.getUnit());
    if (metadata.getLevelType1() != null) {
      desc.append(" at ")
          .append(metadata.getLevelValue1())
          .append(" ")
          .append(metadata.getLevelType2());
    }
    if (metadata.getLevelType2() != null) {
      if (metadata.getLevelType1() != null) {
        desc.append(" and ");
      } else {
        desc.append(" at ");
      }
      desc.append(metadata.getLevelValue2())
          .append(" ")
          .append(metadata.getLevelType2());
    }
    return desc.toString();
  }

}
