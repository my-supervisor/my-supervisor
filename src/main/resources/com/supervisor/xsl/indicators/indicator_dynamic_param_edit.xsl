<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" 
                version="2.0">
	<xsl:output method="html" cdata-section-elements="script style" include-content-type="no" doctype-system="about:legacy-compat" encoding="UTF-8" indent="yes"/>
  	<xsl:strip-space elements="*"/>
  	<xsl:include href="/com/supervisor/xsl/layout.xsl"/>
  	<xsl:template match="page" mode="head">
	    <title>
	      <xsl:text>Supervisor - Minlessika - Configurer un paramètre d'indicateur</xsl:text>
	    </title>
	</xsl:template>
	<xsl:template match="page" mode="body">
		<div class="col-12">
           <form class="card" action="/indicator/dynamic-param/save?id={item/id}&amp;source={source}" method="post">
		        <div class="card-header">
		       		<h3 class="card-title"><xsl:text>Modifier </xsl:text><i><xsl:value-of select="item/name"/></i> - <xsl:value-of select="item/indicator"/></h3>		        
		       </div>
               <div class="card-body"> 
                 <input hidden="hidden" name="indicator_id" value="{item/indicator_id}"/>             
                 <div class="row">                 
                   <div class="col-sm-6 col-md-6">
                     <div class="form-group">
                       <label class="form-label">Fonction d'aggrégation <span class="form-required">*</span></label>
                       <xsl:choose>
                       		<xsl:when test="item/type_id = 'NUMBER'">                       			
                       			<select name="aggregate_func_id" class="form-control" required="">                                                 	                        
			                         <xsl:variable name="item" select="item" />
			                         <xsl:if test="item/type_id = 'STRING'">
			                         	<xsl:attribute name="disabled">disabled</xsl:attribute>
			                         </xsl:if>                         
			                         <xsl:for-each select="aggregate_funcs/aggregate_func">                             	                     
			                         	<option>
			                         		<xsl:if test="id = $item/aggregate_func_id"> 
												<xsl:attribute name="selected">selected</xsl:attribute> 
											</xsl:if>
			                         		<xsl:attribute name="value">
			                         			<xsl:value-of select="id"/>
			                         		</xsl:attribute>
			                         		<xsl:value-of select="name"/>                         		                       	
			                         	</option>
			                         </xsl:for-each>                         
			                    </select>
                       		</xsl:when>
                       		<xsl:otherwise>
                       			<span><xsl:value-of select="item/aggregate_func"/></span>
                       		</xsl:otherwise>
                       </xsl:choose>                                          
                     </div>
                   </div>
                   <xsl:if test="item/type_id = 'NUMBER'">
                   	 <div class="col-sm-6 col-md-6">
	                     <div class="form-group">
	                       <label class="form-label">Précision <span class="form-required">*</span></label>
	                       <div class="col">
		                       <input name="precision" type="number" class="form-control" placeholder="Saisir un entier" required="">
		                       		<xsl:if test="item">
							            <xsl:attribute name="value">
							              <xsl:value-of select="item/precision"/>
							            </xsl:attribute>
							        </xsl:if>
		                       </input>
		                   </div>                       
	                     </div>
	                   </div>
	                   <div class="col-sm-6 col-md-4">
	                    	<div class="form-group">
	                         <label class="custom-control custom-checkbox">
	                            <input name="apply_thousands_separator" type="checkbox" class="custom-control-input">
	                            	<xsl:if test="item/apply_thousands_separator='true'"> 
										<xsl:attribute name="checked">checked</xsl:attribute> 
									</xsl:if>
	                            </input>
	                            <span class="custom-control-label">Appliquer séparateur de milliers ?</span>
	                          </label>
	                      </div>
	                    </div>
                   </xsl:if> 
                   <xsl:if test="item/type_id = 'STRING'">
                   	 <div class="col-sm-6 col-md-6">
	                     <div class="form-group">
	                       <label class="form-label">Mise en forme</label>
	                       <div class="row gutters-sm">
		                       <div class="col">
			                       <input name="markup" type="text" class="form-control" placeholder="Saisir une fonction de mise en forme">
			                       		<xsl:if test="item">
								            <xsl:attribute name="value">
								              <xsl:value-of select="item/markup"/>
								            </xsl:attribute>
								        </xsl:if>
			                       </input>
			                   </div>
			                   <span class="col-auto align-self-center">
	                              <span class="form-help" data-toggle="popover" data-placement="top"
									  data-content="&lt;p&gt;upper($$): mettre en majuscules&lt;br\&gt; lower($$): mettre en miniscules&lt;br\&gt; split_part($$, '-', 2): extraire la 2ème partie de la chaîne séparée par tiret. Vous pouvez combiner ces expressions.&lt;/p&gt;
	                              ">?</span>
	                          </span>
			               </div>                       
	                     </div>
	                   </div>
                   </xsl:if>                                                      
                  </div>
                </div>
                <div class="card-footer text-right">
                 <div class="d-flex">
                    <a href="/{short_name}-setting/edit?id={item/indicator_id}&amp;shortname={short_name}&amp;source={source}" class="btn btn-link">Annuler</a>
                    <button type="submit" class="btn btn-primary ml-auto">Enregistrer</button>
                  </div>
               	</div>
             </form>
        </div>
	</xsl:template>  	
	<xsl:template match="page" mode="customScript"></xsl:template>	
</xsl:stylesheet>