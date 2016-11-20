package model;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import controller.Controller;

/**
 * This class create a server and handle with clients.
 * @param port 
 * @param server - serverSocket to create connection with clients.
 * @param clientHandler - for handle with the clients.
 * @param numOfClients - indicate the number of threads witch run at the same time.
 * @param stop - volatile boolean, can be changed in different threads.
 * @param threadPool - manage the threads.
 * @param mainServerThread - indicate the main server thread.
 * @param controller.
 * @author Moran
 */
public class MyServer {
	int port;
	ServerSocket server;
	
	ClientHandler clientHandler;
	int numOfClients;
	ExecutorService threadpool;
	
	volatile boolean stop;
	
	Thread mainServerThread;
	Controller controller;
	int clientsHandled = 0;
	
	/**
	 * C'tor
	 * @param port
	 * @param clientHandler
	 * @param numOfClients
	 * @param controller
	 */
	public MyServer(int port, ClientHandler clientHandler, int numOfClients, Controller controller) {
		super();
		this.port = port;
		this.clientHandler = clientHandler;
		this.numOfClients = numOfClients;
		this.controller = controller;
		this.stop = false;
	}
	/**
	 * This method start the for-loop witch listen to the clients who want to connect the server
	 * @throws Exception
	 */
	public void start() {
		try {
			server = new ServerSocket(port);
			server.setSoTimeout(10 * 1000);
		} catch (IOException e) {
			e.printStackTrace();
		}
		threadpool = Executors.newFixedThreadPool(numOfClients);

		mainServerThread = new Thread(new Runnable() {

			@Override
			public void run() {
				while (!stop) {
					try {
						// server.accept() - return socket for some client
						final Socket someClient = server.accept();
						if (someClient != null) {
							threadpool.execute(new Runnable() {

								@Override
								public void run() {
									try {
										clientsHandled++;

										controller.display("handling client " + clientsHandled);
										clientHandler.handleClient(someClient.getInputStream(),someClient.getOutputStream());
										someClient.close();
										controller.display("done handling client " + clientsHandled);
									} catch (IOException e) {
										e.printStackTrace();
									}
								}
							});
						}
					} catch (SocketTimeoutException e) {
						// System.out.println("no clinet connected...");
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				// controller.display("done accepting new clients.");
			}// end of the mainServerThread task
		});
		mainServerThread.start();
	}

	/**
	 * This method stop the communication with the server
	 */
	public void close(){
		stop = true;
		// do not execute jobs in queue, continue to execute running threads
		threadpool.shutdown();
		// wait 10 seconds over and over again until all running jobs have finished
		try {
			while (!(threadpool.awaitTermination(10, TimeUnit.SECONDS))) {
				threadpool.shutdown();
			}
			System.out.println("all the tasks have finished");
			// join means that after mainServerThread will finish, the next rows
			// in the code will implement
			mainServerThread.join();
			System.out.println("main server thread is done");
			System.out.println("server is safely closed");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		try {
			server.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
