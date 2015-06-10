package dcc.agent.server.service.swget.utils;

public class Constants {

	public static final String OWL_NS = "http://www.w3.org/2002/07/owl#";
	public static final String RDF_NS = "http://www.w3.org/1999/02/22-rdf-syntax-ns#";
	public static final String RDF_BLANK_NS = "http://www.w3.org/1999/02/22-rdf-syntax-ns#nodeID=";
	public static final String RDFS_NS = "http://www.w3.org/2000/01/rdf-schema#";
	public static final String SKOS_NS = "http://www.w3.org/2004/02/skos/core#";
	public static final String FOAF_NS = "http://xmlns.com/foaf/0.1/";
	public static final String DCTERMS_NS = "http://purl.org/dc/terms/";
	public static final String DCELEM_NS = "http://purl.org/dc/elements/1.1/";
	public static final String DBPEDIA_PROP_NS = "http://dbpedia.org/property/";
	public static final String DBPEDIA_ONTO_NS = "http://dbpedia.org/ontology/";
	public static final String DBPEDIA_RES_NS = "http://dbpedia.org/resource/";
	public static final String GEO_NS = "http://www.w3.org/2003/01/geo/wgs84_pos#";
	public static final String GEO_RSS_NS = "http://www.georss.org/georss/";
	public static final String XSD_NS = "http://www.w3.org/2001/XMLSchema#";
	public static final String SCHOLAR_NS = "http://scholar.google.com/";
	public static final String SCHOLAR_SEARCH_NS = "http://scholar.google.com/scholar?q=";
	public static final String DBLP_AUTH_NS = "http://dblp.l3s.de/d2r/resource/authors/";
	public static final String DBLP_CONF_NS = "http://dblp.l3s.de/d2r/resource/publications/conf/";
	public static final String DBLP_JOURN_NS = "http://dblp.l3s.de/d2r/resource/publications/journals/";
	public static final String DBLP_BOOKS_NS = "http://dblp.l3s.de/d2r/resource/publications/books/";

	public static final String[] namespaces_values = { DBPEDIA_ONTO_NS, OWL_NS,
			RDF_BLANK_NS, RDF_NS, RDFS_NS, SKOS_NS, FOAF_NS, DCTERMS_NS,
			DCELEM_NS, DBPEDIA_PROP_NS, DBPEDIA_ONTO_NS, DBPEDIA_RES_NS,
			GEO_NS, GEO_RSS_NS, XSD_NS, SCHOLAR_SEARCH_NS, SCHOLAR_NS,
			DBLP_AUTH_NS, DBLP_CONF_NS, DBLP_JOURN_NS, DBLP_BOOKS_NS };

	public static final String OWL = "owl:";
	public static final String RDF = "rdf:";
	public static final String RDF_BLANK = "_:";
	public static final String RDFS = "rdfs:";
	public static final String SKOS = "skos:";
	public static final String FOAF = "foaf:";
	public static final String DCTERMS = "dc:";
	public static final String DCELEM = "dce:";
	public static final String DBPEDIA_PROP = "dbpprop:";
	public static final String DBPEDIA_ONTO = "dbpedia:";
	public static final String DBPEDIA_RES = "dbp:";
	public static final String DBPEDIA_ONTO_OWL = "dbpedia-owl:";
	public static final String GEO = "geo:";
	public static final String GEO_RSS = "georss:";
	public static final String XSD = "xsd:";
	public static final String SCHOLAR = "scholar:";
	public static final String SCHOLAR_QUERY = "scholar-q:";
	public static final String DBLP_AUTH = "dblp-a:";
	public static final String DBLP_CONF = "dblp-c:";
	public static final String DBLP_JOURN = "dblp-j:";
	public static final String DBLP_BOOKS = "dblp-b:";

	public static final String[] namespaces_keys = { DBPEDIA_ONTO_OWL, OWL,
			RDF_BLANK, RDF, RDFS, SKOS, FOAF, DCTERMS, DCELEM, DBPEDIA_PROP,
			DBPEDIA_ONTO, DBPEDIA_RES, GEO, GEO_RSS, XSD, SCHOLAR_QUERY,
			SCHOLAR, DBLP_AUTH, DBLP_CONF, DBLP_JOURN, DBLP_BOOKS };

	public static final String NUM_THREADS = "-t";
	public static final String BUDGET = "-b";
	public static final String RECONSTRUCT = "-recon";
	public static final String SEED = "-s";
	public static final String STREAM_OUTPUT = "-stream";

	public static final String DIRECTORY = "-dir";
	public static final String WRITE_OUTPUT_FILE = "-f";
	public static final String LOG_FILE = "-log";
	public static final String VERBOSE = "-v";
	public static final String USER_AGENT = "-u";
	public static final String TIMEOUT_PER_CONNECTION = "-timeoutDer";
	public static final String PRINT = "-print";
	public static final String MAX_SIZE = "-maxSize";
	public static final String MAX_SIZE_PER_CONNECTION = "-maxSizeDer";
	public static final String MAX_NUM_TRIPLES_PER_CONNECTION = "-maxDerTriples";
	public static final String REG_EXPR_PREDICATE = "-p";
	public static final String DOMAIN = "-domains";
	public static final String NOT_SAVE_MODELS = "-noM";
	public static final String VISITED = "-visited";

	public static final String EXIT = "-exit";
	public static final String HELP = "-help";
	public static final String RETRIEVE = "-retrieve";
	public static final String CLEAR = "-clear";
	public static final String HISTORY = "-h";

	public static final String DEFAULT_USER_AGENT_VALUE = "swget";
	public static final int DEFAULT_DEPTH = -1;
	public static final int DEFAULT_CONNECTION_TIMEOUT = 1;// in seconds
	public static final String DEFAULT_OUTPUTFILE = "output_swget";
	public static final String DEFAULT_CONTENT_TYPES = "application/rdf+xml";

	public static final String HISTORY_FILE = "swget_history.dat";
	public static final int HISTORY_SIZE = 10;

	public static final String INTERNAL_LINK = "internal_link";
	public static final String EXTERNAL_LINK = "external_link";

	public static final String RDF_EXTENSION = ".rdf";
	public static final String N3_EXTENSION = ".n3";

	// HTTP HEADER FIELDS
	public static final String LINK = "Link";
	public static final String DATE = "Date";
	public static final String CONTENT_LENGTH = "Content-Length";
	public static final String EXPIRES = "Expires";
	public static final String SPARQL_DEFAUL_GRAPH = "X-SPARQL-default-graph";
	public static final String CONTENT_TYPE = "Content-Type";
	public static final String CONNECTION = "Connection";
	public static final String ACCEPT_RANGES = "Accept-Ranges";
	public static final String SERVER = "Server";

	public static final String SWGET_EMAIL_MSG_BEGIN = "Dear user, in the following the results for an ACTION activated within an swget request \n";
	public static final String SWGET_EMAIL_SUBJECT = "Results for the action";
	public static final String SWGET_EMAIL_MSG_END = "\n \n Yours Sincerely";

	public static final String EMAIL_COMMAND = "sendEmail(";
	public static final String SWGET_EMAIL_FROM = "swget@gmail.com";

	// SCHOLAR
	public static final long timer = 20000;

	// swget ontology
	public static final String ONTO_SEED = "http://inf.unibz.it/ontologies/2012/10/swget#seed_uri";
	public static final String ONTO_EXPR = "http://inf.unibz.it/ontologies/2012/10/swget#nav_expr";
	public static final String ONTO_VERBOSE = "http://inf.unibz.it/ontologies/2012/10/swget#verbose";
	public static final String ONTO_PRINT = "http://inf.unibz.it/ontologies/2012/10/swget#print";
	public static final String ONTO_DEPTH = "http://inf.unibz.it/ontologies/2012/10/swget#depth";
	public static final String ONTO_DIR = "http://inf.unibz.it/ontologies/2012/10/swget#dir";
	public static final String ONTO_FILE = "http://inf.unibz.it/ontologies/2012/10/swget#file";
	public static final String ONTO_LOG = "http://inf.unibz.it/ontologies/2012/10/swget#log";
	public static final String ONTO_SIZE = "http://inf.unibz.it/ontologies/2012/10/swget#size";
	public static final String ONTO_CONN_TIMEOUT = "http://inf.unibz.it/ontologies/2012/10/swget#conn_timeout";
	public static final String ONTO_CONN_DATA_LIMIT = "http://inf.unibz.it/ontologies/2012/10/swget#conn_data_limit";
	public static final String ONTO_TRIPLES = "http://inf.unibz.it/ontologies/2012/10/swget#triples";
	public static final String ONTO_DOMAINS = "http://inf.unibz.it/ontologies/2012/10/swget#domains";

}
