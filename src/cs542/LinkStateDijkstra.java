package cs542;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class LinkStateDijkstra {
	
	public static void main(String args[]) throws Exception 
	{
		
		System.out.println("CS 542 Link State Routing Simulator, Choose an option:");

		int[][] cost = new int[1][1];
		int[][] correctCost = new int[1][1];
		int rcount=0;
		int sourceNumber = 0;
		NodeVisited[] nodeVisitedList = new NodeVisited[rcount+1];
		DistanceNode[][] finalDistanceNodeNumbers = new DistanceNode[rcount+1][rcount+1];
		Map<Integer, ShortestDistancePathNode> finalMap = new HashMap<Integer, ShortestDistancePathNode>();
		
		while(true){
			
			//Options input
			System.out.print("\n");
			System.out.println("1. Input Network Topology File");
			System.out.println("2. Build a connection table");
			System.out.println("3. Shortest path to the destination router");
			System.out.println("4. Exit");
			
		System.out.println("\nEnter the menu option");
		BufferedReader option = new BufferedReader(new InputStreamReader(System.in));
		String optionStr = "";
		int optionInt = 5;
		try 
		 { 
			optionStr = option.readLine();
			optionInt = Integer.parseInt(optionStr);
		 } 
		 catch (IOException e) 
		 { 
			 System.out.println("\nError: choose a correct menu option"); 
			 continue;
		 } 
		 catch(NumberFormatException e){
			 System.out.println("\nError: choose a correct menu option"); 
			 continue;
		 }
		
		switch (optionInt) { //Take actions based on the option input
		case 1:
			
			 System.out.println("\nEnter the file path");
			 BufferedReader b = new BufferedReader(new InputStreamReader(System.in)); 
			 String filename="";
			 
			 try 
			 { 
			 filename=b.readLine(); 
			 } 
			 catch (IOException e) 
			 { 
			 System.out.println("\n IOError"); 
			 } 
			 
			 try{
			 
	         FileInputStream fstream = new FileInputStream(filename); 
	         DataInputStream in = new DataInputStream(fstream); 
	         BufferedReader br = new BufferedReader(new InputStreamReader(in)); 
	         String strLine;
	         

	         while ((strLine = br.readLine()) != null) 
	         { 
	         rcount++; 
	         }
	         in.close(); 
	         cost=new int[rcount+1][rcount+1];
	         correctCost = new int[rcount+1][rcount+1];
	         FileInputStream fstream1 = new FileInputStream(filename); 
	         DataInputStream in1 = new DataInputStream(fstream1); 
	         BufferedReader br1 = new BufferedReader(new InputStreamReader(in1));
	         int j=0; 
	         int k=0; 
	         for(k=0;k<=rcount;k++){
	        	 cost[j][k] = k;
	         }
	         while ((strLine = br1.readLine()) != null) 
	         { 
	         j++; 
	         k=0;
	         String[] sarray= new String[rcount]; 
	         sarray=strLine.split(" ");
	         cost[j][k] = j;
	         for(k=1;k<=rcount;k++) 
	         { 
	        	 cost[j][k]=Integer.parseInt(sarray[k-1]); 
	         } 
	         }
	         in1.close(); 
	         
	         
	         for(j=0;j<=rcount;j++) 
	         { 
	         for(k=0;k<=rcount;k++) 
	         { 
	        	 if(cost[j][k] == -1){
	        		 correctCost[j][k] = Integer.MAX_VALUE;
	        	 } 
	        	 else{
	        		 correctCost[j][k] = cost[j][k];
	        	 }
	         }  
	         }
	         
	        
	         
	         System.out.println("\nReview original topology matrix:"); 
	         for(j=1;j<=rcount;j++) 
	         { 
	         for(k=1;k<=rcount;k++) 
	         { 
	        	 
	         System.out.print(cost[j][k] + " "); 
	         } 
	         System.out.println(); 
	         } 
	         
	         //debug statements start
	         /*System.out.println("Correct Input Table is"); 
	         for(j=1;j<=rcount;j++) 
	         { 
	         for(k=1;k<=rcount;k++) 
	         { 
	         System.out.print(correctCost[j][k] + " "); 
	         } 
	         System.out.println(); 
	         } 
	         */
	         //debug statements end
	         
			 }
			 catch(FileNotFoundException e){
				 System.out.println("\nFile not found! Please specify the path properly");
				 continue;
			 }
			 catch(IndexOutOfBoundsException e){
				 System.out.println("\nTopology Matrix in the file is not N x N. Please check the content of the file again");
				 continue;
			 }
			
			break;
		case 2:
			
			if(rcount == 0){
				System.out.println("\nTopology Matrix is not initialized, please select option 1 prior to other options to enter the same");
				continue;
			}
			
			System.out.println("\nEnter the source node number");
			BufferedReader sourceOption = new BufferedReader(new InputStreamReader(System.in));
			String sourceOptionStr = "";
			int sourceOptionInt = 0;
			try 
			 { 
				sourceOptionStr = sourceOption.readLine();
				sourceOptionInt = Integer.parseInt(sourceOptionStr);
				
				if(sourceOptionInt <= 0){
					System.out.println("\nNode does not exist, enter correct number present in the topology"); 
					 continue;
				}
				if(sourceOptionInt > rcount){
					System.out.println("\nNode does not exist, enter correct number present in the topology"); 
					 continue;
				}
				
			 } 
			 catch (IOException e) 
			 { 
				 System.out.println("\nError: choose a correct node number"); 
				 continue;
			 } 
			 catch(NumberFormatException e){
				 System.out.println("\nError: choose a correct node number"); 
				 continue;
			 }
			 
			 sourceNumber = sourceOptionInt;
			 
	         //initialise the array of NodeVisited objects.
	         nodeVisitedList = new NodeVisited[rcount+1];
	         for(int i =1; i<= rcount; i++){
	        	 nodeVisitedList[i] = new NodeVisited();
	        	 nodeVisitedList[i].setVisited(false);
	        	 nodeVisitedList[i].setNodeNumber(i);
	         }
	         
	         //initialise source NodeVisited Object
	         nodeVisitedList[sourceNumber].setVisited(true);
	         
	         //final distanceNode Array
	         finalDistanceNodeNumbers = new DistanceNode[rcount+1][rcount+1];
	         
	         for(int j=0;j<=rcount;j++) 
	         { 
	         for(int k=0;k<=rcount;k++) 
	         { 
	        	 finalDistanceNodeNumbers[j][k] = new DistanceNode(); 
	         }  
	         }
	         
	         int minDistance = 0;
	         int nextSourcenumber = sourceNumber;
	         
	         int n = 0;
	         int m = 0;
	         for(m = 0; m<=rcount; m++){  //initialization
	        	 
	    			 if(m == nextSourcenumber){
	    				 finalDistanceNodeNumbers[n][m].setDistance(0);
	    				 finalDistanceNodeNumbers[n][m].setNodeNumber(m);
	    			 }
	    			 else if(n ==0 && m ==0){
	    				 finalDistanceNodeNumbers[n][m].setDistance(0);
	    				 finalDistanceNodeNumbers[n][m].setNodeNumber(0);
	    			 }
	    			 else{
	    				 finalDistanceNodeNumbers[n][m].setDistance(Integer.MAX_VALUE);
	    				 finalDistanceNodeNumbers[n][m].setNodeNumber(-1);
	    			 }
	    		 
	         }
	         
	         //finding the minimum cost path and the nodes iteratively and populating the minimum path table using Dijkstras algorithm 
	         for(n = 1; n<=rcount; n++){
	        	 m = 0;
	        	 finalDistanceNodeNumbers[n][m].setDistance(minDistance);
	        	 finalDistanceNodeNumbers[n][m].setNodeNumber(nextSourcenumber);
	        	 nodeVisitedList[nextSourcenumber].setVisited(true);
	        	 
	        	 for(m = 1; m<=rcount; m++){
	        		 
	        		 {
	        			 int minDistanceToBeAdded = minDistance;
	        			 if(nodeVisitedList[m].isVisited() || m == nextSourcenumber){  //copy the above value
	        				 finalDistanceNodeNumbers[n][m].setDistance(finalDistanceNodeNumbers[n-1][m].getDistance());
	        				 finalDistanceNodeNumbers[n][m].setNodeNumber(finalDistanceNodeNumbers[n-1][m].getNodeNumber());
	        				 continue;
	        			 }
	        				 
	        			 int currentDistance = finalDistanceNodeNumbers[n-1][m].getDistance();
	        			 
	        			 if(correctCost[nextSourcenumber][m] != Integer.MAX_VALUE && minDistanceToBeAdded != Integer.MAX_VALUE)
	        			 {
	        				int newDistance = correctCost[nextSourcenumber][m] + minDistanceToBeAdded;
	        				if(newDistance <  currentDistance){
	        					finalDistanceNodeNumbers[n][m].setDistance(newDistance);
	        					finalDistanceNodeNumbers[n][m].setNodeNumber(nextSourcenumber);
	        				}
	        				else{
	        					finalDistanceNodeNumbers[n][m].setDistance(finalDistanceNodeNumbers[n-1][m].getDistance());
	           				 	finalDistanceNodeNumbers[n][m].setNodeNumber(finalDistanceNodeNumbers[n-1][m].getNodeNumber());
	        				}
	        			 }
	        			 else{
	        				 finalDistanceNodeNumbers[n][m].setDistance(finalDistanceNodeNumbers[n-1][m].getDistance());
	        				 finalDistanceNodeNumbers[n][m].setNodeNumber(finalDistanceNodeNumbers[n-1][m].getNodeNumber());
	        			 }
	        		 }
	        		 
	        	 }
	        	 
	        	 //find out the next source number and the minDistance to continue filling the shortest path table using dijkstra's algorithm
	        	 Integer lineMinimumDistance = null;
	        	 Integer lineNextSourceNodeNumber = null;
	        	 
	        	 for(m = 1; m<=rcount; m++){
	        		 
	        		 if(!(nodeVisitedList[m].isVisited())){
	        			 if(lineMinimumDistance == null){
	        				 lineMinimumDistance = finalDistanceNodeNumbers[n][m].getDistance();
	            			 lineNextSourceNodeNumber = m;
	        			 }
	        			 else{
	        				 if(finalDistanceNodeNumbers[n][m].getDistance() < lineMinimumDistance){
	                			 lineMinimumDistance = finalDistanceNodeNumbers[n][m].getDistance();
	                			 lineNextSourceNodeNumber = m;
	                		 }  
	        			 }
	        		 }       		   
	        		 
	        	 }
	        	 
	        	 if(lineMinimumDistance != null) {
	        		 minDistance = lineMinimumDistance;
	            	 nextSourcenumber = lineNextSourceNodeNumber;  
	        	 }
	        	 else{
	        		 break;
	        	 }
	        	       	 
	        	 
	         }
	         
	         //debug statements start
	         System.out.println("Final Table is"); 
	         for(int j=0;j<=rcount;j++) 
	         { 
	         for(int k=0;k<=rcount;k++) 
	         { 
	         System.out.print(finalDistanceNodeNumbers[j][k].getDistance() + "," + finalDistanceNodeNumbers[j][k].getNodeNumber() + "  "); 
	         } 
	         System.out.println(); 
	         }
	        //debug statements end
	         
	         
	         finalMap = new HashMap<Integer, ShortestDistancePathNode>();
	         //store the distances in the arraylist
	         m = 0;
	         for(n =1;n<=rcount; n++){
	        	 
	        	 int nodeNumber = finalDistanceNodeNumbers[n][m].getNodeNumber();
	        	 int distance = finalDistanceNodeNumbers[n][m].getDistance();
	        	 
	        	 ShortestDistancePathNode node = new ShortestDistancePathNode();
	        	 node.setDistance(distance);
	        	 node.setNodeNmber(nodeNumber);
	        	 
	        	 finalMap.put(nodeNumber, node);
	         }
	         
	         Iterator<Integer> itr = finalMap.keySet().iterator();
	         while(itr.hasNext()){
	        	 Integer nodeNumber = itr.next();
	        	 ShortestDistancePathNode node = finalMap.get(nodeNumber);
	        	 int currentNodenumber = 0;
	        	 String strRoute = nodeNumber + " - ";
	        	 int innerCount = 0;
	        	 while(true){
	        		 if(innerCount == rcount){
	        			 break;
	        		 }
	        		 if(nodeNumber == -1){
	        			 strRoute += " is not reachable";
	        			 break;
	        		 }
	        		 currentNodenumber = finalDistanceNodeNumbers[rcount][nodeNumber].getNodeNumber();
	        		 if(currentNodenumber == -1){
	        			 strRoute += " is not reachable";
	        			 break;
	        		 }
	        		 if(currentNodenumber == sourceNumber){
	        			 strRoute += currentNodenumber;
	        			 break;
	        		 }
	        		 else{
	            		 strRoute += currentNodenumber + " - ";       
	            		 nodeNumber = currentNodenumber;
	        		 }
	        		 innerCount++;
	        	 }
	        	 if(strRoute != null){
	        		 if(!(strRoute.contains("reachable"))){
	        			 strRoute = reverseNL(strRoute);
	        		 }	        		 
	        	 }
	        	 
	        	 node.setShortestRoute(strRoute);
	         }
	         
	         //print the connection table
	         Iterator<Integer> itr3 = finalMap.keySet().iterator();
        	 System.out.println("\nConnection table for node " + sourceOptionStr);
        	 System.out.println("Destination   Interface");
	         while(itr3.hasNext()){
	        	 Integer nodeNumber = itr3.next();
	        	 String sourceNodeSubString = sourceOptionStr + " - ";
	        	 ShortestDistancePathNode node = finalMap.get(nodeNumber);
	        	 System.out.print(node.getNodeNmber() + "             ");
	        	 String distanceStr = node.getShortestRoute();
	        	 if(distanceStr != null) {
	        		 if(distanceStr.contains("reachable")){
	        			 System.out.println("N/A");
	        		 }
	        		 else{
	        			 String[] splitStrings = distanceStr.split(sourceNodeSubString);
		        		 System.out.println(splitStrings[1].charAt(0));
	        		 }	        		
	        	 }
	         }
			
			
			break;
		case 3:
			
			if(rcount == 0){
				System.out.println("\nTopology Matrix is not initialized, please select option 1 prior to other options to enter the same");
				continue;
			}
			
			System.out.println("\nEnter the destination node number");
			BufferedReader destOption = new BufferedReader(new InputStreamReader(System.in));
			String destOptionStr = "";
			int destOptionInt = 0;
			try 
			 { 
				destOptionStr = destOption.readLine();
				destOptionInt = Integer.parseInt(destOptionStr);
				
				if(destOptionInt <= 0){
					System.out.println("\nNode does not exist, enter correct number present in the topology"); 
					 continue;
				}
				if(destOptionInt > rcount){
					System.out.println("\nNode does not exist, enter correct number present in the topology"); 
					 continue;
				}
				
			 } 
			 catch (IOException e) 
			 { 
				 System.out.println("\nError: choose a correct node number"); 
				 continue;
			 } 
			 catch(NumberFormatException e){
				 System.out.println("\nError: choose a correct node number"); 
				 continue;
			 }
			
			//print the distances and the shortest routes
	         Iterator<Integer> itr2 = finalMap.keySet().iterator();
	         while(itr2.hasNext()){
	        	 Integer nodeNumber = itr2.next();
	        	 
	        	 if(destOptionInt != nodeNumber){
	        		 continue;
	        	 }
	        	 
	        	 ShortestDistancePathNode node = finalMap.get(nodeNumber);
	        	 if(node.getDistance() == Integer.MAX_VALUE){
	        		 System.out.println("Shortest distance from " + sourceNumber + " to " + node.getNodeNmber() + " is infinity and is not reachable ");
	        	 }
	        	 else{
	        		 System.out.println("Shortest distance from " + sourceNumber + " to " + node.getNodeNmber() + " is " + node.getDistance() + " via " + node.getShortestRoute());
	        	 }
	         }
			
			break;
		case 4:
			System.out.println("\nExit the Program");
			System.exit(-1);
		default:
			System.out.println("\nExit the Program");
			System.exit(-1);
		}
         
	}
		
	}
	
	public static String reverseNL(String s) {
	    if (s.length() <= 1) { 
	        return s;
	    }
	    return reverseNL(s.substring(1, s.length())) + s.charAt(0);
	}

}
