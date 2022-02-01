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
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="2.0">
  <xsl:output method="html" cdata-section-elements="script style" include-content-type="no" doctype-system="about:legacy-compat" encoding="UTF-8" indent="yes"/>
  <xsl:strip-space elements="*"/>
  <xsl:include href="/xsl/layout.xsl"/>
  <xsl:template match="page" mode="head">
    <title>
      <xsl:text>Supervisor - Minlessika - Configurer une expression condition multiple de formule</xsl:text>
    </title>
  </xsl:template>
  <xsl:template match="page" mode="body">
    <div class="col-12">
      <form class="card" action="/collect/aggregated-model/formular/case-expression/save" method="post">
        <div class="card-header">
          <xsl:if test="item and item/id">
            <h3 class="card-title">Modifier expression condition multiple <xsl:value-of select="item/name"/> - formule <i><xsl:value-of select="formular"/></i></h3>
          </xsl:if>
          <xsl:if test="not(item) or not(item/id)">
            <h3 class="card-title">Nouvelle expression condition multiple - formule <i><xsl:value-of select="formular"/></i></h3>
          </xsl:if>
        </div>
        <div class="card-body">
          <xsl:if test="item/id">
            <input name="id" type="text" hidden="hidden" value="{item/id}"/>
          </xsl:if>
          <input name="formular_id" hidden="hidden" type="text" value="{formular_id}"/>
          <input name="model_id" hidden="hidden" type="text" value="{model_id}"/>
          <div class="row">
            <xsl:if test="item/id">
              <div class="col-md-12">
                <div class="form-group">
                  <label class="form-label">Valeur par défaut</label>
                  <div class="table-responsive form-control">
                    <table class="table card-table table-vcenter text-nowrap">
                      <tbody>
                        <tr>
                          <td>
                            <xsl:value-of select="item/default_value/name"/>
                          </td>
                          <td>
                            <a class="icon" href="/collect/aggregated-model/formular/expression/argument/edit?id={item/default_value/id}&amp;type={item/default_value/type_id}&amp;expression={item/id}&amp;formular={formular_id}&amp;model={model_id}">
                              <i class="fe fe-edit"/>
                            </a>
                          </td>
                        </tr>
                      </tbody>
                    </table>
                  </div>
                </div>
              </div>
              <div class="col-md-12 mb-0">
                <div class="form-group">
                  <label class="form-label">
		                       		Possibilités 
		                       		(<a href="/collect/aggregated-model/formular/case-expression/when-case/save?model={model_id}&amp;formular={formular_id}&amp;expression={item/id}" onclick="return confirm('Voulez-vous ajouter une nouvelle possibilité ?');"><i class="fa fa-plus"/> Nouvelle possibilité
		                       		 </a>)
		                       </label>
                  <div class="table-responsive form-control">
                    <table class="table card-table table-vcenter text-nowrap">
                      <thead>
                        <tr>
                          <th class="w-1">N°</th>
                          <th>Opérande 1</th>
                          <th>Comparaison</th>
                          <th>Opérande 2</th>
                          <th>Résultat</th>
                          <th/>
                        </tr>
                      </thead>
                      <tbody>
                        <xsl:for-each select="when_cases/when_case">
                          <tr>
                            <td>
                              <span class="text-muted">
                                <xsl:value-of select="position()"/>
                              </span>
                            </td>
                            <td>
                              <xsl:value-of select="left_arg/name"/>
                              <a class="icon pull-right" href="/collect/aggregated-model/formular/expression/argument/edit?id={left_arg/id}&amp;type={left_arg/type_id}&amp;expression={../../item/id}&amp;formular={../../formular_id}&amp;model={../../model_id}">
                                <i class="fe fe-edit"/>
                              </a>
                            </td>
                            <td>
                              <xsl:value-of select="comparator"/>
                              <a class="icon pull-right" href="/collect/aggregated-model/formular/case-expression/when-case/comparison/edit?id={id}&amp;model={../../model_id}&amp;formular={../../formular_id}&amp;expression={../../item/id}">
                                <i class="fe fe-edit"/>
                              </a>
                            </td>
                            <td>
                              <xsl:value-of select="right_arg/name"/>
                              <a class="icon pull-right" href="/collect/aggregated-model/formular/expression/argument/edit?id={right_arg/id}&amp;type={right_arg/type_id}&amp;expression={../../item/id}&amp;formular={../../formular_id}&amp;model={../../model_id}">
                                <i class="fe fe-edit"/>
                              </a>
                            </td>
                            <td>
                              <xsl:value-of select="result/name"/>
                              <a class="icon pull-right" href="/collect/aggregated-model/formular/expression/argument/edit?id={result/id}&amp;type={result/type_id}&amp;expression={../../item/id}&amp;formular={../../formular_id}&amp;model={../../model_id}">
                                <i class="fe fe-edit"/>
                              </a>
                            </td>
                            <td>
                              <a class="icon pull-right" href="/collect/aggregated-model/formular/case-expression/when-case/delete?id={id}&amp;model={../../model_id}&amp;formular={../../formular_id}&amp;expression={../../item/id}" onclick="return confirm('Voulez-vous supprimer cette possibilité ?');">
                                <i class="fe fe-trash"/>
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
            <a role="button" class="btn btn-primary" href="/collect/aggregated-model/formular/edit?id={formular_id}&amp;model={model_id}"><i class="fa fa-arrow-left"/> Retour
	                </a>
            <xsl:if test="not(item/id)">
              <button type="submit" class="btn btn-primary ml-auto">Enregistrer</button>
            </xsl:if>
          </div>
        </div>
      </form>
    </div>
  </xsl:template>
  <xsl:template match="page" mode="customScript"/>
</xsl:stylesheet>
