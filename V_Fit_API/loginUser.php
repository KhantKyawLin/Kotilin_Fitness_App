<?php
    include 'dbconnection.php';

    if (isset($_POST['useremail']) &&
        isset($_POST['userpassword'])) {

            $email = mysqli_real_escape_string($connect, $_POST['useremail']);
            $password = mysqli_real_escape_string($connect, $_POST['userpassword']);

            // existing username

            $sql_existing = "SELECT * FROM user WHERE useremail='$email' AND userpassword='$password'";

            $result_exist = mysqli_query($connect, $sql_existing);

            $no_of_row = mysqli_num_rows($result_exist);

            if ($no_of_row == 1) {
                $record = mysqli_fetch_assoc($result_exist);
                echo "" .$record['userID'];
            }
            else {
                echo "0";
            }

            mysqli_close($connect);
    }
    else {
        echo "NO POST request!";
    }
?>