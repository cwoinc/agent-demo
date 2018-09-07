package com.wantdo.agent;

import com.wantdo.agent.transformer.AgentClass;

import java.lang.instrument.Instrumentation;

/**
 * @author king
 * @version 2018-09-06 11:33 PM
 */
public class QingHao {
    public static void agentmain(String agentArgs, Instrumentation inst) {
        //inst.addTransformer(new AgentClass(), true);
        //inst.retransformClasses(AgentClass.class);
        
        inst.addTransformer(new AgentClass());
        
        System.out.println("Agent Main Done");
        
    }
}