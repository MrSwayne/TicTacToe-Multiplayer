/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ttt.james.server;

import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;

/**
 *
 * @author james.murphy
 */
@WebService(serviceName = "TTTWebService")
public class TTTWebService {
    
    private MySQLAccess dao; 
    
    public TTTWebService() {
        dao = new MySQLAccess("localhost", "root", "", "tttexample");
    }

    /**
     * Web service operation
    */
    /*@WebMethod(operationName = "insert")
    public String insert(@WebParam(name = "sql") String sql) {
        try {
            String whatup = dao.insert(sql);
            return whatup;
        } catch(Exception e) {
            return "Unable to enter data";
        }
    }*/

    /**
     * Web service operation
    */
    /*@WebMethod(operationName = "retrieve")
    public String retrieve(@WebParam(name = "sql") String sql) {
        try {
            String whatup = dao.retrieve(sql);
            return whatup;
        } catch(Exception e) {
            return "Unable to enter data";
        }
    }*/

    /**
     * Web service operation
    */
    /*@WebMethod(operationName = "remove")
    public String remove(@WebParam(name = "sql") String sql) {
        try {
            String whatup = dao.remove(sql);
            return whatup;
        } catch(Exception e) {
            return "Unable to enter data";
        }
    }   */

    /**
     * Web service operation
     * @param username
     * @param password
     * @return 
     */
    @WebMethod(operationName = "login")
    public int login(@WebParam(name = "username") String username, @WebParam(name = "password") String password) {
        try {
            String sqlCmd = "SELECT autokey FROM tttexample.users WHERE username = '" + username;
            sqlCmd += "' AND password = PASSWORD('" + password + "') AND isactive = 1;";
            String result = dao.retrieve(sqlCmd);
            if("ERROR".equals(result)) {
                return 0;
            } else {
                return Integer.parseInt(result);
            }
        } catch(Exception e) {
            System.out.println("Error is : " + e.getMessage());
            return -1;
        }
    }

    /**
     * Web service operation
     * @param username
     * @param password
     * @param name
     * @param surname
     * @return 
     */
    @WebMethod(operationName = "register")
    public String register(@WebParam(name = "username") String username, @WebParam(name = "password") String password, @WebParam(name = "name") String name, @WebParam(name = "surname") String surname) {
        String sqlCmd = "";
        
        sqlCmd = "SELECT COUNT(*) FROM tttexample.users WHERE username = '" + username + "';";
        try {
            String result = "";
            result = dao.retrieve(sqlCmd);
            if(result.equals("0")) {
                sqlCmd = "INSERT INTO users VALUES (default, '";
                sqlCmd += name + "', '" + surname + "', '" + username + "', PASSWORD('";
                sqlCmd += password + "'), 1, default);";
                System.out.println(sqlCmd);
                try {
                    result = dao.insert(sqlCmd);
                    if(!"INSERTED 1 ROWS.".equals(result)) {
                        return "ERROR-INSERT";
                    } else {
                        sqlCmd = "SELECT autokey FROM tttexample.users WHERE username = '" + username;
                        sqlCmd += "' AND password = PASSWORD('" + password + "') AND isactive = 1;";

                        try {
                            result = dao.retrieve(sqlCmd);
                            return result;
                        } catch(Exception g) {
                            return "ERROR-RETRIEVE";
                        }
                    }
                } catch(Exception e) {
                    return "ERROR-DB";
                }
            } else {
                return "ERROR-REPEAT";
            }
        } catch(Exception u) {
            return "ERROR-DB";
        }        
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "newGame")
    public String newGame(@WebParam(name = "uid") int uid) {
        String sqlCmd = "INSERT INTO games VALUES(default, " + uid + ", NULL, -1, default);";
        try {
            String result = dao.insert(sqlCmd);
            if(result.equals("INSERTED 1 ROWS.")) {
                sqlCmd = "SELECT autokey FROM games WHERE gamestate = -1 AND p1 = " + uid + " AND p2 IS NULL ORDER BY autokey DESC LIMIT 1;";
                try {
                    result = dao.retrieve(sqlCmd);
                    if(result.length() > 0) {
                        return result;
                    } else {
                        return "ERROR-NOTFOUND";
                    }
                } catch(Exception g) {
                    return "ERROR-RETRIEVE";
                }
            } else {
                return "ERROR-INSERT";
            }
        } catch(Exception e) {
            return "ERROR-DB";
        }
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "joinGame")
    public String joinGame(@WebParam(name = "uid") int uid, @WebParam(name = "gid") int gid) {
        String sqlCmd = "UPDATE games SET p2 = " + uid + ", gamestate = 0 WHERE autokey = " + gid + ";";
        try {
            String result = dao.update(sqlCmd);
            if(result.equals("UPDATED 1 ROWS.")) {
                return "1";
            } else {
                return "0";
            }
        } catch(Exception e) {
            return "ERROR-DB";
        }
    }
    
    @WebMethod(operationName = "getBoard")
    public String getBoard(@WebParam(name = "gid") int gid) {
        String sqlCmd = "SELECT pId, x, y FROM moves WHERE gId = " + gid + " ORDER BY played ASC;";
        try {
            String result = dao.retrieve(sqlCmd);
            if(result.length() > 0) {
                return result;
            } else {
                return "ERROR-NOMOVES";
            }
        } catch(Exception e) {
            return "ERROR-DB";
        }
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "getGameState")
    public String getGameState(@WebParam(name = "gid") int gid) {
        String sqlCmd = "SELECT gamestate FROM games WHERE autokey = " + gid + ";";
        try {
            String result = dao.retrieve(sqlCmd);
            if(result.length() > 0) {
                return result;
            } else {
                return "ERROR-NOGAME";
            }
        } catch(Exception e) {
            return "ERROR-DB";
        }
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "setGameState")
    public String setGameState(@WebParam(name = "gid") int gid, @WebParam(name = "gstate") int gstate) {
        String sqlCmd = "UPDATE games SET gamestate = " + gstate + " WHERE autokey = " + gid + ";";
        try {
            String result = dao.update(sqlCmd);
            if(result.equals("UPDATED 1 ROWS.")) {
                return "1";
            } else {
                return "ERROR-NOGAME";
            }
        } catch(Exception e) {
            return "ERROR-DB";
        }
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "checkSquare")
    public String checkSquare(@WebParam(name = "x") int x, @WebParam(name = "y") int y, @WebParam(name = "gid") int gid) {
        String sqlCmd = "SELECT COUNT(*) FROM moves WHERE x = " + x + " AND y = " + y + " AND ";
        sqlCmd += " gId = " + gid + ";";
        try {
            String result = dao.retrieve(sqlCmd);
            return result;
        } catch (Exception e) {
            return "ERROR-DB";
        }
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "takeSquare")
    public String takeSquare(@WebParam(name = "x") int x, @WebParam(name = "y") int y, @WebParam(name = "gid") int gid, @WebParam(name = "pid") int pid) {
        int count = Integer.parseInt(checkSquare(x, y, gid));
        String flag = "";
        switch(count) {
            case 1:
                flag = "ERROR-TAKEN";
            break;
            case 0:
                String sqlCmd = "INSERT INTO moves VALUES(default, " + pid + ", " + x + ", " + y;
                sqlCmd += ", default, " + gid + ");";
                try {
                    String result = dao.insert(sqlCmd);
                    if(result.equals("INSERTED 1 ROWS.")) {
                        flag = "1";
                    } else {
                        flag = "0";
                    }
                } catch(Exception e) {
                    flag = "ERROR-DB";
                }
            break;
            default: 
                flag = "ERROR";
        }
        return flag;
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "checkWin")
    public String checkWin(@WebParam(name = "gid") int gid) {
        String result = getBoard(gid);
        int winner = 0;
        if(result.length() > 0) {
            String[] rows = result.split("\n");
            int num_moves = rows.length;
            int[][] table = new int[3][3];
            for(int a=0;a<3;a++) {
                for(int b=0;b<3;b++) {
                    table[a][b] = 0;
                }
            }
            for(int i=0;i<num_moves;i++) {
                String[] cells = rows[i].split(",");
                int pid = Integer.parseInt(cells[0]);
                int x = Integer.parseInt(cells[1]);
                int y = Integer.parseInt(cells[2]);
                table[x][y] = pid;
            }
            
            //check rows and cols
            for(int t=0;t<3;t++) {
                if(table[t][0] == table[t][1] && table[t][1] == table[t][2] && table[t][0] != 0) {
                    winner = table[t][0];
                }
                if(table[0][t] == table[1][t] && table[1][t] == table[2][t] && table[0][t] != 0) {
                    winner = table[0][t];
                }
            }
            
            //Check diagonals
            if(table[0][0] == table[1][1] && table[1][1] == table[2][2] && table[0][0] != 0) {
                winner = table[0][0];
            }
            if(table[0][2] == table[1][1] && table[1][1] == table[2][0] && table[0][2] != 0) {
                winner = table[0][2];
            }
            
            if(winner > 0) {
                try {
                    // check is winner p1 or p2;
                    String sqlCmd = "SELECT * FROM games WHERE autokey = " + gid + ";";
                    String gDetails = dao.retrieve(sqlCmd);
                    if(gDetails.length() > 0) {
                        String[] details = gDetails.split(",");
                        if(details.length > 0) {
                            int p1 = Integer.parseInt(details[1]);
                            int p2 = Integer.parseInt(details[2]);
                            if(winner == p1) {
                                return "1";
                            } else {
                                return "2";
                            }
                        } else {
                            return "ERROR-RETRIEVE";
                        }
                    } else {
                        return "ERROR-RETRIEVE";
                    }
                } catch(Exception e) {
                    return "ERROR-DB";
                }
            } else {
                if(num_moves < 9) {
                    return "0";
                } else {
                    return "3";
                }
            }
        } else {
            return "ERROR-NOGAME";
        }
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "deleteGame")
    public String deleteGame(@WebParam(name = "gid") int gid, @WebParam(name = "uid") int uid) {
        String sqlCmd = "SELECT COUNT(*) FROM games WHERE autokey = " + gid + " AND p1 = ";
        sqlCmd += uid + " AND p2 IS NULL;";
        try {
            String result = dao.retrieve(sqlCmd);
            if(result.equals("1")) {
                sqlCmd = "DELETE FROM games WHERE autokey = " + gid + ";";
                result = dao.remove(sqlCmd);
                return "1";
            } else {
                return "ERROR-GAMESTARTED";
            }
        } catch(Exception e) {
            return "ERROR-DB";
        }
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "showMyOpenGames")
    public String showMyOpenGames(@WebParam(name = "uid") int uid) {
        String sqlCmd = "SELECT g.autokey, u.username, g.started FROM games g, users u WHERE g.p1 = " + uid + " AND g.p2 IS NULL AND g.p1 = u.autokey ORDER BY g.started ASC;";
        try {
            String result = dao.retrieve(sqlCmd);
            if(result.length() > 0) {
                return result;
            } else {
                return "ERROR-NOGAMES";
            }
        } catch(Exception e) {
            return "ERROR-DB";
        }
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "showAllMyGames")
    public String showAllMyGames(@WebParam(name = "uid") int uid) {
        String sqlCmd = "SELECT g.autokey, h.username, o.username, g.started FROM games g, users h, users o WHERE (g.p1 = " + uid + " OR g.p2 = " + uid + ") AND g.p1 = h.autokey AND g.p2 = o.autokey ORDER BY g.started ASC;";
        try {
            String result = dao.retrieve(sqlCmd);
            if(result.length() > 0) {
                return result;
            } else {
                return "ERROR-NOGAMES";
            }
        } catch(Exception e) {
            return "ERROR-DB";
        }
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "leagueTable")
    public String leagueTable() {
        String sqlCmd = "SELECT g.autokey, h.username, o.username, g.gamestate, g.started FROM games g, users h, users o WHERE g.p1 = h.autokey AND g.p2 = o.autokey ORDER BY g.started ASC;";
        try {
            String result = dao.retrieve(sqlCmd);
            if(result.length() > 0) {
                return result;
            } else {
                return "ERROR-NOGAMES";
            }
        } catch(Exception e) {
            return "ERROR-DB";
        }
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "showOpenGames")
    public String showOpenGames() {
        String sqlCmd = "SELECT g.autokey, h.username, g.started FROM games g, users h WHERE g.p2 IS NULL AND g.p1 = h.autokey ORDER BY g.started ASC;";
        try {
            String result = dao.retrieve(sqlCmd);
            if(result.length() > 0) {
                return result;
            } else {
                return "ERROR-NOGAMES";
            }
        } catch(Exception e) {
            return "ERROR-DB";
        }
    }
}
