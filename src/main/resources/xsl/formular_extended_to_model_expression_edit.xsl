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
      <xsl:text>Supervisor - Minlessika - Configurer une expression étendue à un modèle</xsl:text>
    </title>
  </xsl:template>
  <xsl:template match="page" mode="body">
    <div class="col-12">
      <form class="card" action="/collect/aggregated-model/formular/extended-to-model/save" method="post">
        <div class="card-header">
          <h3 class="card-title">Modifier expression étendue à un modèle <xsl:value-of select="item/name"/> - formule <i><xsl:value-of select="formular"/></i></h3>
        </div>
        <div class="card-body">
          <input name="id" type="text" hidden="hidden" value="{item/id}"/>
          <input name="formular_id" hidden="hidden" type="text" value="{formular_id}"/>
          <input name="model_id" hidden="hidden" type="text" value="{model_id}"/>
          <div class="row">
            <div class="col-md-6">
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
            <div class="col-md-6">
              <div class="form-group">
                <label class="form-label">Fonction d'aggrégation</label>
                <div class="table-responsive form-control">
                  <table class="table card-table table-vcenter text-nowrap">
                    <tbody>
                      <tr>
                        <td>
                          <xsl:value-of select="item/aggregate"/>
                        </td>
                        <td>
                          <a class="icon" href="/collect/aggregated-model/formular/extended-to-model-expression/aggregate/edit?id={item/id}&amp;model={model_id}&amp;formular={formular_id}">
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
                <label class="form-label">Sources de données</label>
                <div class="table-responsive form-control">
                  <table class="table card-table table-vcenter text-nowrap">
                    <thead>
                      <tr>
                        <th class="w-1">N°</th>
                        <th>Modèle</th>
                        <th>Champ</th>
                        <th>Comparateur</th>
                        <th>Référence</th>
                        <th>Est activée ?</th>
                        <th colspan="3" class="text-right">
                          <a href="/collect/aggregated-model/formular/extended-to-model-expression/source/edit?expr={item/id}&amp;model={model_id}&amp;formular={formular_id}" role="button" class="btn btn-sm btn-primary"><i class="fe fe-plus"/> Nouvelle source
			                        </a>
                        </th>
                      </tr>
                    </thead>
                    <tbody>
                      <xsl:for-each select="formular_extended_to_model_sources/formular_extended_to_model_source">
                        <tr>
                          <td>
                            <span class="text-muted">
                              <xsl:value-of select="position()"/>
                            </span>
                          </td>
                          <td>
                            <xsl:value-of select="model_to_extend"/>
                          </td>
                          <td>
                            <xsl:value-of select="model_field"/>
                          </td>
                          <td>
                            <xsl:value-of select="comparator"/>
                          </td>
                          <td>
                            <xsl:value-of select="reference"/>
                          </td>
                          <td>
                            <xsl:choose>
                              <xsl:when test="active = 'true'">Oui</xsl:when>
                              <xsl:otherwise>Non</xsl:otherwise>
                            </xsl:choose>
                          </td>
                          <td>
                            <xsl:choose>
                              <xsl:when test="active='true'">
                                <a class="btn btn-sm btn-danger" href="/collect/aggregated-model/formular/extended-to-model-expression/source/activate?id={id}&amp;expr={expr_id}&amp;model={model_id}&amp;formular={formular_id}&amp;active=false">
								                         Désactiver
								                    </a>
                              </xsl:when>
                              <xsl:otherwise>
                                <a class="btn btn-sm btn-success" href="/collect/aggregated-model/formular/extended-to-model-expression/source/activate?id={id}&amp;expr={expr_id}&amp;model={model_id}&amp;formular={formular_id}&amp;active=true">
								                         Activer
								                    </a>
                              </xsl:otherwise>
                            </xsl:choose>
                          </td>
                          <td>
                            <a class="icon" href="/collect/aggregated-model/formular/extended-to-model-expression/source/edit?id={id}&amp;expr={expr_id}&amp;model={model_id}&amp;formular={formular_id}">
                              <i class="fe fe-edit"/>
                            </a>
                          </td>
                          <td>
                            <a class="icon" href="/collect/aggregated-model/formular/extended-to-model-expression/source/delete?id={id}&amp;expr={expr_id}&amp;model={model_id}&amp;formular={formular_id}" onclick="return confirm('Etes-vous sûr de vouloir supprimer cette source de données ?');">
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
