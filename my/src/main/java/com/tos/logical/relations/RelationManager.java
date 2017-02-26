package com.tos.logical.relations;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import com.tos.enums.Command;
import com.tos.logical.relations.bt.BehaviorTree;
import com.tos.logical.relations.bt.composite.BTSequence;
import com.tos.logical.relations.bt.model.BTExecution;
import com.tos.logical.relations.bt.model.BTOperatorUntilTimeOut.OperatorEntity;
import com.tos.logical.relations.bt.model.BTOperatorode;
import com.tos.module.devices.Device;
import com.tos.utils.LogManager;

/**
 * 用于管理各个设备之间的联动
 * 
 * @author wulinan
 *
 */
public class RelationManager {
	private static final Logger logger = LogManager.getLogger(RelationManager.class);
	
	private List<BehaviorTree> behaviorTrees = new ArrayList<>();
	private Thread mainLoopThread;
	
	private static RelationManager instance = new RelationManager();
	
	public static RelationManager getInstance() {
		return instance;
	}
	

	/**
	 * 行为树主循环
	 */
	private void mainLoop() {
		while (true) {
			try {
				List<BehaviorTree> failedTrees = new ArrayList<>();
				
				List<BehaviorTree> successTrees = new ArrayList<>();
				
				for (BehaviorTree bTree : behaviorTrees) {
					int status = bTree.execute();
					if (status == BTExecution.CONST_EXEC_FAILURE) {
						//TODO 
						failedTrees.add(bTree);
						logger.fine(bTree+ " CONST_EXEC_FAILURE");
					}
					if(status == BTExecution.CONST_EXEC_SUCCESS){
						successTrees.add(bTree);
						logger.fine(bTree+ " CONST_EXEC_SUCCESS");
					}
				}
				for(BehaviorTree failedTree : failedTrees){
					behaviorTrees.remove(failedTree);
				}
				for(BehaviorTree successTree : successTrees){
					behaviorTrees.remove(successTree);
				}
				// 间隔0.5s
				try {
					TimeUnit.MILLISECONDS.sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 把主循环跑在线程上
	 */
	public synchronized void runMainLoopAsThread() {
		Thread thread = new Thread(new Runnable() {
			
			@Override
			public void run() {
				mainLoop();

			}
		});
		mainLoopThread = thread;
		logger.info("----start AI main loop---");
		mainLoopThread.start();
	}

	public synchronized void addBehaviorTree(BehaviorTree bt) {
		behaviorTrees.add(bt);
	}
	public static BehaviorTree generateTestBt(Device device) {
		logger.fine("-----generateTestBt----" + device);
		BehaviorTree bt = new BehaviorTree();
		BTSequence btSequence = new BTSequence();
		bt.setRoot(btSequence);
		
		BTOperatorode btOperatorode = new BTOperatorode();
		OperatorEntity operatorEntity = new OperatorEntity();
		operatorEntity.cmd = Command.TurnOn.toCmd();
		btOperatorode.init(operatorEntity,device);
		btSequence.add(btOperatorode);
		
		BTOperatorode btOperatorode1 = new BTOperatorode();
		OperatorEntity operatorEntity1 = new OperatorEntity();
		operatorEntity1.cmd = Command.TurnOff.toCmd();
		btOperatorode1.init(operatorEntity1,device);
		
		btSequence.add(btOperatorode1);

		return bt;
	}
	
	/**
	 * 从json中生成一个行为树
	 * @param Json
	 * @return
	 */
	public static BehaviorTree generateBTFromJson(String Json){
		BehaviorTree behaviorTree = new BehaviorTree();
		return behaviorTree;
	}

}
