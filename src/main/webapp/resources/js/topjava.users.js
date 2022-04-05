let form;
let userForm;

const userAjaxUrl = "admin/users/";

const ctx = {
    ajaxUrl: userAjaxUrl
};

// $(document).ready(function () {
$(function () {
    makeEditable($("#datatable").DataTable({
        "paging": false, "info": true, "columns": [{
            "data": "name"
        }, {
            "data": "email"
        }, {
            "data": "roles"
        }, {
            "data": "enabled"
        }, {
            "data": "registered"
        }, {
            "defaultContent": "Edit", "orderable": false
        }, {
            "defaultContent": "Delete", "orderable": false
        }], "order": [[0, "asc"]]
    }));
});

function makeEditable(datatableApi) {
    ctx.datatableApi = datatableApi;
    form = $('#detailsForm');
    userForm = $('#userForm');
    $(".delete").click(function () {
        if (confirm('Are you sure?')) {
            deleteRow($(this).closest('tr').attr("id"));
        }
    });

    $('#userForm :checkbox').change(function () {
        enable($(this).closest('tr').attr("id"), $(this).is(":checked"));
    });

    $(document).ajaxError(function (event, jqXHR, options, jsExc) {
        failNoty(jqXHR);
    });

    $.ajaxSetup({cache: false});
}

function enable(id, checked) {
    $.ajax({
        url: ctx.ajaxUrl + id,
        type: "POST",
        data: {enabled: checked},
    }).done(function () {
        updateTable();
        successNoty("enable User");
    });
}
