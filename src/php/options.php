<?php session_start(); 
	$wsdl = $_SESSION['wsdl'];
	$trace = $_SESSION['trace'];
	$exceptions = $_SESSION['exceptions'];
?>
<!doctype html>
<html lang="en-ie">

<head>
	<title></title>
</head>

<body>

	<?php
		if(isset($_SESSION['uname'])) {

		} else
			header("location: index.php");
	?>

	<div id="container">
		<div id="content">
		</div>
	</div>
</body>
</html>