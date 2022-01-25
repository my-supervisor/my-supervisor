<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" 
                version="2.0">
	<xsl:output method="html" cdata-section-elements="script style" include-content-type="no" doctype-system="about:legacy-compat" encoding="UTF-8" indent="yes"/>
  	<xsl:strip-space elements="*"/>
  	<xsl:include href="/xsl/layout.xsl"/>
  	<xsl:template match="page" mode="head">
	    <title>
	      <xsl:text>Supervisor - Minlessika - Configurer une expression étendue au parent</xsl:text>
	    </title>
	</xsl:template>
	<xsl:template match="page" mode="body">
		<div class="col-12">
           <form class="card" action="/collect/aggregated-model/formular/extended-to-parent-expression/save" method="post">
		       <div class="card-header">
		       		<xsl:if test="item and item/id">
			            <h3 class="card-title">Modifier expression étendue au parent <xsl:value-of select="parent/name"/> - <xsl:value-of select="item/name"/> - formule <i><xsl:value-of select="formular"/></i></h3>
			        </xsl:if>
			        <xsl:if test="not(item) or not(item/id)">
			            <h3 class="card-title">Nouvelle expression étendue au parent <xsl:value-of select="parent/name"/> - formule <i><xsl:value-of select="formular"/></i></h3>
			        </xsl:if>
		       </div>
               <div class="card-body">  
                 <xsl:if test="item/id">
                 	<input name="id" type="text" hidden="hidden" value="{item/id}"/>
                 </xsl:if>               	 
               	 <input name="formular_id" hidden="hidden" type="text" value="{formular_id}" />
               	 <input name="model_id" hidden="hidden" type="text" value="{model_id}" />          	
                 <div class="row">
                   <xsl:choose>
                   		<xsl:when test="item/id">
                   			<div class="col-sm-12 col-md-12">
		                      <div class="form-group">
		                        <label class="form-label">Reference </label>
		                        <p><xsl:value-of select="item/reference"/></p>
		                      </div>
		                    </div>
		                    <div class="col-md-12 mb-0">
		                     <div class="form-group">
		                       <label class="form-label">Sources de données</label>
		                       <div class="table-responsive form-control">
				                    <table class="table card-table table-vcenter text-nowrap">
				                      <thead>
				                        <tr>
				                          <th class="w-1">N°</th>
				                          <th>Modèle</th>
				                          <th>Etendre au champ</th>
				                          <th>Est activée ?</th>
				                          <th colspan="3" class="text-right">							                
							                <a href="/collect/aggregated-model/formular/extended-to-parent-expression/source/edit?expr={item/id}&amp;reference={item/reference_id}&amp;model={model_id}&amp;formular={formular_id}" role="button" class="btn btn-sm btn-primary">
					                         	<i class="fe fe-plus"></i> Nouvelle source
					                        </a>	
				                          </th>
				                        </tr>
				                      </thead>
				                      <tbody>	
				                        <xsl:for-each select="formular_extended_to_parent_sources/formular_extended_to_parent_source">
				                        	<tr>
					                          <td>
					                          	<span class="text-muted"><xsl:value-of select="position()"/></span>
					                          </td>
					                          <td>
					                          	<xsl:value-of select="modelViewName"/>
					                          </td>
					                          <td>
					                          	<xsl:value-of select="field"/>
					                          </td>
					                          <td>
					                            <xsl:choose>
					                            	<xsl:when test="active = 'true'">Oui</xsl:when>
					                            	<xsl:otherwise>Non</xsl:otherwise>
					                            </xsl:choose>
					                          </td>
					                          <td>
				                              	<a class="icon" href="/collect/aggregated-model/formular/extended-to-parent-expression/source/edit?id={id}&amp;expr={../../item/id}&amp;reference={../../item/reference_id}&amp;model={../../model_id}&amp;formular={../../formular_id}">
						                         	<i class="fe fe-edit"></i>
						                        </a>						                       
							                  </td>							                   
							                  <td>
							                   	<a class="icon" href="/collect/aggregated-model/formular/extended-to-parent-expression/source/delete?id={id}&amp;expr={../../item/id}&amp;reference={../../item/reference_id}&amp;model={../../model_id}&amp;formular={../../formular_id}" onclick="return confirm('Etes-vous sûr de vouloir supprimer cette source de données ?');">
							                         <i class="fe fe-trash"></i>
							                    </a>							                       
							                   </td>
					                        </tr>
				                        </xsl:for-each>	                        				                        
				                      </tbody>
				                    </table>
				                  </div>
		                      </div>
		                    </div> 
                   		</xsl:when>
                   		<xsl:otherwise>
                   		    <div class="col-sm-12 col-md-12">
			                     <div class="form-group">
			                       <label class="form-label">Reference <span class="form-required">*</span></label>
			                       <select name="reference_id" class="form-control" required="">                         
			                         <xsl:for-each select="list_data_fields/list_data_field">                             	                     
			                         	<option>
			                         		<xsl:attribute name="value">
			                         			<xsl:value-of select="id"/>
			                         		</xsl:attribute>
			                         		<xsl:value-of select="name"/>                         		                       	
			                         	</option>
			                         </xsl:for-each>                         
			                       </select>
			                     </div>
			                </div>
                   		</xsl:otherwise>
                   </xsl:choose>                                 
                  </div>
                </div>
                <div class="card-footer text-right">
                 <div class="d-flex">                    
                    <a role="button" class="btn btn-primary" href="/collect/aggregated-model/formular/edit?id={formular_id}&amp;model={model_id}">
	                  	<i class="fa fa-arrow-left"></i> Retour
	                </a>
	                <xsl:if test="not(item/id)">
	                	<button type="submit" class="btn btn-primary ml-auto">Enregistrer</button>
	                </xsl:if>	                                                    
                  </div>
               	</div>               	
             </form>
        </div>      
	</xsl:template> 
	<xsl:template match="page" mode="customScript">

	</xsl:template> 		
</xsl:stylesheet>