<%--
  Created by IntelliJ IDEA.
  User: EddieSevenMBA
  Date: 4/9/18
  Time: 12:00 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Anton">
    <link rel="stylesheet" type="text/css" href="risk-styles.css">
    <meta charset="UTF-8">
    <title>Risk Demo</title>
</head>
<body>
    <!-- Left Panel -->
    <div id=startpage">
        <h1>Risk</h1>
        <h2>Welcome to Risk, ${user}!</h2>
    </div>${user}

    <!-- Right Panel -->
    <div id="board">
        <img src="../resources/world_map.png">
    </div>
</body>
</html>
