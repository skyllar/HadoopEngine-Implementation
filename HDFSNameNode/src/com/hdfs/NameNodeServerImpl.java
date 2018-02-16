package com.hdfs;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

public class NameNodeServerImpl {

	public static NameNodeConfiguration nNCnf;

	public static int initialiseVariables(String[] args) {

		int status = 0;
		String nameNodeConfigFilePath;

		nameNodeConfigFilePath = args[0];
		// nameNodeConfigFilePath =
		// "/home/apratim/workspace/HDFSCore/namenode/nameNodeConfig";
		File file = new File(nameNodeConfigFilePath);
		if (file.exists()) {
			try {
				BufferedReader bufferedReader = new BufferedReader(
						new FileReader(file));
				String line;
				nNCnf = new NameNodeConfiguration();

				// nNCnf.remoteReference = "Namenode";
				// nNCnf.nameNodePort = 5001;
				// nNCnf.nameNodeIP = "localhost";
				// nNCnf.fileDescriptor = 0;
				// nNCnf.replicationFactor = 3;
				// nNCnf.blockNumber = 0;
				// nNCnf.thresholdTime = 200000;
				// nNCnf.fileToBlocksRegistryDir =
				// "/home/apratim/workspace/HDFSCore/namenode/";
				// nNCnf.fileToBlocksRegistryFileName = "fileBlocksDetails";
				// nNCnf.fileToBlocksRegistryDelimiter = "~";
				// nNCnf.lastBlockNumberFilePath=/home/apratim/workspace/HDFSCore/namenode/lastBlockNumber.txt

				line = bufferedReader.readLine();
				nNCnf.remoteReference = line.split("=")[1];

				line = bufferedReader.readLine();
				nNCnf.nameNodePort = Integer.parseInt(line.split("=")[1]);

				line = bufferedReader.readLine();
				nNCnf.nameNodeIP = line.split("=")[1];

				line = bufferedReader.readLine();
				nNCnf.fileDescriptor = Integer.parseInt(line.split("=")[1]);

				line = bufferedReader.readLine();
				nNCnf.replicationFactor = Integer.parseInt(line.split("=")[1]);

				line = bufferedReader.readLine();
				nNCnf.blockNumber = Integer.parseInt(line.split("=")[1]);

				line = bufferedReader.readLine();
				nNCnf.thresholdTime = Integer.parseInt(line.split("=")[1]);

				line = bufferedReader.readLine();
				nNCnf.fileToBlocksRegistryDir = line.split("=")[1];

				line = bufferedReader.readLine();
				nNCnf.fileToBlocksRegistryFileName = line.split("=")[1];

				line = bufferedReader.readLine();
				nNCnf.fileToBlocksRegistryDelimiter = line.split("=")[1];

				line = bufferedReader.readLine();
				nNCnf.lastBlockNumberFile = line.split("=")[1];

				bufferedReader.close();

				file = new File(nNCnf.lastBlockNumberFile);
				if (!file.exists())
					file.createNewFile();
				else {
					try {
						bufferedReader = new BufferedReader(
								new FileReader(file));
						line = bufferedReader.readLine();
						nNCnf.blockNumber = Integer
								.parseInt(line.split("=")[1]);
						bufferedReader.close();
					} catch (Exception e) {
						// e.printStackTrace();
						System.out
								.println("LastBlockNumberFile is invalid creating new file with default last block count..");
						file.createNewFile();
					}
				}

				status = 1;
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			status = 0;
		}
		return status;
	}

	public static void main(String args[]) {
		try {
			int status = initialiseVariables(args);
			if (status == 0) {
				System.out
						.println("NameNode Could not launch, Error Reading Configuration File..");
			} else {

				System.out.println("Namenode is starting..");
				INameNode nameNodeStub = new NameNodeInterfaceImpl(nNCnf);
				LocateRegistry.createRegistry(nNCnf.nameNodePort);
				Naming.rebind("rmi://" + nNCnf.nameNodeIP + ":"
						+ nNCnf.nameNodePort + "/" + nNCnf.remoteReference,
						nameNodeStub);
				System.out.println("Namenode started");

				// nameNodeStub.test();
			}
		} catch (Exception e) {

			e.printStackTrace();
		}
	}
}
