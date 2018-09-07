package com.wantdo.agent;

import com.wantdo.agent.transformer.FirstAgent;

import java.lang.instrument.Instrumentation;

/**
 * @author king
 * @version 2018-09-04 10:47 PM
 */
public class App {
    public static void premain(String agentOps, Instrumentation inst) {
        System.out.println("=========premain方法执行========");
        // 添加Transformer
        inst.addTransformer(new FirstAgent());
    }
    
}