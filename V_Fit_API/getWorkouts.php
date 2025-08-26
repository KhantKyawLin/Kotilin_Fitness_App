<?php
include 'dbconnection.php';

if (isset($_POST['userID'])) {
    $userID = $_POST['userID'];

    $sql_existing = "SELECT * FROM workout WHERE userID = $userID";
    $result_exist = mysqli_query($connect, $sql_existing);

    $workouts = array();

    while ($record = mysqli_fetch_assoc($result_exist)) {
        array_push($workouts, $record);
    }

    echo json_encode($workouts);

    mysqli_close($connect);
} else {
    echo json_encode(["error" => "No POST request!"]);
}
?>