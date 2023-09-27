<html lang="en">
<head>
<title>Select page</title>
    <link rel="stylesheet" href="style.css" type="text/css"/>
</head>
<body>
<h1>Таблица товаров (Операция SELECT)</h1>
<table>
    <tr><th>Id</th><th>Name</th><th>Description</th></tr>
<?php
$mysqli = new mysqli("db", "User", "123456789", "appDB");
$result = $mysqli->query("SELECT * FROM goods");
foreach ($result as $row){
    echo "<tr><td>{$row['ID']}</td><td>{$row['name']}</td><td>{$row['description']}</td></tr>";
}
?>
</table>
<h1>Перейти на другую страницу</h1>
<div class="wrapper">
    <ul>
        <?php if (basename($_SERVER['PHP_SELF']) !== 'index.php') echo '<li><a href="index.php">SELECT ALL</a></li>';
        if (basename($_SERVER['PHP_SELF']) !== 'create.php') echo '<li><a href="create.php">CREATE</a></li>';
        if (basename($_SERVER['PHP_SELF']) !== 'read.php') echo '<li><a href="read.php">READ</a></li>';
        if (basename($_SERVER['PHP_SELF']) !== 'update.php') echo '<li><a href="update.php">UPDATE</a></li>';
        if (basename($_SERVER['PHP_SELF']) !== 'delete.php') echo '<li><a href="delete.php">DELETE</a></li>'?>
    </ul>
</div>
</body>
</html>