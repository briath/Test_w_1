$(document).ready(function(){
    $("#add_new_customer").click(function() {
        $.redirect('/endpoint/add_customer.html', {'login': 'admin', 'pass': 'setup', 'role': 'ADMIN'}, 'GET');
    });

    $.get({
        url: 'rest/get_customers',
        headers: {
            'Authorization': 'Basic ' + btoa('admin' + ':' + 'setup')
        }
    }).done(function(data) {
        var json = $.parseJSON(data);

        var dataSet = []
        for(var i = 0; i < json.length; i++) {
            var obj = json[i];
            dataSet.push([obj.firstName, obj.lastName, obj.login, obj.pass, obj.balance])
        }

        //$("#customer_list_id").html(data);
        $('#customer_list_id')
            .DataTable({
                data: dataSet,
                columns: [
                    { title: "Fist Name" },
                    { title: "Last Name" },
                    { title: "Email" },
                    { title: "Pass" },
                    { title: "Balance" }
                ]
            });
    });
});