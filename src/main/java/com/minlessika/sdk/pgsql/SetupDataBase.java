package com.minlessika.sdk.pgsql;

import com.minlessika.sdk.datasource.Base;
import com.minlessika.sdk.datasource.BaseScheme;
import com.minlessika.sdk.datasource.ResultStatement;
import com.minlessika.sdk.datasource.SetupData;
import com.minlessika.sdk.datasource.TableImpl;
import com.minlessika.sdk.metadata.FieldMetadata;
import com.minlessika.sdk.pgsql.statement.PgInsertStatement;
import com.minlessika.sdk.pgsql.statement.PgSimpleSelectStatement;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public abstract class SetupDataBase implements SetupData {

	protected final BaseScheme scheme;
	protected final Class<?> clazz;
	protected final Base base;
	
	public SetupDataBase(final BaseScheme scheme, final Class<?> clazz, final Base base) {
		this.clazz = clazz;
		this.scheme = scheme;
		this.base = base;
	}

	public abstract String path() throws IOException;
	
	@Override
	public final void execute() throws IOException {
		
		try {
			
			final String domainName = scheme.nameOf(clazz);		

			/*final String path = path();
			final List<String> files = IOUtils.readLines(clazz.getClassLoader().getResourceAsStream("./" + path), Charsets.UTF_8)
											  .stream()
					                          .filter(
					                        		  filename -> filename.equals(String.format("%s_set.xml", domainName)) || 
					                        		  			  Pattern.matches(String.format("%s_[1-99]_set.xml", domainName), filename)
					                          ).collect(Collectors.toList());
	    	
			for (String filename : files) {
	    		List<Map<String, Object>> data = selectData(clazz, path + filename, base);
				if(!data.isEmpty()) {								
					for (Map<String, Object> row : data) {
						boolean notExists = new PgSimpleSelectStatement(base, Arrays.asList("id"), new TableImpl(domainName), "tag=?", Arrays.asList(row.get("tag")))
													.execute()
													.isEmpty();
						if(notExists)
							new PgInsertStatement(base, domainName, row, UUID.randomUUID()).execute();
					}	
				}				
			}*/
			
			final String path = path();
			final List<String> files = new ArrayList<>();
			files.add(String.format("%s_set.xml", domainName));
			for (int i = 1; i <= 10; i++) {
				files.add(String.format("%s_%s_set.xml", domainName, i));
			}

			for (String filename : files) {
				
				final String fullFileName = path + filename;
				if(clazz.getClassLoader().getResourceAsStream(fullFileName) == null)
					continue;
				
	    		List<Map<String, Object>> data = selectData(clazz, fullFileName, base);
				if(!data.isEmpty()) {								
					for (Map<String, Object> row : data) {
						boolean notExists = new PgSimpleSelectStatement(base, Arrays.asList("id"), new TableImpl(domainName), "tag=?", Arrays.asList(row.get("tag")))
													.execute()
													.isEmpty();
						if(notExists)
							new PgInsertStatement(base, domainName, row, UUID.randomUUID()).execute();
					}	
				}				
			}
		} catch (NullPointerException e) {
			// directory doesn't exists
		}	
	}

	private List<Map<String, Object>> selectData(final Class<?> clazz, final String fileName, final Base base) throws IOException {
		
		List<Map<String, Object>> data = new ArrayList<>();
		final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		
		try {
			final InputStream inputStream = clazz.getClassLoader().getResourceAsStream(fileName);
			
			if(inputStream != null) { // file exists
				final DocumentBuilder builder = factory.newDocumentBuilder();
				final Document document = builder.parse(inputStream);
				
				final Element racine = document.getDocumentElement();
				
				final NodeList entityNodes = racine.getChildNodes();
				final int nbEntityNodes = entityNodes.getLength();	
				List<FieldMetadata> fields = scheme.fieldsOf(clazz);
				final String domainName = scheme.nameOf(clazz);
				
				for (int i = 0; i < nbEntityNodes; i++) {
					if(entityNodes.item(i).getNodeType() != Node.ELEMENT_NODE)
						continue;
						
				    final Map<String, Object> row = new HashMap<>();
				    final Element entityNode = (Element) entityNodes.item(i);
				    
				    final NodeList fieldNodes = entityNode.getChildNodes();
				    final int nbFieldNodes = fieldNodes.getLength();
				    for(int j = 0; j < nbFieldNodes; j++) {
				    	if(fieldNodes.item(j).getNodeType() != Node.ELEMENT_NODE)
							continue;
				    	
				    	final Element fieldNode = (Element) fieldNodes.item(j);
				    	String fieldKey = fieldNode.getNodeName();
				    					    	
				    	Optional<FieldMetadata> mt = fields.stream().filter(c -> c.name().equals(fieldKey)).findFirst();				    	
					    
				    	if(!mt.isPresent()) // vérifier que le champ existe
					    	throw new IllegalArgumentException(String.format("Initialisation : le champ %s n'existe pas dans l'objet %s.", fieldKey, domainName)); 
					  						    
					    // vérifier que c'est une clé étrangère
					    Object fieldValue;
					    if(fieldNode.hasAttribute("entity")) {
					    	
					    	if (!fieldNode.hasAttribute("tag"))
					    		throw new IllegalArgumentException(String.format("Initialisation : Vous n'avez pas spécifié de tag pour la clé étrangère %s de l'objet %s !", fieldKey, domainName));
					    	
					    	if (!fieldNode.hasAttribute("fk"))
					    		throw new IllegalArgumentException(String.format("Initialisation : Vous n'avez pas spécifié la cible de la clé étrangère %s de l'objet %s !", fieldKey, domainName));
					    	
					    	String entity = fieldNode.getAttribute("entity");
					    	String fk = fieldNode.getAttribute("fk");
					    	String tag = fieldNode.getAttribute("tag");
					    	
					    	List<ResultStatement> results = new PgSimpleSelectStatement(base, Arrays.asList(fk), new TableImpl(entity), "tag=?", Arrays.asList(tag)).execute();
					    	
					    	if (results.isEmpty())
					    		throw new IllegalArgumentException(String.format("Clé étrangère %s de l'objet %s : Aucun enregistrement ne correspond au tag '%s'", fieldKey, domainName, tag));
					    	
					    	fieldValue = results.get(0).data().get(fk);
					    }else
					    	fieldValue = mt.get().cast(fieldNode.getTextContent());
					    
					    row.put(fieldKey, fieldValue);
				    }
				    
				    data.add(row);
				}
			}
			
		} catch (ParserConfigurationException | SAXException e) {
			throw new IOException(e);
		}

		return data;
	}
}
