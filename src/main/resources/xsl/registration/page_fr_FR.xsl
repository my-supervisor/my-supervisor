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
  <xsl:variable name="i18n.registerForFree" select="'Inscrivez-vous gratuitement !'"/>
  <xsl:variable name="i18n.createASingleAccountForAllServices" select="'Créer un seul compte pour tous nos services.'"/>
  <xsl:variable name="i18n.formTitle" select="' Inscription'"/>
  <xsl:variable name="i18n.yourName" select="'Votre nom'"/>
  <xsl:variable name="i18n.yourEmailAddress" select="'Votre adresse mail'"/>
  <xsl:variable name="i18n.yourPassword" select="'Votre mot de passe'"/>
  <xsl:variable name="i18n.yourConfirmedPassword" select="'Confirmer votre mot de passe'"/>
  <xsl:variable name="i18n.signUp" select="'S''inscrire'"/>
  <xsl:variable name="i18n.alreadyMember" select="'Déjà membre ?'"/>
  <xsl:variable name="i18n.logIn" select="' Connectez-vous'"/>
  <xsl:variable name="i18n.seperatorLabel" select="'OU'"/>
  <xsl:variable name="i18n.locale" select="'fr_FR'"/>
  <xsl:include href="/xsl/registration/page.xsl"/>
</xsl:stylesheet>
