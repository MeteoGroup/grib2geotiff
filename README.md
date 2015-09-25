# grib2geotiff - Gribfile to Geotiff

This easy-to-use tool converts Gribfiles to Geotiffs.
Gribfiles (grb, grib or grib2) are the de-facto standard file format in the Meteorology world while Geotiff (tif) is this standard on the GIS world.
Because Gribfiles are binary with a lot of free configuration capabilities not every GIS tool can handle this files properly.

## Usage
A Gribfile can contain hundreds of records, while one record usually represents one specific meteorological parameter with a rich set off metadata.
grib2geotiff converts all records of a Gribfile to a single Geotiffs.
The converted Geotiffs are grouped by the parameter's metadata so that the bulk of them is manageable.
The important parameter's metadata like level, unit or reference and forecast time is applied to the Geotiffs metadata.


```
java -jar grib2geotiff -in [inputDirectory] -out [outputDirectory]
```

* `inputDirectory` should contain one or more Gribfiles form the Global Forecast System (GFS), which can be downloaded from [their FTP server](ftp://ftp.ncep.noaa.gov/pub/data/nccf/com/gfs/prod/).
* `outputDirectory` will contain all Geotiffs in the file structure schema below. This folder will get created if it does not exist.

```
OUTPUT_DIRECTORY/
│-- [referenceTime]/
    │-- [category]/
        │-- [parameterName]/
            │-- [parameterName]_[level]_[forecastTime].tif
```

* `referenceTime` is the time the whole forecast is referenced
* `category` is the parameter's category
* `parameterName` is the name of the parameter
* `level` is the elevation (if set), often in meters or hP
* `forecastTime` is the time when the forecast affects

## Limitations
Currently this tool is only tested with Gribfiles from the GFS. But feel free to try Gribfiles from other data sources.
Please give us Feedback about the models you want to see integrated.

## Example

1. The GFS Gribfile `gfs.t18z.pgrb2.1p00.f003` was downloaded from the [GFS' FTP server](ftp://ftp.ncep.noaa.gov/pub/data/nccf/com/gfs/prod/gfs.2015091218/)
and placed in the folder `/geodata/weather/input/`.
2. The tool is executed with the command `java -jar -in /geodata/weather/input/ -out /geodata/weather/output/`
3. The output folder now contains the following data:

......
... (coming soon..)
