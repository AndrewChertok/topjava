var ajaxUrl = "ajax/profile/meals/";
var datatableApi;

function clearFilter(){
    $("#filter")[0].reset();
    updateTable();
}

function mealsFilter(){
    $.ajax({
       type: "GET",
        url: ajaxUrl+"between",
       data: $("#filter").serialize(),
        success: updateByFilter
    });

}


$(function(){
    datatableApi = $("#datatable").DataTable({
        "paging": false,
        "info": true,
        "columns":[
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
        "order":[
            [
                0, "asc"
            ]
        ]

    });
    makeEditable();
});
