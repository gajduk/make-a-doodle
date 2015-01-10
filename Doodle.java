import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;


@SuppressWarnings({ "rawtypes", "unchecked" })
public class Doodle extends JFrame {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4096806544983110185L;

	private class Canvas extends JPanel {
		
		/**
		 * 
		 */
		private static final long serialVersionUID = 4161588462410511138L;

		private class Linija {
			int sx,sy,ex,ey,r;
			Color color;
			
			public Linija ( int sx , int sy , int ex , int ey , int r , Color color ) {
				this.sx = sx;
				this.sy = sy;
				this.ex = ex;
				this.ey = ey;
				this.r = r;
				this.color = color;
			}
			
			public void paint (Graphics g ) {
				g.setColor(color);
				((Graphics2D) g).setStroke(new BasicStroke(r,BasicStroke.CAP_ROUND,BasicStroke.JOIN_ROUND));
				g.drawLine(sx, sy, ex, ey);
			}
		}
		
		public Color current_color;
		public int current_radius;
		private int lastx,lasty;
		public ArrayList<Linija> tocki;
		
		public Canvas( Color c , int r ) {
			current_color = c;
			current_radius = r;
			tocki = new ArrayList<Linija>();
			addMouseMotionListener(new MouseMotionListener() {
				
				@Override
				public void mouseMoved(MouseEvent arg0) {
					
				}
				
				@Override
				public void mouseDragged(MouseEvent arg0) {
					tocki.add(new Linija(lastx,lasty,arg0.getX(),arg0.getY(),current_radius,current_color));
					lastx = arg0.getX();
					lasty = arg0.getY();
					repaint();
				}
			});
			addMouseListener(new MouseListener() {
				
				@Override
				public void mouseReleased(MouseEvent arg0) {
					
				}
				
				@Override
				public void mousePressed(MouseEvent arg0) {
					lastx = arg0.getX();
					lasty = arg0.getY();
				}
				
				@Override
				public void mouseExited(MouseEvent arg0) {
				}
				
				@Override
				public void mouseEntered(MouseEvent arg0) {
				}
				
				@Override
				public void mouseClicked(MouseEvent arg0) {
				}
			});
		}
		
		@Override
		public void paint(Graphics g) {
			super.paint(g);
			for ( Linija k : tocki ) k.paint(g);
					((Graphics2D) g).setStroke(new BasicStroke(5));
					((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, // Anti-alias!
					        RenderingHints.VALUE_ANTIALIAS_ON);
					g.setColor(Color.black);
					g.drawLine(0, 0,this.getWidth(),0);
					((Graphics2D) g).setStroke(new BasicStroke(8));

					g.drawLine(this.getWidth(), 0,this.getWidth(),getHeight());

					g.drawLine(0, 0,0,getHeight());

					g.drawLine(0, getHeight(),this.getWidth(),getHeight());
		}

		public void reset() {
			tocki.clear();
		}
		
	}
	
	public JButton clear_btn,color_chooser;
	public JComboBox combo_box;
	public Canvas panel_za_crtanje;
	public JPanel meni_panel;
	
	public Color default_color = Color.black;
	public int default_radius = 1;
	
	public Doodle () {
		this.setTitle("DOODLE");
		this.setLayout(new BorderLayout());
		panel_za_crtanje = new Canvas(default_color,default_radius);
		meni_panel = new JPanel();
		this.add(panel_za_crtanje,BorderLayout.CENTER);
		color_chooser = new JButton("Choose a color...");
		clear_btn = new JButton("Clear");
		combo_box = new JComboBox(new String[]{"1","2","3","4","5","10","20","50","100","800"});
		combo_box.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				panel_za_crtanje.current_radius = Integer.parseInt((String)combo_box.getSelectedItem());
			}
		});
		clear_btn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				panel_za_crtanje.reset();
				repaint();
			}
		});
		color_chooser.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Color nc = JColorChooser.showDialog(null,"Boja", panel_za_crtanje.current_color);
				if ( nc != null ) 
					panel_za_crtanje.current_color = nc;
			}
		});
		meni_panel.add(color_chooser);
		combo_box.setToolTipText("Choose width");
		meni_panel.add(combo_box);
		meni_panel.add(clear_btn);
		this.add(meni_panel,BorderLayout.NORTH);
		this.setSize(700,500);
		this.setResizable(false);
	}
	
	public static void main(String[] args) {
		Doodle c = new Doodle();
		c.setVisible(true);
	}

}
