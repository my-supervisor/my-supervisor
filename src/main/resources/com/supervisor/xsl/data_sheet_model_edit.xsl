<?xml version="1.0"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" 
                version="2.0">
	<xsl:output method="html" cdata-section-elements="script style" include-content-type="no" doctype-system="about:legacy-compat" encoding="UTF-8" indent="yes"/>
  	<xsl:strip-space elements="*"/>
  	<xsl:include href="/com/supervisor/xsl/layout.xsl"/>
  	<xsl:template match="page" mode="head">
	    <title>
	      <xsl:text>Supervisor - Minlessika - Configurer un modèle</xsl:text>
	    </title>
	</xsl:template>
	<xsl:template match="page" mode="body">
		<div class="col-12">
           <form class="card" action="/collect/model/save" method="post">
               <xsl:if test="item and item/id">
		            <xsl:attribute name="action">
		              <xsl:text>/collect/model/save?id=</xsl:text><xsl:value-of select="item/id"/>
		            </xsl:attribute>
		        </xsl:if>
		       <div class="card-header">
		       		<xsl:if test="item and item/id">
			            <h3 class="card-title"><xsl:text>Modifier modèle </xsl:text><i><xsl:value-of select="item/name"/></i></h3>
			        </xsl:if>
			        <xsl:if test="not(item) or (item and not(item/id))">
			            <h3 class="card-title">Nouveau modèle</h3>
			        </xsl:if>
			        <div class="card-options">
	                  <label class="custom-switch mr-4">
	                     <input name="active" type="checkbox" class="custom-switch-input">
	                     	<xsl:if test="(not(item) or not(item/id)) or (item and item/active='true')"> 
								<xsl:attribute name="checked">checked</xsl:attribute> 
							</xsl:if>
	                     </input>
	                     <span class="custom-switch-indicator"></span>
	                     <span class="custom-switch-description">Activé</span>
	                   </label>
	                   <xsl:if test="item and item/id">
	                    	<a role="button" class="btn btn-primary mr-1" href="/collect/sheet/edit?model={item/id}">
			                  	<i class="fa fa-plus"></i> Nouvelle feuille
			                </a>
			                <a role="button" class="btn btn-primary" href="/collect/model/data/import/edit?model={item/id}">
			                  	<i class="fa fa-plus"></i> Importer données
			                </a>
	                    </xsl:if>
	                </div>
		       </div>
               <div class="card-body">               	
                 <div class="row">
                   <div class="col-sm-6 col-md-6">
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
                   <xsl:if test="not(item) or not(item/id)">
                   		<div class="col-sm-6 col-md-6">
	                     <div class="form-group">
	                       <label class="form-label">Activité <span class="form-required">*</span></label>
	                       <select name="activity_id" class="form-control" required="">                         
	                         <xsl:variable name="item" select="item" />
	                         <xsl:for-each select="own_activities/activity">                             	                     
	                         	<option>
	                         		<xsl:if test="$item and id = $item/activity_id"> 
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
                   </xsl:if>
                   <xsl:if test="item and item/id">
                   		<input name="activity_id" type="text" class="form-control" hidden="" value="{item/activity_id}" />
                   		<div class="col-sm-6 col-md-6">
	                     <div class="form-group">
	                       <label class="form-label">Activité</label>
	                       <input type="text" class="form-control" disabled="" value="{item/activity}"/>
	                     </div>
	                   </div>                   		
                   </xsl:if>
                   <div class="col-md-12">
                     <div class="form-group">
                       <label class="form-label">Description</label>
                       <textarea name="description" rows="1" class="form-control" placeholder="Faire une description...">
                       		<xsl:if test="item">
					            <xsl:value-of select="item/description"/>
					        </xsl:if>
                       </textarea>
                      </div>
                    </div>
                    <div class="col-md-3">
                    	<div class="form-group">
                         <label class="custom-control custom-checkbox">
                            <input name="can_merge_at_same_date" type="checkbox" class="custom-control-input">
                            	<xsl:if test="item and item/can_merge_at_same_date='true'"> 
									<xsl:attribute name="checked">checked</xsl:attribute> 
								</xsl:if>
                            </input>
                            <span class="custom-control-label">Fusionner à la même date ?</span>
                          </label>
                      </div>
                    </div>
                    <xsl:if test="item/id">
                    	<div class="col-md-12 mb-0">
		                     <div class="form-group">
		                       <label class="form-label">Champs de données</label>
		                       <div class="table-responsive form-control">
				                    <table class="table card-table table-vcenter text-nowrap">
				                      <thead>
				                        <tr>
				                          <th class="w-1">Ordre d'affichage</th>
				                          <th>Libellé</th>
				                          <th>Type</th>
				                          <th>Est obligatoire ?</th>
				                          <th colspan="2" class="text-right">
							                <div class="dropdown">
								                <a href="javascript:void(0)" role="button" class="dropdown-toggle btn btn-sm btn-primary mr-1" id="dropdownMenuButton" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
					                       			<i class="fa fa-plus"></i> Champ
					                       		</a>
								                <div class="dropdown-menu" aria-labelledby="dropdownMenuButton">
												    <a class="dropdown-item" href="/collect/field/simple/edit?model={item/id}">Simple</a>
												    <a class="dropdown-item" href="/collect/field/list/edit?model={item/id}">Liste</a>
												</div>
											</div>
							                <a href="/collect/table/edit?model={item/id}" role="button" class="btn btn-sm btn-primary">
							                  	<i class="fa fa-plus"></i> Table
							                </a>
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
					                               		<xsl:when test="type_id='TABLE'">
					                               			<a class="icon" href="/collect/table/edit?id={id}&amp;model={model_id}">
									                         	<i class="fe fe-edit"></i>
									                        </a>
					                               		</xsl:when>
					                               		<xsl:otherwise>
					                               		    <xsl:choose>
					                               		    	<xsl:when test="style_id = 'SIMPLE'">
					                               		    		<a class="icon" href="/collect/field/simple/edit?id={id}&amp;model={model_id}">
											                         	<i class="fe fe-edit"></i>
											                        </a>
					                               		    	</xsl:when>
					                               		    	<xsl:when test="style_id = 'LIST'">
					                               		    		<a class="icon" href="/collect/field/list/edit?id={id}&amp;model={model_id}">
											                         	<i class="fe fe-edit"></i>
											                        </a>
					                               		    	</xsl:when>
					                               		    	<xsl:otherwise>
					                               		    		<a class="icon" href="/collect/field/predefined-list/edit?id={id}&amp;model={model_id}">
											                         	<i class="fe fe-edit"></i>
											                        </a>
					                               		    	</xsl:otherwise>
					                               		    </xsl:choose>					                               			
					                               		</xsl:otherwise>
					                               </xsl:choose>
							                       
							                   </td>
							                   <td>
							                       <a class="icon" href="/collect/field/delete?id={id}&amp;model={model_id}" onclick="return confirm('Etes-vous sûr de vouloir supprimer ce champ ?');">
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
                  </div>
                </div>
                <div class="card-footer text-right">
                 <div class="d-flex">                    
                    <a role="button" class="btn btn-primary" href="/collect/model">
	                  	<i class="fa fa-arrow-left"></i> Retour
	                </a>	                
                    <button type="submit" class="btn btn-primary ml-auto">Enregistrer</button>
                  </div>
               	</div>               	
             </form>
        </div>            
	</xsl:template>  		
	<xsl:template match="page" mode="customScript"></xsl:template>
</xsl:stylesheet>