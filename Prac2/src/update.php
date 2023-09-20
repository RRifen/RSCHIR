<html lang="en">
<head>
<title>Update page</title>
    <link rel="stylesheet" href="style.css" type="text/css"/>
</head>
<body>
<h1>Операция UPDATE</h1>
<?php
if (isset($_POST['name']) && isset($_POST['description'])
    && $_POST['name'] !== '' && $_POST['description'] !== ''
    && isset($_POST['new_name']) && isset($_POST['new_description'])
    && $_POST['new_name'] !== '' && $_POST['new_description'] !== '') {
    $mysqli = new mysqli("db", "User", "123456789", "appDB");
    $statement = $mysqli->prepare("UPDATE goods SET name=?,description=? WHERE name=? && description=?");
    $statement->bind_param('ssss', $_POST['new_name'], $_POST['new_description'], $_POST['name'], $_POST['description']);
    $statement->execute();
    if (mysqli_affected_rows($mysqli) !== 0) {
        echo '<p>Update successful! You can go to <a href="index.php">SELECT</a> to see results</p>';
    } else {
        echo '<p class="error">Bad Request</p>';
    }
}
?>
<form action="update.php" method="post">
    <label for="name">Good name:</label>
    <input name="name" id="name" type="text">
    </br>
    <label for="description">Good description:</label>
    <input name="description" id="description" type="text">
    </br>
    <label for="new_name">Good new name:</label>
    <input name="new_name" id="new_name" type="text">
    </br>
    <label for="new_description">Good new description:</label>
    <input name="new_description" id="new_description" type="text">
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