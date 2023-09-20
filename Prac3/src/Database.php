<?php

class Database
{
    public $mysqli;

    public function __construct() {
        $this->mysqli = null;

        try {
            $this->mysqli = new mysqli("db", "User", "123456789", "appDB");;
            $this->mysqli->set_charset("utf8");
        } catch (Exception $exception) {
            header("{$_SERVER['SERVER_PROTOCOL']} 502 Bad Gateway");
            echo json_encode(array(
                'error' => 'Bad Gateway')
            );
        }
    }

    public function create_goods($data, $params) {
        $params = $this->get_params_from_body();
        $statement = $this->mysqli->prepare("INSERT INTO goods(producer_id, name, description) VALUES(?, ?, ?)");
        $statement->bind_param('iss', $params['producer_id'], $params['name'], $params['description']);
        try {
            $statement->execute();
            $result = $statement->get_result();
            $result = $this->mysqli->query("SELECT * FROM goods WHERE good_id={$this->mysqli->insert_id}");
            return $this->formReturn($result->fetch_array(MYSQLI_ASSOC), "201 Created");
        }
        catch (Exception) {
            return;
        }
    }

    public function create_producers($data, $params) {
        $params = $this->get_params_from_body();
        $statement = $this->mysqli->prepare("INSERT INTO producers(name, description) VALUES(?, ?)");
        $statement->bind_param('ss', $params['name'], $params['description']);
        try {
            $statement->execute();
            $result = $statement->get_result();
            $result = $this->mysqli->query("SELECT * FROM producers WHERE producer_id={$this->mysqli->insert_id}");
            return $this->formReturn($result->fetch_array(MYSQLI_ASSOC), "201 Created");
        }
        catch (Exception) {
            return;
        }
    }

    public function update_goods($data, $params) {
        try {
            if (isset($data[2])) {
                $id = $data[2];
                $params = $this->get_params_from_body();
                $command = "UPDATE goods SET ";
                $values = array();
                foreach($params as $key => $value) {
                    array_push($values, $key."='".$value."'");
                }
                $command .= implode(',', $values).' ';
                $command .= "WHERE good_id={$id}";
                $result = $this->mysqli->query($command);
                if ($result) $result = $this->mysqli->query("SELECT * FROM goods WHERE good_id={$id}");
                return $this->formReturn($result->fetch_array(MYSQLI_ASSOC), "200 OK");
            }
        }
        catch (Exception) {
            return;
        }
    }

    public function update_producers($data, $params) {
        try {
            if (isset($data[2])) {
                $id = $data[2];
                $params = $this->get_params_from_body();
                $command = "UPDATE producers SET ";
                $values = array();
                foreach($params as $key => $value) {
                    array_push($values, $key."='".$value."'");
                }
                $command .= implode(',', $values).' ';
                $command .= "WHERE producer_id={$id}";
                $result = $this->mysqli->query($command);
                if ($result) $result = $this->mysqli->query("SELECT * FROM producers WHERE producer_id={$id}");
                return $this->formReturn($result->fetch_array(MYSQLI_ASSOC), "200 OK");
            }
        }
        catch (Exception) {
            return;
        }
    }

    public function get_goods($data, $params) {
        try {
            if (isset($data[2])) {
                $statement = $this->mysqli->prepare("SELECT * FROM goods WHERE good_id=?");
                $statement->bind_param('i', $data[2]);
                $statement->execute();
                $result = $statement->get_result();
            }
            else {
                $result = $this->mysqli->query("SELECT * FROM goods");
            }
            $get = array();
            $index = 0;
            while ($row = $result->fetch_array(MYSQLI_ASSOC)) {
                $get += [$index => $row];
                ++$index;
            }
            $get = $this->formReturn($get, "200 OK");
            return $get;
        }
        catch (Exception) {
            return;
        }
    }

    public function get_producers($data, $params) {
        try {
            if (isset($data[2])) {
                $statement = $this->mysqli->prepare("SELECT * FROM producers WHERE producer_id=?");
                $statement->bind_param('i', $data[2]);
                $statement->execute();
                $result = $statement->get_result();
            }
            else {
                $result = $this->mysqli->query("SELECT * FROM producers");
            }
            $get = array();
            $index = 0;
            while ($row = $result->fetch_array(MYSQLI_ASSOC)) {
                $get += [$index => $row];
                ++$index;
            }
            $get = $this->formReturn($get, "200 OK");
            return $get;
        }
        catch (Exception) {
            return;
        }
    }

    public function delete_goods($data, $params) {
        try {
            if (isset($data[2])) {
                $statement = $this->mysqli->prepare("DELETE FROM goods WHERE good_id=?");
                $statement->bind_param('i', $data[2]);
                $statement->execute();
                $delete = array();
                $delete = $this->formReturn($delete, "200 OK");
                return $delete;
            }
        }
        catch (Exception) {
            return;
        }
    }

    public function delete_producers($data, $params) {
        try {
            if (isset($data[2])) {
                $statement = $this->mysqli->prepare("DELETE FROM producers WHERE producer_id=?");
                $statement->bind_param('i', $data[2]);
                $statement->execute();
                $delete = array();
                $delete = $this->formReturn($delete, "200 OK");
                return $delete;
            }
        }
        catch (Exception) {
            return;
        }
    }

    public function formReturn($data, $status) {
        $created = array(
            "success" => $data,
            "status" => $status
        );
        return $created;
    }

    public function get_params_from_body() {
        $json = file_get_contents('php://input');
        return json_decode($json, true);
    }
}