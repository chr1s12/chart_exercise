package de.guerz.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import de.guerz.domain.Chart;
import de.guerz.domain.ChartData;
import jxl.*;
import jxl.read.biff.BiffException;

public class ReadChartFile {

	private String inputFile;

	public void setInputFile(String inputFile) {
		this.inputFile = inputFile;
	}

	public Chart readXLSFile() throws IOException, BiffException {
		File inputWorkbook = new File(inputFile);
		Chart chart = new Chart();
		chart.setName(inputWorkbook.getName());
		List<ChartData> data = new ArrayList<>();

		WorkbookSettings ws = new WorkbookSettings();
		ws.setEncoding("ISO-8859-1");
		Workbook workbook;
		workbook = Workbook.getWorkbook(inputWorkbook, ws);
		Sheet sheet = workbook.getSheet(0);

		for (int i = 1; i < sheet.getRows(); i++) {
			ChartData chartData = new ChartData();
			for (int j = 0; j < sheet.getColumns(); j++) {
				Cell cell = sheet.getCell(j, i);
				CellType type = cell.getType();
				if (type == CellType.LABEL) {
					chartData.setName(cell.getContents());
				}

				if (type == CellType.NUMBER) {
					chartData.setWeight(Double.parseDouble(cell.getContents().replace(',', '.')));
					data.add(chartData);
				}
			}
		}
		chart.setData(data);
		return chart;
	}
	
	public Chart readCSVFile() throws ParseException, FileNotFoundException {
		File file = new File(inputFile);
		Chart chart = new Chart();
		chart.setName(file.getName());
		List<ChartData> data = new ArrayList<>();

		Scanner inputStream = new Scanner(file);
		inputStream.nextLine();
		while (inputStream.hasNextLine()) {
			String line = inputStream.nextLine();
			ChartData chartData = new ChartData();
			String[] values = line.split(",");
			chartData.setName(values[1]);
			chartData.setWeight(Double.parseDouble(values[2].replace(',', '.')));
			data.add(chartData);
		}
		inputStream.close();

		chart.setData(data);
		return chart;
	}
}
