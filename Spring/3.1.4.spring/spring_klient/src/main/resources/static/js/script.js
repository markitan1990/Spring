var user = null;
var allUsers = null;

/////////////////////////////////////////////// при скролле header прилипает
$(window).scroll(function () {
    if ($(this).scrollTop() > 100) {
        $("#scroll_header").addClass("active");
    } else {
        $("#scroll_header").removeClass("active");
    }
});

/////////////////////////////////////////////////////// меняю title в браузере
$("#v-pills-home-tab").click(function () {
    $("#title").text("Admin panel");
});
$("#v-pills-profile-tab").click(function () {
    $("#title").text("User page");
});

$(document).ready(function () {
    var s = "";
    $.ajax({   ////////////////////////////// Заполняем шапку данными о пользователе
        url: '/findUser',
        type: 'get',
        dataType: 'json',
        success: function (data) {
            user = data;
            for (var i in user.roles) {
                s += user.roles[i].roleName + " ";
            }
///////////////////////////////////////////////////////// заполнение данных о пользователе в header
            $("#principalInfo").html("<b style='margin: 0  5px;'>" + user.email + "</b>");
            $("#principalRoles").text(s);

            ////////////////////////////// заполняем панель навигации с учетом возможных ролей
            var sArray = s.split(' ');
            if (sArray.includes("admin") == true && !sArray.includes("user") == true) {
                $("#title").text("Admin panel");
                isAdmin();
            } else if (!sArray.includes("admin") == true && sArray.includes("user") == true) {
                $("#v-pills-profile-tab, #v-pills-profile").addClass("active");
                $("#v-pills-profile").addClass("show");
                $("#title").text("User page");
                isUser(user);
            } else {
                $("#title").text("Admin panel");
                isAdmin();
                isUser(user);
            }
        }

    });
});

///////////////////////////////////////////////// добавление нового пользователя
$("#addUserForm").click(
    function () {
        var add_u = $("#addNUser").serialize();
        $.ajax({
            url: "/addUser",
            type: "get",
            data: add_u,
            success: function () {
                $("#table_tbody").empty();
                isAdmin();
                setTimeout(function () {
                    $("#nav-home-tab").trigger('click');
                    $('#addNUser')[0].reset();
                }, 500);
            }
        });
    }
);

///////////////////////////////////////////////////// удаление пользователя
function deleteU(data) {
    var id = data.id;
    $.ajax({
        url: "/deleteUser/" + id,
        type: "get",
        success: function (data) {
            $("#table_tbody").empty();

            isAdmin();
        }
    });
    return false;
};

function deleteUser(data) {
    $('.form-control').prop('disabled', true);
    $("#d_roles, .delete_u").css("display", "block");
    $("#e_roles, .edit_u ").css("display", "none");
    $(".delete_u").attr("id", data.id);
    $("#exampleModalLabel").text("Delete user");
    $("#d_password, .d_password").css("display", "none");
    fillFormForEditAndDelete(data);
};

/////////////////////////////////////////////////////////////////// редактирование пользователя
function editUser(data) {
    $('#d_id').prop('disabled', true);
    $("#d_password, .d_password").css("display", "block");
    $("#d_roles, .delete_u").css("display", "none");
    $("#e_roles, .edit_u").css("display", "block");
    $(".edit_u").attr("id", data.id);
    $("#exampleModalLabel").text("Edit user");
    fillFormForEditAndDelete(data);
};

function editU(id) {
    var id = id.id;
    var edit = $("#formForEdit").serialize();
    $.ajax({
        url: "/editUser/" + id,
        type: "get",
        data: edit,
        success: function (data) {
            $("#table_tbody").empty();
            isAdmin();
        }
    });
    return false;
};

////////////////////////////////////////////////// заполнение модальной формы данными
function fillFormForEditAndDelete(data) {
    var id = data.id;
    $(".deleteButton").attr("id", id);
    $("#exampleModal, .modal_bg").addClass("show").css('display', 'block');

    for (var i = 0; i < allUsers.length; i++) {
        if (allUsers[i].id == id) {
            $("#d_id").val(allUsers[i].id);
            $("#d_name").val(allUsers[i].firstName);
            $("#d_last_name").val(allUsers[i].lastName);
            $("#d_age").val(allUsers[i].age);
            $("#d_email").val(allUsers[i].email);
            $("#d_password").val(allUsers[i].password);
            if (allUsers[i].roles.length == 2) {
                $('#d_roles').append('<option value="">' + allUsers[i].roles[0].roleName + '</option>');
                $('#d_roles').append('<option value="">' + allUsers[i].roles[1].roleName + '</option>');
            } else {
                $('#d_roles').append('<option value="">' + allUsers[i].roles[0].roleName + '</option>');
            }
            break;
        }
    }
}

/////////////////////////////////////////////////////////////////////// закрывает модальное окно
$(".closeCus, .modal_bg, .deleteButton").click(function () {
    $("#exampleModal, .modal_bg").removeClass("show").css('display', 'none');
    $('#d_roles').empty();
    $('.form-control').prop('disabled', false);
});

//////////////////////////////////////////////////////////////// функция заполняет таблицу admin
function isAdmin() {
    $.ajax({
        url: '/getUsers',
        type: 'get',
        dataType: 'json',
        success: function (data) {
            allUsers = data;
            for (var i = 0; i < data.length; i++) {
                var roles = "";
                for (var j in data[i].roles) {
                    roles += data[i].roles[j].roleName + " ";
                }
                $("#table_tbody").append(
                    "<tr>" +
                    "<th scope='row'>" + data[i].id + "</th>" +
                    "<td>" + data[i].firstName + "</td>" +
                    "<td>" + data[i].lastName + "</td>" +
                    "<td>" + data[i].age + "</td>" +
                    "<td>" + data[i].email + "</td>" +
                    "<td style='text-transform: uppercase'>" + roles + "</td>" +
                    "<td>" +
                    "<button id=" + data[i].id + "  name='Edit' class='btn btn-primary ' onclick='editUser(this)'>Edit</button>" +
                    "</td>" +
                    "<td>" +
                    "<button id=" + data[i].id + "  name='Delete'  class='btn btn-primary delete_user' onclick='deleteUser(this)'>Delete</button>" +
                    "</td>" +
                    "</tr>");
            }
        }
    });
}

/////////////////////////////////////////////////////////// функция заполняет таблицу user
function isUser(data) {
    var roles = "";
    for (var i in data.roles) {
        roles += data.roles[i].roleName + " ";
    }
    $("#user_table").append(
        "<tr>" +
        "<th scope='row'>" + data.id + "</th>" +
        "<td>" + data.firstName + "</td>" +
        "<td>" + data.lastName + "</td>" +
        "<td>" + data.age + "</td>" +
        "<td>" + data.email + "</td>" +
        "<td style='text-transform: uppercase'>" + roles + "</td>" +
        "</tr>");
}
