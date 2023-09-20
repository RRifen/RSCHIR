<?php
require_once('Database.php');
$db = new Database();
if (!isset($db->mysqli)) exit;

$method = $_SERVER['REQUEST_METHOD'];
$url = $_SERVER['REQUEST_URI'];
$url_data = parse_url($url);
$paths = explode('/', trim($url_data['path'], '/'));
$params = null;
if (isset($url_data['query'])) {
    $params = explode('&', $url_data['query']);
}

switch ($method) {
    case 'PUT':
        db_func('update', $paths, $params);
        break;
    case 'GET':
        db_func('get', $paths, $params);
        break;
    case 'POST':
        db_func('create', $paths, $params);
        break;
    case 'DELETE':
        db_func('delete', $paths, $params);
        break;
    default:
        unknown_method();
}

function db_func($funcName, $data, $params) {
    global $db;
    $result = '';
    switch($data[1]) {
        case 'goods':
            $result = $db->{$funcName.'_goods'}($data, $params);
            break;
        case 'producers':
            $result = $db->{$funcName.'_producers'}($data, $params);
            break;
    }
    return returnJSON($result);
}

function returnJSON($result) {
    header('Content-Type: application/json; charset=utf-8');
    if(isset($result['success'])){
        header("{$_SERVER['SERVER_PROTOCOL']} {$result['status']}");
        echo json_encode($result['success']);
    } else {
        header("{$_SERVER['SERVER_PROTOCOL']} 400 Bad Request");
        echo json_encode(array(
            'error' => 'Bad Request')
        );
    } 
}

function unknown_method() {
    header("{$_SERVER['SERVER_PROTOCOL']} 400 Bad Request");
    echo json_encode(array(
        'error' => 'Bad Request')
    );
}
   
?>