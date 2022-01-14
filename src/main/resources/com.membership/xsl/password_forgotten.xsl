<?xml version="1.0"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" 
                version="2.0">
	<xsl:output method="html" cdata-section-elements="script style" include-content-type="no" doctype-system="about:legacy-compat" encoding="UTF-8" indent="yes"/>
  	<xsl:include href="/com/membership/xsl/website_layout.xsl"/>
  	<xsl:template match="page" mode="body">  		
		<!--Grid row-->
        <div class="row mt-5 flex-center">
          <!--Grid column-->
          <div class="col-md-6 col-xl-5 mb-4">
          	<!--Form-->
            <form id="secure-formular" role="form" action="/password/reset/request" method="post" class="card wow fadeInLeft" data-wow-delay="0.3s">
              <div class="card-body">
                <!--Header-->
                <div class="text-center">
                  <h3 class="dark-grey-text">
                    <i class="fa fa-key grey-text"></i> Password forgotten ?</h3>
                  <hr class="mt-2 mb-2"/>
                </div>
                <!--Body-->
                <div class="md-form">
                  <i class="fa fa-envelope prefix grey-text active"></i>
                  <input name="email" autocomplete="off" type="email" id="form2" class="form-control" required="">
                  	<xsl:if test="item">
			            <xsl:attribute name="value">
			              <xsl:value-of select="item/email"/>
			            </xsl:attribute>
			        </xsl:if>
                  </input>
                  <label for="form2" class="active">Your e-mail address</label>
                </div>
                <div class="text-center mt-4">
                	<button type="submit" class="btn btn-indigo g-recaptcha" data-sitekey="{recaptcha/site-key}" data-callback="captchaSubmit" data-badge="inline">Reset password</button>
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