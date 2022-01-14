<?xml version="1.0"?>
<xsl:stylesheet 
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:interactive="http://www.minlessika.com/IndicatorOptions"
	version="2.0">
	
	<xsl:template name="indicator_options">
		<xsl:param name="identity"/>	
		<xsl:param name="activity_selected"/>
		<xsl:param name="shortname"/>
   		
       	<div class="item-action dropdown">
            <a href="javascript:void(0)" data-toggle="dropdown" class="icon">
            	<i class="fe fe-more-vertical"></i>
            </a>
            <xsl:if test="$identity/id = $activity_selected/owner_id">
            <div class="dropdown-menu dropdown-menu-right">	                            						                      	
              	<a ng-href="/{$shortname}-setting/edit?id={{{{id}}}}&amp;shortname={$shortname}&amp;source=activity{$activity_selected/id}" class="dropdown-item">
 		  			<i class="dropdown-icon fa fa-cog"></i> Propriétés
 		  		</a>
 		  		<xsl:if test="not($activity_selected/designer_id) or $identity/id = $activity_selected/designer_id">
	     			<a ng-href="/indicator/delete?id={{{{id}}}}&amp;source=activity{$activity_selected/id}" class="dropdown-item" onclick="return confirm('Voulez-vous supprimer cet indicateur ?');">
		   		  		<i class="dropdown-icon fe fe-trash"></i> Supprimer
		   		  	</a>
	     		</xsl:if>			         		  				         			 		  						         		  	
            </div>	
     		</xsl:if>     		
         </div>				                 
	</xsl:template>
</xsl:stylesheet>