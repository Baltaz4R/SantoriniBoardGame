package etf.santorini.dv150189d;

import etf.santorini.dv150189d.Tile.BuildLevel;

public class Robot {
	public enum Stage {START, MOVE1, MOVE2, BUILD, END };
	Stage stage = Stage.START;
	
	Board board;
	
	byte whosTurn;
	int numOfMove;
	byte endOfGame;
	
	public Robot(Board board) {
		this.board = board;
		
		whosTurn = 1;
		numOfMove = 1;
		endOfGame = 0;
		
		stage = Stage.MOVE1;
	}
	
	private Robot(Robot p) {
		this.board = new Board(p.board);
		this.endOfGame = p.endOfGame;
		this.numOfMove = p.numOfMove;
		this.whosTurn = p.whosTurn;
		this.pawn = p.pawn;
		this.stage = p.stage;
	}
	
	/*public byte endOfGame() {
		if (numOfMove > 4) {
	        if (whosTurn == 1) {
	            if((!board.player2[0].canMove(board) && !board.player2[1].canMove(board)) || 
	            	(board.player1[0].getTile().getLevel() == BuildLevel.LEVEL3) || (board.player1[1].getTile().getLevel() == BuildLevel.LEVEL3)) {
	            	return 1;
	            } else {return 0;}
	        } else if (whosTurn == 2) {
	        	if((!board.player1[0].canMove(board) && !board.player1[1].canMove(board))  || 
	        		(board.player2[0].getTile().getLevel() == BuildLevel.LEVEL3)  || (board.player2[1].getTile().getLevel() == BuildLevel.LEVEL3)) {
	        		return 2;
	        	} else {return 0;}
	        } else {
	        	System.out.println("Nepostojeci igrac na potezu, pa nema pobednika!");
	        	return -1;
	        }
		}
		return 0;
    }*/
	
	private int pawn = -1;
	
	public boolean move(byte player, Tile t) {
        if (numOfMove <= 4) {
            switch (numOfMove) {
                case 1: case 2:
                    if (player == 1) {
            			board.setPlayer1(numOfMove-1, new Player(t));
            			t.player = board.player1[numOfMove-1];
                        numOfMove++;
                    } else {
                    	System.out.println("Pogresan igrac na potezu");
                    	return false;
                    }
                break;

                case 3: case 4:
                    if (player == 2) {
                        board.setPlayer2((numOfMove-1) % 2, new Player(t));
        				t.player = board.player2[(numOfMove-1) % 2];
                        numOfMove++;
                    } else {
                    	System.out.println("Pogresan igrac na potezu");
                    	return false;
                    }
                break;
            }
        } else if (numOfMove % 2 == player % 2) {

            switch(stage) {
                case MOVE1:
                	if (((player == 1) && (t.player == board.player1[0])) || 
                		((player == 2) && (t.player == board.player2[0]))) {
        				pawn = 0;
        				stage = Stage.MOVE2;
        			} else if (((player == 1) && (t.player == board.player1[1])) || 
                    		   ((player == 2) && (t.player == board.player2[1]))) {
        				pawn = 1;
        				stage = Stage.MOVE2;
        			} else {
        				pawn = -1;
                		return false;
        			}
                break;

                case MOVE2:
                	/*if (((player == 1) && (t.player == board.player1[0])) || 
                		((player == 2) && (t.player == board.player2[0]))) {
        				pawn = 0;
        				stage = Stage.MOVE2;
        			} else if (((player == 1) && (t.player == board.player1[1])) || 
                    		   ((player == 2) && (t.player == board.player2[1]))) {
        				pawn = 1;
        				stage = Stage.MOVE2;
        			} else*/ if ((player == 1) && board.move1(pawn, t) || (player == 2) && board.move2(pawn, t)) {
        				if((player == 1) && ((!board.player2[0].canMove(board) && !board.player2[1].canMove(board)) || 
        					(board.player1[pawn].getTile().getLevel() == BuildLevel.LEVEL3))) {
        					endOfGame = 1;
        					stage = Stage.END;
        				} else if ((player == 2) && (!board.player1[0].canMove(board) && !board.player1[1].canMove(board))  || 
        						(board.player2[pawn].getTile().getLevel() == BuildLevel.LEVEL3)) {
        					endOfGame = 2;
        					stage = Stage.END;
        				} else {
        					stage = Stage.BUILD;
        				}
        			} else {
        				return false;
        			}
                break;
                
                case BUILD:
                	if ((player == 1) && board.build1(pawn, t) || (player == 2) && board.build2(pawn, t)) {
                		if((player == 1) && (!board.player2[0].canMove(board) && !board.player2[1].canMove(board))) {
                			endOfGame = 1;
        					stage = Stage.END;
                		} else if((player == 2) && (!board.player1[0].canMove(board) && !board.player1[1].canMove(board))) {
                			endOfGame = 2;
        					stage = Stage.END;
                		} else {
	        				stage = Stage.MOVE1;
	        				pawn = -1;
	        				numOfMove++;
	        				whosTurn = (byte) (2 - (whosTurn+1)%2);
                		}
        			} else {
        				return false;
        			}
                break;
                
                case END:
                	System.out.println("Igra je vec zavrsena!");
                	return false;
                //break;
                default:
                	System.out.println("Upao u default!");
                	return false;
                //break;
            }
        } else {
        	System.out.println("Pogresan igrac na potezu");
        	return false;
        }
        return true;
    }
	
	private int evaluate (byte player, Tile tile, byte Pawn) {
		int myLevel = 0;
		int buildLevel = 0;
		int myDistance = 1;
		int Distance = 0;
		if (player == 1) {
			myLevel = board.player1[Pawn].getTile().getPosition().getZ();
			int x = board.player1[(Pawn + 1) % 2].getTile().getPosition().getX();
			int y = board.player1[(Pawn + 1) % 2].getTile().getPosition().getY();
			myDistance += (x>y ? x : y);
			x = board.player2[0].getTile().getPosition().getX();
			y = board.player2[0].getTile().getPosition().getY();
			Distance = (x>y ? x : y);
			x = board.player2[1].getTile().getPosition().getX();
			y = board.player2[1].getTile().getPosition().getY();
			Distance += (x>y ? x : y);
		} else if (player == 2) {
			myLevel = board.player2[Pawn].getTile().getPosition().getZ();
			int x = board.player2[(Pawn + 1) % 2].getTile().getPosition().getX();
			int y = board.player2[(Pawn + 1) % 2].getTile().getPosition().getY();
			myDistance += (x>y ? x : y);
			x = board.player1[0].getTile().getPosition().getX();
			y = board.player1[0].getTile().getPosition().getY();
			Distance = (x>y ? x : y);
			x = board.player1[1].getTile().getPosition().getX();
			y = board.player1[1].getTile().getPosition().getY();
			Distance += (x>y ? x : y);
		} else {
			System.out.println("Nepostojeci igrac je prosledjen!");
		}
		if(tile.getPosition() != null) {
			buildLevel = tile.getPosition().getZ() - 1;
		} else {
			buildLevel = 4 - 1;
		}
		int f = myLevel + (buildLevel*(myDistance-Distance));
		
	    return f;
	}

	
	public int minimax(byte player, int depth, Tile tile, byte Pawn) {

	    byte movelist[][] = { {-1,-1},{-1,0},{-1,1},{0,-1},{0,1},{1,-1},{1,0},{1,1} };
	    if(depth == 0) {
	    	return evaluate(player, tile, Pawn);
	    }
	    depth--;
        if (endOfGame == player){ //ovo mi mozda ne treba
            return 10000;
        } else if (endOfGame > 0){
            return -10000;
	    } else {
	    	int bestValue;
	    	byte p;
	    	if (whosTurn == player) { //a max node
	    		bestValue = -10000;
	    		p = player;
	    	} else { 
	    		bestValue = 10000;
	    		p = (byte) (2 - (player+1)%2);
	    	}
	        for(int pawn = 0; pawn <= 1; pawn++) { //odabir pijuna sa kojim igramo

		        Robot botgame = new Robot(this); //treba deep copy
                Tile t = null;
                if (whosTurn == 1) t = botgame.board.getPlayer1()[pawn].getTile();
                else if (whosTurn == 2) t = botgame.board.getPlayer2()[pawn].getTile();
                else System.out.println("Nepostojeci igrac na potezu!");
                int x0 = t.getPosition().getX();
                int y0 = t.getPosition().getY();
                botgame.move(p, t);
                
                for (int i=0; i<=7; i++) { //pomeramo pijuna
                	int x1 = x0;
                    int y1 = y0;
                	Robot botgame1 = new Robot(botgame);
                    x1 += movelist[i][0];
                    y1 += movelist[i][1];
                    t = botgame1.board.getTile(x1, y1);
                    if (botgame1.move(p, t)) {
                    	if((p == player) && (botgame1.endOfGame == player)) {
                    		return 10000;
                    	} else if((p == (2 - (player+1)%2)) && (botgame1.endOfGame == p)) {
                    		return -10000;
                    	} else if(botgame1.endOfGame != 0) {
                    		continue;
                    	}
                    } else {
                    	continue;
                    }
                        
                    for (int j=0; j<=7; j++) {
                    	int x2 = x1;
                        int y2 = y1;
                    	Robot botgame2 = new Robot(botgame1);
                        x2 += movelist[j][0];
                        y2 += movelist[j][1];
                        t = botgame2.board.getTile(x2, y2);
                        if (botgame2.move(p, t)) {
                        	if((p == player) && (botgame2.endOfGame == player)) {
                        		return 10000;
                        	} else if((p == (2 - (player+1)%2)) && (botgame2.endOfGame == p)) {
                        		return -10000;
                        	} else if(botgame2.endOfGame != 0) {
                        		continue;
                        	}
                        } else {
                        	continue;
                        }
                        
                        int value = botgame2.minimax(player, depth, t, (byte) pawn);
                        if (whosTurn == player) { //a max node
                        	bestValue = (value>bestValue ? value : bestValue);
                        } else {
                        	bestValue = (value<bestValue ? value : bestValue);
                        }
                    }  
                }
	        }
	        return bestValue;
	    }
	}
	
	public int[][] GenerateMove(byte player, int depth, MainWindow gui) {
		
		if (gui!=null)
			for (int i=0; i<5; i++) 
				for (int j=0; j<5; j++) 
					gui.getBoardRenderer().getTileRenderers(i, j).setText("");
		
	    byte movelist[][] = { {-1,-1},{-1,0},{-1,1},{0,-1},{0,1},{1,-1},{1,0},{1,1} };
	    int value = -100000;
	    int[][] move = new int[3][2];
	    
	    if (whosTurn == player) {
	        for(int pawn=0; pawn<=1; pawn++) {
	        	Robot botgame = new Robot(this);
	        	Tile t0 = null;
                if (whosTurn == 1) t0 = botgame.board.getPlayer1()[pawn].getTile();
                else if (whosTurn == 2) t0 = botgame.board.getPlayer2()[pawn].getTile();
                else System.out.println("Nepostojeci igrac na potezu!");
                int x0 = t0.getPosition().getX();
                int y0 = t0.getPosition().getY();
                botgame.move(player, t0);
                for (int i=0; i<=7; i++) {
                	int x1 = x0;
                    int y1 = y0;
                	Robot botgame1 = new Robot(botgame);
                    x1 += movelist[i][0];
                    y1 += movelist[i][1];
                    Tile t1 = botgame1.board.getTile(x1, y1);
                    if (botgame1.move(player, t1)) {
                    	if(botgame1.endOfGame == player) {
                    		move[0][0] = x0;
                    		move[0][1] = y0;
                    		move[1][0] = x1;
                    		move[1][1] = y1;
                    		return move;
                    	} else if(botgame1.endOfGame != 0) {
                    		continue;
                    	}
                    } else {
                    	continue;
                    }
                    for (int j=0; j<=7; j++){
                    	int x2 = x1;
                        int y2 = y1;
                    	Robot botgame2 = new Robot(botgame1);
                        x2 += movelist[j][0];
                        y2 += movelist[j][1];
                        Tile t2 = botgame2.board.getTile(x2, y2);
                        if (botgame2.move(player, t2)) {
                        	if(botgame2.endOfGame == player) {
                        		move[0][0] = x0;
                        		move[0][1] = y0;
                        		move[1][0] = x1;
                        		move[1][1] = y1;
                        		move[2][0] = x2;
                        		move[2][1] = y2;
                        		return move;
                        	} else if(botgame2.endOfGame != 0) {
                        		continue;
                        	}
                        } else {
                        	continue;
                        }
                    	
                        int v = botgame2.minimax(player, depth, null, (byte) -1);
                        if (gui!=null) gui.getBoardRenderer().getTileRenderers(x1, y1).setText(gui.getBoardRenderer().getTileRenderers(x1, y1).getText() + " / " +Integer.toString(v));
                        if (v>value){
                            value = v;
                            move[0][0] = x0;
                    		move[0][1] = y0;
                    		move[1][0] = x1;
                    		move[1][1] = y1;
                    		move[2][0] = x2;
                    		move[2][1] = y2;
                        }
                    }
                }            
	        }
	    }
        return move;
	}
	
	public int minimaxAB(byte player, int depth, Tile tile, byte Pawn, int min, int max) {

	    byte movelist[][] = { {-1,-1},{-1,0},{-1,1},{0,-1},{0,1},{1,-1},{1,0},{1,1} };
	    if(depth == 0) {
	    	return evaluate(player, tile, Pawn);
	    }
	    depth--;
        if (endOfGame == player){ //ovo mi mozda ne treba
            return 10000;
        } else if (endOfGame > 0){
            return -10000;
	    } else {
	    	int bestValue;
	    	byte p;
	    	if (whosTurn == player) { //a max node
	    		bestValue = min;
	    		p = player;
	    	} else { 
	    		bestValue = max;
	    		p = (byte) (2 - (player+1)%2);
	    	}
	        for(int pawn = 0; pawn <= 1; pawn++) { //odabir pijuna sa kojim igramo

		        Robot botgame = new Robot(this); //treba deep copy
                Tile t = null;
                if (whosTurn == 1) t = botgame.board.getPlayer1()[pawn].getTile();
                else if (whosTurn == 2) t = botgame.board.getPlayer2()[pawn].getTile();
                else System.out.println("Nepostojeci igrac na potezu!");
                int x0 = t.getPosition().getX();
                int y0 = t.getPosition().getY();
                botgame.move(p, t);
                
                for (int i=0; i<=7; i++) { //pomeramo pijuna
                	int x1 = x0;
                    int y1 = y0;
                	Robot botgame1 = new Robot(botgame);
                    x1 += movelist[i][0];
                    y1 += movelist[i][1];
                    t = botgame1.board.getTile(x1, y1);
                    if (botgame1.move(p, t)) {
                    	if((p == player) && (botgame1.endOfGame == player)) {
                    		return 10000;
                    	} else if((p == (2 - (player+1)%2)) && (botgame1.endOfGame == p)) {
                    		return -10000;
                    	} else if(botgame1.endOfGame != 0) {
                    		continue;
                    	}
                    } else {
                    	continue;
                    }
                        
                    for (int j=0; j<=7; j++) {
                    	int x2 = x1;
                        int y2 = y1;
                    	Robot botgame2 = new Robot(botgame1);
                        x2 += movelist[j][0];
                        y2 += movelist[j][1];
                        t = botgame2.board.getTile(x2, y2);
                        if (botgame2.move(p, t)) {
                        	if((p == player) && (botgame2.endOfGame == player)) {
                        		return 10000;
                        	} else if((p == (2 - (player+1)%2)) && (botgame2.endOfGame == p)) {
                        		return -10000;
                        	} else if(botgame2.endOfGame != 0) {
                        		continue;
                        	}
                        } else {
                        	continue;
                        }
                        
                        if (whosTurn == player) { //a max node
                        	int value = botgame2.minimaxAB(player, depth, t, (byte) pawn, bestValue, max);
                        	bestValue = (value>bestValue ? value : bestValue);
                        	if (bestValue>max) {return max;}
                        } else {
                        	int value = botgame2.minimaxAB(player, depth, t, (byte) pawn, min, bestValue);
                        	bestValue = (value<bestValue ? value : bestValue);
                        	if (bestValue<min){return min;}
                        }
                    }  
                }
	        }
	        return bestValue;
	    }
	}
	
	public int[][] GenerateMoveAB(byte player, int depth, MainWindow gui) {
		
		if (gui!=null)
			for (int i=0; i<5; i++) 
				for (int j=0; j<5; j++) 
					gui.getBoardRenderer().getTileRenderers(i, j).setText("");
		
	    byte movelist[][] = { {-1,-1},{-1,0},{-1,1},{0,-1},{0,1},{1,-1},{1,0},{1,1} };
	    int value = -100000;
	    int[][] move = new int[3][2];
	    
	    if (whosTurn == player) {
	        for(int pawn=0; pawn<=1; pawn++) {
	        	Robot botgame = new Robot(this);
	        	Tile t0 = null;
                if (whosTurn == 1) t0 = botgame.board.getPlayer1()[pawn].getTile();
                else if (whosTurn == 2) t0 = botgame.board.getPlayer2()[pawn].getTile();
                else System.out.println("Nepostojeci igrac na potezu!");
                int x0 = t0.getPosition().getX();
                int y0 = t0.getPosition().getY();
                botgame.move(player, t0);
                for (int i=0; i<=7; i++) {
                	int x1 = x0;
                    int y1 = y0;
                	Robot botgame1 = new Robot(botgame);
                    x1 += movelist[i][0];
                    y1 += movelist[i][1];
                    Tile t1 = botgame1.board.getTile(x1, y1);
                    if (botgame1.move(player, t1)) {
                    	if(botgame1.endOfGame == player) {
                    		move[0][0] = x0;
                    		move[0][1] = y0;
                    		move[1][0] = x1;
                    		move[1][1] = y1;
                    		return move;
                    	} else if(botgame1.endOfGame != 0) {
                    		continue;
                    	}
                    } else {
                    	continue;
                    }
                    for (int j=0; j<=7; j++){
                    	int x2 = x1;
                        int y2 = y1;
                    	Robot botgame2 = new Robot(botgame1);
                        x2 += movelist[j][0];
                        y2 += movelist[j][1];
                        Tile t2 = botgame2.board.getTile(x2, y2);
                        if (botgame2.move(player, t2)) {
                        	if(botgame2.endOfGame == player) {
                        		move[0][0] = x0;
                        		move[0][1] = y0;
                        		move[1][0] = x1;
                        		move[1][1] = y1;
                        		move[2][0] = x2;
                        		move[2][1] = y2;
                        		return move;
                        	} else if(botgame2.endOfGame != 0) {
                        		continue;
                        	}
                        } else {
                        	continue;
                        }
                    	
                        int v = botgame2.minimaxAB(player, depth, null, (byte) -1, -100000, 100000);
                        if (gui!=null) gui.getBoardRenderer().getTileRenderers(x1, y1).setText(gui.getBoardRenderer().getTileRenderers(x1, y1).getText() + " / " +Integer.toString(v));
                        if (v>value){
                            value = v;
                            move[0][0] = x0;
                    		move[0][1] = y0;
                    		move[1][0] = x1;
                    		move[1][1] = y1;
                    		move[2][0] = x2;
                    		move[2][1] = y2;
                        }
                    }
                }            
	        }
	    }
        return move;
	}
	
}
