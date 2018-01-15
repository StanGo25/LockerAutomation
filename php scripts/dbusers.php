<html>
  <head><title>Insert Title Here</title></head>
  <body>
    <?php
	// Open Connection
$con = @mysqli_connect('sl-us-south-1-portal.17.dblayer.com', 'admin', 'OPPDatabase2017', 'compose', 32001);

if (!$con) {
    echo "Error: " . mysqli_connect_error();
	exit();
}
$uname = $_GET['user_name'];
$upin = $_GET['user_pin'];


// Some Query
$sql 	= "SELECT user_name FROM users where user_name='$uname' and user_pin='$upin'";
$query 	= mysqli_query($con, $sql);

while ($row = mysqli_fetch_array($query))
{
	$go = $row['user_name'];
	echo $go;

}

// Close connection
mysqli_close ($con);
?>
  </body>
</html>
