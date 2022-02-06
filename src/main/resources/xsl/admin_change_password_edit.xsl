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
  <xsl:include href="/xsl/admin_layout.xsl"/>
  <xsl:template match="page" mode="head">
    <title>
      <xsl:text>MySupervisor - Changer mot de passe</xsl:text>
    </title>
  </xsl:template>
  <xsl:template match="page" mode="body">
    <form action="/admin/user/change-password/save?user={user/id}" method="post">
      <div class="p-lg-6">
        <h2 class="mt-0 mb-4">Changer mot de passe - Utilisateur <xsl:value-of select="user/name"/></h2>
        <div class="row">
          <div class="col-md-12">
            <div class="form-group">
              <label class="form-label">Mot de passe <span class="form-required">*</span></label>
              <input type="password" name="password" class="form-control" placeholder="Saisir un mot de passe" required=""/>
            </div>
          </div>
        </div>
      </div>
      <div class="card-footer text-right">
        <a href="/admin/user/edit?id={user/id}" class="btn btn-link">Annuler</a>
        <button type="submit" class="btn btn-primary">Enregistrer</button>
      </div>
    </form>
  </xsl:template>
  <xsl:template match="page" mode="customScript"/>
</xsl:stylesheet>
