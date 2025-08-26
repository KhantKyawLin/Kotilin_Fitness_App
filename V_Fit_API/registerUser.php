<?php

    include 'dbconnection.php';

    if (isset($_POST['username']) && 
        isset($_POST['useremail']) &&
        isset($_POST['userpassword'])) {
        
            $name = mysqli_real_escape_string($connect, $_POST['username']);
            $email = mysqli_real_escape_string($connect, $_POST['useremail']);
            $password = mysqli_real_escape_string($connect, $_POST['userpassword']);

            // existing username

            $sql_existing = "SELECT * FROM user WHERE username='$name'";

            $result_exist = mysqli_query($connect, $sql_existing);

            $no_of_row = mysqli_num_rows($result_exist);

            if ($no_of_row > 0) {
                echo "Username is already existed! User different username!";
            }
            else {
                $sql = "INSERT INTO user (username, useremail, userpassword) VALUES 
                    (
                        '$name',
                        '$email',
                        '$password'
                    )";
        
                if (mysqli_query($connect, $sql)) {
                    echo "Registration completed!";
                }
                else {
                    echo "Registration error!";
                }
            }
        
            mysqli_close($connect);
    }
    else {
        echo "No POST request!";
    }

    

?>