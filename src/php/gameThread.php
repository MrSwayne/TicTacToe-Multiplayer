<?php
class GameThread extends Thread {
	protected $wsdl;
	protected $gid;
	protected $pid;

	protected $myTurn = false;

	public function __construct($wsdl, $gid, $pid) {
		echo "creating $pid";
		$this->$wsdl = $wsdl;
		$this->gid = $gid;
		$this->pid = $pid;
		$client = new SoapClient($wsdl, array('trace' => true, 'exceptions' => true));
		takeSquare(0,0);
		takeSquare(5,0);
		takeSquare(4,2);
		drawBoard();
		//init inital stuff;


	}

	public function run() {
		echo "running";
		$client = new SoapClient($wsdl, array('trace' => true, 'exceptions' => true));

		$params = array('gid' => $gid);
		$response = $client->getGameState($params);
		$resp = (int)$response->return;
	}

	function drawBoard() {
		$params = array('gid' => $gid);
		$response = $client->getBoard($params);
		$data = $response->return;

		switch($data) {
			case 'ERROR-NOMOVES': break;
			case 'ERROR-DB': header("location: index.php"); break;
			default:
			var_dump($data);
		}
	}

	function takeSquare($x, $y) {
		$params = array('x' => $x, 'y' => $y, 'gid' => $gid, 'pid' => $pid);
		$response = $client->takeSquare($params);
		$data = (int)$response->return;

		switch($data) {
			case 1: drawBoard();  break;
			default: 
		}
	}
}