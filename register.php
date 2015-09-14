<?php
	$servername = "mysqlv114";
	$username = "ruiyan";
	$password = "YanRui1656786";
	$database = "buddyfinder"
	$con = mysqli_connect($servername, $username, $password, $database);

	$name = $_post["ID"];
	$email = $_post["EMAIL"];
	$password = $_post["PASSWORD"];

	$statement = mysqli_prepare($con, "INSERT INTO User (name, email, password) VALUES ($name, $email, $password)");

	mysqli_close($con);

?>