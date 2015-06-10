/*   1:    */
package dcc.agent.server.service.swget.rdf2prefuse.graph;
/*   2:    */ 
/*   3:    */

import com.hp.hpl.jena.rdf.model.*;
import dcc.agent.server.service.swget.rdf2prefuse.Converter;
import dcc.agent.server.service.swget.utils.Constants;
import prefuse.data.Edge;
import prefuse.data.Graph;
import prefuse.data.Node;

import java.util.ArrayList;
import java.util.Hashtable;


/*  18:    */ public class RDFGraphConverter
/*  19:    */ extends Converter
/*  20:    */ {
    /*  21:    */   private Graph m_graph;
    /*  22:    */   private ArrayList<String[]> m_edges;
    /*  23:    */   private Hashtable<String, Boolean> predicates;
    /*  24:    */   private Hashtable<String, Boolean> uris;
    /*  25:    */   private String rootUri;
    /*  26:    */   private Hashtable<String, Node> m_nodes;
    /*  27: 52 */   private String uri = null;

    /*  28:    */
/*  29:    */
    public RDFGraphConverter(String p_RDFFile, String uri)
/*  30:    */ {
/*  31: 67 */
        super(p_RDFFile);
/*  32: 68 */
        this.uri = uri;
/*  33: 69 */
        for (int i = 0; i < Constants.namespaces_values.length; i++) {
/*  34: 70 */
            if (this.uri.startsWith(Constants.namespaces_values[i]))
/*  35:    */ {
/*  36: 71 */
                this.uri =
/*  37: 72 */           (Constants.namespaces_keys[i] + this.uri.substring(this.uri
/*  38: 73 */.indexOf(Constants.namespaces_values[i]) +
/*  39: 74 */           Constants.namespaces_values[i].length()));
/*  40: 75 */
                break;
/*  41:    */
            }
/*  42:    */
        }
/*  43: 78 */
        init();
/*  44:    */
    }

    /*  45:    */
/*  46:    */
    public RDFGraphConverter(String p_RDFFile)
/*  47:    */ {
/*  48: 82 */
        super(p_RDFFile);
/*  49: 83 */
        this.uri = null;
/*  50: 84 */
        init();
/*  51:    */
    }


    public RDFGraphConverter(Model p_model, String uri) {

        super(p_model);


        this.uri = uri;

        init();

    }


    private void init() {

        this.m_edges = new ArrayList();

        this.m_nodes = new Hashtable();

        this.predicates = new Hashtable();

        this.uris = new Hashtable();


        createGraph();

    }


    public Graph getGraph() {

        return this.m_graph;

    }


    private void createGraph() {

        this.m_graph = new Graph(true);


        this.m_graph.getNodeTable().addColumn("name", String.class);

        this.m_graph.getNodeTable().addColumn("starting", String.class, "no");

        this.m_graph.getNodeTable().addColumn("ending", String.class, "no");

        this.m_graph.getNodeTable().addColumn("uri", String.class, "no");

        this.m_graph.getEdgeTable().addColumn("label", String.class);

        if (this.uri != null) {

            Resource root = this.m_model.getResource(this.uri);

            this.rootUri = root.getURI();

            if (this.rootUri.endsWith("/")) {

                this.rootUri = this.rootUri.substring(0, this.rootUri.length() - 1);

            }

            String rootName = root.getURI().substring(root.getURI().lastIndexOf('/') + 1);

            this.uris.put(this.rootUri, Boolean.valueOf(true));

            Node rootNode = this.m_graph.addNode();

            rootNode.setString("name", rootName);

            rootNode.setString("uri", this.rootUri);

            this.m_nodes.put(this.rootUri, rootNode);

        }

        buildGraph();

        createEdges();

    }


    private void buildGraph() {

        StmtIterator iter = this.m_model.listStatements();

        while (iter.hasNext()) {

            Statement stmt = iter.nextStatement();


            Resource subject = stmt.getSubject();

            Property predicate = stmt.getPredicate();

            RDFNode object = stmt.getObject();

            String[] edge = new String[3];

            edge[1] = predicate.getLocalName();

            if (subject.isURIResource()) {

                String uri = subject.getURI();

                if (uri.endsWith("/")) {

                    uri = uri.substring(0, uri.length() - 1);

                }

                String uriName = uri.substring(uri.lastIndexOf('/') + 1);

                if (this.m_nodes.get(uri) == null) {

                    Node currNode = this.m_graph.addNode();

                    currNode.setString("name", uriName);

                    currNode.setString("uri", uri);

                    this.m_nodes.put(uri, currNode);

                }

                this.predicates.put(predicate.getLocalName(), Boolean.valueOf(true));

                edge[0] = uri;

                this.uris.put(uri, Boolean.valueOf(true));

            } else {

                if (this.m_nodes.get(subject.toString()) == null) {

                    Node currNode = this.m_graph.addNode();

                    currNode.setString("name", subject.toString());

                    currNode.setString("uri", subject.toString());

                    this.m_nodes.put(subject.toString(), currNode);

                }

                this.predicates.put(predicate.getLocalName(), Boolean.valueOf(true));

                edge[0] = subject.toString();

            }

            if (object.isURIResource()) {

                String uri = ((Resource) object).getURI();

                if (uri.endsWith("/")) {

                    uri = uri.substring(0, uri.length() - 1);

                }

                String uriName = ((Resource) object).getURI().substring(((Resource) object).getURI().lastIndexOf('/') + 1);

                if ((this.m_nodes.get(uri) == null) &&
                        (!uri.equals("http://nmpg.org/ending")) &&
                        (!uri.equals("http://nmpg.org/starting"))) {

                    Node currNode = this.m_graph.addNode();

                    currNode.setString("name", uriName);

                    currNode.setString("uri", uri);

                    this.m_nodes.put(uri, currNode);

                    this.uris.put(uri, Boolean.valueOf(true));

                }

                this.predicates.put(predicate.getLocalName(), Boolean.valueOf(true));

                edge[2] = uri;

            } else {

                if (this.m_nodes.get(object.toString()) == null) {

                    Node currNode = this.m_graph.addNode();

                    currNode.setString("name", object.toString());

                    currNode.setString("uri", object.toString());

                    this.m_nodes.put(object.toString(), currNode);

                }

                this.predicates.put(predicate.getLocalName(), Boolean.valueOf(true));

                edge[2] = object.toString();

            }

            this.m_edges.add(edge);

        }

    }

    public Hashtable<String, Boolean> getUris() {

        return this.uris;

    }


    public String getRootUri() {

        return this.rootUri;

    }


    private void createEdges() {

        for (int i = 0; i < this.m_edges.size(); i++) {

            String[] strEdge = (String[]) this.m_edges.get(i);

            Node source = (Node) this.m_nodes.get(strEdge[0]);

            Node target = (Node) this.m_nodes.get(strEdge[2]);

            if (strEdge[1].equals("type")) {

                if (strEdge[2].equals("http://nmpg.org/ending")) {

                    source.setString("ending", "yes");

                } else if (strEdge[2].equals("http://nmpg.org/starting")) {

                    source.setString("starting", "yes");

                } else {

                    Edge edge = this.m_graph.addEdge(source, target);

                    edge.setString("label", strEdge[1]);

                }

            } else {

                Edge edge = this.m_graph.addEdge(source, target);

                edge.setString("label", strEdge[1]);

            }

        }

    }


    public Hashtable<String, Boolean> getPredicates() {

        return this.predicates;

    }


    public Graph prune(String predicate) {

        Graph prunedGraph = new Graph(true);

        Hashtable<String, Node> nodesPrun = new Hashtable();

        prunedGraph.getNodeTable().addColumn("name", String.class);

        prunedGraph.getNodeTable().addColumn("starting", String.class, "no");

        prunedGraph.getNodeTable().addColumn("ending", String.class, "no");

        prunedGraph.getEdgeTable().addColumn("label", String.class);

        if (this.uri != null) {

            Node root = (Node) this.m_nodes.get(this.uri);

            Node rootNode = prunedGraph.addNode();

            rootNode.setString("name", root.getString("name"));

            rootNode.setString("ending", root.getString("ending"));

            rootNode.setString("starting", root.getString("starting"));

            nodesPrun.put(root.getString("name"), rootNode);

        }

        for (int i = 0; i < this.m_edges.size(); i++) {

            String[] strEdge = (String[]) this.m_edges.get(i);

            Node source = (Node) this.m_nodes.get(strEdge[0]);

            Node target = (Node) this.m_nodes.get(strEdge[2]);

            if ((predicate.equals("_")) || (strEdge[1].equals(predicate))) {

                if (nodesPrun.get(source.getString("name")) == null) {

                    Node currNode = prunedGraph.addNode();

                    currNode.setString("name", source.getString("name"));

                    currNode.setString("ending", source.getString("ending"));

                    currNode.setString("starting", source.getString("starting"));

                    nodesPrun.put(source.getString("name"), currNode);

                }

                if (target != null) {

                    if (nodesPrun.get(target.getString("name")) == null) {

                        Node currNode = prunedGraph.addNode();

                        currNode.setString("name", target.getString("name"));

                        currNode.setString("ending", target.getString("ending"));

                        currNode.setString("starting",
                                target.getString("starting"));

                        nodesPrun.put(target.getString("name"), currNode);

                    }

                    Edge edge = prunedGraph.addEdge(
                            (Node) nodesPrun.get(source.getString("name")),
                            (Node) nodesPrun.get(target.getString("name")));

                    edge.setString("label", strEdge[1]);

                }

            }

        }

        this.m_graph = prunedGraph;


        return prunedGraph;

    }


    public Graph changeStartingURI(String uri) {

        Graph prunedGraph = new Graph(true);

        Hashtable<String, Node> nodesPrun = new Hashtable();

        prunedGraph.getNodeTable().addColumn("name", String.class);

        prunedGraph.getNodeTable().addColumn("starting", String.class, "no");

        prunedGraph.getNodeTable().addColumn("ending", String.class, "no");

        prunedGraph.getEdgeTable().addColumn("label", String.class);

        if (uri != null) {

            Node root = (Node) this.m_nodes.get(uri);

            Node rootNode = prunedGraph.addNode();

            rootNode.setString("name", root.getString("name"));

            rootNode.setString("ending", root.getString("ending"));

            rootNode.setString("starting", root.getString("starting"));

            nodesPrun.put(root.getString("name"), rootNode);

        } else {

            Node root = (Node) this.m_nodes.get(this.rootUri);

            Node rootNode = prunedGraph.addNode();

            rootNode.setString("name", root.getString("name"));

            rootNode.setString("ending", root.getString("ending"));

            rootNode.setString("starting", root.getString("starting"));

            nodesPrun.put(root.getString("name"), rootNode);

        }

        for (int i = 0; i < this.m_edges.size(); i++) {

            String[] strEdge = (String[]) this.m_edges.get(i);

            Node source = (Node) this.m_nodes.get(strEdge[0]);

            Node target = (Node) this.m_nodes.get(strEdge[2]);

            if (nodesPrun.get(source.getString("name")) == null) {

                Node currNode = prunedGraph.addNode();

                currNode.setString("name", source.getString("name"));

                currNode.setString("ending", source.getString("ending"));

                currNode.setString("starting", source.getString("starting"));

                nodesPrun.put(source.getString("name"), currNode);

            }

            if (target != null) {

                if (nodesPrun.get(target.getString("name")) == null) {

                    Node currNode = prunedGraph.addNode();

                    currNode.setString("name", target.getString("name"));

                    currNode.setString("ending", target.getString("ending"));

                    currNode.setString("starting", target.getString("starting"));

                    nodesPrun.put(target.getString("name"), currNode);

                }

                Edge edge = prunedGraph.addEdge(
                        (Node) nodesPrun.get(source.getString("name")),
                        (Node) nodesPrun.get(target.getString("name")));

                edge.setString("label", strEdge[1]);

            }

        }

        this.m_graph = prunedGraph;

        return prunedGraph;

    }


    public void clean() {

        this.m_graph.clear();

        this.m_graph.dispose();

        this.m_graph.removeAllSets();

        this.m_edges = null;

        this.predicates = null;

        this.uris = null;

        this.m_nodes = null;

        this.m_graph = null;

    }

}



