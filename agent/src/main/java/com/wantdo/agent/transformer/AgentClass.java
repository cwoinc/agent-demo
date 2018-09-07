package com.wantdo.agent.transformer;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.CtNewMethod;

import java.lang.instrument.ClassFileTransformer;
import java.security.ProtectionDomain;

/**
 * @author king
 * @version 2018-09-06 11:33 PM
 */
public class AgentClass implements ClassFileTransformer {
    
    private static final String CLASS_NAME = "com.wantdo.agent.Target";
    
    @Override
    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) {
        className = className.replace("/", ".");
        if (CLASS_NAME.equals(className)) {
            try {
                CtClass ctClass = ClassPool.getDefault().getCtClass(CLASS_NAME);
                
                CtMethod ctMethod = ctClass.getDeclaredMethod("doBusiness");
                CtMethod doTestMethod = ctClass.getDeclaredMethod("doTest");
                
                String name = ctMethod.getReturnType().getName();
                //拷贝一个方法
                CtMethod copy = CtNewMethod.copy(ctMethod, "doBusiness", ctClass, null);
                CtMethod doTestCopy = CtNewMethod.copy(doTestMethod, "doTest", ctClass, null);
                
                String oldMethodName = "doBuesiness$Impl";
                ctMethod.setName(oldMethodName);
                doTestMethod.setName("aabb");
                
                // 对输入参数的监控
                ctMethod.insertBefore(("System.out.println(\"a 参数\" + a) ; System.out.println( \"b 参数\"+b) ;  "));
                
                String st = ("{");
                st += (name + " xx = null;  ");
                st += ("xx = " + oldMethodName + "($$);");
                st += ("System.out.println( \" 执行结果\" + xx );");
                // 返回的对象
                st += ("return null;");
                st += ("}");
                System.out.println(st);
                copy.setBody(st);
                ctClass.addMethod(copy);
                
                //// 不知为何报错
                //doTestCopy.setBody("{doTest($$);}");
                ctClass.addMethod(doTestCopy);
                
                return ctClass.toBytecode();
                
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
