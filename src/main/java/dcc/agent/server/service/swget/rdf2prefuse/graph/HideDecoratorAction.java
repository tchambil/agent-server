/*  1:   */
package dcc.agent.server.service.swget.rdf2prefuse.graph;
/*  2:   */ 
/*  3:   */

import prefuse.Visualization;
import prefuse.action.Action;
import prefuse.data.tuple.TupleSet;
import prefuse.visual.DecoratorItem;
import prefuse.visual.VisualItem;

import java.util.Iterator;


public class HideDecoratorAction extends Action {

    public HideDecoratorAction(Visualization p_vis) {

        this.m_vis = p_vis;

    }


    public void run(double frac) {

        TupleSet decorators = this.m_vis.getGroup("edgeDecorators");

        if (decorators != null) {

            Iterator<DecoratorItem> it = decorators.tuples();

            while (it.hasNext()) {

                DecoratorItem decorator = (DecoratorItem) it.next();

                VisualItem edge = decorator.getDecoratedItem();

                decorator.setVisible((edge.isVisible()) && (edge.isHighlighted()));

            }

        }

    }

}



