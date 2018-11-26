<?php session_start(); 
	if(!(isset($_SESSION['wsdl']) && isset($_SESSION['gameid'])))
		header("location: index.php");

	require_once('gameThread.php');

	$wsdl = $_SESSION['wsdl'];
	$trace = $_SESSION['trace'];
	$exceptions = $_SESSION['exceptions'];
?>
<!doctype html>
<html lang="en-ie">

<head>

	<link rel="stylesheet" href="css/style.css">
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


		<table id="table"></table>
		<?php
			$wsdl = $_SESSION['wsdl'];
			$userid = $_SESSION['uname'];
			$gameOver = false;

			$client = new SoapClient($_SESSION['wsdl'], array('trace' => $_SESSION['trace'], 'exceptions' => $_SESSION['exceptions']));
			$changed = true;
			if(isset($_POST["submit"])) {
				if(isset($_POST['col']) && isset($_POST['row'])) {
					$params = array('x' => $_POST['col'], 'y' => $_POST['row'], 'gid' => $_SESSION['gameid']);
					$response = $client->checkSquare($params);
					switch($response->return) {
						case 1: 
						$params['pid'] = $userid; 

						$client->takeSquare($params)->return;
						$response = $client->checkWin($params);

						switch($response->return) {
							case 1: echo "Player 1 has won";
							case 2: echo "Player 2 has won";
							case 3: echo "DRAW!";
							default: echo "ERROR DB/NO GAME";
						}
						break;
						case 0:
							echo "<h3>Error, That tile has been taken.</h3>";
							break;
						default: echo "<h1>FAILURE CONNECTING TO DBMS</h1>";
					}
				}
				else {
					echo "<h2>Invalid column and row supplied.</h2>";
				}
				
			}

			function isMyTurn($data) {
					$data = explode("\n", $data);
					$line = $data[count($data) - 1];
					$line = explode(",", $line);
					if(!$line[0] == $_SESSION['uname']) 
						return true;
					return false;
			}

			function drawBoard($data) {
				?><script>$("#table").remove() ;</script><?php
				$lines = explode(",", $data);
				echo "<table id='table'>";
				$arr;
				for($i = 0;$i < 3;$i++) {
					for($j = 0; $j < 3;$j++) {
						$arr[$i][$j] = "NA";
					}
				}
				foreach($lines as $line) {
					if($data != "")  {
					$x = $line[1];
					$y = $line[2];
					if($line[0] == $_SESSION['uname'])
						$arr[$x][$y] = "x";
					else
						$arr[$x][$y] = "o";
				}
				}

				for($i = 0;$i < count($arr) - 1;$i++) {
					for($j = 0;$j < count($arr) - 1;$j++) {
						echo "<td> " . $arr[$i][$j] . "</td>";
					}
					echo "</tr>";

				}
				echo "</table>";
			}

			function showControls() {
				?>
				<form action="game.php" method="POST">
					<table>
						<tr>
							<td><input type="text" placeholder="Col" id="col" name="col"></td>
							<td><input type="text" placeholder="Row" id="row" name="row"></td>
							<td><input type="submit" value="submit" name="submit" id="submit"></td>
						</tr>
					</table>
					<?php
			}
			if((isset($_SESSION['gameid']) && isset($_SESSION['username']) ) && isset($_SESSION['uname'])) {
				drawBoard("");
				showControls();
				$params = array('gid' => $_SESSION['gameid']);
				while(!gameOver){

					$response = $client->getBoard($params);

					$data = $response->return;

					if(!isMyTurn()) {
						drawBoard($data);
					} else {
						showControls();
					}
				}

			}
	
		?>

		<table id="gameTable">
			<tr>
			</tr>
		</table>

		</div>
	</div>
</body>
</html>