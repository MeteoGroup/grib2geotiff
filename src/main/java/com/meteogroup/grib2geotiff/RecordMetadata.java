package com.meteogroup.grib2geotiff;

import java.awt.geom.Rectangle2D;

/**
 * Created by danielt on 22.09.15.
 */
public class RecordMetadata {

    int id;
    String name;
    String category;
    String unit;
    String referenceTime;
    String forecastTime;
    Rectangle2D.Double bbox;
    int columns;
    int rows;
    String levelType1;
    Double levelValue1;
    String levelType2;
    Double levelValue2;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getReferenceTime() {
        return referenceTime;
    }

    public void setReferenceTime(String referenceTime) {
        this.referenceTime = referenceTime;
    }

    public String getForecastTime() {
        return forecastTime;
    }

    public void setForecastTime(String forecastTime) {
        this.forecastTime = forecastTime;
    }

    public Rectangle2D.Double getBbox() {
        return bbox;
    }

    public void setBbox(Rectangle2D.Double bbox) {
        this.bbox = bbox;
    }

    public String getLevelType1() {
        return levelType1;
    }

    public void setLevelType1(String levelType1) {
        this.levelType1 = levelType1;
    }

    public Double getLevelValue1() {
        return levelValue1;
    }

    public void setLevelValue1(Double levelValue1) {
        this.levelValue1 = levelValue1;
    }

    public String getLevelType2() {
        return levelType2;
    }

    public void setLevelType2(String levelType2) {
        this.levelType2 = levelType2;
    }

    public Double getLevelValue2() {
        return levelValue2;
    }

    public void setLevelValue2(Double levelValue2) {
        this.levelValue2 = levelValue2;
    }

    public int getColumns() {
        return columns;
    }

    public void setColumns(int columns) {
        this.columns = columns;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }
}
