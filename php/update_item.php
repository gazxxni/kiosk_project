<?php
require 'config.php';

header("Access-Control-Allow-Origin: *");
header("Access-Control-Allow-Methods: POST");
header("Access-Control-Allow-Headers: Content-Type, Authorization");

$conn = new mysqli($servername, $username, $password, $dbname);

if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
}

$data = json_decode(file_get_contents("php://input"), true);
$id = $data['id'];
$name = $data['name'];
$category_id = $data['category_id'];
$price = $data['price'];
$image_url = $data['image_url'];
$quantity = $data['quantity'];

$sql = "UPDATE items SET name=?, category_id=?, price=?, image_url=?, quantity=? WHERE id=?";
$stmt = $conn->prepare($sql);
$stmt->bind_param("siisii", $name, $category_id, $price, $image_url, $quantity, $id);

if ($stmt->execute()) {
    echo json_encode(["message" => "Item updated successfully"]);
} else {
    echo json_encode(["message" => "Error updating item"]);
}

$stmt->close();
$conn->close();
?>
