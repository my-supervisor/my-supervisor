<?xml version="1.0" encoding="UTF-8"?>
<!--
Copyright (c) 2018-2022 Minlessika

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to read
the Software only. Permissions is hereby NOT GRANTED to use, copy, modify,
merge, publish, distribute, sublicense, and/or sell copies of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NON-INFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
-->
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:interactive="http://www.minlessika.com/IndicatorOptions" version="2.0">
  <xsl:template name="indicator_options">
    <xsl:param name="identity"/>
    <xsl:param name="activity_selected"/>
    <xsl:param name="shortname"/>
    <div class="item-action dropdown">
      <a href="javascript:void(0)" data-toggle="dropdown" class="icon">
        <i class="fe fe-more-vertical"/>
      </a>
      <xsl:if test="$identity/id = $activity_selected/owner_id">
        <div class="dropdown-menu dropdown-menu-right">
          <a ng-href="/{$shortname}-setting/edit?id={{{{id}}}}&amp;shortname={$shortname}&amp;source=activity{$activity_selected/id}" class="dropdown-item"><i class="dropdown-icon fa fa-cog"/> Propriétés
 		  		</a>
          <xsl:if test="not($activity_selected/designer_id) or $identity/id = $activity_selected/designer_id">
            <a ng-href="/indicator/delete?id={{{{id}}}}&amp;source=activity{$activity_selected/id}" class="dropdown-item" onclick="return confirm('Voulez-vous supprimer cet indicateur ?');"><i class="dropdown-icon fe fe-trash"/> Supprimer
		   		  	</a>
          </xsl:if>
        </div>
      </xsl:if>
    </div>
  </xsl:template>
</xsl:stylesheet>
