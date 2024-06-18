<?php
require 'config.php';

header("Access-Control-Allow-Origin: *");
header("Access-Control-Allow-Methods: GET, POST, OPTIONS");
header("Access-Control-Allow-Headers: Content-Type, Authorization");

$conn = new mysqli($servername, $username, $password, $dbname);

if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
}

$sql = "SELECT * FROM promotions";
$result = $conn->query($sql);

$promotions = array();
if ($result->num_rows > 0) {
    while($row = $result->fetch_assoc()) {
        $promotions[] = array(
            'id' => $row['id'],
            'name' => $row['name'],
            'detail' => $row['detail']
        );
    }
}

echo json_encode($promotions, JSON_UNESCAPED_UNICODE);
$conn->close();
?>
