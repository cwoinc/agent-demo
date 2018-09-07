package com.wantdo.agent;

import com.sun.tools.attach.VirtualMachine;
import com.sun.tools.attach.VirtualMachineDescriptor;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author king
 * @version 2018-09-06 11:09 PM
 */
public class Main {
    
    public static void main(String[] args) throws Exception {
        List<VirtualMachineDescriptor> listAfter = VirtualMachine.list();
        for (VirtualMachineDescriptor vmd : listAfter) {
            if (vmd.displayName().startsWith("com.wantdo.agent.Main")) {
                VirtualMachine attach = VirtualMachine.attach(vmd.id());
                attach.loadAgent("/home/king/diy/workspace/diy-test/agentdemo/agent/target/agent-1.0-SNAPSHOT.jar");
                attach.detach();
                break;
            }
        }
        
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        Target target = new Target();
        target.doBusiness("dsad", "hndsjadsaa");
        target.doTest("1", "2", "3");
    }
}