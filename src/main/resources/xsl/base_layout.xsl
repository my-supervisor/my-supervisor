<?xml version="1.0"?>
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
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:layout="http://www.minlessika.com/Layout" exclude-result-prefixes="layout" version="2.0">
  <xsl:template name="head">
    <head>
      <xsl:comment>Required meta tags</xsl:comment>
      <meta charset="utf-8"/>
      <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no"/>
      <xsl:comment>Material Design for Bootstrap CSS</xsl:comment>
      <link rel="stylesheet" href="/css/bootstrap.min.css"/>
      <xsl:comment>Material Design Component for Bootstrap CSS</xsl:comment>
      <link rel="stylesheet" href="/css/mdb.min.css"/>
      <xsl:comment>Font Awesome for Icon CSS</xsl:comment>
      <link rel="stylesheet" href="/css/fontawesome-free-5.15.1-web-all.min.css"/>
      <xsl:comment>Toastr CSS</xsl:comment>
      <link rel="stylesheet" href="/css/toastr.min.css"/>
      <link rel="stylesheet" href="/css/flag-icon.css"/>
      <link rel="icon" href="/img/favicon.ico" type="image/x-icon"/>
      <link rel="shortcut icon" type="image/x-icon" href="/img/favicon.ico"/>
      <xsl:if test="recaptcha/active = 'true'">
        <script src="https://www.google.com/recaptcha/api.js" async="" defer=""/>
        <script>					
			        function captchaSubmit(data) {
			        	if (grecaptcha.getResponse() !== '') {
					        document.getElementById("secure-formular").submit();
					    }			            
			        }
			        
			        function validate(event) {
					    event.preventDefault();
					    grecaptcha.execute();
					}
					
					function onload() {
					    var element = document.getElementById('submitter');
					    element.onclick = validate;
					}
			    </script>
      </xsl:if>
      <title>
        <xsl:text>Minlessika</xsl:text>
      </title>
      <xsl:if test="mailchimp/active = 'true'">
        <script id="mcjs">!function(c,h,i,m,p){m=c.createElement(h),p=c.getElementsByTagName(h)[0],m.async=1,m.src=i,p.parentNode.insertBefore(m,p)}(document,"script","https://chimpstatic.com/mcjs-connected/js/users/3f778d6a05433c5bb2085402f/4b0432e23d38fb5d45e376f64.js");</script>
      </xsl:if>
    </head>
  </xsl:template>
  <xsl:template name="import-jslib">
    <xsl:comment>jQuery first, then Popper.js, then Bootstrap JS</xsl:comment>
    <script src="/js/jquery-3.2.1.min.js"/>
    <script src="/js/toastr.min.js"/>
    <script src="/js/popper.min.js"/>
    <script src="/js/bootstrap.min.js"/>
    <script src="/js/mdb.min.js"/>
    <xsl:if test="recaptcha/active = 'true'">
      <script>					
		        onload();
		    </script>
    </xsl:if>
  </xsl:template>
  <xsl:template name="footer">
    <xsl:comment>Footer</xsl:comment>
    <footer class="page-footer font-small blue mt-4 fixed-bottom">
      <xsl:comment>Copyright</xsl:comment>
      <div class="footer-copyright text-center py-2">
        <xsl:choose>
          <xsl:when test="lang='fr'">
               			Copyright &#xA9; 2018-2022 <a href="/">Minlessika</a>. Tous droits r&#xE9;serv&#xE9;s.
               		</xsl:when>
          <xsl:otherwise>
               			Copyright &#xA9; 2018-2022 <a href="/">Minlessika</a>. All rights reserved.
               		</xsl:otherwise>
        </xsl:choose>
      </div>
      <xsl:comment>Copyright</xsl:comment>
    </footer>
    <xsl:comment>Footer</xsl:comment>
  </xsl:template>
</xsl:stylesheet>
