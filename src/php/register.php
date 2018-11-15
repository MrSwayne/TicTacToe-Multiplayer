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
	<div id="container">
		<div id="content">

			<?php 

			if(isset($_SESSION['wsdl'])) {

				if(isset($_POST['cancel']))
					header("location: index.php");

				if(isset($_POST['register']))
					if(isset($_POST['uname']) && isset($_POST['pword']) && isset($_POST['firstName']) && isset($_POST['secondName'])) {
						
						if($_POST['uname'] == "")
							echo "Error no username supplied.";
						else if($_POST['pword'] == "")
							echo "Error no password supplied.";
						else if($_POST['firstName'] == "")
							echo "Error no first name supplied.";
						else if($_POST['secondName'] == "")
							echo "Error no second name supplied.";
						else
							echo "done";
					}
				else {
					echo "incomplete stuff there bud";
				}
				?>

				<form action="register.php" method="POST">
				<table>
					<tr>
						<td>Username</td>
						<td><input type="text" name="uname" id="uname"  value="<?php if(isset($_POST['uname'])) echo $_POST['uname']; ?>" tabindex="1"</td>
					</tr>
					<tr>
						<td>Password</td>
						<td><input type="password" name="pword" id="pword" value="<?php if(isset($_POST['pword'])) echo $_POST['pword']; ?>"tabindex="2"></td>
					</tr>

					<tr>
						<td>First Name</td>
						<td><input type="text" name="firstName" id="firstName" value="<?php if(isset($_POST['firstName'])) echo $_POST['firstName']; ?>"
							tabindex="3"></td>
					<tr>
						<td>Second Name</td>
						<td><input type="text" name="secondName" id="secondName" value="<?php if(isset($_POST['secondName'])) echo $_POST['secondName']; ?>"
							tabindex="4"></td>
					</tr>
					<tr>

						<td colspan="1">
							<hr />
							<input type="submit" value="Cancel" name="cancel"/>
						</td>
						<td colspan="1">

							<hr />
							<input type="submit" value="Register" name="register" tabindex="6" /> 
						</td>
					</tr>
				</table>
			</form>

			<?php 

			} else {
				echo "Error: Could not connect to Webservice";
			}
			?>
		</div>
	</div>

</body>
</html>