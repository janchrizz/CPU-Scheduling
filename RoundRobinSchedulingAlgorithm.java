/** RoundRobinSchedulingAlgorithm.java
 * 
 * A scheduling algorithm that randomly picks the next job to go.
 *
 * @author: Kyle Benson
 * Winter 2013
 *
 */
package com.jimweller.cpuscheduler;

import java.util.*;

public class RoundRobinSchedulingAlgorithm extends BaseSchedulingAlgorithm {

    /** the time slice each process gets */
    private int counter;
    private int quantum;
    private Vector<Process> jobs;
    private long recentPID;

    RoundRobinSchedulingAlgorithm() {
        activeJob = null;
        recentPID = 0;
        counter = 0;
        jobs = new Vector<Process>();
    }

    /** Add the new job to the correct queue. */
    public void addJob(Process p) {
        System.out.println("ADD CALLED");
        jobs.add(p);
    }

    /** Returns true if the job was present and was removed. */
    public boolean removeJob(Process p) {
        return jobs.remove(p);
    }

    /** Transfer all the jobs in the queue of a SchedulingAlgorithm to another, such as
    when switching to another algorithm in the GUI */
    public void transferJobsTo(SchedulingAlgorithm otherAlg) {
        throw new UnsupportedOperationException();
    }

    /**
     * Get the value of quantum.
     * 
     * @return Value of quantum.
     */
    public int getQuantum() {
        return quantum;
    }

    /**
     * Set the value of quantum.
     * 
     * @param v
     *            Value to assign to quantum.
     */
    public void setQuantum(int v) {
        this.quantum = v;
    }

    /**
     * Returns the next process that should be run by the CPU, null if none
     * available.
     */
    public Process getNextJob(long currentTime) {

        if(jobs.size() == 0)
        {
            counter++;
            return null;
        }//if queue empty return nothing
        
        else
        {
            for(int i = 0; i < jobs.size(); i++)
            {
                if(jobs.get(i).isActive())
                {
                        activeJob = jobs.get(i);
                }//if
            }//for loop to find active job
            
            if(activeJob != null && activeJob.isFinished())
            {
                activeJob = null;
            }//if the active job is finished, set the active job to null so other job can move in
            
            if(activeJob != null)
            {
                if((counter != 0) && (counter % quantum != 0))
                {
                    counter++;
                    recentPID = activeJob.getPID();
                    return activeJob;
                }//if in between quantum and there is active job, return that job
                
                else
                {
                    Process temp = null;
                    long tempPID = activeJob.getPID();
                    System.out.println(tempPID);
                    int temp1 = jobs.indexOf(activeJob);
                    
                    for(int i = 0; i< jobs.size(); i++)
                    {
                        System.out.println(jobs.get(i).getPID());
                        if(jobs.get(i).getPID() > tempPID)
                        {
                            System.out.println("Found new process");
                            if(temp == null)
                            {
                                temp = jobs.get(i);
                                activeJob = jobs.get(i);
                            }//if
                            
                            else
                            {
                                if(jobs.get(i).getPID() < temp.getPID())
                                {
                                    temp = jobs.get(i);
                                    activeJob = jobs.get(i);
                                }//if
                            }//else
                            
                        }//if
                    }//for to find job with minimum PID that is greater than active job's pid
                   
                    if(temp == null)
                    {
                        activeJob = jobs.get(0);
                        
                        for(int i = 1; i < jobs.size() && i != temp1; i++)
                        {
                           if(jobs.get(i).getPID() < activeJob.getPID())
                               activeJob = jobs.get(i);
                        }//for
                        
                        recentPID = activeJob.getPID();
                        counter = 1;
                        return activeJob;
                    }//if job pid is max, return the smallest PID from the list (start from beginning)
                    else
                    {
                        System.out.println("Returning new process");
                        counter = 1;
                        recentPID = activeJob.getPID();
                        return activeJob;
                    }
                }//if time quantum is finished (time to get a new process)
            }//if time quantum is reached or if counter is still zero in beginning
            
            else /**if activeJob is null*/
            {
                if(recentPID == 0)
                {
                    activeJob = jobs.get(0);
                    for(int i = 1; i< jobs.size(); i++)
                    {
                        if(jobs.get(i).getPID() < activeJob.getPID() && jobs.get(i).getArrivalTime() <= currentTime)
                            activeJob = jobs.get(i);
                    }
                    
                    counter++;
                    recentPID = activeJob.getPID();
                    return activeJob;
                }//in initial start, choose arrived job with smallest pid
                
                else //next,assuming recent PID exists
                {
                    for(int i = 0; i< jobs.size(); i++)
                    {
                        if(jobs.get(i).getPID() > recentPID)
                        {
                            if(activeJob == null)
                            {
                                activeJob = jobs.get(i);
                                recentPID = jobs.get(i).getPID();
                            }//if
                            else
                            {
                                if(activeJob.getPID() > jobs.get(i).getPID())
                                {
                                    activeJob = jobs.get(i);
                                    recentPID = jobs.get(i).getPID();
                                }//if
                            }//else
                        }//if
                    }//for
                    
                    if(activeJob == null)
                    {
                        activeJob = jobs.get(0);
                        recentPID = activeJob.getPID();
                        for(int i = 0; i< jobs.size(); i++)
                        {
                            if(jobs.get(i).getPID() < activeJob.getPID())
                            {
                                activeJob = jobs.get(i);
                                recentPID = jobs.get(i).getPID();
                            }//if
                        }//for
                    }//if recent pid is highest
                    
                    counter = 1;
                    return activeJob;
                    
                }//else if there is recent PID
            }//else if no active job
        
        }//else if jobs size is not zero
        
    }//getNextJob()

    public String getName() {
        return "Round Robin";
    }
    
}