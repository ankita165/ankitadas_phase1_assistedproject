package accessModifiers;

 public class accessmodifers {
 public void meth1() {
 System.out.println("public access modifiers implemt");
 }
 private void meth2() {
 System.out.println("private access modifiers implemt");
 }
void meth3() {
 System.out.println("default access modifiers implement");
 }
 protected void meth4() {
 System.out.println("protected access modifiers implement");
 }
 public static void main(String[] args) {

 accessmodifers mod=new accessmodifers();
 mod.meth1() ;
 mod.meth2();
 mod.meth3();
 mod.meth4();

}
 }