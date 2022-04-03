let formFilter;

const mealAjaxUrl = "profile/meals/";

const ctx = {
    ajaxUrl: mealAjaxUrl
};

$(function () {
    makeEditable(
        $("#datatable").DataTable({
            "paging": false,
            "info": true,
            "columns": [
                {
                    "data": "dateTime"
                },
                {
                    "data": "description"
                },
                {
                    "data": "calories"
                },
                {
                    "defaultContent": "Edit",
                    "orderable": false
                },
                {
                    "defaultContent": "Delete",
                    "orderable": false
                }
            ],
            "order": [
                [
                    0,
                    "desc"
                ]
            ]
        })
    );
});

function filter() {
    formFilter = $('#filterForm');
    $.ajax({
        url: mealAjaxUrl + "filter",
        type: "GET",
        data: formFilter.serialize()
    }).done(function (data) {
        ctx.datatableApi.clear().rows.add(data).draw();
        successNoty("filter");
    });
}

function clearFilter() {
    $.get(mealAjaxUrl, function (data) {
        $('#filterForm').trigger("reset");
        ctx.datatableApi.clear().rows.add(data).draw();
    });
}
