package dcc.agent.server.controller;


import com.hp.hpl.jena.query.*;

public class exampeJena {
public static void main(String[] args) {

    QueryExecution qe = QueryExecutionFactory.sparqlService(
            "http://n11.degu.cl:3031/ds/query", "SELECT * { \n" +
                    "   <http://dbpedia.org/resource/Peru> ?f ?y\n" +
                    "}");
    String queryString =  "SELECT * { ?x <http://dbpedia.org/ontology/hometown> <http://dbpedia.org/resource/Peru>}";
    ResultSet results = qe.execSelect();
    ResultSetFormatter.out(System.out, results);
    qe.close();

    Query query = QueryFactory.create(queryString);
    System.out.println(queryString);
    QueryExecution qexec = QueryExecutionFactory.sparqlService("http://n11.degu.cl:3031/ds/query", queryString);
    results = qexec.execSelect();
    ResultSetFormatter.out(System.out, results);


}}
