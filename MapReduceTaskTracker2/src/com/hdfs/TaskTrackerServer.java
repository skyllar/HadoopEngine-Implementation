package com.hdfs;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;
import java.util.Timer;

public class TaskTrackerServer {

	public static TaskTrackerConfiguration tTCnf;

	public static int initialiseVariables(String[] args) {
		int status = 0;
		String taskTrackerConfigFilePath;

		taskTrackerConfigFilePath = args[0];
		// taskTrackerConfigFilePath =
		// "/home/apratim/workspace/HDFSCore/tasktracker/taskTrackerConfig";

		File file = new File(taskTrackerConfigFilePath);
		if (file.exists()) {
			try {
				BufferedReader bufferedReader = new BufferedReader(
						new FileReader(file));

				String line;
				tTCnf = new TaskTrackerConfiguration();

				// tTCnf.dataNodePort = 5002;
				// tTCnf.dataNodeIP = "localhost";
				// tTCnf.dataNodeId = 1;
				// tTCnf.nameNodeIP = "localhost";
				// tTCnf.nameNodePort = 5001;
				// tTCnf.dataNodeBlocksDirectory =
				// "/home/apratim/workspace/HDFSCore/datanode/";
				// tTCnf.nameNodeReference = "Namenode";
				// tTCnf.dataNodeReference = "Datanode";

				// tTCnf.jobTrackerIp = "localhost";
				// tTCnf.jobTrackerPort = 6000;
				// tTCnf.jobTrackerReference = "JobTracker";

				// tTCnf.taskTrackerId = 1;

				line = bufferedReader.readLine();
				tTCnf.dataNodeReference = line.split("=")[1];

				line = bufferedReader.readLine();
				tTCnf.dataNodePort = Integer.parseInt(line.split("=")[1]);

				line = bufferedReader.readLine();
				tTCnf.dataNodeIP = line.split("=")[1];

				line = bufferedReader.readLine();
				tTCnf.dataNodeId = Integer.parseInt(line.split("=")[1]);

				line = bufferedReader.readLine();
				tTCnf.dataNodeBlocksDirectory = line.split("=")[1];

				line = bufferedReader.readLine();
				tTCnf.nameNodeIP = line.split("=")[1];

				line = bufferedReader.readLine();
				tTCnf.nameNodePort = Integer.parseInt(line.split("=")[1]);

				line = bufferedReader.readLine();
				tTCnf.nameNodeReference = line.split("=")[1];

				line = bufferedReader.readLine();
				tTCnf.schedulerDelay = Long.parseLong(line.split("=")[1]);

				line = bufferedReader.readLine();
				tTCnf.jobTrackerIp = line.split("=")[1];

				line = bufferedReader.readLine();
				tTCnf.jobTrackerPort = Integer.parseInt(line.split("=")[1]);

				line = bufferedReader.readLine();
				tTCnf.jobTrackerReference = line.split("=")[1];

				line = bufferedReader.readLine();
				tTCnf.taskTrackerId = Integer.parseInt(line.split("=")[1]);

				line = bufferedReader.readLine();
				tTCnf.taskTrackerPort = Integer.parseInt(line.split("=")[1]);

				line = bufferedReader.readLine();
				tTCnf.totalMapSlots = Integer.parseInt(line.split("=")[1]);

				line = bufferedReader.readLine();
				tTCnf.totalReduceSlots = Integer.parseInt(line.split("=")[1]);

				line = bufferedReader.readLine();
				tTCnf.blockSize = Integer.parseInt(line.split("=")[1]);

				line = bufferedReader.readLine();
				tTCnf.mappedFilesLocation = line.split("=")[1];

				line = bufferedReader.readLine();
				tTCnf.reducedFilesLocation = line.split("=")[1];

				line = bufferedReader.readLine();
				tTCnf.jarFilesPath = line.split("=")[1];

				tTCnf.freeMapSlots = tTCnf.totalMapSlots;
				tTCnf.freeReduceSlots = tTCnf.totalReduceSlots;

				status = 1;
			} catch (Exception e) {

				e.printStackTrace();
			}
		}
		return status;
	}

	public static void main(String[] args) {
		try {
			int status = initialiseVariables(args);
			if (status == 0) {
				System.out
						.println("TaskTracker Could not launch, Error Reading Configuration File..");
			} else {
				System.out.println("TaskTracker starting..");
				RepeatTask repeatTask = new RepeatTask(tTCnf);
				Timer timer = new Timer();
				timer.schedule(repeatTask, 0, tTCnf.schedulerDelay);
				System.out.println("TaskTracker started.");
			}
		} catch (Exception e) {
			System.out.println(e);
		}
	}
}
