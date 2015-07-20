package dcc.agent.server.service.swget.multithread;

/**
 * Created by teo on 16/07/15.
 */
public enum Endpoint {

    DBPEDIA("http://dbpedia.org/sparql"),
    DBLP("http://dblp.l3s.de/d2r/sparql"),
    FREEBASE("http://freebases.cloudapp.net/sparql"),
    YAGO("http://lod2.openlinksw.com/sparql"),
    GEONAMES("http://geonames.cloudapp.net/sparql"),
    dblprkbexplorer("http://dblp.rkbexplorer.com/sparql/") ;
    private String url;
    private Endpoint(String url) {
        this.url=url;
    }
    public String getEndpoints(){
        return  url;
    }


}
