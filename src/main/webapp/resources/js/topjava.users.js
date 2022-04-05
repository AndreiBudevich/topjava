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
        enable($(this));
    });

    $(document).ajaxError(function (event, jqXHR, options, jsExc) {
        failNoty(jqXHR);
    });

    $.ajaxSetup({cache: false});
}

function enable(checkbox) {
    const enable = checkbox.is(":checked");
    const id = checkbox.closest('tr').attr("id");
    $.ajax({
        url: ctx.ajaxUrl + id,
        type: "POST",
        data: {enabled: enable},
    }).done(function () {
        checkbox.closest('tr').attr("data-user-enabled", enable);
        successNoty(enable ? "Record enabled" : "Record disabled");
    }).fail(function () {
        successNoty("didn't change");
    })
}
