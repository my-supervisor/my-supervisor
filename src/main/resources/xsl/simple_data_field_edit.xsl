<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" 
                version="2.0">
	<xsl:output method="html" cdata-section-elements="script style" include-content-type="no" doctype-system="about:legacy-compat" encoding="UTF-8" indent="yes"/>
  	<xsl:strip-space elements="*"/>
  	<xsl:include href="/xsl/layout.xsl"/>
  	<xsl:template match="page" mode="head">
	    <title>
	      <xsl:text>Supervisor - Minlessika - Configurer un champ simple</xsl:text>
	    </title>
	</xsl:template>
	<xsl:template match="page" mode="body">
		<div class="col-12">
           <form class="card" action="/collect/field/simple/save" method="post">
               <xsl:if test="item and item/id">
		            <xsl:attribute name="action">
		              <xsl:text>/collect/field/simple/save?id=</xsl:text><xsl:value-of select="item/id"/>
		            </xsl:attribute>
		        </xsl:if>
		        <div class="card-header">
		            <xsl:choose>
		            	<xsl:when test="item/id">
		            		<h3 class="card-title"><xsl:text>Modifier </xsl:text><i><xsl:value-of select="item/name"/></i> - <xsl:value-of select="model/name"/></h3>
		            	</xsl:when>
		            	<xsl:otherwise>
		            		<xsl:choose>
				            	<xsl:when test="table">
				            		<h3 class="card-title">Nouveau champ simple - <xsl:value-of select="table/name"/></h3>
				            	</xsl:when>
				            	<xsl:otherwise>
				            		<h3 class="card-title">Nouveau champ simple - <xsl:value-of select="model/name"/></h3>
				            	</xsl:otherwise>
				            </xsl:choose>
		            	</xsl:otherwise>
		            </xsl:choose>
			        <div class="card-options">
	                  <xsl:choose>
	                  	<xsl:when test="table">
	                  		<a role="button" class="btn btn-primary" href="/collect/table/edit?id={table/id}&amp;model={table_model_id}">
			                  	<i class="fa fa-arrow-left"></i> Retour
			                  </a>	                  		
	                  	</xsl:when>
	                  	<xsl:otherwise>
	                  		<a role="button" class="btn btn-primary" href="/collect/model/edit?id={model/id}">
			                  	<i class="fa fa-arrow-left"></i> Retour
			                </a>	                  		
	                  	</xsl:otherwise>
	                  </xsl:choose>
	                </div>			        
		       </div>
               <div class="card-body"> 
                 <input hidden="hidden" name="model_id" value="{model/id}"/>
                 <xsl:if test="table">
                 	<input hidden="hidden" name="table_id" value="{table/id}"/>
                 	<input hidden="hidden" name="table_model_id" value="{table_model_id}"/>
                 </xsl:if>                               
                 <div class="row">
                   <div class="col-md-12">
                      <div class="form-group">
                        <label class="form-label">Intitulé <span class="form-required">*</span></label>
                        <input name="name" type="text" class="form-control" placeholder="Saisir un nom" required="">
                        	<xsl:if test="item">
					            <xsl:attribute name="value">
					              <xsl:value-of select="item/name"/>
					            </xsl:attribute>
					        </xsl:if>
                        </input>
                      </div>
                   </div>
                   <div class="col-sm-6 col-md-3">
                     <div class="form-group">
                       <label class="form-label">Position <span class="form-required">*</span></label>
                       <div class="row gutters-sm">
                       		<div class="col">
                       			<input name="order" type="number" class="form-control" placeholder="Saisir un entier" required="">
		                       		<xsl:if test="item">
							            <xsl:attribute name="value">
							              <xsl:value-of select="item/order"/>
							            </xsl:attribute>
							        </xsl:if>
		                       </input>
                       		</div>
                       		<span class="col-auto align-self-center">
	                              <span class="form-help" data-toggle="popover" data-placement="top"
									  data-content="&lt;p&gt;Position est un nombre entier compris entre des entiers ayant leur dizaine compris entre 1, 2, 3 et 4. Les positions appartenant à la même unité sont sur la même ligne. Ainsi, nous n'acceptons que 4 éléments au plus par ligne.&lt;/p&gt;
	                              ">?</span>
	                          </span>
                       </div>
                       
                     </div>
                   </div>
                   <div class="col-sm-6 col-md-3">
                     <div class="form-group">
                       <label class="form-label">Type <span class="form-required">*</span></label>
                       <select name="type_id" class="form-control" required="">                         
                         <xsl:variable name="datafield" select="item" />
                         <xsl:for-each select="datafieldtypes/datafieldtype">                             	                     
                         	<option>
                         		<xsl:if test="$datafield and id = $datafield/type_id"> 
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
                   <div class="col-sm-6 col-md-3">
                     <div class="form-group">
                       <label class="form-label">Valeur par défaut </label>
                       <input name="default_value" type="text" class="form-control" placeholder="Saisir une valeur de même type">
                       		<xsl:if test="item">
					            <xsl:attribute name="value">
					              <xsl:value-of select="item/default_value"/>
					            </xsl:attribute>
					        </xsl:if>
                       </input>
                     </div>
                   </div>                   
                   <div class="col-md-12">
                     <div class="form-group">
                       <label class="form-label">Description</label>
                       <textarea name="description" rows="5" class="form-control" placeholder="Faire une description...">
                       		<xsl:if test="item">
					            <xsl:value-of select="item/description"/>
					        </xsl:if>
                       </textarea>
                      </div>
                    </div>
                    <div class="col-md-3">
                    	<div class="form-group">
                         <label class="custom-control custom-checkbox">
                            <input name="is_mandatory" type="checkbox" class="custom-control-input">
                            	<xsl:if test="not(item) or (item and item/is_mandatory='true')"> 
									<xsl:attribute name="checked">checked</xsl:attribute> 
								</xsl:if>
                            </input>
                            <span class="custom-control-label">Est obligatoire ?</span>
                          </label>
                      </div>
                    </div>
                    <div class="col-md-3">
                    	<div class="form-group">
                         <label class="custom-control custom-checkbox">
                            <input name="take_last_value" type="checkbox" class="custom-control-input">
                            	<xsl:if test="item and item/take_last_value='true'"> 
									<xsl:attribute name="checked">checked</xsl:attribute> 
								</xsl:if>
                            </input>
                            <span class="custom-control-label">Prendre dernière valeur ?</span>
                          </label>
                      </div>
                    </div>
                    <div class="col-md-3">
                    	<div class="form-group">
                         <label class="custom-control custom-checkbox">
                            <input name="must_edit_once" type="checkbox" class="custom-control-input">
                            	<xsl:if test="item and item/must_edit_once='true'"> 
									<xsl:attribute name="checked">checked</xsl:attribute> 
								</xsl:if>
                            </input>
                            <span class="custom-control-label">Editer une seule fois ?</span>
                          </label>
                      </div>
                    </div>
                  </div>
                </div>
                <div class="card-footer text-right">
                 <div class="d-flex">
                    <xsl:choose>
                    	<xsl:when test="table">
                    		<a href="/collect/table/edit?id={table/id}&amp;model={table_model_id}" class="btn btn-link">Annuler</a>
                    	</xsl:when>
                    	<xsl:otherwise>
                    		<a href="/collect/model/edit?id={model/id}" class="btn btn-link">Annuler</a>
                    	</xsl:otherwise>
                    </xsl:choose>
                    <button type="submit" class="btn btn-primary ml-auto">Enregistrer</button>
                  </div>
               	</div>
             </form>
        </div>        
	</xsl:template>  
	<xsl:template match="page" mode="customScript">

	</xsl:template>		
</xsl:stylesheet>