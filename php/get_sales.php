<?php
require 'config.php';

header("Access-Control-Allow-Origin: *");
header("Content-Type: application/json; charset=UTF-8");

$date = isset($_GET['date']) ? $_GET['date'] : '';

$sql = "SELECT date, item_name, quantity, total_price FROM sales WHERE date = ?";
$stmt = $conn->prepare($sql);
$stmt->bind_param("s", $date);
$stmt->execute();
$result = $stmt->get_result();

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
