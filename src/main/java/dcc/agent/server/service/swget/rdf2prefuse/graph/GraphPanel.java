/*   1:    */
package dcc.agent.server.service.swget.rdf2prefuse.graph;
/*   2:    */ 
/*   3:    */

import dcc.agent.server.service.swget.gui.SwgetGUI;
import prefuse.action.filter.GraphDistanceFilter;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Collections;
import java.util.LinkedList;


public class GraphPanel extends JPanel implements ChangeListener {
    private GraphDisplay m_display;
    private SwgetGUI gui;
    private JSpinner m_spinner;
    private JPanel filterPanel = new JPanel(new FlowLayout());
    private JPanel uriSelectPanel = new JPanel(new FlowLayout());
    private JPanel distancePanel = new JPanel(new FlowLayout());
    private JPanel predPanel = new JPanel(new FlowLayout());
    private JPanel nodeSearchPanel;
    private JPanel edgeSearchPanel;
    private RDFGraphConverter graphConv;


    public GraphPanel(SwgetGUI gui) {

        super(new BorderLayout());

        this.gui = gui;

    }


    public GraphPanel(GraphDisplay p_display, RDFGraphConverter graphConverter, SwgetGUI gui) {

        super(new BorderLayout());

        this.graphConv = graphConverter;

        this.m_display = p_display;

        this.gui = gui;

        initPanel();

    }


    public void refreshPanel(GraphDisplay p_display) {

        remove(this.m_display);

        GraphDisplay old = this.m_display;

        this.m_display = p_display;

        this.filterPanel.remove(this.distancePanel);

        this.filterPanel.remove(this.nodeSearchPanel);

        this.filterPanel.remove(this.edgeSearchPanel);

        add(this.m_display, "Center");

        old.clean();

        createDistancePanel();

        this.filterPanel.add(this.distancePanel);

        this.nodeSearchPanel = this.m_display.getSearchPanelNode();

        this.filterPanel.add(this.nodeSearchPanel);

        this.edgeSearchPanel = this.m_display.getSearchPanelEdge();

        this.filterPanel.add(this.edgeSearchPanel);

        repaint();

        validate();

    }


    private void initPanel() {

        setBackground(this.m_display.getBackground());

        setForeground(this.m_display.getForeground());

        add(this.m_display, "Center");

        this.filterPanel.add(getPredicateFilterWidget());

        add(getUriSelectFilterWidget(), "North");

        createDistancePanel();

        this.filterPanel.add(this.distancePanel);

        this.nodeSearchPanel = this.m_display.getSearchPanelNode();

        this.filterPanel.add(this.nodeSearchPanel);

        this.edgeSearchPanel = this.m_display.getSearchPanelEdge();

        this.filterPanel.add(this.edgeSearchPanel);

        this.filterPanel.setBackground(this.m_display.getBackground());

        this.filterPanel.setForeground(this.m_display.getForeground());

        add(this.filterPanel, "South");

    }


    private JPanel getPredicateFilterWidget() {

        JLabel label = new JLabel("Predicate: ");

        this.predPanel.add(label);

        JComboBox pred = new JComboBox();

        pred.addItem("_");

        LinkedList<String> predS = new LinkedList(this.graphConv
                .getPredicates().keySet());

        Collections.sort(predS);

        for (String s : predS) {

            pred.addItem(s);

        }

        pred.setSelectedIndex(0);

        pred.addItemListener(new PredItemListener(null));

        this.predPanel.add(pred);

        this.predPanel.setBackground(this.m_display.getBackground());

        this.predPanel.setBorder(new TitledBorder("Predicate Filter"));

        return this.predPanel;

    }


    private JPanel getUriSelectFilterWidget() {

        JLabel label = new JLabel("Starting URI: ");

        this.uriSelectPanel.add(label);

        JComboBox uris = new JComboBox();

        LinkedList<String> uriS = new LinkedList(this.graphConv.getUris()
                .keySet());

        Collections.sort(uriS);

        for (String s : uriS) {

            uris.addItem(s);

        }

        uris.setSelectedItem(this.graphConv.getRootUri());

        uris.addItemListener(new UriItemListener(null));

        this.uriSelectPanel.add(uris);

        this.uriSelectPanel.setBackground(this.m_display.getBackground());

        this.uriSelectPanel.setBorder(new TitledBorder("Starting URI"));

        return this.uriSelectPanel;

    }


    private void createDistancePanel() {

        this.distancePanel = new JPanel(new FlowLayout());

        GraphDistanceFilter filter = this.m_display.getDistanceFilter();

        JLabel label = new JLabel("Radius: ");

        SpinnerNumberModel model = new SpinnerNumberModel(filter.getDistance(),
                0, 99, 1);

        this.m_spinner = new JSpinner();

        this.m_spinner.addChangeListener(this);

        this.m_spinner.setModel(model);

        this.distancePanel.add(label);

        this.distancePanel.add(this.m_spinner);

        this.distancePanel.setBackground(this.m_display.getBackground());

        this.distancePanel.setBorder(new TitledBorder("Radius control"));

    }


    public void stateChanged(ChangeEvent e) {

        int value = ((Integer) this.m_spinner.getValue()).intValue();

        GraphDistanceFilter filter = this.m_display.getDistanceFilter();

        filter.setDistance(value);

        filter.run();

        this.m_display.getVisualization().run("draw");

        this.gui.setNodeSizeValue("1");

    }


    public void changeForce(String s) {

        this.m_display.setForceDirectedParameter(Float.parseFloat(s));

    }


    public void changeNodeSize(String s) {

        this.m_display.changeNodeSize(Float.parseFloat(s));

    }


    public void changeLabelSize(String s) {

        this.m_display.changeLabelSize(Float.parseFloat(s));

    }


    public void clear() {

        if (this.m_display != null) {

            this.m_display.clean();

        }

        if (this.graphConv != null) {

            this.graphConv.clean();

        }

        this.graphConv = null;

        this.m_display = null;

        this.m_spinner = null;

        this.filterPanel = null;

        this.uriSelectPanel = null;

        if (this.filterPanel != null) {

            this.filterPanel.remove(this.distancePanel);

            this.filterPanel.remove(this.nodeSearchPanel);

            this.filterPanel.remove(this.edgeSearchPanel);

            remove(this.filterPanel);

            this.filterPanel = null;

        }

        this.distancePanel = null;

        this.predPanel = null;

        this.nodeSearchPanel = null;

        this.edgeSearchPanel = null;

    }


    private class PredItemListener
            implements ItemListener {
        String prevPred = "";


        private PredItemListener(Object object) {
        }


        public void itemStateChanged(ItemEvent evt) {

            JComboBox cb = (JComboBox) evt.getSource();

            String newPred = (String) cb.getSelectedItem();

            if (!newPred.equals(this.prevPred)) {

                this.prevPred = newPred;

                GraphPanel.this.refreshPanel(new GraphDisplay(GraphPanel.this.graphConv.prune(newPred), GraphPanel.this.gui));

                GraphPanel.this.gui.setForceValue("-10");

                GraphPanel.this.gui.setLabelSizeValue("20");

                GraphPanel.this.gui.setNodeSizeValue("1");

            }

        }

    }


    private class UriItemListener implements ItemListener {

        private UriItemListener(Object object) {
        }


        public void itemStateChanged(ItemEvent evt) {

            JComboBox cb = (JComboBox) evt.getSource();

            String newUri = (String) cb.getSelectedItem();

            GraphPanel.this.refreshPanel(new GraphDisplay(GraphPanel.this.graphConv.changeStartingURI(newUri), GraphPanel.this.gui));

            GraphPanel.this.gui.setForceValue("-10");

            GraphPanel.this.gui.setLabelSizeValue("20");

            GraphPanel.this.gui.setNodeSizeValue("1");

        }

    }

}



