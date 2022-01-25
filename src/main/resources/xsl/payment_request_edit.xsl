<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE castlist [
<!ENTITY nbsp '&#160;'>
]>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" 
                version="2.0">
	<xsl:output method="html" cdata-section-elements="script style" include-content-type="no" doctype-system="about:legacy-compat" encoding="UTF-8" indent="yes"/>
  	<xsl:strip-space elements="*"/>
  	<xsl:include href="/xsl/layout.xsl"/>
  	<xsl:template match="page" mode="head">
  	    <xsl:variable name="has_hub2_method" select="count(payment_methods/payment_method[starts-with(tag, 'HUB2')]) > 0"/>
  	    <xsl:if test="$has_hub2_method">
  	    	<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/select2/4.0.6-rc.0/css/select2.min.css"/>
  	    	<link rel="stylesheet" href="https://static.hub2.io/v1/api.css" />
  	    </xsl:if>
	    <title>
	      <xsl:text>Minlessika - Edit a payment request</xsl:text>
	    </title>
	</xsl:template>
	<xsl:template match="page" mode="body">
	    <div class="page-header">
          <h1 class="page-title">
            Payment request N° <xsl:value-of select="item/reference"/> - <xsl:value-of select="item/status"/>
            <xsl:if test="item/statusId = 'PENDING'">
            	&nbsp;<a href="/payment-request/cancel?id={item/id}" class="btn btn-sm btn-primary ml-auto" onclick="return confirm('Do you want to cancel this payment request ?');">Cancel request</a>
            </xsl:if>
          </h1>
        </div>
        <div class="row">
        	<div class="col-lg-8">
        	    <div class="row">
        	    	<div class="col-12">
        	    	    <div class="card">
			               <div class="card-header">
			                 <h3 class="card-title">Payment informations</h3>
			               </div>
			               <div class="card-body">
			                 <div class="form-group">
			                   <div class="row align-items-center">
			                     <label class="col-sm-3">Objet: </label>
			                     <div class="col-sm-9">
			                       <p><xsl:value-of select="item/object"/></p>
			                     </div>
			                   </div>
			                 </div>
			                 <div class="form-group">
			                   <div class="row align-items-center">
			                     <label class="col-sm-3">Montant: </label>
			                     <div class="col-sm-9">
			                       <p><xsl:value-of select="item/amount"/></p>			                       
			                     </div>
			                   </div>
			                 </div>
			                 <div class="form-group">
			                   <div class="row align-items-center">
			                     <label class="col-sm-3">Bon de commande N°: </label>
			                     <div class="col-sm-9">
			                       <p><a target="_blank" href="/purchase-order/edit?id={item/order_id}"><xsl:value-of select="item/order"/> - Cliquer pour voir détails</a></p>			                       
			                     </div>
			                   </div>
			                 </div>
			                 <div class="form-group">
		                       <h4>Details</h4>
		                       <p><xsl:value-of select="item/notes"/></p>
		                     </div>
			                </div>
			             </div>
        	    	</div>
        	    </div>	        	
	        </div>
	        <div class="col-lg-4">
                <div class="card">
                  <div class="card-header">
                  	<h3 class="card-title">Payment methods</h3>
                  </div>
                  <table class="table card-table table-vcenter">
                    <xsl:for-each select="payment_methods/payment_method">
                        <xsl:variable name="is_cinetpay_method" select="tag='CINETPAY'" />
                        <xsl:variable name="is_hub2_method" select="tag='HUB2'" />
                        <xsl:variable name="is_om_method" select="tag='OM'" />
                        <xsl:variable name="is_pay_test" select="tag='PAY_TEST'" />
                    	<tr>
	                      <td><img src="{logo}" alt="" style="width:75px; height:auto"/></td>
	                      <td>
	                        <xsl:value-of select="name"/>
	                      </td>
	                      <td id="{tag}" class="text-right">
	                        <xsl:if test="../../item/statusId = 'PENDING'">
	                            <xsl:choose>
	                            	<xsl:when test="$is_hub2_method">
	                            		<a href="javascript:void(0)" class="btn btn-primary ml-auto">
			                        		Buy
			                        	</a>
	                            	</xsl:when>
	                            	<xsl:when test="$is_om_method">
			                        	<form action="/om/transaction/open?request={../../item/id}" method="post">
								            <button type="submit" class="btn btn-primary ml-auto">Buy</button>
								        </form>
	                            	</xsl:when>
	                            	<xsl:when test="$is_cinetpay_method">
			                        	<form action="/cinetpay/transaction/open?request={../../item/id}" method="post">
								            <button type="submit" class="btn btn-primary ml-auto">Buy</button>
								        </form>
	                            	</xsl:when>
	                            	<xsl:when test="$is_pay_test">
			                        	<form action="/pay-test/buy?request={../../item/id}" method="post">
								            <button type="submit" class="btn btn-primary ml-auto">Buy</button>
								        </form>
	                            	</xsl:when>
	                            	<xsl:otherwise>
	                            		<a href="/payment-method/mobile-money/edit?request={../../item/id}&amp;method={id}" class="btn btn-primary ml-auto">
			                        		Buy
			                        	</a>
	                            	</xsl:otherwise>
	                            </xsl:choose>
	                        </xsl:if>	                        
	                      </td>
	                    </tr>
                    </xsl:for-each>                    
                  </table>
                </div>
              </div>
        </div>                  
	</xsl:template>  	
	<xsl:template match="page" mode="customScript">
	    <xsl:variable name="has_hub2_method" select="count(payment_methods/payment_method[starts-with(tag, 'HUB2')]) > 0"/>
		<xsl:if test="$has_hub2_method">
  	    	<script src="https://js.hub2.io/external/jquery-redirect.js"></script>
  	    	<script src="https://cdnjs.cloudflare.com/ajax/libs/select2/4.0.6-rc.0/js/select2.min.js"></script>
  	    	<script src="https://js.hub2.io/v1/api.js"></script>
  	    	<script type="text/javascript">
  	    	    <![CDATA[
  	    		$(document).ready(function(){
  	    		    
  	    		    var is_openning = false;
  	    			$("#HUB2").click(function (event) {
  	    					
  	    				if(is_openning == true)
				        	return;
				        	
				        event.preventDefault();
				        				        
				        is_openning = true;
				        $.ajax({
				            // URL vers une page du serveur du partenaire
				            // Les autre informations importantes seront inséré dans cette page
				            url: '/hub2/transaction/open?request=]]><xsl:value-of select="item/id" /><![CDATA[',
				            type: 'POST',
				            dataType: 'json'
				        })
				            .done(function (data) {
								is_openning = false;
				                /*
				                * On récupère le resultat de la page pay.php pour lancer le formulaire de paiement HUB2.
				                */
				                var hub2 = new Hub2(data.tuid);
				                hub2.init();
				
				            })
				            .fail(function (jqXHR, textStatus) {
				                is_openning = false;
				                
				                try {
				                    var msg = $.parseJSON(jqXHR.responseText);
				                    toastr.error(msg.message);
				                }
				                catch (e) {
				                    toastr.error(jqXHR.responseText);
				                }
				            });
				    });
  	    		});
  	    		]]>
  	    	</script>
  	    </xsl:if>
	</xsl:template>	
</xsl:stylesheet>