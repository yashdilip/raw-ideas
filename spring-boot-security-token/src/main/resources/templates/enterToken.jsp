<!DOCTYPE html>
<html>
<head>
    <title>Enter Token</title>
</head>
<body>
    <h2>Enter Token</h2>
    <form action="/validate-token" method="post">
        <label>Token:</label>
        <input type="text" name="token"/><br/>
        <input type="submit" value="Submit"/>
        <c:if test="\${not empty error}">
            <div style="color: red">\${error}</div>
        </c:if>
    </form>
</body>
</html>
