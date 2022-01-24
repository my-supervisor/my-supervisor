<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="2.0">
	<xsl:output method="html" encoding="UTF-8"/>
    
    <xsl:variable name="lang.contactUs" select="'Contact us !'"/>
    <xsl:variable name="lang.weAreListeningToYou" select="'We are listening to you.'"/>
    <xsl:variable name="lang.formTitle" select="' Write to us'"/>
    <xsl:variable name="lang.yourName" select="'Your name'"/>
    <xsl:variable name="lang.yourEmailAddress" select="'Your e-mail address'"/>
    <xsl:variable name="lang.yourMessage" select="'Your message'"/>
    <xsl:variable name="lang.submitText" select="'Send'"/>
    
    <xsl:include href="/com/membership/xsl/contacts/page.xsl"/>
</xsl:stylesheet>