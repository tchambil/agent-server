package dcc.agent.server.service.swget.multithread;

/**
 * Created by teo on 20/07/15.
 */
import dcc.agent.server.service.swget.utils.URIData;

import java.net.MalformedURLException;
import java.net.URL;
public class EndPoints {
    public static String getEndpoint(URIData uriData) {
        {
            String current_URI = uriData.getUrl();
            if (current_URI.toString().contains("#")) {
                current_URI = current_URI.substring(0, current_URI.indexOf("#"));
            }
            try {
                URL tempUrl = null;
                tempUrl = new URL(current_URI);
                String host = tempUrl.getHost();
                if (host.equals("dblp.l3s.de")) {
                    return Endpoint.DBLP.getEndpoints();
                } else if (host.equals("dbpedia.org")) {
                    return Endpoint.DBPEDIA.getEndpoints();
                } else if (host.equals("sws.geonames.org")) {
                    return Endpoint.GEONAMES.getEndpoints();
                } else if (host.equals("rdf.freebase.com")) {
                    return Endpoint.FREEBASE.getEndpoints();
                } else if (host.equals("yago-knowledge.org")) {
                    return Endpoint.YAGO.getEndpoints();
                } else {
                    return null;
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            return  null;
        }
    }
    public static final String[] endpointArray={
            "http://www.rdfabout.com/sparql",
            "http://purl.org/openbibliosets/iucrsparql",
            "http://sparql.linkedopendata.it/grrt",
            "http://affymetrix.bio2rdf.org/sparql",
            "http://api.talis.com/stores/airports/services/sparql",
            "http://acm.rkbexplorer.com/sparql/",
            "http://api.talis.com/stores/bbc-backstage/services/sparql",
            "http://api.talis.com/stores/bbc-wildlife/services/sparql",
            "http://data.bibbase.org:2020/sparql",
            "http://biocyc.bio2rdf.org/sparql",
            "http://budapest.rkbexplorer.com/sparql/",
            "http://cas.bio2rdf.org/sparql",
            "http://eris.okfn.org/sparql",
            "http://river.styx.org/sparql",
            "http://chebi.bio2rdf.org/sparql",
            "http://citeseer.rkbexplorer.com/sparql/",
            "http://cordis.rkbexplorer.com/sparql/",
            "http://www4.wiwiss.fu-berlin.de/dblp/sparql",
            "http://dblp.rkbexplorer.com/sparql/",
            "http://dblp.l3s.de/d2r/sparql",
            "http://dbtune.org/jamendo/sparql/",
            "http://dbtune.org/bbc/peel/sparql/",
            "http://dbtune.org/magnatune/sparql/",
            "http://dbtune.org/musicbrainz/sparql",
            "http://dbtune.org/myspace/sparql/",
            "http://dbtune.org/classical/sparql/",
            "http://dbpedia.org/sparql",
            "http://deploy.rkbexplorer.com/sparql/",
            "http://www4.wiwiss.fu-berlin.de/dailymed/sparql",
            "http://api.talis.com/stores/musicbrainz/services/sparql",
            "http://services.data.gov/sparql",
            "http://deepblue.rkbexplorer.com/sparql/",
            "http://www4.wiwiss.fu-berlin.de/diseasome/sparql",
            "http://italy.rkbexplorer.com/sparql",
            "http://www4.wiwiss.fu-berlin.de/drugbank/sparql",
            "http://linkeddata.ge.imati.cnr.it:2020/sparql",
            "http://era.rkbexplorer.com/sparql/",
            "http://api.talis.com/stores/eutc-productions/services/sparql",
            "http://lsd.taxonconcept.org/sparql",
            "http://www4.wiwiss.fu-berlin.de/eurostat/sparql",
            "http://factforge.net/sparql",
            "http://api.talis.com/stores/fanhubz/services/sparql",
            "http://ft.rkbexplorer.com/sparql/",
            "http://lisbon.rkbexplorer.com/sparql",
            "http://genbank.bio2rdf.org/sparql",
            "http://go.bio2rdf.org/sparql",
            "http://geneid.bio2rdf.org/sparql",
            "http://geo.linkeddata.es/sparql",
            "http://lod.openlinksw.com/sparql",
            "http://hgnc.bio2rdf.org/sparql",
            "http://homologene.bio2rdf.org/sparql",
            "http://ibm.rkbexplorer.com/sparql/",
            "http://ieee.rkbexplorer.com/sparql/",
            "http://eurecom.rkbexplorer.com/sparql/",
            "http://interpro.bio2rdf.org/sparql",
            "http://sparql.linkedopendata.it/musei",
            "http://api.talis.com/stores/jgoodwin-genealogy/services/sparql",
            "http://cpd.bio2rdf.org/sparql",
            "http://dr.bio2rdf.org/sparql",
            "http://ec.bio2rdf.org/sparql",
            "http://gl.bio2rdf.org/sparql",
            "http://kegg.bio2rdf.org/sparql",
            "http://rn.bio2rdf.org/sparql",
            "http://kisti.rkbexplorer.com/sparql/",
            "http://laas.rkbexplorer.com/sparql/",
            "http://lab3.libris.kb.se/sparql",
            "http://data.bib.uni-mannheim.de/sparql",
            "http://data.linkedmdb.org/sparql",
            "http://linkedopencommerce.com/sparql",
            "http://sonicbanana.cs.wright.edu:8890/sparql",
            "http://data.linkedct.org/sparql",
            "http://linkedgeodata.org/sparql/",
            "http://api.talis.com/stores/rsinger-dev4/services/sparql",
            "http://sw.unime.it:8890/sparql",
            "http://www.lotico.com:2020/lotico",
            "http://www4.wiwiss.fu-berlin.de/medicare/sparql",
            "http://api.talis.com/stores/moseley/services/sparql",
            "http://mgi.bio2rdf.org/sparql",
            "http://id.ndl.go.jp/auth/ndlsh/",
            "http://nsf.rkbexplorer.com/sparql/",
            "http://obo.bio2rdf.org/sparql",
            "http://omim.bio2rdf.org/sparql",
            "http://oai.rkbexplorer.com/sparql/",
            "http://api.talis.com/stores/openlibrary/services/sparql",
            "http://en.openei.org/sparql",
            "http://api.talis.com/stores/ordnance-survey/services/sparql",
            "http://pdb.bio2rdf.org/sparql",
            "http://prosite.bio2rdf.org/sparql",
            "http://pfam.bio2rdf.org/sparql",
            "http://api.talis.com/stores/pokedex/services/sparql",
            "http://prodom.bio2rdf.org/sparql",
            "http://api.talis.com/stores/productdb/services/sparql",
            "http://www4.wiwiss.fu-berlin.de/gutendata/sparql",
            "http://pubchem.bio2rdf.org/sparql",
            "http://pubmed.bio2rdf.org/sparql",
            "http://risks.rkbexplorer.com/sparql/",
            "http://curriculum.rkbexplorer.com/sparql/",
            "http://wiki.rkbexplorer.com/sparql/",
            "http://resex.rkbexplorer.com/sparql/",
            "http://reactome.bio2rdf.org/sparql",
            "http://eculture2.cs.vu.nl:5020/sparql/",
            "http://rae2001.rkbexplorer.com/sparql/",
            "http://courseware.rkbexplorer.com/sparql/",
            "http://revyu.com/sparql",
            "http://www4.wiwiss.fu-berlin.de/sider/sparql",
            "http://www4.wiwiss.fu-berlin.de/stitch/sparql",
            "http://sgd.bio2rdf.org/sparql",
            "http://ecs.rkbexplorer.com/sparql",
            "http://southampton.rkbexplorer.com/sparql/",
            "http://cb.semsol.org/sparql",
            "http://data.semanticweb.org/sparql",
            "http://darmstadt.rkbexplorer.com/sparql/",
            "http://api.talis.com/stores/datagovuk/services/sparql",
            "http://api.talis.com/stores/theviewfrom/services/sparql",
            "http://lod.gesis.org/thesoz/sparql",
            "http://rdfabout.com/sparql",
            "http://jisc.rkbexplorer.com/sparql/",
            "http://unlocode.rkbexplorer.com/sparql/",
            "http://linkeddata.uriburner.com/sparql",
            "http://uniparc.bio2rdf.org/sparql",
            "http://unipathway.bio2rdf.org/sparql",
            "http://uniref.bio2rdf.org/sparql",
            "http://uniprot.bio2rdf.org/sparql",
            "http://taxonomy.bio2rdf.org/sparql",
            "http://unists.bio2rdf.org/sparql",
            "http://newcastle.rkbexplorer.com/sparql/",
            "http://roma.rkbexplorer.com/sparql/",
            "http://pisa.rkbexplorer.com/sparql/",
            "http://ulm.rkbexplorer.com/sparql/",
            "http://irit.rkbexplorer.com/sparql/",
            "http://kaunas.rkbexplorer.com/sparql",
            "http://webscience.rkbexplorer.com/sparql",
            "http://wordnet.rkbexplorer.com/sparql/",
            "http://www4.wiwiss.fu-berlin.de/factbook/sparql",
            "http://services.data.gov.uk/business/sparql",
            "http://crm.rkbexplorer.com/sparql",
            "http://data.open.ac.uk/query",
            "http://digitaleconomy.rkbexplorer.com/sparql",
            "http://dotac.rkbexplorer.com/sparql/",
            "http://eprints.rkbexplorer.com/sparql/",
            "http://services.data.gov.uk/education/sparql",
            "http://enaktingpsi.rkbexplorer.com/sparql",
            "http://services.data.gov.uk/environment/sparql",
            "http://epsrc.rkbexplorer.com/sparql",
            "http://fun.rkbexplorer.com/sparql",
            "http://gdlc.rkbexplorer.com/sparql",
            "http://iserve.kmi.open.ac.uk/data/execute-query",
            "http://rdf.myexperiment.org/sparql/",
            "http://notube.rkbexplorer.com/sparql",
            "http://services.data.gov.uk/patents/sparql",
            "http://photos.rkbexplorer.com/sparql",
            "http://services.data.gov.uk/reference/sparql",
            "http://services.data.gov.uk/research/sparql",
            "http://roni.rkbexplorer.com/sparql",
            "http://services.data.gov.uk/statistics/sparql",
            "http://services.data.gov.uk/transport/sparql",
            "http://webconf.rkbexplorer.com/sparql",
            "http://sparql.reegle.info/",
            "http://ecowlim.tfri.gov.tw/sparql/query",
            "http://biolit.rkbexplorer.com/sparql",
            "http://hcls.deri.org/sparql",
            "http://apps.ideaconsult.net:8080/ontology",
            "http://sparql.linkedopendata.it/grrp",
            "http://sparql.linkedopendata.it/scuole",
            "http://linkedlifedata.com/sparql",
            "http://sparql.linkedopendata.it/cap",
            "http://lobid.org/sparql/",
            "http://sparql.linkedopendata.it/los",
            "http://aseg.cs.concordia.ca/secold/sparqlendpoint",
            "http://www.open-biomed.org.uk/sparql/endpoint/bdgp_20081030",
            "http://www.open-biomed.org.uk/sparql/endpoint/flybase",
            "http://www.open-biomed.org.uk/sparql/endpoint/flyatlas",
            "http://catalogus-professorum.org/sparql",
            "http://www4.wiwiss.fu-berlin.de/cordis/sparql",
            "http://fintrans.publicdata.eu/sparql",
            "http://www.open-biomed.org.uk/sparql/endpoint/flyted",
            "http://labs.mondeca.com/endpoint/lov",
            "http://os.rkbexplorer.com/sparql/",
            "http://labs.mondeca.com/endpoint/ends",
            "http://www.kanzaki.com/works/2011/stat/",
            "http://link.informatics.stonybrook.edu/sparql/",
            "http://opendatacommunities.org/sparql",
            "http://linkedscotland.org/sparql",
            "http://smartlink.open.ac.uk/smartlink/sparql",
            "http://linkedmanchester.org/sparql",
            "http://data.bibsys.no/data/query_authority.html",
            "http://crime.rkbexplorer.com/sparql/",
            "http://sparql.linkedopendata.it/istat",
            "http://data.archiveshub.ac.uk/sparql",
            "http://pt.dbpedia.org/sparql",
            "http://govwild.org/sparql",
            "http://www.zaragoza.es/datosabiertos/sparql",
            "http://data.oceandrilling.org/sparql",
            "http://www.zaragoza.es/turruta/sparql",
            "http://data.semanticuniverse.com/sparql",
            "http://id.ndl.go.jp/auth/ndla/",
            "http://ndb.publink.lod2.eu/sparql",
            "http://www.archivesdefrance.culture.gouv.fr/thesaurus/sparql",
            "http://el.dbpedia.org/sparql",
            "http://cr3.eionet.europa.eu/sparql",
            "http://setaria.oszk.hu/sparql",
            "http://soa4all.isoco.net/luf/sparql",
            "http://api.kasabi.com/api/sparql-endpoint-near",
            "http://meducator.open.ac.uk/resourcesrestapi/rest/meducator/sparql",
            "http://vocabulary.semantic-web.at/PoolParty/sparql/semweb",
            "http://telegraphis.net/data/countries/sparql",
            "http://api.talis.com/stores/gunnbib-digitalmanuskripter/sparql",
            "http://semanticweb.cs.vu.nl/europeana/sparql",
            "http://semantic.ckan.net/sparql/",
            "http://doc.metalex.eu:8000/sparql/",
            "http://api.talis.com/stores/mesh-norwegian/services/sparql",
            "http://vocabulary.semantic-web.at/PoolParty/sparql/OpenData",
            "http://api.talis.com/stores/wordnet/services/sparql",
            "http://cultura.linkeddata.es/sparql",
            "http://miuras.inf.um.es/sparql",
            "http://el-devtc01.tugraz.at/~ldv/interlinking/endpoint_handler.php",
            "http://hcls.deri.org:8080/openrdf-sesame/repositories/tcga",
            "http://neuroweb.med.yale.edu:8890/sparql",
            "http://aemet.linkeddata.es/sparql",
            "http://webenemasuno.linkeddata.es/sparql",
            "http://www4.wiwiss.fu-berlin.de/eures/sparql",
            "http://bnb.data.bl.uk/sparql",
            "http://spending.lichfielddc.gov.uk/sparql",
            "http://sparql.neurocommons.org:8890/nsparql/",
            "http://platform.uberblic.org/api/v1/sparql",
            "http://sparql.yovisto.com/",
            "http://sparql.data.southampton.ac.uk/",
            "http://data.fundacionctic.org/sparql",
            "http://dewey.info/sparql.php",
            "http://gov.tso.co.uk/coins/sparql",
            "http://gov.tso.co.uk/dclg/sparql",
            "http://openuplabs.tso.co.uk/sparql/gov-education",
            "http://os.services.tso.co.uk/geo/sparql",
            "http://gov.tso.co.uk/legislation/sparql",
            "http://gov.tso.co.uk/transport/sparql",
            "http://api.talis.com/stores/trafficscotland/services/sparql",
            "http://kent.zpr.fer.hr:8080/educationalProgram/sparql",
            "http://semantics.eurecom.fr/sparql",
            "http://id.sgcb.mcu.es/sparql",
            "http://twarql.org:8890/sparql",
            "http://212.81.220.68:2020/elviajero",
            "http://api.talis.com/stores/climb/services/sparql",
            "http://api.talis.com/stores/metoffice/services/sparql",
            "http://kwijibo.talis.com/latc-linksets/sparql",
            "http://api.talis.com/stores/massobservation/services/sparql",
            "http://api.talis.com/stores/pbac/services/sparql",
            "http://api.talis.com/stores/smcjournals/services/sparql",
            "http://202.73.13.50:55824/catalogs/performance/repositories/agrovoc",
            "http://data-gov.ie/sparql",
            "http://enipedia.tudelft.nl/wiki/Special:SparqlExtension",
            "http://api.talis.com/stores/nvd/services/sparql",
            "http://api.kasabi.com/dataset/ecco-tcp-eighteenth-century-collections-online-texts/apis/sparql",
            "http://crystaleye.ch.cam.ac.uk/sparql/",
            "http://data.cnr.it/sparql-proxy/",
            "http://greek-lod.math.auth.gr/police/sparql",
            "http://eculture2.cs.vu.nl:8890/sparql",
            "http://greek-lod.math.auth.gr/fire-brigade/sparql",
            "http://semantic.eea.europa.eu/sparql",
            "http://cr.eionet.europa.eu/sparql",
            "http://dbmi-icode-01.dbmi.pitt.edu:2020/snorql/",
            "http://live.dbpedia.org/sparql",
            "http://vocabulary.semantic-web.at/PoolParty/sparql/AustrianSkiTeam",
            "http://core.kmi.open.ac.uk/squery",
            "http://idi.fundacionctic.org/classifications_endpoint/eurovoc",
            "http://resource.geolba.ac.at/PoolParty/sparql/GeologicUnit",
            "http://data.copac.ac.uk/sparql",
            "http://data.ngii.go.kr/SPARQL/sparql",
            "http://api.kasabi.com/dataset/musicnet/apis/sparql",
            "http://api.kasabi.com/dataset/bricklink/apis/sparql",
            "http://api.kasabi.com/dataset/chempedia-rdf/apis/sparql",
            "http://api.kasabi.com/dataset/discogs/apis/sparql",
            "http://api.kasabi.com/dataset/english-heritage/apis/sparql",
            "http://api.kasabi.com/dataset/federal-reserve-economic-data/apis/sparql",
            "http://kasabi.com/api/sparql-endpoint-foodista",
            "http://api.kasabi.com/dataset/jisc-cetis-project-directory/apis/sparql",
            "http://api.kasabi.com/dataset/nasa/apis/sparql",
            "http://api.kasabi.com/dataset/pali-english-lexicon/apis/sparql",
            "http://api.kasabi.com/dataset/prelinger-archives/apis/sparql",
            "http://api.kasabi.com/dataset/renewable-energy-generators/apis/sparql",
            "http://api.kasabi.com/dataset/sanskrit-english-lexicon/apis/sparql",
            "http://api.kasabi.com/dataset/un-hazardous-material-numbers/apis/sparql",
            "http://api.kasabi.com/dataset/yahoo-geoplanet/apis/sparql",
            "http://datos.bcn.cl/sparql",
            "http://data.ox.ac.uk/sparql/",
            "http://api.talis.com/stores/schemapedia/services/sparql",
            "http://zhishi.me/sparql",
            "http://kwijibo.talis.com/kasabi/eumida/sparql",
            "http://kasabi.com/dataset/global-hunger-index/apis/sparql",
            "http://minsky.gsi.dit.upm.es/semanticwiki/index.php/Special:SPARQLEndpoint",
            "http://kasabi.com/dataset/chembl-rdf/apis/sparql",
            "http://lod.sztaki.hu/sparql",
            "http://156.35.31.156/sparql",
            "http://rdf.ng-london.org.uk/raphael",
            "http://logd.tw.rpi.edu/sparql",
            "http://www.morelab.deusto.es/joseki/articles",
            "http://sparql.linkedopendata.it/loc ",
            "http://www.open-biomed.org.uk/sparql/endpoint/tcm. ",
            "http://202.73.13.50:55824/catalogs/performance/repositories/jita ",
            "http://greek-lod.math.auth.gr/intervalue/sparql",
            "http://collection.britishmuseum.org/Sparql",
            "http://bibleontology.com/sparql/index.jsp",
            "http://lod.b3kat.de/sparql",
            "http://datos.bne.es/sparql",
            "http://data.allie.dbcls.jp/sparql",
            "http://diwis.imis.athena-innovation.gr:8181/sparql",
            "http://eur-lex.publicdata.eu/sparql",
            "http://www4.wiwiss.fu-berlin.de/euraxess/sparql",
            "http://gd.projekty.ms.mff.cuni.cz:8892/sparql",
            "http://biordf.net/sparql",
            "http://www.sparql.org/sparql.html",
            "http://kwijibo.talis.com/kasabi/european-election-results/sparql",
            "http://kwijibo.talis.com/kasabi/eurobarometer-standard/sparql",
            "http://api.kasabi.com/dataset/latc-eu-media/apis/sparql",
            "http://socialarchive.iath.virginia.edu/sparql/eac",
            "http://data.noticias.universia.net/d2r-server/snorql/",
            "http://de.dbpedia.org/sparql",
            "http://europeana-triplestore.isti.cnr.it/sparql",
            "http://www.rechercheisidore.fr/sparql",
            "http://environment.data.gov.uk/sparql/bwq/query",
            "http://dbmi-icode-01.dbmi.pitt.edu/linkedSPLs/sparql",
            "http://zbw.eu/beta/sparql/stw"
    };
}
