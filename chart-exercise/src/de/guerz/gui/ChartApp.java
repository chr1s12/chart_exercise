package de.guerz.gui;

import java.awt.EventQueue;
import javax.swing.JFrame;

import de.guerz.dao.IChartDAO;
import de.guerz.domain.Chart;
import de.guerz.domain.ChartData;
import de.guerz.utils.ReadChartFile;
import jxl.read.biff.BiffException;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.ClassPathResource;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.event.ListSelectionEvent;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JRadioButton;

public class ChartApp {

	private XmlBeanFactory factory;
	private ReadChartFile readChartFile;
	private IChartDAO chartDAO;
	private JFrame frmChartapp;
	private JList listChart;
	private JList listData;
	private JTextField txtFieldPath;
	private JRadioButton rdbtnRchart;
	private JRadioButton rdbtnPchart;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ChartApp window = new ChartApp();
					window.frmChartapp.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public ChartApp() {
		initialize();
		factory = new XmlBeanFactory(new ClassPathResource("applicationContext.xml"));
		chartDAO = (IChartDAO) factory.getBean("chartDAO");
		readChartFile = (ReadChartFile) factory.getBean("readChartFile");

		updateList();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmChartapp = new JFrame();
		frmChartapp.setTitle("ChartApp");
		frmChartapp.setBounds(100, 100, 450, 277);
		frmChartapp.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmChartapp.getContentPane().setLayout(null);

		listChart = new JList();
		listChart.setModel(new DefaultListModel());
		listChart.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listChart.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent arg0) {
				if (arg0.getValueIsAdjusting() == false) {
					((DefaultListModel) getListData().getModel()).removeAllElements();
					if (listChart.getSelectedIndex() != -1) {
						for (ChartData chartData : ((Chart) listChart.getSelectedValue()).getData()) {
							((DefaultListModel) getListData().getModel()).addElement(chartData);
						}
					}
				}
			}
		});
		JScrollPane listChartScroller = new JScrollPane(listChart);
		listChartScroller.setBounds(10, 10, 180, 140);
		frmChartapp.getContentPane().add(listChartScroller);

		listData = new JList();
		listData.setModel(new DefaultListModel());
		JScrollPane listDataScroller = new JScrollPane(listData);
		listDataScroller.setBounds(190, 10, 250, 140);
		frmChartapp.getContentPane().add(listDataScroller);

		JLabel lblInfoDB = new JLabel("Tabelle wählen");
		lblInfoDB.setBounds(200, 155, 220, 15);
		frmChartapp.getContentPane().add(lblInfoDB);

		txtFieldPath = new JTextField();
		txtFieldPath.setBounds(200, 170, 200, 20);
		frmChartapp.getContentPane().add(txtFieldPath);

		JButton btnPath = new JButton("...");
		btnPath.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setMultiSelectionEnabled(false);
				fileChooser.setFileFilter(new FileNameExtensionFilter("Tabellendateien", "xls", "csv"));

				int state = fileChooser.showOpenDialog(null);

				if (state == JFileChooser.APPROVE_OPTION) {
					getTxtFieldPath().setText(fileChooser.getSelectedFile().getAbsolutePath());
				}
			}
		});
		btnPath.setBounds(400, 170, 30, 19);
		frmChartapp.getContentPane().add(btnPath);

		JButton btnSave = new JButton("Speichern in DB");
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				readChartFile.setInputFile(getTxtFieldPath().getText());
				/*
				try {
					chartDAO.saveChart(readChartFile.readXLSFile());
					JOptionPane.showMessageDialog(null, "Datei erfolgreich in Datenbank gespeichert.", "Info", JOptionPane.PLAIN_MESSAGE);
				} catch (BiffException | IOException e) {
					JOptionPane.showMessageDialog(null, e.getMessage(), "Fehlermeldung", JOptionPane.PLAIN_MESSAGE);
				}
				*/
				try {
					chartDAO.saveChart(readChartFile.readCSVFile());
					JOptionPane.showMessageDialog(null, "Datei erfolgreich in Datenbank gespeichert.", "Info", JOptionPane.PLAIN_MESSAGE);
					updateList();
				} catch (FileNotFoundException | ParseException e) {
					JOptionPane.showMessageDialog(null, e.getMessage(), "Fehlermeldung", JOptionPane.PLAIN_MESSAGE);
				}
			}
		});
		btnSave.setBounds(200, 190, 150, 22);
		frmChartapp.getContentPane().add(btnSave);

		JLabel lblInfoCB = new JLabel("Diagramm auswählen");
		lblInfoCB.setBounds(10, 155, 160, 15);
		frmChartapp.getContentPane().add(lblInfoCB);

		rdbtnPchart = new JRadioButton("Piechart");
		rdbtnPchart.setBounds(10, 170, 150, 20);
		frmChartapp.getContentPane().add(rdbtnPchart);

		rdbtnRchart = new JRadioButton("Ringchart");
		rdbtnRchart.setBounds(10, 195, 150, 20);
		frmChartapp.getContentPane().add(rdbtnRchart);

		JButton btnShowDia = new JButton("Anzeigen");
		btnShowDia.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (listChart.getSelectedIndex() == -1) {
					JOptionPane.showMessageDialog(null, "Es muss eine Datei ausgewählt sein!", "Fehlermeldung",
							JOptionPane.PLAIN_MESSAGE);
				} else {
					if (!getRdbtnPchart().isSelected() & getRdbtnRchart().isSelected()) {
						// Ringchart
						DefaultPieDataset pieDataset = new DefaultPieDataset();
						Chart chart = (Chart) ((DefaultListModel) getListChart().getModel())
								.getElementAt(getListChart().getSelectedIndex());
						for (ChartData chartData : chart.getData()) {
							pieDataset.setValue(chartData.getName(), chartData.getWeight());
						}

						JFreeChart freeChart = ChartFactory.createRingChart(null, pieDataset, true, true, true);
						PiePlot p = (PiePlot) freeChart.getPlot();

						ChartFrame frame = new ChartFrame("Ring Chart", freeChart);
						frame.setVisible(true);
						frame.setSize(450, 500);

					} else if (!getRdbtnRchart().isSelected() & getRdbtnPchart().isSelected()) {
						// Piechart
						DefaultPieDataset pieDataset = new DefaultPieDataset();
						Chart chart = (Chart) ((DefaultListModel) getListChart().getModel())
								.getElementAt(getListChart().getSelectedIndex());
						for (ChartData chartData : chart.getData()) {
							pieDataset.setValue(chartData.getName(), chartData.getWeight());
						}

						JFreeChart freeChart = ChartFactory.createPieChart("Anteil am Fondsvermögen", pieDataset, true,
								true, false);
						PiePlot p = (PiePlot) freeChart.getPlot();

						ChartFrame frame = new ChartFrame("Pie Chart", freeChart);
						frame.setVisible(true);
						frame.setSize(450, 500);
					} else {
						JOptionPane.showMessageDialog(null, "Es muss genau ein Diagrammtyp ausgewählt sein!",
								"Fehlermeldung", JOptionPane.PLAIN_MESSAGE);
					}
				}
			}
		});
		btnShowDia.setBounds(10, 220, 110, 22);
		frmChartapp.getContentPane().add(btnShowDia);
	}

	protected void updateList() {
		((DefaultListModel) getListChart().getModel()).removeAllElements();
		List<Chart> charts = chartDAO.loadAllCharts();
		if (!charts.isEmpty()) {
			for (Chart chart : charts) {
				((DefaultListModel) this.listChart.getModel()).addElement(chart);
			}
		}
	}

	public ReadChartFile getReadChartFile() {
		return readChartFile;
	}

	public void setReadChartFile(ReadChartFile readChartFile) {
		this.readChartFile = readChartFile;
	}

	public IChartDAO getChartDAO() {
		return chartDAO;
	}

	public void setChartDAO(IChartDAO chartDAO) {
		this.chartDAO = chartDAO;
	}

	public JFrame getFrame() {
		return frmChartapp;
	}

	public void setFrame(JFrame frame) {
		this.frmChartapp = frame;
	}

	public JList getListChart() {
		return listChart;
	}

	public void setListChart(JList listChart) {
		this.listChart = listChart;
	}

	public JList getListData() {
		return listData;
	}

	public void setListData(JList listData) {
		this.listData = listData;
	}

	public JTextField getTxtFieldPath() {
		return txtFieldPath;
	}

	public void setTxtFieldPath(JTextField txtFieldPath) {
		this.txtFieldPath = txtFieldPath;
	}

	public JRadioButton getRdbtnRchart() {
		return rdbtnRchart;
	}

	public void setRdbtnRchart(JRadioButton rdbtnRchart) {
		this.rdbtnRchart = rdbtnRchart;
	}

	public JRadioButton getRdbtnPchart() {
		return rdbtnPchart;
	}

	public void setRdbtnPchart(JRadioButton rdbtnPchart) {
		this.rdbtnPchart = rdbtnPchart;
	}
}
