<html lang="en">
<head>
<title>Create page</title>
    <link rel="stylesheet" href="style.css" type="text/css"/>
</head>
<body>
<h1>Операция CREATE</h1>
<?php
if (isset($_POST['name']) && isset($_POST['description'])
    && $_POST['name'] !== '' && $_POST['description'] !== '') {
    $mysqli = new mysqli("db", "User", "123456789", "appDB");
    $statement = $mysqli->prepare("INSERT INTO goods(name, description) VALUES(?, ?)");
    $statement->bind_param('ss', $_POST['name'], $_POST['description']);
    try {
        $statement->execute();
        echo '<p>Insert successful! You can go to <a href="index.php">SELECT</a> to see results</p>';
    }
    catch (Exception $e) {
        echo '<p class="error">Bad Request</p>';
    }
}
?>
<form action="create.php" method="post">
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
    <?php if (basename($_SERVER['PHP_SELF']) !== 'index.php') echo '<li><a href="index.php">SELECT ALL</a></li>';
    if (basename($_SERVER['PHP_SELF']) !== 'create.php') echo '<li><a href="create.php">CREATE</a></li>';
    if (basename($_SERVER['PHP_SELF']) !== 'read.php') echo '<li><a href="read.php">READ</a></li>';
    if (basename($_SERVER['PHP_SELF']) !== 'update.php') echo '<li><a href="update.php">UPDATE</a></li>';
    if (basename($_SERVER['PHP_SELF']) !== 'delete.php') echo '<li><a href="delete.php">DELETE</a></li>'?>
</ul>
</body>
</html>