<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>TestTask5</title>
    <%@ page import="app.ModelTMP" %>
    <%@ page contentType="text/html;charset=UTF-8" %>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/2.2.0/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery.maskedinput/1.4.1/jquery.maskedinput.min.js"></script>
    <script src="/scripts/calendar/tcal.js"></script>
<%--    <script src="https://cdn.jsdelivr.net/npm/jquery-validation@1.19.1/dist/jquery.validate.js"></script>--%>
    <link rel="stylesheet" type="text/css" href="/scripts/calendar/tcal.css" />
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
        <th>Зарезервирован</th>
        <th>Изменить запись</th>
    </tr>
    <%
        // Table initialization
//        String table = (String) request.getAttribute("table");
        String table = ModelTMP.refresh();
        if (/*table != null && */!table.equals("")) {
            out.println(table);
        } else out.println("<td>-</td><td>-</td><td>-</td><td>-</td><td>-</td><td>-</td><td>-</td><td>-</td>");
    %>
</table>

<script>
    reqest.getAll();
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
// Маска даты
    $(function(){
        $('input#create_date').mask('99.99.9999');

    });

    // function validateForm() {
        // var result;
        // var date = document.forms["CreateForm"]["create_date"].value;
        // var arrD = date.split(".");
        // arrD[1] -= 1;
        // var d = new Date(arrD[2], arrD[1], arrD[0]);
        // if ((d.getFullYear() == arrD[2]) && (d.getMonth() == arrD[1]) && (d.getDate() == arrD[0])) {
        //     result = true;
        // } else {
        //     alert("Введена некорректная дата");
        //     result = false;
        // }
    //
    //     var i = document.forms["CreateForm"]['place_storage'].value;
    //     if (isFinite(i) && +i >=0) {
    //         // if (!isNaN(+i) && isFinite(i) && +i >=0) {
    //         result = true;
    //     } else {
    //         alert("Введен некорректный номер ячейки хранения");
    //         result = false;
    //     }
    //     return result;
    // }
</script>
<p> <input type="button" value="Удалить" onclick="return Delete(this);"> </p>

<div name="CreateForm">
    <form action="/create" onsubmit="return validateForm()">
        <h1>Создание продукта</h1>
        <label for="name"><b>Название</b></label>
        <input type="text" id="name" maxlength="512" required>
        <br>
        <label for="description"><b>Описание</b></label>
        <textarea  type="text" id="description" maxlength="1024" rows="3" cols="22" required></textarea>
        <br>
        <label for="create_date"><b>Добавлен в базу</b></label>
        <input type="text" id="create_date" class="tcal" required>
        <br>
        <label for="place_storage"><b>Ячейка хранения</b></label>
        <input type="text" id="place_storage" required>
        <br>
        <label for="reserved"><b>Зарезервирован</b></label>
        <input type="checkbox" id="reserved">
        <br>
        <button type="submit">Создать</button>
        <br>
        <button type="button" onclick="closeForm()">Отмена</button>
    </form>
</div>

</body>
</html>