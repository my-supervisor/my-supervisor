<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" 
                version="2.0">
	<xsl:output method="html" cdata-section-elements="script style" include-content-type="no" doctype-system="about:legacy-compat" encoding="UTF-8" indent="yes"/>
  	<xsl:strip-space elements="*"/>
  	<xsl:include href="/xsl/layout.xsl"/>
  	<xsl:template match="page" mode="head">
	    <title>
	      <xsl:text>Supervisor - Minlessika - Configurer un champ liste</xsl:text>
	    </title>
	</xsl:template>
	<xsl:template match="page" mode="body">
		<div class="col-12"  ng-controller="AppCtrl as vm">
           <form class="card" action="/collect/field/list/save" method="post">
               <xsl:if test="item and item/id">
		            <xsl:attribute name="action">
		              <xsl:text>/collect/field/list/save?id=</xsl:text><xsl:value-of select="item/id"/>
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
				            		<h3 class="card-title">Nouveau champ liste - <xsl:value-of select="table/name"/></h3>
				            	</xsl:when>
				            	<xsl:otherwise>
				            		<h3 class="card-title">Nouveau champ liste - <xsl:value-of select="model/name"/></h3>
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
                       <label class="form-label">Type<span class="form-required">*</span></label>
                       <select name="type_id" class="form-control" required="">                         
                         <xsl:variable name="item" select="item" />
                         <xsl:for-each select="datafieldtypes/datafieldtype">                             	                     
                         	<option>
                         		<xsl:if test="$item and id = $item/type_id"> 
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
                       <label class="form-label">Order direction<span class="form-required">*</span></label>
                       <select name="order_direction_id" class="form-control" required="">                         
                         <xsl:variable name="item" select="item" />
                         <xsl:for-each select="order_directions/order_direction">                             	                     
                         	<option>
                         		<xsl:if test="$item and id = $item/order_direction_id"> 
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
                       <label class="form-label">Limite par page<span class="form-required">*</span></label>
                       <input name="limit" type="number" class="form-control" placeholder="Saisir un entier positif" required="" value="5">
                       		<xsl:if test="item">
					            <xsl:attribute name="value">
					              <xsl:value-of select="item/limit"/>
					            </xsl:attribute>
					        </xsl:if>
                       </input>
                     </div>
                   </div>
                   <xsl:if test="item/id">
                    	<div class="col-md-12 mb-0">
		                     <div class="form-group">
		                       <label class="form-label">Sources de données</label>
		                       <div class="table-responsive form-control">
				                    <table class="table card-table table-vcenter text-nowrap">
				                      <thead>
				                        <tr>
				                          <th class="w-1">N°</th>
				                          <th>Modèle</th>
				                          <th>Champ à afficher</th>
				                          <th>Ordonnée par</th>
				                          <th>Est activée ?</th>
				                          <th colspan="3" class="text-right">							                
							                <xsl:choose>
			                               		<xsl:when test="table/type_id='TABLE'">
			                               			<a href="/collect/field/list/source/edit?field={item/id}&amp;model={model/id}&amp;table={table/id}&amp;table_model={table/model_id}" role="button" class="btn btn-sm btn-primary">
							                         	<i class="fe fe-plus"></i> Nouvelle source
							                        </a>
			                               		</xsl:when>
			                               		<xsl:otherwise>
			                               		    <a href="/collect/field/list/source/edit?field={item/id}&amp;model={model/id}" role="button" class="btn btn-sm btn-primary">
							                         	<i class="fe fe-plus"></i> Nouvelle source
							                        </a>				                               			
			                               		</xsl:otherwise>
			                               </xsl:choose>	
				                          </th>
				                        </tr>
				                      </thead>
				                      <tbody>	
				                        <xsl:for-each select="list_data_field_sources/list_data_field_source">
				                        	<tr>
					                          <td>
					                          	<span class="text-muted"><xsl:value-of select="position()"/></span>
					                          </td>
					                          <td>
					                          	<xsl:value-of select="list_model"/>
					                          </td>
					                          <td>
					                          	<xsl:value-of select="field_to_display"/>
					                          </td>
					                          <td>
					                            <xsl:value-of select="order_field"/>
					                          </td>
					                          <td>
					                            <xsl:choose>
					                            	<xsl:when test="active = 'true'">Oui</xsl:when>
					                            	<xsl:otherwise>Non</xsl:otherwise>
					                            </xsl:choose>
					                          </td>
					                          <td>
					                          	   <xsl:choose>
					                               		<xsl:when test="../../table/type_id='TABLE'">
					                               			<xsl:choose>
							                               		<xsl:when test="active='true'">
							                               			<a class="btn btn-sm btn-danger" href="/collect/field/list/source/activate?id={id}&amp;field={field_id}&amp;model={model_id}&amp;active=false&amp;table={../../table/id}&amp;table_model={../../table/model_id}">
												                         Désactiver
												                    </a>
							                               		</xsl:when>
							                               		<xsl:otherwise>
							                               		    <a class="btn btn-sm btn-success" href="/collect/field/list/source/activate?id={id}&amp;field={field_id}&amp;model={model_id}&amp;active=true&amp;table={../../table/id}&amp;table_model={../../table/model_id}">
												                         Activer
												                    </a>				                               			
							                               		</xsl:otherwise>
							                               </xsl:choose>
					                               		</xsl:when>
					                               		<xsl:otherwise>
					                               		    <xsl:choose>
							                               		<xsl:when test="active='true'">
							                               			<a class="btn btn-sm btn-danger" href="/collect/field/list/source/activate?id={id}&amp;field={field_id}&amp;model={model_id}&amp;active=false">
												                         Désactiver
												                    </a>
							                               		</xsl:when>
							                               		<xsl:otherwise>
							                               		    <a class="btn btn-sm btn-success" href="/collect/field/list/source/activate?id={id}&amp;field={field_id}&amp;model={model_id}&amp;active=true">
												                         Activer
												                    </a>				                               			
							                               		</xsl:otherwise>
							                               </xsl:choose>				                               			
					                               		</xsl:otherwise>
					                               </xsl:choose>								                       							                       
							                   </td>
					                          <td>
					                               <xsl:choose>
					                               		<xsl:when test="../../table/type_id='TABLE'">
					                               			<a class="icon" href="/collect/field/list/source/edit?id={id}&amp;field={field_id}&amp;model={model_id}&amp;table={../../table/id}&amp;table_model={../../table/model_id}">
									                         	<i class="fe fe-edit"></i>
									                        </a>
					                               		</xsl:when>
					                               		<xsl:otherwise>
					                               		    <a class="icon" href="/collect/field/list/source/edit?id={id}&amp;field={field_id}&amp;model={model_id}">
									                         	<i class="fe fe-edit"></i>
									                        </a>				                               			
					                               		</xsl:otherwise>
					                               </xsl:choose>							                       
							                   </td>							                   
							                   <td>
							                   		<xsl:choose>
					                               		<xsl:when test="../../table/type_id='TABLE'">
					                               			<a class="icon" href="/collect/field/list/source/delete?id={id}&amp;field={field_id}&amp;model={model_id}&amp;table={../../table/id}&amp;table_model={../../table/model_id}" onclick="return confirm('Etes-vous sûr de vouloir supprimer cette source de données ?');">
										                         <i class="fe fe-trash"></i>
										                    </a>
					                               		</xsl:when>
					                               		<xsl:otherwise>
					                               		    <a class="icon" href="/collect/field/list/source/delete?id={id}&amp;field={field_id}&amp;model={model_id}" onclick="return confirm('Etes-vous sûr de vouloir supprimer cette source de données ?');">
										                         <i class="fe fe-trash"></i>
										                    </a>			                               			
					                               		</xsl:otherwise>
					                               </xsl:choose>							                       
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
                       <textarea name="description" rows="3" class="form-control" placeholder="Faire une description...">
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
                    <div class="col-md-3">
                    	<div class="form-group">
                         <label class="custom-control custom-checkbox">
                            <input name="can_be_updated" type="checkbox" class="custom-control-input">
                            	<xsl:if test="item and item/can_be_updated='true'"> 
									<xsl:attribute name="checked">checked</xsl:attribute> 
								</xsl:if>
                            </input>
                            <span class="custom-control-label">Peut être mise à jour ?</span>
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