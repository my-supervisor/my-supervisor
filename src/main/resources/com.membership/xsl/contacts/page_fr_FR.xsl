<?xml version="1.0"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="2.0">
	<xsl:output method="html" encoding="UTF-8"/>
    
    <xsl:variable name="lang.contactUs" select="'Contactez-nous !'"/>
    <xsl:variable name="lang.weAreListeningToYou" select="'Nous sommes à votre écoute.'"/>
    <xsl:variable name="lang.formTitle" select="' Ecrivez-nous'"/>
    <xsl:variable name="lang.yourName" select="'Votre nom'"/>
    <xsl:variable name="lang.yourEmailAddress" select="'Votre adresse mail'"/>
    <xsl:variable name="lang.yourMessage" select="'Votre message'"/>
    <xsl:variable name="lang.submitText" select="'Envoyer'"/>
    
    <xsl:include href="/com/membership/xsl/contacts/page.xsl"/>
</xsl:stylesheet>