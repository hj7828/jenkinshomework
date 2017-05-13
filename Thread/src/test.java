import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class test extends JFrame {
   
   private JLabel label = new JLabel();
   private JLabel label2 = new JLabel();
   private JTextField tf = new JTextField();
   MyThread th;
   MyThread th2;
   public test() {
      setTitle("나의 프레임");
      Container c = getContentPane();
      c.setLayout(new FlowLayout());
       
       
       
       c.add(tf);
       tf.setFont(new Font("Gothic", Font.ITALIC, 100));
       tf.addActionListener(new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			JTextField t= (JTextField)e.getSource();
			String s = t.getText();
			
			
			int n=0;
			try
			{
			 n = Integer.parseInt(s);
			}
			catch (NumberFormatException ex) {
				t.setText(" ");
				return;
			}
			th.setNumber(n);
			
		}
	});
      
      label.setFont(new Font("Gothic", Font.ITALIC, 60));
      label.setHorizontalAlignment(JLabel. CENTER);
      label.setBackground(Color.YELLOW);
      label.setOpaque(true);
      c.add(label);
      
      label2.setFont(new Font("Gothic", Font.ITALIC, 60));
      label2.setHorizontalAlignment(JLabel. CENTER);
      label2.setBackground(Color.GREEN);
      label2.setOpaque(true);
      c.add(label2);
      
      setSize(300, 300);
      setVisible(true);
      
      c.addMouseListener(new MouseAdapter() {
    	  public void mousePressed(MouseEvent e){
    		  th.interrupt();
    		
    	  }
    	  
	});
      
      th2 = new MyThread(label2, 200, null);
      th2.start();
      
      th = new MyThread(label, 100, th2);
      th.start();
      
      Thread th3 = new Thread(new ColorThread(label));
      th3.start();
      
   }
   
   class ColorThread implements Runnable{
	   private JLabel label;
	   public ColorThread(JLabel label)
	   {
		   this.label = label;
	   }
	   public void run(){
		   while(true)
		   {
			   int r = (int)(Math.random()*256);
			   int g = (int)(Math.random()*256);
			   int b = (int)(Math.random()*256);
			Color c = new Color(r,g,b);
			label.setForeground(c);
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		   }
		   
	   }
   }
   
   class MyThread extends Thread {
      private JLabel label;
      private long delay;
      private Thread next;
      private int n;
      public MyThread(JLabel label, long delay, Thread next){
         this.label = label;
         this.delay = delay;
         this.next = next;
         setNumber(n);
         
      }
           
      public void setNumber(int n)
      {
    	  this.n=n;
      }
      public void run(){
         
         while(true){
            label.setText(Integer.toString(n));
            try{
            sleep(delay);
            n++;
            } catch (InterruptedException e){
            	label.setText(label.getText()+"후 killed");
            	if(next != null)
            	{
            		next.interrupt();
            	}
               break;
            }
         }
      }
   }
   
   public static void main(String[] args) { //main함수가죽으면 thread도 죽음
	  Thread m = Thread.currentThread();
	  int n= Thread.activeCount();
	  System.out.println(n);
	  System.out.println(m.getName());
	  System.out.println(m.getId());
	  System.out.println(m.getPriority());
	  System.out.println(m.getState()); 
      test f = new test();
      
      n= Thread.activeCount();
      
   }
}