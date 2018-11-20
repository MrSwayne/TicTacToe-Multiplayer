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

				$userid = $_SESSION['uname'];

				$client = new SoapClient($_SESSION['wsdl'], array('trace' => $_SESSION['trace'], 'exceptions' => $_SESSION['exceptions']));

			function newGame($userid, $client) {
				$params = null;
				$params['uid'] = $userid;
				$response = $client->newGame($params);
				$gameid = (int) $response->return;
				
				switch($gameid) {

				}
			}

			function joinGame($userid, $client) {
				?>

				<form action="game.php" method="POST">
				<table>
					<tr>
						<td>GameID</td>
						<td><input type="text" name="gameid" id="gameid" tabindex="1"> </td>
					</tr>
					<tr>
						<td><input type="submit" name="join" value="Join" id="join" tabindex="2"></td>
					</tr>


				<?php
			}

			if(isset($_POST['newGame'])) {
				newGame($userid, $client);

			} else if(isset($_POST['joinGame'])) {
				joinGame($userid, $client);
			}

			else {
			?>

			<h4>Welcome to TicTacToe!</h4>
			<form action="game.php" method="POST">
			<table>
				<tr>
					<td><input type="submit" value="New Game" name="newGame" id="newGame" tabindex="1"></td>
				</tr>
				<tr>
					<td><input type="submit" value="Join Game "name="joinGame" id="joinGame" tabindex="2"></td>
				</tr>
			</table>
			</form>

			<form action="options.php" method="POST">
				<table>
					<tr>
						<td><input type="submit" value="Options" name="options" id="options" tabindex="3"></td>
					</tr>
				</table>
			</form>

			<?php

			 } ?>

		</div>
	</div>

</body>
</html>