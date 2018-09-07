package com.wantdo.agent;

/**
 * @author king
 * @version 2018-09-04 11:03 PM
 */
public class AppTest {
    // 在VM option 中添加该命令
    // -javaagent:/home/king/diy/workspace/diy-test/agentdemo/agent/target/agent-1.0-SNAPSHOT.jar
    
    public static void main(String[] args) {
        hello();
    }
    
    public static void hello() {
        System.out.println("hello begin");
        try {
            Thread.sleep(5 * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("hello end");
    }
    
}
