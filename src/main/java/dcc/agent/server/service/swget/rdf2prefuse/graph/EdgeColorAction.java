package dcc.agent.server.service.swget.rdf2prefuse.graph;


import dcc.agent.server.service.swget.rdf2prefuse.Constants;
import prefuse.Visualization;
import prefuse.action.assignment.ColorAction;
import prefuse.visual.VisualItem;

public class EdgeColorAction
        extends ColorAction {

    public EdgeColorAction(String p_group, Visualization p_vis) {

        super(p_group, VisualItem.STROKECOLOR);

        this.m_vis = p_vis;

    }
    public int getColor(VisualItem p_item) {
        int retval = Constants.EDGE_DEFAULT_COLOR;

        if (this.m_vis.isInGroup(p_item, Visualization.SEARCH_ITEMS)) {

            retval = Constants.EDGE_COLOR_SEARCH;

        }

        return retval;

    }

}


