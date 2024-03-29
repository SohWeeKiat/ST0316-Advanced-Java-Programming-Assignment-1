/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import ajpassignment.*;
import java.util.ArrayList;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Wee Kiat
 */
public class BusStopBrowserForm extends javax.swing.JDialog {

    /**
     * Creates new form BusStopBrowserForm
     */
    private BusStop Selection;
    private ArrayList<BusStop> bus_stops;
    
    public BusStopBrowserForm(javax.swing.JFrame owner) {
        super(owner,true);
        initComponents();
        
        Selection = null;
        setLocationRelativeTo(null);
        
        bus_stops = BusService.get().GetAllBusStop();
        DefaultTableModel model = (DefaultTableModel)TableBusStops.getModel();
        for(BusStop bs : bus_stops){
            model.addRow(new Object[]{ bs.GetBusStopCode(),bs.GetBusStopDesc(),bs.GetRoadDesc()});
        }
        
        TableBusStops.addMouseListener(new TableClickEvent(true, (Integer row) ->{
            Selection = bus_stops.get(row);
            setVisible(false);
            return null;
        }));
        
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
    }
    
    private void RefreshSearchResult()
    {
        String SearchText = tBSearch.getText();
        bus_stops = BusService.get().SearchBusStop(SearchText);
        DefaultTableModel model = (DefaultTableModel)TableBusStops.getModel();
        model.setRowCount(0);
        for(BusStop bs : bus_stops){
            model.addRow(new Object[]{ bs.GetBusStopCode(), bs.GetBusStopDesc(),bs.GetRoadDesc()});
        }
    }
    
    public BusStop GetSelection()
    {
        return Selection;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        tBSearch = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        TableBusStops = new javax.swing.JTable();

        setTitle("Choose a bus stop...");

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ui/images/Search-25.png"))); // NOI18N

        tBSearch.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        TableBusStops.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Bus Stop Code", "Bus Stop Desc", "Road Desc"
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
        jScrollPane1.setViewportView(TableBusStops);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(0, 10, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tBSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 604, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addComponent(jScrollPane1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(tBSearch))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 438, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable TableBusStops;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField tBSearch;
    // End of variables declaration//GEN-END:variables
}
