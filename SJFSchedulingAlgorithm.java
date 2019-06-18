/** SJFSchedulingAlgorithm.java
 * 
 * A shortest job first scheduling algorithm.
 *
 * @author: Charles Zhu
 * Spring 2016
 *
 */
package com.jimweller.cpuscheduler;

import java.util.*;

import com.jimweller.cpuscheduler.Process;

public class SJFSchedulingAlgorithm extends BaseSchedulingAlgorithm implements OptionallyPreemptiveSchedulingAlgorithm {

    private Vector<Process> jobs;
    boolean x;
    protected Process temp;
    
    SJFSchedulingAlgorithm()
    {
        temp = null;
        activeJob = null;
        jobs = new Vector<Process>();
        x = false;
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
    public Process getNextJob(long currentTime)
    {
        if(x==false)
        {
            Process temp;
            temp = jobs.get(0);
            if(jobs.size()==0)
            {
                return null;
            }
            else
            {
                for(int i =0; i < jobs.size() ; i++ )
                {
                    if(jobs.get(i).isActive())
                    {
                        return jobs.get(i);
                    }//if
                    else
                    {
                            if(temp.getInitBurstTime()>jobs.get(i).getInitBurstTime() && jobs.get(i).getArrivalTime() <= currentTime)
                            {
                                temp= jobs.get(i);
                            }//if
                            else if (temp.getInitBurstTime() == jobs.get(i).getInitBurstTime() && jobs.get(i).getArrivalTime() <= currentTime)
                            {
                                if(temp.getPID()>jobs.get(i).getPID())
                                {
                                    temp = jobs.get(i);
                                }//if
                            }//elseif
                    }//else
                
                }//for
                if (temp != null)
                    return temp;
                else
                    return null;
            }//else
        }//ifx==false
        
        else
        {
            activeJob = jobs.get(0);
            for(int i = 1; i < jobs.size(); i++)
            {
                if(activeJob.getBurstTime() > jobs.get(i).getBurstTime() && jobs.get(i).getArrivalTime() <= currentTime)
                {
                    activeJob = jobs.get(i);
                }//if
                else if(activeJob.getBurstTime() == jobs.get(i).getBurstTime() && jobs.get(i).getArrivalTime() <= currentTime)
                {
                    if(jobs.get(i).getPID() < activeJob.getPID())
                        activeJob = jobs.get(i);
                }//elseif
            }
            return activeJob;
        }
    }
        
        
        
        
        
    

    public String getName(){
        return "Shortest Job First";
    }

    /**
     * @return Value of preemptive.
     */
    public boolean isPreemptive(){
        return x;
    }
    
    /**
     * @param v  Value to assign to preemptive.
     */
    public void setPreemptive(boolean  v){
        x = v;
    }

    
}