<?php

    $host = "localhost";
    $username = "root";
    $password = "";

    $connect = mysqli_connect($host, $username, $password);

    $sql = "CREATE DATABASE v_fit_db";

    if (mysqli_query($connect, $sql)) {
        echo "Database Created!";
    }
    else {
        echo "Database Creation Error!";
    }

    mysqli_close($connect);

?>