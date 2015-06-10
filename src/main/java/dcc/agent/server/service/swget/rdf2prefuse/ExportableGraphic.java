package dcc.agent.server.service.swget.rdf2prefuse;

import java.io.File;

public abstract interface ExportableGraphic
{
  public abstract void export(File paramFile, String paramString);
}
