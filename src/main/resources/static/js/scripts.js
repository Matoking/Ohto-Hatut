
function confirmDeletion(referenceId) {

    if (confirm("Confirm deletion of reference") === true) {
        deletionFormId = "#delete-reference";
        
        if (referenceId !== undefined) {
            deletionFormId += "-" + referenceId;
        }

        $(deletionFormId).submit();
    }
}

function confirmListDeletion() {
    if (confirm("Confirm deletion of reference list")) {
        $("#delete-reference-list").submit();
    }
}