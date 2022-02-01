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
  <xsl:output method="html" encoding="UTF-8"/>
  <xsl:variable name="i18n.formTitle" select="' Se connecter'"/>
  <xsl:variable name="i18n.yourEmailAddress" select="'Votre adresse mail'"/>
  <xsl:variable name="i18n.yourPassword" select="'Votre mote de passe'"/>
  <xsl:variable name="i18n.submitText" select="'Se connecter'"/>
  <xsl:variable name="i18n.notAMember" select="'Pas encore membre ?'"/>
  <xsl:variable name="i18n.registerForFree" select="' S''enregistrer gratuitement'"/>
  <xsl:variable name="i18n.password" select="'Mot de passe'"/>
  <xsl:variable name="i18n.forgotten" select="' oublié ?'"/>
  <xsl:variable name="i18n.seperatorLabel" select="'OU'"/>
  <xsl:variable name="i18n.locale" select="'fr_FR'"/>
  <xsl:include href="/xsl/login/page.xsl"/>
</xsl:stylesheet>
