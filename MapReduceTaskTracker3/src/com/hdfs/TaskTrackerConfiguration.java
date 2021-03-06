package com.hdfs;

public class TaskTrackerConfiguration {
	public Integer dataNodeId;
	public String nameNodeIP;
	public Integer nameNodePort;
	public String dataNodeIP;
	public Integer dataNodePort;
	public String dataNodeBlocksDirectory;
	public String nameNodeReference;
	public String dataNodeReference;
	public Long schedulerDelay;
	public String jobTrackerIp;
	public Integer jobTrackerPort;
	public String jobTrackerReference;
	public Integer taskTrackerId;
	public Integer totalMapSlots;
	public Integer totalReduceSlots;
	public Integer freeMapSlots;
	public Integer freeReduceSlots;
	public int taskTrackerPort;
	public int blockSize;
	public String mappedFilesLocation;
	public String reducedFilesLocation;
	public String jarFilesPath;

}
