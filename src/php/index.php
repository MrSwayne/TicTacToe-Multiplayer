<?php session_start(); 
	$_SESSION['wsdl'] = "http://localhost:8080/TTTWebApplication/TTTWebService?WSDL";
	$_SESSION['exceptions'] = true;
	$_SESSION['trace'] = true;
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
			if(isset($_POST["uname"]) && isset($_POST["login"])) {
				$user = $_POST["uname"];
				$pass = $_POST["pword"];

				$wsdl = $_SESSION['wsdl'];
				$trace = $_SESSION['trace'];
				$exceptions = $_SESSION['exceptions'];

				try {
					$client = new SoapClient($wsdl, array('trace' => $trace, 'exceptions' => $exceptions));

					$params = array('username' => $user, 'password' => $pass);
					$response = $client->login($params);
					$userid = (int) $response->return;
					
					echo $userid;
					/*
					switch($userid) {
						case -2:
						case -1:
							echo "<h4> ERRRRRRRRRRRRRRRRRRRRRRRRRRRROR </h4";
							break;
						case 0:
							echo "<h4> Invalid creds </h4>";
							break;
						}
							/*
						default:
							$xml_array = null;
							$xml_array["playerid"] = $userid;
							$response = $client->newGame($xml_array);
							$gameid = (int) $response->return;

							/*
							//Check states aight
							switch($gameid) {
								//CHECK BLAH BLAH BLAH

								case 5: ?><h4> HI</h4> <?php break;

								default:
								$_SESSION["uid"] = $userid;
								$_SESSION["gid"] = $gameid;

								?>
								<script>
									window.location("mainscreen.php");
								</script>
							}
							*/
					//}

				$_SESSION["wsdl"] = $wsdl;
				$_SESSION["trace"] = $trace;
				$_SESSION["exceptions"] = $exceptions;

				} catch(Exception $ex) {
					echo $ex->getMessage();
				}

			} else if(isset($_POST['register'])) {
				header("location: register.php");
			} else {
		?>

		<form action="index.php" method="POST">
			<table>
				<tr>
					<td>Username</td>
					<td><input type="text" name="uname" id="uname" tabindex="1"</td>
				</tr>
				<tr>
					<td>Password</td>
					<td><input type="password" name="pword" id="pword" tabindex="2"></td>
				</tr>
				<tr>

					<td colspan="1">
						<hr />
						<input type="submit" value="Register" name="register"/>
					</td>
					<td colspan="1">

						<hr />
						<input type="submit" value="Log in" name="login" tabindex="3" /> 
					</td>
				</tr>
			</table>
		</form>
	<?php
	}
	?>		
	</div>

	<div id="sidebar">

	</div>

	<div id="footbar">

	</div>
</div>

</body>
</html>