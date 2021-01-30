package client;

import io.grpc.health.MedicationPlanMessage;

import javax.swing.Timer;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.*;
import java.util.stream.Collectors;

public class UserInterface extends JFrame {

    private static final int HEIGHT = 500;
    private static final int WIDTH = 600;

    private static final long TEN_SECONDS = 10 * 1000;
    private static final long TWENTY_FOUR_HOURS = 24 * 60 * 60 * 1000;
    private static final long ONE_HOUR = 60 * 60 * 1000;

    private static final int PORT = 23970;
    private static final String HOST = "localhost";
    private static final int PATIENT_ID = 55;

    private static HealthGrpcClient healthGrpcClient = new HealthGrpcClient(HOST, PORT);

    private JPanel mainPanel;
    private JPanel medicationPlanPanel;
    private JTabbedPane jTabbedPane;

    private JLabel clockLabel;

    private JTable jTable;
    private JScrollPane jScrollPane;

    private boolean[] medicationNotTaken;
    private List<MedicationPlanMessage> medicationPlanMessageList = new ArrayList<>();

    public UserInterface() {
        this.setSize(WIDTH, HEIGHT);
        this.setResizable(false);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setVisible(true);

        mainPanel = new JPanel();
        medicationPlanPanel = new JPanel();
        jTabbedPane = new JTabbedPane(JTabbedPane.LEFT);

        jTabbedPane.addTab("Med plan", null, medicationPlanPanel);

        jTabbedPane.setVisible(true);

        mainPanel.add(jTabbedPane);
        mainPanel.setVisible(true);
        medicationPlanPanel.setVisible(true);
        this.add(mainPanel);

        mainPanel.setPreferredSize(new Dimension(575, 475));
        medicationPlanPanel.setPreferredSize(new Dimension(450, 425));

        jTable = new JTable();
        jScrollPane = new JScrollPane(jTable);
        medicationPlanPanel.add(jScrollPane);

        this.setLocation(0, 0);

        setupClock();
        setupCronjobs();
        updateTable(new ArrayList<>());

        repaint();
    }

    private void setupClock() {
        clockLabel = new JLabel();
        Timer timer = new Timer(1000, event -> {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            clockLabel.setText(format.format(new Date()));
        });

        timer.start();

        medicationPlanPanel.add(clockLabel);
        clockLabel.setVisible(true);
        repaint();
    }

    private void setupCronjobs() {

        Calendar dateNow = Calendar.getInstance();
        dateNow.set(Calendar.HOUR_OF_DAY, 19);
        dateNow.set(Calendar.MINUTE, 0);
        dateNow.set(Calendar.SECOND, 10);

        java.util.Timer timerTimeOfDay = new java.util.Timer();
        timerTimeOfDay.scheduleAtFixedRate(
                new TimerTaskCronGetMedicationPlans(),
                dateNow.getTime(),
                TWENTY_FOUR_HOURS
        );

        java.util.Timer timerFrequent = new java.util.Timer();
        timerFrequent.scheduleAtFixedRate(
                new TimerTaskUpdateButtonStatus(),
                new Date(),
                TEN_SECONDS / 10
        );
    }

    private void updateTable(List<MedicationPlanMessage> medicationPlanMessageList) {
        medicationPlanMessageList.forEach(System.out::println);

        String[] columnNames = {"startDate", "endDate", "hour", "name", "dosage", "action"};
        String[][] values = new String[medicationPlanMessageList.size()][6];

        for (int count = 0; count < medicationPlanMessageList.size(); count++) {
            values[count][0] = "" + medicationPlanMessageList.get(count).getStartDate();
            values[count][1] = "" + medicationPlanMessageList.get(count).getEndDate();
            values[count][2] = "" + medicationPlanMessageList.get(count).getHourInDay() / 60 + ":" + medicationPlanMessageList.get(count).getHourInDay() % 60;
            values[count][3] = "" + medicationPlanMessageList.get(count).getName();
            values[count][4] = "" + medicationPlanMessageList.get(count).getDosage();
            values[count][5] = "Taken";

        }

        medicationPlanPanel.remove(jTable);
        medicationPlanPanel.remove(jScrollPane);
        DefaultTableModel model = new DefaultTableModel(values, columnNames);
        jTable = new JTable(model);
        jTable.setVisible(true);
        jScrollPane = new JScrollPane(jTable);
        jScrollPane.setVisible(true);
        medicationPlanPanel.add(jScrollPane);

        Action pressButton = new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                JTable table = (JTable) e.getSource();
                int modelRow = Integer.valueOf(e.getActionCommand());
                String hourInDay = (String) table.getModel().getValueAt(modelRow, 2);

                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                try {
                    Date date = format.parse(new java.sql.Date(new Date().getTime()).toString() + " " + hourInDay);

                    Date nowDate = new Date();

                    if (Math.abs(date.getTime() - nowDate.getTime()) < ONE_HOUR) {
                        format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        healthGrpcClient.receiveMessage(PATIENT_ID, "true", table.getModel().getValueAt(modelRow, 3).toString());
                        medicationNotTaken[modelRow] = true;
                        ((DefaultTableModel) table.getModel()).removeRow(modelRow);
                    }
                } catch (ParseException e1) {
                    e1.printStackTrace();
                }

            }
        };

        ButtonColumn buttonColumn = new ButtonColumn(jTable, pressButton, 5);
        buttonColumn.setMnemonic(KeyEvent.VK_D);

        this.medicationNotTaken = new boolean[medicationPlanMessageList.size()];
        this.medicationPlanMessageList = medicationPlanMessageList;

        repaint();
    }

    private void checkMedicationNotTaken() {
        for (int count = 0; count < medicationPlanMessageList.size(); count++) {

            String hourInDay = medicationPlanMessageList.get(count).getHourInDay() / 60 + ":" + medicationPlanMessageList.get(count).getHourInDay() % 60;
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            try {
                Date date = format.parse(new java.sql.Date(new Date().getTime()).toString() + " " + hourInDay);
                Date nowDate = new Date();
                if (nowDate.getTime() - date.getTime() > ONE_HOUR && !medicationNotTaken[count]) {
                    medicationNotTaken[count] = true;
                    healthGrpcClient.receiveMessage(PATIENT_ID, "false", medicationPlanMessageList.get(count).getName());
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }

    private class TimerTaskCronGetMedicationPlans extends TimerTask {

        @Override
        public void run() {
            List<MedicationPlanMessage> medicationPlanMessageList = healthGrpcClient.streamMedicationPlans(PATIENT_ID);

            updateTable(
                    medicationPlanMessageList
                            .stream()
                            .filter(medicationPlanMessage -> {
                                try {
                                    DateFormat format = new SimpleDateFormat("yyyy-MM-dd");

                                    Date startDate = format.parse(medicationPlanMessage.getStartDate());
                                    Date endDate = format.parse(medicationPlanMessage.getEndDate());
                                    Date nowDate = new Date();

                                    return startDate.getTime() <= nowDate.getTime() && nowDate.getTime() <= endDate.getTime();
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                    return false;
                                }
                            })
                            .collect(Collectors.toList())
            );
        }
    }

    private class TimerTaskUpdateButtonStatus extends TimerTask {

        @Override
        public void run() {
            checkMedicationNotTaken();
        }
    }
}
