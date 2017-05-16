/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import ajpassignment.*;
import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Wee Kiat
 */
public class MainForm extends javax.swing.JFrame {

    private ArrayList<BusStop> BusStopResult;
    private ArrayList<Bus> BusResult;
    private List<BusStop> AllBusStops;
    private List<Bus> AllBuses;
    
    private BusStop StartLocation;
    private BusStop EndLocation;
    private ArrayList<BusStopPathCollection> Routes;
    
    /**
     * Creates new form MainForm2
     */
    public MainForm() {
        initComponents();
        StartLocation = null;
        EndLocation = null;
        setLocationRelativeTo(null);
    }

    private void SelfCreateTab(int index,String Title,String ImagePath)
    {
        javax.swing.JPanel pane = new javax.swing.JPanel();
        pane.setBackground(new Color(0,0,0,0));
        pane.setLayout(new javax.swing.BoxLayout(pane, javax.swing.BoxLayout.LINE_AXIS));
        pane.setMinimumSize(new Dimension(130, 75));
        pane.setPreferredSize(new Dimension(130, 75));
        pane.setMaximumSize(new Dimension(130, 75));
    
        javax.swing.JLabel lbl = new javax.swing.JLabel(Title);
        javax.swing.Icon icon = new javax.swing.ImageIcon(getClass().getResource(ImagePath));
        lbl.setIcon(icon);

        lbl.setIconTextGap(5);
        lbl.setHorizontalTextPosition(javax.swing.JLabel.TRAILING);
        pane.add(lbl);
        jTabbedPane1.setTabComponentAt(index, pane);
    }
    
    private void ShowAllBusAndBusStops()
    {
        AllBusStops = BusService.get().GetAllBusStop();
        AllBuses = BusService.get().GetAllBuses();
        DefaultTableModel model = (DefaultTableModel)TableBus.getModel();
        for(Bus b : AllBuses){
            model.addRow(new Object[]{ b.GetBusCode() });
        }
        model = (DefaultTableModel)TableBusStops.getModel();
        for(BusStop bs : AllBusStops){
            model.addRow(new Object[]{ bs.GetBusStopCode(),bs.GetBusStopDesc(),
            bs.GetRoadDesc()});
        }
    }
    
    private void RefreshSearchResult()
    {
        String SearchText = tBSearch.getText();
        BusStopResult = BusService.get().SearchBusStop(SearchText);
        DefaultTableModel model = (DefaultTableModel)TableBusStopResult.getModel();
        model.setRowCount(0);
        for(BusStop bs : BusStopResult){
            model.addRow(new Object[]{ bs.GetBusStopCode(), bs.GetBusStopDesc(),bs.GetRoadDesc()});
        }
        
        BusResult = BusService.get().SearchBus(SearchText);
        model = (DefaultTableModel)TableBusResult.getModel();
        model.setRowCount(0);
        for(Bus b : BusResult){
            model.addRow(new Object[]{ b.GetBusCode() });
        }
    }
    
    private void ShowResultBusStop(int Row)
    {
        BusStopInfoForm ui = new BusStopInfoForm(this,BusStopResult.get(Row));
        ui.setVisible(true);
    }
    
    private void ShowResultBus(int Row)
    {
        BusInfoForm ui = new BusInfoForm(this,BusResult.get(Row));
        ui.setVisible(true);
    }
    
    private void ShowBusStop(int Row)
    {
        BusStopInfoForm ui = new BusStopInfoForm(this,AllBusStops.get(Row));
        ui.setVisible(true);
    }
    
    private void ShowBus(int Row)
    {
        BusInfoForm ui = new BusInfoForm(this,AllBuses.get(Row));
        ui.setVisible(true);
    }
    
    private void ShowRoute(int Row)
    {
        if (Row >= Routes.size()){
            return;
        }
        BusStopPathCollection path = Routes.get(Row);
        DefaultTableModel model = (DefaultTableModel)TableRouteInfo.getModel();
        model.setRowCount(0);
        int Index = 1;
        for(BusStopPath p : path.GetPath()){
            BusStop src = p.GetSrc();
            model.addRow(new Object[]{
                Index++,
                src.GetBusStopCode(),
                src.GetBusStopDesc(),
                src.GetRoadDesc(),
                p.GetBus().GetBusCode()});
            
            if (path.GetPath().get(path.GetPath().size() - 1) == p){
                src = p.GetDest();
                model.addRow(new Object[]{
                    Index++,
                    src.GetBusStopCode(),
                    src.GetBusStopDesc(),
                    src.GetRoadDesc(),
                    p.GetBus().GetBusCode()});
            }
        }
    }
    
    private void SearchRoute()
    {
        if (StartLocation == null || EndLocation == null)
            return;
        Routes = BusService.get().GeneratePath(StartLocation, EndLocation);
        DefaultTableModel model = (DefaultTableModel)TableRouteResult.getModel();
        model.setRowCount(0);
        int Count = 1;
        for(BusStopPathCollection c : Routes){
            model.addRow(new Object[]{ Count++ + " - " + c.toString() });
        }
        if (Routes.size() > 0){
            TableRouteResult.setRowSelectionInterval(0, 0);
            ShowRoute(0);
        }else{
            model = (DefaultTableModel)TableRouteInfo.getModel();
            model.setRowCount(0);
            JOptionPane.showMessageDialog(null, "Fail to find possible routes","Error",JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        tBSearch = new javax.swing.JTextField();
        jSeparator1 = new javax.swing.JSeparator();
        jTabbedPane2 = new javax.swing.JTabbedPane();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        TableBusResult = new javax.swing.JTable();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        TableBusStopResult = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        TableBus = new javax.swing.JTable();
        jLabel4 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        TableBusStops = new javax.swing.JTable();
        jLabel3 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        tBStartLoc = new javax.swing.JTextField();
        bStartLocBrowse = new javax.swing.JButton();
        tBEndLoc = new javax.swing.JTextField();
        bEndLocBrowse = new javax.swing.JButton();
        jScrollPane5 = new javax.swing.JScrollPane();
        TableRouteResult = new javax.swing.JTable();
        jScrollPane6 = new javax.swing.JScrollPane();
        TableRouteInfo = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Bus Service Browser");
        setMinimumSize(new java.awt.Dimension(751, 505));
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                formComponentShown(evt);
            }
        });

        jTabbedPane1.setTabPlacement(javax.swing.JTabbedPane.LEFT);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        tBSearch.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        tBSearch.setBorder(null);

        TableBusResult.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Bus Number"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(TableBusResult);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 825, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 405, Short.MAX_VALUE)
        );

        jTabbedPane2.addTab("Buses", new javax.swing.ImageIcon(getClass().getResource("/ui/images/BusIcon16.png")), jPanel4); // NOI18N

        TableBusStopResult.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Bus Stop Number", "Bus Stop Description", "Road Description"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(TableBusStopResult);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 825, Short.MAX_VALUE)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 405, Short.MAX_VALUE)
        );

        jTabbedPane2.addTab("Bus Stops", new javax.swing.ImageIcon(getClass().getResource("/ui/images/BusStops16.png")), jPanel5); // NOI18N

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ui/images/Search-25.png"))); // NOI18N

        jLabel2.setText("Tip: Double click for more info");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane2)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(tBSearch)
                    .addComponent(jSeparator1))
                .addContainerGap())
            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 28, Short.MAX_VALUE)
                    .addComponent(tBSearch))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTabbedPane2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2))
        );

        jTabbedPane2.getAccessibleContext().setAccessibleName("Buses");

        jTabbedPane1.addTab("Search", new javax.swing.ImageIcon(getClass().getResource("/ui/images/MagnifyingGlass.png")), jPanel1); // NOI18N

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        TableBus.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Bus Number"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane3.setViewportView(TableBus);

        jLabel4.setText("Tip: Double click for more info");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, 830, Short.MAX_VALUE)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 830, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 496, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel4))
        );

        jTabbedPane1.addTab("Buses", new javax.swing.ImageIcon(getClass().getResource("/ui/images/BusIcon64.png")), jPanel2); // NOI18N

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        TableBusStops.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Bus Stop Number", "Bus Stop Description", "Road Description"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane4.setViewportView(TableBusStops);

        jLabel3.setText("Tip: Double click for more info");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 830, Short.MAX_VALUE)
            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, 830, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jScrollPane4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3))
        );

        jTabbedPane1.addTab("Bus Stops", new javax.swing.ImageIcon(getClass().getResource("/ui/images/BusStops.png")), jPanel3); // NOI18N

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));

        jLabel5.setText("Start Location :");

        jLabel6.setText("End Location :");

        tBStartLoc.setEditable(false);

        bStartLocBrowse.setText("Browse...");
        bStartLocBrowse.setToolTipText("");
        bStartLocBrowse.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bStartLocBrowseActionPerformed(evt);
            }
        });

        tBEndLoc.setEditable(false);

        bEndLocBrowse.setText("Browse...");
        bEndLocBrowse.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bEndLocBrowseActionPerformed(evt);
            }
        });

        TableRouteResult.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Route"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane5.setViewportView(TableRouteResult);

        TableRouteInfo.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Sequence", "Bus Stop Code", "Bus Stop Desc", "Road Desc", "Bus To Take"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane6.setViewportView(TableRouteInfo);

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel5)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(tBStartLoc, javax.swing.GroupLayout.DEFAULT_SIZE, 647, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(bStartLocBrowse))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(tBEndLoc)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(bEndLocBrowse)))
                .addContainerGap())
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane6))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(tBStartLoc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(bStartLocBrowse))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tBEndLoc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(bEndLocBrowse)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 447, Short.MAX_VALUE)
                    .addComponent(jScrollPane6)))
        );

        jLabel6.getAccessibleContext().setAccessibleName("End Location :");

        jTabbedPane1.addTab("Find Route", new javax.swing.ImageIcon(getClass().getResource("/ui/images/Compass.png")), jPanel6); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentShown
        SelfCreateTab(0,"Search","/ui/images/MagnifyingGlass.png");
        SelfCreateTab(1,"Buses","/ui/images/BusIcon64.png");
        SelfCreateTab(2,"Bus Stops","/ui/images/BusStops.png");
        SelfCreateTab(3,"Find Route","/ui/images/Compass.png");
        tBSearch.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                RefreshSearchResult();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                RefreshSearchResult();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
            }
        });
        TableBusStopResult.addMouseListener(new TableClickEvent(true,(Integer row) -> {
            ShowResultBusStop(row);
            return null;
        }));
        TableBusResult.addMouseListener(new TableClickEvent(true,(Integer row) -> {
            ShowResultBus(row);
            return null;
        }));
        TableBusStops.addMouseListener(new TableClickEvent(true,(Integer row) -> {
            ShowBusStop(row);
            return null;
        }));
        TableBus.addMouseListener(new TableClickEvent(true,(Integer row) -> {
            ShowBus(row);
            return null;
        }));
        TableRouteResult.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

            @Override
            public void valueChanged(ListSelectionEvent lse) {
                if (!lse.getValueIsAdjusting()) {
                    ShowRoute(TableRouteResult.getSelectedRow());
                }
            }
        });
        RefreshSearchResult();
        ShowAllBusAndBusStops();
    }//GEN-LAST:event_formComponentShown

    private void bStartLocBrowseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bStartLocBrowseActionPerformed
        boolean Success = false;
        do{
            BusStopBrowserForm browser = new BusStopBrowserForm(this);
            browser.setVisible(true);
            StartLocation = browser.GetSelection();
            browser.dispose();
            if (StartLocation != null){
                 if (EndLocation != null && EndLocation == StartLocation){
                    JOptionPane.showMessageDialog(rootPane, "Start location cannot be the same as destination","Error",JOptionPane.ERROR_MESSAGE);
                }else{
                     tBStartLoc.setText(StartLocation.toString());
                     Success = true;
                     SearchRoute();
                }
            }else{
                Success = true;
            }
        }while(!Success);
    }//GEN-LAST:event_bStartLocBrowseActionPerformed

    private void bEndLocBrowseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bEndLocBrowseActionPerformed
        boolean Success = false;
        do{
            BusStopBrowserForm browser = new BusStopBrowserForm(this);
            browser.setVisible(true);
            EndLocation = browser.GetSelection();
            browser.dispose();
            if (EndLocation != null){
                if (StartLocation != null && StartLocation == EndLocation){
                    JOptionPane.showMessageDialog(rootPane, "Destination cannot be the same as start location","Error",JOptionPane.ERROR_MESSAGE);
                }else{
                    tBEndLoc.setText(EndLocation.toString());
                    Success = true;
                    SearchRoute();
                }
            }else{
                Success = true;
            }
        }while(!Success);
        
    }//GEN-LAST:event_bEndLocBrowseActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MainForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainForm().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable TableBus;
    private javax.swing.JTable TableBusResult;
    private javax.swing.JTable TableBusStopResult;
    private javax.swing.JTable TableBusStops;
    private javax.swing.JTable TableRouteInfo;
    private javax.swing.JTable TableRouteResult;
    private javax.swing.JButton bEndLocBrowse;
    private javax.swing.JButton bStartLocBrowse;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTabbedPane jTabbedPane2;
    private javax.swing.JTextField tBEndLoc;
    private javax.swing.JTextField tBSearch;
    private javax.swing.JTextField tBStartLoc;
    // End of variables declaration//GEN-END:variables
}
