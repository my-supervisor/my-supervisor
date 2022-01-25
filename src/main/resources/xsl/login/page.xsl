<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" 
                version="2.0">
	<xsl:output method="html" cdata-section-elements="script style" include-content-type="no" doctype-system="about:legacy-compat" encoding="UTF-8" indent="yes" />
  	<xsl:include href="/xsl/website_layout.xsl"/>
  	<xsl:template match="page" mode="body">  		
		<!--Grid row-->
        <div class="row mt-5 flex-center">
          <!--Grid column-->
          <div class="col-md-6 col-xl-5 mb-4">
          	<!--Form-->
            <form id="secure-formular" role="form" action="/signin" method="post">
              <div class="card wow fadeInLeft" data-wow-delay="0.3s">
              	<div class="card-body">
	                <!--Header-->
	                <div class="text-center">
	                  <h3 class="dark-grey-text">
	                    <i class="fa fa-user grey-text"></i> <xsl:value-of select="$lang.formTitle"/>
	                  </h3>
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
	                  <label for="form2" class="active"><xsl:value-of select="$lang.yourEmailAddress"/></label>
	                </div>
	                <div class="md-form">
	                  <i class="fa fa-lock prefix grey-text active"></i>
	                  <input name="password" type="password" id="form4" class="form-control" required=""/>
	                  <label for="form4"><xsl:value-of select="$lang.yourPassword"/></label>
	                </div>
	                <div class="text-center mt-4">                  
	                  <xsl:choose>
	                  	<xsl:when test="recaptcha/active = 'true'">
	                  		<button id="submitter" class="btn btn-indigo"><xsl:value-of select="$lang.submitText"/></button>
	                  	</xsl:when>
	                  	<xsl:otherwise>
	                  		<button type="submit" class="btn btn-indigo"><xsl:value-of select="$lang.submitText"/></button>
	                  	</xsl:otherwise>
	                  </xsl:choose>          
	                </div>	
	                  		                
	              </div>
	              <!--Footer-->
			      <div class="modal-footer">
				        <div class="options">
				            <p><xsl:value-of select="$lang.notAMember"/> <a href="/registration"><xsl:value-of select="$lang.registerForFree"/></a></p>
				            <p><xsl:value-of select="$lang.password"/> <a href="/password/forgotten"><xsl:value-of select="$lang.forgotten"/></a></p>
				        </div>
			      </div>
              </div>
              <xsl:if test="recaptcha/active = 'true'">
              	<div class="g-recaptcha" data-sitekey="{recaptcha/site-key}" data-badge="bottomright" data-callback="captchaSubmit" data-size="invisible"></div>	      	
		      </xsl:if>
            </form>
            <!--/.Form-->			            
          </div>
          <!--Grid column-->
        </div>
        <!--Grid row-->        
	</xsl:template>
</xsl:stylesheet>