package dcc.agent.server.service.swget.multithread;

/**
 * Created by teo on 16/07/15.
 */
public enum Endpoints {

    dbpedia("http://dbpedia.org/sparql"),
    dblprkbexplorer("http://dblp.rkbexplorer.com/sparql/"),
    dblp("http://dblp.l3s.de/d2r/sparql"),
    freebase("http://freebases.cloudapp.net/sparql"),
    yago("http://lod2.openlinksw.com/sparql"),
    geonames("http://geonames.cloudapp.net/sparql");
    private String url;
    private Endpoints(String url) {
        this.url=url;
    }
    public String getEndpoints(){
        return  url;
    }
}
