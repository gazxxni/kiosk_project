<?php
require 'config.php';

header("Access-Control-Allow-Origin: *");
header("Access-Control-Allow-Methods: GET, POST, OPTIONS");
header("Access-Control-Allow-Headers: Content-Type, Authorization");

$conn = new mysqli($servername, $username, $password, $dbname);

if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
}

$sql = "SELECT name, category_id, price, image_url, quantity FROM items";
$result = $conn->query($sql);

$items = array();
if ($result->num_rows > 0) {
    while($row = $result->fetch_assoc()) {
        $category = "";
        switch ($row['category_id']) {
            case 1:
                $category = "coffee";
                break;
            case 2:
                $category = "drink";
                break;
            case 3:
                $category = "dessert";
                break;
        }

        $image_url = str_replace("C:\\xampp\\htdocs\\", "http://10.0.2.2/", $row['image_url']);
        $image_url = str_replace("\\", "/", $image_url);
        $image_url = str_replace('"', '', $image_url);

        $items[] = array(
            'name' => $row['name'],
            'category' => $category,
            'price' => $row['price'],
            'image_url' => $image_url,
            'quantity' => $row['quantity']
        );
    }
}

echo json_encode($items, JSON_UNESCAPED_SLASHES);
$conn->close();
?>
