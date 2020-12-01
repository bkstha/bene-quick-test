angular.module('bqTestModule', [])
    .controller('FetchController', ['$scope', '$http',
        function ($scope, $http) {
            $scope.memberList = {};
            $scope.selectedState = "";
            $scope.selectedMembers = [];


            fetchData();

            function fetchData() {
                $http({method: 'GET', url: 'api/load-data'}).then(function (response) {
                    $scope.memberList = response.data;
                    console.log("memberList", $scope.memberList);
                }, function (reason) {
                    console.log('error ' + reason)
                });
            }

            $scope.changeState = function () {
                console.log("changing data");
                $scope.selectedMembers = $scope.memberList[$scope.selectedState];
                console.log("selected members", $scope.selectedMembers);
            };

        }]);
