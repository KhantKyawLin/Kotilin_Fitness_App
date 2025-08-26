<?php

    include 'dbconnection.php';

    $id = $_POST['workoutID'];

    $sql = "DELETE FROM workout WHERE workoutID = $id";

    if (mysqli_query($connect, $sql)) {
        echo "Workout is successfully Deleted!";
    }
    else {
        echo "Deletion Error!";
    }

    mysqli_close($connect);
?>