package com.meteogroup.grib2geotiff.geotiff;

import com.meteogroup.grib2geotiff.RecordMetadata;
import com.sun.media.imageio.plugins.tiff.BaselineTIFFTagSet;
import org.geotools.coverage.CoverageFactoryFinder;
import org.geotools.coverage.grid.GridCoverage2D;
import org.geotools.coverage.grid.GridCoverageFactory;
import org.geotools.coverage.grid.io.AbstractGridFormat;
import org.geotools.coverage.grid.io.imageio.GeoToolsWriteParams;
import org.geotools.gce.geotiff.GeoTiffFormat;
import org.geotools.gce.geotiff.GeoTiffWriteParams;
import org.geotools.gce.geotiff.GeoTiffWriter;
import org.geotools.geometry.jts.ReferencedEnvelope;
import org.geotools.referencing.CRS;
import org.opengis.parameter.GeneralParameterValue;
import org.opengis.parameter.ParameterValueGroup;
import org.opengis.referencing.FactoryException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.media.jai.TiledImage;
import java.awt.image.ColorModel;
import java.awt.image.ComponentSampleModel;
import java.awt.image.DataBuffer;
import java.awt.image.SampleModel;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Created by danielt on 14.09.15.
 */
public class GeoTiffExporter {

    static final Logger LOGGER = LoggerFactory.getLogger(GeoTiffExporter.class);
    static final int TILE_SIZE = 256;
    static final int WORLD_LEFT = -180;
    static final int WORLD_RIGHT = 180;
    static final int WORLD_TOP = 90;
    static final int WORLD_BOTTOM = -90;

    private File outDirectory;

    /**
     *
     * @param outDirectory
     */
    public void setOutDirectory(File outDirectory) {
        this.outDirectory = outDirectory;
    }

    /**
     *
     * @param data
     * @param metadata
     * @throws Exception
     */
    public void createGeoTiff(float[] data, RecordMetadata metadata) throws Exception{
        String targetFile = createTargetFilename(metadata);
        verifyDirectories(targetFile);
        writeGeotiff(data, metadata, targetFile);
    }

    /**
     *
     * @param targetFile
     */
    private void verifyDirectories(String targetFile) {
        File target = new File(targetFile);
        if(!target.getParentFile().exists()){
            target.getParentFile().mkdirs();
        }
    }

    /**
     * writer.writeGrid(GribDataset, GribDatatype, Array, Boolean);
     * @param data
     */
    private void writeGeotiff(float[] data, RecordMetadata metadata, String targetFile) throws Exception{
        LOGGER.info("target: " + targetFile);
        GridCoverage2D coverage = createCoverage(data, metadata.getColumns(), metadata.getRows());
        GeoTiffWriter writer = new GeoTiffWriter(new File(targetFile));
        appendTextMetadata(writer, metadata);
        writer.write(coverage, createWriteParameters());
    }

    /**
     * Collect parameters to configure a tiles and compressed GeoTiff
     * see http://docs.geotools.org/stable/javadocs/org/geotools/gce/geotiff/GeoTiffWriteParams.html
     * @return
     */
    private GeneralParameterValue[] createWriteParameters() {

        final GeoTiffFormat format = new GeoTiffFormat();

        final GeoTiffWriteParams wp = new GeoTiffWriteParams();

        wp.setCompressionMode(GeoTiffWriteParams.MODE_EXPLICIT);
        wp.setCompressionType("LZW");

        wp.setTilingMode(GeoToolsWriteParams.MODE_EXPLICIT);
        wp.setTiling(TILE_SIZE, TILE_SIZE);

        final ParameterValueGroup params = format.getWriteParameters();
        params.parameter(
                AbstractGridFormat.GEOTOOLS_WRITE_PARAMS.getName().toString())
                .setValue(wp);

        GeneralParameterValue[] writeParameters = params.values().toArray(new GeneralParameterValue[1]);

        return writeParameters;
    }

    private void appendTextMetadata(GeoTiffWriter writer, RecordMetadata metadata) throws IOException {
        String desc = createDescriptionText(metadata);
        writer.setMetadataValue(Integer.toString(BaselineTIFFTagSet.TAG_DOCUMENT_NAME), metadata.getName());
        writer.setMetadataValue(Integer.toString(BaselineTIFFTagSet.TAG_IMAGE_DESCRIPTION), desc);
        writer.setMetadataValue(Integer.toString(BaselineTIFFTagSet.TAG_SOFTWARE), "edu.ucar & geotools");
        writer.setMetadataValue(Integer.toString(BaselineTIFFTagSet.TAG_RESOLUTION_UNIT), "");
        writer.setMetadataValue(Integer.toString(BaselineTIFFTagSet.TAG_X_RESOLUTION), "");
        writer.setMetadataValue(Integer.toString(BaselineTIFFTagSet.TAG_Y_RESOLUTION), "");
    }

    /**
     * Creates the
     * @param data
     * @return
     */
    public GridCoverage2D createCoverage(float[] data, int columns, int rows) {
        GridCoverageFactory gcf = CoverageFactoryFinder.getGridCoverageFactory(null);
        TiledImage img = ceateImageContent(data, columns, rows);
        ReferencedEnvelope env = createEnvelope();
        return gcf.create("coverage", img, env);
    }

    private TiledImage ceateImageContent(float[] data, int columns, int rows) {
        SampleModel sm = new ComponentSampleModel(DataBuffer.TYPE_DOUBLE,
                TILE_SIZE, TILE_SIZE, 1, TILE_SIZE, new int[]{0});

        ColorModel cm = TiledImage.createColorModel(sm);

        TiledImage img = new TiledImage(0, 0, columns, rows, 0, 0, sm, cm);

        int i = 0;
        int halfColumns = (int)((double)columns/2.0);
        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < columns; x++) {
                if(x >= halfColumns) {
                    img.setSample(x-halfColumns, y, 0, data[i]);
                }else{
                    img.setSample(x+halfColumns, y, 0, data[i]);
                }
                i++;
            }
        }
        return img;
    }

    private ReferencedEnvelope createEnvelope() {
        ReferencedEnvelope env = null;
        try {
            env = new ReferencedEnvelope(
                    WORLD_BOTTOM, WORLD_TOP, WORLD_LEFT, WORLD_RIGHT,
                    CRS.decode("EPSG:4326"));
        } catch (FactoryException e) {
            LOGGER.error("Cannot create georeferenced Envelope: "+e.getMessage(), e);
        }
        return env;
    }

    /**
     *
     * @param metadata
     * @return
     */
    private String createTargetFilename(RecordMetadata metadata) {
        StringBuffer targetFilename = new StringBuffer()
                .append(outDirectory)
                .append("/")
                .append(metadata.getReferenceTime())
                .append("/")
                .append(metadata.getCategory())
                .append("/")
                .append(metadata.getName())
                .append("/")
                .append(metadata.getName());

        if(metadata.getLevelType1() != null) {
            BigDecimal bd = new BigDecimal(metadata.getLevelValue1())
                    .stripTrailingZeros()
                    .setScale(0, RoundingMode.HALF_UP);

            targetFilename.append("_")
                    .append(bd.longValue());
        }
        if(metadata.getLevelType2() != null){
            BigDecimal bd = new BigDecimal(metadata.getLevelValue2())
                    .stripTrailingZeros()
                    .setScale(0, RoundingMode.HALF_UP);
            targetFilename.append("_")
                    .append(bd.longValue());
        }

        targetFilename.append("_")
                .append(metadata.getForecastTime())
                .append(".tif");
        return targetFilename.toString();
    }

    /**
     *
     * @param metadata
     * @return
     */
    private String createDescriptionText(RecordMetadata metadata) {
        StringBuffer desc = new StringBuffer()
                .append("Parameter ID ")
                .append(metadata.getId())
                .append(": ")
                .append(metadata.getName())
                .append(" in ")
                .append(metadata.getUnit());
        if(metadata.getLevelType1() != null){
            desc.append(" at ")
                    .append(metadata.getLevelValue1())
                    .append(" ")
                    .append(metadata.getLevelType2());
        }
        if(metadata.getLevelType2() != null){
            if(metadata.getLevelType1() != null) {
                desc.append(" and ");
            }else{
                desc.append(" at ");
            }
            desc.append(metadata.getLevelValue2())
                    .append(" ")
                    .append(metadata.getLevelType2());
        }
        return desc.toString();
    }

}
