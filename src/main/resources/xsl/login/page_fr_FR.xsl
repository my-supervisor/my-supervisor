<?xml version="1.0" encoding="UTF-8"?>
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