package com.wantdo.agent;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;

public class Test {
    
    public static void main(String[] args) throws Exception {
        ClassPool classPool = ClassPool.getDefault();
        // 必须将class文件放在这个工程编译后的class文件中，路径也对应起来
        CtClass ctClass = classPool.get("com.ambitionstone.esa2000.pki.AZTPkiServlet");
        
        //设置方法需要的参数，一定要能匹配起来，而且必须引入这些参数类的包
        CtClass[] param = new CtClass[4];
        param[0] = classPool.get("javax.servlet.http.HttpServletRequest");
        param[1] = classPool.get("javax.servlet.http.HttpServletResponse");
        param[2] = classPool.get("int");
        param[3] = classPool.get("java.lang.String");
        
        // 找到需要修改的行所在的方法
        CtMethod method = ctClass.getDeclaredMethod("doOnlineValidate", param);
        
        // 在这个方法的182行添加关闭文件流的方法
        method.insertAt(182, "fin.close();");
        
        // 将文件写到指定的目录，生成之后在test\com\ambitionstone\esa2000\pki\AZTPkiServlet\这个文件夹下面可以找到编译后的类
        ctClass.writeFile("D:\\test");
    }
}