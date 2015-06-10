/*  1:   */
package dcc.agent.server.service.swget.rdf2prefuse.graph;
/*  2:   */ 
/*  3:   */

import prefuse.Visualization;
import prefuse.action.layout.Layout;
import prefuse.visual.DecoratorItem;
import prefuse.visual.VisualItem;

import java.awt.geom.Rectangle2D;
import java.util.Iterator;


class LabelLayoutNode
        extends Layout {

    public LabelLayoutNode(String p_group, Visualization p_vis) {

        super(p_group);

        this.m_vis = p_vis;

    }


    public void run(double frac) {

        Iterator iter = this.m_vis.items(this.m_group);

        while (iter.hasNext()) {

            DecoratorItem item = (DecoratorItem) iter.next();

            VisualItem node = item.getDecoratedItem();

            Rectangle2D bounds = node.getBounds();

            setX(item, null, bounds.getMaxX() + 12.0D);

            setY(item, null, bounds.getMaxY() + 12.0D);

        }

    }

}

