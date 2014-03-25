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
		//System.out.println(new File(".").getAbsolutePath());
		 System.out.println("Enter the file path");
		 BufferedReader b = new BufferedReader(new InputStreamReader(System.in)); 
		 String filename="";
		 int[][] cost = new int[1][1];
		 int[][] correctCost = new int[1][1];
		 try 
		 { 
		 filename=b.readLine(); 
		 } 
		 catch (IOException e) 
		 { 
		 System.out.println("IOError"); 
		 } 
		 
		 try{
		 
         FileInputStream fstream = new FileInputStream(filename); 
         DataInputStream in = new DataInputStream(fstream); 
         BufferedReader br = new BufferedReader(new InputStreamReader(in)); 
         String strLine;
         int rcount=0;

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
         
        
         
         System.out.println("The Input Table is"); 
         for(j=1;j<=rcount;j++) 
         { 
         for(k=1;k<=rcount;k++) 
         { 
        	 
         System.out.print(cost[j][k] + " "); 
         } 
         System.out.println(); 
         } 
         
         System.out.println("Correct Input Table is"); 
         for(j=1;j<=rcount;j++) 
         { 
         for(k=1;k<=rcount;k++) 
         { 
         System.out.print(correctCost[j][k] + " "); 
         } 
         System.out.println(); 
         }  
         
         int sourceNumber = 3;
         
         //initialise the array of NodeVisited objects.
         NodeVisited[] nodeVisitedList = new NodeVisited[rcount+1];
         for(int i =1; i<= rcount; i++){
        	 nodeVisitedList[i] = new NodeVisited();
        	 nodeVisitedList[i].setVisited(false);
        	 nodeVisitedList[i].setNodeNumber(i);
         }
         
         //initialise source NodeVisited Object
         nodeVisitedList[sourceNumber].setVisited(true);
         
         //final distanceNode Array
         DistanceNode[][] finalDistanceNodeNumbers = new DistanceNode[rcount+1][rcount+1];
         
         for(j=0;j<=rcount;j++) 
         { 
         for(k=0;k<=rcount;k++) 
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
        	 
        	 //find out the next source number and the minDistance
        	
        	 Integer lineMinimumDistance = null;
        	 Integer lineNextSourceNodeNumber = null;
        	 
        		 //finalDistanceNodeNumbers[n][m].getDistance();
        	 //int lineNextSourceNodeNumber = finalDistanceNodeNumbers[n][m].getNodeNumber();
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
         
         
         System.out.println("Final Table is"); 
         for(j=0;j<=rcount;j++) 
         { 
         for(k=0;k<=rcount;k++) 
         { 
         System.out.print(finalDistanceNodeNumbers[j][k].getDistance() + "," + finalDistanceNodeNumbers[j][k].getNodeNumber() + "  "); 
         } 
         System.out.println(); 
         }
         
         Map<Integer, ShortestDistancePathNode> finalMap = new HashMap<Integer, ShortestDistancePathNode>();
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
        	 node.setShortestRoute(strRoute);
         }
         
         //print the distances and the shortest routes
         Iterator<Integer> itr2 = finalMap.keySet().iterator();
         while(itr2.hasNext()){
        	 Integer nodeNumber = itr2.next();
        	 ShortestDistancePathNode node = finalMap.get(nodeNumber);
        	 if(node.getDistance() == Integer.MAX_VALUE){
        		 System.out.println("Distance to " + node.getNodeNmber() + " is infinity and is not reachable ");
        	 }
        	 else{
        		 System.out.println("Distance to " + node.getNodeNmber() + " is " + node.getDistance() + " via " + node.getShortestRoute());
        	 }
         }
         
		 }
		 catch(FileNotFoundException e){
			 System.out.println("File not found! Please specify the path properly");
		 }
		 //catch(IndexOutOfBoundsException e){
			 //System.out.println("Matrix in the file is not N x N. Please check the content of the file again");
		 //}
         
	}

}
