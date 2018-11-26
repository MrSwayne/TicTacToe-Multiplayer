<?php session_start(); 
	if(!isset($_SESSION['wsdl']))
		header("location: index.php");

	require_once('gameThread.php');

	$wsdl = $_SESSION['wsdl'];
	$trace = $_SESSION['trace'];
	$exceptions = $_SESSION['exceptions'];
?>
<!doctype html>
<html lang="en-ie">

<head>
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>

	<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>

	<!-- Include one of jTable styles. -->
	<link href="/jtable/themes/metro/blue/jtable.min.css" rel="stylesheet" type="text/css" />
	 
	<!-- Include jTable script file. -->
	<script src="/jtable/jquery.jtable.min.js" type="text/javascript"></script>
	<title></title>
</head>

<body>

	<div id="container">
		<div id="content">

		<?php
			$wsdl = $_SESSION['wsdl'];
			$userid = $_SESSION['uname'];

			$client = new SoapClient($_SESSION['wsdl'], array('trace' => $_SESSION['trace'], 'exceptions' => $_SESSION['exceptions']));

			if(isset($_SESSION['gameid']) && isset($_SESSION['uname'])) {
				$player = new GameThread($wsdl, $_SESSION['gameid'], $userid);
				$player->run;

			}


				/*
				$params = array('gid' => $_SESSION['gameid']);
				$response = $client->getGameState($params);
				$gameState = $response->return;
				if($gameState == 0) {

					//takeSquare(0, 0);
					//$response = $client->takeSquare();

					$response = $client->getBoard($params);
					$data = $response->return;
					switch($data) {
						case  'ERROR-NOMOVES': break;
						case 'ERROR-DB': break;
						default: var_dump($data);
					}



					echo "<form action='game.php' method='POST'> <table>";
					for($i = 0;$i < 3;$i++) {
						echo "<tr>";
						for($j = 0;$j < 3;$j++) {
							echo "<td>$j</td>";
						} echo "</tr>";
					} echo "
					<
					</table></form>";

					?> 
					<?php

				} else if($gameState == 1) {
					$_SESSION['winner'] = "1";
				}
			} else {
				header("location: menu.php");
			}*/
		?>
		</div>
	</div>
</body>
</html>