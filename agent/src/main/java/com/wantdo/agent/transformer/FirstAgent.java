package com.wantdo.agent.transformer;

import javassist.*;

import java.io.ByteArrayInputStream;
import java.lang.instrument.ClassFileTransformer;
import java.security.ProtectionDomain;

/**
 * @author king
 * @version 2018-09-04 10:46 PM
 */
public class FirstAgent implements ClassFileTransformer {
    public final String injectedClassName = "com.wantdo.agent.AppTest";
    public final String methodName = "hello";
    
    @Override
    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) {
        className = className.replace("/", ".");
        if (className.equals(injectedClassName)) {
            try {
                // 使用全称,用于取得字节码类<使用javassist>
                CtClass ctclass = ClassPool.getDefault().get(className);
                // 得到这方法实例
                CtMethod ctmethod = ctclass.getDeclaredMethod(methodName);
                
                ctmethod.addLocalVariable("t1", CtClass.longType);
                ctmethod.insertBefore("t1 = System.currentTimeMillis();");
                ctmethod.insertAfter("System.out.println(\"本次执行耗时（毫秒）：\"+(System.currentTimeMillis()-t1));");
                return ctclass.toBytecode();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
        
        //aa(loader, className, classBeingRedefined, protectionDomain, classfileBuffer);
    }
    
    private byte[] aa(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) {
        byte[] transformed = null;
        System.out.println("Transforming " + className);
        ClassPool pool = ClassPool.getDefault();
        CtClass cl = null;
        try {
            cl = pool.makeClass(new ByteArrayInputStream(classfileBuffer));
            if (!cl.isInterface()) {
                CtBehavior[] methods = cl.getDeclaredBehaviors();
                for (CtBehavior method : methods) {
                    doMethod(method);
                }
                transformed = cl.toBytecode();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cl != null) {
                cl.detach();
            }
        }
        return transformed;
    }
    
    private void doMethod(CtBehavior method) throws NotFoundException, CannotCompileException {
        if (methodName.equalsIgnoreCase(method.getName())) {
            //添加局部变量，如果不同过addLocalVariable设置，在调用属性时将出现compile error: no such field: startTime
            method.addLocalVariable("startTime", CtClass.longType);
            method.insertBefore("System.out.println(startTime);");
            method.insertBefore("startTime = System.currentTimeMillis();");
//			method.insertBefore("long startTime = System.currentTimeMillis();System.out.println(startTime);");
            method.insertBefore("System.out.println(\"insert before ......\");");
            method.insertAfter("System.out.println(\"leave " + method.getName() + " and time is :\" + (System.currentTimeMillis() - startTime));");
        }
    }
    
}

