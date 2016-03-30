package de.guerz.test;

import de.guerz.domain.Chart;
import de.guerz.domain.ChartData;
import de.guerz.utils.ReadChartFile;
import jxl.read.biff.BiffException;
import org.junit.Test;

import java.io.IOException;
import java.text.ParseException;
import java.util.Arrays;

import static junit.framework.Assert.assertEquals;

public class ReadChartFileTest {

    @Test
    public void readChartFile() throws IOException, ParseException, BiffException {
        Chart expectedCSVChart = createExpectedCSVChart();

        ReadChartFile readChartFile = new ReadChartFile();
        readChartFile.setInputFile("TestData.csv");
        Chart csv = readChartFile.readCSVFile();
        assertEquals("returned csv-chart doesn't match", expectedCSVChart, csv);

        Chart expectedXLSChart = createExpectedXLSChart();

        readChartFile.setInputFile("TestData.xls");
        Chart xls = readChartFile.readXLSFile();

        assertEquals("returned xls-chart doesn't match", expectedXLSChart, xls);
    }

    private Chart createExpectedXLSChart() {
        final Chart chart = new Chart();
        chart.setName("TestData.xls");
        chart.setData(Arrays.asList(
                new ChartData("Deutschland", 36.95),
                new ChartData("Großbritannien",	1.71),
                new ChartData("Kanada",	1.21),
                new ChartData("Liquidität/Terminkontrakte",	4.71)
        ));
        return chart;
    }

    private Chart createExpectedCSVChart() {
        final Chart chart = new Chart();
        chart.setName("TestData.csv");
        chart.setData(Arrays.asList(
                new ChartData("Apple", 0.30),
                new ChartData("/\\/\\icrosoft Computers and ASCII Art Inc. ", .15),
                new ChartData("München Mag Dich T-Shirts GmbH", .10)
        ));
        return chart;
    }
}
