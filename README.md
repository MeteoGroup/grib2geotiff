# grib2geotiff - Gribfile to Geotiff
This handy tool converts Gribfiles to Geotiffs.
Gribfiles (.grb, .grib or .grib2) are the de-facto standard file format in the Meteorology world while Geotiff (.tif) has the same rank in the GIS world.
Because Gribfiles are binary with a lot of free configuration capabilities, not every GIS tool can handle these files properly.

## Usage
A Gribfile can contain hundreds of records, while one record usually represents one specific meteorological parameter with a rich set of metadata.
grib2geotiff converts all records of a Gribfile to atomized Geotiffs.
The converted Geotiffs are grouped by the parameter's metadata so that the bulk of them is manageable.
The important parameter's metadata like level, unit or reference and forecast time is applied to the Geotiffs metadata.

## Install

To install this tool you need to install [Git](https://git-scm.com/doc), [Maven](https://maven.apache.org/guides/getting-started/) and [Java 8](https://java.com/de/download/) first.
1. clone this repository via Git: `git clone https://github.com/MeteoGroup/grib2geotiff.git`
2. go into the directory: `cd grib2geotiff`
3. build the java sources with Maven: `mvn install`


## Run

 ```
 java -jar grib2geotiff.jar -in [inputDirectory] -out [outputDirectory]
 ```

 * `inputDirectory` should contain one or more Gribfiles form the Global Forecast System (GFS), which can be downloaded from their FTP server: ftp://ftp.ncep.noaa.gov/pub/data/nccf/com/gfs/prod/ .
 * `outputDirectory` will contain all Geotiffs in the file structure schema below. This folder will get created if it does not exist.

 ```
 [outputDirectory]/
 └── [referenceTime]/
     └── [category]/
         └── [parameterName]/
             └── [parameterName]_[level]_[forecastTime].tif
 ```

 * `referenceTime` is the time the whole forecast is referenced to
 * `category` is the parameter's category
 * `parameterName` is the name of the parameter
 * `level` is the elevation (if set), often in meters (m) or hectopascal (hPa)
 * `forecastTime` is the time when the forecast affects


## Limitations

#### GFS only
Currently this tool is only tested with Gribfiles from the GFS. But feel free to try Gribfiles from other data sources.

#### mixed resolutions
Currently grib2geotiff is overwriting already existing Geotiffs. So the processing the Gribfiles with the same `referenceTime` und `forecastTime` but different resolutions has to be done in two steps. See this two Gribfiles:
 * `gfs.t18z.pgrb2.1p00.f003`
 * `gfs.t18z.pgrb2.2p50.f003`

## Example

1. The GFS Gribfile `gfs.t18z.pgrb2.1p00.f003` was downloaded from the [GFS FTP server](ftp://ftp.ncep.noaa.gov/pub/data/nccf/com/gfs/prod/gfs.2015091218/)
and placed in the folder `/geodata/weather/input/`
2. The tool is executed with the command `java -jar -in /geodata/weather/input/ -out /geodata/weather/output/`
3. The output folder now contains XXX Geotiffs where each of them represents one record / parameter of the given Gribfile (see list below)

```
 /geodata/weather/output/
 └── 2015-09-12T18:00:00Z
     ├── Cloud
     │   ├── Cloud_Work_Function
     │   │   └── Cloud_Work_Function_0_2015-09-13T00:00:00Z.tif
     │   ├── Cloud_water
     │   │   └── Cloud_water_0_2015-09-13T00:00:00Z.tif
     │   ├── SunShine_duration
     │   │   └── SunShine_duration_0_2015-09-13T00:00:00Z.tif
     │   └── Total_cloud_cover
     │       └── Total_cloud_cover_0_2015-09-13T00:00:00Z.tif
     ├── Ice
     │   └── Ice_cover
     │       └── Ice_cover_0_2015-09-13T00:00:00Z.tif
     ├── Long-wave_Radiation
     │   ├── Downward_long_wave_rad_flux
     │   │   └── Downward_long_wave_rad_flux_0_2015-09-13T00:00:00Z.tif
     │   └── Upward_long_wave_rad_flux
     │       └── Upward_long_wave_rad_flux_0_2015-09-13T00:00:00Z.tif
     ├── Mass
     │   ├── Geopotential_height
     │   │   ├── Geopotential_height_0_2015-09-13T00:00:00Z.tif
     │   │   ├── Geopotential_height_100000_2015-09-13T00:00:00Z.tif
     │   │   ├── Geopotential_height_10000_2015-09-13T00:00:00Z.tif
     │   │   ├── Geopotential_height_1000_2015-09-13T00:00:00Z.tif
     │   │   ├── Geopotential_height_15000_2015-09-13T00:00:00Z.tif
     │   │   ├── Geopotential_height_20000_2015-09-13T00:00:00Z.tif
     │   │   ├── Geopotential_height_2000_2015-09-13T00:00:00Z.tif
     │   │   ├── Geopotential_height_25000_2015-09-13T00:00:00Z.tif
     │   │   ├── Geopotential_height_30000_2015-09-13T00:00:00Z.tif
     │   │   ├── Geopotential_height_3000_2015-09-13T00:00:00Z.tif
     │   │   ├── Geopotential_height_35000_2015-09-13T00:00:00Z.tif
     │   │   ├── Geopotential_height_40000_2015-09-13T00:00:00Z.tif
     │   │   ├── Geopotential_height_45000_2015-09-13T00:00:00Z.tif
     │   │   ├── Geopotential_height_50000_2015-09-13T00:00:00Z.tif
     │   │   ├── Geopotential_height_5000_2015-09-13T00:00:00Z.tif
     │   │   ├── Geopotential_height_55000_2015-09-13T00:00:00Z.tif
     │   │   ├── Geopotential_height_60000_2015-09-13T00:00:00Z.tif
     │   │   ├── Geopotential_height_65000_2015-09-13T00:00:00Z.tif
     │   │   ├── Geopotential_height_70000_2015-09-13T00:00:00Z.tif
     │   │   ├── Geopotential_height_7000_2015-09-13T00:00:00Z.tif
     │   │   ├── Geopotential_height_75000_2015-09-13T00:00:00Z.tif
     │   │   ├── Geopotential_height_80000_2015-09-13T00:00:00Z.tif
     │   │   ├── Geopotential_height_85000_2015-09-13T00:00:00Z.tif
     │   │   ├── Geopotential_height_90000_2015-09-13T00:00:00Z.tif
     │   │   ├── Geopotential_height_92500_2015-09-13T00:00:00Z.tif
     │   │   ├── Geopotential_height_95000_2015-09-13T00:00:00Z.tif
     │   │   └── Geopotential_height_97500_2015-09-13T00:00:00Z.tif
     │   ├── ICAO_Standard_Atmosphere_Reference_Height
     │   │   └── ICAO_Standard_Atmosphere_Reference_Height_0_2015-09-13T00:00:00Z.tif
     │   ├── MSLP_Eta_Reduction
     │   │   └── MSLP_Eta_Reduction_0_2015-09-13T00:00:00Z.tif
     │   ├── Meridional_Flux_of_Gravity_Wave_Stress
     │   │   └── Meridional_Flux_of_Gravity_Wave_Stress_0_2015-09-13T00:00:00Z.tif
     │   ├── N5-Wave_Geopotential_Height
     │   │   └── N5-Wave_Geopotential_Height_50000_2015-09-13T00:00:00Z.tif
     │   ├── Planetary_Boundary_Layer_Height
     │   │   └── Planetary_Boundary_Layer_Height_0_2015-09-13T00:00:00Z.tif
     │   ├── Pressure
     │   │   ├── Pressure_0_2015-09-13T00:00:00Z.tif
     │   │   └── Pressure_80_2015-09-13T00:00:00Z.tif
     │   ├── Pressure_of_level_from_which_parcel_was_lifted
     │   │   └── Pressure_of_level_from_which_parcel_was_lifted_25500_0_2015-09-13T00:00:00Z.tif
     │   ├── Pressure_reduced_to_MSL
     │   │   └── Pressure_reduced_to_MSL_0_2015-09-13T00:00:00Z.tif
     │   └── Zonal_Flux_of_Gravity_Wave_Stress
     │       └── Zonal_Flux_of_Gravity_Wave_Stress_0_2015-09-13T00:00:00Z.tif
     ├── Moisture
     │   ├── Categorical_Freezing_Rain
     │   │   └── Categorical_Freezing_Rain_0_2015-09-13T00:00:00Z.tif
     │   ├── Categorical_Ice_Pellets
     │   │   └── Categorical_Ice_Pellets_0_2015-09-13T00:00:00Z.tif
     │   ├── Categorical_Rain
     │   │   └── Categorical_Rain_0_2015-09-13T00:00:00Z.tif
     │   ├── Categorical_Snow
     │   │   └── Categorical_Snow_0_2015-09-13T00:00:00Z.tif
     │   ├── Cloud_mixing_ratio
     │   │   ├── Cloud_mixing_ratio_100000_2015-09-13T00:00:00Z.tif
     │   │   ├── Cloud_mixing_ratio_10000_2015-09-13T00:00:00Z.tif
     │   │   ├── Cloud_mixing_ratio_15000_2015-09-13T00:00:00Z.tif
     │   │   ├── Cloud_mixing_ratio_20000_2015-09-13T00:00:00Z.tif
     │   │   ├── Cloud_mixing_ratio_25000_2015-09-13T00:00:00Z.tif
     │   │   ├── Cloud_mixing_ratio_30000_2015-09-13T00:00:00Z.tif
     │   │   ├── Cloud_mixing_ratio_35000_2015-09-13T00:00:00Z.tif
     │   │   ├── Cloud_mixing_ratio_40000_2015-09-13T00:00:00Z.tif
     │   │   ├── Cloud_mixing_ratio_45000_2015-09-13T00:00:00Z.tif
     │   │   ├── Cloud_mixing_ratio_50000_2015-09-13T00:00:00Z.tif
     │   │   ├── Cloud_mixing_ratio_55000_2015-09-13T00:00:00Z.tif
     │   │   ├── Cloud_mixing_ratio_60000_2015-09-13T00:00:00Z.tif
     │   │   ├── Cloud_mixing_ratio_65000_2015-09-13T00:00:00Z.tif
     │   │   ├── Cloud_mixing_ratio_70000_2015-09-13T00:00:00Z.tif
     │   │   ├── Cloud_mixing_ratio_75000_2015-09-13T00:00:00Z.tif
     │   │   ├── Cloud_mixing_ratio_80000_2015-09-13T00:00:00Z.tif
     │   │   ├── Cloud_mixing_ratio_85000_2015-09-13T00:00:00Z.tif
     │   │   ├── Cloud_mixing_ratio_90000_2015-09-13T00:00:00Z.tif
     │   │   ├── Cloud_mixing_ratio_92500_2015-09-13T00:00:00Z.tif
     │   │   ├── Cloud_mixing_ratio_95000_2015-09-13T00:00:00Z.tif
     │   │   └── Cloud_mixing_ratio_97500_2015-09-13T00:00:00Z.tif
     │   ├── Convective_Precipitation_Rate
     │   │   └── Convective_Precipitation_Rate_0_2015-09-13T00:00:00Z.tif
     │   ├── Convective_precipitation
     │   │   └── Convective_precipitation_0_2015-09-13T00:00:00Z.tif
     │   ├── Percent_frozen_precipitation
     │   │   └── Percent_frozen_precipitation_0_2015-09-13T00:00:00Z.tif
     │   ├── Potential_Evaporation_Rate
     │   │   └── Potential_Evaporation_Rate_0_2015-09-13T00:00:00Z.tif
     │   ├── Precipitable_water
     │   │   └── Precipitable_water_0_2015-09-13T00:00:00Z.tif
     │   ├── Precipitation_rate
     │   │   └── Precipitation_rate_0_2015-09-13T00:00:00Z.tif
     │   ├── Relative_humidity
     │   │   ├── Relative_humidity_0_1_2015-09-13T00:00:00Z.tif
     │   │   ├── Relative_humidity_0_2015-09-13T00:00:00Z.tif
     │   │   ├── Relative_humidity_100000_2015-09-13T00:00:00Z.tif
     │   │   ├── Relative_humidity_10000_2015-09-13T00:00:00Z.tif
     │   │   ├── Relative_humidity_1000_2015-09-13T00:00:00Z.tif
     │   │   ├── Relative_humidity_15000_2015-09-13T00:00:00Z.tif
     │   │   ├── Relative_humidity_1_1_2015-09-13T00:00:00Z.tif
     │   │   ├── Relative_humidity_1_2015-09-13T00:00:00Z.tif
     │   │   ├── Relative_humidity_20000_2015-09-13T00:00:00Z.tif
     │   │   ├── Relative_humidity_2000_2015-09-13T00:00:00Z.tif
     │   │   ├── Relative_humidity_25000_2015-09-13T00:00:00Z.tif
     │   │   ├── Relative_humidity_2_2015-09-13T00:00:00Z.tif
     │   │   ├── Relative_humidity_30000_2015-09-13T00:00:00Z.tif
     │   │   ├── Relative_humidity_3000_0_2015-09-13T00:00:00Z.tif
     │   │   ├── Relative_humidity_3000_2015-09-13T00:00:00Z.tif
     │   │   ├── Relative_humidity_35000_2015-09-13T00:00:00Z.tif
     │   │   ├── Relative_humidity_40000_2015-09-13T00:00:00Z.tif
     │   │   ├── Relative_humidity_45000_2015-09-13T00:00:00Z.tif
     │   │   ├── Relative_humidity_50000_2015-09-13T00:00:00Z.tif
     │   │   ├── Relative_humidity_5000_2015-09-13T00:00:00Z.tif
     │   │   ├── Relative_humidity_55000_2015-09-13T00:00:00Z.tif
     │   │   ├── Relative_humidity_60000_2015-09-13T00:00:00Z.tif
     │   │   ├── Relative_humidity_65000_2015-09-13T00:00:00Z.tif
     │   │   ├── Relative_humidity_70000_2015-09-13T00:00:00Z.tif
     │   │   ├── Relative_humidity_7000_2015-09-13T00:00:00Z.tif
     │   │   ├── Relative_humidity_75000_2015-09-13T00:00:00Z.tif
     │   │   ├── Relative_humidity_80000_2015-09-13T00:00:00Z.tif
     │   │   ├── Relative_humidity_85000_2015-09-13T00:00:00Z.tif
     │   │   ├── Relative_humidity_90000_2015-09-13T00:00:00Z.tif
     │   │   ├── Relative_humidity_92500_2015-09-13T00:00:00Z.tif
     │   │   ├── Relative_humidity_95000_2015-09-13T00:00:00Z.tif
     │   │   └── Relative_humidity_97500_2015-09-13T00:00:00Z.tif
     │   ├── Snow_depth
     │   │   └── Snow_depth_0_2015-09-13T00:00:00Z.tif
     │   ├── Specific_humidity
     │   │   ├── Specific_humidity_2_2015-09-13T00:00:00Z.tif
     │   │   ├── Specific_humidity_3000_0_2015-09-13T00:00:00Z.tif
     │   │   └── Specific_humidity_80_2015-09-13T00:00:00Z.tif
     │   ├── Total_precipitation
     │   │   └── Total_precipitation_0_2015-09-13T00:00:00Z.tif
     │   └── Water_equivalent_of_accumulated_snow_depth
     │       └── Water_equivalent_of_accumulated_snow_depth_0_2015-09-13T00:00:00Z.tif
     ├── Momentum
     │   │   ├── Absolute_vorticity_100000_2015-09-13T00:00:00Z.tif
     │   ├── Absolute_vorticity
     │   │   ├── Absolute_vorticity_10000_2015-09-13T00:00:00Z.tif
     │   │   ├── Absolute_vorticity_1000_2015-09-13T00:00:00Z.tif
     │   │   ├── Absolute_vorticity_15000_2015-09-13T00:00:00Z.tif
     │   │   ├── Absolute_vorticity_20000_2015-09-13T00:00:00Z.tif
     │   │   ├── Absolute_vorticity_2000_2015-09-13T00:00:00Z.tif
     │   │   ├── Absolute_vorticity_25000_2015-09-13T00:00:00Z.tif
     │   │   ├── Absolute_vorticity_30000_2015-09-13T00:00:00Z.tif
     │   │   ├── Absolute_vorticity_3000_2015-09-13T00:00:00Z.tif
     │   │   ├── Absolute_vorticity_35000_2015-09-13T00:00:00Z.tif
     │   │   ├── Absolute_vorticity_40000_2015-09-13T00:00:00Z.tif
     │   │   ├── Absolute_vorticity_45000_2015-09-13T00:00:00Z.tif
     │   │   ├── Absolute_vorticity_50000_2015-09-13T00:00:00Z.tif
     │   │   ├── Absolute_vorticity_5000_2015-09-13T00:00:00Z.tif
     │   │   ├── Absolute_vorticity_55000_2015-09-13T00:00:00Z.tif
     │   │   ├── Absolute_vorticity_60000_2015-09-13T00:00:00Z.tif
     │   │   ├── Absolute_vorticity_65000_2015-09-13T00:00:00Z.tif
     │   │   ├── Absolute_vorticity_70000_2015-09-13T00:00:00Z.tif
     │   │   ├── Absolute_vorticity_7000_2015-09-13T00:00:00Z.tif
     │   │   ├── Absolute_vorticity_75000_2015-09-13T00:00:00Z.tif
     │   │   ├── Absolute_vorticity_80000_2015-09-13T00:00:00Z.tif
     │   │   ├── Absolute_vorticity_85000_2015-09-13T00:00:00Z.tif
     │   │   ├── Absolute_vorticity_90000_2015-09-13T00:00:00Z.tif
     │   │   ├── Absolute_vorticity_92500_2015-09-13T00:00:00Z.tif
     │   │   ├── Absolute_vorticity_95000_2015-09-13T00:00:00Z.tif
     │   │   └── Absolute_vorticity_97500_2015-09-13T00:00:00Z.tif
     │   ├── Momentum_flux_u_component
     │   │   └── Momentum_flux_u_component_0_2015-09-13T00:00:00Z.tif
     │   ├── Momentum_flux_v_component
     │   │   └── Momentum_flux_v_component_0_2015-09-13T00:00:00Z.tif
     │   ├── U-Component_Storm_Motion
     │   │   └── U-Component_Storm_Motion_6000_0_2015-09-13T00:00:00Z.tif
     │   ├── U-component_of_wind
     │   │   ├── U-component_of_wind_0_2015-09-13T00:00:00Z.tif
     │   │   ├── U-component_of_wind_100000_2015-09-13T00:00:00Z.tif
     │   │   ├── U-component_of_wind_10000_2015-09-13T00:00:00Z.tif
     │   │   ├── U-component_of_wind_1000_2015-09-13T00:00:00Z.tif
     │   │   ├── U-component_of_wind_100_2015-09-13T00:00:00Z.tif
     │   │   ├── U-component_of_wind_10_2015-09-13T00:00:00Z.tif
     │   │   ├── U-component_of_wind_15000_2015-09-13T00:00:00Z.tif
     │   │   ├── U-component_of_wind_1829_2015-09-13T00:00:00Z.tif
     │   │   ├── U-component_of_wind_1_2015-09-13T00:00:00Z.tif
     │   │   ├── U-component_of_wind_20000_2015-09-13T00:00:00Z.tif
     │   │   ├── U-component_of_wind_2000_2015-09-13T00:00:00Z.tif
     │   │   ├── U-component_of_wind_25000_2015-09-13T00:00:00Z.tif
     │   │   ├── U-component_of_wind_2743_2015-09-13T00:00:00Z.tif
     │   │   ├── U-component_of_wind_30000_2015-09-13T00:00:00Z.tif
     │   │   ├── U-component_of_wind_3000_0_2015-09-13T00:00:00Z.tif
     │   │   ├── U-component_of_wind_3000_2015-09-13T00:00:00Z.tif
     │   │   ├── U-component_of_wind_35000_2015-09-13T00:00:00Z.tif
     │   │   ├── U-component_of_wind_3658_2015-09-13T00:00:00Z.tif
     │   │   ├── U-component_of_wind_40000_2015-09-13T00:00:00Z.tif
     │   │   ├── U-component_of_wind_45000_2015-09-13T00:00:00Z.tif
     │   │   ├── U-component_of_wind_50000_2015-09-13T00:00:00Z.tif
     │   │   ├── U-component_of_wind_5000_2015-09-13T00:00:00Z.tif
     │   │   ├── U-component_of_wind_55000_2015-09-13T00:00:00Z.tif
     │   │   ├── U-component_of_wind_60000_2015-09-13T00:00:00Z.tif
     │   │   ├── U-component_of_wind_65000_2015-09-13T00:00:00Z.tif
     │   │   ├── U-component_of_wind_70000_2015-09-13T00:00:00Z.tif
     │   │   ├── U-component_of_wind_7000_2015-09-13T00:00:00Z.tif
     │   │   ├── U-component_of_wind_75000_2015-09-13T00:00:00Z.tif
     │   │   ├── U-component_of_wind_80000_2015-09-13T00:00:00Z.tif
     │   │   ├── U-component_of_wind_80_2015-09-13T00:00:00Z.tif
     │   │   ├── U-component_of_wind_85000_2015-09-13T00:00:00Z.tif
     │   │   ├── U-component_of_wind_90000_2015-09-13T00:00:00Z.tif
     │   │   ├── U-component_of_wind_92500_2015-09-13T00:00:00Z.tif
     │   │   ├── U-component_of_wind_95000_2015-09-13T00:00:00Z.tif
     │   │   └── U-component_of_wind_97500_2015-09-13T00:00:00Z.tif
     │   ├── UnknownParameter_D0_C2_224
     │   │   └── UnknownParameter_D0_C2_224_0_2015-09-13T00:00:00Z.tif
     │   ├── V-Component_Storm_Motion
     │   │   └── V-Component_Storm_Motion_6000_0_2015-09-13T00:00:00Z.tif
     │   ├── V-component_of_wind
     │   │   ├── V-component_of_wind_0_2015-09-13T00:00:00Z.tif
     │   │   ├── V-component_of_wind_100000_2015-09-13T00:00:00Z.tif
     │   │   ├── V-component_of_wind_10000_2015-09-13T00:00:00Z.tif
     │   │   ├── V-component_of_wind_1000_2015-09-13T00:00:00Z.tif
     │   │   ├── V-component_of_wind_100_2015-09-13T00:00:00Z.tif
     │   │   ├── V-component_of_wind_10_2015-09-13T00:00:00Z.tif
     │   │   ├── V-component_of_wind_15000_2015-09-13T00:00:00Z.tif
     │   │   ├── V-component_of_wind_1829_2015-09-13T00:00:00Z.tif
     │   │   ├── V-component_of_wind_1_2015-09-13T00:00:00Z.tif
     │   │   ├── V-component_of_wind_20000_2015-09-13T00:00:00Z.tif
     │   │   ├── V-component_of_wind_2000_2015-09-13T00:00:00Z.tif
     │   │   ├── V-component_of_wind_25000_2015-09-13T00:00:00Z.tif
     │   │   ├── V-component_of_wind_2743_2015-09-13T00:00:00Z.tif
     │   │   ├── V-component_of_wind_30000_2015-09-13T00:00:00Z.tif
     │   │   ├── V-component_of_wind_3000_0_2015-09-13T00:00:00Z.tif
     │   │   ├── V-component_of_wind_3000_2015-09-13T00:00:00Z.tif
     │   │   ├── V-component_of_wind_35000_2015-09-13T00:00:00Z.tif
     │   │   ├── V-component_of_wind_3658_2015-09-13T00:00:00Z.tif
     │   │   ├── V-component_of_wind_40000_2015-09-13T00:00:00Z.tif
     │   │   ├── V-component_of_wind_45000_2015-09-13T00:00:00Z.tif
     │   │   ├── V-component_of_wind_50000_2015-09-13T00:00:00Z.tif
     │   │   ├── V-component_of_wind_5000_2015-09-13T00:00:00Z.tif
     │   │   ├── V-component_of_wind_55000_2015-09-13T00:00:00Z.tif
     │   │   ├── V-component_of_wind_60000_2015-09-13T00:00:00Z.tif
     │   │   ├── V-component_of_wind_65000_2015-09-13T00:00:00Z.tif
     │   │   ├── V-component_of_wind_70000_2015-09-13T00:00:00Z.tif
     │   │   ├── V-component_of_wind_7000_2015-09-13T00:00:00Z.tif
     │   │   ├── V-component_of_wind_75000_2015-09-13T00:00:00Z.tif
     │   │   ├── V-component_of_wind_80000_2015-09-13T00:00:00Z.tif
     │   │   ├── V-component_of_wind_80_2015-09-13T00:00:00Z.tif
     │   │   ├── V-component_of_wind_85000_2015-09-13T00:00:00Z.tif
     │   │   ├── V-component_of_wind_90000_2015-09-13T00:00:00Z.tif
     │   │   ├── V-component_of_wind_92500_2015-09-13T00:00:00Z.tif
     │   │   ├── V-component_of_wind_95000_2015-09-13T00:00:00Z.tif
     │   │   └── V-component_of_wind_97500_2015-09-13T00:00:00Z.tif
     │   ├── Vertical_speed_sheer
     │   │   └── Vertical_speed_sheer_0_2015-09-13T00:00:00Z.tif
     │   ├── Vertical_velocity
     │   │   ├── Vertical_velocity_100000_2015-09-13T00:00:00Z.tif
     │   │   ├── Vertical_velocity_10000_2015-09-13T00:00:00Z.tif
     │   │   ├── Vertical_velocity_15000_2015-09-13T00:00:00Z.tif
     │   │   ├── Vertical_velocity_1_2015-09-13T00:00:00Z.tif
     │   │   ├── Vertical_velocity_20000_2015-09-13T00:00:00Z.tif
     │   │   ├── Vertical_velocity_25000_2015-09-13T00:00:00Z.tif
     │   │   ├── Vertical_velocity_30000_2015-09-13T00:00:00Z.tif
     │   │   ├── Vertical_velocity_35000_2015-09-13T00:00:00Z.tif
     │   │   ├── Vertical_velocity_40000_2015-09-13T00:00:00Z.tif
     │   │   ├── Vertical_velocity_45000_2015-09-13T00:00:00Z.tif
     │   │   ├── Vertical_velocity_50000_2015-09-13T00:00:00Z.tif
     │   │   ├── Vertical_velocity_55000_2015-09-13T00:00:00Z.tif
     │   │   ├── Vertical_velocity_60000_2015-09-13T00:00:00Z.tif
     │   │   ├── Vertical_velocity_65000_2015-09-13T00:00:00Z.tif
     │   │   ├── Vertical_velocity_70000_2015-09-13T00:00:00Z.tif
     │   │   ├── Vertical_velocity_75000_2015-09-13T00:00:00Z.tif
     │   │   ├── Vertical_velocity_80000_2015-09-13T00:00:00Z.tif
     │   │   ├── Vertical_velocity_85000_2015-09-13T00:00:00Z.tif
     │   │   ├── Vertical_velocity_90000_2015-09-13T00:00:00Z.tif
     │   │   ├── Vertical_velocity_92500_2015-09-13T00:00:00Z.tif
     │   │   ├── Vertical_velocity_95000_2015-09-13T00:00:00Z.tif
     │   │   └── Vertical_velocity_97500_2015-09-13T00:00:00Z.tif
     │   └── Wind_speed
     │       └── Wind_speed_0_2015-09-13T00:00:00Z.tif
     ├── Physical_atmospheric_properties
     │   └── Albedo
     │       └── Albedo_0_2015-09-13T00:00:00Z.tif
     ├── Short-wave_Radiation
     │   ├── Downward_short_wave_rad_flux
     │   │   └── Downward_short_wave_rad_flux_0_2015-09-13T00:00:00Z.tif
     │   └── Upward_short_wave_rad_flux
     │       └── Upward_short_wave_rad_flux_0_2015-09-13T00:00:00Z.tif
     ├── Soil_Products
     │   └── Field_capacity
     │       └── Field_capacity_0_2015-09-13T00:00:00Z.tif
     ├── Temperature
     │   ├── Dew_point_temperature
     │   │   └── Dew_point_temperature_2_2015-09-13T00:00:00Z.tif
     │   ├── Latent_heat_net_flux
     │   │   └── Latent_heat_net_flux_0_2015-09-13T00:00:00Z.tif
     │   ├── Maximum_temperature
     │   │   └── Maximum_temperature_2_2015-09-13T00:00:00Z.tif
     │   ├── Minimum_temperature
     │   │   └── Minimum_temperature_2_2015-09-13T00:00:00Z.tif
     │   ├── Potential_temperature
     │   │   └── Potential_temperature_1_2015-09-13T00:00:00Z.tif
     │   ├── Sensible_heat_net_flux
     │   │   └── Sensible_heat_net_flux_0_2015-09-13T00:00:00Z.tif
     │   ├── Temperature
     │   │   ├── Temperature_0_2015-09-13T00:00:00Z.tif
     │   │   ├── Temperature_100000_2015-09-13T00:00:00Z.tif
     │   │   ├── Temperature_10000_2015-09-13T00:00:00Z.tif
     │   │   ├── Temperature_1000_2015-09-13T00:00:00Z.tif
     │   │   ├── Temperature_100_2015-09-13T00:00:00Z.tif
     │   │   ├── Temperature_15000_2015-09-13T00:00:00Z.tif
     │   │   ├── Temperature_1829_2015-09-13T00:00:00Z.tif
     │   │   ├── Temperature_1_2015-09-13T00:00:00Z.tif
     │   │   ├── Temperature_20000_2015-09-13T00:00:00Z.tif
     │   │   ├── Temperature_2000_2015-09-13T00:00:00Z.tif
     │   │   ├── Temperature_25000_2015-09-13T00:00:00Z.tif
     │   │   ├── Temperature_2743_2015-09-13T00:00:00Z.tif
     │   │   ├── Temperature_2_2015-09-13T00:00:00Z.tif
     │   │   ├── Temperature_30000_2015-09-13T00:00:00Z.tif
     │   │   ├── Temperature_3000_0_2015-09-13T00:00:00Z.tif
     │   │   ├── Temperature_3000_2015-09-13T00:00:00Z.tif
     │   │   ├── Temperature_35000_2015-09-13T00:00:00Z.tif
     │   │   ├── Temperature_3658_2015-09-13T00:00:00Z.tif
     │   │   ├── Temperature_40000_2015-09-13T00:00:00Z.tif
     │   │   ├── Temperature_45000_2015-09-13T00:00:00Z.tif
     │   │   ├── Temperature_50000_2015-09-13T00:00:00Z.tif
     │   │   ├── Temperature_5000_2015-09-13T00:00:00Z.tif
     │   │   ├── Temperature_55000_2015-09-13T00:00:00Z.tif
     │   │   ├── Temperature_60000_2015-09-13T00:00:00Z.tif
     │   │   ├── Temperature_65000_2015-09-13T00:00:00Z.tif
     │   │   ├── Temperature_70000_2015-09-13T00:00:00Z.tif
     │   │   ├── Temperature_7000_2015-09-13T00:00:00Z.tif
     │   │   ├── Temperature_75000_2015-09-13T00:00:00Z.tif
     │   │   ├── Temperature_80000_2015-09-13T00:00:00Z.tif
     │   │   ├── Temperature_80_2015-09-13T00:00:00Z.tif
     │   │   ├── Temperature_85000_2015-09-13T00:00:00Z.tif
     │   │   ├── Temperature_90000_2015-09-13T00:00:00Z.tif
     │   │   ├── Temperature_92500_2015-09-13T00:00:00Z.tif
     │   │   ├── Temperature_95000_2015-09-13T00:00:00Z.tif
     │   │   └── Temperature_97500_2015-09-13T00:00:00Z.tif
     │   └── UnknownParameter_D0_C0_21
     │       └── UnknownParameter_D0_C0_21_2_2015-09-13T00:00:00Z.tif
     ├── Thermodynamic_Stability_indices
     │   ├── Best_lifted_index
     │   │   └── Best_lifted_index_0_2015-09-13T00:00:00Z.tif
     │   ├── Convective_available_potential_energy
     │   │   ├── Convective_available_potential_energy_0_2015-09-13T00:00:00Z.tif
     │   │   ├── Convective_available_potential_energy_18000_0_2015-09-13T00:00:00Z.tif
     │   │   └── Convective_available_potential_energy_25500_0_2015-09-13T00:00:00Z.tif
     │   ├── Convective_inhibition
     │   │   ├── Convective_inhibition_0_2015-09-13T00:00:00Z.tif
     │   │   ├── Convective_inhibition_18000_0_2015-09-13T00:00:00Z.tif
     │   │   └── Convective_inhibition_25500_0_2015-09-13T00:00:00Z.tif
     │   ├── Storm_relative_helicity
     │   │   └── Storm_relative_helicity_3000_0_2015-09-13T00:00:00Z.tif
     │   └── Surface_Lifted_Index
     │       └── Surface_Lifted_Index_0_2015-09-13T00:00:00Z.tif
     ├── Trace_gases
     │   ├── Ozone_Mixing_Ratio
     │   │   ├── Ozone_Mixing_Ratio_10000_2015-09-13T00:00:00Z.tif
     │   │   ├── Ozone_Mixing_Ratio_1000_2015-09-13T00:00:00Z.tif
     │   │   ├── Ozone_Mixing_Ratio_15000_2015-09-13T00:00:00Z.tif
     │   │   ├── Ozone_Mixing_Ratio_20000_2015-09-13T00:00:00Z.tif
     │   │   ├── Ozone_Mixing_Ratio_2000_2015-09-13T00:00:00Z.tif
     │   │   ├── Ozone_Mixing_Ratio_25000_2015-09-13T00:00:00Z.tif
     │   │   ├── Ozone_Mixing_Ratio_30000_2015-09-13T00:00:00Z.tif
     │   │   ├── Ozone_Mixing_Ratio_3000_2015-09-13T00:00:00Z.tif
     │   │   ├── Ozone_Mixing_Ratio_35000_2015-09-13T00:00:00Z.tif
     │   │   ├── Ozone_Mixing_Ratio_40000_2015-09-13T00:00:00Z.tif
     │   │   ├── Ozone_Mixing_Ratio_5000_2015-09-13T00:00:00Z.tif
     │   │   └── Ozone_Mixing_Ratio_7000_2015-09-13T00:00:00Z.tif
     │   └── Total_ozone
     │       └── Total_ozone_0_2015-09-13T00:00:00Z.tif
     ├── UnknownCategory_4
     │   └── UnknownParameter_D2_C4_2
     │       └── UnknownParameter_D2_C4_2_0_2015-09-13T00:00:00Z.tif
     └── Vegetation_and_Biomass
         ├── Ground_Heat_Flux
         │   └── Ground_Heat_Flux_0_2015-09-13T00:00:00Z.tif
         ├── Land_cover
         │   └── Land_cover_0_2015-09-13T00:00:00Z.tif
         ├── Soil_temperature
         │   ├── Soil_temperature_0_0_2015-09-13T00:00:00Z.tif
         │   ├── Soil_temperature_0_1_2015-09-13T00:00:00Z.tif
         │   └── Soil_temperature_1_2_2015-09-13T00:00:00Z.tif
         ├── Volumetric_Soil_Moisture_Content
         │   ├── Volumetric_Soil_Moisture_Content_0_0_2015-09-13T00:00:00Z.tif
         │   ├── Volumetric_Soil_Moisture_Content_0_1_2015-09-13T00:00:00Z.tif
         │   └── Volumetric_Soil_Moisture_Content_1_2_2015-09-13T00:00:00Z.tif
         ├── Water_runoff
         │   └── Water_runoff_0_2015-09-13T00:00:00Z.tif
         └── Wilting_Point
             └── Wilting_Point_0_2015-09-13T00:00:00Z.tif
```