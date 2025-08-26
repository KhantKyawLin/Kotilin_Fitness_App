<?php
include 'dbconnection.php';

// Log the received POST data
error_log("POST Data: " . print_r($_POST, true));

if (isset($_POST['workoutType']) && 
    isset($_POST['workoutDate']) &&
    isset($_POST['workoutDay']) &&
    isset($_POST['workoutMonth']) &&
    isset($_POST['workoutYear']) &&
    isset($_POST['workoutTime']) &&
    isset($_POST['indoorOutdoor']) &&
    isset($_POST['equipments']) &&
    isset($_POST['distance']) &&
    isset($_POST['duration']) &&
    isset($_POST['workoutWeight']) &&
    isset($_POST['remark']) &&
    isset($_POST['userID'])) {
    
    $workoutType = mysqli_real_escape_string($connect, $_POST['workoutType']);
    $workoutDate = mysqli_real_escape_string($connect, $_POST['workoutDate']);
    $workoutDay = $_POST['workoutDay'];
    $workoutMonth = $_POST['workoutMonth'];
    $workoutYear = $_POST['workoutYear'];
    $workoutTime = mysqli_real_escape_string($connect, $_POST['workoutTime']);
    $indoorOutdoor = mysqli_real_escape_string($connect, $_POST['indoorOutdoor']);
    $equipments = mysqli_real_escape_string($connect, $_POST['equipments']);
    $distance = $_POST['distance'];
    $duration = $_POST['duration'];
    $workoutWeight = $_POST['workoutWeight'];
    $remark = mysqli_real_escape_string($connect, $_POST['remark']);
    $userID = $_POST['userID'];

    // New workout
    $sql = "INSERT INTO workout (workoutType, workoutDate, workoutDay, workoutMonth, workoutYear, workoutTime, indoor_outdoor, equipments, distance, duration, workoutWeight, remark, userID) VALUES 
        (
            '$workoutType',
            '$workoutDate',
            '$workoutDay',
            '$workoutMonth',
            '$workoutYear',
            '$workoutTime',
            '$indoorOutdoor',
            '$equipments',
            '$distance',
            '$duration',
            '$workoutWeight',
            '$remark',
            '$userID'
        )";

    if (mysqli_query($connect, $sql)) {
        echo "Workout successfully completed!";
    } else {
        echo "Saving error: " . mysqli_error($connect);
    }

    mysqli_close($connect);
} else {
    echo "No POST request! Missing parameters: ";
    $missing = [];
    if (!isset($_POST['workoutType'])) $missing[] = 'workoutType';
    if (!isset($_POST['workoutDate'])) $missing[] = 'workoutDate';
    if (!isset($_POST['workoutDay'])) $missing[] = 'workoutDay';
    if (!isset($_POST['workoutMonth'])) $missing[] = 'workoutMonth';
    if (!isset($_POST['workoutYear'])) $missing[] = 'workoutYear';
    if (!isset($_POST['workoutTime'])) $missing[] = 'workoutTime';
    if (!isset($_POST['indoorOutdoor'])) $missing[] = 'indoorOutdoor';
    if (!isset($_POST['equipments'])) $missing[] = 'equipments';
    if (!isset($_POST['distance'])) $missing[] = 'distance';
    if (!isset($_POST['duration'])) $missing[] = 'duration';
    if (!isset($_POST['workoutWeight'])) $missing[] = 'workoutWeight';
    if (!isset($_POST['remark'])) $missing[] = 'remark';
    if (!isset($_POST['userID'])) $missing[] = 'userID';
    echo implode(", ", $missing);
}
?>