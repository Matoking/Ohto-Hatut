
function confirmDeletion() {

    if (confirm("Confirm deletion of reference") === true) {
        $("#delete-reference").submit();
    }
}

function confirmListDeletion() {
    if (confirm("Confirm deletion of reference list")) {
        $("#delete-reference-list").submit();
    }
}

$(document).ready(function () {
    $("select").searchable();
});