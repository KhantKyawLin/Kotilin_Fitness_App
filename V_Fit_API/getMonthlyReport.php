<?php
    include 'dbconnection.php';

    if (isset($_POST['userID'])) {

            $userID = $_POST['userID']; 

            $sql_existing = "SELECT SUM(duration) as total_duration, workoutMonth FROM workout WHERE userID = $userID GROUP BY workoutMonth";

            $result_exist = mysqli_query($connect, $sql_existing);

            $no_of_row = mysqli_num_rows($result_exist);

            $report = array();

            for ($i=0; $i < $no_of_row ; $i++) { 
                $record = mysqli_fetch_assoc($result_exist);

                array_push($report, $record);
            }
                
            echo json_encode($report);

            mysqli_close($connect);
    }
    else {
        echo "NO POST request!";
    }
?>