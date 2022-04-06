let form;

const userAjaxUrl = "admin/users/";

const ctx = {
    ajaxUrl: userAjaxUrl
};

$(function () {
    makeEditableUser($("#datatable").DataTable({
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

function makeEditableUser(datatableApi) {
    makeEditable(datatableApi)
    $('#datatable :checkbox').change(function () {
        enable($(this));
    });
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
        checkbox.prop('checked', checkbox.is(":checked") ? false : true);
        successNoty("didn't change");
    })
}
