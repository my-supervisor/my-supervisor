<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" 
                version="2.0">
	<xsl:output method="html" cdata-section-elements="script style" include-content-type="no" doctype-system="about:legacy-compat" encoding="UTF-8" indent="yes"/>
  	<xsl:strip-space elements="*"/>
  	<xsl:include href="/com/supervisor/xsl/layout.xsl"/>
  	<xsl:template match="page" mode="head">
	    <title>
	      <xsl:text>Supervisor - Minlessika - Configurer une table</xsl:text>
	    </title>
	</xsl:template>
	<xsl:template match="page" mode="body">
		<div class="col-12" ng-controller="AppCtrl as vm">
           <form class="card" action="/collect/table/save" method="post">
               <xsl:if test="item and item/id">
		            <xsl:attribute name="action">
		              <xsl:text>/collect/table/save?id=</xsl:text><xsl:value-of select="item/id"/>
		            </xsl:attribute>
		        </xsl:if>
		        <div class="card-header">
		       		<xsl:if test="item and item/id">
			            <h3 class="card-title"><xsl:text>Modifier </xsl:text><i><xsl:value-of select="item/name"/></i> - <xsl:value-of select="model/name"/></h3>
			        </xsl:if>
			        <xsl:if test="not(item) or not(item/id)">
			            <h3 class="card-title">Nouvelle table - <xsl:value-of select="model/name"/></h3>
			        </xsl:if>
			        <div class="card-options">
	                  <a role="button" class="btn btn-primary" href="/collect/model/edit?id={model/id}">
	                  	<i class="fa fa-arrow-left"></i> Retour
	                  </a>
	                </div>
		       </div>
               <div class="card-body"> 
                 <input hidden="hidden" name="model_id" value="{model/id}"/>               
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
                   <xsl:if test="item/id">
                    	<div class="col-md-12 mb-0">
		                     <div class="form-group">
		                       <label class="form-label pull-left">Colonnes</label>	
		                       <div class="dropdown pull-right mb-1">
					                <a href="javascript:void(0)" role="button" class="dropdown-toggle btn btn-sm btn-primary" id="dropdownMenuButton" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
		                       			<i class="fa fa-plus"></i> Champ
		                       		</a>
					                <div class="dropdown-menu" aria-labelledby="dropdownMenuButton">
									    <a class="dropdown-item" href="/collect/field/simple/edit?model={item/model_id}&amp;table={item/id}&amp;table_model={model/id}">Simple</a>
									    <a class="dropdown-item" href="/collect/field/list/edit?model={item/model_id}&amp;table={item/id}&amp;table_model={model/id}">Liste</a>
									</div>
							   </div>	                       
		                       <div class="table-responsive form-control">		                       		
				                    <table class="table card-table table-vcenter text-nowrap">
				                      <thead>
				                        <tr>
				                          <th class="w-1">Ordre d'affichage</th>
				                          <th>Libellé</th>
				                          <th>Type</th>
				                          <th>Est obligatoire ?</th>
				                          <th colspan="2" class="text-right">
							                
				                          </th>
				                        </tr>
				                      </thead>
				                      <tbody>	
				                        <xsl:for-each select="datafields/datafield">
				                        	<tr>
					                          <td>
					                          	<span class="text-muted"><xsl:value-of select="order"/></span>
					                          </td>
					                          <td>
					                          	<xsl:value-of select="name"/>
					                          </td>
					                          <td>
					                            <xsl:value-of select="type"/>
					                          </td>
					                          <td>
					                            <xsl:choose>
					                            	<xsl:when test="is_mandatory = 'true'">Oui</xsl:when>
					                            	<xsl:otherwise>Non</xsl:otherwise>
					                            </xsl:choose>
					                          </td>
					                          <td>							       
							                       <xsl:choose>
			                               		    	<xsl:when test="style_id = 'SIMPLE'">
			                               		    		<a class="icon" href="/collect/field/simple/edit?id={id}&amp;model={model_id}&amp;table={../../item/id}&amp;table_model={../../model/id}">
									                         	<i class="fe fe-edit"></i>
									                        </a>
			                               		    	</xsl:when>
			                               		    	<xsl:when test="style_id = 'LIST'">
			                               		    		<a class="icon" href="/collect/field/list/edit?id={id}&amp;model={model_id}&amp;table={../../item/id}&amp;table_model={../../model/id}">
									                         	<i class="fe fe-edit"></i>
									                        </a>
			                               		    	</xsl:when>
			                               		    	<xsl:otherwise>
			                               		    		<a class="icon" href="/collect/field/predefined-list/edit?id={id}&amp;model={model_id}&amp;table={../../item/id}&amp;table_model={../../model/id}">
									                         	<i class="fe fe-edit"></i>
									                        </a>
			                               		    	</xsl:otherwise>
			                               		    </xsl:choose>
							                   </td>
							                   <td>
							                       <a class="icon" href="/collect/field/delete?id={id}&amp;model={model_id}&amp;table={../../item/id}&amp;table_model={../../model/id}" onclick="return confirm('Voulez-vous supprimer ce champ ?');">
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
                    </xsl:if>             
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
                  </div>
                </div>
                <div class="card-footer text-right">
                 <div class="d-flex">
                    <a href="/collect/model/edit?id={model/id}" class="btn btn-link">Annuler</a>
                    <button type="submit" class="btn btn-primary ml-auto">Enregistrer</button>
                  </div>
               	</div>
             </form>
        </div>
	</xsl:template>  		
	<xsl:template match="page" mode="customScript"></xsl:template>
</xsl:stylesheet>