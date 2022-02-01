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
  <xsl:variable name="lang.registerForFree" select="'Inscrivez-vous gratuitement !'"/>
  <xsl:variable name="lang.createASingleAccountForAllServices" select="'Créer un seul compte pour tous nos services.'"/>
  <xsl:variable name="lang.formTitle" select="' Inscription'"/>
  <xsl:variable name="lang.yourName" select="'Votre nom'"/>
  <xsl:variable name="lang.yourEmailAddress" select="'Votre adresse mail'"/>
  <xsl:variable name="lang.yourPassword" select="'Votre mot de passe'"/>
  <xsl:variable name="lang.yourConfirmedPassword" select="'Confirmer votre mot de passe'"/>
  <xsl:variable name="lang.signUp" select="'S''inscrire'"/>
  <xsl:variable name="lang.alreadyMember" select="'Déjà membre ?'"/>
  <xsl:variable name="lang.logIn" select="' Connectez-vous'"/>
  <xsl:variable name="lang.seperatorLabel" select="'OU'"/>
  <xsl:variable name="lang.locale" select="'fr_FR'"/>
  <xsl:include href="/xslage.xsl"/>
</xsl:stylesheet>
