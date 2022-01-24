<?xml version="1.0" encoding="UTF-8"?>
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
    
    <xsl:include href="/com/membership/xsl/registration/page.xsl"/>
</xsl:stylesheet>