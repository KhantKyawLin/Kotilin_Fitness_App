<?php

    include 'dbconnection.php';

    // $sql = "CREATE TABLE user 
    //         (
    //             userID int AUTO_INCREMENT primary key not null,
    //             username varchar(40) not null,
    //             useremail varchar(40) not null,
    //             userpassword text not null
    //         )";

    $sql = "CREATE TABLE workout 
            (
                workoutID int AUTO_INCREMENT primary key not null,
                workoutType varchar(40) not null,
                workoutDate text not null,
                workoutDay int not null,
                workoutMonth int not null,
                workoutYear int not null,
                workoutTime text null,
                indoor_outdoor text null,
                equipments text null,
                distance float null,
                duration int null,
                workoutWeight float null,
                remark text null,
                userID int not null,
                foreign key (userID) references user (userID) on delete cascade
            )";

    if (mysqli_query($connect, $sql)) {
        echo "Workout table is created!";
    }
    else {
        echo "Table creation error!";
    }

    mysqli_close($connect);
?>