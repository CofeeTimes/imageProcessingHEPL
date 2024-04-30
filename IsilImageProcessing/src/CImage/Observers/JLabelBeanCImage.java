package CImage.Observers;

import CImage.*;
import CImage.Observers.Events.*;
import java.awt.Color;
import java.awt.event.*;
import java.awt.image.*;
import java.util.*;
import javax.swing.*;

public class JLabelBeanCImage extends JLabel implements Observer, MouseListener, MouseMotionListener
{
    public final static int INACTIF            = 0;
    public final static int CLIC               = 1;
    public final static int SELECT_RECT        = 2;
    public final static int SELECT_RECT_FILL   = 3;
    public final static int SELECT_LIGNE       = 4;
    public final static int SELECT_CERCLE      = 5;
    public final static int SELECT_CERCLE_FILL = 6;
    
    private CImage cimage;
    
    private Vector ClicListeners;
    private Vector SelectLigneListeners;
    private Vector SelectRectListeners;
    private Vector SelectRectFillListeners;
    private Vector SelectCercleListeners;
    private Vector SelectCercleFillListeners;
    
    private int x1,y1,x2,y2;
    private boolean demi;
    private Color couleurPinceau;
    private BufferedImage tmp;
    
    private int mode;
    
    /** Creates a new instance of JLabelCImage */
    public JLabelBeanCImage() 
    {
        super();
        setVerticalAlignment(TOP);
        setHorizontalAlignment(LEFT);

        cimage = null;
        tmp = null;
        setCouleurPinceau(Color.BLACK);
        setMode(INACTIF);
        
        ClicListeners = new Vector();
        SelectLigneListeners = new Vector();
        SelectRectListeners = new Vector();
        SelectRectFillListeners = new Vector();
        SelectCercleListeners = new Vector();
        SelectCercleFillListeners = new Vector();
        demi = false;
        
        addMouseListener(this);
        addMouseMotionListener(this);
    }
    
    public JLabelBeanCImage(CImage ci)
    {
        super();
        setVerticalAlignment(TOP);
        setHorizontalAlignment(LEFT);

        cimage = null;
        tmp = null;
        setCouleurPinceau(Color.BLACK);
        setMode(INACTIF);
        setCImage(ci);
        
        ClicListeners = new Vector();
        SelectLigneListeners = new Vector();
        SelectRectListeners = new Vector();
        SelectRectFillListeners = new Vector();
        SelectCercleListeners = new Vector();
        SelectCercleFillListeners = new Vector();
        demi = false;
        
        addMouseListener(this);
        addMouseMotionListener(this);
    }
    
    public void setCImage(CImage ci)
    {
        if (cimage != null) cimage.setObserver(null);
        cimage = ci;
        cimage.setObserver(this);
        update();
    }
    
    public void update()
    {
        setIcon(new ImageIcon(cimage.getImage()));
    }
    
    public CImage getCImage()
    {
        return cimage;
    }
    
    public void addClicListener(ClicListener cl)
    { if (!ClicListeners.contains(cl)) ClicListeners.addElement(cl); }

    public void addSelectLigneListener(SelectLigneListener sll)
    { if (!SelectLigneListeners.contains(sll)) SelectLigneListeners.addElement(sll); }

    public void addSelectRectListener(SelectRectListener srl)
    { if (!SelectRectListeners.contains(srl)) SelectRectListeners.addElement(srl); }

    public void addSelectRectFillListener(SelectRectFillListener l)
    { if (!SelectRectFillListeners.contains(l)) SelectRectFillListeners.addElement(l); }

    public void addSelectCercleListener(SelectCercleListener l)
    { if (!SelectCercleListeners.contains(l)) SelectCercleListeners.addElement(l); }

    public void addSelectCercleFillListener(SelectCercleFillListener l)
    { if (!SelectCercleFillListeners.contains(l)) SelectCercleFillListeners.addElement(l); }

    public void removeClicListener(ClicListener l)
    { if (ClicListeners.contains(l)) ClicListeners.removeElement(l); }

    public void removeSelectLigneListener(SelectLigneListener l)
    { if (SelectLigneListeners.contains(l)) SelectLigneListeners.removeElement(l); }

    public void removeSelectRectListener(SelectRectListener l)
    { if (SelectRectListeners.contains(l)) SelectRectListeners.removeElement(l); }

    public void removeSelectRectFillListener(SelectRectFillListener l)
    { if (SelectRectFillListeners.contains(l)) SelectRectFillListeners.removeElement(l); }

    public void removeSelectCercleListener(SelectCercleListener l)
    { if (SelectCercleListeners.contains(l)) SelectCercleListeners.removeElement(l); }

    public void removeSelectCercleFillListener(SelectCercleFillListener l)
    { if (SelectCercleFillListeners.contains(l)) SelectCercleFillListeners.removeElement(l); }

    protected void notifyClicDetected(UnClicEvent e)
    {
        int n = ClicListeners.size();
        for (int i=0 ; i<n ; i++)
        {
            ClicListener obj = (ClicListener)ClicListeners.elementAt(i);
            obj.ClicDetected(e);
        }
    }

    protected void notifySelectLigneDetected(DeuxClicsEvent e)
    {
        int n = SelectLigneListeners.size();
        for (int i=0 ; i<n ; i++)
        {
            SelectLigneListener obj = (SelectLigneListener)SelectLigneListeners.elementAt(i);
            obj.SelectLigneDetected(e);
        }
    }

    protected void notifySelectRectDetected(DeuxClicsEvent e)
    {
        int n = SelectRectListeners.size();
        for (int i=0 ; i<n ; i++)
        {
            SelectRectListener obj = (SelectRectListener)SelectRectListeners.elementAt(i);
            obj.SelectRectDetected(e);
        }
    }

    protected void notifySelectRectFillDetected(DeuxClicsEvent e)
    {
        int n = SelectRectFillListeners.size();
        for (int i=0 ; i<n ; i++)
        {
            SelectRectFillListener obj = (SelectRectFillListener)SelectRectFillListeners.elementAt(i);
            obj.SelectRectFillDetected(e);
        }
    }

    protected void notifySelectCercleDetected(DeuxClicsEvent e)
    {
        int n = SelectCercleListeners.size();
        for (int i=0 ; i<n ; i++)
        {
            SelectCercleListener obj = (SelectCercleListener)SelectCercleListeners.elementAt(i);
            obj.SelectCercleDetected(e);
        }
    }

    protected void notifySelectCercleFillDetected(DeuxClicsEvent e)
    {
        int n = SelectCercleFillListeners.size();
        for (int i=0 ; i<n ; i++)
        {
            SelectCercleFillListener obj = (SelectCercleFillListener)SelectCercleFillListeners.elementAt(i);
            obj.SelectCercleFillDetected(e);
        }
    }

    // ***** Gestion de la souris *****
    public void mouseClicked(MouseEvent e) 
    {
        if (getMode() == CLIC)
        {
            UnClicEvent uce = new UnClicEvent(this,e.getX(),e.getY());
            notifyClicDetected(uce);
        }
        
        if (getMode() == SELECT_RECT || getMode() == SELECT_RECT_FILL || getMode() == SELECT_LIGNE || getMode() == SELECT_CERCLE || getMode() == SELECT_CERCLE_FILL)
        {
            if(demi == false)
            {
                x1 = e.getX();
                y1 = e.getY();
                tmp = new BufferedImage(cimage.getLargeur(),cimage.getHauteur(),BufferedImage.TYPE_INT_ARGB);
                demi = true;
            }
            else
            {
                x2 = e.getX();
                y2 = e.getY();
                DeuxClicsEvent dce = new DeuxClicsEvent(this,x1,y1,x2,y2);
                if (getMode() == SELECT_RECT) notifySelectRectDetected(dce);
                if (getMode() == SELECT_RECT_FILL) notifySelectRectFillDetected(dce);
                if (getMode() == SELECT_LIGNE) notifySelectLigneDetected(dce);
                if (getMode() == SELECT_CERCLE) notifySelectCercleDetected(dce);
                if (getMode() == SELECT_CERCLE_FILL) notifySelectCercleFillDetected(dce);
                demi = false;
            }
        }
    }

    public void mousePressed(MouseEvent e) { }
    public void mouseReleased(MouseEvent e) { }
    public void mouseEntered(MouseEvent e) { }
    public void mouseExited(MouseEvent e) { }
    public void mouseDragged(MouseEvent e) { } 

    public void mouseMoved(MouseEvent e) 
    { 
        if (getMode() == SELECT_LIGNE || getMode() == SELECT_RECT || getMode() == SELECT_RECT_FILL || getMode() == SELECT_CERCLE || getMode() == SELECT_CERCLE_FILL)
        {
            if (demi == true)
            {
                int x = e.getX();
                int y = e.getY();
                tmp.getGraphics().drawImage(cimage.getImage(),0,0,this);

                /*
                tmp.getGraphics().setColor(couleurPinceau);
                if (getMode() == SELECT_LIGNE) tmp.getGraphics().drawLine(x1,y1,x,y);
                if (getMode() == SELECT_RECT) tmp.getGraphics().drawRect(x1,y1,(x-x1),(y-y1));
                if (getMode() == SELECT_RECT_FILL) tmp.getGraphics().fillRect(x1,y1,(x-x1+1),(y-y1+1));
                if (getMode() == SELECT_CERCLE)
                {
                    int rayon = (int)Math.sqrt((x-x1)*(x-x1)+(y-y1)*(y-y1));
                    getGraphics().drawOval(x1-rayon,y1-rayon,2*rayon,2*rayon);
                }

                getGraphics().drawImage(tmp,0,0,this);
                //setIcon(new ImageIcon(tmp));
                */
                
                
                getGraphics().drawImage(tmp,0,0,this);
                getGraphics().setColor(couleurPinceau);
                
                if (getMode() == SELECT_LIGNE) getGraphics().drawLine(x1,y1,x,y);
                if (getMode() == SELECT_RECT) getGraphics().drawRect(x1,y1,(x-x1),(y-y1));
                if (getMode() == SELECT_RECT_FILL) getGraphics().fillRect(x1,y1,(x-x1+1),(y-y1+1));
                if (getMode() == SELECT_CERCLE)
                {
                    int rayon = (int)Math.sqrt((x-x1)*(x-x1)+(y-y1)*(y-y1));
                    getGraphics().drawOval(x1-rayon,y1-rayon,2*rayon,2*rayon);
                }
                if (getMode() == SELECT_CERCLE_FILL)
                {
                    int rayon = (int)Math.sqrt((x-x1)*(x-x1)+(y-y1)*(y-y1));
                    getGraphics().fillOval(x1-rayon,y1-rayon,2*rayon,2*rayon);
                }
                 
            }
        }
    }

    public Color getCouleurPinceau() {
        return couleurPinceau;
    }

    public void setCouleurPinceau(Color couleurPinceau) {
        this.couleurPinceau = couleurPinceau;
    }

    public int getMode() {
        return mode;
    }

    public void setMode(int mode) {
        this.mode = mode;
    }
}
