package dcc.agent.server.service.swget.rdf2prefuse.graph;


import prefuse.Visualization;
import prefuse.action.Action;
import prefuse.data.tuple.TupleSet;
import prefuse.visual.DecoratorItem;
import prefuse.visual.VisualItem;

import java.util.Iterator;

public class HideNodeDecoratorAction
        extends Action {

    public HideNodeDecoratorAction(Visualization p_vis) {

        this.m_vis = p_vis;

    }


    public void run(double frac) {

        TupleSet decorators = this.m_vis.getGroup("nodeDecorators");

        if (decorators != null) {

            Iterator<DecoratorItem> it = decorators.tuples();

            while (it.hasNext()) {

                DecoratorItem decorator = (DecoratorItem) it.next();

                VisualItem node = decorator.getDecoratedItem();

                decorator.setVisible(node.isVisible());

            }

        }

    }

}



