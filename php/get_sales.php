<?php
require 'config.php';

header("Access-Control-Allow-Origin: *");
header("Content-Type: application/json; charset=UTF-8");

$conn = new mysqli($servername, $username, $password, $dbname);

if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
}

$sql = "SELECT * FROM sales";
$result = $conn->query($sql);

$sales = array();
if ($result->num_rows > 0) {
    while($row = $result->fetch_assoc()) {
        $sales[] = array(
            'date' => $row['date'],
            'item_name' => $row['item_name'],
            'quantity' => $row['quantity'],
            'total_price' => $row['total_price']
        );
    }
}

echo json_encode($sales, JSON_UNESCAPED_UNICODE);
$conn->close();
?>
