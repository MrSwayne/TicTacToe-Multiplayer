<?php session_start(); 
	$wsdl = $_SESSION['wsdl'];
	$trace = $_SESSION['trace'];
	$exceptions = $_SESSION['exceptions'];
?>
<!doctype html>
<html lang="en-ie">

<head>
	<link rel="stylesheet" href="css/style.css">
	<title></title>
</head>

<body>

	<?php
		if(isset($_SESSION['wsdl'])) {
			$client = new SoapClient($_SESSION['wsdl'], array('trace' => $_SESSION['trace'], 'exceptions' => $_SESSION['exceptions']));

			$response = $client->showOpenGames();
			$data = $response->return;
			$data = explode("\n", $data);

			echo "<table> <tr>
				<td>GameID</td>
				<td>	Created by</td>
				<td>	Date </td></tr";

				echo "<tr></tr>";
			foreach($data as $datum) {
				echo "<tr>";
				$line = explode(",", $datum);
				for($i = 0;$i < count($line);$i++) {
					echo "<td>" . $line[$i] . "<td>";
				}
				echo "</tr>";
			} echo "</table";
		} else
			header("location: index.php");
	?>

	<div id="container">
		<div id="content">
		</div>
	</div>
</body>
</html>