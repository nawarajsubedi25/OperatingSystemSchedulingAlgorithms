/**
 * Description : This program is CPU scheduling program which implements the FCFS, SJNNP,PNP, and RR algorithm to allocate CPU to the process. This program reads the value from the input file which contains 
 *             all the information regarding which algorithm to implement and also about the processess such as process Id, timeStamp, cpuBurst, and priority(in case of PNP). This program uses a map data
               structure to hold the process as Object. This program also uses class ProcessorDetails to instantiate process object with properties such as time, cpuBurst, and so on according the input file.
 *  
 * @author     : Nawaraj Subedi
 * @since      : March 28,2019
 */ 

import java.io.*;
import java.util.Scanner;
import java.util.*;
public class Operating_System_Scheduling_Algorithms
{
  // Data Field
   Map<Integer, ProcessorDetails> map; 
   ProcessorDetails aprocessor;   
   HashSet<Integer> passedProcessor =new HashSet<Integer>();
   int returnId=0;
   int returnCpu=0;
   int returnTime=0;
   int enterTime=0;
   int waitingTime=0;
   int processorEnt=0;
   String algo="";
   PrintWriter writer;
   StringBuilder requiredString =new StringBuilder();
   String fileName="";
   String algorithm="";
   String tatPrint="";
   double averageTat=0.0;
   String wtPrint="";
   double averageWt=0.0;
   String clock="";
   String printer="";



 /**
  * Constructor to create and to initilized empty Map
  */
   public Operating_System_Scheduling_Algorithms()
   {
      this.map =new HashMap<Integer, ProcessorDetails>();   
   }
   
 /** This method takes the input file and process the data according the instruction. It also stores the process ID as key of map while value would be object of ProcessorDetails
   *@param filename the name of the input file which contains the data
 */
  
   public void buildMap(String filename)
   {
      map = new HashMap<Integer, ProcessorDetails>();
      try {
         File infile=new File (filename);
         Scanner sc=new Scanner(infile);
         String line =sc.nextLine(); 
         Scanner read=new Scanner(line);
         read.next();
         if(read.hasNext())
         {
            algo=read.next();
         }  
         
         while(sc.hasNextLine())
         {
            line =sc.nextLine();
            read=new Scanner(line);
            read.next();
            int id=Integer.parseInt(read.next()) ;
            int time=Integer.parseInt(read.next());
            int priority=0;
            int turnAroundTime=0;
            int waitTime=0;
            int cpuBurst=Integer.parseInt(read.next());
            
            //If the algorithm says pnp it stores the extra token on the priority properties
          if(algo.equals("pnp"))
            {
               priority=Integer.parseInt(read.next());
            }
            
            aprocessor=new ProcessorDetails(time,cpuBurst,priority,turnAroundTime,waitTime);
           //*******To put information in map *******************
            map.put(id,aprocessor);             
         }
         
         sc.close();
         
         if ( algo.equals("rr"))
         {
         
            int pId[] = new int[map.size()];            
         // arrival for every process 
            int arrivalTime[] =new int[map.size()];
         // burst time for every process 
            int burstTime[]=new int[map.size()]; 
         // quantum time of each process 
            Iterator<Integer> iter = map.keySet().iterator();
            int processorDetail=0;
            int n=0;
            while (iter.hasNext()) 
            {  pId[n] = iter.next();
               ProcessorDetails reader = map.get(pId[n]);
               arrivalTime[n]= reader.getTime();
               burstTime[n]=reader.getCpuBurst();    
               n++;
            }
            roundRobin(pId, arrivalTime, burstTime);   
         }
         else
         {
            algorithm();
         }
      } 
      catch(FileNotFoundException e)
       {
         System.out.println("Alert: File "+filename+" doesn't exit !!");
      }
      
      catch(Exception e) 
      {
         System.out.println("Alert: File  "+filename+" can't be opened !!");
      }
     
   }
   
   public static void main(String[] args)
   { 
      Operating_System_Scheduling_Algorithms algo = new Operating_System_Scheduling_Algorithms();
      algo.buildMap("in.txt");
   }
   
   /** This method takes the two helper method. The first is getWaitingTime and the second is the corressponding algorithm method to implement
      * cpu scheduling algorithm according to the input file. It also output the solution in a correct format with correct output name as specified by algorithm.
      */
   
   public void algorithm()
   { 
      int waitingTime=getWaitingTime(0);
      if ( waitingTime!=0)
      {
         clock+="Clock: "+processorEnt+"\r\nPending CPU request(s):\r\n";
         clock+="None"+
                  "\r\n----------------------------------------------------------\r\n";
      }
      int n=0;
      int tat=0;
      while ( n < map.size())
      { 
         int exitTime=waitingTime+processorEnt;
         try {
            clock+="Clock: "+exitTime+"\r\nPending CPU request(s):\r\n";
            Iterator<Integer> iter = map.keySet().iterator();
            int processorDetail=0;
            while (iter.hasNext()) 
            {           processorDetail = iter.next();
               if (!passedProcessor.contains(processorDetail))
               {  
                  ProcessorDetails read = map.get(processorDetail);
                  int time= read.getTime();
                  if ( time <= (exitTime))
                  { 
                     if(!algo.equals("pnp")){
                        clock+=processorDetail+ " "+read.getTime()+" "+read.getCpuBurst()+"\r\n";}
                     
                     if(algo.equals("pnp")){
                        clock+=processorDetail+ " "+read.getTime()+" "+read.getCpuBurst()+" "+read.getPriority()+"\r\n";}
                  
                  }    
               }
            }
            
            // Depending upon the value of algo the subsequent if/else blocks execute the required algorithms.
            if (algo.equals("sjnnp"))
            {
               fileName="sjnnp_out.txt";
               sjnnp(exitTime);                     
            }
            else if (algo.equals("fcfs"))
            {
               fileName="fcfs_out.txt";
               fcfs(exitTime);
            }
            else if (algo.equals("pnp"))
            { 
               fileName="pnp_out.txt";
               pnp(exitTime);
            } 
            ProcessorDetails read = map.get(returnId);
            if (!algo.equals("pnp"))
            {
               clock+="\r\nCPU Request serviced during this clock interval: "+ returnId+" "+read.getTime()+" "+ read.getCpuBurst()+
                  "\r\n----------------------------------------------------------\r\n"; 
            }
            else
            {
               clock+="\r\nCPU Request serviced during this clock interval: "+ returnId+" "+read.getTime()+" "+ read.getCpuBurst()+ " "+read.getPriority()+
                  "\r\n----------------------------------------------------------\r\n"; 
            
            }
            writer= new PrintWriter(fileName); 
            //calculating turnAroudTime
            tat = returnTime;
            averageTat+=tat;
            tatPrint+="TAT("+returnId+") = "+tat+"\r\n";
            processorEnt=processorEnt+returnCpu+waitingTime;
            //calculating waitingTime
            int wat=tat-returnCpu;
            wtPrint+="WT("+returnId+") = "+wat+"\r\n";
            averageWt+=wat;
            //Setting the tat time and wat time so that it can later retrieve to print the output file.
            read.setTurnAroundTime(tat);
            read.setWaitTime(wat);
            n++;   
            waitingTime=getWaitingTime(processorEnt);
            if ( waitingTime!=0)
            {
               clock+="Clock: "+processorEnt+"\r\nPending CPU request(s):\r\n";
               clock+="None"+
                  "\r\n----------------------------------------------------------\r\n";
            }
         }
         catch(Exception e) {
            System.out.println("Alert: File "+ fileName+ " can't be opened output !!");
            
         }      
        
      }
      requiredString.append("CPU scheduling algorithm: "+ algo+"\r\n" +"Total number of CPU requests: "+map.size()+
         "\r\n----------------------------------------------------------\r\n");
      writer.write(requiredString.toString());
      writer.write(clock);
      writer.write("Turn-Around Time Computations\r\n\r\n"+tatPrint+"\r\nAverage TAT = "+(averageTat/(map.size()))+
         "\r\n----------------------------------------------------------"+
         "\r\nWait Time Computations\r\n\r\n"+wtPrint+"\r\nAverage WT = "+(averageWt/(map.size())));
      writer.flush();
   
   }  
   
  /**This method helps to determine the waiting time of the process if there is a gap between the process arrival and clock cycle.
      * @param requiredTime current clock time of the CPU  
      * @return waiting Time of the process   
     
     */
          
   int getWaitingTime( int requiredTime)
   {
      int tempTime=0;
      waitingTime=0;
      int time=0;
      Iterator<Integer> iter1 = map.keySet().iterator();
      int processorDetail=0;
      while (iter1.hasNext()) 
      {           processorDetail = iter1.next();
         if (!passedProcessor.contains(processorDetail))
         {  
            ProcessorDetails read = map.get(processorDetail);
            time= read.getTime();    
         }
      }
      Iterator<Integer> iter = map.keySet().iterator();
      while (iter.hasNext()) 
      {           processorDetail = iter.next();
         if (!passedProcessor.contains(processorDetail))
         {  
            ProcessorDetails read = map.get(processorDetail);
            tempTime= read.getTime();
            if ( tempTime<=time){
               enterTime=tempTime;
               returnId=processorDetail;
               returnCpu=read.getCpuBurst();
               time=tempTime;  
            }  
         }
      }
      if ( enterTime == requiredTime)
      {
         waitingTime=0;
      }
      if ( enterTime<requiredTime)
      {
         waitingTime=0;
      } 
      if (enterTime > requiredTime)
      {
         waitingTime=enterTime-requiredTime;
      }
       
      return (waitingTime);
   }
   
   
    /**
      * This method implements the shortest job next non preemptive algorithm to schedule the process. The algorithm looks up for process with minimum cpu burst time. If two process have same cpu burst time then
      * algorithm checks the process Id and gives access of CPU to the porcess with lowest process Id.
      * This method uses a map to iterate through the process to find the process with earliest arrival time.
      * @param processorEnter the total time from the first process to last process calculated by adding their arrival time and its exectution time i.e cpu burst
     */

   
   void sjnnp( int processorEnter)
   {
      int checkId=0;
      int checkCpuBurst=0;
      int processorDetail=0;
      Iterator<Integer> iter = map.keySet().iterator();
      while (iter.hasNext()) 
      {           processorDetail = iter.next();
         if (!passedProcessor.contains(processorDetail))
         {  
            ProcessorDetails read = map.get(processorDetail);
            int tempTime= read.getTime();
            checkId= processorDetail;
            checkCpuBurst=read.getCpuBurst();
            if ( tempTime<=processorEnter)
            {
               if ( checkCpuBurst<returnCpu)
               {
                  enterTime=tempTime;
                  returnId=checkId;
                  returnCpu=checkCpuBurst;
                  returnTime=processorEnter+returnCpu-enterTime;
               }
               if ( checkCpuBurst==returnCpu)
               {
                  if (checkId <= returnId)
                  
                  {
                     enterTime=tempTime;
                     returnId=checkId;
                     returnCpu=checkCpuBurst;
                     returnTime=processorEnter+returnCpu-enterTime;
                  }
               }
                                
            }  
         }
      }
      passedProcessor.add(returnId);
   }
   
    /**
      * This method implements the first come first serve algorithm to schedule the process. If two process have same arrival time then the algorithm looks up for process with lowest process
      * Id. This method uses a map to iterate through the process to find the process with earliest arrival time.
      * @param processorEnter the total time from the first process to last process calculated by adding their arrival time and its exectution time i.e cpu burst
    */

  
   void fcfs( int processorEnter)
   {
      int checkId=0;
      int checkCpuBurst=0;
      int processorDetail=0;
      int timer=processorEnter;
      Iterator<Integer> iter = map.keySet().iterator();
      while (iter.hasNext()) 
      {           processorDetail = iter.next();
         if (!passedProcessor.contains(processorDetail))
         {  
            ProcessorDetails read = map.get(processorDetail);
            int tempTime= read.getTime();
            checkId= processorDetail;
            checkCpuBurst=read.getCpuBurst();
            
            if ( tempTime<timer)
            {
               timer=tempTime;
               enterTime=tempTime;
               returnId=checkId;
               returnCpu=checkCpuBurst;
               returnTime=processorEnter+returnCpu-enterTime;
            }
             
            if ( tempTime==timer)
            {
               if (checkId <= returnId)
                  
               {
                  enterTime=tempTime;
                  returnId=checkId;
                  returnCpu=checkCpuBurst;
                  returnTime=processorEnter+returnCpu-enterTime;
               }
            }
         }
      }
      passedProcessor.add(returnId);
   } 
   
   /**
      * This method that implements the priority non-preemmptive algorithm to schedule the process. If two process have same priority then the algorithm looks up for process with lowest process
      * Id. This method uses a map to iterate through the process to find the process with highest priority.
      * @param processorEnter the total time from the first process to last process calculated by adding their arrival time and its exectution time i.e cpu burst
 
     */
              
   void pnp(int processorEnter)
   {
      ProcessorDetails i = map.get(returnId);
      int returnPriority = i.getPriority();
      int checkId=0;
      int checkCpuBurst=0;
      int processorDetail=0;
      int checkPriority=0;
      Iterator<Integer> iter = map.keySet().iterator();
      while (iter.hasNext()) 
      {           processorDetail = iter.next();
         if (!passedProcessor.contains(processorDetail))
         {  
            ProcessorDetails read = map.get(processorDetail);
            int tempTime= read.getTime();
            checkId= processorDetail;
            checkCpuBurst=read.getCpuBurst();
            checkPriority=read.getPriority();
            
            if ( tempTime<=processorEnter)
            {
               if ( checkPriority<returnPriority)
               {
                  enterTime=tempTime;
                  returnId=checkId;
                  returnCpu=checkCpuBurst;
                  returnPriority=checkPriority;
                  returnTime=processorEnter+returnCpu-enterTime;
               }
               if ( checkPriority==returnPriority)
               {
                  if (checkId <= returnId)
                  {
                     enterTime=tempTime;
                     returnId=checkId;
                     returnCpu=checkCpuBurst;
                     returnTime=processorEnter+returnCpu-enterTime;
                  }
               }
                                
            }  
         }
      }
      passedProcessor.add(returnId);
   }
    
   /**
      * This method that implements the Round Robin algorithm to schedule the process. This method takes three argument.
      * @param pId array to hold the Process ID
      * @param arrivalTime array to hold the arrival Time
      * @param burstTime array to hold the CPU Burst    
     */

   
   void roundRobin(int pId[], int arrivalTime[], int burstTime[]) 
   { 
      try
      {
         writer= new PrintWriter("rr_out.txt"); 
      }
      catch(Exception e) 
      {
            System.out.println("Alert: File rr_out.txt can't be opened output !!");
            }         
      printer+="CPU scheduling algorithm: "+ algo+"\r\n" +"Total number of CPU requests: "+map.size()+
          "\r\n----------------------------------------------------------\r\n";
   
      int timeSlices=1;
      String pendingCpu="";   
        
      int copyBurstTime[] = new int[burstTime.length]; 
      int copyArrivalTime[] = new int[arrivalTime.length]; 
      
      for (int i = 0; i < copyBurstTime.length; i++) 
      { 
         copyBurstTime[i] = burstTime[i]; 
         copyArrivalTime[i] = arrivalTime[i]; 
      } 
      
      int t = 0;  
      int waitTime[] = new int[pId.length]; 
      int cominTime[] = new int[pId.length]; 
   
      while (true) { 
         boolean flag = true; 
         for (int i = 0; i < pId.length; i++) { 
            if (copyArrivalTime[i] <= t) 
            { 
               if (copyArrivalTime[i] <= timeSlices) 
               { 
                  if (copyBurstTime[i] > 0) 
                  { 
                     flag = false; 
                     if (copyBurstTime[i] > timeSlices)
                     { 
                        printer+="Clock: "+t+"\r\nPending CPU request(s): \r\n";  
                        String servedCpu=" "+pId[i]+" "+arrivalTime[i]+" "+copyBurstTime[i];  
                        for ( int k=0; k<map.size(); k++)
                        {
                           if (!(copyBurstTime[k]<=0))
                           {
                              printer+=pId[k]+" "+arrivalTime[k]+" "+copyBurstTime[k]+"\r\n";
                           }
                        }
                                
                        t = t + timeSlices; 
                        copyBurstTime[i] = copyBurstTime[i] - timeSlices; 
                        copyArrivalTime[i] = copyArrivalTime[i] + timeSlices;  
                        printer+="\r\nCPU Request serviced during this clock interval:"+servedCpu+"\r\n"+
                            "----------------------------------------------------------\r\n";
                                       
                     } 
                     else 
                     { 
                        printer+="Clock: "+t+"\r\nPending CPU request(s): \r\n";  
                        String servedCpu=" "+pId[i]+" "+arrivalTime[i]+" "+copyBurstTime[i];  
                        for ( int k=0; k<map.size(); k++)
                        {
                           if (!(copyBurstTime[k]<=0))
                           {
                              printer+=pId[k]+" "+arrivalTime[k]+" "+copyBurstTime[k]+"\r\n";
                           }                           }
                        t = t + copyBurstTime[i]; 
                     
                        cominTime[i] = t - arrivalTime[i]; 
                     
                        
                        waitTime[i] = t - burstTime[i] - arrivalTime[i]; 
                        copyBurstTime[i] = 0;
                        printer+="\r\nCPU Request serviced during this clock interval:"+servedCpu+"\r\n"+
                            "----------------------------------------------------------\r\n";
                      
                     } 
                  } 
               } 
               
               else if (copyArrivalTime[i] > timeSlices) 
               { 
                  for (int j = 0; j < pId.length; j++)
                  { 
                     if (copyArrivalTime[j] < copyArrivalTime[i])
                     { 
                        if (copyBurstTime[j] > 0)
                        { 
                           flag = false; 
                           if (copyBurstTime[j] >timeSlices) 
                           { 
                              printer+="Clock: "+t+"\r\nPending CPU request(s): \r\n";  
                              String servedCpu=" "+pId[i]+" "+arrivalTime[i]+" "+copyBurstTime[i];  
                              for ( int k=0; k<map.size(); k++)
                              {
                                 if (!(copyBurstTime[k]<=0))
                                 {
                                    printer+=pId[k]+" "+arrivalTime[k]+" "+copyBurstTime[k]+"\r\n";
                                 }                                 }
                              t = t + timeSlices; 
                              copyBurstTime[j] = copyBurstTime[j] - timeSlices; 
                              copyArrivalTime[j] = copyArrivalTime[j] + timeSlices; 
                              printer+="\r\nCPU Request serviced during this clock interval:"+servedCpu+"\r\n"+
                                  "----------------------------------------------------------\r\n";
                             
                           } 
                           else 
                           { 
                              printer+="Clock: "+t+"\r\nPending CPU request(s): \r\n";  
                              String servedCpu=" "+pId[i]+" "+arrivalTime[i]+" "+copyBurstTime[i];  
                              for ( int k=0; k<map.size(); k++)
                              {
                                 if (!(copyBurstTime[k]<=0))
                                 {
                                    printer+=pId[k]+" "+arrivalTime[k]+" "+copyBurstTime[k]+"\r\n";
                                 }                                 }
                              t = t + copyBurstTime[j]; 
                              cominTime[j] = t - arrivalTime[j]; 
                              waitTime[j] = t - burstTime[j] - arrivalTime[j]; 
                              copyBurstTime[j] = 0; 
                            
                                                            
                          } 
                        } 
                     } 
                  } 
               
                  if (copyBurstTime[i] > 0) { 
                     flag = false; 
                     if (copyBurstTime[i] > timeSlices) { 
                        printer+="Clock: "+t+"\r\nPending CPU request(s): \r\n";  
                        String servedCpu=" "+pId[i]+" "+arrivalTime[i]+" "+copyBurstTime[i];  
                        for ( int k=0; k<map.size(); k++)
                        {
                           if (!(copyBurstTime[k]<=0))
                           {
                              printer+=pId[k]+" "+arrivalTime[k]+" "+copyBurstTime[k]+"\r\n";
                           }                           }
                     
                        t = t + timeSlices; 
                        copyBurstTime[i] = copyBurstTime[i] - timeSlices; 
                        copyArrivalTime[i] = copyArrivalTime[i] + timeSlices; 
                        
                        printer+="\r\nCPU Request serviced during this clock interval:"+servedCpu+"\r\n"+
                            "----------------------------------------------------------\r\n";
                     
                     } 
                     else { 
                        printer+="Clock: "+t+"\r\nPending CPU request(s): \r\n";  
                        String servedCpu=" "+pId[i]+" "+arrivalTime[i]+" "+copyBurstTime[i];  
                        for ( int k=0; k<map.size(); k++)
                        {
                           if (!(copyBurstTime[k]<=0))
                           {
                              printer+=pId[k]+" "+arrivalTime[k]+" "+copyBurstTime[k]+"\r\n";
                           }                           }
                        t = t + copyBurstTime[i]; 
                        cominTime[i] = t - arrivalTime[i]; 
                        waitTime[i] = t - burstTime[i] - arrivalTime[i]; 
                        copyBurstTime[i] = 0; 
                        
                        printer+="\r\nCPU Request serviced during this clock interval:"+servedCpu+"\r\n"+
                            "----------------------------------------------------------\r\n";
                     
                     } 
                  } 
               } 
            } 
            else if (copyArrivalTime[i] > t) { 
               t++; 
               i--; 
            }
            
         } 
         if (flag) { 
            break; 
         } 
      } 
      printer+="Turn-Around Time Computations\r\n"; 
      for (int i = 0; i < pId.length; i++) { 
         printer+="TAT(" + pId[i] + ") = " + cominTime[i]+"\r\n";
         averageTat +=
         cominTime[i]; 
      }
      printer+="\r\nAverage TAT ="+ (averageTat/(map.size()))+
          "\r\n----------------------------------------------------------\r\n";
      printer+="Wait Time Computations\r\n\r\n";
      for (int i = 0; i < pId.length; i++) { 
         printer+="WT(" + pId[i] + ") = " + waitTime[i]+"\r\n";
         averageWt+=waitTime[i];  
      }
      printer+="\r\nAverage WT = "+ (averageWt/(map.size()));
      writer.write(printer); 
      writer.flush();
   
   } 
                
}
           




