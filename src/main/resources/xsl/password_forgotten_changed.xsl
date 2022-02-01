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
      <div class="col-md-6 col-xl-5 mb-4">
        <!--Form-->
        <form id="secure-formular" role="form" action="/password/reset?key={key}" method="post" class="card wow fadeInLeft" data-wow-delay="0.3s">
          <div class="card-body">
            <!--Header-->
            <div class="text-center">
              <h3 class="dark-grey-text"><i class="fa fa-key grey-text"/> Change password</h3>
              <hr class="mt-2 mb-2"/>
            </div>
            <!--Body-->
            <div class="md-form">
              <i class="fa fa-lock prefix grey-text active"/>
              <input name="password" type="password" id="form2" class="form-control" required=""/>
              <label for="form2" class="active">New password</label>
            </div>
            <div class="md-form">
              <i class="fa fa-lock prefix grey-text"/>
              <input name="password_confirmed" type="password" id="form3" class="form-control" required=""/>
              <label for="form3" class="">Confirm your password</label>
            </div>
            <div class="text-center mt-4">
              <button type="submit" class="btn btn-indigo g-recaptcha" data-sitekey="{recaptcha/site-key}" data-callback="captchaSubmit" data-badge="inline">Apply</button>
            </div>
          </div>
        </form>
        <!--/.Form-->
      </div>
      <!--Grid column-->
    </div>
    <!--Grid row-->
  </xsl:template>
</xsl:stylesheet>
