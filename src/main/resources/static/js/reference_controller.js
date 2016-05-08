var App = angular.module('Ohtu-Hatut', []);

App.controller('ReferenceController', function ($scope, $http) {

    getReferences();

    $scope.confirmDeletion = function (referenceId) {
        console.log("got into deleteion");
        if (confirm("Confirm deletion of reference") === true) {          
                $scope.references.splice(getReferenceIndex(referenceId), 1);
                $http.post('/references/' + referenceId + '/delete');
            
        }
    };
    
    function getReferenceIndex(id) {
        for (var i = 0; i < $scope.references.length; i++) {
            if ($scope.references[i].id === id) {
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
