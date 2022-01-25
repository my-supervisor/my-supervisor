<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" 
                version="2.0">
	<xsl:output method="html" cdata-section-elements="script style" include-content-type="no" doctype-system="about:legacy-compat" encoding="UTF-8" indent="yes"/>
  	<xsl:strip-space elements="*"/>
  	<xsl:include href="/xsl/layout.xsl"/>
  	<xsl:template match="page" mode="head">
	    <title>
	      <xsl:text>Supervisor - Minlessika - Configurer un argument d'une expression</xsl:text>
	    </title>
	</xsl:template>
	<xsl:template match="page" mode="body">
		<div class="col-12">
           <form class="card" action="/collect/aggregated-model/formular/expression/argument/save" method="post">
		        <div class="card-header">
		       		<xsl:if test="item/id">
			            <h3 class="card-title">Modifier argument <xsl:value-of select="item/order"/> - expression <i><xsl:value-of select="item/expression"/></i></h3>
			        </xsl:if>
			        <div class="card-options">
	                   <xsl:if test="item/id and item/type_id = type_id">
	                        <div class="dropdown">
							  <a class="btn btn-primary dropdown-toggle" href="javascript:void(0)" id="dropdownMenuButton" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
							    <i class="fa fa-plus"></i> Transformer en
							  </a>
							  <div class="dropdown-menu" aria-labelledby="dropdownMenuButton">
							    <xsl:for-each select="expression_arg_types/expression_arg_type">
							    	<xsl:if test="not(id = ../../type_id)">
							    		<a class="dropdown-item" href="/collect/aggregated-model/formular/expression/argument/edit?id={../../item/id}&amp;type={id}&amp;expression={../../item/expression_id}&amp;formular={../../formular_id}&amp;model={../../model_id}" onclick="return confirm('Voulez-vous changer le type de l'argument ?');">
								    		<xsl:value-of select="name"/>
								    	</a>
							    	</xsl:if>							    	
							    </xsl:for-each>
							  </div>
							</div>
	                    </xsl:if>
	                </div>			        
		       </div>
               <div class="card-body"> 
               	 <input hidden="hidden" name="id" value="{item/id[1]}"/>
                 <input hidden="hidden" name="model_id" value="{model_id}"/>
                 <input hidden="hidden" name="type_id" value="{type_id}"/>
                 <input hidden="hidden" name="formular_id" value="{formular_id}"/>
                 <input hidden="hidden" name="expression_id" value="{item/expression_id}"/>               
                 <xsl:choose>
                 	<xsl:when test="type_id = 'VALUE'">
                 		<div class="row">
		                   <div class="col-sm-6 col-md-6">
		                     <div class="form-group">
		                       <label class="form-label">Type <span class="form-required">*</span></label>
		                       <select name="value_type_id" class="form-control" required="">                         
		                         <xsl:variable name="arg" select="arg" />
		                         <xsl:for-each select="datafieldtypes/datafieldtype">                             	                     
		                         	<option>
		                         		<xsl:if test="id = $arg/value_type_id"> 
											<xsl:attribute name="selected">selected</xsl:attribute> 
										</xsl:if>
		                         		<xsl:attribute name="value">
		                         			<xsl:value-of select="id"/>
		                         		</xsl:attribute>
		                         		<xsl:value-of select="name"/>                         		                       	
		                         	</option>
		                         </xsl:for-each>                         
		                       </select>
		                     </div>
		                   </div>
		                   <div class="col-sm-6 col-md-6">
		                      <div class="form-group">
		                        <label class="form-label">Valeur <span class="form-required">*</span></label>
		                        <input name="value" type="text" class="form-control" placeholder="Saisir une valeur" required="" value="{arg/value}"/>
		                      </div>
		                   </div>                   
		                </div>
                 	</xsl:when>
                 	<xsl:when test="type_id = 'DATA_FIELD'">
                 		<div class="row">
		                   <div class="col-sm-6 col-md-6">
		                     <div class="form-group">
		                       <label class="form-label">Champ de données <span class="form-required">*</span></label>
		                       <select name="field_id" class="form-control" required="">                         
		                         <xsl:variable name="arg" select="arg" />
		                         <xsl:for-each select="datafields/datafield">                             	                     
		                         	<option>
		                         		<xsl:if test="id = $arg/field_id"> 
											<xsl:attribute name="selected">selected</xsl:attribute> 
										</xsl:if>
		                         		<xsl:attribute name="value">
		                         			<xsl:value-of select="id"/>
		                         		</xsl:attribute>
		                         		<xsl:value-of select="name"/>                         		                       	
		                         	</option>
		                         </xsl:for-each>                         
		                       </select>
		                     </div>
		                   </div>                  
		                </div>
                 	</xsl:when>
                 	<xsl:when test="type_id = 'PARAMETER'">
                 		<div class="row">
		                   <div class="col-sm-6 col-md-6">
		                     <div class="form-group">
		                       <label class="form-label">Paramètre <span class="form-required">*</span></label>
		                       <select name="param_id" class="form-control" required="">                         
		                         <xsl:variable name="arg" select="arg" />
		                         <xsl:for-each select="rule_params/rule_param">                             	                     
		                         	<option>
		                         		<xsl:if test="id = $arg/param_id"> 
											<xsl:attribute name="selected">selected</xsl:attribute> 
										</xsl:if>
		                         		<xsl:attribute name="value">
		                         			<xsl:value-of select="id"/>
		                         		</xsl:attribute>
		                         		<xsl:value-of select="name"/>                         		                       	
		                         	</option>
		                         </xsl:for-each>                         
		                       </select>
		                     </div>
		                   </div>                  
		                </div>
                 	</xsl:when>
                 	<xsl:when test="type_id = 'FORMULAR'">
                 		<div class="row">
		                   <div class="col-sm-6 col-md-6">
		                     <div class="form-group">
		                       <label class="form-label">Formule <span class="form-required">*</span></label>
		                       <select name="target_formular_id" class="form-control" required="">                         
		                         <xsl:variable name="arg" select="arg" />
		                         <xsl:for-each select="rule_formulars/rule_formular"> 
		                            <xsl:if test="not(id = ../../formular_id)">
		                            	<option>
			                         		<xsl:if test="id = $arg/formular_id"> 
												<xsl:attribute name="selected">selected</xsl:attribute> 
											</xsl:if>
			                         		<xsl:attribute name="value">
			                         			<xsl:value-of select="id"/>
			                         		</xsl:attribute>
			                         		<xsl:value-of select="name"/>                         		                       	
			                         	</option>
		                            </xsl:if>                            	                     		                         	
		                         </xsl:for-each>                         
		                       </select>
		                     </div>
		                   </div>                  
		                </div>
                 	</xsl:when>
                 	<xsl:when test="type_id = 'EXPRESSION'">
                 		<div class="row">
		                   <div class="col-sm-6 col-md-6">
		                     <div class="form-group">
		                       <label class="form-label">Expression <span class="form-required">*</span></label>
		                       <select name="target_expr_id" class="form-control" required="">                         
		                         <xsl:variable name="arg" select="arg" />
		                         <xsl:for-each select="formular_expressions/formular_expression"> 
		                            <xsl:if test="not(id = ../../item/expression_id)">
		                            	<option>
			                         		<xsl:if test="id = $arg/target_expr_id"> 
												<xsl:attribute name="selected">selected</xsl:attribute> 
											</xsl:if>
			                         		<xsl:attribute name="value">
			                         			<xsl:value-of select="id"/>
			                         		</xsl:attribute>
			                         		<xsl:value-of select="full_name"/>                         		                       	
			                         	</option>
		                            </xsl:if>                            	                     		                         	
		                         </xsl:for-each>                         
		                       </select>
		                     </div>
		                   </div>                  
		                </div>
                 	</xsl:when>
                 	<xsl:otherwise>
                 	
                 	</xsl:otherwise>
                 </xsl:choose>
                </div>
                <div class="card-footer text-right">
                 <div class="d-flex">
                    <xsl:choose>
                    	<xsl:when test="item/expression_type_id = 'SIMPLE'">
                    		<a href="/collect/aggregated-model/formular/simple-expression/edit?id={item/expression_id}&amp;model={model_id}&amp;formular={formular_id}" class="btn btn-link">Annuler</a>
                    	</xsl:when>
                    	<xsl:when test="item/expression_type_id = 'CASE'">
                    		<a href="/collect/aggregated-model/formular/case-expression/edit?id={item/expression_id}&amp;model={model_id}&amp;formular={formular_id}" class="btn btn-link">Annuler</a>
                    	</xsl:when>
                    	<xsl:otherwise>
                    	 	
                    	</xsl:otherwise>
                    </xsl:choose>                    
                    <button type="submit" class="btn btn-primary ml-auto">Enregistrer</button>
                  </div>
               	</div>
             </form>
        </div>
	</xsl:template> 
	<xsl:template match="page" mode="customScript"></xsl:template> 		
</xsl:stylesheet>