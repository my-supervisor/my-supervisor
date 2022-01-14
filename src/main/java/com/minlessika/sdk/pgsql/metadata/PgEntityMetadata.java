package com.minlessika.sdk.pgsql.metadata;

import com.minlessika.sdk.datasource.Base;
import com.minlessika.sdk.datasource.ResultStatement;
import com.minlessika.sdk.datasource.TableImpl;
import com.minlessika.sdk.metadata.EntityMetadata;
import com.minlessika.sdk.metadata.FieldMetadata;
import com.minlessika.sdk.pgsql.statement.PgSimpleSelectStatement;
import org.apache.commons.lang.StringUtils;
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
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class PgEntityMetadata implements EntityMetadata {

	final String name;
	final String label;
	
	public PgEntityMetadata(final String name, final String label) {
		this.name = name;
		this.label = label;
	}

	@Override
	public String name() {
		return name;
	}

	@Override
	public String label() {
		return label;
	}

	@Override
	public FieldMetadata idField() {
		return new PgPrimaryKeyField(
					new PgAutomaticLongField(0, name, "id", "ID")
				);
	}

	@Override
	public FieldMetadata guidField() {
		return new PgMandatoryField(
				new PgUuidField(1, name, "guid", "GUID")
			);
	}

	@Override
	public FieldMetadata creationDateField() {
		return new PgMandatoryField(
				new PgDateTimeField(100, name, "creation_date", "Date de création")
			); 
	}
	
	@Override
	public FieldMetadata creatorIdField() {
		return new PgMandatoryField(
				new PgLongField(101, name, "creator_id", "Créateur")
			);
	}
	
	@Override
	public FieldMetadata ownerIdField() {
		return new PgMandatoryField(
				new PgLongField(102, name, "owner_id", "Propriétaire")
			);
	}

	@Override
	public FieldMetadata lastModificationDateField() {
		return new PgMandatoryField(
				new PgDateTimeField(103, name, "last_modification_date", "Date de dernière modification")
			);
	}

	@Override
	public FieldMetadata lastModifierIdField() {
		return new PgMandatoryField(
				new PgLongField(104, name, "last_modifier_id", "Dernier modificateur")
			);
	}
	
	@Override
	public FieldMetadata tagField() {
		return new PgStringField(105, name, "tag", "Tag");
	}

	private List<FieldMetadata> fields(){
		List<FieldMetadata> fields = new ArrayList<FieldMetadata>();
		Method[] methods = this.getClass().getMethods();
		for (Method method : methods) {
			if (method.getReturnType() == FieldMetadata.class)
				try {
					fields.add((FieldMetadata)method.invoke(this));
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				}
		}
		
		fields.sort((c1, c2) -> c1.order().compareTo(c2.order()));
		
		return fields;
	}
	
	@Override
	public String script() {
		
		List<FieldMetadata> fields = fields();

		String definition = StringUtils.EMPTY;
		for (FieldMetadata field : fields) {
			if(StringUtils.isBlank(definition))
				definition = field.definitionScript();
			else
				definition = String.format("%s, %s", definition, field.definitionScript());
		}
		
		for (FieldMetadata field : fields) {
			String constraintScript = field.constraintScript();
			if (!StringUtils.isBlank(constraintScript))
				definition = String.format("%s, %s", definition, constraintScript);
		}
		
		definition = String.format("CREATE TABLE %s (%s) WITH (OIDS=FALSE);", name(), definition);
		
		for (FieldMetadata field : fields) {
			String commentScript = field.commentScript();
			if (!StringUtils.isBlank(commentScript))
				definition = String.format("%s %s;", definition, commentScript);
		}
		
		return definition;
	}

	private List<Map<String, Object>> selectData(final String fileName, final Base base) throws IOException {
		List<Map<String, Object>> data = new ArrayList<>();
		final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		
		try {
			ClassLoader classLoader = PgEntityMetadata.class.getClassLoader();
			InputStream inputStream = classLoader.getResourceAsStream(fileName);
			
			if(inputStream != null) { // file exists
				final DocumentBuilder builder = factory.newDocumentBuilder();
				final Document document = builder.parse(inputStream);
				
				final Element racine = document.getDocumentElement();
				
				final NodeList entityNodes = racine.getChildNodes();
				final int nbEntityNodes = entityNodes.getLength();	
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
				    	
				    	List<FieldMetadata> fields = fields();
				    	Optional<FieldMetadata> mt = fields.stream().filter(c -> c.name() == fieldKey).findFirst();
					    if(!mt.isPresent()) // v�rifier que le champ existe
					    	throw new IllegalArgumentException(String.format("Initialisation : le champ %s n'existe pas dans l'objet %s.", fieldKey, name())); 
					  						    
					    // v�rifier que c'est une cl� �trang�re
					    Object fieldValue;
					    if(fieldNode.hasAttribute("entity")) {
					    	
					    	if (!fieldNode.hasAttribute("tag"))
					    		throw new IllegalArgumentException(String.format("Initialisation : Vous n'avez pas sp�cifi� de tag pour la cl� �trang�re %s de l'objet %s !", fieldKey, name()));
					    	
					    	if (!fieldNode.hasAttribute("fk"))
					    		throw new IllegalArgumentException(String.format("Initialisation : Vous n'avez pas sp�cifi� la cible de la cl� �trang�re %s de l'objet %s !", fieldKey, name()));
					    	
					    	String entity = fieldNode.getAttribute("entity");
					    	String fk = fieldNode.getAttribute("fk");
					    	String tag = fieldNode.getAttribute("tag");
					    	
					    	List<ResultStatement> results = new PgSimpleSelectStatement(base, Arrays.asList(fk), new TableImpl(entity), "tag=?", Arrays.asList(tag)).execute();
					    	
					    	if (results.isEmpty())
					    		throw new IllegalArgumentException(String.format("Cl� �trang�re %s de l'objet %s : Aucun enregistrement ne correspond au tag '%s'", fieldKey, name(), tag));
					    	
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
	
	@Override
	public List<Map<String, Object>> settingData(final Base base) throws IOException {
		String fileName = String.format("data/settings/%s_set.xml", name());
		return selectData(fileName, base);
	}

	@Override
	public List<Map<String, Object>> demoData(final  Base base) throws IOException {
		String fileName = String.format("data/demo/%s_demo.xml", name());
		return selectData(fileName, base);
	}
}
