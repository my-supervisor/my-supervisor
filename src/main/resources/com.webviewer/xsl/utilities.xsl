<?xml version="1.0"?>
<xsl:stylesheet 
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform" 
	xmlns:layout="http://www.minlessika.com/Utilities"
	exclude-result-prefixes="layout"
	version="2.0">
	
	<xsl:template name='some-features-to-initialize'>
		<script type="text/javascript">
	    	<![CDATA[
	    		Array.prototype.swap = function(a, b) {
				  var temp = this[a];
				  this[a] = this[b];
				  this[b] = temp;
				};
				
				if (!String.format) {
				  String.format = function(format) {
				    var args = Array.prototype.slice.call(arguments, 1);
				    return format.replace(/{(\d+)}/g, function(match, number) { 
				      return typeof args[number] != 'undefined'
				        ? args[number] 
				        : match
				      ;
				    });
				  };
				}
				
				if ( !Date.prototype.toISOString ) {
				  ( function() {
				    
				    function pad(number) {
				      if ( number < 10 ) {
				        return '0' + number;
				      }
				      return number;
				    }
				 
				    Date.prototype.toISOString = function() {
				      return this.getUTCFullYear() +
				        '-' + pad( this.getUTCMonth() + 1 ) +
				        '-' + pad( this.getUTCDate() ) +
				        'T' + pad( this.getUTCHours() ) +
				        ':' + pad( this.getUTCMinutes() ) +
				        ':' + pad( this.getUTCSeconds() ) +
				        '.' + (this.getUTCMilliseconds() / 1000).toFixed(3).slice(2, 5) +
				        'Z';
				    };
				  
				  }() );
				}
	    	]]>
	    </script>
	    
	    <script type="text/javascript">
	    	<![CDATA[			
	    		$(document).ready(function () {
                    $('#input-tags').selectize({
                        delimiter: '|',
                        persist: false,
                        create: function (input) {
                            return {
                                value: input,
                                text: input
                            }
                        }
                    });
                });
                    		
				$('form').submit(function () {
				    $(this).find('input[type="checkbox"]').each( function () {
				        var checkbox = $(this);
				        if( checkbox.is(':checked')) {
				            checkbox.attr('value','true');
				        } else {
				            checkbox.after().append(checkbox.clone().attr({type:'hidden', value:'false'}));
				            checkbox.prop('disabled', true);
				        }
				    })
				});
			]]>
	    </script>	    
	</xsl:template>
	
	<xsl:template name="toast">
	   		<xsl:if test="flash">   		
   			<script type="text/javascript">
   			     <xsl:choose>
		   			<xsl:when test="lang='fr'">
		   			<![CDATA[
		   				var successTitle = 'Succès';
		   				var informationTitle = 'Information';
		   				var warningTitle = 'Avertissement';
		   				var errorTitle = 'Erreur';
		   			]]>
		   			</xsl:when>
		   			<xsl:otherwise>
		   			<![CDATA[
		   				var successTitle = 'Success';
		   				var informationTitle = 'Information';
		   				var warningTitle = 'Warning';
		   				var errorTitle = 'Error';
		   			]]>
		   			</xsl:otherwise>
		   		</xsl:choose>
	   		
				 $(function() {
				 	 toastr.options = {
						  "closeButton": true,
						  "debug": false,
						  "newestOnTop": false,
						  "progressBar": false,
						  "positionClass": "toast-top-right",
						  "preventDuplicates": false,
						  "onclick": null,
						  "showDuration": "300",
						  "hideDuration": "1000",
						  "timeOut": "5000",
						  "extendedTimeOut": "1000",
						  "showEasing": "swing",
						  "hideEasing": "linear",
						  "showMethod": "fadeIn",
						  "hideMethod": "fadeOut"
						};
					 <xsl:choose>
					 	<xsl:when test="flash/level = 'FINE'">
					 		toastr.success("<xsl:value-of select="flash/message"/>", successTitle);
					 	</xsl:when>
					 	<xsl:when test="flash/level = 'INFO'">
					 		toastr.info("<xsl:value-of select="flash/message"/>", informationTitle);
					 	</xsl:when>
					 	<xsl:when test="flash/level = 'WARNING'">
					 		toastr.warning("<xsl:value-of select="flash/message"/>", warningTitle);
					 	</xsl:when>
					 	<xsl:otherwise>
					 		toastr.error("<xsl:value-of select="flash/message"/>", errorTitle);
					 	</xsl:otherwise>
					 </xsl:choose>
				});
			</script>
   		</xsl:if>   		 		
	</xsl:template>
</xsl:stylesheet>