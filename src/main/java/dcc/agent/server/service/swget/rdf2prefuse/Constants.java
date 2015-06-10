package dcc.agent.server.service.swget.rdf2prefuse;

import prefuse.util.ColorLib;

import java.awt.*;

public final class Constants
{
  public static final int NODE_COLOR_SELECTED = ColorLib.rgb(100, 255, 100);
  public static final int NODE_COLOR_HIGHLIGHTED = ColorLib.rgb(250, 250, 126);
  public static final int NODE_COLOR_SEARCH = ColorLib.rgb(255, 120, 50);
  public static final int EDGE_COLOR_SEARCH = ColorLib.rgb(255, 0, 0);
  public static final int EDGE_DEFAULT_COLOR = ColorLib.rgb(105, 105, 105);
  public static final int NODE_DEFAULT_COLOR = ColorLib.rgb(100, 160, 255);
  public static final int STARTING_NODE_DEFAULT_COLOR = ColorLib.rgb(255, 0, 0);
  public static final int ENDING_NODE_DEFAULT_COLOR = ColorLib.rgb(138, 43, 226);
  public static final String GRAPH = "graph";
  public static final String GRAPH_NODES = "graph.nodes";
  public static final String GRAPH_EDGES = "graph.edges";
  public static final String EDGE_DECORATORS = "edgeDecorators";
  public static final String NODE_DECORATORS = "nodeDecorators";
  public static final String TREE_NODE_LABEL = "name";
  public static final String TREE_EDGE = "label";
  public static final Color BACKGROUND = Color.WHITE;
  public static final Color FOREGROUND = Color.BLACK;
}
