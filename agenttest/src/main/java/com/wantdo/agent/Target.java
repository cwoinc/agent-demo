package com.wantdo.agent;

/**
 * @author king
 * @version 2018-09-06 11:34 PM
 */
public class Target {
    
    public TargetReturn doBusiness(String a, String b) {
        TargetReturn targetReturn = new TargetReturn();
        targetReturn.setA(a);
        targetReturn.setB(b);
        return targetReturn;
    }
    
    public void doTest(String a, String b, String c) {
        System.out.println(a + "-" + b + "+" + c);
    }
    
    private class TargetReturn {
        private String a;
        private String b;
        
        public String getA() {
            return a;
        }
        
        public void setA(String a) {
            this.a = a;
        }
        
        public String getB() {
            return b;
        }
        
        public void setB(String b) {
            this.b = b;
        }
        
        @Override
        public String toString() {
            return a + "\t" + b;
        }
    }
    
}
