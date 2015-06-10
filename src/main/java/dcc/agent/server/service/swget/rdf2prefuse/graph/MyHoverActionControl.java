/*  1:   */
package dcc.agent.server.service.swget.rdf2prefuse.graph;
/*  2:   */ 
/*  3:   */

import prefuse.controls.HoverActionControl;
import prefuse.visual.VisualItem;

import java.awt.event.MouseEvent;


public class MyHoverActionControl
        extends HoverActionControl {
    private GraphDisplay gd;


    public MyHoverActionControl(GraphDisplay gd) {

        super("__myHover");

        this.gd = gd;

    }


    public void itemEntered(VisualItem item, MouseEvent evt) {

        this.gd.fireHoverStateChanged(item.getString("uri"), true);

    }


    public void itemExited(VisualItem item, MouseEvent evt) {

        this.gd.fireHoverStateChanged("", false);

    }

}
