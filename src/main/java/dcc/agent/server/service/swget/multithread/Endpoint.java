package dcc.agent.server.service.swget.multithread;

/**
 * Created by teo on 16/07/15.
 */
public enum Endpoint {

    DBPEDIA("http://dbpedia.org/sparql","dbpedia.org","http://agentserver.herokuapp.com/"),
    DBLP("http://dblp.l3s.de/d2r/sparql","dblp.l3s.de","http://dblps.cloudapp.net"),
    FREEBASE("http://freebases.cloudapp.net/sparql","rdf.freebase.com","http://freebases.cloudapp.net"),
    YAGO("http://lod2.openlinksw.com/sparql","yago-knowledge.org","http://yagos.cloudapp.net"),
    GEONAMES("http://geonames.cloudapp.net:8890/sparql","sws.geonames.org","http://geonames.cloudapp.net");

    private String sparql;
    private String graph;
    private String host;
    private Endpoint(String sparql,String graph,String host) {
        this.sparql=sparql;
        this.graph=graph;
        this.host=host;
    }
    public String getSparql(){
        return  sparql;
    }
    public String getGraph(){
        return  graph;
    }
    public String getHost(){
        return  host;
    }
}
