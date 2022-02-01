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
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:interactive="http://www.minlessika.com/ReportViewComponent" version="2.0">
  <xsl:template name="viewer_view">
    <div class="col-md-12" ng-controller="printPreviewCtrl">
      <div class="panel panel-primary">
        <div class="panel-heading clearfix">
          <h4 class="panel-title pull-left" style="padding-top: 7.5px;">Preview report </h4>
          <div class="pull-right">
            <div class="form-inline">
              <span class="mr-1">Page: </span>
              <input class="form-control form-control-sm mr-1" style="color: black;" type="number" ng-min="1" ng-max="pageCount" ng-model="pageNum"/>
              <span class="mr-1"> / {{pageCount}}</span>
              <button type="button" class="btn btn-primary btn-sm mr-1" ng-disabled="pageNum == 1" ng-click="goPrevious()">
                <i class="fa fa-arrow-left"/>
              </button>
              <button type="button" class="btn btn-primary btn-sm mr-1" ng-disabled="pageNum == pageCount" ng-click="goNext()">
                <i class="fa fa-arrow-right"/>
              </button>
              <button type="button" class="btn btn-primary btn-sm mr-1" ng-click="zoomIn()">
                <i class="fe fe-zoom-in"/>
              </button>
              <button type="button" class="btn btn-primary btn-sm mr-1" ng-click="fit()">100%</button>
              <button type="button" class="btn btn-primary btn-sm" ng-click="zoomOut()">
                <i class="fe fe-zoom-out"/>
              </button>
            </div>
          </div>
        </div>
        <div class="panel-body">
          <div class="panel panel-default">
            <div class="panel-body h-100" style="max-height: 1500px; overflow-y: scroll;">
              <div ng-show="loadingData">
                <div class="col-xs-4"/>
                <div class="col-xs-4">
                  <i class="fa fa-refresh fa-5x fa-spin"/>
                  <label class="label label-primary">Loading report...</label>
                </div>
                <div class="col-xs-4"/>
              </div>
              <div id="viewer" ng-show="!loadingData">
                <ng-pdf template-url="/com/accounting/html/viewer.html" scale="page-fit"/>
              </div>
            </div>
          </div>
        </div>
        <div class="panel-footer clearfix">
          <div class="pull-left">
            <a role="button" class="btn btn-primary mr-1" href="/home">Return</a>
            <button type="button" class="btn btn-primary" ng-click="refresh()">Refresh</button>
          </div>
          <div class="pull-right">
            <button type="button" class="btn btn-primary mr-1" ng-click="print()">Print</button>
            <button type="button" class="btn btn-primary mr-1" ng-click="exportPdf()">Export PDF</button>
            <button type="button" class="btn btn-primary" ng-click="exportXls()">Export EXCEL</button>
          </div>
        </div>
      </div>
    </div>
  </xsl:template>
  <xsl:template name="viewer_logic">
    <script src="/com/webviewer/vendors/pdfjs-dist/build/pdf.combined.js"/>
    <script src="/com/webviewer/vendors/angular-pdf/dist/angular-pdf.js"/>
    <script type="text/javascript"><![CDATA[
        var app = angular.module("app", ['pdf']);
				                 
		app.controller("printPreviewCtrl", ["$scope", "$rootScope", "$timeout", "$http", function ($scope, $rootScope, $timeout, $http) {
			
			// Data
	        $scope.fromWebApi = true;        
	        
	        // Methods
	        $scope.print = print;
	        $scope.exportPdf = exportPdf;
	        $scope.exportXls = exportXls;
	        $scope.refresh = refresh;
	
	        function refresh(){
	        	render({ format: 'PDF' }, function (url) {
	                $scope.pdfUrl = url;
	            });
	        }
	        
	        function exportXls() {
	            render({ format: 'XLS' }, function (url) {
	                window.open(url, "_blank");
	            });
	        }
	
	        function exportPdf() {
	            window.open($scope.pdfUrl, "_blank");
	        }
	
	        $scope.onError = function (error) {
	            $scope.loadingData = false;
	        }
	
	        $scope.onLoad = function () {            
	            $scope.loadingData = false;
	        }
	
	        $scope.onProgress = function (progress) {
	            // handle a progress bar
	            // progress% = progress.loaded / progress.total
	
	            // console.log(progress);
	        }
	
	        //////////
	
	        function print() {
	            render({ format: 'PDF' }, function (url) {
	                window.open(url, "_blank");
	            });            
	        }
	
			function buildReport(url, data, success, failure) {
			
			    var config = {
	                headers : {
	                    'Content-Type': 'application/json;charset=utf-8;'
	                },
	                responseType: "arraybuffer"
	            };
	            
	            return $http.post(url, data, config).
	                          then(function (response) {
	                              if(success)
	                                success(response.data);
	                          }, function (error, status) {
	                              if (error.status == -1) {
	                                  toastr.error('Connexion au serveur momentanément interrompue.');
	                              } else if (error.status == '401') {
	                                  toastr.error(error);
	                              } else if(error.status == '400') {
	                              	toastr.error(error);
	                              } else if (error.status == '500') {
	                                  toastr.error("An error occured during generation of report. Please contact administrator.");
	                              } 
	                              
	                              if(failure)
	                                  failure(error);
	                          });
	        }
        
	        function render(config, callback) {
	            $scope.loadingData = true;
								
	            if ($scope.fromWebApi) {	            
	            	]]><xsl:for-each select="parameters/parameter">
	            	config.<xsl:value-of select="name"/> = "<xsl:value-of select="value"/>";
	            	</xsl:for-each><![CDATA[
	                buildReport($scope.url, config,
	                function (arraybuffer) {
	                	
	                	var mimeType = null;
	                    switch (config.format) {
	                        case 'XLS':
	                            mimeType = 'application/vnd.ms-excel';
	                            break;
	                        case 'postscript':
	                            mimeType = 'application/postscript';
	                            break;
	                        default:
	                            mimeType = 'application/pdf';
	                            break;
	                    }
	
	                    var currentBlob = new Blob([arraybuffer], { type: mimeType });                    
	                    var url = URL.createObjectURL(currentBlob);	                   
	
	                    if (callback)
	                        callback(url);	                     	                    
	
						$scope.loadingData = false;
	                }, function (response) {
	                    $scope.loadingData = false;
	                    toastr.error("Error during generating report : " + response.statusText);
	                });
	            } else {
	            	$scope.loadingData = false;
	                if (callback)
	                    callback($scope.url);
	            }            
	        }
	
	        $rootScope.$on('print-preview', function(event, args){
	            $scope.url = args.url;
	            
	        	render({ format: 'PDF' }, function (url) {
	                $scope.pdfUrl = url;
	            });
	        	
	        });
	        
	        this.$onInit = function () {
	   			$rootScope.$broadcast('print-preview', {url: "]]><xsl:value-of select="report_url"/><![CDATA["});
	   			// $rootScope.$broadcast('print-preview', {url : "/com/accounting/pdf/35613-les-requetes-http.pdf"});	   			
	        }
		}]);
		
		angular.bootstrap(document, ['app']);
        ]]></script>
  </xsl:template>
</xsl:stylesheet>
