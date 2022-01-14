<?xml version="1.0"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="2.0">
	<xsl:output method="html" encoding="UTF-8"/>
    
    <xsl:variable name="lang.formTitle" select="' Log in'"/>
    <xsl:variable name="lang.yourEmailAddress" select="'Your e-mail address'"/>
    <xsl:variable name="lang.yourPassword" select="'Your password'"/>
    <xsl:variable name="lang.submitText" select="'Sign in'"/>
    <xsl:variable name="lang.notAMember" select="'Not a member ?'"/>
    <xsl:variable name="lang.registerForFree" select="' Register for free'"/>
    <xsl:variable name="lang.password" select="'Password'"/>
    <xsl:variable name="lang.forgotten" select="' forgotten ?'"/>
    <xsl:variable name="lang.seperatorLabel" select="'OR'"/>
    <xsl:variable name="lang.locale" select="'en_US'"/>
    
    <xsl:include href="/com/membership/xsl/login/page.xsl"/>
</xsl:stylesheet>