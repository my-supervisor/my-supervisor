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
  <xsl:output method="html" cdata-section-elements="script style" include-content-type="no" doctype-system="about:legacy-compat" encoding="UTF-8" indent="yes"/>
  <xsl:include href="/xsl/website_layout.xsl"/>
  <xsl:template match="page" mode="body">
    <!--Grid row-->
    <div class="row mt-5 flex-center">
      <!--Grid column-->
      <div class="col-md-6 mb-5 mt-md-0 mt-5 white-text text-center text-md-center">
        <h1 class="h1-responsive font-weight-bold wow fadeInLeft" data-wow-delay="0.3s">
          <xsl:value-of select="$lang.slogan1"/>
        </h1>
        <hr class="hr-light wow fadeInLeft" data-wow-delay="0.3s"/>
        <h6 class="mb-3 wow fadeInRight" data-wow-delay="0.3s">
          <xsl:value-of select="$lang.slogan2"/>
        </h6>
      </div>
      <!--Grid column-->
    </div>
    <!--Grid row-->
  </xsl:template>
</xsl:stylesheet>
