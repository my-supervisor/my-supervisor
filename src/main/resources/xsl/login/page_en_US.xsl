<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="2.0">
	<xsl:output method="html" encoding="UTF-8"/>
    
    <xsl:variable name="i18n.formTitle" select="' Log in'"/>
    <xsl:variable name="i18n.yourEmailAddress" select="'Your e-mail address'"/>
    <xsl:variable name="i18n.yourPassword" select="'Your password'"/>
    <xsl:variable name="i18n.submitText" select="'Sign in'"/>
    <xsl:variable name="i18n.notAMember" select="'Not a member ?'"/>
    <xsl:variable name="i18n.registerForFree" select="' Register for free'"/>
    <xsl:variable name="i18n.password" select="'Password'"/>
    <xsl:variable name="i18n.forgotten" select="' forgotten ?'"/>
    <xsl:variable name="i18n.seperatorLabel" select="'OR'"/>
    <xsl:variable name="i18n.locale" select="'en_US'"/>
    
    <xsl:include href="/xsl/login/page.xsl"/>
</xsl:stylesheet>