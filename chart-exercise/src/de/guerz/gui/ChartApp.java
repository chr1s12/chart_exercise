package de.guerz.gui;

import de.guerz.bean.ApplContext;
import de.guerz.dao.ChartDAOService;
import de.guerz.domain.Chart;
import de.guerz.domain.ChartData;
import de.guerz.utils.ReadChartFile;
import jxl.read.biff.BiffException;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;

import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

public class ChartApp {

    private ReadChartFile readChartFile;
    private ChartDAOService chartDAOService;
    private JFrame frmChartapp;
    private JList<Chart> listChart;
    private JList<ChartData> listData;
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
/*        XmlBeanFactory factory = new XmlBeanFactory(new ClassPathResource("applicationContext.xml"));
        chartDAO = (IChartDAO) factory.getBean("chartDAO");
        readChartFile = (ReadChartFile) factory.getBean("readChartFile");*/


        ApplicationContext ctx =  new AnnotationConfigApplicationContext(ApplContext.class);
        chartDAOService = ctx.getBean(ChartDAOService.class);
        readChartFile = ctx.getBean(ReadChartFile.class);

        updateList();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        frmChartapp = new JFrame();
        frmChartapp.setTitle("ChartApp");
        frmChartapp.setBounds(100, 100, 450, 277);
        frmChartapp.setDefaultCloseOperation(EXIT_ON_CLOSE);
        frmChartapp.getContentPane().setLayout(null);

        listChart = new JList<>();
        listChart.setModel(new DefaultListModel<Chart>());
        listChart.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listChart.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent arg0) {
                if (!arg0.getValueIsAdjusting()) {
                    ((DefaultListModel) getListData().getModel()).removeAllElements();
                    if (listChart.getSelectedIndex() != -1) {
                        for (ChartData chartData : (listChart.getSelectedValue()).getData()) {
                            ((DefaultListModel<ChartData>) getListData().getModel()).addElement(chartData);
                        }
                    }
                }
            }
        });
        JScrollPane listChartScroller = new JScrollPane(listChart);
        listChartScroller.setBounds(10, 10, 180, 140);
        frmChartapp.getContentPane().add(listChartScroller);

        listData = new JList<>();
        listData.setModel(new DefaultListModel<ChartData>());
        JScrollPane listDataScroller = new JScrollPane(listData);
        listDataScroller.setBounds(190, 10, 250, 140);
        frmChartapp.getContentPane().add(listDataScroller);

        JLabel lblInfoDB = new JLabel("Tabelle wählen");
        lblInfoDB.setBounds(200, 155, 220, 15);
        frmChartapp.getContentPane().add(lblInfoDB);

        txtFieldPath = new JTextField();
        txtFieldPath.setBounds(200, 170, 200, 20);
        frmChartapp.getContentPane().add(txtFieldPath);

        JButton btnPath = createFileReadButton();
        frmChartapp.getContentPane().add(btnPath);

        JButton btnSave = createSaveButton();
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

        JButton btnShowDia = createShowChartButton();
        frmChartapp.getContentPane().add(btnShowDia);
    }

    private JButton createShowChartButton() {
        JButton btnShowDia = new JButton("Anzeigen");
        btnShowDia.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                if (listChart.getSelectedIndex() == -1) {
                    JOptionPane.showMessageDialog(null, "Es muss eine Datei ausgewählt sein!", "Fehlermeldung",
                            JOptionPane.PLAIN_MESSAGE);
                } else {
                    if (!getRdbtnPchart().isSelected() & getRdbtnRchart().isSelected()) {
                        createChart("Ring-Chart", "Anzeigefonds");


                    } else if (!getRdbtnRchart().isSelected() & getRdbtnPchart().isSelected()) {
                        createChart("Pie-Chart", "");
                    } else {
                        JOptionPane.showMessageDialog(null, "Es muss genau ein Diagrammtyp ausgewählt sein!",
                                "Fehlermeldung", JOptionPane.PLAIN_MESSAGE);
                    }
                }
            }
        });
        btnShowDia.setBounds(10, 220, 110, 22);
        return btnShowDia;
    }

    private void createChart(String chartName, String title) {
        DefaultPieDataset pieDataset = new DefaultPieDataset();
        Chart chart = (Chart) getListChart().getModel()
                .getElementAt(getListChart().getSelectedIndex());
        for (ChartData chartData : chart.getData()) {
            pieDataset.setValue(chartData.getName(), chartData.getWeight());
        }

        JFreeChart freeChart = ChartFactory.createPieChart(title, pieDataset, false, true, false);


        final PiePlot plot = (PiePlot) freeChart.getPlot();
        plot.setBackgroundPaint(Color.WHITE);

        plot.setBaseSectionOutlinePaint(Color.WHITE);
        plot.setShadowPaint(null);
        plot.setSectionOutlinesVisible(true);
        plot.setBaseSectionOutlineStroke(new BasicStroke(2.0f));

        plot.setInteriorGap(0.0);
        plot.setLabelGenerator(null);

        // new Color(10, 10, 10);

        ChartFrame frame = new ChartFrame(chartName, freeChart);
        frame.setVisible(true);
        frame.setSize(450, 500);
    }

    private JButton createSaveButton() {
        JButton btnSave = new JButton("Speichern in DB");
        btnSave.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                readChartFile.setInputFile(getTxtFieldPath().getText());

                try {
                    try {
                        chartDAOService.saveChart(readChartFile.readXLSFile());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    JOptionPane.showMessageDialog(null, "Datei erfolgreich in Datenbank gespeichert.", "Info", JOptionPane.PLAIN_MESSAGE);
                    updateList();
                } catch (BiffException e) {
                    JOptionPane.showMessageDialog(null, e.getMessage(), "Fehlermeldung", JOptionPane.PLAIN_MESSAGE);
                }
                /*
                try {
                    chartDAOService.saveChart(readChartFile.readCSVFile());
                    JOptionPane.showMessageDialog(null, "Datei erfolgreich in Datenbank gespeichert.", "Info", JOptionPane.PLAIN_MESSAGE);
                    updateList();
                } catch (FileNotFoundException | ParseException e) {
                    JOptionPane.showMessageDialog(null, e.getMessage(), "Fehlermeldung", JOptionPane.PLAIN_MESSAGE);
                }
                */
            }
        });
        btnSave.setBounds(200, 190, 150, 22);
        return btnSave;
    }

    private JButton createFileReadButton() {
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
        return btnPath;
    }

    protected void updateList() {
        ((DefaultListModel) getListChart().getModel()).removeAllElements();
        List<Chart> charts = chartDAOService.loadAllCharts();
        if (!charts.isEmpty()) {
            for (Chart chart : charts) {
                ((DefaultListModel<Chart>) this.listChart.getModel()).addElement(chart);
            }
        }
    }

    public JList getListChart() {
        return listChart;
    }

    public JList<ChartData> getListData() {
        return listData;
    }

    public JTextField getTxtFieldPath() {
        return txtFieldPath;
    }

    public JRadioButton getRdbtnRchart() {
        return rdbtnRchart;
    }

    public JRadioButton getRdbtnPchart() {
        return rdbtnPchart;
    }

}
