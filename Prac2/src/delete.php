<html lang="en">
<head>
<title>Delete page</title>
    <link rel="stylesheet" href="style.css" type="text/css"/>
</head>
<body>
<h1>Операция DELETE</h1>
<?php
if (isset($_POST['name']) && isset($_POST['description'])
    && $_POST['name'] !== '' && $_POST['description'] !== '') {
    $mysqli = new mysqli("db", "User", "123456789", "appDB");
    $statement = $mysqli->prepare("DELETE FROM goods WHERE name=? && description=?");
    $statement->bind_param('ss', $_POST['name'], $_POST['description']);
    $statement->execute();
    if (mysqli_affected_rows($mysqli) !== 0) {
        echo '<p>Delete successful! You can go to <a href="index.php">SELECT</a> to see results</p>';
    }
    else {
        echo '<p class="error">Bad Request</p>';
    }
}
?>
<form action="delete.php" method="post">
    <label for="name">Good name:</label>
    <input name="name" id="name" type="text">
    </br>
    <label for="description">Good description:</label>
    <input name="description" id="description" type="text">
    </br>
    <button type="submit">Submit</button>
</form>
<h1>Перейти на другую страницу</h1>
<ul>
    <?php if (basename($_SERVER['PHP_SELF']) !== 'index.php') echo '<li><a href="index.php">SELECT</a></li>'?>
    <?php if (basename($_SERVER['PHP_SELF']) !== 'insert.php') echo '<li><a href="insert.php">INSERT</a></li>'?>
    <?php if (basename($_SERVER['PHP_SELF']) !== 'update.php') echo '<li><a href="update.php">UPDATE</a></li>'?>
    <?php if (basename($_SERVER['PHP_SELF']) !== 'delete.php') echo '<li><a href="delete.php">DELETE</a></li>'?>
</ul>
</body>
</html>