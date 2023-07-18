$(document).ready(function () {
    $.get("/api/tasks", function (data) {
        const tableBody = $('table#task tbody');
        tableBody.empty();
        let html = "";
        data.forEach((o, i) => {
            html += `<tr>
                        <th scope="row">`+(i+1)+`</th>
                        <td>`+o.assignee+`</td>
                        <td>`+o.node+`</td>
                        <td>`+o.flow+`</td>
                        <td>`+o.status+`</td>
                    </tr>`;
        });
        tableBody.html(html);
    });
});