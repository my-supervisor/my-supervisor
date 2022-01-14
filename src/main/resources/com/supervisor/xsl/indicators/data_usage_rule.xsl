<?xml version="1.0"?>
<xsl:stylesheet 
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:interactive="http://www.minlessika.com/DataUsageRule"
	version="2.0">
	
	<xsl:template name="data_usage_rule_list">
		<xsl:param name="indic_id"/>	
		<xsl:param name="is_indicator_template"/>
		<xsl:param name="data_usage_rules"/>
		<xsl:param name="source"/>
		<xsl:param name="short_name"/>
   		<xsl:if test="$indic_id">
                   <div class="row">
                    	<div class="col-md-12">
		                     <div class="form-group">
		                       <label class="form-label">Liaison de données</label>
		                       <div class="table-responsive form-control">
				                    <table class="table card-table table-vcenter text-nowrap">
				                      <thead>
				                        <tr>
				                          <th class="w-1">N°</th>
				                          <th>Libellé</th>
				                          <th>Modèle utilisé</th>
				                          <th>Etat</th>
				                          <th colspan="2" class="text-right">
				                            <xsl:if test="$is_indicator_template = 'false'">
				                            	<a href="/activity/indic/data-link/edit?indic={$indic_id}&amp;source={$source}" role="button" class="btn btn-sm btn-primary">
								                  	<i class="fa fa-plus"></i> Nouvelle liaison
								                </a>
				                            </xsl:if>				                          	
				                          </th>
				                        </tr>
				                      </thead>
				                      <tbody>	
				                        <xsl:for-each select="$data_usage_rules/data_link">
				                        	<tr>
					                          <td>
					                          	<span class="text-muted"><xsl:value-of select="position()"/></span>
					                          </td>
					                          <td>
					                          	<span class="mr-2">
					                          		<xsl:value-of select="name"/>  					                          		
							                    </span>
					                          </td>
					                          <td>
					                          	<span class="mr-2">
					                          		<xsl:value-of select="model"/>  					                          		
							                    </span>
							                    <a class="icon" href="/collect/aggregated-model/edit?id={model_id}">
						                         	<i class="fe fe-edit"></i>
						                       	</a>
					                          </td>
					                          <td>
					                            <xsl:choose>
						                     		<xsl:when test="active='true'">
						                     			<xsl:text>Activée</xsl:text>
						                     		</xsl:when>
						                     		<xsl:otherwise>
						                     			<xsl:text>Désactivée</xsl:text>
						                     		</xsl:otherwise>
						                     	</xsl:choose>
					                          </td>
					                          <td>
							                       <a class="icon" href="/activity/indic/data-link/edit?indic={$indic_id}&amp;id={id}&amp;source={$source}">
							                         <i class="fe fe-edit"></i>
							                       </a>
						                      </td>
						                      <td>
						                           <xsl:if test="$is_indicator_template = 'false'">
						                           	<a class="icon" href="/activity/indic/data-link/delete?indic={$indic_id}&amp;id={id}&amp;source={$source}" onclick="return confirm('Etes-vous sûr de vouloir supprimer cette liaison ?');">
							                         	<i class="fe fe-trash"></i>
							                        </a>
						                           </xsl:if>							                       
						                      </td>
					                        </tr>
				                        </xsl:for-each>	                        				                        
				                      </tbody>
				                    </table>
				                  </div>
		                      </div>
		                   </div>
		            </div>
                    </xsl:if>	
	</xsl:template>
	
	<xsl:template name="indicator_dynamic_param_list">
		<xsl:param name="indic_id"/>	
		<xsl:param name="is_indicator_template"/>
		<xsl:param name="indicator_dynamic_params"/>
		<xsl:param name="source"/>
		<xsl:param name="short_name"/>
   		<xsl:if test="$indic_id">
                   <div class="row">
                    	<div class="col-md-12">
		                     <div class="form-group">
		                       <label class="form-label">Paramètres dynamiques</label>
		                       <div class="table-responsive form-control">
				                    <table class="table card-table table-vcenter text-nowrap">
				                      <thead>
				                        <tr>
				                          <th class="w-1">N°</th>
				                          <th>Libellé</th>
				                          <th>Type</th>
				                          <th>Agrégat</th>
				                          <th></th>
				                        </tr>
				                      </thead>
				                      <tbody>	
				                        <xsl:for-each select="$indicator_dynamic_params/indicator_dynamic_param">
				                        	<tr>
					                          <td>
					                          	<span class="text-muted"><xsl:value-of select="position()"/></span>
					                          </td>
					                          <td>
					                          	<span class="mr-2">
					                          		<xsl:value-of select="name"/>  					                          		
							                    </span>
					                          </td>
					                          <td>
					                          	<span class="mr-2">
					                          		<xsl:value-of select="type"/>  					                          		
							                    </span>
					                          </td>
					                          <td>
					                            <span class="mr-2">
					                          		<xsl:value-of select="aggregate_func"/>  					                          		
							                    </span>
					                          </td>
					                          <td>
							                       <a class="icon" href="/indicator/dynamic-param/edit?id={id}&amp;indic={$indic_id}&amp;short_name={$short_name}&amp;source={$source}">
							                         <i class="fe fe-edit"></i>
							                       </a>
						                      </td>
					                        </tr>
				                        </xsl:for-each>	                        				                        
				                      </tbody>
				                    </table>
				                  </div>
		                      </div>
		                   </div>
		            </div>
                    </xsl:if>	
	</xsl:template>
</xsl:stylesheet>