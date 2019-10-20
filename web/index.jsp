<%@ page import="app.Model" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>TestTask5</title>
</head>

<body>
<div>
    <h1>Список товаров</h1>
</div>
<button onclick="location.href='/create'">Добавить продукт</button>
<table border="1">
    <tr>
        <th>Идентификатор</th>
        <th>Название</th>
        <th>Описание</th>
        <th>Добавлен в базу</th>
        <th>Ячейка хранения</th>
        <th>Зарезервировано</th>
        <th>Изменить запись</th>
    </tr>
    <%
        // Table initialization
//        String table = (String) request.getAttribute("table");
        String table = Model.refresh();
        if (/*table != null && */!table.equals("")) {
            out.println(table);
        } else out.println("<td>-</td><td>-</td><td>-</td><td>-</td><td>-</td><td>-</td><td>-</td><td>-</td>");
    %>
</table>

<script>
    function Delete(Element) {
        var id = Element.id;

        if ((typeof lineForDeleteID !== 'number') || (lineForDeleteID < 0)) return;
        var url = "/delete?id=" + id;

        // Открыть соединение с сервером
        xmlHttp.open("GET", url, true);

        // Установить функцию для сервера, которая выполнится после его ответа
        xmlHttp.onreadystatechange = updatePage;

        // SПередать запрос
        xmlHttp.send(null);

    }
</script>
<p> <input type="button" value="Удалить" onclick="return Delete(this);"> </p>

</body>
</html>