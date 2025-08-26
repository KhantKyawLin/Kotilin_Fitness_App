<?php
    include 'dbconnection.php';

    if (isset($_POST['userID'])) {

            $userID = $_POST['userID']; 

            $sql_existing = "SELECT * FROM user WHERE userID = $userID";

            $result_exist = mysqli_query($connect, $sql_existing);

            $no_of_row = mysqli_num_rows($result_exist);

            if ($no_of_row == 1) {
                $record = mysqli_fetch_assoc($result_exist);
                // echo "" . $record['username'] . ", " . $record['useremail'];

                echo json_encode($record);
            }

            mysqli_close($connect);
    }
    else {
        echo "NO POST request!";
    }
?>