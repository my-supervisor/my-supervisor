<?xml version="1.0"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="2.0">
	<xsl:output method="html" encoding="UTF-8"/>
    
    <xsl:variable name="lang.registerForFree" select="'Register for free !'"/>
    <xsl:variable name="lang.createASingleAccountForAllServices" select="'Create a single account for all our services.'"/>
    <xsl:variable name="lang.formTitle" select="' Registration'"/>
    <xsl:variable name="lang.yourName" select="'Your name'"/>
    <xsl:variable name="lang.yourEmailAddress" select="'Your e-mail address'"/>
    <xsl:variable name="lang.yourPassword" select="'Your password'"/>
    <xsl:variable name="lang.yourConfirmedPassword" select="'Confirm your password'"/>
    <xsl:variable name="lang.signUp" select="'Sign up'"/>
    <xsl:variable name="lang.alreadyMember" select="'Already a member ?'"/>
    <xsl:variable name="lang.logIn" select="' Log in'"/>
    <xsl:variable name="lang.seperatorLabel" select="'OR'"/>
    <xsl:variable name="lang.locale" select="'en_US'"/>
    
    <xsl:include href="/com/membership/xsl/registration/page.xsl"/>
</xsl:stylesheet>