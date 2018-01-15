
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

// Some Query
$sql 	= 'SELECT * FROM products';
$query 	= mysqli_query($con, $sql);

$rows = array();
while($r = mysqli_fetch_assoc($query))
{
	$rows[] = $r;
}
print json_encode($rows);

// Close connection
mysqli_close ($con);
?>
  </body>
</html>
