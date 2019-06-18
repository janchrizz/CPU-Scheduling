/** FCFSSchedulingAlgorithm.java
 * 
 * A first-come first-served scheduling algorithm.
 *
 * @author: Charles Zhu
 * Spring 2016
 *
 */
package com.jimweller.cpuscheduler;

import java.util.*;

public class FCFSSchedulingAlgorithm extends BaseSchedulingAlgorithm {
    
    private Vector<Process> jobs;
    
    FCFSSchedulingAlgorithm(){
        
        activeJob = null;
        jobs = new Vector<Process>();
        
    }

    /** Add the new job to the correct queue.*/
    public void addJob(Process p){
    
        jobs.add(p);

    }
    
    /** Returns true if the job was present and was removed. */
    public boolean removeJob(Process p){
        
        return jobs.remove(p);
        
    }

    /** Transfer all the jobs in the queue of a SchedulingAlgorithm to another, such as
    when switching to another algorithm in the GUI */
    public void transferJobsTo(SchedulingAlgorithm otherAlg) {
       
        throw new UnsupportedOperationException();
        
    }

    /** Returns the next process that should be run by the CPU, null if none available.*/
    public Process getNextJob(long currentTime){
        if(jobs.size() == 0)
        {
            return null;
        }
        else
        {
            activeJob = jobs.get(0);
            for(int i= 1; i < jobs.size(); i++)
            {
                if(jobs.get(i).getArrivalTime() < activeJob.getArrivalTime())
                {
                    activeJob = jobs.get(i);
                }
                else if(jobs.get(i).getArrivalTime() == activeJob.getArrivalTime())
                {
                    if(jobs.get(i).getPID() < activeJob.getPID())
                        activeJob = jobs.get(i);
                }
                
                       
        
            }//for
            return activeJob;
        }//else

    }

    public String getName()
    {
        
        return "First-Come First-Served";
        
    }
    
}