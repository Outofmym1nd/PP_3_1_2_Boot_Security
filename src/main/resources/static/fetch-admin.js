const url = 'http://localhost:8080/api/admin';

$(async function() {
    allUsers();
    editUser();
    deleteUser();
    createUser();
});

async function allUsers() {
    const tableAdmin = $('#admin-tbody');
    tableAdmin.empty();
    let response = await fetch(url);
    let userData = await response.json();
    userData.forEach(user => {
        let role = user.roles.map(role => " " + role.role.slice(5));
        let users = `$(
            <tr><td>${user.id}</td>
            <td>${user.name}</td>
            <td>${user.lastName}</td>
            <td>${user.age}</td>
            <td>${user.email}</td>
            <td>${role}</td>
            <td>
                <button type="button" class="btn btn-info btn-sm" data-toggle="modal" id="buttonEdit"
                data-action="edit" data-id="${user.id}" data-target="#edit">Edit</button>
            </td>
            <td>
                <button type="button" class="btn btn-danger btn-sm" data-toggle="modal" id="buttonDelete"
                data-action="delete" data-id="${user.id}" data-target="#delete">Delete</button>
            </td></tr>)`;

        tableAdmin.append(users);
    })
}

$('#delete').on('show.bs.modal', async click => {
    let button = $(click.relatedTarget);
    let id = button.data('id');

    let urlId = "http://localhost:8080/api/admin/users/" + id;
    let response = await fetch(urlId);
    let user = await response.json();
    idDelete.value = user.id;
    firstNameDelete.value = user.name;
    lastNameDelete.value = user.lastName;
    ageDelete.value = user.age;
    emailDelete.value = user.email;
})

function deleteUser(){
    const formDelete = document.forms["formDeleteUser"];
    formDelete.addEventListener("submit", async event => {
        event.preventDefault();
        await fetch("http://localhost:8080/api/admin/users/" + idDelete.value, {
            method: "DELETE",
            headers: {
                "Content-Type": "application/json"
            }});
        $('#btnCloseDeleteModal').click();
        allUsers();
    })
}

$('#edit').on('show.bs.modal', async click => {
    let button = $(click.relatedTarget);
    let id = button.data('id');

    let urlId = "http://localhost:8080/api/admin/users/" + id;
    let response = await fetch(urlId);
    let user = await response.json();
    idEdit.value = user.id;
    firstNameEdit.value = user.name;
    lastNameEdit.value = user.lastName;
    ageEdit.value = user.age;
    emailEdit.value = user.email;
})

function editUser() {
    const formEdit = document.forms["formEditUser"];
    formEdit.addEventListener("submit", async event => {
        event.preventDefault();
        let userRoles = [];
        if (roleEdit.value !== "") {
            userRoles[0] = roleEdit.value;
        }

        await fetch("http://localhost:8080/api/admin/users/" + idEdit.value, {
            method: "PATCH",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({
                id: idEdit.value,
                name: firstNameEdit.value,
                lastName: lastNameEdit.value,
                age: ageEdit.value,
                email: emailEdit.value,
                password: passwordEdit.value,
                roles: userRoles
            })});
        $('#btnCloseEditModal').click();
        allUsers();
    })
}

function createUser() {
    const formCreate = document.forms["formCreateUser"];
    formCreate.addEventListener("submit", async event => {
        event.preventDefault();
        let userRoles = [];
        if (roles.value !== "") {
            userRoles[0] = roles.value;
        }

        await fetch(url, {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({
                name: firstName.value,
                lastName: lastName.value,
                age: age.value,
                email: email.value,
                password: password.value,
                roles: userRoles
            })});
        formCreate.reset();
        allUsers();
        $('#all-users-tab').click();
    })
}