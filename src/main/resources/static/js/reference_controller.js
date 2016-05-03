var App = angular.module('Ohtu-Hatut', []);

App.controller('ReferenceController', function ($scope, $http) {

    getReferences();

    $scope.confirmDeletion = function (referenceId) {
        console.log("got into deleteion");
        if (confirm("Confirm deletion of reference") === true) {          
                $scope.references.splice(getReference(referenceId), 1);
                $http.post('/references/' + referenceId + '/delete');
            
        }
    };
    
    function getReference(id) {
        for (var i = 0; i < $scope.references.length; i++) {
            if ($scope.references[i].id === id) {
                console.log("löydettiin")
                return i;
            }
        }
    }

    function getReferences() {
        $http.get('/references/json')
                .then(function (response) {
                    $scope.references = response.data;
                });
    }
   

});
