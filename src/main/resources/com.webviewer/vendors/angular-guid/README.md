ngGuid
======

A simple Guid generator based on this SO discussion: http://stackoverflow.com/questions/105034/create-guid-uuid-in-javascript.

##Install
You can install this package from bower:

```
bower install angular-guid --save
````

And add the script reference:

```html
<script src="bower_components/angular-guid/guid.min.js"></script>
````

##Load module into your ng application
```javascript
var app = angular.module('app',['ngGuid']);
````

##Inject into your controller or service

```javascript
app.controller('mainCtrl', ['$scope','Guid', function($scope, Guid){

}])
````

##Usage
Using `newGuid()` or `empty`:

```javascipt
app.controller('mainCtrl', ['$scope','Guid', function($scope, Guid){
	console.log('New Guid: ' + Guid.newGuid());
    console.log('Empty Guid: ' + Guid.empty);
}])
````
