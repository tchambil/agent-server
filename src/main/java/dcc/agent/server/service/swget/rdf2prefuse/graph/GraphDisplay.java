/*   1:    */
package dcc.agent.server.service.swget.rdf2prefuse.graph;
/*   2:    */ 
/*   3:    */


import dcc.agent.server.service.swget.rdf2prefuse.Constants;
import prefuse.Display;
import prefuse.Visualization;
import prefuse.action.ActionList;
import prefuse.action.RepaintAction;
import prefuse.action.assignment.ColorAction;
import prefuse.action.assignment.DataSizeAction;
import prefuse.action.filter.GraphDistanceFilter;
import prefuse.action.layout.graph.ForceDirectedLayout;
import prefuse.controls.*;
import prefuse.data.Graph;
import prefuse.data.Schema;
import prefuse.data.Tuple;
import prefuse.data.event.TupleSetListener;
import prefuse.data.tuple.TupleSet;
import prefuse.render.DefaultRendererFactory;
import prefuse.render.EdgeRenderer;
import prefuse.render.LabelRenderer;
import prefuse.util.ColorLib;
import prefuse.util.FontLib;
import prefuse.util.force.ForceSimulator;
import prefuse.util.ui.JSearchPanel;
import prefuse.visual.VisualGraph;
import prefuse.visual.VisualItem;
import prefuse.visual.expression.InGroupPredicate;

import javax.swing.*;


public class GraphDisplay
        extends Display {
    private static final long serialVersionUID = 1L;
    private static Schema DECORATOR_SCHEMA;


    static {

        DECORATOR_SCHEMA.setDefault(VisualItem.INTERACTIVE, false);

        DECORATOR_SCHEMA.setDefault(VisualItem.TEXTCOLOR, ColorLib.gray(0));

        DECORATOR_SCHEMA.setDefault(VisualItem.FONT,
                FontLib.getFont("Tahoma", 20.0D));

    }

    float size = 1.0F;
    DefaultRendererFactory drf;
    private JSearchPanel m_searchNode;
    private JSearchPanel m_searchEdge;
    private GraphDistanceFilter m_filter;
    private FocusControl m_focusControl;
    private ForceDirectedLayout m_fdl;
    private ForceSimulator m_fsim;
    private DragControl dc;
    private PanControl pc;
    private ZoomControl zc;
    private WheelZoomControl wzc;
    private ZoomToFitControl zfc;
    private NeighborHighlightControl nhc;



    public GraphDisplay(Graph p_graph ) {

        super(new Visualization());



        initVisualization(p_graph);

        initDisplay();

        createSearchPanels();

        this.m_vis.run("draw");

    }


    public JSearchPanel getSearchPanelEdge() {

        return this.m_searchEdge;

    }


    private void initVisualization(Graph p_graph) {

        VisualGraph vg = this.m_vis.addGraph("graph", p_graph);


        this.drf = new DefaultRendererFactory();

        this.m_vis.setValue("graph.nodes", null, VisualItem.SHAPE, new Integer(1));

        this.m_vis.setValue("graph.nodes", null, VisualItem.SIZE, Float.valueOf(this.size));

        this.drf.add(new InGroupPredicate("nodeDecorators"), new LabelRenderer("name"));

        this.drf.add(new InGroupPredicate("edgeDecorators"),
                new LabelRenderer("label"));

        this.drf.add(new InGroupPredicate("graph.edges"), new EdgeRenderer(
                0,
                1));

        this.m_vis.setRendererFactory(this.drf);

        this.m_vis.addDecorators("edgeDecorators", "graph.edges",
                DECORATOR_SCHEMA);

        this.m_vis.addDecorators("nodeDecorators", "graph.nodes",
                DECORATOR_SCHEMA);

        this.m_vis.setValue("graph.edges", null, VisualItem.INTERACTIVE,
                Boolean.FALSE);

        VisualItem f = (VisualItem) vg.getNode(0);

        this.m_vis.getGroup(Visualization.FOCUS_ITEMS).setTuple(f);


        f.setFixed(false);

        TupleSet focusGroup = this.m_vis.getGroup(Visualization.FOCUS_ITEMS);

        focusGroup.addTupleSetListener(getFocusedItemsListner());

        this.m_vis.putAction("draw", getDrawActions());

        this.m_vis.putAction("layout", getLayoutActions());

        this.m_vis.runAfter("draw", "layout");

    }


    private void initDisplay() {

        setSize(500, 500);

        setForeground(Constants.FOREGROUND);

        setBackground(Constants.BACKGROUND);

        this.dc = new DragControl();

        addControlListener(this.dc);

        this.pc = new PanControl();

        addControlListener(this.pc);

        this.zc = new ZoomControl();

        addControlListener(this.zc);

        this.wzc = new WheelZoomControl();

        addControlListener(this.wzc);

        this.zfc = new ZoomToFitControl();

        addControlListener(this.zfc);

        this.nhc = new NeighborHighlightControl();

        addControlListener(this.nhc);

        this.m_focusControl = new FocusControl(1);

        addControlListener(this.m_focusControl);

        addControlListener(new MyHoverActionControl(this));

    }


    public FocusControl getFocusControl() {

        return this.m_focusControl;

    }


    private TupleSetListener getFocusedItemsListner() {

        TupleSetListener listner = new TupleSetListener() {

            public void tupleSetChanged(TupleSet ts, Tuple[] add, Tuple[] rem) {

                for (int i = 0; i < rem.length; i++) {

                    ((VisualItem) rem[i]).setFixed(false);

                }

                for (int i = 0; i < add.length; i++) {

                    ((VisualItem) add[i]).setFixed(false);

                    ((VisualItem) add[i]).setFixed(true);

                }

                if (ts.getTupleCount() == 0) {

                    ts.addTuple(rem[0]);

                    ((VisualItem) rem[0]).setFixed(false);

                }

                GraphDisplay.this.m_vis.run("draw");

            }

        };

        return listner;

    }


    private ActionList getDrawActions() {

        ActionList draw = new ActionList();

        this.m_filter = new GraphDistanceFilter("graph", 1);

        draw.add(this.m_filter);

        draw.add(new ColorAction("graph.nodes", VisualItem.TEXTCOLOR,
                ColorLib.rgb(0, 0, 0)));

        draw.add(new ColorAction("graph.nodes", VisualItem.STROKECOLOR,
                ColorLib.gray(0)));

        draw.add(new ColorAction("graph.edges", VisualItem.STROKECOLOR, Constants.EDGE_DEFAULT_COLOR));

        draw.add(new ColorAction("graph.edges", VisualItem.FILLCOLOR,
                Constants.EDGE_DEFAULT_COLOR));


        DataSizeAction nodeDataSizeAction = new DataSizeAction("graph.nodes", VisualItem.SIZE);

        draw.add(nodeDataSizeAction);

        DataSizeAction nodeDataSizeActionLabel = new DataSizeAction("nodeDecorators", VisualItem.FONT);

        draw.add(nodeDataSizeAction);

        return draw;

    }


    public void setForceDirectedParameter(float p_value) {

        this.m_fsim.getForces()[0].setParameter(0, p_value);

    }


    public void changeNodeSize(float size) {

        this.m_vis.setValue("graph.nodes", null, VisualItem.SIZE, Float.valueOf(size));

    }


    public void changeLabelSize(float size) {

        this.m_vis.setValue("nodeDecorators", null, VisualItem.FONT, FontLib.getFont("Tahoma", size));

        this.m_vis.setValue("edgeDecorators", null, VisualItem.FONT, FontLib.getFont("Tahoma", size));

    }


    private ActionList getLayoutActions() {

        this.m_fdl = new ForceDirectedLayout("graph");

        this.m_fsim = this.m_fdl.getForceSimulator();

        this.m_fsim.getForces()[0].setParameter(0, -10.0F);

        ActionList layout = new ActionList(-1L);

        layout.add(this.m_fdl);

        layout.add(new HideDecoratorAction(this.m_vis));

        layout.add(new HideNodeDecoratorAction(this.m_vis));

        layout.add(new RepaintAction());

        layout.add(new NodeColorAction("graph.nodes", this.m_vis));

        layout.add(new LabelLayoutNode("nodeDecorators", this.m_vis));

        layout.add(new LabelLayout("edgeDecorators", this.m_vis));

        layout.add(new EdgeColorAction("graph.edges", this.m_vis));

        return layout;

    }


    public JSearchPanel getSearchPanelNode() {

        return this.m_searchNode;

    }


    private void createSearchPanels() {

        this.m_searchNode = new JSearchPanel(this.m_vis, "graph.nodes",
                Visualization.SEARCH_ITEMS, "name", true,
                true);

        this.m_searchNode.setShowResultCount(true);

        this.m_searchNode.setBorder(BorderFactory.createTitledBorder("Node search"));

        this.m_searchNode.setFont(FontLib.getFont("Tahoma", 0, 11));

        this.m_searchNode.setBackground(Constants.BACKGROUND);

        this.m_searchNode.setForeground(Constants.FOREGROUND);

        this.m_searchEdge = new JSearchPanel(this.m_vis, "graph.edges",
                Visualization.SEARCH_ITEMS, "label", true, true);

        this.m_searchEdge.setShowResultCount(true);

        this.m_searchEdge.setBorder(BorderFactory.createTitledBorder("Edge search"));

        this.m_searchEdge.setFont(FontLib.getFont("Tahoma", 0, 11));

        this.m_searchEdge.setBackground(Constants.BACKGROUND);

        this.m_searchEdge.setForeground(Constants.FOREGROUND);

    }


    public GraphDistanceFilter getDistanceFilter() {

        return this.m_filter;

    }


    public void clean() {

        try {

            this.m_vis.reset();

        } catch (Exception localException) {
        }

        this.m_focusControl.setEnabled(false);

        this.m_focusControl = null;

        this.m_fdl.setEnabled(false);

        this.m_fdl.cancel();

        this.m_fdl = null;

        this.m_filter = null;

        this.m_searchEdge = null;

        this.m_searchNode = null;

        this.m_fsim = null;

        this.dc.setEnabled(false);

        this.dc = null;

        this.pc.setEnabled(false);

        this.pc = null;

        this.zc.setEnabled(false);

        this.zc = null;

        this.wzc.setEnabled(false);

        this.wzc = null;

        this.zfc.setEnabled(false);

        this.zfc = null;

        this.nhc.setEnabled(false);

        this.nhc = null;

        this.drf = null;

        this.m_vis.setRendererFactory(null);

        this.m_bgpainter = null;

        this.m_offscreen = null;

        this.m_queue.clear();

        this.m_queue = null;

        this.m_bounds = null;

        this.m_clip = null;

        this.m_rclip = null;

        this.m_screen = null;

        setVisualization(null);

    }

    protected void fireHoverStateChanged(String uri, boolean entered) {

        if (entered) {



        }

    }

}


