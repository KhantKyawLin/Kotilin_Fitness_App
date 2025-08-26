<?php
    include 'dbconnection.php';

    if (isset($_POST['workoutID'])) {
        $workoutID = $_POST['workoutID'];
        $sql_existing = "SELECT * FROM workout WHERE workoutID = $workoutID";
        $result_exist = mysqli_query($connect, $sql_existing);
        $no_of_row = mysqli_num_rows($result_exist);
        if ($no_of_row == 1) {
            $record = mysqli_fetch_assoc($result_exist);
            // Replace NULL values with defaults
            $record['duration'] = $record['duration'] ?? 0;
            $record['userID'] = $record['userID'] ?? 0;
            $record['workoutDay'] = $record['workoutDay'] ?? 0;
            $record['workoutMonth'] = $record['workoutMonth'] ?? 0;
            $record['workoutYear'] = $record['workoutYear'] ?? 0;
            $record['workoutID'] = $record['workoutID'] ?? 0;
            $record['workoutType'] = $record['workoutType'] ?? '';
            $record['workoutDate'] = $record['workoutDate'] ?? '';
            $record['workoutTime'] = $record['workoutTime'] ?? '';
            $record['indoor_outdoor'] = $record['indoor_outdoor'] ?? '';
            $record['equipments'] = $record['equipments'] ?? '';
            $record['distance'] = $record['distance'] ?? 0.0;
            $record['workoutWeight'] = $record['workoutWeight'] ?? 0.0;
            $record['remark'] = $record['remark'] ?? '';
            // Use JSON_UNESCAPED_SLASHES to prevent escaping slashes
            echo json_encode($record, JSON_UNESCAPED_SLASHES);
        } else {
            echo json_encode(["error" => "Workout not found"], JSON_UNESCAPED_SLASHES);
        }
        mysqli_close($connect);
    } else {
        echo json_encode(["error" => "No POST request!"], JSON_UNESCAPED_SLASHES);
    }
?>