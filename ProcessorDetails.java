/**
 * Description : This is simple class file which helps to instantiate the Process object. It contains the constructor, setter, and getter methods.
 *  
 * @author     : Bikash Thapa
 * @since      : March 20,2019
*/

public class ProcessorDetails 
{
  // Data Field
   public int time, cpuBurst, priority,turnAroundTime,waitTime;
     
   /**
   * Initializes a ProcessorDetails object with all properties specified
   * @param time the arrival time of the process
   * @param cpuBurst the burst time of the process
   * @param priority the priority number of the process
   * @param turnAroundTime the time between the process enter and exit the system
   * @param waitTime the waiting time of the process to gain the access of the CPU
   */
   public ProcessorDetails(int time,int cpuBurst,int priority,int turnAroundTime,int waitTime)
   {
      this.time=time;
      this.cpuBurst=cpuBurst;
      this.priority=priority;
      this.turnAroundTime=turnAroundTime;
      this.waitTime=waitTime;
   }
   public ProcessorDetails() {}
  /**
   * Get the arrival time of the process
   *@return the arrival time of the process
   */
   public int getTime()
   {
      return time;
   }
  /**
   * Get the burst time of the process
   *@return the burst time of the process
   */
   public int getCpuBurst()
   {
      return cpuBurst;
   }
  /**
   * Get the priority of the process
   *@return the priority of the process
   */
   public int getPriority()
   {
      return priority;
   }
  /**
   * Get the turnAround Time 
   *@return the turnAroundTime of the process
   */
   public int getTurnAroundTime()
   {
      return turnAroundTime;
   }
   /**
   * Get the waiting Time
    *@return the waitTime of the process

   */
   public int getWaitTime()
   {
      return waitTime;
   }
  /**
   *Set the arrival time of the process
   * @param time the arrival time of the process
   */
   public void setTime(int time)
   {
      this.time=time;
   }
  /**
   * Set the cpu burst of the process
   * @param cpuBurst the burst time of the process
   */
   public void setCpuBurst(int cpuBurst)
   {
      this.cpuBurst=cpuBurst;
   }
  /**
   *Set the turn around time of the process
   * @param turnAroundTime the time between the process exit and entry
   */
   public void setTurnAroundTime(int turnAroundTime)
   {
      this.turnAroundTime=turnAroundTime;
   }
  
   /**
   * Set the wait time of the process
   * @param waitTime the time between the process entry and getting access to the CPU

   */
   public void setWaitTime(int waitTime)
   {
      this.waitTime=waitTime;
   }
 
}