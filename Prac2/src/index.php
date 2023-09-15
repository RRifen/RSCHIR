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
<ul>
    <?php if (basename($_SERVER['PHP_SELF']) !== 'index.php') echo '<li><a href="index.php">SELECT</a></li>'?>
    <?php if (basename($_SERVER['PHP_SELF']) !== 'insert.php') echo '<li><a href="insert.php">INSERT</a></li>'?>
    <?php if (basename($_SERVER['PHP_SELF']) !== 'update.php') echo '<li><a href="update.php">UPDATE</a></li>'?>
    <?php if (basename($_SERVER['PHP_SELF']) !== 'delete.php') echo '<li><a href="delete.php">DELETE</a></li>'?>
</ul>
</body>
</html>