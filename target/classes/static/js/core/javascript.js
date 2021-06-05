var Modal = {

    addTrainee : function(id) {
        var data = {};
        data["account"] = $('#account-trainee').val();
        data["classId"] = $('#classId').val();

        $.ajax({
            url: "/class-management/add-trainee",
            type: "post",
            contentType: "application/json", // dữ liệu gửi lên web-service có dạng là json.
            data: JSON.stringify(data), // object json -> string json

            dataType: "json", // dữ liệu từ web-service trả về là json.
            success: function(jsonResult) { // được gọi khi web-service trả về dữ liệu.
                if (jsonResult.status == 200) {
                    $('#alert').text("Add success!");
                }
                if (jsonResult.status == 401) {
                    $('#alert').text("Account not valid!");
                }
                if (jsonResult.status == 402) {
                    $('#alert').text("Account existed!");
                }

            },
            error: function(jqXhr, textStatus, errorMessage) { // error callback

            }
        });
    },

    checkEmail : function() {
        email = $('#email').val();

        $.ajax({
            url: "/check-email",
            type: "post",
            contentType: "application/json", // dữ liệu gửi lên web-service có dạng là json.
            data: email, // object json -> string json

            dataType: "json", // dữ liệu từ web-service trả về là json.
            success: function(jsonResult) { // được gọi khi web-service trả về dữ liệu.
                if (jsonResult.status === 200) {
                    $('#message').text("Email valid!").removeClass().addClass("text-primary");
                }
                if (jsonResult.status === 400) {
                    $('#message').text("Email existed!").removeClass().addClass("text-danger");
                }
            },
            error: function(jqXhr, textStatus, errorMessage) { // error callback

            }
        });
    },
}