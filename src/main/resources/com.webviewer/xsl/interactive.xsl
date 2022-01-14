<?xml version="1.0"?>
<xsl:stylesheet 
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform" 
	xmlns:interactive="http://www.minlessika.com/Interactive"
	exclude-result-prefixes="interactive"
	version="2.0">
	
	<xsl:template name="iform">
		<xsl:param name="action"/>	
		<xsl:param name="canRoute"/>
		<xsl:param name="targetUrl"/>	
   		<script src="/com/webviewer/vendors/toastrjs/toastr.min.js"></script>
   		<script src="/com/webviewer/vendors/angular/angular.min.js"></script>
   		
   		<script type="text/javascript">
   		<![CDATA[
   			toastr.options = {
			  "closeButton": true,
			  "debug": false,
			  "newestOnTop": false,
			  "progressBar": false,
			  "positionClass": "toast-top-full-width",
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
			
			var app = angular.module( "app", [] );
			
			app.factory("transformRequestAsFormPost",
				    function() {				    	
				    				        
				        function transformRequest( data, getHeaders ) {
				            var headers = getHeaders();
				            headers[ "Content-type" ] = "application/x-www-form-urlencoded; charset=utf-8";
				            return( serializeData( data ) );
				        }
				        
				        return( transformRequest );
				        
				        function serializeData( data ) {				        					           
				            if ( ! angular.isObject( data ) ) {
				                return( ( data == null ) ? "" : data.toString() );
				            }
				            var buffer = [];				            				          
				            for ( var name in data ) {
				                if ( ! data.hasOwnProperty( name ) ) {
				                    continue;
				                }
				                var value = data[ name ];
				                buffer.push(
				                    encodeURIComponent( name ) +
				                    "=" +
				                    encodeURIComponent( ( value == null ) ? "" : value )
				                );
				            }
				            				            
				            var source = buffer
				                .join( "&" )
				                .replace( /%20/g, "+" )
				            ;
				            return( source );
				        }
				    }
				);
			
			app.controller("]]><xsl:value-of select="concat($action, 'Controller')"/><![CDATA[", function($scope, $http, transformRequestAsFormPost, $window){
				
				var vm = this;
				
				vm.formData = {}
				
				vm.]]><xsl:value-of select="$action"/><![CDATA[ = function(){
					vm.requesting = true;
				
					var request = $http({
				        method: "post",
				        url: "/]]><xsl:value-of select="$action"/><![CDATA[",
				        transformRequest: transformRequestAsFormPost,
				        data: vm.formData
				    });
															
				    request.then(function( success ) {
				    		]]>
				    		<xsl:if test="$canRoute = 1">
				    			<![CDATA[
				    				$window.location.href=']]><xsl:value-of select="$targetUrl"/><![CDATA[';
				    			]]>
				    		</xsl:if>
				    		
				    		<xsl:if test="$canRoute = 0">
				    			<![CDATA[
				    				toastr.success(success.data);
						    		vm.formData = {};
						    		
						    		vm.requesting = false;
				    			]]>
				    		</xsl:if>
				    		<![CDATA[
				        }, function( error ) {
				        	if(error.status == 400)
				        		toastr.warning(error.data);
				        	else
				        		toastr.error(error.data);
				        	
				        	vm.requesting = false;
				        });
				}
			
			});]]>
   			
   		</script>
   		 		
	</xsl:template>
</xsl:stylesheet>