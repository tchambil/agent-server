package dcc.agent.server.service.swget.rdf2prefuse;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.util.FileManager;
import java.io.InputStream;

public class Converter
{
  protected Model m_model;
  
  public Converter(String p_RDFFile)
  {
    try
    {
      load(p_RDFFile);
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
  }
  
  public Converter(Model p_model)
  {
    this.m_model = p_model;
  }
  
  private void load(String p_RDFFile)
  {
    this.m_model = ModelFactory.createDefaultModel();
    

    InputStream in = FileManager.get().open(p_RDFFile);
    if (in == null) {
      throw new IllegalArgumentException("File: " + p_RDFFile + 
        " not found");
    }
    try
    {
      this.m_model.read(in, null);
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
  }
}
