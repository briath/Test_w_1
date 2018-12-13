$(document).ready(function(){
    $("#create_customer_id").click(
        function() {
            var fName = $("#first_name_id").val();
            var lName = $("#last_name_id").val();
            var email = $("#email_id").val();
            var password = $("#password_id").val();

            // check fields
            if(email =='' || password =='') {
                $('input[type="text"],input[type="password"]').css("border","2px solid red");
                $('input[type="text"],input[type="password"]').css("box-shadow","0 0 3px red");
                alert("Email or password is empty");
            } else {
                $.post({
                    url: 'rest/create_customer',
                    headers: {
                        'Authorization': 'Basic ' + btoa('admin' + ':' + 'setup'),
                        'Content-Type': 'application/json'
                    },
                    data: JSON.stringify({
                                         	"firstName":fName,
                                             "lastName":lName,
                                             "login":email,
                                             "pass":password,
                                             "balance":"0"
                                         })
                }).done(function(data) {
                     $.redirect('/endpoint/customers.html', {'login': 'admin', 'pass': 'setup', 'role': 'ADMIN'}, 'GET');
                });
            }
        }
    );
});