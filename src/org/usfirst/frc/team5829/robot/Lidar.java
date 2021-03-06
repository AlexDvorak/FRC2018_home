package org.usfirst.frc.team5829.robot;

import java.util.Vector;

import edu.wpi.first.wpilibj.DigitalOutput;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.I2C.Port;

public class Lidar {
	
	private static final LidarController lidar = new LidarController();
	
	public static class LidarController{
		private I2C i2c;
		private Thread updateThread;
		private Vector<Lidar> lidarVector;
		private int counter;
		
		private final int LIDAR_CONFIG_REGISTER = 0x00;
		private final int LIDAR_DISTANCE_REGISTER = 0x8f;
		private final int LIDAR_ADDRESS = 0x62;
		
		public LidarController() {
			i2c = new I2C(Port.kMXP, LIDAR_ADDRESS);
			lidarVector = new Vector<Lidar>();
			counter = 0;
			updateThread = new Thread(){
				public void wait(int timeMs) {
					try {
						Thread.sleep(timeMs);
					}
					catch(InterruptedException e) {
						e.printStackTrace();
					}
				}
				
				public void run() {
					while(true) {
						if(lidarVector.size() != 0) {
							Lidar lidar = (Lidar) lidarVector.get(counter);
							if(lidar == null) {
								wait(50);
							} else {
								lidar.setDigOutState(true);
								wait(10);
								byte[] by = new byte[2];
								i2c.write(LIDAR_CONFIG_REGISTER, 0x04);
								wait(30);
								i2c.read(LIDAR_DISTANCE_REGISTER, 2, by);
								int output = (int) Integer.toUnsignedLong(by[0] << 8) + Byte.toUnsignedInt(by[1]);
								lidar.setDistanceCm(output);
								lidar.setDigOutState(true);
								counter++;
								if(counter == lidarVector.size())
									counter = 0;
							}
						}
					}
				}
			};
			updateThread.start();
		}
		
		public void registerLidar(Lidar lidar) {
			lidarVector.add(lidar);
		}
		
	}
	
	private double distance;
	private DigitalOutput output;
	
	public Lidar(int port) {
		this.output = new DigitalOutput(port);
		this.distance = 0;
		lidar.registerLidar(this);
	}
	
	private void setDigOutState(boolean a) {
		output.set(a);
	}

	public synchronized double getDistanceIn() {
		return distance / 2.54;
	}

	private synchronized void setDistanceCm(double a) {
		this.distance = a;
	}

}
