package dcc.agent.server.service.swget.rdf2prefuse.graph;


import dcc.agent.server.service.swget.rdf2prefuse.Constants;
import prefuse.Visualization;
import prefuse.action.assignment.ColorAction;
import prefuse.visual.VisualItem;


public class NodeColorAction
        extends ColorAction {

    public NodeColorAction(String p_group, Visualization p_vis) {

        super(p_group, VisualItem.FILLCOLOR);

        this.m_vis = p_vis;

    }


    public int getColor(VisualItem p_item) {

        int retval = Constants.NODE_DEFAULT_COLOR;

        if (this.m_vis.isInGroup(p_item, Visualization.SEARCH_ITEMS)) {

            retval = Constants.NODE_COLOR_SEARCH;

        } else if (p_item.isHighlighted()) {

            retval = Constants.NODE_COLOR_HIGHLIGHTED;

        } else if (p_item.isFixed()) {

            retval = Constants.NODE_COLOR_SELECTED;

        } else if (p_item.getString("ending").equals("yes")) {

            retval = Constants.ENDING_NODE_DEFAULT_COLOR;

        } else if (p_item.getString("starting").equals("yes")) {

            retval = Constants.STARTING_NODE_DEFAULT_COLOR;

        }

        return retval;

    }

}

