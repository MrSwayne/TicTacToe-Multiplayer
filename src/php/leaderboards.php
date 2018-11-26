<?php session_start();
	
	if(!(isset($_SESSION['wsdl'])))
		header("location: index.php");
?>
<!doctype html>
<html lang="en-ie">

<head>
	<title></title>
</head>

<body>
	<div id="container">
		<div id="content">
			<?php
			if(isset($_POST['leaderboard'])) {
				$client = new SoapClient($_SESSION['wsdl'], array('trace' => $_SESSION['trace'], 'exceptions' => $_SESSION['exceptions']));

				$response = $client->leagueTable();
				$data = $response->return;

				switch($data) {
					case "ERROR-NOGAMES":
						echo "No Games have been played yet.";
						break;
					case "ERROR-DB":
						echo "Cannot conenct to db.";
						break;
					default:
						var_dump($data);
				}
			}

				?>
		</div>

		<div id="footer">
			<form action="menu.php" method="POST">
				<table>
					<tr>
						<td><input type="submit" value="Back" id="back" name="back"></td>
					</tr>
				</table>
		</div>

	</div>
</body>