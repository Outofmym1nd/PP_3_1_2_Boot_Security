fetch('http://localhost:8080/api/user')
    .then(response => response.json())
    .then(userData => {
        let role = userData.roles.map(role => role.role.slice(5));
        let user = `
            <tr><td>${userData.id}</td>
            <td>${userData.name}</td>
            <td>${userData.lastName}</td>
            <td>${userData.age}</td>
            <td>${userData.email}</td>
            <td>${role}</td></tr>`;
        $('#user-tbody').append(user);
    });


