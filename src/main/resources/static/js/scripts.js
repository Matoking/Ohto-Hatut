
function confirmDeletion(referenceId) {

    if (confirm("Confirm deletion of reference") === true) {
        deletionFormId = "#delete-reference";
        
        if (referenceId !== undefined) {
            deletionFormId += "-" + referenceId;
        }

        $(deletionFormId).submit();
    }
}
